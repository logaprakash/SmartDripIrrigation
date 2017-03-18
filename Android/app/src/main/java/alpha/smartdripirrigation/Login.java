package alpha.smartdripirrigation;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;


import java.util.ArrayList;


public class Login extends AppCompatActivity {

    String name="",pass="";
    EditText roverName,password;
    Button loginBtn;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        roverName = (EditText) findViewById(R.id.rover_name);
        password = (EditText)findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isNetworkAvailable()) {
                    name = roverName.getText().toString();
                    pass = password.getText().toString();

                    if (!name.equals("") && !pass.equals("")) {
                        dialog = ProgressDialog.show(Login.this, "",
                                "Logging in. Please wait...", true);
                        new RoverLogin().execute();
                    }
                }
                else
                    msgdialog("NO INTERNET","Check your internet connection!!!");
            }
        });

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void msgdialog(String title,String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(Login.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public class RoverLogin extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

                    try
                {
                    HttpClient httpclient = new DefaultHttpClient();

                    HttpGet getRequest = new HttpGet("http://cyberknights.in/api/sdi/check.php?id="+name); // create an HTTP GET object
                    getRequest.setHeader("Content-Type", "application/json");
                    HttpResponse response = httpclient.execute(getRequest);
                    String responseString = EntityUtils.toString(response.getEntity());
                    return responseString;
                }
                catch (Exception e)
                {
                    // Output the stack trace.
                    e.printStackTrace();
                }
            return "false" ;
        }

        protected void onPostExecute(String dec) {
            dialog.dismiss();
            if (dec != null) {
                if (!dec.equals("false")){

                    try {

                        JSONArray jsonArray = new JSONArray(dec);
                        if(jsonArray != null) {
                            ArrayList<String> list = new ArrayList<String>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                list.add(jsonArray.getString(i));

                            }
                            if (list.get(1).equals(pass)) {

                                Intent i = new Intent(Login.this, MainActivity.class);
                                startActivity(i);
                            } else {
                                msgdialog("Wrong password", "Try to enter correct password");
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        msgdialog("Wrong credential", "Try to enter correct rover name ");
                    }

                } else {
                    msgdialog("Wrong Rover Name","No such rover is available in our database");
                }
            }
        }
    }
}
