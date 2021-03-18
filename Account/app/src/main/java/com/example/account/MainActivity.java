package com.example.account;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LoginFragment.ILoginListener,RegisterFragment.IRegisterListener,
        AccountFragment.IAccountListener,UpdateAccountFragment.IUpdateListener {

    DataServices.Account account ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().add(R.id.container,new LoginFragment(),AppConstants.LOGIN_FRAGMENT_KEY).commit();
    }

    @Override
    public void showAccount(DataServices.Account account) {
        this.account = account;
        getSupportFragmentManager().beginTransaction().replace(R.id.container, AccountFragment.newInstance(account.getName(), account.getEmail(), account.getPassword()), AppConstants.ACCOUNT_FRAGMENT_KEY).addToBackStack(null).commit();

    }

    @Override
    public void showSignUp() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new RegisterFragment(),AppConstants.REGISTER_FRAGMENT_KEY).addToBackStack(null).commit();
    }

    @Override
    public void updateAccount(String name ,String password) {
            DataServices.Account updatedAccount = DataServices.update(this.account,name, password);
            this.account = updatedAccount;
            if(updatedAccount!=null){
                Toast.makeText(this,"Account was updated successfully", Toast.LENGTH_SHORT).show();
                showAccount(updatedAccount);
            }
    }

    @Override
    public void showUpdateProfile() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container, UpdateAccountFragment.newInstance(this.account.getName(),
                this.account.getEmail(), this.account.getPassword()), AppConstants.UPDATE_FRAGMENT_KEY)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void doLogout() {
        Log.d("main", "doLogout: ");
        this.account = null;
        int count = getSupportFragmentManager().getBackStackEntryCount();
        while(count > 0){
            getSupportFragmentManager().popBackStack();
            count --;
        }
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
    }


