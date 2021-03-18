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


/**
* a. Assignment : Midterm.
* b. File Name : RegisterFragment (com.uncc.mad.forums).
* c. Full name of the student : Mahalavanya Sriram.
**/
public class RegisterFragment extends Fragment {

    EditText registerName;
    EditText registerEmail;
    EditText registerPassword;
    Button registerButton;
    TextView registerCancel;

    IRegisterListener registerListener;

    public interface IRegisterListener{
        void showForums(DataServices.AuthResponse response);
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

        registerCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PostRegister().execute(registerName.getText().toString(), registerEmail.getText().toString(),registerPassword.getText().toString());

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


    class PostRegister extends AsyncTask<String,String, DataServices.AuthResponse> {
        String message;

        @Override
        protected DataServices.AuthResponse doInBackground(String... registerDetails) {
            try {
                return DataServices.register(registerDetails[0],registerDetails[1], registerDetails[2]);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataServices.AuthResponse response) {
            if(response!=null){
                registerListener.showForums(response);
            }else{
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }

    }
}