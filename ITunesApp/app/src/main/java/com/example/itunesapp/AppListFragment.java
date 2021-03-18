package com.example.itunesapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.itunesapp.DataServices.*;


/**
* a. Assignment : #05.
* b. File Name : AppListFragment (com.example.itunesapp).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class AppListFragment extends Fragment implements DataResponse<App>{

    private String category;
    private String token;
    private ArrayList<App> appsList;
    private ListView listView;
    private IAppListFragmentListener appListFragmentListener;

    public interface IAppListFragmentListener{
        void showAppDetails(DataServices.App app);
    }

    public AppListFragment() {

    }

    public static AppListFragment newInstance(String category, String token) {
        AppListFragment fragment = new AppListFragment();
        Bundle args = new Bundle();
        args.putString(AppConstants.ARG_PARAM_CATEGORY, category);
        args.putString(AppConstants.ARG_PARAM_TOKEN, token);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(AppConstants.ARG_PARAM_CATEGORY);
            token = getArguments().getString(AppConstants.ARG_PARAM_TOKEN);
            getAppsByCategory(token, category, AppListFragment.this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_app_list, container, false);
        listView = view.findViewById(R.id.appListListView);
        AppAdapter adapter = new AppAdapter(getActivity(), R.layout.app_list_row,this.appsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                appListFragmentListener.showAppDetails(AppListFragment.this.appsList.get(position));
            }
        });
        return view;
    }

    @Override
    public void onSuccess(ArrayList<App> data) {
        this.appsList = data;
    }

    @Override
    public void onFailure(RequestException exception) {
        Toast.makeText(getActivity(),exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume(){
        super.onResume();
        ((MainActivity) getActivity()).setActionBarTitle((this.category));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AppListFragment.IAppListFragmentListener){
            appListFragmentListener = (AppListFragment.IAppListFragmentListener) context;
        }
    }
}