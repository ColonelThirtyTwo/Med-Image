
package medimage.models.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import medimage.models.Connection;
import medimage.models.Study;

/**
 * A 'connection' to the local filesystem.
 * @author col32
 */
public class LocalConnection extends Connection {
    /**
     * Creates a local connection object.
     */
    public LocalConnection() {
    }
    
    /**
     * Loads studies.
     * @return List of studies.
     */
    @Override
    public List<Study> getStudies() {
        File folder = new File(".");
        File[] studyFolders = folder.listFiles();
        
        List<Study> list = new ArrayList<Study>();
        
        for(File f : studyFolders)
            list.add(new LocalStudy(f));
        
        return list;
    }
    
}
