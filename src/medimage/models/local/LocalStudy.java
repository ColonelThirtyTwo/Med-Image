
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
        File[] imageFiles = directory.listFiles();
        Arrays.sort(imageFiles);
        
        List<Image> list = new ArrayList<Image>();
        
        for(File f : imageFiles)
            if(!f.getName().equals(".displaystate"))
                list.add(new LocalImage(f));
        
        return list;
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
            Logger.getLogger(LocalStudy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
