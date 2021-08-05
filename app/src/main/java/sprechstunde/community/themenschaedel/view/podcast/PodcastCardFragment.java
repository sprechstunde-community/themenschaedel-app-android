package sprechstunde.community.themenschaedel.view.podcast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import sprechstunde.community.themenschaedel.adapter.podcast.PodcastCardAdapter;
import sprechstunde.community.themenschaedel.listener.ParentChildFragmentListener;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastCardBinding;
import sprechstunde.community.themenschaedel.model.ViewModel;

public class PodcastCardFragment extends Fragment implements ParentChildFragmentListener {

    private FragmentPodcastCardBinding mBinding;
    private ViewModel mViewModel;

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

        mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel .class);
        mViewModel.getAllEpisodes().observe(getViewLifecycleOwner(), episodes -> {
            Collections.sort(episodes, (a,b) -> Integer.compare(b.getNumber(), a.getNumber()));
            PodcastCardAdapter adapter = new PodcastCardAdapter(getContext(), episodes);
            Objects.requireNonNull(mBinding.fragmentCardGridview).setAdapter(adapter);
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onSortChanged(SORTED_BY sortedBy) {
        PodcastCardAdapter adapter = (PodcastCardAdapter) mBinding.fragmentCardGridview.getAdapter();
        List<Episode> episodes = adapter.getEpisodes();

        switch (sortedBy) {
            case DATE:
            default: {
                Collections.sort(episodes, (a,b) -> Integer.compare(b.getNumber(), a.getNumber()));
            } break;

            case TITLE: {
                Collections.sort(episodes, (a, b) -> a.getTitle().compareTo(b.getTitle()));
            } break;

            case USER: {

            } break;

            case STATE: {

            } break;
        }

        adapter.notifyDataSetChanged();
    }
}