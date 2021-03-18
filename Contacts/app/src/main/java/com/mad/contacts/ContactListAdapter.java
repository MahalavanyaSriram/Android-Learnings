package com.mad.contacts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


/**
* a. Assignment : #07.
* b. File Name : ContactListAdapter (com.mad.contacts).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    ArrayList<Contact> contacts;
    IContactListAdapter contactListAdapterListener;

    public interface IContactListAdapter{
       void showContactDetail(Contact contact);
       void deleteContact(int id);
    }

    public ContactListAdapter(ArrayList<Contact> contacts){
        this.contacts = contacts;
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_row_item,parent,false);
       ContactViewHolder contactViewHolder = new ContactViewHolder(view);

        return contactViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        Contact contact = this.contacts.get(position);
        holder.textViewName.setText(contact.getName());
        holder.contact = contact;

    }

    public void updateAdapter(ArrayList<Contact> mDataList) {
        this.contacts = mDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.contacts.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (recyclerView.getContext() instanceof ContactListAdapter.IContactListAdapter){
            contactListAdapterListener = (ContactListAdapter.IContactListAdapter) recyclerView.getContext();
        }
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder{

        TextView textViewName;
        Contact contact;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.contactRowName);
            itemView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    contactListAdapterListener.showContactDetail(contact);

                }
            });
            itemView.findViewById(R.id.contactRowButton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());
                    builder.setTitle(R.string.delete_message)
                            .setCancelable(false)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    contactListAdapterListener.deleteContact(contact.getId());
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
        }
    }
}
