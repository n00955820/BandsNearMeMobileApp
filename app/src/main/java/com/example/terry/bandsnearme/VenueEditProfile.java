package com.example.terry.bandsnearme;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
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

public class VenueEditProfile extends AppCompatActivity {


    ConnectionClass connectionClass;
    String query = "";
    Statement stmt = null;
    ResultSet rs = null;
    Connection con = null;
    Button btnEdtName = null;
    Button btnEdtAdd = null;
    Button btnEdtLoc = null;
    Button btnEdtDesc = null;
    Button btnSubName = null;
    Button btnSubAdd = null;
    Button btnSubLoc = null;
    Button btnSubDesc = null;
    String newName = "";
    String newDesc = "";
    String newLoc = "";
    String newAdd = "";
    String result = "";
    TextView currentView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        connectionClass = new ConnectionClass();
        con = connectionClass.CONN();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_edit_profile);

        query = "select * from VENUE where VUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                result = rs.getString("VenueName");
                currentView = (TextView)findViewById(R.id.displayName);
                currentView.setText("Name: " + result);
                result = rs.getString("VenueLoc");
                currentView = (TextView)findViewById(R.id.displayLoc);
                currentView.setText("Location: " + result);
                result = rs.getString("VDesc");
                currentView = (TextView)findViewById(R.id.displayDesc);
                currentView.setText("Description: "+result);
                result = rs.getString("VAddress");
                currentView = (TextView)findViewById(R.id.displayAddress);
                currentView.setText("Address: " + result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        btnEdtDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescShow();
            }
        });

        btnEdtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAddresseShow();
            }
        });

        btnEdtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNameShow();
            }
        });
        btnEdtLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLocationShow();
            }
        });
        btnSubDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescAction();
            }
        });

        btnSubAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAddressAction();
            }
        });

        btnSubName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNameAction();
            }
        });

        btnSubLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editLocationAction();
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
                        Intent intent = new Intent(VenueEditProfile.this, LoginCreateAccountActivity.class);
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

    protected void editDescShow(){
        findViewById(R.id.nameDisplay).setVisibility(View.GONE);
        findViewById(R.id.descEdit).setVisibility(View.VISIBLE);
        findViewById(R.id.locDisplay).setVisibility(View.GONE);
        findViewById(R.id.descDisplay).setVisibility(View.GONE);
        findViewById(R.id.addressDisplay).setVisibility(View.GONE);
    }

    protected void editLocationShow(){
        findViewById(R.id.nameDisplay).setVisibility(View.GONE);
        findViewById(R.id.locEdit).setVisibility(View.VISIBLE);
        findViewById(R.id.locDisplay).setVisibility(View.GONE);
        findViewById(R.id.descDisplay).setVisibility(View.GONE);
        findViewById(R.id.addressDisplay).setVisibility(View.GONE);
    }

    protected void editNameShow(){
        findViewById(R.id.nameDisplay).setVisibility(View.GONE);
        findViewById(R.id.nameEdit).setVisibility(View.VISIBLE);
        findViewById(R.id.locDisplay).setVisibility(View.GONE);
        findViewById(R.id.descDisplay).setVisibility(View.GONE);
        findViewById(R.id.addressDisplay).setVisibility(View.GONE);
    }

    protected void editAddresseShow(){
        findViewById(R.id.nameDisplay).setVisibility(View.GONE);
        findViewById(R.id.addressEdit).setVisibility(View.VISIBLE);
        findViewById(R.id.locDisplay).setVisibility(View.GONE);
        findViewById(R.id.descDisplay).setVisibility(View.GONE);
        findViewById(R.id.addressDisplay).setVisibility(View.GONE);
    }

    protected void editDescAction(){
        currentView = (EditText)findViewById(R.id.editDesc);
        newDesc = currentView.getText().toString();

        query = "UPDATE VENUE set VDesc = '"+ newDesc+"' where VUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);


        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "select VDesc from VENUE where VUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                currentView = (TextView)findViewById(R.id.displayDesc);
                result = rs.getString("VDesc");
                currentView.setText("Description: "+result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        findViewById(R.id.descEdit).setVisibility(View.GONE);
        findViewById(R.id.addressDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.nameDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.descDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.locDisplay).setVisibility(View.VISIBLE);
    }
    protected void editNameAction(){
        currentView = (EditText)findViewById(R.id.editName);
        newName = currentView.getText().toString();

        query = "UPDATE VENUE set VenueName = '"+ newName+"' where VUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);


        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "select VenueName from VENUE where VUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                currentView = (TextView)findViewById(R.id.displayName);
                result = rs.getString("VenueName");
                currentView.setText("Name: "+result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        findViewById(R.id.nameEdit).setVisibility(View.GONE);
        findViewById(R.id.addressDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.nameDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.descDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.locDisplay).setVisibility(View.VISIBLE);
    }

    protected void editLocationAction(){
        currentView = (EditText)findViewById(R.id.editLoc);
        newLoc = currentView.getText().toString();

        query = "UPDATE VENUE set VenueLoc = '"+ newLoc+"' where VUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);


        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "select VenueLoc from VENUE where VUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                currentView = (TextView)findViewById(R.id.displayLoc);
                result = rs.getString("VenueLoc");
                currentView.setText("Location: "+result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        findViewById(R.id.locEdit).setVisibility(View.GONE);
        findViewById(R.id.addressDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.nameDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.descDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.locDisplay).setVisibility(View.VISIBLE);
    }

    protected void editAddressAction(){
        currentView = (EditText)findViewById(R.id.editAddress);
        newAdd = currentView.getText().toString();

        query = "UPDATE VENUE set VAddress = '"+ newAdd+"' where VUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);


        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "select VAddress from VENUE where VUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                currentView = (TextView)findViewById(R.id.displayAddress);
                result = rs.getString("VAddress");
                currentView.setText("Address: "+result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        findViewById(R.id.addressEdit).setVisibility(View.GONE);
        findViewById(R.id.addressDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.nameDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.descDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.locDisplay).setVisibility(View.VISIBLE);
    }
}

