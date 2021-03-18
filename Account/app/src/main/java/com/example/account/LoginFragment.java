package com.example.account;

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


public class LoginFragment extends Fragment {

    EditText email;
    EditText password;
    Button login;
    TextView signup;
    ILoginListener loginListener;

    public interface ILoginListener{
        void showAccount(DataServices.Account account);
        void showSignUp();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email = view.findViewById(R.id.loginEmail);
        password = view.findViewById(R.id.loginPassword);
        login = view.findViewById(R.id.loginButton);
        signup = view.findViewById(R.id.loginSignup);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataServices.Account account = DataServices.login(email.getText().toString(), password.getText().toString());
                if(isInputSanitized()) {
                    if (account != null) {
                        email.setText(null);
                        password.setText(null);
                        loginListener.showAccount(account);

                    } else {
                        Toast.makeText(getActivity(),"login was not successful",Toast.LENGTH_SHORT ).show();

                    }
                }

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText(null);
                password.setText(null);
                loginListener.showSignUp();
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
    public boolean isInputSanitized(){
        if(email.getText()==null || email.getText().toString()==null || email.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Email should not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(password.getText()==null || password.getText().toString()==null || password.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Password should not be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.login_fragment_name));
    }

}