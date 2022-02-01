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
import sprechstunde.community.themenschaedel.adapter.podcast.PodcastCellAdapter;
import sprechstunde.community.themenschaedel.listener.ParentChildFragmentListener;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastCellBinding;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;

public class PodcastCellFragment extends Fragment implements ParentChildFragmentListener{

    private FragmentPodcastCellBinding mBinding;
    private SharedPreferences mSharedPref;

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

        EpisodeViewModel viewModel = new ViewModelProvider(requireActivity()).get(EpisodeViewModel.class);
        viewModel.getAllEpisodes().observe(getViewLifecycleOwner(), episodes -> {
            Collections.sort(episodes, (a, b) -> Integer.compare(b.getNumber(), a.getNumber()));
            PodcastCellAdapter adapter = new PodcastCellAdapter(getContext(), episodes);
            Objects.requireNonNull(mBinding.fragmentCellRecyclerview).setAdapter(adapter);
            mBinding.fragmentCellRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

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
        int defaultValue = SORTED_BY.DATE_DOWN.ordinal();
        int displayType = mSharedPref.getInt(getString(R.string.saved_filter_type_podcast), defaultValue);
        onSortChanged(SORTED_BY.values()[displayType]);
    }

    @Override
    public void onSortChanged(SORTED_BY sortedBy) {
        PodcastCellAdapter adapter = (PodcastCellAdapter) mBinding.fragmentCellRecyclerview.getAdapter();
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
    public void onSearch(List<Episode> episodeList) {
       ((PodcastCellAdapter) Objects.requireNonNull(mBinding.fragmentCellRecyclerview.getAdapter())).setEpisodes(episodeList);
        mBinding.fragmentCellRecyclerview.getAdapter().notifyDataSetChanged();

    }

    @Override
    public boolean onScrollBackToTop() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mBinding.fragmentCellRecyclerview.getLayoutManager();
        if(Objects.requireNonNull(layoutManager).findFirstCompletelyVisibleItemPosition()!=0){
            mBinding.fragmentCellRecyclerview.smoothScrollToPosition(0);
        }

        return Objects.requireNonNull(layoutManager).findFirstCompletelyVisibleItemPosition()==0;
    }
}