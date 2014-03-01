
package medimage.views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import medimage.ImageIterator;
import medimage.MedImage;
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
     * Asks the user to save the display state, if needed.
     * @return True if user clicked 'yes' or 'no', or if the state hasn't changed.
     * False if the user clicked 'cancel' or closed the dialog box.
     */
    private boolean promptSaveState() {
        if(this.getDisplayState().equals(study.getDisplayState())) // Don't warn if nothing to save.
            return true;

        //Custom button text
        Object[] options = {"Yes",
                            "No",
                            "Cancel"};
        int n = JOptionPane.showOptionDialog(ImageView.this,
            "Save Display State?",
            "MedImage",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]);

        if(n == 0) { // "Yes", save display state
            study.saveDisplayState(getDisplayState());
        }
        
        // If user clicked yes or no, follow through with action. Otherwise, if
        // user clicked cancel or closed dialog, cancel action.
        return n != 2 && n != JOptionPane.CLOSED_OPTION;
    }
    
    /**
     * Sets the close handler to prompt the user to save state before closing.
     * Call last in the constructor.
     */
    protected final void setOnCloseHandler() {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if(promptSaveState())
                    System.exit(0);
            }
        });
    }
    
    /**
     * Executes the back button. Prompts the user to save the state, then
     * go to studies list.
     */
    protected void doBackButton() {                                           
        if(promptSaveState())
        {
            this.setVisible(false);
            MedImage.getStudiesView().viewStudies(conn);
        }
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
