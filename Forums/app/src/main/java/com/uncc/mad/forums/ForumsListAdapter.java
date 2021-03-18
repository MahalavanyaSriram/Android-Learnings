package com.uncc.mad.forums;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
* a. Assignment : Midterm.
* b. File Name : ForumsListAdapter (com.uncc.mad.forums).
* c. Full name of the student : Mahalavanya Sriram.
**/
public class ForumsListAdapter extends RecyclerView.Adapter<ForumsListAdapter.ForumsListViewHolder>{

    private DataServices.Account loggedInUser;

    IForumsListener iForumsListener;

    interface IForumsListener {
        void doDelete(long forumId);
        void likeForum(long forumId);
        void unLikeForum(long forumId);
        void showForumDetail(DataServices.Forum forum);
    }

    private ArrayList<DataServices.Forum> forumsList;
    DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm aa");

    public ForumsListAdapter(ArrayList<DataServices.Forum> data, DataServices.Account loggedInUser, IForumsListener iForumsListener){
        this.forumsList = data;
        this.loggedInUser = loggedInUser;
        this.iForumsListener = iForumsListener;
    }

    @NonNull
    @Override
    public ForumsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forums_row_item,parent,false);
        ForumsListViewHolder viewHolder = new ForumsListViewHolder(view, this.iForumsListener);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return this.forumsList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ForumsListViewHolder holder, int position) {
        DataServices.Forum forum = this.forumsList.get(position);
        holder.position = position;
        holder.forum = forum;
        holder.forumsRowTitle.setText(forum.getTitle());
        holder.forumsRowAuthor.setText(forum.getCreatedBy().getName());
        if(forum.getDescription().length()>200){
            holder.forumsRowDesc.setText(forum.getDescription().substring(0,200));
        }else{
            holder.forumsRowDesc.setText(forum.getDescription());
        }
        holder.forumsRowLikes.setText(forum.getLikedBy().size()+" Likes");
        holder.forumsRowDate.setText(formatter.format(forum.getCreatedAt()).toString());
        if(forum.getCreatedBy().equals(this.loggedInUser)){
            holder.forumsRowDeleteButton.setVisibility(View.VISIBLE);
        }
        if(forum.getLikedBy().contains(this.loggedInUser)){
            holder.forumsRowLikeButton.setImageResource(R.drawable.ic_like_favorite);
            holder.liked = true;
        }else{
            holder.forumsRowLikeButton.setImageResource(R.drawable.ic_like_not_favorite);
            holder.liked=false;
        }
    }

    public static class ForumsListViewHolder extends RecyclerView.ViewHolder{

        TextView forumsRowTitle;
        TextView forumsRowAuthor;
        TextView forumsRowDesc;
        TextView forumsRowLikes;
        TextView forumsRowDate;
        ImageButton forumsRowDeleteButton;
        ImageButton forumsRowLikeButton;
        int position;
        boolean liked;
        DataServices.Forum forum;

        public ForumsListViewHolder(@NonNull View itemView, IForumsListener iForumsListener) {
            super(itemView);
            forumsRowTitle = itemView.findViewById(R.id.forumsRowTitle);
            forumsRowAuthor = itemView.findViewById(R.id.forumsRowAuthor);
            forumsRowDesc = itemView.findViewById(R.id.forumsRowDesc);
            forumsRowLikes = itemView.findViewById(R.id.forumsRowLikes);
            forumsRowDate = itemView.findViewById(R.id.forumsRowDate);
            forumsRowDeleteButton = itemView.findViewById(R.id.forumsRowDeleteButton);
            forumsRowLikeButton = itemView.findViewById(R.id.lforumsRowLikeButton);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    iForumsListener.showForumDetail(forum);

                }
            });
            forumsRowDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iForumsListener.doDelete(forum.getForumId());
                }
            });
            forumsRowLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(liked){
                        iForumsListener.unLikeForum(forum.getForumId());
                    }else{
                        iForumsListener.likeForum(forum.getForumId());
                    }
                }
            });

        }
    }



}