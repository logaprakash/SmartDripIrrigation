package alpha.smartdripirrigation;



import android.content.BroadcastReceiver;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;


public class simulate extends AppCompatActivity {

    ImageButton up,down,left,right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate);
        WifiManager wifiManager=(WifiManager)getSystemService(WIFI_SERVICE);
        //Enable Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        wifiManager.setWifiEnabled(true);
        WifiConfiguration wifiConfig = new WifiConfiguration();

        wifiConfig.SSID = String.format("\"%s\"", "hotspot");
        wifiConfig.preSharedKey = String.format("\"%s\"", "12345678");


        int netId = wifiManager.addNetwork(wifiConfig);
        wifiManager.disconnect();
        wifiManager.enableNetwork(netId, true);
        wifiManager.reconnect();
        up = (ImageButton) findViewById(R.id.forward);
        down = (ImageButton) findViewById(R.id.backward);
        left = (ImageButton) findViewById(R.id.left);
        right = (ImageButton) findViewById(R.id.right);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new addemptyTask().execute("1");
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new addemptyTask().execute("2");
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new addemptyTask().execute("3");
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new addemptyTask().execute("4");
            }
        });

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

    private class addemptyTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String token="";
            try {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress("192.168.0.237", 80), 5000);
                DataOutputStream DataOut = new DataOutputStream(socket.getOutputStream());
                DataOut.writeBytes(strings[0]);
                DataOut.flush();
                socket.close();
                token="success";
            } catch (MalformedURLException e1) {
                token = e1.toString();
            } catch (IOException e) {
                token = e.toString();
            }
            return token ;
        }

        protected void onPostExecute(String token) {

        }
    }

}


