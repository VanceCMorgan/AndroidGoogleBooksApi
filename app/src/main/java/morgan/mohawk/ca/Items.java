/**
 * I,Vance Morgan,000384251 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 */
package morgan.mohawk.ca;

/**
 * This class represents the items contained by a book
 * The volumeInfo object contains the majority of usable information about
 * the book
 */
public class Items {
    public String   kind;
    public String   id;
    public String   etag;
    public VolumeInfo   volumeInfo;

    /**
     * Override of the base toString method so that we can display a books details attractivley
     * @return string representation of a book
     */
    @Override public String toString() {
        return volumeInfo.title + "\n" + volumeInfo.authors;
    }
}
