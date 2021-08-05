package sprechstunde.community.themenschaedel.view.podcast;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import sprechstunde.community.themenschaedel.adapter.podcast.PodcastCardAdapter;
import sprechstunde.community.themenschaedel.adapter.podcast.PodcastCellAdapter;
import sprechstunde.community.themenschaedel.listener.ParentChildFragmentListener;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastCellBinding;
import sprechstunde.community.themenschaedel.model.ViewModel;

public class PodcastCellFragment extends Fragment implements ParentChildFragmentListener{

    private FragmentPodcastCellBinding mBinding;

    public PodcastCellFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentPodcastCellBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        ViewModel viewModel = new ViewModelProvider(requireActivity()).get(ViewModel .class);
        viewModel.getAllEpisodes().observe(getViewLifecycleOwner(), episodes -> {
            Collections.sort(episodes, (a, b) -> Integer.compare(b.getNumber(), a.getNumber()));
            PodcastCellAdapter adapter = new PodcastCellAdapter(getContext(), episodes);
            Objects.requireNonNull(mBinding.fragmentCellRecyclerview).setAdapter(adapter);
            mBinding.fragmentCellRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onSortChanged(ParentChildFragmentListener.SORTED_BY sortedBy) {
        PodcastCellAdapter adapter = (PodcastCellAdapter) mBinding.fragmentCellRecyclerview.getAdapter();
        List<Episode> episodes = adapter.getEpisodes();

        switch (sortedBy) {
            case DATE:
            default: {
                Collections.sort(episodes, (a, b) -> Integer.compare(b.getNumber(), a.getNumber()));
            }
            break;

            case TITLE: {
                Collections.sort(episodes, (a, b) -> a.getTitle().compareTo(b.getTitle()));
            }
            break;

            case USER: {

            }
            break;

            case STATE: {

            }
            break;
        }
        adapter.notifyDataSetChanged();

    }
}