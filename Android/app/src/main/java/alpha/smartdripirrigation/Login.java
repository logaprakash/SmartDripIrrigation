package alpha.smartdripirrigation;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Login extends Activity {

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
                name = roverName.getText().toString();
                pass = password.getText().toString();

                if(!name.equals("")  && !pass.equals("")) {
                    dialog = ProgressDialog.show(Login.this, "",
                            "Logging in. Please wait...", true);
                    new RoverLogin().execute();
                }
            }
        });

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
                if (dec != "false") {
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(dec);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        ArrayList<String> list = new ArrayList<String>();
                        for (int i = 0; i < jsonArray.length(); i++) {

                            list.add(jsonArray.getString(i));

                        }
                        if (list.get(1).equals(pass)) {

                            Intent i = new Intent(Login.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Invalid details", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "No such rover is available in our database", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
