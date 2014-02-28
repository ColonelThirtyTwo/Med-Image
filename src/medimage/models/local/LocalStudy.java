
package medimage.models.local;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import medimage.models.Image;
import medimage.models.Study;

/**
 * Study on the local filesystem.
 * @author col32
 */
public class LocalStudy extends Study {
    
    private File file;
    
    /**
     * Creates a study.
     * @param f Directory of the study.
     */
    public LocalStudy(File f) {
        file = f;
    }
    
    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public List<Image> getImages() {
        File[] imageFiles = file.listFiles();
        
        List<Image> list = new ArrayList<Image>();
        
        for(File f : imageFiles)
            list.add(new LocalImage(f));
        
        return list;
    }
}
