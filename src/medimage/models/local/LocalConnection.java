/**
 * File: LocalConnection.java
 * Description: An implementation of Connection for Local Connections
 */

package medimage.models.local;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystems;
import java.nio.file.Files;
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

    @Override
    public Study getDefaultStudy() {
        // See if file exists
        File storage = new File("./studies/.defaultstudy");
        if(!storage.exists())
            return null; // No .defaultstudy file
        
        String name;
        try {
            // Read name from file
            name = new String(Files.readAllBytes(FileSystems.getDefault().getPath(storage.getPath())));
        } catch (IOException ex) {
            // Can't do anything about this.
            Logger.getLogger(LocalConnection.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        // Get study if it exists
        File studyFile = new File("./studies/", name);
        if(studyFile.exists())
            return new LocalStudy(studyFile);
        
        // Doesn't exist
        Logger.getLogger(LocalConnection.class.getName()).log(Level.WARNING,
                ".defaultstudy refers to nonexistant study");
        return null;
    }

    @Override
    public void saveDefaultStudy(Study study) {
        if(study == null) {
            // If unsetting study, delete file
            File f = new File("./studies/.defaultstudy");
            f.delete();
            return;
        }
        
        PrintWriter out = null;
        try {
            // Write study name to file
            out = new PrintWriter("./studies/.defaultstudy");
            out.print(study.getName());
        } catch (FileNotFoundException ex) {
            // Can't do anything about this.
            Logger.getLogger(LocalConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            // Close file.
            if(out != null)
                out.close();
        }
    }
    
}
