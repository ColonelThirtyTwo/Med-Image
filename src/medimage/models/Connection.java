
package medimage.models;

import java.util.List;

/**
 * Represents a data source, which is either a local filesystem or a remote
 * imaging device.
 * @author col32
 */
public abstract class Connection {
    
    /**
     * Gets the studies that this data source knows about.
     * @return List of studies.
     */
    public abstract List<Study> getStudies();
    
    /**
     * Copies a study through the connection.
     * @param newName Name of the destination study to create.
     * @param study The source study.
     */
    public abstract void copyStudyInto(String newName, Study study);
    
    @Override
    public String toString() {
        return "Local Connection";
    }
}
