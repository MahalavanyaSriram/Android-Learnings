package com.uncc.mad.forums;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;


/**
* a. Assignment : Midterm.
* b. File Name : NewForumFragment (com.uncc.mad.forums).
* c. Full name of the student : Mahalavanya Sriram.
**/
public class NewForumFragment extends Fragment {

    private static final String AUTH_RESPONSE = "AUTH_RESPONSE";
    DataServices.AuthResponse authResponse;
    INewForumFragmentListener newForumFragmentListener;
    EditText newForumTitle;
    EditText newForumDesc;

    public interface INewForumFragmentListener{
        void showUpdatedForums();
    }
    public NewForumFragment() {

    }

    public static NewForumFragment newInstance(DataServices.AuthResponse authResponse) {
        NewForumFragment fragment = new NewForumFragment();
        Bundle args = new Bundle();
        args.putSerializable(AUTH_RESPONSE, authResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.authResponse = (DataServices.AuthResponse) getArguments().getSerializable(AUTH_RESPONSE);
        }
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof INewForumFragmentListener){
            newForumFragmentListener = (INewForumFragmentListener) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_new_forum, container, false);
        newForumTitle = view.findViewById(R.id.newForumTitle);
        newForumDesc = view.findViewById(R.id.newForumDesc);
        view.findViewById(R.id.newForumCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        view.findViewById(R.id.newForumSubmitButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newForumTitle.getText()==null || newForumTitle.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),R.string.empty_title,Toast.LENGTH_SHORT).show();
                }else if(newForumDesc.getText()==null || newForumDesc.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), R.string.empty_desc,Toast.LENGTH_SHORT).show();
                }else{
                    new NewForumFragment.CreateForums().execute(newForumTitle.getText().toString(), newForumDesc.getText().toString());
                }


            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.new_forum_fragment_name));
    }
    class CreateForums extends AsyncTask<String,String, DataServices.Forum> {
        String message;
        @Override
        protected DataServices.Forum doInBackground(String... details) {
           try {
                return DataServices.createForum(NewForumFragment.this.authResponse.getToken(),details[0],details[1]);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
           }
            return null;
        }

        @Override
        protected void onPostExecute(DataServices.Forum forum) {
            if(forum!=null){
                newForumFragmentListener.showUpdatedForums();
            }else{
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }

    }


}