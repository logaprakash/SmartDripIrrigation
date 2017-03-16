package alpha.smartdripirrigation;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;

import java.io.IOException;

public class Login extends Activity {

    String name,pass;
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
                name = roverName.getText().toString();
                pass = password.getText().toString();
                new RoverLogin().execute();

            }
        });

    }

    public class RoverLogin extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

                String storageConnectionString =
                        "DefaultEndpointsProtocol=http;" +
                                "AccountName=smartdripirrigation;" +
                                "AccountKey=wQFS5WHHisI2wZNaonXUtvyRajMQtrB8iYUIK16fxW+bO8COxEdU+ZQKuQOViqIpXgVigFBLvR+/ge1rnfOyKA==";
                try
                {
                    // Retrieve storage account from connection-string.
                    CloudStorageAccount storageAccount =
                            CloudStorageAccount.parse(storageConnectionString);
                    // Create the blob client.
                    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                    // Retrieve reference to a previously created container.
                    CloudBlobContainer container = blobClient.getContainerReference("password");

                    CloudBlockBlob blob = container.getBlockBlobReference(name);
                    String temp = blob.downloadText();
                    if(temp.equals(pass))
                        return "true";
                }
                catch (Exception e)
                {
                    // Output the stack trace.
                    e.printStackTrace();
                }
            return "false" ;
        }

        protected void onPostExecute(String dec) {
            if(dec=="true"){
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
            }
        }
    }
}
