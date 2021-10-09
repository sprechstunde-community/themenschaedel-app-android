package sprechstunde.community.themenschaedel.view.podcast;

import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.Locale;
import java.util.Objects;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.podcast.PodcastCardAdapter;
import sprechstunde.community.themenschaedel.listener.ParentChildFragmentListener;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastCardBinding;
import sprechstunde.community.themenschaedel.model.ViewModel;

public class PodcastCardFragment extends Fragment implements ParentChildFragmentListener {

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

        ViewModel mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        mViewModel.getAllEpisodes().observe(getViewLifecycleOwner(), episodes -> {
            Collections.sort(episodes, (a,b) -> Integer.compare(b.getNumber(), a.getNumber()));
            PodcastCardAdapter adapter = new PodcastCardAdapter(getContext(), episodes);
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
        int defaultValue = ParentChildFragmentListener.SORTED_BY.DATE_DOWN.ordinal();
        int displayType = mSharedPref.getInt(getString(R.string.saved_filter_type_podcast), defaultValue);
        onSortChanged(ParentChildFragmentListener.SORTED_BY.values()[displayType]);
    }

    @Override
    public void onSortChanged(SORTED_BY sortedBy) {
        PodcastCardAdapter adapter = (PodcastCardAdapter) mBinding.fragmentCardGridview.getAdapter();
        List<Episode> episodes = adapter.getEpisodes();

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
        ((PodcastCardAdapter) mBinding.fragmentCardGridview.getAdapter()).setEpisodes(episodeList);
        ((PodcastCardAdapter) mBinding.fragmentCardGridview.getAdapter()).notifyDataSetChanged();

    }
}