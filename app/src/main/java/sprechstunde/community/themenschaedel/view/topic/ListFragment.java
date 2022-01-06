package sprechstunde.community.themenschaedel.view.topic;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
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

import com.google.android.material.chip.Chip;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.list.TopicAdapter;
import sprechstunde.community.themenschaedel.databinding.FragmentListBinding;
import sprechstunde.community.themenschaedel.listener.ParentChildFragmentListener;
import sprechstunde.community.themenschaedel.model.Topic;
import sprechstunde.community.themenschaedel.model.TopicWithSubtopic;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;
import sprechstunde.community.themenschaedel.view.CustomPopupWindow;

public class  ListFragment extends Fragment implements View.OnClickListener, BottomSheetDialogFilterFragment.ProcessFilter {

    private FragmentListBinding mBinding;
    private SharedPreferences mSharedPref;
    private int mPreSelectedChipId;
    private TopicAdapter mAdapter;
    public ListFragment() {
        // Required empty public constructor
    }

    public TopicAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentListBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        TopicViewModel mViewModel = new ViewModelProvider(requireActivity()).get(TopicViewModel.class);
        mViewModel.getAllTopicsWithSubtopics().observe(getViewLifecycleOwner(), topics -> {
            Collections.sort(topics, (a, b) -> a.getTopic().getName().compareTo(b.getTopic().getName()));
            mAdapter = new TopicAdapter(topics, (MainActivity) requireActivity());
            Objects.requireNonNull(mBinding.fragmentListRecylerview).setAdapter(mAdapter);
            mBinding.fragmentListRecylerview.setLayoutManager(new LinearLayoutManager(getContext()));

            mSharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
            getFilterTypeFromSharedPreferences();
        });

        mBinding.fragmentListDetails.setOnClickListener(this);
        mBinding.filterName.setOnClickListener(this);
        mBinding.filterNumber.setOnClickListener(this);
        mPreSelectedChipId = mBinding.filterNumber.getId();

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_search_info, menu);
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

    private void fromASCToDESC(Chip chip, boolean wasAlreadySelected, ParentChildFragmentListener.SORTED_BY up, ParentChildFragmentListener.SORTED_BY down) {
        String tag = (String) chip.getTag();
        Drawable upIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_up);
        Drawable downIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_down);

        if (tag.equals("ASC") && wasAlreadySelected || (tag.equals("DESC") && !wasAlreadySelected)) { // if already selected -> make ASC to DESC or if was not selected and is DESC
            sortList(down);
            chip.setTag("DESC");
            chip.setChipIcon(downIcon);
            saveFilterTypeToSharedPreferences(down);
        } else if (tag.equals("DESC") || tag.equals("ASC"))  { // if already selected -> make DESC to ASC or if was not selected and is ASC
            sortList(up);
            chip.setTag("ASC");
            chip.setChipIcon(upIcon);
            saveFilterTypeToSharedPreferences(up);
        }
    }

    private void saveFilterTypeToSharedPreferences(ParentChildFragmentListener.SORTED_BY filter) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(getString(R.string.saved_filter_type_list), filter.ordinal());
        editor.apply();
    }

    private void getFilterTypeFromSharedPreferences() {
        int defaultValue = ParentChildFragmentListener.SORTED_BY.NUMBER_DOWN.ordinal();
        int displayType = mSharedPref.getInt(getString(R.string.saved_filter_type_list), defaultValue);
        sortList(ParentChildFragmentListener.SORTED_BY.values()[displayType]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if(v == mBinding.fragmentListDetails) {
            final BottomSheetDialogFilterFragment bottomSheetDialog = new BottomSheetDialogFilterFragment(this, mAdapter);
            bottomSheetDialog.show(getChildFragmentManager(), "OpenFilterBottomSheet");
        } else if (mBinding.filterNumber == v) {
            fromASCToDESC(mBinding.filterNumber, v.getId() == mPreSelectedChipId, ParentChildFragmentListener.SORTED_BY.NUMBER_UP, ParentChildFragmentListener.SORTED_BY.NUMBER_DOWN);
            mPreSelectedChipId = mBinding.filterNumber.getId();
        } else if (mBinding.filterName == v) {
            fromASCToDESC(mBinding.filterName, v.getId() == mPreSelectedChipId, ParentChildFragmentListener.SORTED_BY.TITLE_UP, ParentChildFragmentListener.SORTED_BY.TITLE_DOWN);
            mPreSelectedChipId = mBinding.filterName.getId();
        }
    }

    private void sortList(ParentChildFragmentListener.SORTED_BY sortedBy) {
        TopicAdapter adapter = (TopicAdapter) mBinding.fragmentListRecylerview.getAdapter();
        List<TopicWithSubtopic> topics = Objects.requireNonNull(adapter).getTopics();

        switch (sortedBy) {
            case NUMBER_UP:
            default: {
                Collections.sort(topics, (a,b) -> Integer.compare(a.getTopic().getEpisode(), b.getTopic().getEpisode()));
            } break;
            case NUMBER_DOWN: {
                Collections.sort(topics, (a,b) -> Integer.compare(b.getTopic().getEpisode(), a.getTopic().getEpisode()));
            } break;
            case TITLE_UP: {
                Collections.sort(topics, (a,b) -> {
                    Collator germanCollator = Collator.getInstance(Locale.GERMAN);
                    germanCollator.setStrength(Collator.PRIMARY);
                    return germanCollator.compare(a.getTopic().getName(), b.getTopic().getName());
                });
            } break;
            case TITLE_DOWN: {
                Collections.sort(topics, (a,b) -> {
                    Collator germanCollator = Collator.getInstance(Locale.GERMAN);
                    germanCollator.setStrength(Collator.PRIMARY);
                    return germanCollator.compare(b.getTopic().getName(), a.getTopic().getName());
                });
            } break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProcessFilter(boolean showDetails) {
        mAdapter.setShowDetails(showDetails);
        mAdapter.notifyDataSetChanged();
    }
}