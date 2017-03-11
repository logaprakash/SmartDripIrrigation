package alpha.smartdripirrigation;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

public class Login extends Activity {

    EditText roverName,password;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        roverName = (EditText) findViewById(R.id.rover_name);
        password = (EditText)findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
    private class login extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {

          return null ;
        }

        protected void onPostExecute(String token) {

        }
    }

}
