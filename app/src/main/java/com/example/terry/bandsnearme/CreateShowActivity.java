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

public class CreateShowActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    String query = "";
    Statement stmt = null;
    ResultSet rs = null;
    TextView currentView = null;
    Connection con = null;
    Button btnSearch = null;
    Button btnSubmit = null;
    EditText venueSearch = null;
    TextView venueResult = null;
    String venueName = "";
    String ShowID = "";
    String VUserName = "";
    String SDesc = "";
    String VenueLoc = "";
    String ShowDate = "";
    String ShowTime = "";
    String BandName = "";
    String VAddress = "";
    EditText date = null;
    EditText time = null;
    EditText desc = null;
    String result = null;
    int index = 0;
    TextView status = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        connectionClass = new ConnectionClass();
        con = connectionClass.CONN();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_show);

        venueSearch = (EditText) findViewById(R.id.venueSearchShow);
        btnSearch = (Button) findViewById(R.id.venueSearchShowButton);
        venueResult = (TextView) findViewById(R.id.venueSearchShowResults);
        date = (EditText) findViewById(R.id.bandCreateDate);
        time = (EditText) findViewById(R.id.bandCreateTime);
        desc = (EditText) findViewById(R.id.bandCreateDescription);
        btnSubmit = (Button) findViewById(R.id.bandCreateSubmit);
        status = (TextView) findViewById(R.id.bandCreated);

        query = "select top 1 * from SHOW order by ShowID DESC";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                result = rs.getString("ShowID");
                result = result.substring(1);
                index = Integer.parseInt(result);
                index++;
                result = Integer.toString(index);
                ShowID = "S";
                for(int j = 0; j < 13-result.length(); j++){
                    ShowID += '0';
                }
                ShowID += result;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "select BandName from Users where UserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try{
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                BandName = rs.getString("BandName");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchVenueAction();

            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitShowAction();

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
                        Intent intent = new Intent(CreateShowActivity.this, LoginCreateAccountActivity.class);
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

    protected void searchVenueAction(){

        venueName = venueSearch.getText().toString();

        query = "select * from VENUE where VenueName = '" + venueName + "'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                VUserName = rs.getString("VUserName");
                VenueLoc = rs.getString("VenueLoc");
                VAddress = rs.getString("VAddress");

                venueResult.setText("Great! The venue was found, please enter the rest of the information. ");
                date.setVisibility(View.VISIBLE);
                time.setVisibility(View.VISIBLE);
                desc.setVisibility(View.VISIBLE);
                btnSubmit.setVisibility(View.VISIBLE);
                venueSearch.setVisibility(View.GONE);

            }else{
                venueResult.setText("Sorry, your search did not yeild any results. Please try again");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        venueResult.setVisibility(View.VISIBLE);



    }

    protected void submitShowAction(){

        SDesc = desc.getText().toString();
        ShowDate = date.getText().toString();
        ShowTime = time.getText().toString();



        query = "insert into SHOW (ShowID, BUserName, VUserName, SDesc, VenueLoc, ShowDate, ShowTime, BandName, VenueName, VAddress) VALUES ('"+ShowID+"', '"+LogInScreenActivity.verifiedUserLoginID+"', '"+
                VUserName+"', '"+SDesc+"', '"+VenueLoc+"', '"+ShowDate+"', '"+ShowTime+"', '"+BandName+"', '"+venueName+"', '"+VAddress+"')";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

        }catch (SQLException e){
            e.printStackTrace();
        }
        status.setVisibility(View.VISIBLE);
        status.setText("Thank you, your show has been added.");

        Intent i = new Intent(CreateShowActivity.this, BandActivity.class);
        startActivity(i);
    }
}
