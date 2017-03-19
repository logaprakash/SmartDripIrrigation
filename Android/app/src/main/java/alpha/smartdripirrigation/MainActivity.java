package alpha.smartdripirrigation;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog dialog;
    TextView seg1,seg2;
    String temp1="",temp2="";
    Button refresh,goNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        seg1 = (TextView)findViewById(R.id.value1);
        seg2 = (TextView)findViewById(R.id.value2);
        refresh = (Button) findViewById(R.id.refresh);
        goNow = (Button) findViewById(R.id.goNow);
        dialog = ProgressDialog.show(MainActivity.this, "",
                "Getting live feeds...", true);
        new getFeeds().execute("seg1");
        new getFeeds().execute("seg2");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(MainActivity.this, "",
                        "Getting live feeds...", true);
                new getFeeds().execute("seg1");
                new getFeeds().execute("seg2");
            }
        });

        goNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(MainActivity.this, "",
                        "Turning on rover", true);
                new changeMode().execute("on");
            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.liveFeed) {

        } else if (id == R.id.simulate) {

            Intent i = new Intent(MainActivity.this, simulate.class);
            startActivity(i);
        }
        else if(id == R.id.logOut){
            Intent i = new Intent(MainActivity.this, Login.class);
            startActivity(i);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void msgdialog(String title,String msg){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
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

    public class getFeeds extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

            try
            {
                HttpClient httpclient = new DefaultHttpClient();

                HttpGet getRequest = new HttpGet("http://cyberknights.in/api/sdi/getBlobContent.php?name=sample&segment="+strings[0]);
                getRequest.setHeader("Content-Type", "application/json");
                HttpResponse response = httpclient.execute(getRequest);
                String responseString = EntityUtils.toString(response.getEntity());

                if(strings[0].equals("seg1"))
                    temp1 = responseString;
                else
                    temp2 = responseString;

                return strings[0];
            }
            catch (Exception e)
            {
                // Output the stack trace.
                e.printStackTrace();
            }
            return "false" ;
        }

        protected void onPostExecute(String dec) {

            seg1.setText("VALUE: " +temp1);
            seg2.setText("VALUE: " +temp2);
            if(!temp1.equals("")&& !temp2.equals(""))
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
