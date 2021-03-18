package com.mad.contacts;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


/**
* a. Assignment : #07.
* b. File Name : BlankContactFragment (com.mad.contacts).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class BlankContactFragment extends Fragment {

    private FloatingActionButton floatingCreateBlank;
    private BlankContactFragment.IBlankContactFragmentListener iBlankContactFragmentListener;

    public BlankContactFragment() {
    }

   @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof BlankContactFragment.IBlankContactFragmentListener){
            iBlankContactFragmentListener = (BlankContactFragment.IBlankContactFragmentListener)context;
        }
   }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_blank_contact, container, false);
        floatingCreateBlank = view.findViewById(R.id.floatingCreateBlank);
        floatingCreateBlank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iBlankContactFragmentListener.showCreateContact();
            }
        });
        return view;
    }

    public interface IBlankContactFragmentListener{
        public void showCreateContact();
    }
}