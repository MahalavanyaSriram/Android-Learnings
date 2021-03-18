package com.example.account;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    private String name;
    private String email;
    private String password;
    private TextView accountName;
    private Button accountEditProfileButton;
    private Button accountLogoutButton;

    IAccountListener accountListener;

    public interface IAccountListener{
        void showUpdateProfile();
        void doLogout();
    }

    public AccountFragment() {
    }

    public static AccountFragment newInstance(String name, String email, String password) {
        AccountFragment fragment = new AccountFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_account, container, false);
        accountName = view.findViewById(R.id.accountName);
        accountName.setText(this.name);
        accountEditProfileButton = view.findViewById(R.id.accountEditProfileButton);
        accountLogoutButton = view.findViewById(R.id.accountLogoutButton);
        accountEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accountListener.showUpdateProfile();
            }
        });
        accountLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: doLogout ");
                accountListener.doLogout();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IAccountListener){
            accountListener = (IAccountListener) context;
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.account_fragment_name));
    }
}