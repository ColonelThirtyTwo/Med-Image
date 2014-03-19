
package medimage;

import medimage.views.ConnectionsView;
import medimage.views.StudiesView;
import medimage.views.improvedimageview.ImageView;

/**
 * Class that holds the main method.
 * This class also holds all of the views. The program switches between views
 * by hiding the previous one and making visible the next one.
 * @author col32
 */
public class MedImage {
    
    private static ConnectionsView connectionsView;
    private static StudiesView studiesView;
    private static ImageView imageView;

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
     * Gets the Image View JPanel
     * @return panel
     */
    public static ImageView getImageView() {
        return imageView;
    }
    
    /**
     * Main method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Creates all the views, but hide all but the connections view.
        connectionsView = new ConnectionsView();
        studiesView = new StudiesView();
        imageView = new ImageView();
        connectionsView.setVisible(true);
        studiesView.setVisible(false);
        imageView.setVisible(false);
    }
}
