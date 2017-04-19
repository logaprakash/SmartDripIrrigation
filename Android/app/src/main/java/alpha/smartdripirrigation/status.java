package alpha.smartdripirrigation;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;

public class status extends AppCompatActivity {
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);


        ActionBar bar = getSupportActionBar();

        bar.setDisplayHomeAsUpEnabled(true);

        dialog = ProgressDialog.show(status.this, "",
                "Getting segment status...", true);
        new DownloadImageTask((ImageView) findViewById(R.id.imgview))
                .execute("http://smartdripirrigation.blob.core.windows.net/sample/crop1.jpg");

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            try {
                int count = 0;
                int height = result.getHeight();
                int width = result.getWidth();
                int length = width * height;
                int[] pixels = new int[length];
                result.getPixels(pixels, 0, width, 0, 0, width, height);
                for (int i = 0; i < pixels.length; i++) {
                        if ((pixels[i] & 0x00FFFFFF) < 10)
                            count++;
                }
                Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_LONG).show();
            }
            catch (Exception e){ Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();}
            bmImage.setImageBitmap(result);
            dialog.dismiss();
        }
    }
}
