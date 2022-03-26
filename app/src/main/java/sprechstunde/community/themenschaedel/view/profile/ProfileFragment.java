package sprechstunde.community.themenschaedel.view.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.UsedSharedPreferences;
import sprechstunde.community.themenschaedel.adapter.ProfileTabAdapter;
import sprechstunde.community.themenschaedel.databinding.FragmentProfileBinding;
import sprechstunde.community.themenschaedel.view.CustomPopupWindow;
import sprechstunde.community.themenschaedel.viewmodel.SessionViewModel;

public class ProfileFragment extends Fragment {

    FragmentProfileBinding mBinding;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_info_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.menu_info) {
            CustomPopupWindow popupWindow = new CustomPopupWindow();
            popupWindow.showSortPopup(R.id.dialog_info_profile_roles_layout, R.layout.dialog_info_profile_roles, requireActivity());
        }

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
        NavController nvController = Objects.requireNonNull(navHostFragment).getNavController();
        return  NavigationUI.onNavDestinationSelected(item, nvController);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        Toolbar toolbar = requireActivity().findViewById(R.id.activity_main_toolbar);
        toolbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.toolbar_gradient, requireActivity().getTheme()));
        UsedSharedPreferences.getInstance((MainActivity) requireActivity()).getUserRoleToSharedPreferences();


        ProfileTabAdapter profileTabAdapter = new ProfileTabAdapter(this);

        // Set up the ViewPager with the sections adapter.
        ViewPager2 viewPager = mBinding.fragmentProfileViewpager;
        viewPager.setAdapter(profileTabAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                int numPages = 3;
                float motionProgress = (position + positionOffset) / (numPages - 1);
                mBinding.fragmentProfileMotionlayout.setProgress(motionProgress);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if(position == 0) {
                    mBinding.fragmentProfileEmail.setVisibility(View.VISIBLE);
                    mBinding.fragmentProfileName.setVisibility(View.VISIBLE);
                    mBinding.fragmentProfileCircle.setVisibility(View.VISIBLE);
                    mBinding.fragmentProfileImageMask.setVisibility(View.VISIBLE);
                } else {
                    mBinding.fragmentProfileEmail.setVisibility(View.GONE);
                    mBinding.fragmentProfileName.setVisibility(View.GONE);
                    mBinding.fragmentProfileCircle.setVisibility(View.GONE);
                    mBinding.fragmentProfileImageMask.setVisibility(View.GONE);
                }
            }
        });

        setHasOptionsMenu(true);
        SessionViewModel sessionViewModel = new ViewModelProvider(requireActivity()).get(SessionViewModel.class);
        sessionViewModel.getMyself().observe(getViewLifecycleOwner(), myself -> {
             mBinding.fragmentProfileEmail.setText(myself.getEmail());
             mBinding.fragmentProfileName.setText(myself.getUsername());
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}