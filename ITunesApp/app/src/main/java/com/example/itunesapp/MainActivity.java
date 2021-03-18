package com.example.itunesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import static com.example.itunesapp.DataServices.*;


/**
* a. Assignment : #05.
* b. File Name : MainActivity (com.example.itunesapp).
* c. Full name of the student : Mahalavanya Sriram, Chandan Mannem.
**/

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginListener , RegisterFragment.IRegisterListener,
        AppCategoryFragment.IAppCategoryFragmentListener,AppListFragment.IAppListFragmentListener {

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment(),AppConstants.LOGIN_FRAGMENT_KEY)
                .commit();
    }

    public void setActionBarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void showAppCategories(String token) {
        this.token = token;
        getSupportFragmentManager().beginTransaction().replace(R.id.container,AppCategoryFragment.newInstance(token),AppConstants.APP_CATEGORY_FRAGMENT_KEY ).addToBackStack(null).commit();

    }

    @Override
    public void showRegister() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new RegisterFragment(), AppConstants.REGISTER_FRAGMENT_KEY).addToBackStack(null).commit();

    }

    @Override
    public void logOut() {
        this.token =null;
        int count = getSupportFragmentManager().getBackStackEntryCount();
        while(count > 0){
            getSupportFragmentManager().popBackStack();
            count --;
        }
    }

    @Override
    public void showAppsList(String category) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, AppListFragment.newInstance(category, this.token), AppConstants.APPS_LIST_FRAGMENT_KEY)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showAppDetails(App app) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,AppDetailsFragment.newInstance(app),AppConstants.APP_DETAIL_FRAGMENT_KEY)
                .addToBackStack(null)
                .commit();
    }
}