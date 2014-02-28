
package medimage;

import java.io.FileNotFoundException;
import java.io.IOException;
import medimage.views.ConnectionsView;
import medimage.views.QuadImageView;
import medimage.views.SingleImageView;
import medimage.views.StudiesView;

/**
 * Class that holds the main method.
 * @author col32
 */
public class MedImage {
    
    private static ConnectionsView connectionsView;
    private static StudiesView studiesView;
    private static SingleImageView singleImageView;
    private static QuadImageView quadImageView;

    /**
     * Gets the Connections JPanel
     * @return panel
     */
    public static ConnectionsView getConnectionsView() {
        return connectionsView;
    }
    
    /**
     * Gets the Studies JPanel.
     * @return panel
     */
    public static StudiesView getStudiesView() {
        return studiesView;
    }
    
    /**
     * Gets the Single image viewer JPanel.
     * @return panel
     */
    public static SingleImageView getSingleImageView() {
        return singleImageView;
    }
    
    /**
     * Gets the 2x2 Image viewer JPanel.
     * @return panel.
     */
    public static QuadImageView getQuadImageView() {
        return quadImageView;
    }
    
    /**
     * Main method
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
        // Creates all the views, but hide all but the connections view.
        connectionsView = new ConnectionsView();
        studiesView = new StudiesView();
        singleImageView = new SingleImageView();
        quadImageView = new QuadImageView();
        connectionsView.setVisible(true);
        studiesView.setVisible(false);
        singleImageView.setVisible(false);
        quadImageView.setVisible(false);
        
        /*
        DisplayState testState = new DisplayState(DisplayState.States.SINGLE_IMAGE, 3);
        testState.serialize("test.ser");
        DisplayState newState = (DisplayState) DisplayState.deSerialize("test.ser");
        System.out.println("DisplayState: " + newState.getCurrState() + " --  ImageIndex: " + newState.getImageIndex());
        */
    }
    
}
