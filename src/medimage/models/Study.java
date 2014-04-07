/**
 * File: Study.java
 * Description: A model for a study object.
 */

package medimage.models;

import java.util.List;

/**
 * Represents a study, which is a collection of images.
 * @author col32
 */
public abstract class Study {
    
    /**
     * Gets the name of the study.
     * @return 
     */
    public abstract String getName();
    
    /**
     * Gets the list of images that this study contains. Images should be
     * ordered.
     * @return 
     */
    public abstract List<Image> getImages();
    
    /**
     * Gets the list of sub-studies that this study contains. Studies should be
     * ordered.
     * @return 
     */
    public abstract List<Study> getStudies();
    
    /**
     * Gets the saved display state.
     * @return 
     */
    public abstract DisplayState getDisplayState();
    
    /**
     * Saves the display state.
     * @param state Display state to save.
     */
    public abstract void saveDisplayState(DisplayState state);
    
    @Override
    public String toString() {
        return getName();
    }
}
