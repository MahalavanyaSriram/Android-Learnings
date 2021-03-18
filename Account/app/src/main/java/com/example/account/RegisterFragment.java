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

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    EditText registerName;
    EditText registerEmail;
    EditText registerPassword;
    Button registerButton;
    TextView registerCancel;

    IRegisterListener registerListener;

    public interface IRegisterListener{
        void showAccount(DataServices.Account account);
    }
    public RegisterFragment() {
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
                if(isInputSanitized()){
                    DataServices.Account account = DataServices.register(registerName.getText().toString(), registerEmail.getText().toString(), registerPassword.getText().toString());
                    if (account != null) {
                        registerListener.showAccount(account);
                        Toast.makeText(getActivity(),"Successfully Registered",Toast.LENGTH_SHORT ).show();
                    } else {
                        Toast.makeText(getActivity(),"User Already Exist",Toast.LENGTH_SHORT ).show();

                    }
                }
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
    public boolean isInputSanitized(){
        if(registerName.getText()==null || registerName.getText().toString()==null || registerName.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if(registerEmail.getText()==null || registerEmail.getText().toString()==null || registerEmail.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if(registerPassword.getText()==null || registerPassword.getText().toString()==null || registerPassword.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidName(registerName.getText().toString())){
            Toast.makeText(getActivity(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidEmailAddress(registerEmail.getText().toString())){
            Toast.makeText(getActivity(), "Please enter a valid email", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public boolean isValidEmailAddress(String email) {

        return Pattern.compile(AppConstants.emailRegex).matcher(email).matches();
    }
    public boolean isValidName(String name){
        return Pattern.compile(AppConstants.nameRegex).matcher(name).matches();
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.register_fragment_name));
    }
}