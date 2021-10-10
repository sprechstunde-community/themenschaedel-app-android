package sprechstunde.community.themenschaedel.view.podcast;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastBinding;
import sprechstunde.community.themenschaedel.listener.ParentChildFragmentListener;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;

public class PodcastFragment extends Fragment implements View.OnClickListener, SearchView.OnQueryTextListener {

    private FragmentPodcastBinding mBinding;
    private Display mCurrentDisplay;
    private SharedPreferences mSharedPref;
    private int mPreSelectedChipId;

    private enum Display {
        CARDS,
        CELLS,
        ROWS
    }

    public PodcastFragment() {
        // Required empty public constructor
    }

    private void notifyFragmentsForSort(ParentChildFragmentListener.SORTED_BY sortedBy) {
        List<Fragment> fragments = getParentFragmentManager().getFragments();
        for (Fragment f : fragments) {
            if(f != this)
                ((ParentChildFragmentListener) f).onSortChanged(sortedBy);
        }
    }

    private void notifyFragmentsForSearch(List<Episode> episodes) {
        List<Fragment> fragments = getParentFragmentManager().getFragments();
        for (Fragment f : fragments) {
            if(f != this)
                ((ParentChildFragmentListener) f).onSearch(episodes);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search_settings, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentPodcastBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        mSharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        getDisplayTypeFromSharedPreferences();
        mBinding.fragmentPodcastDisplay.setOnClickListener(this);

        mBinding.filterDate.setOnClickListener(this);
        mBinding.filterTitle.setOnClickListener(this);
        mPreSelectedChipId = mBinding.filterDate.getId();

        changeDisplayFragment(true);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (mBinding.fragmentPodcastDisplay == v && getActivity() != null) {
            changeDisplayFragment(false);
        } else if (mBinding.filterDate == v) {
            fromASCToDESC(mBinding.filterDate, v.getId() == mPreSelectedChipId, ParentChildFragmentListener.SORTED_BY.DATE_UP, ParentChildFragmentListener.SORTED_BY.DATE_DOWN);
            mPreSelectedChipId = mBinding.filterDate.getId();
        } else if (mBinding.filterTitle == v) {
            fromASCToDESC(mBinding.filterTitle, v.getId() == mPreSelectedChipId, ParentChildFragmentListener.SORTED_BY.TITLE_UP, ParentChildFragmentListener.SORTED_BY.TITLE_DOWN);
            mPreSelectedChipId = mBinding.filterTitle.getId();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        onSearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        onSearch(query);
        return false;
    }

    private void onSearch(String query) {
        EpisodeViewModel viewModel = new ViewModelProvider(this).get(EpisodeViewModel.class);
        if(!query.equals("")) {
            viewModel.searchForEpisodes(query).observe(this, this::notifyFragmentsForSearch);
        }
    }

    private void fromASCToDESC(Chip chip, boolean wasAlreadySelected, ParentChildFragmentListener.SORTED_BY up, ParentChildFragmentListener.SORTED_BY down) {
        String tag = (String) chip.getTag();
        Drawable upIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_up);
        Drawable downIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_down);

        if (tag.equals("ASC") && wasAlreadySelected || (tag.equals("DESC") && !wasAlreadySelected)) { // if already selected -> make ASC to DESC or if was not selected and is DESC
            notifyFragmentsForSort(down);
            chip.setTag("DESC");
            chip.setChipIcon(downIcon);
            saveFilterTypeToSharedPreferences(down);
        } else if (tag.equals("DESC") || tag.equals("ASC"))  { // if already selected -> make DESC to ASC or if was not selected and is ASC
            notifyFragmentsForSort(up);
            chip.setTag("ASC");
            chip.setChipIcon(upIcon);
            saveFilterTypeToSharedPreferences(up);
        }
    }

    private void saveFilterTypeToSharedPreferences(ParentChildFragmentListener.SORTED_BY filter) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(getString(R.string.saved_filter_type_podcast), filter.ordinal());
        editor.apply();
    }

    private void saveDisplayTypeToSharedPreferences() {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(getString(R.string.saved_display_type), mCurrentDisplay.ordinal());
        editor.apply();
    }

    private void getDisplayTypeFromSharedPreferences() {
        int defaultValue = Display.CARDS.ordinal();
        int displayType = mSharedPref.getInt(getString(R.string.saved_display_type), defaultValue);
        mCurrentDisplay = Display.values()[displayType];
    }

    private void changeDisplayFragment(boolean startedFragment) {
        Drawable card = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_view_cards);
        Drawable cell = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_view_cells);
        Drawable row = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_view_rows);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        if (mCurrentDisplay == Display.CARDS && startedFragment || mCurrentDisplay == Display.ROWS && !startedFragment) {
            changeToCards(card, transaction);
        } else if (mCurrentDisplay == Display.CELLS && startedFragment || mCurrentDisplay == Display.CARDS) {
            changeToCells(cell, transaction);
        } else if (mCurrentDisplay == Display.ROWS || mCurrentDisplay == Display.CELLS) {
            changeToRows(row, transaction);
        }

        saveDisplayTypeToSharedPreferences();
        transaction.commit();
    }

    private void changeToCards(Drawable card, FragmentTransaction transaction) {
        mCurrentDisplay = Display.CARDS;
        mBinding.fragmentPodcastDisplay.setBackground(card);
        transaction.replace(R.id.fragment_podcast_container, PodcastCardFragment.class, null);
    }

    private void changeToCells(Drawable cell, FragmentTransaction transaction) {
        mCurrentDisplay = Display.CELLS;
        mBinding.fragmentPodcastDisplay.setBackground(cell);
        transaction.replace(R.id.fragment_podcast_container, PodcastCellFragment.class, null);
    }

    private void changeToRows(Drawable row, FragmentTransaction transaction) {
        mCurrentDisplay = Display.ROWS;
        mBinding.fragmentPodcastDisplay.setBackground(row);
        transaction.replace(R.id.fragment_podcast_container, PodcastRowFragment.class, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}