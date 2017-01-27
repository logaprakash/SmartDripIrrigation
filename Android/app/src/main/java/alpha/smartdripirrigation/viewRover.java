package alpha.smartdripirrigation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class viewRover extends AppCompatActivity {

    ArrayList<String> rovers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rover);

        //Read temp file
        String filename = "rovers.ini";
        File file = new File(getApplicationContext().getFilesDir(), filename);
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] separated = line.split("\\$");
                String temp = "Rover name: "+separated[0]+"\n"+"IP:"+separated[1];
                rovers.add(temp);
            }
        }
        catch (IOException e) {
            //You'll need to add proper error handling here
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.rover_list, rovers);

        ListView listView = (ListView) findViewById(R.id.roverList);
        listView.setAdapter(adapter);

        //Enable Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // selected item
                String selected = ((TextView) view.findViewById(R.id.roverName)).getText().toString();
                Intent i = new Intent(viewRover.this,roverDetails.class);
                i.putExtra("message", selected);
                startActivity(i);
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
