package com.uncc.mad.forums;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


/**
* a. Assignment : Midterm.
* b. File Name : MainActivity (com.uncc.mad.forums).
* c. Full name of the student : Mahalavanya Sriram.
**/
public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginListener, RegisterFragment.IRegisterListener, NewForumFragment.INewForumFragmentListener, ForumsFragment.IForumsFragmentListener{


    private static final String LOGIN_FRAGMENT_KEY = "LOGIN_FRAGMENT";
    private static final String REGISTER_FRAGMENT_KEY = "REGISTER_FRAGMENT";
    private static final String FORUMS_FRAGMENT_KEY = "FORUMS_FRAGMENT";
    private static final String NEW_FORUM_FRAGMENT_KEY = "NEW_FORUM_FRAGMENT";
    private static final String FORUM_DETAIL_FRAGMENT_KEY = "FORUM_DETAIL_FRAGMENT";

    private DataServices.AuthResponse authResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment(), LOGIN_FRAGMENT_KEY)
                .commit();
    }

    @Override
    public void showForums(DataServices.AuthResponse response) {
        this.authResponse = response;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, ForumsFragment.newInstance(this.authResponse), FORUMS_FRAGMENT_KEY)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showSignUp() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new RegisterFragment(), REGISTER_FRAGMENT_KEY)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showUpdatedForums() {
        ForumsFragment forumsFragment = (ForumsFragment) getSupportFragmentManager().findFragmentByTag(FORUMS_FRAGMENT_KEY);
        forumsFragment.pullLatestForums(this.authResponse);
        getSupportFragmentManager().popBackStack();

    }

    @Override
    public void logout() {
        this.authResponse = null;
        int count = getSupportFragmentManager().getBackStackEntryCount();
        while(count > 0){
            getSupportFragmentManager().popBackStack();
            count --;
        }
    }

    @Override
    public void showNewForum() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, NewForumFragment.newInstance(this.authResponse) , NEW_FORUM_FRAGMENT_KEY)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showForumDetailFragment(DataServices.Forum forum) {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, ForumDetailFragment.newInstance(forum,this.authResponse) , FORUM_DETAIL_FRAGMENT_KEY)
                .addToBackStack(null)
                .commit();
    }
}