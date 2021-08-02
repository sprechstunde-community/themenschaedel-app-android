package sprechstunde.community.themenschaedel.view.topic;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
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

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.PodcastRowAdapter;
import sprechstunde.community.themenschaedel.adapter.TopicAdapter;
import sprechstunde.community.themenschaedel.data.Podcast;
import sprechstunde.community.themenschaedel.data.Topic;
import sprechstunde.community.themenschaedel.databinding.FragmentListBinding;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastCardBinding;

public class ListFragment extends Fragment {

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
        topics.add(new Topic("Call of the Void", 0, 55,true));
        topics.add(new Topic("Der Mars", 0, 162,false));
        topics.add(new Topic("Eine Random Campingplatz-Geschichte", 0, 100,false));
        topics.add(new Topic("Erlebnisse im Verkehr", 0, 120,false));
        topics.add(new Topic("Erste Erinnerung", 0, 108,true));
        topics.add(new Topic("Omas Essen", 0, 120,false));

        TopicAdapter adapter = new TopicAdapter(topics, getContext());
        Objects.requireNonNull(mBinding.fragmentListRecylerview).setAdapter(adapter);
        mBinding.fragmentListRecylerview.setLayoutManager(new LinearLayoutManager(getContext()));
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
            showSortPopup(requireActivity());
        }

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
        NavController nvController = Objects.requireNonNull(navHostFragment).getNavController();
        return  NavigationUI.onNavDestinationSelected(item, nvController);
    }

    private void showSortPopup(final Activity context)
    {
        ConstraintLayout viewGroup = context.findViewById(R.id.dialog_info_sugg_topic_layout);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.dialog_info_sugg_topic_types, viewGroup);

        // Creating the PopupWindow
        PopupWindow popupWindow = new PopupWindow(context);
        popupWindow.setContentView(layout);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);

        popupWindow.setBackgroundDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_card, context.getTheme()));
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        dimBehind(popupWindow);
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container = popupWindow.getContentView().getRootView();
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}