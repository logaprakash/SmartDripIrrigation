package alpha.smartdripirrigation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class uploadPathOnline extends AppCompatActivity {

    ProgressDialog pdialog;
    Button upload;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_path_online);
        upload = (Button) findViewById(R.id.upload);
        tv = (TextView) findViewById(R.id.sim_path);
        Bundle bundle = getIntent().getExtras();
        final String path = bundle.getString("path");
        tv.setText("Simulated Path: "+ path);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pdialog = ProgressDialog.show(uploadPathOnline.this, "",
                        "Uploading path...", true);
                new storePath().execute(path);
            }
        });
    }

    public void msgdialog(String title,String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(uploadPathOnline.this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                            Intent i = new Intent(uploadPathOnline.this, MainActivity.class);
                            startActivity(i);

                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    public class storePath extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            try
            {
                HttpClient httpclient = new DefaultHttpClient();

                HttpGet getRequest = new HttpGet("http://cyberknights.in/api/sdi/updateBlob.php?name=sample&segment=path&content="+strings[0]);
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
            pdialog.dismiss();
            msgdialog("SUCCESS","Your simulated path has been stored");
        }
    }

}
