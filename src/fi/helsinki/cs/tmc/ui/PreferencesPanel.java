package fi.helsinki.cs.tmc.ui;

import fi.helsinki.cs.tmc.actions.RefreshCoursesAction;
import fi.helsinki.cs.tmc.data.Course;
import fi.helsinki.cs.tmc.data.CourseCollection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * The settings panel.
 * 
 * This is missing the "OK" and "Cancel" buttons because it is placed in
 * a dialog that provides these.
 */
/*package*/ class PreferencesPanel extends JPanel implements PreferencesUI {
    
    private RefreshCoursesAction refreshAction = new RefreshCoursesAction();
    
    /*package*/ PreferencesPanel() {
        initComponents();
        
        setUpAdviceUpdating();
    }
    
    @Override
    public String getUsername() {
        return usernameTextField.getText();
    }
    
    @Override
    public void setUsername(String username) {
        usernameTextField.setText(username);
    }
    
    @Override
    public String getServerBaseUrl() {
        return serverAddressTextField.getText();
    }
    
    @Override
    public void setServerBaseUrl(String baseUrl) {
        serverAddressTextField.setText(baseUrl);
    }
    
    @Override
    public String getProjectDir() {
        return projectFolderTextField.getText();
    }
    
    @Override
    public void setProjectDir(String projectDir) {
        projectFolderTextField.setText(projectDir);
    }
    
    @Override
    public void setAvailableCourses(CourseCollection courses) {
        String previousSelectedCourseName = null;
        if (getSelectedCourse() != null) {
            previousSelectedCourseName = getSelectedCourse().getName();
        }
        
        coursesComboBox.removeAllItems();
        int newSelectedIndex = -1;
        for (int i = 0; i < courses.size(); ++i) {
            Course course = courses.get(i);
            coursesComboBox.addItem(courses.get(i));
            
            if (course.getName().equals(previousSelectedCourseName)) {
                newSelectedIndex = i;
            }
        }
        
        coursesComboBox.setSelectedIndex(newSelectedIndex);
        
        setCourseSelectionEnabled(true);
    }
    
    @Override
    public void courseRefreshFailedOrCanceled() {
        setCourseSelectionEnabled(true);
    }
    
    private void setCourseSelectionEnabled(boolean enabled) {
        refreshCoursesBtn.setEnabled(enabled);
        coursesComboBox.setEnabled(enabled);
    }
    
    @Override
    public void setSelectedCourse(Course course) {
        coursesComboBox.setSelectedItem(course);
    }
    
    @Override
    public Course getSelectedCourse() {
        Object item = coursesComboBox.getSelectedItem();
        if (item instanceof Course) {
            return (Course)item;
        } else { // because the combobox isn't populated yet
            return null;
        }
    }
    
    private void setUpAdviceUpdating() {
        DocumentListener docListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateAdvice();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateAdvice();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateAdvice();
            }
        };
        usernameTextField.getDocument().addDocumentListener(docListener);
        serverAddressTextField.getDocument().addDocumentListener(docListener);
        projectFolderTextField.getDocument().addDocumentListener(docListener);
        
        coursesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateAdvice();
            }
        });
    }
    
    private void updateAdvice() {
        ArrayList<String> advices = new ArrayList<String>();
        if (usernameTextField.getText().isEmpty()) {
            advices.add("fill in username");
        }
        if (serverAddressTextField.getText().isEmpty()) {
            advices.add("fill in server address");
        }
        if (projectFolderTextField.getText().isEmpty()) {
            advices.add("select folder for projects");
        }
        if (coursesComboBox.getSelectedIndex() == -1) {
            advices.add("select course");
        }
        
        if (!advices.isEmpty()) {
            String advice = "Please " + joinWithCommasAndAnd(advices) + ".";
            adviceLabel.setText(advice);
        } else {
            adviceLabel.setText("");
        }
    }
    
    private String joinWithCommasAndAnd(List<String> strings) {
        if (strings.isEmpty()) {
            return "";
        } else if (strings.size() == 1) {
            return strings.get(0);
        } else {
            String s = "";
            for (int i = 0; i < strings.size() - 2; ++i) {
                s += strings.get(i) + ", ";
            }
            s += strings.get(strings.size() - 2);
            s += " and ";
            s += strings.get(strings.size() - 1);
            return s;
        }
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        serverAddressLabel = new javax.swing.JLabel();
        serverAddressTextField = new javax.swing.JTextField();
        projectFolderLabel = new javax.swing.JLabel();
        projectFolderTextField = new javax.swing.JTextField();
        folderChooserBtn = new javax.swing.JButton();
        refreshCoursesBtn = new javax.swing.JButton();
        coursesLabel = new javax.swing.JLabel();
        coursesComboBox = new javax.swing.JComboBox();
        adviceLabel = new javax.swing.JLabel();

        usernameLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.usernameLabel.text")); // NOI18N

        usernameTextField.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.usernameTextField.text")); // NOI18N
        usernameTextField.setMinimumSize(new java.awt.Dimension(150, 27));
        usernameTextField.setPreferredSize(new java.awt.Dimension(150, 27));

        serverAddressLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.serverAddressLabel.text")); // NOI18N

        serverAddressTextField.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.serverAddressTextField.text")); // NOI18N
        serverAddressTextField.setMinimumSize(new java.awt.Dimension(250, 27));
        serverAddressTextField.setPreferredSize(new java.awt.Dimension(250, 27));
        serverAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                serverAddressTextFieldActionPerformed(evt);
            }
        });

        projectFolderLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.projectFolderLabel.text")); // NOI18N

        projectFolderTextField.setEditable(false);
        projectFolderTextField.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.projectFolderTextField.text")); // NOI18N
        projectFolderTextField.setEnabled(false);
        projectFolderTextField.setPreferredSize(new java.awt.Dimension(250, 27));

        folderChooserBtn.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.folderChooserBtn.text")); // NOI18N
        folderChooserBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                folderChooserBtnActionPerformed(evt);
            }
        });

        refreshCoursesBtn.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.refreshCoursesBtn.text")); // NOI18N
        refreshCoursesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshCoursesBtnActionPerformed(evt);
            }
        });

        coursesLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.coursesLabel.text")); // NOI18N

        coursesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        adviceLabel.setForeground(new java.awt.Color(255, 102, 0));
        adviceLabel.setText(org.openide.util.NbBundle.getMessage(PreferencesPanel.class, "PreferencesPanel.adviceLabel.text")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(adviceLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 667, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameLabel)
                            .addComponent(serverAddressLabel)
                            .addComponent(projectFolderLabel)
                            .addComponent(coursesLabel))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(serverAddressTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                            .addComponent(usernameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(projectFolderTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE)
                                    .addComponent(coursesComboBox, 0, 382, Short.MAX_VALUE))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(refreshCoursesBtn)
                                    .addComponent(folderChooserBtn))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(adviceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 12, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(serverAddressLabel)
                    .addComponent(serverAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(projectFolderLabel)
                    .addComponent(projectFolderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(folderChooserBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshCoursesBtn)
                    .addComponent(coursesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(coursesLabel))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * The method is used to select the default save folder for downloaded exercises.
     * It is called when the user presses the "Browse" button on the preferences window.
     * @param evt 
     */
    private void folderChooserBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_folderChooserBtnActionPerformed

        JFileChooser folderChooser = new JFileChooser();
        folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int choice = folderChooser.showOpenDialog(this);
        if (choice == JFileChooser.CANCEL_OPTION) {
            return;
        }
        File projectDefaultFolder = folderChooser.getSelectedFile();

        projectFolderTextField.setText(projectDefaultFolder.getAbsolutePath());
    }//GEN-LAST:event_folderChooserBtnActionPerformed

    private void refreshCoursesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshCoursesBtnActionPerformed
        setCourseSelectionEnabled(false);
        refreshAction.actionPerformed(evt);
    }//GEN-LAST:event_refreshCoursesBtnActionPerformed

    private void serverAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_serverAddressTextFieldActionPerformed
        // Pressing return in the server address field presses the refresh button
        refreshCoursesBtn.doClick();
    }//GEN-LAST:event_serverAddressTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adviceLabel;
    private javax.swing.JComboBox coursesComboBox;
    private javax.swing.JLabel coursesLabel;
    private javax.swing.JButton folderChooserBtn;
    private javax.swing.JLabel projectFolderLabel;
    private javax.swing.JTextField projectFolderTextField;
    private javax.swing.JButton refreshCoursesBtn;
    private javax.swing.JLabel serverAddressLabel;
    private javax.swing.JTextField serverAddressTextField;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables

    
}
