
package medimage.models;

import java.io.Serializable;
import java.io.*;

/**
 * A plain-data object used to serialize what view and image to open a study
 * at.
 *
 * @author jcdesimp
 */
public class DisplayState implements Serializable {

    /**
     * SerializationUID
     */
    private static final long serialVersionUID = 7526471155622776147L;

    /**
     * Enumeration for various DisplayStates
     */
    public enum States {

        QUAD_IMAGE, SINGLE_IMAGE
    }

    /**
     * The current imageView state.
     *
     * @serial
     */
    private final States currState;

    /**
     * Index of image being displayed
     *
     * @serial
     */
    private final int imageIndex;

    /**
     * Constructor for DisplayState
     *
     * @param currState Current display mode
     * @param imgIndex Current image index.
     */
    public DisplayState(States currState, int imgIndex) {
        this.currState = currState;
        this.imageIndex = imgIndex;

    }

    /**
     * Getter for currState.
     *
     * @return
     */
    public States getCurrState() {
        return currState;
    }

    /**
     * Getter for imageIndex.
     *
     * @return
     */
    public int getImageIndex() {
        return imageIndex;
    }
    
    /**
     * Compares display states.
     * @param other Object to compare it to.
     * @return True if equal.
     */
    public boolean equals(DisplayState other) {
        return other.getCurrState() == this.getCurrState() &&
                this.getImageIndex() == other.getImageIndex();
    }
    
    /**
     * Writes the object to a file.
     *
     * @param outFile Path to the file to write to
     * @throws IOException
     */
    public void serialize(String outFile)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(outFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
    }

    /**
     * Loads a DisplayState from a file serialized with the serialize method
     *
     * @param serilizedObject Path to the file to read from.
     * @return The deserialized object.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deSerialize(String serilizedObject)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(serilizedObject);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
    }

    @Override
    public String toString() {
        return "DisplayState{" + "currState=" + currState + ", imageIndex=" + imageIndex + '}';
    }
}
