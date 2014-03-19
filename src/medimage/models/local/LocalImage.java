
package medimage.models.local;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageInputStream;
import medimage.models.Image;

/**
 * Image on the local filesystem.
 * @author col32
 */
public class LocalImage extends Image {
    
    private final String name;
    private BufferedImage image;
    
    /**
     * Loads a local image.
     * @param f File of the image.
     */
    public LocalImage(File f) {
        name = f.getName();
        
        //Handling of ACR file types
        if(name.endsWith(".acr")){
            FileImageInputStream imageFile = null;
            try {
                imageFile = new FileImageInputStream(f);
                imageFile.seek(0x2000);
            }
            catch (FileNotFoundException e) {
                System.err.print("Error opening file: ");
                System.err.println(e.getMessage());
                System.exit(2);
            }
            catch (IOException e) {
                System.err.print("IO error on file: ");
                System.err.println(e.getMessage());
                System.exit(2);
            }

            int sliceWidth = 256;
            int sliceHeight = 256;

            BufferedImage sliceBuffer = 
                new BufferedImage( sliceWidth,sliceHeight,
                                   BufferedImage.TYPE_USHORT_GRAY );

            for ( int i = 0; i < sliceBuffer.getHeight(); i++ ) {
                for ( int j = 0; j < sliceBuffer.getWidth(); j++ ) {

                    int pixelHigh = 0;
                    int pixelLow = 0;
                    int pixel;

                    try {
                        pixelHigh = imageFile.read();
                        pixelLow = imageFile.read();
                        pixel = pixelHigh << 4 | pixelLow >> 4;

                        sliceBuffer.setRGB( j, i,
                                          pixel << 16 | pixel << 8 | pixel);

                    }
                    catch (IOException e) {
                        System.err.print("IO error readin byte: ");
                        System.err.println(e.getMessage());
                        System.exit(2);
                    }


                }
            }
            image = sliceBuffer;
        } else {
        
            try {
                image = ImageIO.read(f);
            } catch (IOException ex) {
                Logger.getLogger(LocalImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public BufferedImage getImageData() {
        return image;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
}
