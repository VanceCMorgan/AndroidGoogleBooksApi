/**
 * I,Vance Morgan,000384251 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 */
package morgan.mohawk.ca;

import java.util.ArrayList;

/**
 * This class represents the information received from the api about a specific book
 */
public class VolumeInfo {
    public String   title; // the books title
    public ArrayList<String> authors; // the authors of the book
    public String   description; // the books description
    public String publisher; // the publishing company
    public String publishedDate; // the date of publish
    public imageLinks imageLinks; // the image links for the book
}


