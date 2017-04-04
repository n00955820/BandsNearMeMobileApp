package com.example.terry.bandsnearme;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LogInScreenActivity extends AppCompatActivity {

    ConnectionClass connectionClass;
    EditText edtuserid,edtpass;
    Button btnlogin;
    ProgressBar pbbar;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);

        connectionClass = new ConnectionClass();
        edtuserid = (EditText) findViewById(R.id.username);
        edtpass = (EditText) findViewById(R.id.password);
        btnlogin = (Button) findViewById(R.id.buttonLogIn);
        pbbar = (ProgressBar) findViewById(R.id.pbbar);
        pbbar.setVisibility(View.GONE);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DoLogin doLogin = new DoLogin();
                doLogin.execute("");

            }
        });

    }

    public class DoLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;


        String userid = edtuserid.getText().toString();
        String password = edtpass.getText().toString();


        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);
            Toast.makeText(LogInScreenActivity.this,r, Toast.LENGTH_SHORT).show();

            if(isSuccess) {

                if(userType.equals("Band")){
                    Intent i = new Intent(LogInScreenActivity.this, BandActivity.class);
                    startActivity(i);
                }else if(userType.equals("Venue")){
                    Intent i = new Intent(LogInScreenActivity.this, VenueActivity.class);
                    startActivity(i);
                }else if(userType.equals("Indiv")){
                    Intent i = new Intent(LogInScreenActivity.this, IndividualUserActivity.class);
                    startActivity(i);
                }

                finish();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            if(userid.trim().equals("")|| password.trim().equals(""))
                z = "Please enter User Id and Password";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    } else {
                        //String query = "select * from users where UserName= 'Band451' and UserPW= 'Band1PW'";
                        String query = "select * from users where UserName='" + userid + "' and UserPW='" + password + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if(rs.next())
                        {

                            userType = rs.getString("UserType");

                            z = "Login successful";
                            isSuccess=true;
                        }
                        else
                        {
                            z = "Incorrect Username or Password";
                            isSuccess = false;
                        }

                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions";
                }
            }
            return z;
        }
    }

}
