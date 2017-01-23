package alpha.smartdripirrigation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class viewRover extends AppCompatActivity {

    String[] roverNames = {
            "Rover-1",
            "Rover-2",
            "Rover-3",
            "Rover-4",
            "Rover-5",
            "Rover-6",
            "Rover-7",
            "Rover-8"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rover);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                R.layout.rover_list, roverNames);

        ListView listView = (ListView) findViewById(R.id.roverList);
        listView.setAdapter(adapter);
    }
}
