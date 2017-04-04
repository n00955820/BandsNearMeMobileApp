package com.example.terry.bandsnearme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToMaps(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void goToIndividualUser(View v){
        Intent intent = new Intent(this, IndividualUserActivity.class);
        startActivity(intent);
    }

    public void goToBand(View v){
        Intent intent = new Intent(this, BandActivity.class);
        startActivity(intent);
    }

    public void goToVenue(View v){
        Intent intent = new Intent(this, VenueActivity.class);
        startActivity(intent);
    }

    public void goToLoginCreateAccount(View v){
        Intent intent = new Intent(this, LoginCreateAccountActivity.class);
        startActivity(intent);
    }

}
