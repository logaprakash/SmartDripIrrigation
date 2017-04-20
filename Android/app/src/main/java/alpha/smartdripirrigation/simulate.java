package alpha.smartdripirrigation;



import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;


public class simulate extends AppCompatActivity {
    int count = 0;
    ImageButton up, down, left, right;
    Button fetch,end,nextSeg;
    StringBuilder path;
    WifiManager wifiManager;
    ProgressDialog dialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    //private Socket Client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate);
        path  = new StringBuilder();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        up = (ImageButton) findViewById(R.id.forward);
        down = (ImageButton) findViewById(R.id.backward);
        left = (ImageButton) findViewById(R.id.left);
        right = (ImageButton) findViewById(R.id.right);
        fetch = (Button) findViewById(R.id.fetch);
        end = (Button) findViewById(R.id.endSimulate);
        nextSeg = (Button) findViewById(R.id.nextSegment);


        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = "1";
                path.append(value);
                new addemptyTask().execute(value);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = "2";
                path.append(value);
                new addemptyTask().execute(value);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = "3";
                path.append(value);
                new addemptyTask().execute(value);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = "4";
                path.append(value);
                new addemptyTask().execute(value);
            }
        });
        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = "5";
                path.append(value);
                new addemptyTask().execute(value);
            }
        });

        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog alertDialog = new AlertDialog.Builder(simulate.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Do you want to end simulation?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "YES",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String value = "6";
                                new addemptyTask().execute(value);
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "NO",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

        nextSeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                msgdialog("Next Segment","Path has been ended for segment - "+String.valueOf(count));
                path.append("7");
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        dialog = ProgressDialog.show(simulate.this, "",
                "Making rover ready for simulation...", true);
        wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        WifiConfiguration wifiConfig = new WifiConfiguration();

        wifiConfig.SSID = String.format("\"%s\"", "sdi");
        wifiConfig.preSharedKey = String.format("\"%s\"", "");


        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();

        if(wifiManager.isWifiEnabled())
            dialog.dismiss();

    }


    //Back button function
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("simulate Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void msgdialog(String title,String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(simulate.this).create();
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
    private class addemptyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {


                try{
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpGet getRequest = new HttpGet("http://192.168.4.1:80/?value=" + strings[0]); // create an HTTP GET object
                    HttpResponse response = httpclient.execute(getRequest);

                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                /* Client = new Socket("192.168.4.1", 80 );
                    OutputStreamWriter printwriter = new OutputStreamWriter(Client.getOutputStream(), "ISO-8859-1");
                    printwriter.write(strings[0]);
                    printwriter.flush();
                    printwriter.close();
                    Client.close();*/

            return strings[0];

        }

        protected void onPostExecute(String token) {
            if(token.equals("6")){
                wifiManager.disconnect();
                wifiManager.setWifiEnabled(false);
                Intent i = new Intent(simulate.this, uploadPathOnline.class);
                i.putExtra("path", path.toString());
                startActivity(i);
            }
        }
    }




}


