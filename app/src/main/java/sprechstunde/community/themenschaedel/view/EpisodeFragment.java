package sprechstunde.community.themenschaedel.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.chip.ChipGroup;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.episodeList.EpisodeHostAdapter;
import sprechstunde.community.themenschaedel.adapter.episodeList.EpisodeTopicAdapter;
import sprechstunde.community.themenschaedel.databinding.FragmentEpisodeBinding;
import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.model.topic.TopicWithSubtopic;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;

public class EpisodeFragment extends Fragment implements ChipGroup.OnCheckedChangeListener {

    FragmentEpisodeBinding mBinding;

    public EpisodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_info_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.menu_info) {
            CustomPopupWindow popupWindow = new CustomPopupWindow();
            popupWindow.showSortPopup(R.id.dialog_info_topic_layout, R.layout.dialog_info_episode_types, requireActivity());
        }

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
        NavController nvController = Objects.requireNonNull(navHostFragment).getNavController();
        return  NavigationUI.onNavDestinationSelected(item, nvController);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentEpisodeBinding.inflate(inflater, container, false);
        int id = EpisodeFragmentArgs.fromBundle(requireArguments()).getEpisodeId();

        EpisodeViewModel episodeViewModel = new ViewModelProvider(requireActivity()).get(EpisodeViewModel.class);
        TopicViewModel topicViewModel = new ViewModelProvider(requireActivity()).get(TopicViewModel.class);

        episodeViewModel.getEpisodeWithHosts(id).observe(getViewLifecycleOwner(), episodeWithHosts -> {
            Episode episode = episodeWithHosts.getEpisode();
            mBinding.fragmentEpisodeTitle.setText(episode.getTitle());
            String number =  getString(R.string.list_item_topic_number) + " " + episode.getEpisodeNumber();
            mBinding.fragmentEpisodeNumber.setText(number);
            mBinding.fragmentEpisodeDate.setText(episode.getDate());
            mBinding.fragmentEpisodeLength.setText(episode.getDuration());
            mBinding.fragmentEpisodeLikes.setText(String.valueOf(episode.getUpvotes()));
            mBinding.fragmentEpisodeDislikes.setText(String.valueOf(episode.getDownvotes()));

            setEpisodeState(episode);

            if(episodeWithHosts.getHosts() != null && episodeWithHosts.getHosts().size() > 0) {
                EpisodeHostAdapter adapter = new EpisodeHostAdapter((MainActivity) requireActivity(), episodeWithHosts.getHosts());
                Objects.requireNonNull(mBinding.fragmentEpisodeGridview).setAdapter(adapter);
                mBinding.fragmentEpisodeHosts.setVisibility(View.VISIBLE);
                mBinding.fragmentEpisodeGridview.setVisibility(View.VISIBLE);
            } else {
                mBinding.fragmentEpisodeHosts.setVisibility(View.GONE);
                mBinding.fragmentEpisodeGridview.setVisibility(View.GONE);
            }

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(40));

            Glide.with(this).asBitmap()
                    .load(episode.getImage())
                    .transition(BitmapTransitionOptions.withCrossFade())
                    .apply(requestOptions)
                    .fallback(R.drawable.podcast_default_image)
                    .into(mBinding.fragmentEpisodeImage);
        });

        topicViewModel.getAllTopicsWithSubtopicsFromEpisode(id).observe(getViewLifecycleOwner(), topics -> {
            EpisodeTopicAdapter adapter = new EpisodeTopicAdapter(topics, (MainActivity) requireActivity());
            Objects.requireNonNull(mBinding.fragmentEpisodeRecyclerview).setAdapter(adapter);
            mBinding.fragmentEpisodeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

            if(topics == null || topics.size() == 0) {
                mBinding.fragmentEpisodeNoTopics.setVisibility(View.VISIBLE);
                mBinding.fragmentEpisodeNoTopicsBackground.setVisibility(View.VISIBLE);
                mBinding.fragmentEpisodeNoTopicsIll.setVisibility(View.VISIBLE);
                mBinding.fragmentEpisodeMotionlayout.getTransition(R.id.motionlayout_episode).setEnabled(false);
                mBinding.fragmentEpisodeRecyclerview.setVisibility(View.GONE);
            } else {
                mBinding.fragmentEpisodeNoTopics.setVisibility(View.GONE);
                mBinding.fragmentEpisodeNoTopicsBackground.setVisibility(View.GONE);
                mBinding.fragmentEpisodeNoTopicsIll.setVisibility(View.GONE);
                mBinding.fragmentEpisodeMotionlayout.getTransition(R.id.motionlayout_episode).setEnabled(true);
                mBinding.fragmentEpisodeRecyclerview.setVisibility(View.VISIBLE);
            }
        });

        mBinding.fragmentEpisodeFilter.setOnCheckedChangeListener(this);
        Toolbar toolbar = requireActivity().findViewById(R.id.activity_main_toolbar);
        toolbar.setBackgroundColor(requireActivity().getColor(R.color.primaryColor));
        return mBinding.getRoot();
    }

    private void setEpisodeState(Episode episode) {
        Drawable icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_open);
        if(episode.getVerified()) {
            mBinding.fragmentEpisodeStateText.setText(R.string.state_closed);
            icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_closed);
        } else if (!episode.getVerified() && episode.isClaimed()) {
            mBinding.fragmentEpisodeStateText.setText(R.string.state_unverified);
            icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_unverified);
        } else if (episode.isClaimed()) {
            mBinding.fragmentEpisodeStateText.setText(R.string.state_claimed);
            icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_claimed);
        } else {
            mBinding.fragmentEpisodeStateText.setText(R.string.state_open);
        }
        mBinding.fragmentEpisodeStateIcon.setBackground(icon);
    }

    @Override
    public void onCheckedChanged(ChipGroup group, int checkedId) {
        EpisodeTopicAdapter adapter = (EpisodeTopicAdapter) mBinding.fragmentEpisodeRecyclerview.getAdapter();
        List<TopicWithSubtopic> topics = Objects.requireNonNull(adapter).getTopics();

        if(checkedId == R.id.filter_episode_time) {
            Collections.sort(topics, (a,b) -> Integer.compare(a.getTopic().getStart(), b.getTopic().getStart()));
        } else {
            Collections.sort(topics, (a,b) -> {
                Collator germanCollator = Collator.getInstance(Locale.GERMAN);
                germanCollator.setStrength(Collator.PRIMARY);
                return germanCollator.compare(a.getTopic().getName(), b.getTopic().getName());
            });
        }
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}