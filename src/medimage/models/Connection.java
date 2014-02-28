
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
     * @return list of studies.
     */
    public abstract List<Study> getStudies();
    
}
