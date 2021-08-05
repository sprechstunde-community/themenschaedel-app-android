package sprechstunde.community.themenschaedel.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.episodeList.EpisodeHostAdapter;
import sprechstunde.community.themenschaedel.adapter.episodeList.EpisodeTopicAdapter;
import sprechstunde.community.themenschaedel.adapter.list.TopicAdapter;
import sprechstunde.community.themenschaedel.adapter.podcast.PodcastCardAdapter;
import sprechstunde.community.themenschaedel.databinding.FragmentEpisodeBinding;
import sprechstunde.community.themenschaedel.model.Host;
import sprechstunde.community.themenschaedel.model.Topic;
import sprechstunde.community.themenschaedel.model.ViewModel;

public class EpisodeFragment extends Fragment {

    FragmentEpisodeBinding mBinding;

    public EpisodeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentEpisodeBinding.inflate(inflater, container, false);
        int id = EpisodeFragmentArgs.fromBundle(getArguments()).getEpisodeId();

        ViewModel mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        mViewModel.getAllTopicsFromEpisode(id).observe(getViewLifecycleOwner(), topics -> {
            EpisodeTopicAdapter adapter = new EpisodeTopicAdapter(topics, getContext());
            Objects.requireNonNull(mBinding.fragmentEpisodeRecyclerview).setAdapter(adapter);
            mBinding.fragmentEpisodeRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        mViewModel.getEpisode(id).observe(getViewLifecycleOwner(), episode -> {
            mBinding.fragmentEpisodeTitle.setText(episode.getTitle());
            String number =  getString(R.string.list_item_topic_number) + " " + episode.getNumber();
            mBinding.fragmentEpisodeNumber.setText(number);
            mBinding.fragmentEpisodeDate.setText(episode.getDate());
            mBinding.fragmentEpisodeLength.setText(episode.getDuration());

            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(40));

            Glide.with(this)
                    .load(episode.getImage())
                    .apply(requestOptions)
                    .into(mBinding.fragmentEpisodeImage);
        });

        List<Host> hosts = new ArrayList<>();
        hosts.add(new Host("Flo"));
        hosts.add(new Host("Olli"));
        hosts.add(new Host("Paul"));

        EpisodeHostAdapter adapterHost = new EpisodeHostAdapter(getContext(), hosts);
        Objects.requireNonNull(mBinding.fragmentEpisodeGridview).setAdapter(adapterHost);
        return mBinding.getRoot();
    }
}