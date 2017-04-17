package com.example.terry.bandsnearme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        search = (EditText)findViewById(R.id.search);
        visitProfile = (Button)findViewById(R.id.goToBandOrVenueProfile);
        notFound = (TextView)findViewById(R.id.notFoundTxt);
        SearchNotFound = (TextView)findViewById(R.id.searchNotFound);
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
