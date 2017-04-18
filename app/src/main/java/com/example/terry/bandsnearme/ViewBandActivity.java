package com.example.terry.bandsnearme;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Rating;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;

public class ViewBandActivity extends AppCompatActivity {

    TextView bandName;
    TextView bandGenre;
    TextView bandDesc;
    TextView bandRating;
    TextView upcomingShows;
    Button rateBand;
    Button favoriteBand;
    RatingBar rateBar;
    String bandUsername;
    String theBandsName;
    String venueShowUsername;
    ConnectionClass connectionClass;
    float rating = 0.0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_band);

        final String dbBandName = IndividualUserActivity.verifiedSearchBandOrVenueName;
        theBandsName = dbBandName;
        bandName = (TextView)findViewById(R.id.viewBandName);
        bandGenre = (TextView)findViewById(R.id.viewBandGenre);
        bandDesc = (TextView)findViewById(R.id.viewBandDescription);
        bandRating = (TextView)findViewById(R.id.viewBandRating);
        upcomingShows = (TextView)findViewById(R.id.UpcomingShows);
        rateBand = (Button)findViewById(R.id.rateBandButton);
        rateBar = (RatingBar)findViewById(R.id.ratingBar);
        favoriteBand = (Button)findViewById(R.id.favoriteButton);
        connectionClass = new ConnectionClass();

        rateBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = v;
                rateBand.setText("Submit");

                rateBand.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        try {
                            Connection con = connectionClass.CONN();
                            if (con == null) {

                            } else {

                                String selectBandQuery = "select * from BAND where BandName = '" + dbBandName + "'";

                                Statement selectBandQueryStmt = con.createStatement();
                                ResultSet bandQueryRs = selectBandQueryStmt.executeQuery(selectBandQuery);

                                if (bandQueryRs.next()) {

                                    float currentRating = bandQueryRs.getFloat(bandQueryRs.findColumn("BRating"));

                                    int numOfRatings = bandQueryRs.getInt(bandQueryRs.findColumn("NumOfRatings")) + 1;


                                    rating = (rating + currentRating) / numOfRatings;

                                    BigDecimal bd = new BigDecimal(Float.toString(rating));
                                    bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
                                    rating = bd.floatValue();



                                    String numRatingsQuery = "UPDATE BAND SET NumOfRatings = " +Integer.toString(numOfRatings) + " where BandName = '" + dbBandName + "'";



                                    int i = selectBandQueryStmt.executeUpdate(numRatingsQuery);


                                    String bRatingQuery = "UPDATE BAND SET BRating = " + Float.toString(rating) + " where BandName = '" + dbBandName + "'";
                                    Statement bRatingStmt = con.createStatement();

                                    bRatingStmt.executeUpdate(bRatingQuery);



                                    rateBar.setVisibility(View.GONE);
                                    rateBand.setEnabled(false);
                                }
                            }

                        }catch (Exception e){
                            Log.e("BandsNearMe", "exception", e);
                        }


                    }
                });


            }
        });




        bandName.setText(dbBandName);


        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                bandGenre.setText("Error in connection with SQL server");
            } else {

                String query = "select * from BAND where BandName = '" + dbBandName + "'";

                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                if (rs.next()) {
                    bandGenre.setText("Genre: " + rs.getString(rs.findColumn("Genre")));
                    bandDesc.setText("Description: " + rs.getString(rs.findColumn("BDesc")));
                    bandRating.setText("Rating: " + rs.getString(rs.findColumn("BRating")));
                    bandUsername = rs.getString((rs.findColumn("BUserName")));
                }

                String query2 = "select * from show where BUserName = '" + bandUsername + "'";
                Statement stmt2 = con.createStatement();
                ResultSet rs2 = stmt2.executeQuery(query2);

                while(rs2.next()){
                    upcomingShows.append("Venue: " + rs2.getString("VenueName") + "\n");
                    upcomingShows.append("Location: " + rs2.getString("VenueLoc") + "\n");
                    upcomingShows.append("Show Date: " + rs2.getString("ShowDate") + "\n");
                    upcomingShows.append("Show Time: " + rs2.getString("ShowTime") + "\n\n");
                }
            }

        }catch (Exception e){

        }
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
                        Intent intent = new Intent(ViewBandActivity.this, LoginCreateAccountActivity.class);
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

    public void onRateBandClick(View view){
        rateBar.setVisibility(View.VISIBLE);
    }

    public void clickFavoriteButton(View view){

        try {

            Connection con = connectionClass.CONN();
            String query1 = "select * from favorite where Username = '" + LogInScreenActivity.verifiedUserLoginID + "' AND BandName = '" + theBandsName + "'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query1);

            if(rs.next()){
                favoriteBand.setText("Band already in favorites");
                favoriteBand.setEnabled(false);
            }else{

                String query2 = "insert into favorite values ('" + LogInScreenActivity.verifiedUserLoginID + "', 'Band', '" + bandUsername + "', NULL, '" +
                        theBandsName + "', NULL)";
                Statement stmt2 = con.createStatement();
                stmt2.executeUpdate(query2);
                favoriteBand.setText("Added to favorites");
                favoriteBand.setEnabled(false);
            }

        }catch (Exception e){

        }

    }

}
