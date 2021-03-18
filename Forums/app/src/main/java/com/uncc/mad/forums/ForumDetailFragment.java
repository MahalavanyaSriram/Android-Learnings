package com.uncc.mad.forums;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
* a. Assignment : Midterm.
* b. File Name : ForumDetailFragment (com.uncc.mad.forums).
* c. Full name of the student : Mahalavanya Sriram.
**/
public class ForumDetailFragment extends Fragment implements ForumsDetailsListAdapter.IForumsDetailsListener{

    private static final String FORUM = "FORUM";
    private static final String AUTH_RESPONSE = "AUTH_RESPONSE";
    private DataServices.AuthResponse authResponse;
    private DataServices.Forum forum;
    private TextView forumDetailTitle;
    private TextView forumDetailAuthor;
    private TextView forumDetailDesc;
    private TextView forumDetailCommentNumber;
    private EditText forumDetailCommentEdit;
    private Button forumDetailPostButton;
    private RecyclerView forumDetailRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<DataServices.Comment> listOfComments;
    private ForumsDetailsListAdapter adapter;


    public ForumDetailFragment() {

    }

    public static ForumDetailFragment newInstance(DataServices.Forum forum,DataServices.AuthResponse authResponse) {
        ForumDetailFragment fragment = new ForumDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(FORUM, forum);
        args.putSerializable(AUTH_RESPONSE, authResponse);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            forum = (DataServices.Forum)getArguments().getSerializable(FORUM);
            authResponse = (DataServices.AuthResponse)getArguments().getSerializable(AUTH_RESPONSE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getString(R.string.forum_detail_fragment_name));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_forum_detail, container, false);
        forumDetailTitle = view.findViewById(R.id.forumDetailTitle);
        forumDetailAuthor = view.findViewById(R.id.forumDetailAuthor);
        forumDetailDesc = view.findViewById(R.id.forumDetailDesc);
        forumDetailCommentNumber= view.findViewById(R.id.forumDetailCommentNumber);
        forumDetailCommentEdit = view.findViewById(R.id.forumDetailCommentEdit);
        forumDetailPostButton = view.findViewById(R.id.forumDetailPostButton);
        forumDetailRecyclerView = view.findViewById(R.id.forumDetailRecyclerView);
        forumDetailTitle.setText(forum.getTitle());
        forumDetailAuthor.setText(forum.getCreatedBy().getName());
        forumDetailDesc.setText(forum.getDescription());

        new GetForumComments().execute(forum.getForumId());


        forumDetailPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forumDetailCommentEdit.getText()!=null&& !forumDetailCommentEdit.getText().toString().isEmpty()){
                        new CreateForumComments(forumDetailCommentEdit.getText().toString()).execute(forum.getForumId());
                }else{
                    Toast.makeText(getActivity(),"Empty comment cannot be posted",Toast.LENGTH_SHORT).show();
                }
            }
        });

        linearLayoutManager = new LinearLayoutManager(getActivity());
        forumDetailRecyclerView.setLayoutManager(linearLayoutManager);
        return view;
    }

    @Override
    public void delete(long forumId, long commentId) {
        new DeleteForumComments().execute(forumId,commentId);
    }


    class GetForumComments extends AsyncTask<Long,String, ArrayList<DataServices.Comment>> {
        String message;

        @Override
        protected ArrayList<DataServices.Comment> doInBackground(Long... id) {
            try {
                return DataServices.getForumComments(authResponse.getToken(), id[0]);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<DataServices.Comment> comments) {
            if(comments!=null){
                listOfComments = comments;
                Log.d("App",listOfComments.size()+"");
                forumDetailCommentNumber.setText(listOfComments.size()+ " Comments");
                adapter = new ForumsDetailsListAdapter(listOfComments,authResponse.getAccount(),ForumDetailFragment.this,forum);
                forumDetailRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    class CreateForumComments extends AsyncTask<Long,String, DataServices.Comment> {
        String message;
        String commentText;

        public CreateForumComments(String commentText){
            this.commentText = commentText;
        }

        @Override
        protected DataServices.Comment doInBackground(Long... id) {
            try {
                return DataServices.createComment(authResponse.getToken(), id[0],commentText);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(DataServices.Comment comment) {
            if(comment!=null){
                new GetForumComments().execute(forum.getForumId());
            }else{
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    }


    class DeleteForumComments extends AsyncTask<Long,String, Void> {
        String message;
        @Override
        protected Void doInBackground(Long... id) {
            try {
                DataServices.deleteComment(authResponse.getToken(), id[0],id[1]);
            } catch (DataServices.RequestException e) {
                message = e.getMessage();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(message==null){
                new GetForumComments().execute(forum.getForumId());
            }else{
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }

    }
}