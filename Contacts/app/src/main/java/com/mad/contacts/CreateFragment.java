package com.mad.contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;


/**
* a. Assignment : #07.
* b. File Name : CreateFragment (com.mad.contacts).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class CreateFragment extends Fragment {


    private static final String ARG_CONTACT = "CONTACT";
    public  static final String emailRegex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
    public  static final String nameRegex = "^[a-zA-Z][a-zA-Z0-9., ]+$";

    private ICreateFragment createFragmentListener;

    EditText name;
    EditText email;
    EditText phone;
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    Boolean isEdit;

    private Contact contact = null;

    public CreateFragment() {
        isEdit = false;
    }


    public interface ICreateFragment{
        void createContact(Contact contact);
        void updateContact(Contact contact);
    }


    public static CreateFragment newInstance(Contact contact) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           contact = (Contact) getArguments().getSerializable(ARG_CONTACT);
           isEdit = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create, container, false);
        name = view.findViewById(R.id.createName);
        email = view.findViewById(R.id.createEmail);
        phone = view.findViewById(R.id.createPhone);
        radioGroup = view.findViewById(R.id.radioGroup);
        if(isEdit){
            name.setText(contact.getName());
            email.setText(contact.getEmail());
            phone.setText(contact.getPhone());
            Log.d("fragment", "onCreateView: "+contact.getType().trim());
            if(contact.getType().trim().equals("CELL")){
                Log.d("fragment", "onCreateView: "+contact.getType().trim());
                radioGroup.check(R.id.cellRadioButton);
            }
            else if(contact.getType().trim().equals("OFFICE")){
                radioGroup.check(R.id.officeRadioButton);

            }else if(contact.getType().trim().equals("HOME")){
                radioGroup.check(R.id.homeRadioButton);

            }
        }
        view.findViewById(R.id.cancelButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        view.findViewById(R.id.submitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInputSanitized()){
                    selectedRadioButton = view.findViewById(radioGroup.getCheckedRadioButtonId());
                   if(isEdit) {

                       createFragmentListener.updateContact(setContact(contact));
                   }
                   else{
                      createFragmentListener.createContact(setContact(new Contact()));

                   }
                }

            }
        });
        return view;
    }
    public boolean isInputSanitized(){
        if(name.getText()==null || name.getText().toString().trim().isEmpty()){
            showAlertDialogForError(getString(R.string.name_empty_error));
            return false;
        } else if(email.getText()==null || email.getText().toString().trim().isEmpty()){
            showAlertDialogForError(getString(R.string.email_empty_error));
            return false;
        }else if(phone.getText()==null || phone.getText().toString().trim().isEmpty()){
            showAlertDialogForError(getString(R.string.phone_empty_error));
            return false;
        }else if(!isValidName(name.getText().toString().trim())){
            showAlertDialogForError(getString(R.string.name_valid_error));
            return false;
        }else if(!isValidEmailAddress(email.getText().toString().trim())){
            showAlertDialogForError(getString(R.string.email_valid_error));
            return false;
        }
        return true;
    }

    public void showAlertDialogForError(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.error);
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        TextView text = customLayout.findViewById(R.id.alertText);
        builder.setView(customLayout);
        text.setText(message);
        builder.setNegativeButton(getText(R.string.close), new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public boolean isValidEmailAddress(String email) {

        return Pattern.compile(emailRegex).matcher(email).matches();
    }
    public boolean isValidName(String name){
        return Pattern.compile(nameRegex).matcher(name).matches();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof CreateFragment.ICreateFragment){
            createFragmentListener = (CreateFragment.ICreateFragment) context;
        }
    }

    private Contact setContact(Contact contact){
        contact.setName(name.getText().toString());
        contact.setEmail(email.getText().toString());
        contact.setPhone(phone.getText().toString());
        contact.setType(selectedRadioButton.getText().toString());
        return contact;
    }
    @Override
    public void onResume() {
        super.onResume();
        if(isEdit) {
            getActivity().setTitle(R.string.update_contact);
        }
        else{
            getActivity().setTitle(R.string.create_contact);
        }
    }

}