/**
 * I,Vance Morgan,000384251 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 */
package morgan.mohawk.ca;

import java.util.ArrayList;

/**
 * This class represents a list of books returned by the Google API
 */
public class Books {
    public String   kind; // kind of books
    public int      totalItems; // total items in list
    public ArrayList<Items> items; //the items that the book contains
}
