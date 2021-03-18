package com.mad.contacts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


/**
* a. Assignment : #07.
* b. File Name : MainActivity (com.mad.contacts).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class MainActivity extends AppCompatActivity implements ContactListAdapter.IContactListAdapter, DetailsFragment.IDetailsFragment,ListFragment.IListFragment, CreateFragment.ICreateFragment, BlankContactFragment.IBlankContactFragmentListener {

    private static final String TAG = "Contact Main Activity";

    private static final String LIST_FRAGMENT_KEY = "LIST_FRAGMENT";
    private static final String DETAIL_FRAGMENT_KEY = "DETAIL_FRAGMENT";
    private static final String CREATE_FRAGMENT_KEY = "CREATE_FRAGMENT";
    private static final String BlANK_FRAGMENT_KEY = "BlANK_FRAGMENT";

    public static final OkHttpClient client = new OkHttpClient();

    public static Contact getContact(int id){

        final Contact contact = new Contact();

        Request getContactRequest = new Request.Builder().url(Util.getUrl(UrlConstants.GET_CONTACT_URL)).build();

        client.newCall(getContactRequest).enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();

                    String body = responseBody.string();
                    Log.d("Response", body);
                    String[] row = body.split("/n");
                    String[] item = row[0].split(",");
                    if(item.length == 5){
                        contact.setId(Integer.parseInt(item[0]));
                        contact.setName(item[1]);
                        contact.setEmail(item[2]);
                        contact.setPhone(item[3]);
                        contact.setType(item[4]);
                    }

                }
            }


            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });
        return contact;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getAllContacts();

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void showContactDetail(Contact contact) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, DetailsFragment.newInstance(contact),DETAIL_FRAGMENT_KEY).addToBackStack(null).commit();

    }

    @Override
    public void showUpdateContact(Contact contact) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, CreateFragment.newInstance(contact),CREATE_FRAGMENT_KEY).addToBackStack(null).commit();

    }

    @Override
    public void showCreateContact() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new CreateFragment(),CREATE_FRAGMENT_KEY).addToBackStack(null).commit();

    }

    @Override
    public void createContact(Contact contact){
        FormBody formBody = new FormBody.Builder().add("name",contact.getName()).add("email",contact.getEmail()).add("phone",contact.getPhone()).add("type",contact.getType()).build();
        Request postCreateRequest = new Request.Builder().url(Util.getUrl(UrlConstants.POST_CREATE_CONTACT_URL)).post(formBody).build();

        MainActivity.client.newCall(postCreateRequest).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    Log.d(TAG,"onResponse" + body);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,body,Toast.LENGTH_SHORT).show();
                            getAllContacts();
                            getSupportFragmentManager().popBackStack();

                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, R.string.create_unsuccessful, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void updateContact(Contact contact) {
        FormBody formBody = new FormBody.Builder().add("id",String.valueOf(contact.getId())).add("name",contact.getName()).add("email",contact.getEmail()).add("phone",contact.getPhone()).add("type",contact.getType()).build();
        Request postUpdateRequest = new Request.Builder().url(Util.getUrl(UrlConstants.POST_UPDATE_CONTACT_URL)).post(formBody).build();

        MainActivity.client.newCall(postUpdateRequest).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,body,Toast.LENGTH_SHORT).show();
                            DetailsFragment detailsFragment =
                                    (DetailsFragment) getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT_KEY);

                            if(detailsFragment != null){
                                detailsFragment.updateContact(contact);
                            }
                            getSupportFragmentManager().popBackStack();
                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, R.string.update_unsuccessful, Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void deleteContact(int id) {
        FormBody formBody = new FormBody.Builder().add("id", String.valueOf(id)).build();
        Request postDeleteRequest = new Request.Builder().url(Util.getUrl(UrlConstants.POST_DELETE_CONTACT_URL)).post(formBody).build();

        MainActivity.client.newCall(postDeleteRequest).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this,body,Toast.LENGTH_SHORT).show();
                            getSupportFragmentManager().popBackStack();
                            getAllContacts();

                        }
                    });
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, R.string.delete_unsuccessful, Toast.LENGTH_SHORT).show();
                            getAllContacts();

                        }
                    });
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });
        }



    private void getAllContacts() {
        ArrayList<Contact> contactsList = new ArrayList<>();
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setMessage("Loading Contacts");
        dialog.setCancelable(false);
        dialog.show();
        Request getAllRequest = new Request.Builder().url(Util.getUrl(UrlConstants.GET_ALL_CONTACTS_URL)).build();
        MainActivity.client.newCall(getAllRequest).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    String body = responseBody.string();
                        Log.d(TAG, "onResponse" + body);
                        if(!body.isEmpty()) {
                            String[] rows = body.split("\\n");
                            for (int i = 0; i < rows.length; i++) {
                                String[] item = rows[i].split(",");
                                Contact contact = new Contact();
                                contact.setId(Integer.parseInt(item[0]));
                                contact.setName(item[1]);
                                contact.setEmail(item[2]);
                                contact.setPhone(item[3]);
                                contact.setType(item[4]);
                                contactsList.add(contact);

                            }
                        }
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                dialog.dismiss();
                                if(!contactsList.isEmpty()) {
                                    ListFragment listFragment =
                                            (ListFragment) getSupportFragmentManager().findFragmentByTag(LIST_FRAGMENT_KEY);

                                    if(listFragment != null){
                                        listFragment.updateContactList(contactsList);
                                    }
                                    else{
                                        getSupportFragmentManager().beginTransaction().replace(R.id.container, ListFragment.newInstance(contactsList), LIST_FRAGMENT_KEY).commit();
                                    }
                                }else{
                                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new BlankContactFragment(), BlANK_FRAGMENT_KEY).commit();
                                }
                            }
                        });
                    }
                }


            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
        });
    }


}
