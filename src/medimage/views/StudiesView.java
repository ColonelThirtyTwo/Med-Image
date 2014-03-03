
package medimage.views;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import medimage.MedImage;
import medimage.models.Connection;
import medimage.models.DisplayState;
import medimage.models.Study;
import medimage.models.local.LocalConnection;

/**
 * View for listing all the studies of a connection.
 * @author col32
 */
public class StudiesView extends javax.swing.JFrame {

    private Connection connection;
    
    /**
     * Creates new form StudiesView
     */
    public StudiesView() {
        initComponents();
    }
    
    /**
     * Updates the UI to view a list of studies and makes the frame visible.
     * @param connection Connection to get studies from.
     */
    public void viewStudies(Connection connection) {
        this.viewStudies(connection, false);
    }
    
    /**
     * Updates the UI to view a list of studies and makes the frame visible.
     * @param connection Connection to get studies from.
     * @param dontLoadDefault Set to true to prevent loading of the default study.
     */
    public void viewStudies(Connection connection, boolean dontLoadDefault)
    {
        this.connection = connection;
        
        // Check for default study
        Study deflt = connection.getDefaultStudy();
        if(!dontLoadDefault && deflt != null)
            // Load default study instead
            this.loadStudy(deflt);
        else
        {
            // Show study picker
            this.updateStudiesUI();
            this.setVisible(true);
        }
    }
    
    /**
     * Refreshes the studies list.
     */
    private void updateStudiesUI() {
        List<Study> studies = connection.getStudies();
        
        // Convert to array
        Study[] studiesArr = new Study[studies.size()];
        studies.toArray(studiesArr);
        
        this.studiesList.setListData(studiesArr);
    }
    
    /**
     * Views a study. Loads the display state and transitions to the appropriate
     * image view.
     * @param study Study to view.
     */
    private void loadStudy(Study study) {
        // Do nothing if nothing selected.
        if(study == null)
            return;
        
        // Get previous display state.
        DisplayState state = study.getDisplayState();
        
        if(state == null) // No previous display state
            MedImage.getSingleImageView().viewImages(connection, study);
        else if(state.getCurrState() == DisplayState.States.SINGLE_IMAGE) // Single image display state
            MedImage.getSingleImageView().viewImages(connection, study, state.getImageIndex());
        else // Quad image display state
            MedImage.getQuadImageView().viewImages(connection, study, state.getImageIndex());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label = new javax.swing.JLabel();
        studiesContainer = new javax.swing.JScrollPane();
        studiesList = new javax.swing.JList<medimage.models.Study>();
        loadButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        copyButton = new javax.swing.JButton();
        setDefaultStudyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MedImage");

        label.setText("Select Study");

        studiesList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        studiesList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        studiesContainer.setViewportView(studiesList);

        loadButton.setText("View");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        copyButton.setText("Copy");
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });

        setDefaultStudyButton.setText("Set Default");
        setDefaultStudyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                setDefaultStudyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(studiesContainer)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(loadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(copyButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                        .addComponent(setDefaultStudyButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(backButton))
                    .addComponent(label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(studiesContainer, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loadButton)
                    .addComponent(backButton)
                    .addComponent(copyButton)
                    .addComponent(setDefaultStudyButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Callback for the back button. Returns to the connections list view.
     * @param evt
     * @see medimage.views.ConnectionsView
     */
    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        this.setVisible(false);
        MedImage.getConnectionsView().setVisible(true);
    }//GEN-LAST:event_backButtonActionPerformed
    
    /**
     * Callback for the load button. Loads a study and restores the
     * display state if one exists.
     * @param evt 
     * @see medimage.views.QuadImageView
     * @see medimage.views.SingleImageView
     */
    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        this.setVisible(false);
        
        Study study = this.studiesList.getSelectedValue();
        this.loadStudy(study);
    }//GEN-LAST:event_loadButtonActionPerformed
    
    /**
     * Callback for copy button. Opens the copy view for study copying.
     * @param evt 
     * @see medimage.views.CopyStudyView
     */
    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
        Study study = this.studiesList.getSelectedValue();
        if(study == null)
            return; // No study selected.
        
        // Can only copy to local connection
        Connection[] connections = new Connection[] { new LocalConnection() };
        
        // Create dialog
        CopyStudyView diag = new CopyStudyView(this, study, connections);
        diag.setVisible(true);
        
        // Update when the window closes, in case we copy to the viewed study.
        diag.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                updateStudiesUI();
            }
        });
    }//GEN-LAST:event_copyButtonActionPerformed
    
    /**
     * Callback for the set default study button. Opens a dialog box for setting
     * the default study.
     * @param evt 
     */
    private void setDefaultStudyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_setDefaultStudyButtonActionPerformed
        DefaultStudyView view = new DefaultStudyView(this, connection);
        view.setVisible(true);
    }//GEN-LAST:event_setDefaultStudyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton copyButton;
    private javax.swing.JLabel label;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton setDefaultStudyButton;
    private javax.swing.JScrollPane studiesContainer;
    private javax.swing.JList<medimage.models.Study> studiesList;
    // End of variables declaration//GEN-END:variables
}
