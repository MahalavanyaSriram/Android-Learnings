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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateAccountFragment extends Fragment {

    private String name;
    private String email;
    private String password;
    private TextView updateEmail;
    private EditText updateName;
    private EditText updatePassword;
    private Button updateSubmitButton;
    private TextView updateCancelButton;

    IUpdateListener updateListener;

    public interface IUpdateListener{
        void updateAccount(String name, String password);
    }

    public UpdateAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IUpdateListener){
            updateListener = (IUpdateListener)context;
        }
    }

    public static UpdateAccountFragment newInstance(String name, String email, String password) {
        UpdateAccountFragment fragment = new UpdateAccountFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.NAME, name);
        args.putString(AppConstants.EMAIL, email);
        args.putString(AppConstants.PASSWORD, password);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(AppConstants.NAME);
            email = getArguments().getString(AppConstants.EMAIL);
            password = getArguments().getString(AppConstants.PASSWORD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_update_account, container, false);
        updateEmail = view.findViewById(R.id.updateEmail);
        updateName = view.findViewById(R.id.updateName);
        updatePassword = view.findViewById(R.id.updatePassword);
        updateSubmitButton = view.findViewById(R.id.updateSubmitButton);
        updateCancelButton = view.findViewById(R.id.updateCancel);

        updateEmail.setText(this.email);
        updateName.setText(this.name);
        updatePassword.setText(this.password);
        updateSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInputSanitized()){
                    if(isInputSanitized()){
                        updateListener.updateAccount(updateName.getText().toString(), updatePassword.getText().toString());
                    }

                }
            }
        });
        updateCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Account was not updated", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
        });
        return view;
    }

    public boolean isInputSanitized(){
        if(updateName.getText()==null || updateName.getText().toString()==null || updateName.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if(updatePassword.getText()==null || updatePassword.getText().toString()==null || updatePassword.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidName(updateName.getText().toString())){
            Toast.makeText(getActivity(), "Please enter a valid name", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public boolean isValidName(String name){
        return Pattern.compile(AppConstants.nameRegex).matcher(name).matches();
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.update_fragment_name));
    }
}