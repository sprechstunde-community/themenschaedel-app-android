package sprechstunde.community.themenschaedel;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import sprechstunde.community.themenschaedel.databinding.FragmentRegisterBinding;

public class TextFieldValidation implements TextWatcher {
    View mView;
    private FragmentRegisterBinding mBinding;
    private String mUsername;
    private String mPassword;
    private String mEmail;

    public TextFieldValidation(View view,FragmentRegisterBinding binding ) {
        mView = view;
        mBinding = binding;
    }

    @Override
    public void afterTextChanged(Editable editable) { }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(mView.getId() == R.id.fragment_register_username_text) {
            mUsername = s.toString();
            validateUsername();
        } else if(mView.getId() == R.id.fragment_register_mail_text ) {
            mEmail = s.toString();
            validateEmail();
        } else if(mView.getId() == R.id.fragment_register_password_text) {
            mPassword = s.toString();
            validatePassword();
        }
    }

    /**
     * field must not be empty
     */
    private boolean validateUsername() {
        if (mUsername.trim().isEmpty()) {
            mBinding.fragmentRegisterUsername.setError(mView.getResources().getString(R.string.required_field));
            mBinding.fragmentRegisterUsername.requestFocus();
            return false;
        } else {
            mBinding.fragmentRegisterUsername.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * 1) field must not be empty
     */
    private boolean validateEmail() {
        if (mEmail.trim().isEmpty()) {
            mBinding.fragmentRegisterMail.setError(mView.getResources().getString(R.string.required_field));
            mBinding.fragmentRegisterMail.requestFocus();
            return false;
        } else {
            mBinding.fragmentRegisterMail.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * 1) field must not be empty
     * 2) password length must not be less than 6
     */
    private boolean validatePassword() {
        if (mPassword.trim().isEmpty()) {
            mBinding.fragmentRegisterPassword.setError(mView.getResources().getString(R.string.required_field));
            mBinding.fragmentRegisterPassword.requestFocus();
            return false;
        } else if (mPassword.length() < 6) {
            mBinding.fragmentRegisterPassword.setError(mView.getResources().getString(R.string.password_length));
            mBinding.fragmentRegisterPassword.requestFocus();
            return false;
        } else {
            mBinding.fragmentRegisterPassword.setErrorEnabled(false);
        }
        return true;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }
}
