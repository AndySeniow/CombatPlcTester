/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

// Package
package combat_plc_tester.view;

// Imports
import combat_plc_tester.controller.TestController;
import combat_plc_tester.model.IO.Input;
import combat_plc_tester.model.IO.InputBit;
import combat_plc_tester.model.IO.InputByte;
import combat_plc_tester.model.IO.InputDInt;
import combat_plc_tester.model.IO.InputDWord;
import combat_plc_tester.model.IO.InputReal;
import combat_plc_tester.model.IO.InputInt;
import combat_plc_tester.model.IO.InputWord;
import combat_plc_tester.model.IO.PlcDataType;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Class: InputListView
 * 
 * Purpose: 
 * This class represents the View component in the Model-View-Controller (MVC) pattern
 * for managing and displaying a list of input variables in the application. It provides
 * a graphical interface for users to interact with input data, including adding, updating, 
 * deleting, and clearing input fields. 
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class InputListView extends javax.swing.JPanel {

    private TestController testcontroller;         // The test controller.
    
    /**
    * Initializes the `InputListView` by setting up its graphical components. 
    */
    public InputListView() {
        initComponents();
    }

     public void setTestController(TestController testcontroller){
        this.testcontroller = testcontroller;
    }
    
    /**
    * Displays the list of input variables in the input table view. This method updates the table model 
    * (`DefaultTableModel`) with the provided list of inputs and configures other UI components to reflect 
    * the current input data.
    * 
    * Workflow:
    * - Clears the existing rows in the table model.
    * - Iterates over the provided list of inputs and populates the table with the input data based on 
    *   their type (BIT, BYTE, WORD).
    * - Updates additional UI components (combo box, text fields) to match the last input in the list.
    * - Resets the UI components if the input list is null or empty.
    * 
    * @param inputList a list of `Input` objects to be displayed. Each input contains details such as ID, 
    *                  name, data type, start address, bit address, and initial value.
    */
    public void viewInputs(List<Input> inputList) {
        DefaultTableModel defaulttablemodel = (DefaultTableModel) tblInputList.getModel();
        if (!(inputList== null) && !(inputList.isEmpty())) {
            defaulttablemodel.setRowCount(0);
            for (Input input : inputList) {
                if (input instanceof InputBit) {
                    defaulttablemodel.addRow(new Object[]{input.getInputID(), input.getName(), PlcDataType.BIT.name(), input.getStartAddress(), input.getBitAddress(), input.getInitialValue()});
                    cmbInputDatatype.setSelectedItem("BIT");
                } else if (input instanceof InputByte) {
                    defaulttablemodel.addRow(new Object[]{input.getInputID(), input.getName(), PlcDataType.BYTE.name(), input.getStartAddress(), "", input.getInitialValue()});
                    cmbInputDatatype.setSelectedItem("BYTE");
                } else if (input instanceof InputWord) {
                    defaulttablemodel.addRow(new Object[]{input.getInputID(), input.getName(), PlcDataType.WORD.name(), input.getStartAddress(), "", input.getInitialValue()});
                    cmbInputDatatype.setSelectedItem("WORD");
                }
                else if (input instanceof InputDWord) {
                    defaulttablemodel.addRow(new Object[]{input.getInputID(), input.getName(), PlcDataType.DWORD.name(), input.getStartAddress(), "", input.getInitialValue()});
                    cmbInputDatatype.setSelectedItem("DWORD");
                }
                else if (input instanceof InputInt) {
                    defaulttablemodel.addRow(new Object[]{input.getInputID(), input.getName(), PlcDataType.INT.name(), input.getStartAddress(), "", input.getInitialValue()});
                    cmbInputDatatype.setSelectedItem("INT");
                }
                 else if (input instanceof InputDInt) {
                    defaulttablemodel.addRow(new Object[]{input.getInputID(), input.getName(), PlcDataType.DINT.name(), input.getStartAddress(), "", input.getInitialValue()});
                    cmbInputDatatype.setSelectedItem("DINT");
                }
                else if (input instanceof InputReal) {
                    defaulttablemodel.addRow(new Object[]{input.getInputID(), input.getName(), PlcDataType.REAL.name(), input.getStartAddress(), "", input.getInitialValue()});
                    cmbInputDatatype.setSelectedItem("REAL");
                }
            }
            Input input = inputList.get(inputList.size() - 1);
            txtInputID.setText(input.getInputID());
            txtInputName.setText(input.getName());
            txtInputStartaddress.setText(String.valueOf(input.getStartAddress()));
            txtInputBitaddress.setText(String.valueOf(input.getBitAddress()));
            txtInputInitialvalue.setText(String.valueOf(input.getInitialValue()));
        } else {
            defaulttablemodel.setRowCount(0);
             txtInputID.setText("");
        txtInputName.setText("");
        txtInputStartaddress.setText("");
        cmbInputDatatype.setSelectedItem("BIT");
         txtInputBitaddress.setEnabled(true);
        txtInputBitaddress.setText("");
        txtInputInitialvalue.setText("");
        }
    }
    
    public void resetInputToDefault(){    
        viewInputs(null);
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
        tblInputList = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtInputID = new javax.swing.JTextField();
        txtInputName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cmbInputDatatype = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtInputStartaddress = new javax.swing.JTextField();
        txtInputBitaddress = new javax.swing.JTextField();
        txtInputInitialvalue = new javax.swing.JTextField();
        btnAddInput = new javax.swing.JButton();
        btnUpdateInput = new javax.swing.JButton();
        btnDeleteInput = new javax.swing.JButton();
        btnClearTextFieldsInput = new javax.swing.JButton();

        setBackground(new java.awt.Color(50, 70, 110));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "List of Inputs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        setPreferredSize(new java.awt.Dimension(925, 550));

        tblInputList.setModel(new javax.swing.table.DefaultTableModel(
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
        tblInputList.setDragEnabled(true);
        tblInputList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblInputList.getTableHeader().setReorderingAllowed(false);
        tblInputList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblInputListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblInputList);

        jPanel2.setBackground(new java.awt.Color(50, 70, 110));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("ID");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name");

        txtInputID.setEditable(false);
        txtInputID.setForeground(new java.awt.Color(204, 204, 204));
        txtInputID.setEnabled(false);
        txtInputID.setRequestFocusEnabled(false);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Data type");

        cmbInputDatatype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BIT", "BYTE", "WORD", "DWORD", "INT", "DINT", "REAL" }));
        cmbInputDatatype.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbInputDatatypeItemStateChanged(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Start address");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Bit address");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Initial value");

        btnAddInput.setBackground(new java.awt.Color(27, 38, 61));
        btnAddInput.setForeground(new java.awt.Color(255, 255, 255));
        btnAddInput.setText("Add");
        btnAddInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddInputActionPerformed(evt);
            }
        });

        btnUpdateInput.setBackground(new java.awt.Color(27, 38, 61));
        btnUpdateInput.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateInput.setText("Update");
        btnUpdateInput.setEnabled(false);
        btnUpdateInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateInputActionPerformed(evt);
            }
        });

        btnDeleteInput.setBackground(new java.awt.Color(27, 38, 61));
        btnDeleteInput.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteInput.setText("Delete");
        btnDeleteInput.setEnabled(false);
        btnDeleteInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteInputActionPerformed(evt);
            }
        });

        btnClearTextFieldsInput.setBackground(new java.awt.Color(27, 38, 61));
        btnClearTextFieldsInput.setForeground(new java.awt.Color(255, 255, 255));
        btnClearTextFieldsInput.setText("Clear");
        btnClearTextFieldsInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearTextFieldsInputActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtInputID, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(3, 3, 3)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cmbInputDatatype, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtInputName, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(74, 74, 74)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtInputStartaddress)
                        .addComponent(txtInputBitaddress, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtInputInitialvalue, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addComponent(btnClearTextFieldsInput)
                .addGap(45, 45, 45)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnUpdateInput)
                    .addComponent(btnDeleteInput)
                    .addComponent(btnAddInput))
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddInput, btnUpdateInput});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnDeleteInput)
                        .addGap(1, 1, 1))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(txtInputID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(10, 10, 10)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(txtInputName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel3)
                                .addComponent(cmbInputDatatype, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(txtInputStartaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(10, 10, 10)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(txtInputBitaddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(btnAddInput)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnClearTextFieldsInput)
                                        .addComponent(btnUpdateInput))))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel6)
                                .addComponent(txtInputInitialvalue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 903, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(330, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(241, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
    * Handles the mouse click event on the `tblInputList` table.
    * 
    * @param evt the `MouseEvent` triggered when a user clicks on a row in the `tblInputList` table.
    */
    private void tblInputListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblInputListMouseClicked
    int row = tblInputList.rowAtPoint(evt.getPoint());
        if (row >= 0) {
            txtInputID.setText(tblInputList.getValueAt(row, 0).toString());
            txtInputName.setText(tblInputList.getValueAt(row, 1).toString());
            cmbInputDatatype.setSelectedItem(tblInputList.getValueAt(row, 2).toString());
            txtInputStartaddress.setText(tblInputList.getValueAt(row, 3).toString());
            txtInputBitaddress.setText(tblInputList.getValueAt(row, 4).toString());
            txtInputInitialvalue.setText(tblInputList.getValueAt(row, 5).toString());
            btnAddInput.setEnabled(false);
            cmbInputDatatype.setEnabled(false);
            btnUpdateInput.setEnabled(true);
            btnDeleteInput.setEnabled(true);
        }
    }//GEN-LAST:event_tblInputListMouseClicked

    /**
    * Handles the item state change event for the `cmbInputDatatype` combo box.
    * 
    * This method dynamically updates the state of the `txtInputBitaddress` text field 
    * based on the selected data type from the combo box. For certain data types 
    * (BYTE, WORD, DWORD, INT, DINT, REAL), the `txtInputBitaddress` field 
    * is disabled and cleared, as these types do not require a bit address. For other 
    * types, the field remains enabled.
    * 
    * @param evt the `ItemEvent` triggered when the selected item in the `cmbInputDatatype` 
    *            combo box changes.
    */
    private void cmbInputDatatypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbInputDatatypeItemStateChanged
        switch (cmbInputDatatype.getSelectedItem().toString()) {
            case "BYTE":
            case "WORD":
            case "DWORD":
            case "INT":
            case "DINT":
            case "REAL":
                txtInputBitaddress.setText("");
                txtInputBitaddress.setEnabled(false);
                break;
            default:
                txtInputBitaddress.setEnabled(true);
                break;
        }
    }//GEN-LAST:event_cmbInputDatatypeItemStateChanged

    /**
    * Handles the action event triggered by the "Add Input" button.
    * 
    * Workflow:
    * - Retrieves values for the input name, data type, start address, bit address, and initial value.
    * - Calls the `addInput` method in the `TestController` with the collected values.
    * - Resets UI components to allow adding another input and disables update/delete options.
    * 
    * @param evt the `ActionEvent` triggered by pressing the "Add Input" button.
    */
    private void btnAddInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInputActionPerformed
        testcontroller.addInput(txtInputName.getText(), cmbInputDatatype.getSelectedItem().toString(),
                txtInputStartaddress.getText(), txtInputBitaddress.getText(),
                txtInputInitialvalue.getText());
        cmbInputDatatype.setEnabled(true);
        btnAddInput.setEnabled(true);
        btnUpdateInput.setEnabled(false);
        btnDeleteInput.setEnabled(false);
    }//GEN-LAST:event_btnAddInputActionPerformed

    /**
    * Handles the action event triggered by the "Update Input" button.
    * 
    * Workflow:
    * - Retrieves the input ID, name, start address, bit address, and initial value from the text fields.
    * - Calls the `updateInput` method in the `TestController` to update the input.
    * - Updates the UI components to prevent changes to the data type and keep update/delete options enabled.
    * 
    * @param evt the `ActionEvent` triggered by pressing the "Update Input" button.
    */
    private void btnUpdateInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateInputActionPerformed
        testcontroller.updateInput(txtInputID.getText(), txtInputName.getText(),
                                   txtInputStartaddress.getText(), txtInputBitaddress.getText(),
                                   txtInputInitialvalue.getText());
           
        cmbInputDatatype.setEnabled(false);
        btnAddInput.setEnabled(false);
        btnUpdateInput.setEnabled(true);
        btnDeleteInput.setEnabled(true);
    }//GEN-LAST:event_btnUpdateInputActionPerformed

    /**
    * Handles the action event triggered by the "Delete Input" button.
    * 
    * Workflow:
    * - Retrieves the input ID from the text field.
    * - Calls the `deleteInputByID` method in the `TestController` to remove the input.
    * - Clears the text fields and resets the UI components for a new input operation.
    * 
    * @param evt the `ActionEvent` triggered by pressing the "Delete Input" button.
    */
    private void btnDeleteInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteInputActionPerformed
        testcontroller.deleteInputByID(txtInputID.getText());
        txtInputID.setText("");
        txtInputName.setText("");
        cmbInputDatatype.setEnabled(true);
        txtInputStartaddress.setText("");
        txtInputBitaddress.setText("");
        txtInputInitialvalue.setText("");
        btnAddInput.setEnabled(true);
        btnUpdateInput.setEnabled(false);
        btnDeleteInput.setEnabled(false);
    }//GEN-LAST:event_btnDeleteInputActionPerformed

    /**
    * Handles the action event triggered by the "Clear Text Fields" button for inputs.
    *
    * Workflow:
    * - Clears the text fields for input ID, name, start address, bit address, and initial value.
    * - Enables the data type combo box to allow selecting a data type for a new input.
    * - Resets the state of the buttons to allow adding new inputs while disabling update and delete actions.
    * 
    * @param evt the `ActionEvent` triggered by pressing the "Clear Text Fields" button.
    */
    private void btnClearTextFieldsInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearTextFieldsInputActionPerformed
        txtInputID.setText("");
        txtInputName.setText("");
        cmbInputDatatype.setEnabled(true);
        txtInputStartaddress.setText("");
        txtInputBitaddress.setText("");
        txtInputInitialvalue.setText("");
        btnAddInput.setEnabled(true);
        btnUpdateInput.setEnabled(false);
        btnDeleteInput.setEnabled(false);
    }//GEN-LAST:event_btnClearTextFieldsInputActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddInput;
    private javax.swing.JButton btnClearTextFieldsInput;
    private javax.swing.JButton btnDeleteInput;
    private javax.swing.JButton btnUpdateInput;
    private javax.swing.JComboBox<String> cmbInputDatatype;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblInputList;
    private javax.swing.JTextField txtInputBitaddress;
    private javax.swing.JTextField txtInputID;
    private javax.swing.JTextField txtInputInitialvalue;
    private javax.swing.JTextField txtInputName;
    private javax.swing.JTextField txtInputStartaddress;
    // End of variables declaration//GEN-END:variables
}
