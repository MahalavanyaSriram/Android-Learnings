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
* b. File Name : RegisterFragment (com.example.itunesapp).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class RegisterFragment extends Fragment implements AuthResponse {

    EditText registerName;
    EditText registerEmail;
    EditText registerPassword;
    Button registerButton;
    TextView registerCancel;

    IRegisterListener registerListener;


    public interface IRegisterListener{
        void showAppCategories(String token);
    }

    public RegisterFragment() {
    }

    @Override
    public void onSuccess(String token) {
        registerListener.showAppCategories(token);
        Toast.makeText(getActivity(),getString(R.string.register_success),Toast.LENGTH_SHORT ).show();
    }



    @Override
    public void onFailure(RequestException exception) {
        Toast.makeText(getActivity(),exception.getMessage(),Toast.LENGTH_SHORT ).show();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_register, container, false);
        registerName = view.findViewById(R.id.registerName);
        registerEmail = view.findViewById(R.id.registerEmail);
        registerPassword = view.findViewById(R.id.registerPassword);
        registerButton = view.findViewById(R.id.registerSubmitButton);
        registerCancel = view.findViewById(R.id.registerCancel);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    register(registerName.getText().toString(), registerEmail.getText().toString(), registerPassword.getText().toString(),RegisterFragment.this);
            }
        });

        registerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();

            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IRegisterListener){
            registerListener = (IRegisterListener) context;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getActivity().setTitle(getString(R.string.register_fragment_name));
    }
}