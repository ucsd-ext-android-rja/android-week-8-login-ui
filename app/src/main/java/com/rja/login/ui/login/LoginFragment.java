package com.rja.login.ui.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.rja.login.Preferences;
import com.rja.login.R;
import com.rja.login.ui.main.MainActivity;

public class LoginFragment extends Fragment {

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputEditText usernameView = view.findViewById(R.id.username_edit_text);
        TextInputEditText passwordView = view.findViewById(R.id.password_edit_text);

        MaterialButton loginButton = view.findViewById(R.id.login_button);

        String lastUsername = Preferences.getUsername(requireContext());
        if(lastUsername != null)
            usernameView.setText(lastUsername);

        loginButton.setOnClickListener(v -> {
            if(validateUsernameAndPassword(usernameView, passwordView)) {
                Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                onLogin();
            }
            else
                Toast.makeText(getContext(), "Failure!", Toast.LENGTH_SHORT).show();
        });
    }

    private boolean validateUsernameAndPassword(TextInputEditText username, TextInputEditText password) {
        String usernameText = username.getText() != null ? username.getText().toString() : "";
        String passwordText = password.getText() != null ? password.getText().toString() : "";

        if(usernameText.length() < 3 || username.length() > 25) {
            username.setError("Username must be between 3 and 25 characters");
            return false;
        }

        if(passwordText.length() < 3 || passwordText.length() > 25) {
            password.setError("Wrong password");
            return false;
        }

        Preferences.setUsername(requireContext(), usernameText);

        return true;
    }

    private void onLogin() {
        startActivity(MainActivity.createIntent(getContext()));
    }

}
