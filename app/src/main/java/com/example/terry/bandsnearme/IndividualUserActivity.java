package com.example.terry.bandsnearme;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class IndividualUserActivity extends AppCompatActivity {

    EditText search;
    TextView notFound;
    TextView SearchNotFound;
    TextView welcome;
    Button searchButton;
    ConnectionClass connectionClass;
    public static String verifiedSearchBandOrVenueName;
    Button visitProfile;
    TextView searchTextView;
    boolean isBand = false;
    boolean isVenue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_user);
        connectionClass = new ConnectionClass();
        searchTextView = (TextView)findViewById(R.id.searchId);
        welcome = (TextView)findViewById(R.id.welcomeText);

        search = (EditText)findViewById(R.id.search);
        visitProfile = (Button)findViewById(R.id.goToBandOrVenueProfile);
        notFound = (TextView)findViewById(R.id.notFoundTxt);
        SearchNotFound = (TextView)findViewById(R.id.searchNotFound);

        welcome.setText("Welcome, " + LogInScreenActivity.verifiedUserLoginID + "!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logOut:
                AlertDialog.Builder logoutAlert = new AlertDialog.Builder(this);
                logoutAlert.setMessage("Are you sure you want to log out?");
                logoutAlert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        LogInScreenActivity.verifiedUserLoginID = null;
                        LogInScreenActivity.userType = null;
                        Intent intent = new Intent(IndividualUserActivity.this, LoginCreateAccountActivity.class);
                        startActivity(intent);
                    }
                });
                logoutAlert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing
                    }
                });
                logoutAlert.create().show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickSearchButton(View view){

        String searched = search.getText().toString();

        try {

            Connection con = connectionClass.CONN();
            String query = "select * from users where BandName='" + searched + "' OR VenueName='" +
                    searched + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);


            if(rs.next()){

                String checkIfNull = rs.getString(rs.findColumn("BandName"));
                if(checkIfNull == null) {
                    isBand = false;
                    isVenue = true;
                }else{
                    isBand = true;
                    isVenue = false;
                }
                verifiedSearchBandOrVenueName = searched;
                SearchNotFound.setVisibility(View.GONE);
                visitProfile.setVisibility(View.VISIBLE);
                notFound.setVisibility(View.VISIBLE);
            }else{
                isBand = false;
                isVenue = false;
                SearchNotFound.setVisibility(View.VISIBLE);
                notFound.setVisibility(View.GONE);
                visitProfile.setVisibility(View.GONE);
            }

        }catch (Exception e){

        }

    }



    public void visitBandOrVenueProfile(View view){

        if(isBand) {
            Intent intent = new Intent(IndividualUserActivity.this, ViewBandActivity.class);
            startActivity(intent);
        }else if(isVenue){

        }
    }
}
