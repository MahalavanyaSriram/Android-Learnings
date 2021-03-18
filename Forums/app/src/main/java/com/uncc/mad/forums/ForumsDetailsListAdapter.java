package com.uncc.mad.forums;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
* a. Assignment : Midterm.
* b. File Name : ForumsDetailsListAdapter (com.uncc.mad.forums).
* c. Full name of the student : Mahalavanya Sriram.
**/
public class ForumsDetailsListAdapter extends RecyclerView.Adapter<ForumsDetailsListAdapter.ForumsDetailsListViewHolder>{

    ArrayList<DataServices.Comment>  listOfComments;
    ForumsDetailsListAdapter.IForumsDetailsListener iForumsDetailsListener;
    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");
    private DataServices.Account loggedInUser;
    static DataServices.Forum forum;

    public ForumsDetailsListAdapter(ArrayList<DataServices.Comment>  data, DataServices.Account loggedInUser,ForumsDetailsListAdapter.IForumsDetailsListener iForumsListener, DataServices.Forum forum){
        this.listOfComments = data;
        this.iForumsDetailsListener = iForumsListener;
        this.loggedInUser = loggedInUser;
        this.forum = forum;
    }

    interface IForumsDetailsListener {
        public void delete(long forumId,long commentId);

    }

    @NonNull
    @Override
    public ForumsDetailsListAdapter.ForumsDetailsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_row_item,parent,false);
        ForumsDetailsListAdapter.ForumsDetailsListViewHolder viewHolder = new ForumsDetailsListAdapter.ForumsDetailsListViewHolder(view, this.iForumsDetailsListener);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return this.listOfComments.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ForumsDetailsListAdapter.ForumsDetailsListViewHolder holder, int position) {
        DataServices.Comment comment = this.listOfComments.get(position);
        holder.position = position;
        holder.comment = comment;
        holder.commentsRowMessage.setText(comment.getText());
        holder.commentsRowAuthor.setText(comment.getCreatedBy().getName());
        holder.commentsRowDate.setText(formatter.format(comment.getCreatedAt()).toString());
        if(comment.getCreatedBy().equals(this.loggedInUser)){
            holder.commentsRowDeleteButton.setVisibility(View.VISIBLE);
        }

    }


    public static class ForumsDetailsListViewHolder extends RecyclerView.ViewHolder{
        TextView commentsRowAuthor;
        TextView commentsRowMessage;
        TextView commentsRowDate;
        ImageButton commentsRowDeleteButton;
        int position;
        DataServices.Comment comment;

        public ForumsDetailsListViewHolder(@NonNull View itemView, ForumsDetailsListAdapter.IForumsDetailsListener iForumsDetailsListener) {
            super(itemView);
            commentsRowAuthor = itemView.findViewById(R.id.commentsRowAuthor);
            commentsRowMessage = itemView.findViewById(R.id.commentsRowMessage);
            commentsRowDate = itemView.findViewById(R.id.commentsRowDate);
            commentsRowDeleteButton = itemView.findViewById(R.id.commentsRowDeleteButton);

            commentsRowDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iForumsDetailsListener.delete(forum.getForumId(),comment.getCommentId());
                }
            });


        }
    }
}