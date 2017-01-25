package alpha.smartdripirrigation;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


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
        ipFilter();

        //Enable Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Button function
        addRoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             alert(
                    rovers.addRover(roverName.getText().toString(),
                          ip.getText().toString(),
                          getApplicationContext())
            );
            }
        });

    }

    //BACK FUNCTIONS
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

    //IP TEXT BOX FILTERING
    private void ipFilter(){
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
    }

    //ALERT DIALOG
    private void alert(final int value){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(addRover.this);
        String temp_title ,temp_msg;
        if(value==1){
            temp_title = getString(R.string.add_rover_dialog_title_success);
            temp_msg = getString(R.string.dialog_message_add_rover_success);
        }
        else if(value == 0){
            temp_title = getString(R.string.add_rover_dialog_title_fail);
            temp_msg = getString(R.string.dialog_message_add_rover_both_fail);
        }
        else if(value == -1){
            temp_title = getString(R.string.add_rover_dialog_title_fail);
            temp_msg = getString(R.string.dialog_message_rover_name_fail);
        }
        else {
            temp_title = getString(R.string.add_rover_dialog_title_fail);
            temp_msg = getString(R.string.dialog_message_ip_fail);
        }
        builder.setMessage(temp_msg)
                .setTitle(temp_title);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
               if(value==1)
                   onBackPressed();
            }
        });
        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
