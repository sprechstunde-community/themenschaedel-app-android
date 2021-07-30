package sprechstunde.community.themenschaedel.view.podcast;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastBinding;

public class PodcastFragment extends Fragment implements View.OnClickListener {

    private FragmentPodcastBinding mBinding;
    private Display mCurrentDisplay;

    private enum Display {
        CARDS,
        CELLS,
        ROWS
    }

    public PodcastFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentPodcastBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        getDisplayTypeFromSharedPreferences();
        mBinding.fragmentPodcastDisplay.setOnClickListener(this);

        changeDisplayFragment(true);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (mBinding.fragmentPodcastDisplay == v && getActivity() != null) {
            changeDisplayFragment(false);
        }
    }

    private void saveDisplayTypeToSharedPreferences() {
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getString(R.string.saved_display_type), mCurrentDisplay.ordinal());
        editor.apply();
    }

    private void getDisplayTypeFromSharedPreferences() {
        SharedPreferences sharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
        int defaultValue = Display.CARDS.ordinal();
        int displayType = sharedPref.getInt(getString(R.string.saved_display_type), defaultValue);
        mCurrentDisplay = Display.values()[displayType];
    }

    private void changeDisplayFragment(boolean startedFragment) {
        Drawable card = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_view_cards);
        Drawable cell = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_view_cells);
        Drawable row = AppCompatResources.getDrawable(requireActivity(), R.drawable.ic_view_rows);

        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setReorderingAllowed(true);

        if (mCurrentDisplay == Display.CARDS) {
            if(startedFragment)
                changeToCards(card, transaction);
            else
                changeToCells(cell, transaction);
        } else if (mCurrentDisplay == Display.CELLS) {
            if(startedFragment)
                changeToCells(cell, transaction);
            else
                changeToRows(row, transaction);
        } else {
            if(startedFragment)
                changeToRows(row, transaction);
            else
                changeToCards(card, transaction);
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
}