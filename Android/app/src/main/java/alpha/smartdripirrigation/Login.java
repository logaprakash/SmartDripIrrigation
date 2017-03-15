package alpha.smartdripirrigation;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;

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
                //new RoverLogin().execute();
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);

            }
        });

    }

    private class RoverLogin extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

                String storageConnectionString =
                        "DefaultEndpointsProtocol=http;" +
                                "AccountName=smartdripirrigation;" +
                                "AccountKey=wQFS5WHHisI2wZNaonXUtvyRajMQtrB8iYUIK16fxW+bO8COxEdU+ZQKuQOViqIpXgVigFBLvR+/ge1rnfOyKA==";
                try
                {
                    // Retrieve storage account from connection-string.
                    CloudStorageAccount storageAccount =
                            CloudStorageAccount.parse(storageConnectionString);

                    // Create the table client.
                    CloudTableClient tableClient = storageAccount.createCloudTableClient();

                    // Create the table if it doesn't exist.
                    String tableName = "people";
                    CloudTable cloudTable = tableClient.getTableReference(tableName);
                    cloudTable.createIfNotExists();
                }
                catch (Exception e)
                {
                    // Output the stack trace.
                    e.printStackTrace();
                }
            return null ;
        }

        protected void onPostExecute() {
            Intent i = new Intent(Login.this, MainActivity.class);
            startActivity(i);
        }
    }
}
