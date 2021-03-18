package com.mad.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
* a. Assignment : #07.
* b. File Name : ListFragment (com.mad.contacts).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/


public class ListFragment extends Fragment  {

    private static final String CONTACTS_LIST_PARAM = "CONTACTS_LIST";

    private IListFragment listFragmentListener;

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ArrayList<Contact> contactsList;
    ContactListAdapter adapter;


    public interface IListFragment{
        void showCreateContact();
    }

    public ListFragment() {
    }

    public static ListFragment newInstance(ArrayList<Contact> contactsList) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable(CONTACTS_LIST_PARAM, contactsList);
        fragment.setArguments(args);
        return fragment;
    }
    public void updateContactList(ArrayList<Contact> contactsList){
        adapter.updateAdapter(contactsList);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.contactsList = (ArrayList<Contact>) getArguments().getSerializable(CONTACTS_LIST_PARAM);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ContactListAdapter(this.contactsList);
        recyclerView.setAdapter(adapter);

        view.findViewById(R.id.floatingCreateBlank).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listFragmentListener.showCreateContact();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ListFragment.IListFragment){
            listFragmentListener = (ListFragment.IListFragment) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.contacts);

    }
}