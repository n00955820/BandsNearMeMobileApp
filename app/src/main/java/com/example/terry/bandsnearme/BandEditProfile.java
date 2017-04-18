package com.example.terry.bandsnearme;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.TestMethod;
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

public class BandEditProfile extends AppCompatActivity {

    ConnectionClass connectionClass;
    String query = "";
    Statement stmt = null;
    ResultSet rs = null;
    Connection con = null;
    Button btnEdtName = null;
    Button btnEdtGen = null;
    Button btnEdtDesc = null;
    Button btnSubName = null;
    Button btnSubGen = null;
    Button btnSubDesc = null;
    String newName = "";
    String newDesc = "";
    String newGen = "";
    String result = "";
    TextView currentView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        connectionClass = new ConnectionClass();
        con = connectionClass.CONN();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_edit_profile);

        query = "select * from BAND where BUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                result = rs.getString("BandName");
                currentView = (TextView)findViewById(R.id.displayName);
                currentView.setText("Name: " + result);
                result = rs.getString("Genre");
                currentView = (TextView)findViewById(R.id.displayGenre);
                currentView.setText("Genre: " + result);
                result = rs.getString("BDesc");
                currentView = (TextView)findViewById(R.id.displayDesc);
                currentView.setText("Description: "+ result);
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

        btnEdtGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editGenreShow();
            }
        });

        btnEdtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNameShow();
            }
        });

        btnSubDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDescAction();
            }
        });

        btnSubGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editGenreAction();
            }
        });

        btnSubName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNameAction();
            }
        });
    }

    protected void editDescShow(){
        findViewById(R.id.nameDisplay).setVisibility(View.GONE);
        findViewById(R.id.descEdit).setVisibility(View.VISIBLE);
        findViewById(R.id.genreDisplay).setVisibility(View.GONE);
        findViewById(R.id.descDisplay).setVisibility(View.GONE);
    }

    protected void editGenreShow(){
        findViewById(R.id.genreDisplay).setVisibility(View.GONE);
        findViewById(R.id.genreEdit).setVisibility(View.VISIBLE);
        findViewById(R.id.nameDisplay).setVisibility(View.GONE);
        findViewById(R.id.descDisplay).setVisibility(View.GONE);
    }

    protected void editNameShow(){
        findViewById(R.id.descDisplay).setVisibility(View.GONE);
        findViewById(R.id.nameEdit).setVisibility(View.VISIBLE);
        findViewById(R.id.nameDisplay).setVisibility(View.GONE);
        findViewById(R.id.genreDisplay).setVisibility(View.GONE);
    }

    protected void editDescAction(){
        currentView = (EditText)findViewById(R.id.editDesc);
        newDesc = currentView.getText().toString();

        query = "UPDATE BAND set BDesc = '"+ newDesc+"' where BUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);


        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "select BDesc from BAND where BUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                currentView = (TextView)findViewById(R.id.displayDesc);
                result = rs.getString("BDesc");
                currentView.setText("Description: "+result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        findViewById(R.id.descEdit).setVisibility(View.GONE);
        findViewById(R.id.genreDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.nameDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.descDisplay).setVisibility(View.VISIBLE);
    }

    protected void editGenreAction(){
        currentView = (EditText)findViewById(R.id.editGenre);
        newGen = currentView.getText().toString();

        query = "UPDATE BAND set Genre = '"+ newGen+"' where BUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "select Genre from BAND where BUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                currentView = (TextView)findViewById(R.id.displayGenre);
                result = rs.getString("Genre");
                currentView.setText("Genre: "+result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        findViewById(R.id.genreDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.nameDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.descDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.genreEdit).setVisibility(View.GONE);
    }

    protected void editNameAction(){
        currentView = (EditText)findViewById(R.id.editName);
        newName = currentView.getText().toString();

        query = "UPDATE BAND set BandName = '"+ newName+"' where BUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
        }catch (SQLException e){
            e.printStackTrace();
        }

        query = "select BandName from BAND where BUserName = '"+LogInScreenActivity.verifiedUserLoginID+"'";

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            if(rs.next()){
                currentView = (TextView)findViewById(R.id.displayName);
                result = rs.getString("BandName");
                currentView.setText("Name: "+result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        findViewById(R.id.genreDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.nameDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.descDisplay).setVisibility(View.VISIBLE);
        findViewById(R.id.nameEdit).setVisibility(View.GONE);
    }
}

