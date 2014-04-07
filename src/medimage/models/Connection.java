/**
 * File: Connection.java
 * Description: A model for a Connection abstract class.
 */

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
    
    /**
     * Gets the study to open when the application starts.
     * @return Study, or null if no default set.
     */
    public abstract Study getDefaultStudy();
    
    /**
     * Saves a study to open when the application starts.
     * @param study Study, or null to unset.
     */
    public abstract void saveDefaultStudy(Study study);
    
    @Override
    public String toString() {
        return "Local Connection";
    }
}
