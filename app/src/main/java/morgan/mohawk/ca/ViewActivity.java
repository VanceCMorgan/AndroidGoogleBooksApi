/**
 * I,Vance Morgan,000384251 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 */
package morgan.mohawk.ca;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    //define output displays
    TextView authorView;
    TextView descriptionView;
    TextView publisherView;
    ImageView imgView;

    /**
     * This method is called when the View Activity is started.
     * It receives values from the supplied intent and constructs
     * an attractive output
     * @param savedInstanceState saved instance of the view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        setTitle(R.string.ProjectName);

        //Initializing the displays
        authorView = findViewById(R.id.authorDisplay);
        descriptionView = findViewById(R.id.descriptionDisplay);
        publisherView = findViewById(R.id.publisherInfoDisplay);
        imgView = findViewById(R.id.imageView);

        //set description view to be scrollable
        descriptionView.setMovementMethod(new ScrollingMovementMethod());

        //get the supplied intent and the supplied values
        Intent myIntent = getIntent();
        String title = myIntent.getStringExtra("Title");
        ArrayList<String> author = myIntent.getStringArrayListExtra("Author");
        String publisher = myIntent.getStringExtra("Publisher");
        String publishedDate = myIntent.getStringExtra("Date");
        String description = myIntent.getStringExtra("Description");
        String Url = myIntent.getStringExtra("PicUrl");

        //build a single string of authors from the array list
        String authors = ""; // initialize blank string
        if(author!=null) {
            for (int i = 0; i < author.size(); i++) {
                //if the author is the las one we don't want to add comma at the end
                if (i == author.size() + 1) {
                    authors += "" + author.get(i);
                } else {
                    authors += "" + author.get(i) + ",";
                }
            }
            //if no authors set the output to display "null"
        }else{
            authors = "null";
        }

        //set the output for the other fields
        setTitle("" + title);
        authorView.setText(getString(R.string.Authors) + getString(R.string.nextLine) + authors);
        descriptionView.setText(getString(R.string.Description) + getString(R.string.nextLine)  + description);
        publisherView.setText(getString(R.string.PublisherInfo) + getString(R.string.nextLine)  + publisher + getString(R.string.nextLine)  + publishedDate);

        //only try to download the picture if there is a supplied thumbnail
        if(Url!=null){
            new DownloadImageTask().execute(Url);
        }else{
            Toast.makeText(this, "No Image Available", Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * This class is responsible for downloading the thumbnail images asynchronously
     * After downloading it sets the image view to be our thumbnail
     */
    public class DownloadImageTask extends AsyncTask<String,Void, Bitmap> {

        /**
         *This method downloads the image
         * @param strings strings passed as arguments to this method. in this case the url
         * @return the bitmap so that it can be added to the image view
         */
        @Override
        protected Bitmap doInBackground(String... strings) {

            Bitmap bmp = null;

            //try to download the image from URL string argument
            try{
                URL url = new URL(strings[0]);
                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                //on error, display the stack trace
            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //return the bitmap form of the image
            return bmp;
        }

        /**
         * This method sets the image view to display the downloaded bitmap
         * @param bitmap the image to be displayed in bitmap form
         */
        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            super.onPostExecute(bitmap);

            //set the image
            imgView.setImageBitmap(bitmap);
        }
        }
}
