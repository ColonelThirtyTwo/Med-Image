
package medimage.views.improvedimageview;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import medimage.ImageIterator;
import medimage.MedImage;
import medimage.models.Connection;
import medimage.models.DisplayState;
import medimage.models.Image;
import medimage.models.Study;
import medimage.views.ReconstructionOptionsView;

/**
 * JFrame for displaying images. The JFrame contains a menu and several buttons,
 * as well as a panel that can be swapped based on the display mode, or to
 * display a reconstruction.
 * @author col32
 */
public class ImageView extends JFrame {
    
    private Connection conn;
    private Study study;
    private ImageIterator iterator;
    private ImagePanel imagePanel;
    
    /**
     * Creates new form ImageView
     */
    public ImageView() {
        initComponents();
        
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if(promptSaveState())
                    System.exit(0);
            }
        });
    }
    
    /**
     * Updates the UI to view a list of images in a single image display mode
     * and makes the frame visible.
     * @param conn Connection of the study
     * @param study Study to view images from
     * @param index Index of image to view.
     */
    public void viewSingleImage(Connection conn, Study study, int index) {
        this.conn = conn;
        this.study = study;
        
        ImagePanel p = new SingleImagePanel();
        setImagePanel(p);
        
        this.iterator = imagePanel.createIterator(study.getImages(), index);
        this.updateImageUI();
        this.pack();
        this.setVisible(true);
    }
    
    /**
     * Updates the UI to view a list of images in a 2x2 image display mode
     * and makes the frame visible.
     * @param conn Connection of the study
     * @param study Study to view images from
     * @param index Index of image to view.
     */
    public void viewQuadImage(Connection conn, Study study, int index) {
        this.conn = conn;
        this.study = study;
        
        ImagePanel p = new QuadImagePanel();
        setImagePanel(p);
        
        this.iterator = imagePanel.createIterator(study.getImages(), index);
        this.updateImageUI();
        this.pack();
        this.setVisible(true);
    }
    
    /**
     * Sets the image panel to use.
     * @param panel 
     */
    private void setImagePanel(ImagePanel panel) {
        viewContainer.removeAll();
        viewContainer.add(panel);
        imagePanel = panel;
    }
    
    /**
     * Updates the UI with the current contents of the image iterator.
     */
    protected void updateImageUI() {
        JLabel[] labels = imagePanel.getImageContainers();
        Image[] imgs = this.iterator.getImages();
        for(int i=0; i<labels.length; i++) {
            if(imgs[i] == null)
                labels[i].setIcon(null);
            else
                labels[i].setIcon(new ImageIcon(imgs[i].getImageData()));
            labels[i].setText("");
        }
        this.setTitle(study.getName());
        
        this.saveButton.setEnabled(!imagePanel.isReadOnly());
        this.switchDisplayModeButton.setEnabled(!imagePanel.isReadOnly());
    }
    
    /**
     * Asks the user to save the display state, if needed.
     * @return True if user clicked 'yes' or 'no', or if the state hasn't changed.
     * False if the user clicked 'cancel' or closed the dialog box.
     */
    private boolean promptSaveState() {
        DisplayState state = imagePanel.getDisplayState(iterator);
        if(state == null)
            // Don't warn if panel doesn't have display state to save.
            return true;
        
        if(state.equals(study.getDisplayState()))
            // Don't warn if nothing to save.
            return true;

        //Custom button text
        Object[] options = {"Yes",
                            "No",
                            "Cancel"};
        int n = JOptionPane.showOptionDialog(this,
            "Save Display State?",
            "MedImage",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[2]);

        if(n == 0) { // "Yes", save display state
            study.saveDisplayState(imagePanel.getDisplayState(iterator));
        }
        
        // If user clicked yes or no, follow through with action. Otherwise, if
        // user clicked cancel or closed dialog, cancel action.
        return n != 2 && n != JOptionPane.CLOSED_OPTION;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        viewContainer = new javax.swing.JPanel();
        nextButton = new javax.swing.JButton();
        prevButton = new javax.swing.JButton();
        javax.swing.JMenuBar menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        saveButton = new javax.swing.JMenuItem();
        switchDisplayModeButton = new javax.swing.JMenuItem();
        undoButton = new javax.swing.JMenuItem();
        redoButton = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator menuSeparator = new javax.swing.JPopupMenu.Separator();
        backButton = new javax.swing.JMenuItem();
        javax.swing.JMenu editMenu = new javax.swing.JMenu();
        reconstructButton = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        viewContainer.setLayout(new java.awt.GridLayout(1, 1));

        nextButton.setText("Next");
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        prevButton.setText("Prev");
        prevButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prevButtonActionPerformed(evt);
            }
        });

        fileMenu.setText("File");

        saveButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveButton.setText("Save State");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });
        fileMenu.add(saveButton);

        switchDisplayModeButton.setText("Switch Display Mode");
        switchDisplayModeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchDisplayModeButtonActionPerformed(evt);
            }
        });
        fileMenu.add(switchDisplayModeButton);

        undoButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoButton.setText("Undo");
        undoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoButtonActionPerformed(evt);
            }
        });
        fileMenu.add(undoButton);

        redoButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoButton.setText("Redo");
        redoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoButtonActionPerformed(evt);
            }
        });
        fileMenu.add(redoButton);
        fileMenu.add(menuSeparator);

        backButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_BACK_SPACE, 0));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        fileMenu.add(backButton);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        reconstructButton.setText("Reconstruct 3D Data");
        reconstructButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconstructButtonActionPerformed(evt);
            }
        });
        editMenu.add(reconstructButton);

        menuBar.add(editMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(viewContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 239, Short.MAX_VALUE)
                        .addComponent(prevButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nextButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(viewContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextButton)
                    .addComponent(prevButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        study.saveDisplayState(imagePanel.getDisplayState(iterator));
    }//GEN-LAST:event_saveButtonActionPerformed

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_undoButtonActionPerformed

    private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_redoButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        if(promptSaveState())
        {
            this.setVisible(false);
            MedImage.getStudiesView().viewStudies(conn, true);
        }
    }//GEN-LAST:event_backButtonActionPerformed

    private void switchDisplayModeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchDisplayModeButtonActionPerformed
        if(imagePanel.isReadOnly())
            // Can't switch reconstruction panel
            return;
        
        ImagePanel p;
        if(imagePanel instanceof SingleImagePanel)
            p = new QuadImagePanel();
        else
            p = new SingleImagePanel();
        
        setImagePanel(p);
        
        this.iterator = imagePanel.createIterator(study.getImages(), iterator.getIndex());
        this.updateImageUI();
        this.pack();
        this.setVisible(true);
    }//GEN-LAST:event_switchDisplayModeButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        iterator.prev();
        this.updateImageUI();
    }//GEN-LAST:event_prevButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        iterator.next();
        this.updateImageUI();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void reconstructButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconstructButtonActionPerformed
        ReconstructionOptionsView v = new ReconstructionOptionsView(this, study);
        v.setVisible(true);
    }//GEN-LAST:event_reconstructButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem backButton;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton prevButton;
    private javax.swing.JMenuItem reconstructButton;
    private javax.swing.JMenuItem redoButton;
    private javax.swing.JMenuItem saveButton;
    private javax.swing.JMenuItem switchDisplayModeButton;
    private javax.swing.JMenuItem undoButton;
    private javax.swing.JPanel viewContainer;
    // End of variables declaration//GEN-END:variables
}
