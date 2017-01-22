package alpha.smartdripirrigation;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class addRover extends AppCompatActivity {
    private EditText ip,roverName;
    private Button addRoverBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rover);
        ip = (EditText)findViewById(R.id.ip);
        roverName = (EditText)findViewById(R.id.roverName);
        addRoverBtn = (Button)findViewById(R.id.addRoverBtn);

        //Adding filter for ip
        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       android.text.Spanned dest, int dstart, int dend) {
                if (end > start) {
                    String destTxt = dest.toString();
                    String resultingTxt = destTxt.substring(0, dstart)
                            + source.subSequence(start, end)
                            + destTxt.substring(dend);
                    if (!resultingTxt
                            .matches("^\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3}(\\.(\\d{1,3})?)?)?)?)?)?")) {
                        return "";
                    } else {
                        String[] splits = resultingTxt.split("\\.");
                        for (int i = 0; i < splits.length; i++) {
                            if (Integer.valueOf(splits[i]) > 255) {
                                return "";
                            }
                        }
                    }
                }
                return null;
            }

        };
        ip.setFilters(filters);

        //Enable Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Button function
        addRoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*String filename = "rovers";
                String string = roverName.getText().toString() + "$" +ip.getText().toString() + "\n"  ;
                FileOutputStream outputStream;

                try {
                    outputStream = openFileOutput(filename, MODE_APPEND);
                    outputStream.write(string.getBytes());
                    outputStream.close();
                    File file = new File(getApplicationContext().getFilesDir(), filename);

                    //Read text from file
                    StringBuilder text = new StringBuilder();

                    try {
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;

                        while ((line = br.readLine()) != null) {
                            String[] separated = line.split("\\$");
                            Log.e("Name:",separated[0]);
                            Log.e("IP:",separated[1]);
                        }
                        br.close();
                    }
                    catch (IOException e) {
                        //You'll need to add proper error handling here
                    }
*/
                    AlertDialog.Builder builder = new AlertDialog.Builder(addRover.this);


                    builder.setMessage(R.string.add_rover_dialog_message)
                            .setTitle(R.string.add_rover_dialog_title);

                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            onBackPressed();
                        }
                    });


                AlertDialog alertDialog = builder.create();
                alertDialog.show();
/*
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
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
