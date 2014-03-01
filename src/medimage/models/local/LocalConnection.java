
package medimage.models.local;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import medimage.models.Connection;
import medimage.models.DisplayState;
import medimage.models.Image;
import medimage.models.Study;

/**
 * A 'connection' to the local filesystem. Allows access to studies in the
 * './studies/' folder relative to the current working directory.
 * @author col32
 */
public class LocalConnection extends Connection {
    /**
     * Creates a local connection object.
     */
    public LocalConnection() {
    }
    
    @Override
    public List<Study> getStudies() {
        File folder = new File("./studies/");
        File[] studyFolders = folder.listFiles();
        
        List<Study> list = new ArrayList<Study>();
        
        for(File f : studyFolders)
            if(f.isDirectory())
                list.add(new LocalStudy(f));
        
        return list;
    }

    @Override
    public void copyStudyInto(String newName, Study study) {
        File newStudyDir = new File("./studies/", newName);
        
        if(newStudyDir.exists()) {
            // Override eixsting study
            
            // Need to delete directory contents before deleting directory
            for(File f : newStudyDir.listFiles())
                f.delete();
            newStudyDir.delete(); // Delete directory
        }
        
        // Create new directory
        newStudyDir.mkdir();
        
        // Copy images.
        for(Image i : study.getImages()) {
            File f = new File(newStudyDir, i.getName());
            
            try {
                ImageIO.write(i.getImageData(), "jpeg", f);
            } catch (IOException ex) {
                // Ignore IO exceptions; can't do anything about them.
                Logger.getLogger(LocalConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // Copy display state
        
        DisplayState state = study.getDisplayState();
        if(state != null) {
            File displayStateFile = new File(newStudyDir, ".displaystate");
            try {
                state.serialize(displayStateFile.getPath());
            } catch (IOException ex) {
                // Ignore IO exceptions; can't do anything about them.
                Logger.getLogger(LocalConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
}
