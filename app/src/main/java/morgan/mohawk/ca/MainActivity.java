/**
 * I,Vance Morgan,000384251 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 */
package morgan.mohawk.ca;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is tha main activity it is launched on app startup
 * It is responsible for taking user input and display a list of matching books to the screen
 */
public class MainActivity extends AppCompatActivity {

    //define inputs
    EditText titleInput;
    EditText authorInput;

    /**
     * This method is called when our main activity is created
     * it initializes the input fields
     * @param savedInstanceState saved instance of the view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set app title
        setTitle("Project - Vance Morgan 000384251");

        //initialize inputs
        titleInput = findViewById(R.id.titleInput);
        authorInput = findViewById(R.id.authorInput);

    }

    /**
     * This method is called when the search button is clicked
     * It creates the search URL by taking the user inputs and determining the filters
     * @param view the view that is currently being used
     */
    public void startDownload(View view) {
        String title = titleInput.getText().toString();
        String author = authorInput.getText().toString();

        // Build call to Webservice
        String uri = "https://www.googleapis.com/books/v1/volumes";

        //if no user input, notify user that search cannot be completed with no input
        if(title == "" && author == ""){
            Toast.makeText(this, "Please enter a search term!", Toast.LENGTH_LONG).show();
        }else{
            DownloadAsyncTask dl = new DownloadAsyncTask(this);
            //Determin if searching by Author,Title or Author and Title
            if(author.equals("")){
                // Filter - numbers (Year) no quotes on value, strings have quotes on value
                uri += "?q=intitle:" + title;
            }else if(title.equals("")){
                uri += "?q=inauthor:" + author;
            }else if (!title.equals("") && !title.equals("")) {
                uri += "?q=intitle:" + title;
                uri += "+inauthor:" + author;
            }

            //execute the download task asynchronously and pass it our constructed URL
            dl.execute(uri);

            //Clear text inputs
            titleInput.setText("");
            authorInput.setText("");
        }

    }
}
