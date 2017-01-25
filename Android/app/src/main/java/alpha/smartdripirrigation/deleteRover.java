package alpha.smartdripirrigation;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class deleteRover extends AppCompatActivity {

    private EditText ip;
    private Button clearAllBtn,deleteRoverBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_rover);

        clearAllBtn = (Button)findViewById(R.id.clearAllBtn);
        deleteRoverBtn = (Button)findViewById(R.id.deleteRoverBtn);
        ip = (EditText)findViewById(R.id.deleteIP);

        ipFilter();

        //Enable Back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        clearAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decisionAlert(0);
            }
        });

        deleteRoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decisionAlert(1);
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

    //DECISION ALERT BEFORE DELETING ( 0- CLEAR ALL AND 1 - DELETE ROVER )
    private void decisionAlert(final int value){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(deleteRover.this);
        String temp_title ,temp_msg;

        if(value==1){
            temp_title = getString(R.string.delete_rover_decision_title_rover);
            temp_msg = getString(R.string.delete_rover_decision_msg_rover);
        }
        else{
            temp_title = getString(R.string.delete_rover_dialog_title_fail);
            temp_msg = getString(R.string.delete_rover_message_fail);
        }

        builder.setMessage(String.valueOf(temp_msg))
                .setTitle(String.valueOf(temp_title));
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                 if(value==1){
                     alert(rovers.deleteRover(ip.getText().toString(),getApplicationContext()));
                 }
                else {
                     alert(rovers.deleteFile(getApplicationContext()));
                 }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //Dialog close
            }
        });

        android.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //ALERT DIALOG
    private void alert(final int value){

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(deleteRover.this);
        String temp_title ,temp_msg;
        if(value==1){
            temp_title = getString(R.string.delete_rover_dialog_title_success);
            temp_msg = getString(R.string.delete_rover_message_success);
        }
        else if(value==-1){
            temp_title = getString(R.string.delete_rover_dialog_title_clear);
            temp_msg = getString(R.string.delete_rover_dialog_msg_clear);
        }
        else{
            temp_title = getString(R.string.delete_rover_dialog_title_fail);
            temp_msg = getString(R.string.delete_rover_message_fail);
        }

        builder.setMessage(String.valueOf(temp_msg))
                .setTitle(String.valueOf(temp_title));
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
