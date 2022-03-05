package sprechstunde.community.themenschaedel.view.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.ProfileHostAdapter;
import sprechstunde.community.themenschaedel.databinding.FragmentProfileRandomBinding;
import sprechstunde.community.themenschaedel.view.CustomPopupWindow;
import sprechstunde.community.themenschaedel.viewmodel.HostViewModel;

public class ProfileRandomFragment extends Fragment {

    FragmentProfileRandomBinding mBinding;


    public ProfileRandomFragment() {
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
        mBinding = FragmentProfileRandomBinding.inflate(inflater, container, false);

        HostViewModel hostViewModel = new ViewModelProvider(requireActivity()).get(HostViewModel.class);

        hostViewModel.getAllHosts().observe(getViewLifecycleOwner(), hosts -> {
            ProfileHostAdapter adapter = new ProfileHostAdapter(hosts, (MainActivity) requireActivity());
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);

            Objects.requireNonNull(mBinding.fragmentProfileRandomHostList).setAdapter(adapter);
            mBinding.fragmentProfileRandomHostList.setLayoutManager(layoutManager);
        });

        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}