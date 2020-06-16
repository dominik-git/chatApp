package com.example.myapplication.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.ui.chat.MainChatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private FirebaseAuth mAuth;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth = FirebaseAuth.getInstance();
        mEmailView = v.findViewById(R.id.login_email);
        mPasswordView = v.findViewById(R.id.login_password);

        Button mLoginButton = v.findViewById(R.id.login_sign_in_button);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        return v;
    }

    private void attemptLogin() {
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        if (email.equals("") || password.equals("")) {
            return;
        }
        Toast.makeText(getActivity(), "Login...", Toast.LENGTH_SHORT).show();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (!task.isSuccessful()) {
                    Log.e("APP", "Login not successful " + task.getException());
                    ShowErrorDialog();
                } else {
                    Intent intent = new Intent(getActivity(), MainChatActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void ShowErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(R.string.login_dialog_message)
                .setTitle(R.string.login_dialog_title);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}