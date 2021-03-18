package com.uncc.mad.forums;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;


/**
* a. Assignment : Midterm.
* b. File Name : ForumsFragment (com.uncc.mad.forums).
* c. Full name of the student : Mahalavanya Sriram.
**/
public class ForumsFragment extends Fragment implements ForumsListAdapter.IForumsListener{

    private static final String AUTH_RESPONSE = "AUTH_RESPONSE";
    private DataServices.AuthResponse authResponse;
    Button forumsLogoutButton;
    Button forumsNewForum;
    private RecyclerView forumsRecyclerView;
    LinearLayoutManager linearLayoutManager;
    ForumsListAdapter adapter;
    ArrayList<DataServices.Forum> forumList;

    IForumsFragmentListener forumsFragmentListener;

    interface IForumsFragmentListener {
        void logout();
        void showNewForum();
        void showForumDetailFragment(DataServices.Forum forum);
    }

    public ForumsFragment() {
    }

    public static ForumsFragment newInstance(DataServices.AuthResponse authResponse) {
        ForumsFragment fragment = new ForumsFragment();
        Bundle args = new Bundle();
        args.putSerializable(AUTH_RESPONSE, authResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            authResponse = (DataServices.AuthResponse)getArguments().getSerializable(AUTH_RESPONSE);
        }
    }

    public void pullLatestForums(DataServices.AuthResponse authResponse){
        this.authResponse = authResponse;
        new GetForums().execute(authResponse.getToken());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_forums, container, false);
        forumsLogoutButton = view.findViewById(R.id.forumsLogoutButton);
        forumsNewForum = view.findViewById(R.id.forumsNewForum);
        forumsRecyclerView = view.findViewById(R.id.forumsRecyclerView);
        new GetForums().execute(authResponse.getToken());

        forumsLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumsFragmentListener.logout();
            }
        });

        forumsNewForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forumsFragmentListener.showNewForum();
            }
        });
        linearLayoutManager = new LinearLayoutManager(getActivity());
        forumsRecyclerView.setLayoutManager(linearLayoutManager);
        this.forumList = new ArrayList<>();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof IForumsFragmentListener){
            forumsFragmentListener = (IForumsFragmentListener) context;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
       getActivity().setTitle(getString(R.string.forums_fragment_name));
    }

    @Override
    public void likeForum(long forumId) {
        new LikeForum().execute(forumId);
    }

    @Override
    public void unLikeForum(long forumId) {
        new UnLikeForum().execute(forumId);
    }

    @Override
    public void showForumDetail(DataServices.Forum forum) {
        forumsFragmentListener.showForumDetailFragment(forum);
    }


    @Override
    public void doDelete(long forumId) {
        new DeleteForum().execute(forumId);
    }

    class LikeForum extends AsyncTask<Long,String,Void> {
        String message;
        @Override
        protected Void doInBackground(Long... id) {
            try {
                DataServices.likeForum(ForumsFragment.this.authResponse.getToken(),id[0]);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pullLatestForums(ForumsFragment.this.authResponse);
        }
    }

    class UnLikeForum extends AsyncTask<Long,String,Void> {
        String message;

        @Override
        protected Void doInBackground(Long... id) {
            try {
                DataServices.unLikeForum(ForumsFragment.this.authResponse.getToken(), id[0]);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pullLatestForums(ForumsFragment.this.authResponse);
        }
    }

    class DeleteForum extends AsyncTask<Long,String,Void> {
        String message;
        @Override
        protected Void doInBackground(Long... id) {
            try {
                DataServices.deleteForum(ForumsFragment.this.authResponse.getToken(),id[0]);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pullLatestForums(ForumsFragment.this.authResponse);
        }
    }

    class GetForums extends AsyncTask<String,String,ArrayList<DataServices.Forum>> {
        String message;
        @Override
        protected ArrayList<DataServices.Forum> doInBackground(String... token) {
            try {
                return DataServices.getAllForums(token[0]);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DataServices.Forum> forums) {
            if(forums!=null){
                forumList = forums;
                adapter = new ForumsListAdapter(forumList,authResponse.getAccount(),ForumsFragment.this);
                forumsRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }

    }
}