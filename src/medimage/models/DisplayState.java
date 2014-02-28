
package medimage.models;

import java.io.Serializable;
import java.io.*;

/**
 * Creates the persistent save state.
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
     * @param currState
     * @param imgIndex
     */
    public DisplayState(States currState, int imgIndex) {
        this.currState = currState;
        this.imageIndex = imgIndex;

    }

    /**
     * Getter for currState
     *
     * @return
     */
    public States getCurrState() {
        return currState;
    }

    /**
     * getter for imageIndex
     *
     * @return
     */
    public int getImageIndex() {
        return imageIndex;
    }

    public boolean equals(DisplayState other) {
        return other.getCurrState() == this.getCurrState() &&
                this.getImageIndex() == other.getImageIndex();
    }
    
    /**
     * Method to to serialize object to specified file
     *
     * @param outFile
     * @throws IOException
     */
    public void serialize(String outFile)
            throws IOException {
        FileOutputStream fos = new FileOutputStream(outFile);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
    }

    /**
     * Method to retrieve a stored DisplayState
     *
     * @param serilizedObject
     * @return
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
