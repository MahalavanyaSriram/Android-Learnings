package com.example.itunesapp;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.itunesapp.DataServices.*;


/**
* a. Assignment : #05.
* b. File Name : LoginFragment (com.example.itunesapp).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class LoginFragment extends Fragment implements AuthResponse{

    EditText email;
    EditText password;
    Button login;
    TextView signup;
    ILoginListener loginListener;
    String token;

    @Override
    public void onSuccess(String token) {
        email.setText(null);
        password.setText(null);
        loginListener.showAppCategories(token);
        Toast.makeText(getActivity(),"Successfully Logged In",Toast.LENGTH_SHORT ).show();

    }


    @Override
    public void onFailure(RequestException exception) {
        Toast.makeText(getActivity(),exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    public interface ILoginListener{
        void showAppCategories(String token);
        void showRegister();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);
        login = view.findViewById(R.id.loginButton);
        signup = view.findViewById(R.id.loginSignup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(email.getText().toString(), password.getText().toString(), LoginFragment.this);

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText(null);
                password.setText(null);
                loginListener.showRegister();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ILoginListener){
            loginListener = (ILoginListener) context;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getActivity().setTitle(getString(R.string.login_fragment_name));
    }

}