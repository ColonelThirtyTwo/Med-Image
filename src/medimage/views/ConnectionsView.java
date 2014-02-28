
package medimage.views;

import medimage.MedImage;
import medimage.models.Connection;

/**
 * Views connections, or data sources, such as the local filesystem or a remote
 * imaging devices.
 * @author Alex Parrill
 */
public class ConnectionsView extends javax.swing.JFrame {

    /**
     * Creates new form ConnectionsView
     */
    public ConnectionsView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        javax.swing.JScrollPane connectionsScroller = new javax.swing.JScrollPane();
        connectionsSelector = new javax.swing.JList<medimage.models.Connection>();
        loadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MedImage");

        headerLabel.setText("Select Data Source");

        connectionsSelector.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Local Folder", "Remote Device 1", "Remote Device 2" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        connectionsSelector.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        connectionsSelector.setListData(new medimage.models.Connection[] { new medimage.models.local.LocalConnection() });
        connectionsScroller.setViewportView(connectionsSelector);

        loadButton.setText("Load");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(connectionsScroller)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(headerLabel)
                            .addComponent(loadButton))
                        .addGap(0, 241, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(connectionsScroller, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        Connection conn = connectionsSelector.getSelectedValue();
        if(conn != null) {
            this.setVisible(false);
            MedImage.getStudiesView().viewStudies(conn);
        }
    }//GEN-LAST:event_loadButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConnectionsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConnectionsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConnectionsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConnectionsView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConnectionsView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<medimage.models.Connection> connectionsSelector;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JButton loadButton;
    // End of variables declaration//GEN-END:variables
}
