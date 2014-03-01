
package medimage.views;

import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import medimage.ImageIterator;
import medimage.models.Connection;
import medimage.models.DisplayState;
import medimage.models.Image;
import medimage.models.Study;

/**
 * Superclass for image views.
 * @author col32
 */
public abstract class ImageView extends JFrame {
    protected Connection conn;
    protected Study study;
    protected ImageIterator iterator;

    public ImageView() {
    }

    /**
     * Updates the UI to view a list of images and makes the frame visible.
     * @param conn Connection of the study
     * @param study Study to view images from
     */
    public void viewImages(Connection conn, Study study) {
        this.viewImages(conn, study, 0);
    }

    /**
     * Updates the UI to view a list of images and makes the frame visible.
     * @param conn Connection of the study
     * @param study Study to view images from
     * @param index Index of image to view.
     */
    public void viewImages(Connection conn, Study study, int index) {
        this.setVisible(true);
        this.conn = conn;
        this.study = study;
        this.iterator = createIterator(study.getImages(), index);
        this.updateImageUI();
        this.pack();
    }
    
    /**
     * Returns an array of all the image containers to populate with images.
     * @return 
     */
    protected abstract JLabel[] getImageContainers();
    
    protected abstract ImageIterator createIterator(List<Image> images, int index);
    
    /**
     * Updates the UI with the current contents of the image iterator.
     */
    protected void updateImageUI() {
        JLabel[] labels = getImageContainers();
        Image[] imgs = this.iterator.getImages();
        for(int i=0; i<labels.length; i++) {
            if(imgs[i] == null)
                labels[i].setIcon(null);
            else
                labels[i].setIcon(new ImageIcon(imgs[i].getImageData()));
            labels[i].setText("");
        }
    }

    /**
     * Creates a display state from the currently viewed image.
     * @return Current display state.
     */
    protected abstract DisplayState getDisplayState();
    
}
