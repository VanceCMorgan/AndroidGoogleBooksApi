/**
 * I,Vance Morgan,000384251 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 */
package morgan.mohawk.ca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.google.gson.Gson;

/**
 * This class is responsible for downloading the list of books from the google api
 * It allows the user to search on author and title and then displays relevant information.
 */
public class DownloadAsyncTask extends AsyncTask<String, Void, String> implements View.OnClickListener, AdapterView.OnItemClickListener {

    private Activity myActivity;
    Books books;

    /**
     * sets the activity for the download task
     * @param inActivity the current activity calling this task
     */
    public DownloadAsyncTask(Activity inActivity) {
        myActivity = inActivity;
    }

    /**
     * Handles the get request from google api and stores the results to be deserialized
     * @param params the url to be downloaded from
     * @return the response from the api containing all the matched books
     */
    @Override
    protected String doInBackground(String... params) {

        Log.d("log", "Starting Background Task");
        String results = "";


        //try to download response from API
        try {

            URL url = new URL(params[0]);           // was HttpGet

            // Open the Connection - GET is the default setRequestMethod
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Read the response
            int statusCode = conn.getResponseCode();
            Log.d("log", "Response Code: " + statusCode);

            //IF successful, add each line to results
            if (statusCode == 200) {
                InputStream inputStream = new BufferedInputStream(conn.getInputStream());

                BufferedReader bufferedReader =
                        new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    results += line;
                }
            }
            //catch errors
        } catch (IOException ex) {
        }

        //return the list of matched books
        return results;

    }

    /**
     * This method is called once the download has completed
     * It converts the result into books and displays them in a listview
     * @param result the result from the download method
     */
    protected void onPostExecute(String result) {

        //create new Gson object
        Gson gson = new Gson();

        ListView bookView = myActivity.findViewById(R.id.listView);
        // if result is empty it will be extremely short
        if(result.length() <= 43){
            Toast.makeText(myActivity, myActivity.getString(R.string.NoResults), Toast.LENGTH_LONG).show();
        }else{
            //store each object as a book class so we can access its properties
            books = gson.fromJson(result, Books.class);
            ArrayAdapter<Items> adapter = new ArrayAdapter<Items>(myActivity,android.R.layout.simple_list_item_1, books.items);

            // set the adapter and the on item clicked listener for our lisView
            bookView.setAdapter(adapter);
            bookView.setOnItemClickListener(this);
        }
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * This method handles what happens when a user clicks on an item in the lisk of books
     * it first stores all of that items information in an intent and then launches that intent
     * @param parent adapter view that list is from
     * @param view the current view that is being used
     * @param position the index of the item that was clicked
     * @param id id of item that was clicked
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(myActivity, ""+ books.items.get(position), Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(myActivity,ViewActivity.class);
        String Title = books.items.get(position).volumeInfo.title;
        String description = books.items.get(position).volumeInfo.description;
        ArrayList<String> author = books.items.get(position).volumeInfo.authors;
        String publisher = books.items.get(position).volumeInfo.publisher;
        String publishedDate = books.items.get(position).volumeInfo.publishedDate;
        //String imageLink ="http://books.google.com/books/content?id=szF_pLGmJTQC&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api";
        String imageLink = books.items.get(position).volumeInfo.imageLinks.thumbnail;

        //put our data into our intent befor starting the activity
        myIntent.putExtra("Title",Title);
        myIntent.putExtra("Description",description);
        myIntent.putExtra("Author",author);
        myIntent.putExtra("Publisher",publisher);
        myIntent.putExtra("Date",publishedDate);
        myIntent.putExtra("PicUrl",imageLink);

        //start the viewActivity activity
        myActivity.startActivity(myIntent);
    }
}