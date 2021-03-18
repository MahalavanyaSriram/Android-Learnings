package com.example.itunesapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.itunesapp.AppConstants.ARG_PARAM_TOKEN;
import static com.example.itunesapp.DataServices.*;


/**
* a. Assignment : #05.
* b. File Name : AppCategoryFragment (com.example.itunesapp).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class AppCategoryFragment extends Fragment implements AccountResponse , DataResponse<String>{


    private Account account;
    private String token;
    private ArrayList<String> appCategories;

    private TextView appCategoryText;
    private ListView appCategoryListView;
    private Button appCategoryLogout;
    private  IAppCategoryFragmentListener appCategoryFragmentListener;

    public AppCategoryFragment() {

    }

    public interface IAppCategoryFragmentListener{
        void logOut();
        void showAppsList(String category);
    }

    public static AppCategoryFragment newInstance(String token) {
        AppCategoryFragment fragment = new AppCategoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           token = getArguments().getString(ARG_PARAM_TOKEN);
           getAccount(token,AppCategoryFragment.this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_app_category, container, false);

      appCategoryText = view.findViewById(R.id.appCategoryText);
      appCategoryListView = view.findViewById(R.id.appCategoryListView);
      appCategoryLogout = view.findViewById(R.id.appCategoryLogout);

      appCategoryText.setText(getString(R.string.welcome) + " "+ this.account.getName());

      getAppCategories(this.token, AppCategoryFragment.this);

      ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, this.appCategories);
      appCategoryListView.setAdapter(adapter);
      appCategoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              String category = appCategories.get(position);
              appCategoryFragmentListener.showAppsList(category);
          }
      });
      appCategoryLogout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              appCategoryFragmentListener.logOut();
          }
      });


      return view;
    }

    @Override
    public void onSuccess(Account account) {
        this.account = account;
    }

    @Override
    public void onSuccess(ArrayList<String> data) {
        this.appCategories = data;
    }

    @Override
    public void onFailure(RequestException exception) {
        Toast.makeText(getActivity(), exception.getMessage(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof IAppCategoryFragmentListener){
            appCategoryFragmentListener = (IAppCategoryFragmentListener)context;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        getActivity().setTitle(getString(R.string.app_categories_fragment_name));
    }
}