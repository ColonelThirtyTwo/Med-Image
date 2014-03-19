
package medimage;

import java.io.Serializable;
import medimage.models.Study;
import medimage.views.improvedimageview.ImageView;

/**
 * Represents a user action that can be applied or undone.
 * Commands are only applicable when viewing studies.
 * @author col32
 */
public abstract class Command implements Serializable {
    
    /**
     * Applies the command's effects.
     * @param view The ImageView that the program is currently displaying.
     * @param study The study that the program is currently displaying.
     */
    public abstract void apply(ImageView view, Study study);
    
    /**
     * Undoes the command's effects.
     * @param view The ImageView that the program is currently displaying.
     * @param study The study that the program is currently displaying.
     */
    public abstract void undo(ImageView view, Study study);
}
