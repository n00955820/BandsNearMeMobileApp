package com.example.terry.bandsnearme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VenueActivity extends AppCompatActivity {

    ConnectionClass connectionClass;
    String query = "";
    Statement stmt = null;
    ResultSet rs = null;
    TextView currentView = null;
    Connection con = null;
    String result = "";
    Button btnUpcoming = null;
    Button btnCreateShow = null;
    Button btnEditProfile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        connectionClass = new ConnectionClass();
        con = connectionClass.CONN();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);

        btnUpcoming = (Button) findViewById(R.id.upcoming);
        query = "select * from SHOW where VUserName = '" + LogInScreenActivity.verifiedUserLoginID + "'";
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){

                btnUpcoming.setText("View my Upcoming Shows");
                findViewById(R.id.upcoming).setVisibility(View.VISIBLE);

                currentView = (TextView) findViewById(R.id.myshows);
                currentView.append("Band: " + rs.getString("BandName") + "\n");
                currentView.append("Location: " + rs.getString("VAddress") + "\n");
                currentView.append("Show Date: " + rs.getString("ShowDate") + "\n");
                currentView.append("Show Time: " + rs.getString("ShowTime") + "\n");
                currentView.append("_______________________________________________\n\n");

                while(rs.next()){
                    currentView.append("Band: " + rs.getString("BandName") + "\n");
                    currentView.append("Location: " + rs.getString("VAddress") + "\n");
                    currentView.append("Show Date: " + rs.getString("ShowDate") + "\n");
                    currentView.append("Show Time: " + rs.getString("ShowTime") + "\n");
                    currentView.append("_______________________________________________\n\n");
                }
                currentView.append("MAYBE YOU SHOULD BE OPEN AND SELL SOME DRINKS PROBABLY.");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        if(btnUpcoming.getVisibility() == View.VISIBLE){
            btnUpcoming.setOnClickListener(new  View.OnClickListener(){
                @Override
                public void onClick(View v){
                    ShowUpcomingShows();
                }
            });
        }

        currentView = (TextView) findViewById(R.id.venue_name);
        currentView.setText("Hello, ");
        query = "select VenueName from USERS where UserName = '" + LogInScreenActivity.verifiedUserLoginID + "'";
        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                result = rs.getString(rs.findColumn("VenueName"));
                currentView.append(result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        btnCreateShow = (Button) findViewById(R.id.createShowButton);
        btnCreateShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createShowAction();

            }
        });

        btnEditProfile = (Button) findViewById(R.id.editProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editProfileAction();

            }
        });
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
                        Intent intent = new Intent(VenueActivity.this, LoginCreateAccountActivity.class);
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

    protected void  ShowUpcomingShows(){
        TextView upShow = (TextView) findViewById(R.id.myshows);

        if(upShow.getVisibility() == View.VISIBLE){
            upShow.setVisibility(View.GONE);
            btnUpcoming.setText("View my Upcoming Shows");
        }else{

            upShow.setVisibility(View.VISIBLE);
            btnUpcoming.setText("Hide my upcoming shows");
        }
    }

    protected void createShowAction(){

        Intent i = new Intent(VenueActivity.this, VenueCreateShowActivity.class);
        startActivity(i);
    }

    protected void editProfileAction(){

        Intent i = new Intent(VenueActivity.this, VenueEditProfile.class);
        startActivity(i);
    }
}
