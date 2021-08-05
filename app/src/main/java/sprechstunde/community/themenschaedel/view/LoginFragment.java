package sprechstunde.community.themenschaedel.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.databinding.FragmentLoginBinding;
import sprechstunde.community.themenschaedel.databinding.FragmentProfileBinding;
import sprechstunde.community.themenschaedel.view.podcast.PodcastCardFragment;

public class LoginFragment extends Fragment implements View.OnClickListener {

    FragmentLoginBinding mBinding;

    public LoginFragment() {
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
        inflater.inflate(R.menu.menu_settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentLoginBinding.inflate(inflater, container, false);
        mBinding.fragmentLoginButton.setOnClickListener(this);
        mBinding.fragmentLoginRegisterLink.setOnClickListener(this);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if(v == mBinding.fragmentLoginButton) {
            NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
            NavController nvController = Objects.requireNonNull(navHostFragment).getNavController();
            nvController.navigate(R.id.nav_podcast);
        } else if(v == mBinding.fragmentLoginRegisterLink) {
            NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
            NavController nvController = Objects.requireNonNull(navHostFragment).getNavController();
            nvController.navigate(R.id.nav_register);
        }
    }
}