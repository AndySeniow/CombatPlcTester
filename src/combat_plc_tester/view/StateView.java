/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

// Package
package combat_plc_tester.view;

// Imports
import combat_plc_tester.controller.Command;
import combat_plc_tester.controller.DeleteStateCommand;
import combat_plc_tester.controller.TestController;
import combat_plc_tester.model.IO.Output;
import combat_plc_tester.model.moore.State;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Class: StateView
 * 
 * Purpose: 
 * This class represents the View component in the Model-View-Controller (MVC) pattern
 * for managing and displaying states in a graphical user interface. It provides users with 
 * the ability to create, update, and visualize states in the application. 
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class StateView extends javax.swing.JPanel {

    private TestController testcontroller;              // The test controller.
    
    /**
    * Initializes the `StateView` by setting up its graphical components. 
    */
    public StateView() {
        initComponents();
    }

    public void setTestController(TestController testcontroller){
        this.testcontroller = testcontroller;
    }
    
    /**
    * Populates the state output combo box with a list of output objects.
    * 
    * @param outputList the list of `Output` objects to populate the combo box. Each output contains an ID and a name.
    */
    public void viewOutputs(List<Output> outputList){
        cmbStateOutput.removeAllItems();
        for(Output output : outputList){
            cmbStateOutput.addItem(output.getOutputID() + " " + output.getName());
        } 
    }
    
    /**
    * Populates empty combo box..
    * 
    */
    public void resetviewOutputs(){    
         cmbStateOutput.removeAllItems();
    } 
    
    /**
    * Updates the state outputs table with a list of output objects.
    * 
    * Workflow:
    * - Retrieves the table's default model (`DefaultTableModel`) for manipulation.
    * - Clears all existing rows in the table to ensure it is updated with the latest outputs.
    * - Checks if the provided list of state outputs is not empty.
    * - Iterates through the list of state outputs and adds each output as a new row in the table.
    * 
    * @param stateoutputList the list of `Output` objects to populate the table. Each output contains 
    *                        an ID, name, value before time condition, and value after time condition.
    */
    public void viewStateOutputs(List<Output> stateoutputList) {
       DefaultTableModel defaulttablemodel = (DefaultTableModel) tblStateOutputs.getModel();   
       defaulttablemodel.setRowCount(0);
        if (!stateoutputList.isEmpty()) {          
            for (Output stateoutput : stateoutputList) {               
                defaulttablemodel.addRow(new Object[]{stateoutput.getOutputID(), 
                                         stateoutput.getName(), 
                                         stateoutput.getValueBeforeTimeCondition().toString(), 
                                         stateoutput.getValueAfterTimeCondition().toString()});
            }
        }
    }
    
    /**
    * Displays the details of a state in the user interface.
    *  
    * Workflow:
    * - Checks if the provided `State` object is not null.
    * - If the state is valid:
    *   - Updates the text fields with the state's ID, name, timer condition, and timer condition tolerance.
    *   - Disables the timer condition fields if the state's ID is "S0" (indicating a special state).
    *   - Enables the timer condition fields for all other states.
    *   - Clears the value fields for before and after time conditions.
    * - If the state is null:
    *   - Clears all text fields, resets the combo box selection, and ensures all fields are in their default state.
    * 
    * @param state the `State` object containing the data to be displayed. If null, all fields are reset to their default state.
    */
    public void viewState(State state){ 
         if(state != null){
          txtStateID.setText(state.getStateID());
          txtStateName.setText(state.getName());         
          if(state.getStateID().equals("S0"))
          {
          txtStateTimerCondition.setEnabled(false);
          txtStateTimerConditionTolerance.setEnabled(false);
          }else
          {
          txtStateTimerCondition.setEnabled(true);
          txtStateTimerConditionTolerance.setEnabled(true);
          }         
          txtStateTimerCondition.setText(Integer.toString(state.getTimerCondition()));
          txtStateTimerConditionTolerance.setText(Integer.toString(state.getTimerConditionTolerance()));        
          txtValueBeforeTimeCondition.setText("");
          txtValueAfterTimeCondition.setText("");     
         }else
         {
          txtStateID.setText("");
          txtStateName.setText("");
          txtStateTimerCondition.setText("");
          txtStateTimerConditionTolerance.setText("");
          txtValueBeforeTimeCondition.setText("");
          txtValueAfterTimeCondition.setText("");
          cmbStateOutput.setSelectedIndex(0);
         }
    }
    
    /**
    * Displays an exception error message to the user in a dialog box.
    * 
    * @param exception The exception message to be displayed in the dialog box. 
    */
    public void exceptionErrorView(String exception){ 
         JOptionPane.showMessageDialog(null, exception, "Exception", JOptionPane.ERROR_MESSAGE);
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        btnDeleteSelectedOutput = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtStateID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtStateName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtStateTimerCondition = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtStateTimerConditionTolerance = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cmbStateOutput = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        txtValueAfterTimeCondition = new javax.swing.JTextField();
        btnAddStateOutput = new javax.swing.JButton();
        btnUpdateState = new javax.swing.JButton();
        btnDeleteState = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtValueBeforeTimeCondition = new javax.swing.JTextField();
        btnUpdateSelectedOutput = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblStateOutputs = new javax.swing.JTable();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        setBackground(new java.awt.Color(50, 70, 110));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "State Outputs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        setToolTipText("");
        setPreferredSize(new java.awt.Dimension(340, 515));

        btnDeleteSelectedOutput.setBackground(new java.awt.Color(27, 38, 61));
        btnDeleteSelectedOutput.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteSelectedOutput.setText("Delete Output");
        btnDeleteSelectedOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSelectedOutputActionPerformed(evt);
            }
        });

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("StateID:");

        txtStateID.setEnabled(false);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Name:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Timer Condition:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Timer Condition Tolerance:");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Outputs:");

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Value after time condition:");

        btnAddStateOutput.setBackground(new java.awt.Color(27, 38, 61));
        btnAddStateOutput.setForeground(new java.awt.Color(255, 255, 255));
        btnAddStateOutput.setText("Add Output");
        btnAddStateOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddStateOutputActionPerformed(evt);
            }
        });

        btnUpdateState.setBackground(new java.awt.Color(27, 38, 61));
        btnUpdateState.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateState.setText("Update State");
        btnUpdateState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateStateActionPerformed(evt);
            }
        });

        btnDeleteState.setBackground(new java.awt.Color(27, 38, 61));
        btnDeleteState.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteState.setText("Delete State");
        btnDeleteState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteStateActionPerformed(evt);
            }
        });

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Value before time condition:");

        btnUpdateSelectedOutput.setBackground(new java.awt.Color(27, 38, 61));
        btnUpdateSelectedOutput.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateSelectedOutput.setText("Update Output");
        btnUpdateSelectedOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSelectedOutputActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(51, 235, 224));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("State properties");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel9.setOpaque(true);

        tblStateOutputs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Val Before TC", "Val After TC"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblStateOutputs);
        if (tblStateOutputs.getColumnModel().getColumnCount() > 0) {
            tblStateOutputs.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnUpdateState, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(txtStateTimerCondition, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtStateName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtStateID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(txtStateTimerConditionTolerance, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnAddStateOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(cmbStateOutput, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8)
                                    .addComponent(txtValueBeforeTimeCondition, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(txtValueAfterTimeCondition, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(btnDeleteState, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(btnUpdateSelectedOutput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteSelectedOutput))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(130, 130, 130)
                        .addComponent(jLabel9))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cmbStateOutput, txtStateName, txtValueBeforeTimeCondition});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnUpdateSelectedOutput)
                    .addComponent(btnDeleteSelectedOutput))
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStateID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmbStateOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStateName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValueBeforeTimeCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtStateTimerCondition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtValueAfterTimeCondition, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStateTimerConditionTolerance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnAddStateOutput)
                        .addGap(17, 17, 17)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdateState)
                    .addComponent(btnDeleteState))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btnDeleteState, cmbStateOutput, jLabel2, jLabel3, jLabel4, jLabel5, txtStateID, txtStateTimerCondition, txtStateTimerConditionTolerance, txtValueAfterTimeCondition});

    }// </editor-fold>//GEN-END:initComponents

    /**
    * Handles the action event triggered by the "Update State" button.
    * 
    * @param evt the `ActionEvent` triggered by clicking the "Update State" button.
    */
    private void btnUpdateStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateStateActionPerformed
        testcontroller.updateState(txtStateID.getText(), txtStateName.getText(),
                txtStateTimerCondition.getText(),
                txtStateTimerConditionTolerance.getText());
    }//GEN-LAST:event_btnUpdateStateActionPerformed

    /**
    * Handles the action event triggered by the "Delete State" button.
    * 
    * @param evt the `ActionEvent` triggered by clicking the "Delete State" button.
    */
    private void btnDeleteStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteStateActionPerformed
        Command deletestatecommand = new DeleteStateCommand(testcontroller, txtStateID.getText());
        deletestatecommand.execute();
        testcontroller.addCommand(deletestatecommand);
    }//GEN-LAST:event_btnDeleteStateActionPerformed

    /**
    * Handles the action event triggered by the "Add State Output" button.
    * 
    * Workflow:
    * - Retrieves the selected output ID and name from the combo box.
    * - Splits the selected item to extract the output ID.
    * - Calls the `addOutputToState` method in the controller to add the output to the state.
    * - Clears the input fields for the value before and after the time condition.
    * 
    * @param evt the `ActionEvent` triggered by clicking the "Add State Output" button.
    */
    private void btnAddStateOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddStateOutputActionPerformed
       String outputIDname = (String)cmbStateOutput.getSelectedItem();
       String[] parts = outputIDname.split(" ", 2);
       testcontroller.addOutputToState(txtStateID.getText(), parts[0], 
                                       txtValueBeforeTimeCondition.getText(), 
                                       txtValueAfterTimeCondition.getText());
       txtValueAfterTimeCondition.setText("");
       txtValueBeforeTimeCondition.setText("");
    }//GEN-LAST:event_btnAddStateOutputActionPerformed

    /**
    * Handles the action event triggered by the "Delete Selected Output" button.
    *
    * Workflow:
    * - Retrieves the selected row from the state outputs table.
    * - If a row is selected, retrieves the output ID from the table.
    * - Calls the `deleteStateOutput` method in the controller to remove the output from the state.
    * 
    * @param evt the `ActionEvent` triggered by clicking the "Delete Selected Output" button.
    */
    private void btnDeleteSelectedOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSelectedOutputActionPerformed
        int selectedRow = tblStateOutputs.getSelectedRow();
            if (selectedRow != -1) {
                String stateoutputID = (String)tblStateOutputs.getValueAt(selectedRow, 0);
                testcontroller.deleteStateOutput(txtStateID.getText(), stateoutputID);
            } 
    }//GEN-LAST:event_btnDeleteSelectedOutputActionPerformed

    /**
    * Handles the action event triggered by the "Update Selected Output" button.
    *
    * Workflow:
    * - Retrieves the selected row from the state outputs table.
    * - If a row is selected, retrieves the output ID, value before time condition, 
    *   and value after time condition from the table.
    * - Calls the `updateStateOutput` method in the controller to update the output in the state.
    * 
    * @param evt the `ActionEvent` triggered by clicking the "Update Selected Output" button.
    */
    private void btnUpdateSelectedOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSelectedOutputActionPerformed
        DefaultTableModel defaulttablemodel = (DefaultTableModel) tblStateOutputs.getModel();
        int selectedRow = tblStateOutputs.getSelectedRow();
        if (selectedRow != -1) {
            String stateoutputID = (String)defaulttablemodel.getValueAt(selectedRow, 0);
            String valuebeforetimecondition = (String)defaulttablemodel.getValueAt(selectedRow, 2);
            String valueaftertimecondition = (String)defaulttablemodel.getValueAt(selectedRow, 3);
           testcontroller.updateStateOutput(txtStateID.getText(),stateoutputID, valuebeforetimecondition, valueaftertimecondition);
        } 
    }//GEN-LAST:event_btnUpdateSelectedOutputActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddStateOutput;
    private javax.swing.JButton btnDeleteSelectedOutput;
    private javax.swing.JButton btnDeleteState;
    private javax.swing.JButton btnUpdateSelectedOutput;
    private javax.swing.JButton btnUpdateState;
    private javax.swing.JComboBox<String> cmbStateOutput;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable tblStateOutputs;
    private javax.swing.JTextField txtStateID;
    private javax.swing.JTextField txtStateName;
    private javax.swing.JTextField txtStateTimerCondition;
    private javax.swing.JTextField txtStateTimerConditionTolerance;
    private javax.swing.JTextField txtValueAfterTimeCondition;
    private javax.swing.JTextField txtValueBeforeTimeCondition;
    // End of variables declaration//GEN-END:variables
}
