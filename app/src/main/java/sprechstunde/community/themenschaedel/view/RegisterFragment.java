package sprechstunde.community.themenschaedel.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.TextFieldValidation;
import sprechstunde.community.themenschaedel.api.ApiClient;
import sprechstunde.community.themenschaedel.databinding.FragmentRegisterBinding;
import sprechstunde.community.themenschaedel.viewmodel.HostViewModel;
import sprechstunde.community.themenschaedel.viewmodel.SessionViewModel;
import sprechstunde.community.themenschaedel.viewmodel.UserViewModel;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private FragmentRegisterBinding mBinding;
    private TextFieldValidation mUsername;
    private TextFieldValidation mEmail;
    private TextFieldValidation mPassword;

    public RegisterFragment() {
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
        mBinding = FragmentRegisterBinding.inflate(inflater, container, false);

        mUsername = new TextFieldValidation(mBinding.fragmentRegisterUsernameText, mBinding);
        mEmail = new TextFieldValidation(mBinding.fragmentRegisterPasswordText,mBinding);
        mPassword = new TextFieldValidation(mBinding.fragmentRegisterMailText, mBinding);

        mBinding.fragmentRegisterButton.setOnClickListener(this);
        mBinding.fragmentRegisterLoginLink.setOnClickListener(this);
        mBinding.fragmentRegisterUsernameText.addTextChangedListener(mUsername);
        mBinding.fragmentRegisterPasswordText.addTextChangedListener(mEmail);
        mBinding.fragmentRegisterMailText.addTextChangedListener(mPassword);

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
    public void onClick(View view) {
        if(view == mBinding.fragmentRegisterButton) {
            SessionViewModel sessionViewModel = new ViewModelProvider(requireActivity()).get(SessionViewModel.class);
            UserViewModel userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
            ApiClient.getInstance((MainActivity) requireActivity()).register(sessionViewModel, userViewModel, mUsername.getUsername(), mEmail.getEmail(), mPassword.getPassword());
        } else if(view == mBinding.fragmentRegisterLoginLink) {
            NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
            NavController nvController = Objects.requireNonNull(navHostFragment).getNavController();
            nvController.navigate(R.id.nav_login);
        }
    }
}