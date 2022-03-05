package sprechstunde.community.themenschaedel.view.podcast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import sprechstunde.community.themenschaedel.Enums;
import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.UsedSharedPreferences;
import sprechstunde.community.themenschaedel.adapter.podcast.PodcastCardAdapter;
import sprechstunde.community.themenschaedel.api.ApiClient;
import sprechstunde.community.themenschaedel.listener.ParentChildFragmentListener;
import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastCardBinding;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;
import sprechstunde.community.themenschaedel.viewmodel.HostViewModel;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;

public class PodcastCardFragment extends Fragment implements ParentChildFragmentListener, SwipeRefreshLayout.OnRefreshListener  {

    private FragmentPodcastCardBinding mBinding;
    private SharedPreferences mSharedPref;

    public PodcastCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentPodcastCardBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        mBinding.fragmentCardSwipeUp.setOnRefreshListener(this);

        EpisodeViewModel viewModel = new ViewModelProvider(requireActivity()).get(EpisodeViewModel.class);
        viewModel.getAllEpisodes().observe(getViewLifecycleOwner(), episodes -> {
            Collections.sort(episodes, (a,b) -> Integer.compare(b.getEpisodeNumber(), a.getEpisodeNumber()));
            PodcastCardAdapter adapter = new PodcastCardAdapter(getContext(), episodes, mBinding.fragmentPodcastEmptyIll, mBinding.fragmentPodcastEmptyText);
            Objects.requireNonNull(mBinding.fragmentCardGridview).setAdapter(adapter);

            mSharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
            getFilterTypeFromSharedPreferences();

        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    private void getFilterTypeFromSharedPreferences() {
        int defaultValue = Enums.SORTED_BY.DATE_DOWN.ordinal();
        int displayType = mSharedPref.getInt(getString(R.string.saved_filter_type_podcast), defaultValue);
        onSortChanged(Enums.SORTED_BY.values()[displayType]);
    }

    @Override
    public boolean onScrollBackToTop() {
        if(mBinding.fragmentCardGridview.getFirstVisiblePosition() != 0){
            mBinding.fragmentCardGridview.smoothScrollToPosition(0);
        }

        return mBinding.fragmentCardGridview.getFirstVisiblePosition() == 0;
    }

    @Override
    public void onSortChanged(Enums.SORTED_BY sortedBy) {
        PodcastCardAdapter adapter = (PodcastCardAdapter) mBinding.fragmentCardGridview.getAdapter();
        List<Episode> episodes = adapter.getEpisodes();

        switch (sortedBy) {
            case DATE_UP:
            default: {
                Collections.sort(episodes, (a,b) -> Integer.compare(a.getEpisodeNumber(), b.getEpisodeNumber()));
            } break;
            case DATE_DOWN: {
                Collections.sort(episodes, (a,b) -> Integer.compare(b.getEpisodeNumber(), a.getEpisodeNumber()));
            } break;
            case TITLE_UP: {
                Collections.sort(episodes, (a,b) -> {
                    Collator germanCollator = Collator.getInstance(Locale.GERMAN);
                    germanCollator.setStrength(Collator.PRIMARY);
                    return germanCollator.compare(a.getTitle(), b.getTitle());
                });
            } break;
            case TITLE_DOWN: {
                Collections.sort(episodes, (a,b) -> {
                    Collator germanCollator = Collator.getInstance(Locale.GERMAN);
                    germanCollator.setStrength(Collator.PRIMARY);
                    return germanCollator.compare(b.getTitle(), a.getTitle());
                });
            } break;
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSearch(List<Episode> episodeList) {
        ((PodcastCardAdapter) mBinding.fragmentCardGridview.getAdapter()).setEpisodes(episodeList);
        ((PodcastCardAdapter) mBinding.fragmentCardGridview.getAdapter()).notifyDataSetChanged();

    }

    @Override
    public void onFilterSelected(boolean showState) {
        ((PodcastCardAdapter) mBinding.fragmentCardGridview.getAdapter()).setShowState(showState);
        ((PodcastCardAdapter) mBinding.fragmentCardGridview.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onMenuRefresh() {
        mBinding.fragmentCardSwipeUp.setRefreshing(true);
        refresh();
    }

    private void refresh() {
        new Thread(() -> {
            try {
                EpisodeViewModel episodeViewModel = new ViewModelProvider(requireActivity()).get(EpisodeViewModel.class);
                TopicViewModel topicViewModel = new ViewModelProvider(requireActivity()).get(TopicViewModel.class);
                HostViewModel hostViewModel = new ViewModelProvider(requireActivity()).get(HostViewModel.class);
                MainActivity mainActivity = (MainActivity) requireActivity();

                UsedSharedPreferences.getInstance(mainActivity).saveTopicCountToSharedPreferences(0);
                UsedSharedPreferences.getInstance(mainActivity).saveEpisodeCountToSharedPreferences(0);
                ApiClient.getInstance(mainActivity).saveEpisodesToDB(episodeViewModel, topicViewModel, hostViewModel, true, mBinding.fragmentCardSwipeUp);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}