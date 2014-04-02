/**
 * File: ReconstructionView.java
 * Description: The view for showing the reconstructed images
 */
package medimage.views;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.swing.ImageIcon;
import medimage.models.Image;
import medimage.models.Study;

/**
 * View for reconstructions.
 * @author col32
 */
public class ReconstructionView extends javax.swing.JDialog {
    
    /**
     * Clamps a value between two integers.
     * @param v
     * @param lower
     * @param higher
     * @return 
     */
    private static int clamp(int v, int lower, int higher) {
        return Math.min(Math.max(v, lower), higher);
    }
    
    // Colors
    private final int COLOR_RED   = 0xffff0000;
    private final int COLOR_GREEN = 0xff00ff00;
    private final int COLOR_BLUE  = 0xff0000ff;
    
    /**
     * Study that is being viewed.
     */
    private final Study study;
    
    /**
     * Image data.
     * Coordinates are data[x][y][z] with x going to the right, y going up,
     * and z going out of the xy plane.
     */
    private final int[][][] data;
    
    /**
     * Currently viewed slices.
     */
    private int sliceX, sliceY, sliceZ;
    
    /**
     * Creates new form ReconstructionView
     * @param parent
     * @param study
     * @param isSagattal
     */
    public ReconstructionView(java.awt.Frame parent, Study study, boolean isSagattal) {
        super(parent, true);
        initComponents();
        
        this.study = study;
        
        List<Image> images = study.getImages();
        
        BufferedImage baseImg = images.get(0).getImageData();
        if(isSagattal)
            data = new int[images.size()][baseImg.getWidth()][baseImg.getHeight()];
        else
            data = new int[baseImg.getWidth()][images.size()][baseImg.getHeight()];
        
        for(int i=0; i<images.size(); i++) {
            BufferedImage d = images.get(i).getImageData();
            
            if(isSagattal) {
                for(int x=0; x<d.getWidth(); x++)
                    for(int y=0; y<d.getHeight(); y++)
                        data[i][x][y] = d.getRGB(x, y);
            }
            else {
                for(int x=0; x<d.getWidth(); x++)
                    for(int y=0; y<d.getHeight(); y++)
                        data[x][images.size()-i-1][y] = d.getRGB(x, y);
            }
        }
        
        updateUI();
    }
    
