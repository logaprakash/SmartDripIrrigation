package alpha.smartdripirrigation;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class roverDetails extends AppCompatActivity {
    TextView name , ip;
    Button activeRoverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_details);
        name =  (TextView)findViewById(R.id.nameDetails);
        ip =  (TextView)findViewById(R.id.ipDetails);
        activeRoverBtn = (Button)findViewById(R.id.activeRoverBtn);
        //Enable Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String temp = bundle.getString("message");
        final String[] separated1 = temp.split("\\n");
        name.setText(separated1[0]);
        ip.setText(separated1[1]);

        activeRoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] separated = separated1[0].split(":");
                String value = separated[1].trim();
                SharedPreferences sharedPref = getApplication().getSharedPreferences(value,0);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.active_rover), value);
                editor.commit();
                String temp2 = sharedPref.getString(getString(R.string.active_rover), "none");
                Toast.makeText(getApplicationContext(),temp2,Toast.LENGTH_LONG).show();
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

}
