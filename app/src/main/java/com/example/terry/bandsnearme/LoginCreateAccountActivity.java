package com.example.terry.bandsnearme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginCreateAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_create_account);
    }

    public void goToLoginScreen(View v){
        Intent intent = new Intent(this, LogInScreenActivity.class);
        startActivity(intent);
    }

    public void goToCreateAccount(View v){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

}
