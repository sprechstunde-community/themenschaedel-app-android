package sprechstunde.community.themenschaedel.view.podcast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.podcast.PodcastRowAdapter;
import sprechstunde.community.themenschaedel.listener.ParentChildFragmentListener;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastRowBinding;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;

public class PodcastRowFragment extends Fragment implements ParentChildFragmentListener{

    FragmentPodcastRowBinding mBinding;
    private SharedPreferences mSharedPref;

    public PodcastRowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentPodcastRowBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        EpisodeViewModel viewModel = new ViewModelProvider(requireActivity()).get(EpisodeViewModel.class);
        viewModel.getAllEpisodes().observe(getViewLifecycleOwner(), episodes -> {
            Collections.sort(episodes, (a, b) -> Integer.compare(b.getNumber(), a.getNumber()));
            PodcastRowAdapter adapter = new PodcastRowAdapter(episodes);
            Objects.requireNonNull(mBinding.fragmentRowRecyclerview).setAdapter(adapter);
            mBinding.fragmentRowRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

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
        int defaultValue = ParentChildFragmentListener.SORTED_BY.DATE_DOWN.ordinal();
        int displayType = mSharedPref.getInt(getString(R.string.saved_filter_type_podcast), defaultValue);
        onSortChanged(ParentChildFragmentListener.SORTED_BY.values()[displayType]);
    }

    @Override
    public void onSortChanged(ParentChildFragmentListener.SORTED_BY sortedBy) {
        PodcastRowAdapter adapter = (PodcastRowAdapter) mBinding.fragmentRowRecyclerview.getAdapter();
        List<Episode> episodes = Objects.requireNonNull(adapter).getEpisodes();

        switch (sortedBy) {
            case DATE_UP:
            default: {
                Collections.sort(episodes, (a,b) -> Integer.compare(a.getNumber(), b.getNumber()));
            } break;
            case DATE_DOWN: {
                Collections.sort(episodes, (a,b) -> Integer.compare(b.getNumber(), a.getNumber()));
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
    public boolean onScrollBackToTop() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mBinding.fragmentRowRecyclerview.getLayoutManager();
        if(Objects.requireNonNull(layoutManager).findFirstCompletelyVisibleItemPosition()!=0){
            mBinding.fragmentRowRecyclerview.smoothScrollToPosition(0);
        }

        return Objects.requireNonNull(layoutManager).findFirstCompletelyVisibleItemPosition()==0;
    }

    @Override
    public void onSearch(List<Episode> episodeList) {
        ((PodcastRowAdapter) Objects.requireNonNull(mBinding.fragmentRowRecyclerview.getAdapter())).setEpisodes(episodeList);
        mBinding.fragmentRowRecyclerview.getAdapter().notifyDataSetChanged();
    }
}