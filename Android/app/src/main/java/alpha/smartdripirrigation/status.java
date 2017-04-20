package alpha.smartdripirrigation;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class status extends AppCompatActivity {

    ProgressDialog dialog;
    Button seg1,seg2;
    TextView stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        seg1 = (Button)findViewById(R.id.seg1btn);
        seg2 = (Button)findViewById(R.id.seg2btn);
        stats = (TextView)findViewById(R.id.result);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);

        seg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(status.this, "",
                        "Getting segment-1 status...", true);
                new DownloadImageTask((ImageView) findViewById(R.id.imgview))
                        .execute("http://smartdripirrigation.blob.core.windows.net/sample/crop1.jpg");
            }
        });

        seg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(status.this, "",
                        "Getting segment-2 status...", true);
                new DownloadImageTask((ImageView) findViewById(R.id.imgview))
                        .execute("http://smartdripirrigation.blob.core.windows.net/sample/crop2.jpg");
            }
        });


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
                String temp ="";
                int r = 0 ,g = 0 ;
                int height = result.getHeight();
                int width = result.getWidth();
                int length = width * height;
                int[] pixels = new int[length];
                result.getPixels(pixels, 0, width, 0, 0, width, height);
                for (int i = 0; i < pixels.length; i++) {
                        if ((pixels[i] & 0x00FFFFFF) < 10)
                            count++;
                }
                if(count>30)
                {
                     temp = "Critical";
                     r = 255;
                }
                else
                {
                    temp = "Perfect";
                    g = 255;
                }
                final SpannableStringBuilder sb = new SpannableStringBuilder("Status: "+ temp);
                final ForegroundColorSpan fcs = new ForegroundColorSpan(Color.rgb(r, g, 0));
                final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
                sb.setSpan(fcs, 7, 8+temp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.setSpan(bss, 7, 8+temp.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

                stats.setText(sb);
                Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_LONG).show();
            }
            catch (Exception e){ Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();}
            bmImage.setImageBitmap(result);
            dialog.dismiss();
        }
    }
}
