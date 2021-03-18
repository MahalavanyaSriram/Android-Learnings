package com.uncc.mad.forums;

import android.content.Context;
import android.os.AsyncTask;
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

import java.util.ArrayList;


/**
* a. Assignment : Midterm.
* b. File Name : LoginFragment (com.uncc.mad.forums).
* c. Full name of the student : Mahalavanya Sriram.
**/
public class LoginFragment extends Fragment {

    EditText email;
    EditText password;
    Button login;
    TextView signup;
    ILoginListener loginListener;

    public interface ILoginListener{
        void showForums(DataServices.AuthResponse response);
        void showSignUp();
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
                new PostLogin().execute(email.getText().toString(),password.getText().toString());
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

    @Override
    public void onResume(){
        super.onResume();
        getActivity().setTitle(getString(R.string.login_fragment_name));
    }

    class PostLogin extends AsyncTask<String,String, DataServices.AuthResponse> {
        String message;
        @Override
        protected DataServices.AuthResponse doInBackground(String... loginDetails) {
            try {
                return DataServices.login(loginDetails[0],loginDetails[1]);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataServices.AuthResponse response) {
            if(response!=null){
                loginListener.showForums(response);
                email.setText(null);
                password.setText(null);
            }else{
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }

    }

}
