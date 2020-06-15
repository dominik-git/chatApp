package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Login#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Login extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;

    private String mParam1;
    private String mParam2;

    public Login() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static Login newInstance(String param1, String param2) {
        Login fragment = new Login();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);
        mAuth= FirebaseAuth.getInstance();
        mEmailView = (AutoCompleteTextView) v.findViewById(R.id.login_email);
        mPasswordView = (EditText) v.findViewById(R.id.login_password);

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
            if(email.equals("") || password.equals("")){
                return;
            }
            Toast.makeText(getActivity(),"Login...",Toast.LENGTH_SHORT);
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        System.out.println("Login not successful "+ task.getException());
                        ShowErrorDialog();
                    }else{
                        Intent intent = new Intent(getActivity(), MainChatActivity.class);
                        startActivity(intent);
                    }
                }
            });



        }

        // TODO: Show error on screen with an alert dialog

        private void ShowErrorDialog(){
            // 1. Instantiate an <code><a href="/reference/android/app/AlertDialog.Builder.html">AlertDialog.Builder</a></code> with its constructor
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            // 2. Chain together various setter methods to set the dialog characteristics
            builder.setMessage(R.string.login_dialog_message)
                    .setTitle(R.string.login_dialog_title);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

}