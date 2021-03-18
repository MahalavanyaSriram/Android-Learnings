package com.mad.contacts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
* a. Assignment : #07.
* b. File Name : DetailsFragment (com.mad.contacts).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class DetailsFragment extends Fragment {

    private static final String CONTACT_PARAM = "CONTACT";

    private Contact contact;

    private IDetailsFragment detailsFragmentListener;

    private TextView displayName;
    private TextView displayEmail;
    private TextView displayPhone;
    private TextView displayType;

    public interface IDetailsFragment{
        void showUpdateContact(Contact contact);
        void deleteContact(int id);
    }

    public static DetailsFragment newInstance(Contact contact) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(CONTACT_PARAM, contact);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateContact(Contact contact){
        setContact(contact);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.contact = (Contact) getArguments().getSerializable(CONTACT_PARAM);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        displayName = view.findViewById(R.id.displayName);
        displayEmail = view.findViewById(R.id.displayEmail);
        displayPhone = view.findViewById(R.id.displayPhone);
        displayType = view.findViewById(R.id.displayType);

        setContact(contact);

        view.findViewById(R.id.deleteButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.delete_message)
                        .setCancelable(false)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                detailsFragmentListener.deleteContact(contact.getId());
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();
            }

        });
        view.findViewById(R.id.editButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                detailsFragmentListener.showUpdateContact(contact);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DetailsFragment.IDetailsFragment){
            detailsFragmentListener = (DetailsFragment.IDetailsFragment) context;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.contact_detail);
    }
    private void setContact(Contact contact){
        displayName.setText(contact.getName());
        displayEmail.setText(contact.getEmail());
        displayPhone.setText(contact.getPhone());
        displayType.setText(contact.getType());

    }

}