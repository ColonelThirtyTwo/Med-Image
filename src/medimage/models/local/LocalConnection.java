
package medimage.models.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import medimage.models.Connection;
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
    
}
