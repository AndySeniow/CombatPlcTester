/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

// Package
package combat_plc_tester.view;

// Imports
import combat_plc_tester.controller.TestController;
import combat_plc_tester.model.IO.Output;
import combat_plc_tester.model.IO.OutputBit;
import combat_plc_tester.model.IO.OutputByte;
import combat_plc_tester.model.IO.OutputDInt;
import combat_plc_tester.model.IO.OutputDWord;
import combat_plc_tester.model.IO.OutputInt;
import combat_plc_tester.model.IO.OutputReal;
import combat_plc_tester.model.IO.OutputWord;
import combat_plc_tester.model.IO.PlcDataType;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Class: OutputListView
 * 
 * Purpose: 
 * This class represents the View component in the Model-View-Controller (MVC) pattern
 * for managing and displaying a list of output variables in the application. It provides
 * a graphical interface for users to interact with output data, including adding, updating, 
 * deleting, and clearing output fields. 
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class OutputListView extends javax.swing.JPanel {

    private TestController testcontroller;          // The test controller.
    
    /**
    * Initializes the `OutputListView` by setting up its graphical components. 
    */
    public OutputListView() {
        initComponents();
    }

    public void setTestController(TestController testcontroller){
        this.testcontroller = testcontroller;
    }
    
    /**
    * Displays the list of output variables in the output table view. This method updates the table model 
    * (`DefaultTableModel`) with the provided list of outputs and configures other UI components to reflect 
    * the current output data.
    * 
    * Workflow:
    * - Clears the existing rows in the table model.
    * - Iterates over the provided list of outputs and populates the table with the output data based on 
    *   their type (BIT, BYTE, WORD).
    * - Updates additional UI components (combo box, text fields) to match the last output in the list.
    * - Resets the UI components if the output list is null or empty.
    * 
    * @param outputList a list of `Output` objects to be displayed. Each output contains details such as ID, 
    *                  name, data type, start address, bit address, and initial value.
    */
    public void viewOutputs(List<Output> outputList) {
        DefaultTableModel defaulttablemodel = (DefaultTableModel) tblOutputList.getModel();
        if (!(outputList == null) && !(outputList.isEmpty())) {
            defaulttablemodel.setRowCount(0);
            for (Output output : outputList) {
                if (output instanceof OutputBit) {
                    defaulttablemodel.addRow(new Object[]{output.getOutputID(), output.getName(), PlcDataType.BIT.name(), output.getStartAddress(), output.getBitAddress(), output.getInitialValue()});
                    cmbOutputDatatype.setSelectedItem("BIT");
                } else if (output instanceof OutputByte) {
                    defaulttablemodel.addRow(new Object[]{output.getOutputID(), output.getName(), PlcDataType.BYTE.name(), output.getStartAddress(), "", output.getInitialValue()});
                    cmbOutputDatatype.setSelectedItem("BYTE");
                } else if (output instanceof OutputWord) {
                    defaulttablemodel.addRow(new Object[]{output.getOutputID(), output.getName(), PlcDataType.WORD.name(), output.getStartAddress(), "", output.getInitialValue()});
                    cmbOutputDatatype.setSelectedItem("WORD");
                } else if (output instanceof OutputDWord) {
                    defaulttablemodel.addRow(new Object[]{output.getOutputID(), output.getName(), PlcDataType.DWORD.name(), output.getStartAddress(), "", output.getInitialValue()});
                    cmbOutputDatatype.setSelectedItem("DWORD");
                } else if (output instanceof OutputInt) {
                    defaulttablemodel.addRow(new Object[]{output.getOutputID(), output.getName(), PlcDataType.INT.name(), output.getStartAddress(), "", output.getInitialValue()});
                    cmbOutputDatatype.setSelectedItem("INT");
                } else if (output instanceof OutputDInt) {
                    defaulttablemodel.addRow(new Object[]{output.getOutputID(), output.getName(), PlcDataType.DINT.name(), output.getStartAddress(), "", output.getInitialValue()});
                    cmbOutputDatatype.setSelectedItem("DINT");
                } else if (output instanceof OutputReal) {
                    defaulttablemodel.addRow(new Object[]{output.getOutputID(), output.getName(), PlcDataType.REAL.name(), output.getStartAddress(), "", output.getInitialValue()});
                    cmbOutputDatatype.setSelectedItem("REAL");
                }
            }
            Output output = outputList.get(outputList.size() - 1);
            txtOutputID.setText(output.getOutputID());
            txtOutputName.setText(output.getName());
            txtOutputStartaddress.setText(String.valueOf(output.getStartAddress()));
            txtOutputBitaddress.setText(String.valueOf(output.getBitAddress()));
            txtOutputInitialvalue.setText(String.valueOf(output.getInitialValue()));
        } else {
            defaulttablemodel.setRowCount(0);
            txtOutputID.setText("");
            txtOutputName.setText("");
            txtOutputStartaddress.setText("");
            cmbOutputDatatype.setSelectedItem("BIT");
            txtOutputBitaddress.setEnabled(true);
            txtOutputBitaddress.setText("");
            txtOutputInitialvalue.setText("");
        }
    }
    
    public void resetOutputToDefault(){
          viewOutputs(null);
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

        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtOutputID = new javax.swing.JTextField();
        txtOutputName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        cmbOutputDatatype = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtOutputStartaddress = new javax.swing.JTextField();
        txtOutputInitialvalue = new javax.swing.JTextField();
        btnAddOutput = new javax.swing.JButton();
        btnUpdateOutput = new javax.swing.JButton();
        btnDeleteOutput = new javax.swing.JButton();
        btnClearTextFieldsOutput = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        txtOutputBitaddress = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblOutputList = new javax.swing.JTable();

        setBackground(new java.awt.Color(50, 70, 110));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "List of Outputs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        setPreferredSize(new java.awt.Dimension(925, 550));

        jPanel3.setBackground(new java.awt.Color(50, 70, 110));
        jPanel3.setPreferredSize(new java.awt.Dimension(903, 180));

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("ID");

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Name");

        txtOutputID.setEditable(false);
        txtOutputID.setForeground(new java.awt.Color(204, 204, 204));
        txtOutputID.setEnabled(false);
        txtOutputID.setRequestFocusEnabled(false);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Data type");

        cmbOutputDatatype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BIT", "BYTE", "WORD", "DWORD", "INT", "DINT", "REAL" }));
        cmbOutputDatatype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbOutputDatatypeItemStateChanged(evt);
            }
        });

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Bit address");

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Initial value");

        btnAddOutput.setBackground(new java.awt.Color(27, 38, 61));
        btnAddOutput.setForeground(new java.awt.Color(255, 255, 255));
        btnAddOutput.setText("Add");
        btnAddOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddOutputActionPerformed(evt);
            }
        });

        btnUpdateOutput.setBackground(new java.awt.Color(27, 38, 61));
        btnUpdateOutput.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateOutput.setText("Update");
        btnUpdateOutput.setEnabled(false);
        btnUpdateOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateOutputActionPerformed(evt);
            }
        });

        btnDeleteOutput.setBackground(new java.awt.Color(27, 38, 61));
        btnDeleteOutput.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteOutput.setText("Delete");
        btnDeleteOutput.setEnabled(false);
        btnDeleteOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteOutputActionPerformed(evt);
            }
        });

        btnClearTextFieldsOutput.setBackground(new java.awt.Color(27, 38, 61));
        btnClearTextFieldsOutput.setForeground(new java.awt.Color(255, 255, 255));
        btnClearTextFieldsOutput.setText("Clear");
        btnClearTextFieldsOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearTextFieldsOutputActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Start address");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOutputID, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(cmbOutputDatatype, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtOutputName, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOutputInitialvalue, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOutputStartaddress, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtOutputBitaddress, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnClearTextFieldsOutput)
                .addGap(55, 55, 55)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteOutput)
                    .addComponent(btnUpdateOutput)
                    .addComponent(btnAddOutput))
                .addGap(79, 79, 79))
        );

        jPanel3Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddOutput, btnUpdateOutput});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtOutputStartaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel10))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(txtOutputBitaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnClearTextFieldsOutput))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtOutputInitialvalue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12)))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtOutputID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel8)
                                .addComponent(txtOutputName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel9)
                                .addComponent(cmbOutputDatatype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnAddOutput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnUpdateOutput)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeleteOutput)))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        tblOutputList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Data type", "Start address", "Bit address", "Initial value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblOutputList.setDragEnabled(true);
        tblOutputList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblOutputList.getTableHeader().setReorderingAllowed(false);
        tblOutputList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOutputListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblOutputList);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 888, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 21, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
    * Handles the mouse click event on the `tblOutputList` table.
    * 
    * @param evt the `MouseEvent` triggered when a user clicks on a row in the `tblOutputList` table.
    */
    private void tblOutputListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOutputListMouseClicked
        int row = tblOutputList.rowAtPoint(evt.getPoint());
        if (row >= 0) {
            txtOutputID.setText(tblOutputList.getValueAt(row, 0).toString());
            txtOutputName.setText(tblOutputList.getValueAt(row, 1).toString());
            cmbOutputDatatype.setSelectedItem(tblOutputList.getValueAt(row, 2).toString());
            txtOutputStartaddress.setText(tblOutputList.getValueAt(row, 3).toString());
            txtOutputBitaddress.setText(tblOutputList.getValueAt(row, 4).toString());
            txtOutputInitialvalue.setText(tblOutputList.getValueAt(row, 5).toString());
            btnAddOutput.setEnabled(false);
            cmbOutputDatatype.setEnabled(false);
            btnUpdateOutput.setEnabled(true);
            btnDeleteOutput.setEnabled(true);
        }
    }//GEN-LAST:event_tblOutputListMouseClicked

    /**
    * Handles the action event triggered by the "Clear Text Fields" button for inputs.
    *
    * Workflow:
    * - Clears the text fields for output ID, name, start address, bit address, and initial value.
    * - Enables the data type combo box to allow selecting a data type for a new output.
    * - Resets the state of the buttons to allow adding new outputs while disabling update and delete actions.
    * 
    * @param evt the `ActionEvent` triggered by pressing the "Clear Text Fields" button.
    */
    private void btnClearTextFieldsOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearTextFieldsOutputActionPerformed
        txtOutputID.setText("");
        txtOutputName.setText("");
        cmbOutputDatatype.setEnabled(true);
        txtOutputStartaddress.setText("");
        txtOutputBitaddress.setEnabled(true);
        txtOutputBitaddress.setText("");
        txtOutputInitialvalue.setText("");
        btnAddOutput.setEnabled(true);
        btnUpdateOutput.setEnabled(false);
        btnDeleteOutput.setEnabled(false);
    }//GEN-LAST:event_btnClearTextFieldsOutputActionPerformed

    /**
    * Handles the action event triggered by the "Delete Output" button.
    * 
    * Workflow:
    * - Retrieves the output ID from the text field.
    * - Calls the `deleteOutputByID` method in the `TestController` to remove the output.
    * - Clears the text fields and resets the UI components for a new output operation.
    * 
    * @param evt the `ActionEvent` triggered by pressing the "Delete Output" button.
    */
    private void btnDeleteOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteOutputActionPerformed
        testcontroller.deleteOutputByID(txtOutputID.getText());
        txtOutputID.setText("");
        txtOutputName.setText("");
        cmbOutputDatatype.setEnabled(true);
        txtOutputStartaddress.setText("");
        txtOutputBitaddress.setText("");
        txtOutputInitialvalue.setText("");
        btnAddOutput.setEnabled(true);
        btnUpdateOutput.setEnabled(false);
        btnDeleteOutput.setEnabled(false);
    }//GEN-LAST:event_btnDeleteOutputActionPerformed

    /**
    * Handles the action event triggered by the "Update Output" button.
    * 
    * Workflow:
    * - Retrieves the output ID, name, start address, bit address, and initial value from the text fields.
    * - Calls the `updateOutput` method in the `TestController` to update the output.
    * - Updates the UI components to prevent changes to the data type and keep update/delete options enabled.
    * 
    * @param evt the `ActionEvent` triggered by pressing the "Update Output" button.
    */
    private void btnUpdateOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateOutputActionPerformed
        testcontroller.updateOutput(txtOutputID.getText(), txtOutputName.getText(),
                txtOutputStartaddress.getText(), txtOutputBitaddress.getText(),
                txtOutputInitialvalue.getText());
        cmbOutputDatatype.setEnabled(false);
        btnAddOutput.setEnabled(false);
        btnUpdateOutput.setEnabled(true);
        btnDeleteOutput.setEnabled(true);
    }//GEN-LAST:event_btnUpdateOutputActionPerformed

    /**
    * Handles the action event triggered by the "Add Output" button.
    * 
    * Workflow:
    * - Retrieves values for the Output name, data type, start address, bit address, and initial value.
    * - Calls the `addOutput` method in the `TestController` with the collected values.
    * - Resets UI components to allow adding another output and disables update/delete options.
    * 
    * @param evt the `ActionEvent` triggered by pressing the "Add Output" button.
    */
    private void btnAddOutputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddOutputActionPerformed
        testcontroller.addOutput(txtOutputName.getText(), cmbOutputDatatype.getSelectedItem().toString(),
                txtOutputStartaddress.getText(), txtOutputBitaddress.getText(),
                txtOutputInitialvalue.getText());
        cmbOutputDatatype.setEnabled(false);
        btnAddOutput.setEnabled(false);
        btnUpdateOutput.setEnabled(true);
        btnDeleteOutput.setEnabled(true);
    }//GEN-LAST:event_btnAddOutputActionPerformed

    /**
    * Handles the item state change event for the `cmbOutputDatatype` combo box.
    * 
    * This method dynamically updates the state of the `txtOutputBitaddress` text field 
    * based on the selected data type from the combo box. For certain data types 
    * (BYTE, WORD, DWORD, INT, DINT, REAL), the `txtOutputBitaddress` field 
    * is disabled and cleared, as these types do not require a bit address. For other 
    * types, the field remains enabled.
    * 
    * @param evt the `ItemEvent` triggered when the selected item in the `cmbOutputDatatype` 
    *            combo box changes.
    */
    private void cmbOutputDatatypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbOutputDatatypeItemStateChanged
        switch (cmbOutputDatatype.getSelectedItem().toString()) {
            case "BYTE":
            case "WORD":
            case "DWORD":
            case "INT":
            case "DINT":
            case "REAL":
                txtOutputBitaddress.setText("");
                txtOutputBitaddress.setEnabled(false);
                break;
            default:
                txtOutputBitaddress.setEnabled(true);
                break;
        }
    }//GEN-LAST:event_cmbOutputDatatypeItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddOutput;
    private javax.swing.JButton btnClearTextFieldsOutput;
    private javax.swing.JButton btnDeleteOutput;
    private javax.swing.JButton btnUpdateOutput;
    private javax.swing.JComboBox<String> cmbOutputDatatype;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblOutputList;
    private javax.swing.JTextField txtOutputBitaddress;
    private javax.swing.JTextField txtOutputID;
    private javax.swing.JTextField txtOutputInitialvalue;
    private javax.swing.JTextField txtOutputName;
    private javax.swing.JTextField txtOutputStartaddress;
    // End of variables declaration//GEN-END:variables
}
