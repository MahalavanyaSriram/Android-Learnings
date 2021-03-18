package com.example.itunesapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


/**
* a. Assignment : #05.
* b. File Name : AppDetailsFragment (com.example.itunesapp).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/
public class AppDetailsFragment extends Fragment {

    private DataServices.App selectedApp;
    private TextView appDetailsAppName;
    private TextView appDetailsArtistName;
    private TextView appDetailsReleaseDate;
    private ListView appDetailsListView;
    public AppDetailsFragment() {
    }

    public static AppDetailsFragment newInstance(DataServices.App app) {
        AppDetailsFragment fragment = new AppDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(AppConstants.ARG_PARAM_DETAILED_APP, app);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.selectedApp = (DataServices.App) getArguments().getSerializable(AppConstants.ARG_PARAM_DETAILED_APP);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_app_details, container, false);
        appDetailsAppName = view.findViewById(R.id.appDetailsAppName);
        appDetailsArtistName = view.findViewById(R.id.appDetailsArtistName);
        appDetailsReleaseDate = view.findViewById(R.id.appDetailsReleaseDate);
        appDetailsListView = view.findViewById(R.id.appDetailsListView);

        appDetailsAppName.setText(this.selectedApp.name);
        appDetailsArtistName.setText(this.selectedApp.artistName);
        appDetailsReleaseDate.setText(this.selectedApp.releaseDate);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, this.selectedApp.genres);
        appDetailsListView.setAdapter(adapter);
        return view;

    }

    @Override
    public void onResume(){
        super.onResume();
        getActivity().setTitle(getString(R.string.app_detail_fragment_name));
    }
}