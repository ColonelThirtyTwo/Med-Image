
package medimage.views.improvedimageview;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import medimage.Command;
import medimage.ImageIterator;
import medimage.MedImage;
import medimage.models.Connection;
import medimage.models.DisplayState;
import medimage.models.Image;
import medimage.models.Study;
import medimage.views.ReconstructionOptionsView;
import medimage.views.WindowingOptionsView;

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
    private Deque<Command> executedCommands;
    private Deque<Command> redoCommands;
    
    /**
     * Command that represents a user scrolling between images.
     */
    public static class ScrollCommand extends Command {
        
        private final int prevIndex, newIndex;

        /**
         * Creates a new command.
         * @param prevIndex Previous index of the iterator.
         * @param newIndex New index of the iterator.
         */
        public ScrollCommand(int prevIndex, int newIndex) {
            this.prevIndex = prevIndex;
            this.newIndex = newIndex;
        }
        
        @Override
        public void apply(ImageView view, Study study) {
            view.iterator.setIndex(newIndex);
            view.updateImageUI();
        }

        @Override
        public void undo(ImageView view, Study study) {
            view.iterator.setIndex(prevIndex);
            view.updateImageUI();
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 61 * hash + this.prevIndex;
            hash = 61 * hash + this.newIndex;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ScrollCommand other = (ScrollCommand) obj;
            if (this.prevIndex != other.prevIndex) {
                return false;
            }
            return this.newIndex == other.newIndex;
        }
        
    }
    
    /**
     * Command that represents a user switching the display mode.
     */
    public static class ChangeDisplayModeCommand extends Command {
        
        private final DisplayState.States newState;

        public ChangeDisplayModeCommand(DisplayState.States newState) {
            this.newState = newState;
        }
        
        @Override
        public void apply(ImageView view, Study study) {
            ImagePanel p;
            if(newState == DisplayState.States.QUAD_IMAGE)
                p = new QuadImagePanel();
            else
                p = new SingleImagePanel();

            view.setImagePanel(p);

            view.iterator = p.createIterator(view.study.getImages(), view.iterator.getIndex());
            view.updateImageUI();
            view.pack();
            view.setVisible(true);
        }

        @Override
        public void undo(ImageView view, Study study) {
            ImagePanel p;
            if(newState == DisplayState.States.QUAD_IMAGE)
                p = new SingleImagePanel();
            else
                p = new QuadImagePanel();

            view.setImagePanel(p);

            view.iterator = p.createIterator(view.study.getImages(), view.iterator.getIndex());
            view.updateImageUI();
            view.pack();
            view.setVisible(true);
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 43 * hash + Objects.hashCode(this.newState);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final ChangeDisplayModeCommand other = (ChangeDisplayModeCommand) obj;
            return this.newState == other.newState;
        }
    }
    
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
     * Updates the UI to view a list of images and makes the frame visible.
     * @param conn Connection of the study
     * @param study Study to view images from
     * @param index Index of image to view.
     * @param state Whether to open in single image mode or 2x2 image mode.
     * @param commands Commands to execute upon viewing
     */
    public void viewImages(Connection conn, Study study, int index, DisplayState.States state, List<Command> commands) {
        this.conn = conn;
        this.study = study;
        
        executedCommands = new LinkedList<Command>();
        redoCommands = new LinkedList<Command>();
        
        ImagePanel p;
        if(state == null || state == DisplayState.States.SINGLE_IMAGE)
            p = new SingleImagePanel();
        else
            p = new QuadImagePanel();
        setImagePanel(p);
        
        this.iterator = imagePanel.createIterator(study.getImages(), index);
        
        if(commands != null)
            for(Command c : commands)
                this.addCommand(c);
        
        this.updateImageUI();
        this.pack();
        this.setVisible(true);
    }
    
    /**
     * Executes a command and adds it to the undo stack.
     * @param c Command to execute.
     * @param exec True to execute the command, false to simply add it to the
     * undo stack.
     */
    public void addCommand(Command c, boolean exec) {
        if(exec) c.apply(this, study);
        executedCommands.addLast(c);
        redoCommands.clear();
    }
    
    /**
     * Executes a command and adds it to the undo stack.
     * @param c Command to execute.
     */
    public void addCommand(Command c) {
        this.addCommand(c, true);
    }
    
    /**
     * Undoes the last command.
     */
    public void undoCommand() {
        Command c = executedCommands.pollLast();
        if(c != null) {
            c.undo(this, study);
            redoCommands.addLast(c);
        }
    }
    
    /**
     * Redoes the last undid command.
     */
    public void redoCommand() {
        Command c = redoCommands.pollLast();
        if(c != null) {
            c.apply(this, study);
            executedCommands.addLast(c);
        }
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
    public void updateImageUI() {
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
     * Creates a display state from the current GUI state.
     * @return 
     */
    private DisplayState constructDisplayState() {
        DisplayState.States s = imagePanel instanceof SingleImagePanel ?
                DisplayState.States.SINGLE_IMAGE :
                DisplayState.States.QUAD_IMAGE;
        
        List<Command> filteredCommands = new ArrayList<Command>();
        for(Command c : executedCommands)
            if(!(c instanceof ScrollCommand) && !(c instanceof ChangeDisplayModeCommand))
                filteredCommands.add(c);
        
        return new DisplayState(s, iterator.getIndex(), filteredCommands);
    }
    
    /**
     * Asks the user to save the display state, if needed.
     * @return True if user clicked 'yes' or 'no', or if the state hasn't changed.
     * False if the user clicked 'cancel' or closed the dialog box.
     */
    private boolean promptSaveState() {
        DisplayState state = this.constructDisplayState();
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
            study.saveDisplayState(state);
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
        windowStudyButton = new javax.swing.JMenuItem();

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

        windowStudyButton.setText("Window Study");
        windowStudyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                windowStudyButtonActionPerformed(evt);
            }
        });
        editMenu.add(windowStudyButton);

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
        study.saveDisplayState(this.constructDisplayState());
    }//GEN-LAST:event_saveButtonActionPerformed

    private void undoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoButtonActionPerformed
        this.undoCommand();
    }//GEN-LAST:event_undoButtonActionPerformed

    private void redoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoButtonActionPerformed
        this.redoCommand();
    }//GEN-LAST:event_redoButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        if(promptSaveState())
        {
            this.setVisible(false);
            MedImage.getStudiesView().viewStudies(conn, true);
        }
    }//GEN-LAST:event_backButtonActionPerformed

    private void switchDisplayModeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchDisplayModeButtonActionPerformed
        this.addCommand(new ChangeDisplayModeCommand(imagePanel instanceof SingleImagePanel ?
                DisplayState.States.QUAD_IMAGE :
                DisplayState.States.SINGLE_IMAGE));
    }//GEN-LAST:event_switchDisplayModeButtonActionPerformed

    private void prevButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevButtonActionPerformed
        int oldIndex = iterator.getIndex();
        iterator.prev();
        int newIndex = iterator.getIndex();
        
        this.addCommand(new ScrollCommand(oldIndex, newIndex), false);
        this.updateImageUI();
    }//GEN-LAST:event_prevButtonActionPerformed

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextButtonActionPerformed
        int oldIndex = iterator.getIndex();
        iterator.next();
        int newIndex = iterator.getIndex();
        
        this.addCommand(new ScrollCommand(oldIndex, newIndex), false);
        this.updateImageUI();
    }//GEN-LAST:event_nextButtonActionPerformed

    private void reconstructButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconstructButtonActionPerformed
        ReconstructionOptionsView v = new ReconstructionOptionsView(this, study);
        v.setVisible(true);
    }//GEN-LAST:event_reconstructButtonActionPerformed

    private void windowStudyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_windowStudyButtonActionPerformed
        WindowingOptionsView v = new WindowingOptionsView(this);
        v.setVisible(true);
    }//GEN-LAST:event_windowStudyButtonActionPerformed

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
    private javax.swing.JMenuItem windowStudyButton;
    // End of variables declaration//GEN-END:variables
}
