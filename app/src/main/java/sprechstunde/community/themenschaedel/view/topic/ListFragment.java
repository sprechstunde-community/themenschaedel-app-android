package sprechstunde.community.themenschaedel.view.topic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.TopicAdapter;
import sprechstunde.community.themenschaedel.databinding.FragmentListBinding;
import sprechstunde.community.themenschaedel.model.Topic;
import sprechstunde.community.themenschaedel.view.CustomPopupWindow;

public class ListFragment extends Fragment implements View.OnClickListener {

    FragmentListBinding mBinding;

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

        List<Topic> topics = new LinkedList<>();
        topics.add(new Topic("Call of the Void", 0, 0, false,55));
        topics.add(new Topic("Der Mars", 0, 1,false, 162));
        topics.add(new Topic("Eine Random Campingplatz-Geschichte", 0, 1,true, 100));
        topics.add(new Topic("Erlebnisse im Verkehr", 0, 0,false, 120));
        topics.add(new Topic("Erste Erinnerung", 0, 1,true, 108));
        topics.add(new Topic("Omas Essen", 0, 1,false,120));

        TopicAdapter adapter = new TopicAdapter(topics, getContext());
        Objects.requireNonNull(mBinding.fragmentListRecylerview).setAdapter(adapter);
        mBinding.fragmentListRecylerview.setLayoutManager(new LinearLayoutManager(getContext()));

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
            popupWindow.showSortPopup(R.id.dialog_info_sugg_topic_layout, R.layout.dialog_info_sugg_topic_types, requireActivity());
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