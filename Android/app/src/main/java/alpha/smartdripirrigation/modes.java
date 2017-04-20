package alpha.smartdripirrigation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class modes extends AppCompatActivity {

    TextView currentMode;
    ProgressDialog dialog;
    Button manual,automatic,simulate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modes);

        manual = (Button)findViewById(R.id.manualModeBtn);
        automatic = (Button)findViewById(R.id.automaticModeBtn);
        simulate = (Button) findViewById(R.id.simulateModeBtn);
        currentMode = (TextView) findViewById(R.id.current_mode);
        dialog = ProgressDialog.show(modes.this, "",
                "Getting current mode", true);
        new getMode().execute();
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(modes.this, "",
                        "Turning on rover", true);
                new changeMode().execute("on");
            }
        });

        automatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(modes.this, automatic.class);
                startActivity(i);
            }
        });

        simulate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(modes.this, "",
                        "Making rover ready for simulation", true);
                new changeMode().execute("simulate");
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void msgdialog(String title,String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(modes.this).create();
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

    public class getMode extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            try
            {
                HttpClient httpclient = new DefaultHttpClient();

                HttpGet getRequest = new HttpGet("http://cyberknights.in/api/sdi/getBlobContent.php?name=sample&segment=switch");
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

                if(!dec.equals("false"))
                {
                    if(dec.equals("automatic"))
                        currentMode.append("Automatic mode");
                    else if(dec.equals("simulate"))
                        currentMode.append("Simulate mode");
                    else
                        currentMode.append("Manual mode");
                }


                dialog.dismiss();

        }
    }


    public class changeMode extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            try
            {
                HttpClient httpclient = new DefaultHttpClient();

                HttpGet getRequest = new HttpGet("http://cyberknights.in/api/sdi/updateBlob.php?name=sample&segment=switch&content="+strings[0]);
                getRequest.setHeader("Content-Type", "application/json");
                HttpResponse response = httpclient.execute(getRequest);
                String responseString = EntityUtils.toString(response.getEntity());
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
            msgdialog("MODE Changed","Rover is in action");
        }
    }
}
