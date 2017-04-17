package com.example.terry.bandsnearme;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class CreateAccountActivity extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText edtuserid,edtpass, edtpassconfirm;
    Button btnCreateAccount;
    ProgressBar pbbar;
    RadioGroup radioGroup;
    RadioButton radioButton;
    EditText venueNameTxt;
    EditText bandNameTxt;
    EditText emailTxt;
    public static String createUserAccountType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        connectionClass = new ConnectionClass();
        edtuserid = (EditText) findViewById(R.id.username);
        edtpass = (EditText) findViewById(R.id.password);
        venueNameTxt = (EditText)findViewById(R.id.venueName);
        venueNameTxt.setVisibility(View.GONE);
        bandNameTxt = (EditText)findViewById(R.id.bandName);
        bandNameTxt.setVisibility(View.GONE);
        edtpassconfirm = (EditText) findViewById(R.id.confimPassword);
        emailTxt = (EditText)findViewById(R.id.email);
        btnCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);
        pbbar = (ProgressBar) findViewById(R.id.pbbar2);
        pbbar.setVisibility(View.GONE);
        radioGroup = (RadioGroup)findViewById(R.id.accountTypeRadioGroup);

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoCreateAccount createNew = new DoCreateAccount();
                createNew.execute("");

            }
        });
    }

    public void radioButtonClick(View v){
        int radioButtonId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton)findViewById(radioButtonId);

        if(radioButton.getText().toString().equals("Individual User")){
            venueNameTxt.setVisibility(View.GONE);
            bandNameTxt.setVisibility(View.GONE);
            createUserAccountType = "Indiv";
        }else if(radioButton.getText().toString().equals("Band")){
            bandNameTxt.setVisibility(View.VISIBLE);
            venueNameTxt.setVisibility(View.GONE);
            createUserAccountType = "Band";
        }else if(radioButton.getText().toString().equals("Venue")){
            venueNameTxt.setVisibility(View.VISIBLE);
            bandNameTxt.setVisibility(View.GONE);
            createUserAccountType = "Venue";
        }
    }

    public class DoCreateAccount extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        boolean user = false;
        boolean band = false;
        boolean venue = false;


        String userid = edtuserid.getText().toString();
        String password = edtpass.getText().toString();
        String password2 = edtpassconfirm.getText().toString();
        String bandName = bandNameTxt.getText().toString();
        String venueName = venueNameTxt.getText().toString();
        String email = emailTxt.getText().toString();


        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(CreateAccountActivity.this,r, Toast.LENGTH_SHORT).show();

            if(isSuccess) {

                if(LogInScreenActivity.userType.equals("Band")){
                    //Intent i = new Intent(LogInScreenActivity.this, BandActivity.class);
                    //startActivity(i);
                }else if(LogInScreenActivity.userType.equals("Venue")){
                    //Intent i = new Intent(LogInScreenActivity.this, VenueActivity.class);
                    //startActivity(i);
                }else if(LogInScreenActivity.userType.equals("Indiv")){
                    //Intent i = new Intent(LogInScreenActivity.this, IndividualUserActivity.class);
                    //startActivity(i);
                }

                //finish();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            boolean userNameAlreadyExists = false;

            if(userid.trim().equals("")|| password.trim().equals("")) {
                z = "Please enter User Id and Password";
            }else if(!password.equals(password2)){
                z = "Passwords do not match";
            }
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        String query = "";



                            if(createUserAccountType.equals("Indiv")){
                                query = "insert into users values ('" +userid + "', '" + password + "', '" +
                                        createUserAccountType + "', '" + email + "', NULL, NULL, NULL)";
                            }else if(createUserAccountType.equals("Band")){
                                query = "insert into users values ('" +userid + "', '" + password + "', '" +
                                        createUserAccountType + "', '" + email + "', NULL, NULL, '"
                                        + bandName + "')";
                            }else if(createUserAccountType.equals("Venue")){
                                query = "insert into users values ('" +userid + "', '" + password + "', '" +
                                        createUserAccountType + "', '" + email + "', NULL, '" + venueName
                                        + "', NULL)";
                            }


                            Statement stmt = con.createStatement();
                            //ResultSet rs = stmt.executeQuery(query);
                            int i = stmt.executeUpdate(query);

                            if(i > 0){
                                LogInScreenActivity.verifiedUserLoginID = userid;
                                z = "Account Created!";
                            }else{
                                z = "There was an error in creating your account";
                            }






                    }
                }
                catch (Exception ex) {

                        z = "That username already exists!";

                }
            }
            return z;
        }
    }

}
