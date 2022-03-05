package sprechstunde.community.themenschaedel.view.profile;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.SessionManagement;
import sprechstunde.community.themenschaedel.UsedSharedPreferences;
import sprechstunde.community.themenschaedel.databinding.FragmentProfileSettingsBinding;
import sprechstunde.community.themenschaedel.view.CustomPopupWindow;
import sprechstunde.community.themenschaedel.viewmodel.SessionViewModel;

public class ProfileSettingsFragment extends Fragment {

    FragmentProfileSettingsBinding mBinding;

    public ProfileSettingsFragment() {
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
        mBinding = FragmentProfileSettingsBinding.inflate(inflater, container, false);
        Toolbar toolbar =  requireActivity().findViewById(R.id.activity_main_toolbar);
        toolbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.toolbar_gradient, requireActivity().getTheme()));
        UsedSharedPreferences.getInstance((MainActivity) requireActivity()).getUserRoleToSharedPreferences();

        SessionViewModel sessionViewModel = new ViewModelProvider(requireActivity()).get(SessionViewModel.class);
        sessionViewModel.getMyself().observe(getViewLifecycleOwner(), myself -> {

            Drawable icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_user);
            String role = getString(R.string.profile_role_user);

            if(myself == null) {
                mBinding.fragmentProfileSettingsFieldRoleIcon.setBackground(icon);
                mBinding.fragmentProfileSettingsFieldRole.setText(role);
                return;
            }

            SessionManagement.ROLE roleEnum = SessionManagement.getInstance().getCurrentRole();

            switch (roleEnum) {
                case FROID: {
                    icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_froid);
                    role = getString(R.string.profile_role_froid);
                } break;
                case MOD: {
                    icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_mod);
                    role = getString(R.string.profile_role_mod);
                } break;
                case ADMIN: {
                    icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_admin);
                    role = getString(R.string.profile_role_admin);
                } break;
                default:{
                    icon = ContextCompat.getDrawable(requireActivity(), R.drawable.ic_user);
                    role = getString(R.string.profile_role_user);
                }
            }

            mBinding.fragmentProfileSettingsFieldRoleIcon.setBackground(icon);
            mBinding.fragmentProfileSettingsFieldRole.setText(role);
            // mBinding.fragmentProfileSettingsEmail.setText(myself.getEmail());
            // mBinding.fragmentProfileSettingsName.setText(myself.getUsername());
        });

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}