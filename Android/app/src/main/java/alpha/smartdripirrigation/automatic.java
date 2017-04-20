package alpha.smartdripirrigation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TimePicker;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class automatic extends AppCompatActivity {

    ProgressDialog dialog;
    Button setAutomatic;
    TimePicker tpicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        tpicker = (TimePicker) findViewById(R.id.timePicker);
        setAutomatic = (Button) findViewById(R.id.set_automatic);

        setAutomatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(automatic.this, "",
                        "Setting rover for automatic mode ...", true);
                new update().execute("switch","automatic","0");
            }
        });

    }
    public void msgdialog(String title,String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(automatic.this).create();
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
    public class update extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            try
            {
                HttpClient httpclient = new DefaultHttpClient();

                HttpGet getRequest = new HttpGet("http://cyberknights.in/api/sdi/updateBlob.php?name=sample&segment="+strings[0]+"&content="+strings[1]);
                getRequest.setHeader("Content-Type", "application/json");
                HttpResponse response = httpclient.execute(getRequest);
                String responseString = EntityUtils.toString(response.getEntity());
                return strings[2];
            }
            catch (Exception e)
            {
                // Output the stack trace.
                e.printStackTrace();
            }
            return "false" ;
        }

        protected void onPostExecute(String dec) {
            if(dec=="1"){
                dialog.dismiss();
                msgdialog("MODE Changed","Rover is in action");
            }
            else {
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                String temp = dateFormat.format(date) +" "+String.valueOf(tpicker.getCurrentHour())+" "+(String.valueOf(tpicker.getCurrentMinute()));
                new update().execute("time",temp, "1");
            }
        }
    }
}