    /**
     * Rescales an image. This scales an image to a square with size equal to
     * the maximum dimension of the 3D image data.
     * @param img Image to scale
     * @return Scaled version of image
     */
    private BufferedImage scaleImage(BufferedImage img) {
        int maxCoord = Math.max(Math.max(data.length, data[0].length), data[0][0].length);
        
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage after = new BufferedImage(maxCoord, maxCoord, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(maxCoord/(double)w, maxCoord/(double)h);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        after = scaleOp.filter(img, after);
        return after;
    }
    
    /**
     * Updates the UI.
     */
    private void updateUI() {
        BufferedImage top = new BufferedImage(data.length, data[0][0].length, BufferedImage.TYPE_INT_ARGB);
        BufferedImage front = new BufferedImage(data.length, data[0].length, BufferedImage.TYPE_INT_ARGB);
        BufferedImage side = new BufferedImage(data[0][0].length, data[0].length, BufferedImage.TYPE_INT_ARGB);
        
        // Construct top view from data.
        for(int x=0; x<data.length; x++)
            for(int z=0; z<data[0][0].length; z++) {
                int color;
                if(x == sliceX)
                    color = COLOR_RED;
                else if(z == sliceZ)
                    color = COLOR_BLUE;
                else
                    color = data[x][sliceY][z];
                top.setRGB(x, z, color);
            }
        
        // Construct front view.
        for(int x=0; x<data.length; x++)
            for(int y=0; y<data[0].length; y++) {
                int color;
                if(x == sliceX)
                    color = COLOR_RED;
                else if(y == sliceY)
                    color = COLOR_GREEN;
                else
                    color = data[x][y][sliceZ];
                front.setRGB(x, y, color);
            }
        
        // Construct side view.
        for(int y=0; y<data[0].length; y++)
            for(int z=0; z<data[0][0].length; z++) {
                int color;
                if(y == sliceY)
                    color = COLOR_GREEN;
                else if(z == sliceZ)
                    color = COLOR_BLUE;
                else
                    color = data[sliceX][y][z];
                side.setRGB(z, y, color);
            }
        
        
        
        // Display images
        topImage.setIcon(new ImageIcon(scaleImage(top)));
        frontImage.setIcon(new ImageIcon(scaleImage(front)));
        sideImage.setIcon(new ImageIcon(scaleImage(side)));
        
        topImage.setText("");
        frontImage.setText("");
        sideImage.setText("");
        
        this.pack();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel imagesContainer = new javax.swing.JPanel();
        javax.swing.JPanel topImageContainer = new javax.swing.JPanel();
        topImage = new javax.swing.JLabel();
        topScrollLeftButton = new javax.swing.JButton();
        topScrollRightButton = new javax.swing.JButton();
        javax.swing.JPanel frontImageContainer = new javax.swing.JPanel();
        frontImage = new javax.swing.JLabel();
        frontScrollLeftButton = new javax.swing.JButton();
        frontScrollRightButton = new javax.swing.JButton();
        javax.swing.JPanel sideImageContainer = new javax.swing.JPanel();
        sideImage = new javax.swing.JLabel();
        sideScrollLeftButton = new javax.swing.JButton();
        sideScrollRightButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Reconstruction");

        imagesContainer.setLayout(new java.awt.GridLayout(2, 2));

        topImage.setText("<image>");

        topScrollLeftButton.setText("<");
        topScrollLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topScrollLeftButtonActionPerformed(evt);
            }
        });

        topScrollRightButton.setText(">");
        topScrollRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topScrollRightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topImageContainerLayout = new javax.swing.GroupLayout(topImageContainer);
        topImageContainer.setLayout(topImageContainerLayout);
        topImageContainerLayout.setHorizontalGroup(
            topImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topImageContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(topImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(topImageContainerLayout.createSequentialGroup()
                        .addComponent(topScrollLeftButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(topScrollRightButton)))
                .addContainerGap())
        );
        topImageContainerLayout.setVerticalGroup(
            topImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topImageContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(topImage, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(topImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(topScrollLeftButton)
                    .addComponent(topScrollRightButton))
                .addContainerGap())
        );

        imagesContainer.add(topImageContainer);

        frontImage.setText("<image>");

        frontScrollLeftButton.setText("<");
        frontScrollLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frontScrollLeftButtonActionPerformed(evt);
            }
        });

        frontScrollRightButton.setText(">");
        frontScrollRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                frontScrollRightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout frontImageContainerLayout = new javax.swing.GroupLayout(frontImageContainer);
        frontImageContainer.setLayout(frontImageContainerLayout);
        frontImageContainerLayout.setHorizontalGroup(
            frontImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frontImageContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(frontImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(frontImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(frontImageContainerLayout.createSequentialGroup()
                        .addComponent(frontScrollLeftButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(frontScrollRightButton)))
                .addContainerGap())
        );
        frontImageContainerLayout.setVerticalGroup(
            frontImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(frontImageContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(frontImage, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(frontImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(frontScrollLeftButton)
                    .addComponent(frontScrollRightButton))
                .addContainerGap())
        );

        imagesContainer.add(frontImageContainer);

        sideImage.setText("<image>");

        sideScrollLeftButton.setText("<");
        sideScrollLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sideScrollLeftButtonActionPerformed(evt);
            }
        });

        sideScrollRightButton.setText(">");
        sideScrollRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sideScrollRightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout sideImageContainerLayout = new javax.swing.GroupLayout(sideImageContainer);
        sideImageContainer.setLayout(sideImageContainerLayout);
        sideImageContainerLayout.setHorizontalGroup(
            sideImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideImageContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sideImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sideImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(sideImageContainerLayout.createSequentialGroup()
                        .addComponent(sideScrollLeftButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(sideScrollRightButton)))
                .addContainerGap())
        );
        sideImageContainerLayout.setVerticalGroup(
            sideImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sideImageContainerLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sideImage, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sideImageContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sideScrollLeftButton)
                    .addComponent(sideScrollRightButton))
                .addContainerGap())
        );

        imagesContainer.add(sideImageContainer);

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imagesContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(exitButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imagesContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exitButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void topScrollLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topScrollLeftButtonActionPerformed
        sliceY = clamp(sliceY-1, 0, data[0].length-1);
        updateUI();
    }//GEN-LAST:event_topScrollLeftButtonActionPerformed

    private void topScrollRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_topScrollRightButtonActionPerformed
        sliceY = clamp(sliceY+1, 0, data[0].length-1);
        updateUI();
    }//GEN-LAST:event_topScrollRightButtonActionPerformed

    private void frontScrollLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frontScrollLeftButtonActionPerformed
        sliceZ = clamp(sliceZ-1, 0, data[0][0].length-1);
        updateUI();
    }//GEN-LAST:event_frontScrollLeftButtonActionPerformed

    private void frontScrollRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_frontScrollRightButtonActionPerformed
        sliceZ = clamp(sliceZ+1, 0, data[0][0].length-1);
        updateUI();
    }//GEN-LAST:event_frontScrollRightButtonActionPerformed

    private void sideScrollLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sideScrollLeftButtonActionPerformed
        sliceX = clamp(sliceX-1, 0, data.length-1);
        updateUI();
    }//GEN-LAST:event_sideScrollLeftButtonActionPerformed

    private void sideScrollRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sideScrollRightButtonActionPerformed
        sliceX = clamp(sliceX+1, 0, data.length-1);
        updateUI();
    }//GEN-LAST:event_sideScrollRightButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_exitButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel frontImage;
    private javax.swing.JButton frontScrollLeftButton;
    private javax.swing.JButton frontScrollRightButton;
    private javax.swing.JLabel sideImage;
    private javax.swing.JButton sideScrollLeftButton;
    private javax.swing.JButton sideScrollRightButton;
    private javax.swing.JLabel topImage;
    private javax.swing.JButton topScrollLeftButton;
    private javax.swing.JButton topScrollRightButton;
    // End of variables declaration//GEN-END:variables
}
