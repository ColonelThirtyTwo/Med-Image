
package medimage.models;

import java.util.List;

/**
 * Represents a study, which is a collection of images.
 * @author col32
 */
public abstract class Study {
    
    /**
     * Gets the name of the study.
     * @return study name.
     */
    public abstract String getName();
    
    /**
     * Gets the list of images that this study contains.
     * @return images.
     */
    public abstract List<Image> getImages();
    
}
