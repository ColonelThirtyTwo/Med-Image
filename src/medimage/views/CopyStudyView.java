/**
 * File: CopyStudyView.java
 * Description: The view for copying studies.
 */
package medimage.views;

import medimage.models.Connection;
import medimage.models.Study;

/**
 * Dialog for copying a study.
 * @author col32
 */
public class CopyStudyView extends javax.swing.JDialog {
    
    private final Study sourceStudy;
    
    /**
     * Creates new form CopyStudyView
     * @param parent Parent window.
     * @param sourceStudy Source study to copy from.
     * @param desinationConnections Array of connections that can be copied to.
     */
    public CopyStudyView(java.awt.Frame parent, Study sourceStudy, Connection[] desinationConnections) {
        super(parent, true);
        this.sourceStudy = sourceStudy;
        initComponents();
        this.connectionsList.setListData(desinationConnections);
        this.studyName.setText(sourceStudy.getName());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JLabel studyNameLabel = new javax.swing.JLabel();
        studyName = new javax.swing.JTextField();
        javax.swing.JLabel connectionsLabel = new javax.swing.JLabel();
        javax.swing.JScrollPane collectionsScroller = new javax.swing.JScrollPane();
        connectionsList = new javax.swing.JList<medimage.models.Connection>();
        cancelButton = new javax.swing.JButton();
        copyButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        studyNameLabel.setText("New Name:");

        studyName.setText("<Study>");

        connectionsLabel.setText("To Connection:");

        connectionsList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        collectionsScroller.setViewportView(connectionsList);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        copyButton.setText("Copy");
        copyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(studyNameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(studyName))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(connectionsLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(collectionsScroller)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 214, Short.MAX_VALUE)
                        .addComponent(copyButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(studyNameLabel)
                    .addComponent(studyName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectionsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(collectionsScroller, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(copyButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Callback for the cancel button. Exits the dialog without doing anything.
     * @param evt 
     */
    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed
    
    /**
     * Callback for the copy button. Copies the study.
     * @param evt 
     */
    private void copyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyButtonActionPerformed
        Connection destConnection = this.connectionsList.getSelectedValue();
        if(destConnection == null)
            return; // User hasn't selected anything.
        
        String name = this.studyName.getText();
        if(name.equals(""))
            return; // No name specified.
        
        destConnection.copyStudyInto(name, sourceStudy);
        this.dispose();
    }//GEN-LAST:event_copyButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JList<medimage.models.Connection> connectionsList;
    private javax.swing.JButton copyButton;
    private javax.swing.JTextField studyName;
    // End of variables declaration//GEN-END:variables
}
