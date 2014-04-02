
package medimage.models;

import java.io.Serializable;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import medimage.Command;

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
     * List of commands to execute when loading the display state.
     * This consists only of commands that modify images.
     */
    private final List<Command> commands;

    /**
     * Constructor for DisplayState
     *
     * @param currState Current display mode
     * @param imgIndex Current image index.
     * @param commands List of commands to execute when loading the display state.
     */
    public DisplayState(States currState, int imgIndex, List<Command> commands) {
        this.currState = currState;
        this.imageIndex = imgIndex;
        this.commands = new ArrayList<Command>(commands);
    }

    /**
     * Getter for currState.
     *
     * @return the current state
     */
    public States getCurrState() {
        return currState;
    }

    /**
     * Getter for imageIndex.
     *
     * @return the index of the current image
     */
    public int getImageIndex() {
        return imageIndex;
    }

    /**
     * Gets the list of commands.
     * @return 
     */
    public List<Command> getCommands() {
        return Collections.unmodifiableList(commands);
    }
    
    /**
     * Compares display states.
     * @param other Object to compare it to.
     * @return True if equal.
     */
    public boolean equals(DisplayState other) {
        if(other == null) return false;
        return other.getCurrState() == this.getCurrState() &&
                this.getImageIndex() == other.getImageIndex() &&
                this.commands.equals(other.getCommands());
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
     * @param serializedObject Path to the file to read from.
     * @return The deserialized object.
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Object deSerialize(String serializedObject)
            throws FileNotFoundException, IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(serializedObject);
        ObjectInputStream ois = new ObjectInputStream(fis);
        return ois.readObject();
    }

    @Override
    public String toString() {
        return "DisplayState{" + "currState=" + currState + ", imageIndex=" + imageIndex + '}';
    }
}
