package sprechstunde.community.themenschaedel.view.topic;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.list.TopicAdapter;
import sprechstunde.community.themenschaedel.adapter.podcast.PodcastCardAdapter;
import sprechstunde.community.themenschaedel.databinding.FragmentListBinding;
import sprechstunde.community.themenschaedel.model.Topic;
import sprechstunde.community.themenschaedel.model.ViewModel;
import sprechstunde.community.themenschaedel.view.CustomPopupWindow;

public class ListFragment extends Fragment implements View.OnClickListener {

    private FragmentListBinding mBinding;

    public ListFragment() {
        // Required empty public constructor
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

        ViewModel mViewModel = new ViewModelProvider(requireActivity()).get(ViewModel.class);
        mViewModel.getAllTopics().observe(getViewLifecycleOwner(), topics -> {
            Collections.sort(topics, (a, b) -> a.getName().compareTo(b.getName()));
            TopicAdapter adapter = new TopicAdapter(topics, getContext());
            Objects.requireNonNull(mBinding.fragmentListRecylerview).setAdapter(adapter);
            mBinding.fragmentListRecylerview.setLayoutManager(new LinearLayoutManager(getContext()));
        });

        mBinding.fragmentListDetails.setOnClickListener(this);

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if(v == mBinding.fragmentListDetails) {
            final BottomSheetDialogFilterFragment bottomSheetDialog = new BottomSheetDialogFilterFragment();
            bottomSheetDialog.show(getChildFragmentManager(), "OpenFilterBottomSheet");
        }
    }
}