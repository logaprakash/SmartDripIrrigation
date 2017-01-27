package alpha.smartdripirrigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class roverDetails extends AppCompatActivity {
    TextView name , ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rover_details);
        name =  (TextView)findViewById(R.id.nameDetails);
        ip =  (TextView)findViewById(R.id.ipDetails);

        //Enable Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        String temp = bundle.getString("message");
        String[] separated = temp.split("\\n");
        name.setText(separated[0]);
        ip.setText(separated[1]);
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
