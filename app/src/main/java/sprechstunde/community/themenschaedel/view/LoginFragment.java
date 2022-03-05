package sprechstunde.community.themenschaedel.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.api.ApiClient;
import sprechstunde.community.themenschaedel.databinding.FragmentLoginBinding;
import sprechstunde.community.themenschaedel.viewmodel.SessionViewModel;
import sprechstunde.community.themenschaedel.viewmodel.UserViewModel;

public class LoginFragment extends Fragment implements View.OnClickListener {

    private FragmentLoginBinding mBinding;
    private String mUsername;
    private String mPassword;

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
        mBinding.fragmentLoginUsernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mUsername = editable.toString();
            }
        });
        mBinding.fragmentLoginPasswordText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mPassword = editable.toString();
            }
        });

        Toolbar toolbar =  requireActivity().findViewById(R.id.activity_main_toolbar);
        toolbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.toolbar_gradient, requireActivity().getTheme()));
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
            SessionViewModel sessionViewModel = new ViewModelProvider(requireActivity()).get(SessionViewModel.class);
            UserViewModel userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
            ApiClient.getInstance((MainActivity) requireActivity()).logIn(sessionViewModel, userViewModel, mUsername, mPassword);
        } else if(v == mBinding.fragmentLoginRegisterLink) {
            NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
            NavController nvController = Objects.requireNonNull(navHostFragment).getNavController();
            nvController.navigate(R.id.nav_register);
        }
    }
}