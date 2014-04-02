/**
 * File: LocalStudy.java
 * Description: An implementation of Study for local Studies
 */
package medimage.models.local;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import medimage.models.DisplayState;
import medimage.models.Image;
import medimage.models.Study;

/**
 * Study on the local filesystem.
 * @author col32
 */
public class LocalStudy extends Study {
    
    private final File directory;
    private List<Image> images;
    private List<Study> studies;
    
    /**
     * Creates a study.
     * @param f Directory of the study.
     */
    public LocalStudy(File f) {
        directory = f;
    }
    
    @Override
    public String getName() {
        return directory.getName();
    }

    @Override
    public List<Image> getImages() {
        if(images != null)
            return images;
        
        File[] imageFiles = directory.listFiles();
        Arrays.sort(imageFiles);
        
        images = new ArrayList<Image>();
        
        for(File f : imageFiles)
            if(f.isFile() && !f.getName().equals(".displaystate"))
                images.add(new LocalImage(f));
        
        return images;
    }

    @Override
    public DisplayState getDisplayState() {
        File stateFile = new File(directory, ".displaystate");
        
        if(stateFile.exists())
            try {
                return (DisplayState)DisplayState.deSerialize(stateFile.getPath());
            } catch (IOException ex) {
                Logger.getLogger(LocalStudy.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LocalStudy.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        else
            return null;
    }

    @Override
    public void saveDisplayState(DisplayState state) {
        File f = new File(directory, ".displaystate");
        try {
            state.serialize(f.getPath());
        } catch (IOException ex) {
            // Can't do anything about it
            Logger.getLogger(LocalStudy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Study> getStudies() {
        if(studies != null)
            return studies;
        
        File[] imageFiles = directory.listFiles();
        Arrays.sort(imageFiles);
        
        studies = new ArrayList<Study>();
        
        for(File f : imageFiles)
            if(f.isDirectory())
                studies.add(new LocalStudy(f));
        
        return studies;
    }
}
