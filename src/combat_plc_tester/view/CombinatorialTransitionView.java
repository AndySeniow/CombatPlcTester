/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

// Package
package combat_plc_tester.view;

// Imports
import combat_plc_tester.controller.Command;
import combat_plc_tester.controller.DeleteTransitionCommand;
import combat_plc_tester.controller.TestController;
import combat_plc_tester.model.moore.GraphElement;
import combat_plc_tester.model.IO.Input;
import combat_plc_tester.model.IO.InputBit;
import combat_plc_tester.model.moore.Transition;
import combat_plc_tester.model.moore.SequentialTransition;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Class: CombinatorialTransitionView
 * 
 * Purpose:
 * Serves as the View component in the Model-View-Controller (MVC) pattern for managing 
 * combinatorial transitions. This class is responsible for presenting the data related 
 * to combinatorial transitions and providing a user interface for interaction. 
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class CombinatorialTransitionView extends javax.swing.JPanel {

    private TestController testcontroller;          // The test controller.
    
    /**
    * Initializes the components of the `CombinatorialTransitionView` class, which represents the graphical 
    * user interface for managing combinatorial transitions in the Combat PLC Tester application.
    */
    public CombinatorialTransitionView() {
        initComponents();
    }

    public void setTestController(TestController testcontroller){
        this.testcontroller = testcontroller;
    }
    
    /**
    * Updates the `CombinatorialTransitionView` with a list of inputs that are not of type `InputBit`. 
    * Populates the transition input combo box and adjusts the input value field's state based on 
    * the availability of inputs.
    *
    * Workflow:
    * 1. Clears all items from the `cmbTransitionInput` combo box.
    * 2. Iterates through the provided list of inputs:
    *    - Adds each input to the combo box, excluding inputs of type `InputBit`.
    * 3. Checks if the combo box contains any items:
    *    - If no items exist, disables and clears the transition input value text field.
    *    - Otherwise, enables the text field for user input.
    * 
    * @param inputList A list of `Input` objects to populate the combo box.
    */
    public void viewInputs(List<Input> inputList) {
        cmbTransitionInput.removeAllItems();
        for (Input input : inputList) {
            if (!(input instanceof InputBit)) {
                cmbTransitionInput.addItem(input.getInputID() + " " + input.getName());
            }
        }
        if (cmbTransitionInput.getItemCount() == 0) {
            txtTransitionInputValue.setEnabled(false);
            txtTransitionInputValue.setText("");
        } else {
            txtTransitionInputValue.setEnabled(true);
        }
    }
    
    /**
    * Populates empty combo box..
    * 
    */
    public void resetviewInputs(){    
         cmbTransitionInput.removeAllItems();
    } 
    
    /**
    * Updates the `CombinatorialTransitionView` with a list of sequential transitions from the provided 
    * list of `GraphElement` objects. Populates the combo box with the transition IDs and names of 
    * sequential transitions.
    *
    * Workflow:
    * 1. Clears all items from the `cmbSequentialTransitions` combo box.
    * 2. Iterates through the provided `GraphElementList`:
    *    - Casts each element to a `Transition`.
    *    - Checks if the transition is an instance of `SequentialTransition`.
    *    - Retrieves the name of the transition or assigns an empty string if the name is `null`.
    *    - Adds the transition ID and name to the combo box.
    *
    * @param GraphElementList A list of `GraphElement` objects, some of which may be `SequentialTransition` instances.
    */
    public void viewSequentialTransitions(List<GraphElement> GraphElementList) {
        cmbSequentialTransitions.removeAllItems();
        String transitionname;
        for (GraphElement graphElement : GraphElementList) {
            Transition transition = (Transition) graphElement;
            if (transition instanceof SequentialTransition) {
                if (transition.getName() == null) {
                    transitionname = "";
                } else {
                    transitionname = transition.getName();
                }

                cmbSequentialTransitions.addItem(transition.getTransitionID() + " " + transitionname);
            }
        }
    }
    
    /**
    * Populates the `tblSequentialTransitions` table with a list of sequential transitions 
    * associated with a specific combinatorial transition. Each row in the table represents a 
    * sequential transition, including its ID and name.
    *
    * Workflow:
    * 1. Retrieves the `DefaultTableModel` of `tblSequentialTransitions` and clears existing rows.
    * 2. Checks if the provided list of sequential transitions is not `null` and not empty.
    * 3. Iterates through the list of sequential transitions:
    *    - For each transition, adds a new row to the table containing its ID and name.
    *
    * @param sequentialTransitionList A list of `SequentialTransition` objects to be displayed in the table.
    */
    public void viewSequentialTransitionsFromCombinatorialTransition(List<SequentialTransition> sequentialTransitionList) {
        DefaultTableModel defaulttablemodel = (DefaultTableModel) tblSequentialTransitions.getModel();
        defaulttablemodel.setRowCount(0);
        if (sequentialTransitionList != null) {
            if (!sequentialTransitionList.isEmpty()) {

                for (SequentialTransition sequelTransition : sequentialTransitionList) {
                    defaulttablemodel.addRow(new Object[]{sequelTransition.getTransitionID(),
                        sequelTransition.getName(),});
                }
            }
        }
    }
    
    /**
    * Populates the `tblCombinatorialTransitionInputs` table with a list of inputs associated 
    * with a transition. Each row in the table represents an input, including its ID, name, and value.
    *
    * Workflow:
    * 1. Retrieves the `DefaultTableModel` of `tblCombinatorialTransitionInputs` and clears existing rows.
    * 2. Checks if the provided list of transition inputs is not `null` and not empty.
    * 3. Iterates through the list of transition inputs:
    *    - For each input, adds a new row to the table containing its ID, name, and value.
    *
    * @param transtioninputList A list of `Input` objects associated with a transition to be displayed in the table.
    */
    public void viewTransitionInputs(List<Input> transtioninputList) {
        DefaultTableModel defaulttablemodel = (DefaultTableModel) tblCombinatorialTransitionInputs.getModel();
        defaulttablemodel.setRowCount(0);
        if (transtioninputList != null) {
            if (!transtioninputList.isEmpty()) {

                for (Input transitioninput : transtioninputList) {
                    defaulttablemodel.addRow(new Object[]{transitioninput.getInputID(),
                        transitioninput.getName(),
                        transitioninput.getValue().toString(),});
                }
            }
        }
    }
    
    /**
    * Displays the details of the specified transition in the combinatorial transition view. 
    * If a transition is provided, its ID and name are displayed in their respective text fields.
    * If no transition is provided (`null`), the fields are cleared.
    *
    * Workflow:
    * 1. Checks if the provided `transition` is not `null`.
    *    - If true:
    *      - Sets the `txtCombinatorialTransitionID` field with the transition's ID.
    *      - Sets the `txtTransitionName` field with the transition's name.
    *    - If false:
    *      - Clears both fields by setting their text to an empty string.
    *
    * @param transition The `Transition` object whose details are to be displayed. 
    *                   If `null`, the fields are reset.
    */
    public void viewTransition(Transition transition) {
        if (transition != null) {
            txtCombinatorialTransitionID.setText(transition.getTransitionID());
            txtTransitionName.setText(transition.getName());
        } else {
            txtCombinatorialTransitionID.setText("");
            txtTransitionName.setText("");
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

        btnDeleteSelectedInput = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCombinatorialTransitionInputs = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtCombinatorialTransitionID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTransitionName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtTransitionInputValue = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnAddTransitionInput = new javax.swing.JButton();
        btnUpdateTransition = new javax.swing.JButton();
        btnDeleteTransition = new javax.swing.JButton();
        btnUpdateSelectedInput = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cmbSequentialTransitions = new javax.swing.JComboBox<>();
        btnAddSequentialTransition = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSequentialTransitions = new javax.swing.JTable();
        btnDeleteSelectedSeqTransition = new javax.swing.JButton();
        cmbTransitionInput = new javax.swing.JComboBox<>();

        setBackground(new java.awt.Color(50, 70, 110));
        setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Combinatorial Transition: Sequential Transitions & Inputs", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12), new java.awt.Color(255, 255, 255))); // NOI18N
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(340, 515));

        btnDeleteSelectedInput.setBackground(new java.awt.Color(27, 38, 61));
        btnDeleteSelectedInput.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteSelectedInput.setText("Delete Input");
        btnDeleteSelectedInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSelectedInputActionPerformed(evt);
            }
        });

        tblCombinatorialTransitionInputs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Value"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblCombinatorialTransitionInputs);
        if (tblCombinatorialTransitionInputs.getColumnModel().getColumnCount() > 0) {
            tblCombinatorialTransitionInputs.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Transition ID:");

        txtCombinatorialTransitionID.setEnabled(false);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Name:");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Inputs:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Value:");

        btnAddTransitionInput.setBackground(new java.awt.Color(27, 38, 61));
        btnAddTransitionInput.setForeground(new java.awt.Color(255, 255, 255));
        btnAddTransitionInput.setText("Add Input");
        btnAddTransitionInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddTransitionInputActionPerformed(evt);
            }
        });

        btnUpdateTransition.setBackground(new java.awt.Color(27, 38, 61));
        btnUpdateTransition.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateTransition.setText("Update Transition");
        btnUpdateTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateTransitionActionPerformed(evt);
            }
        });

        btnDeleteTransition.setBackground(new java.awt.Color(27, 38, 61));
        btnDeleteTransition.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteTransition.setText("Delete Transition");
        btnDeleteTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteTransitionActionPerformed(evt);
            }
        });

        btnUpdateSelectedInput.setBackground(new java.awt.Color(27, 38, 61));
        btnUpdateSelectedInput.setForeground(new java.awt.Color(255, 255, 255));
        btnUpdateSelectedInput.setText("Update Input");
        btnUpdateSelectedInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateSelectedInputActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(51, 235, 224));
        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Combinatorial Transition ");
        jLabel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel9.setOpaque(true);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Sequential Transitions:");

        btnAddSequentialTransition.setBackground(new java.awt.Color(27, 38, 61));
        btnAddSequentialTransition.setForeground(new java.awt.Color(255, 255, 255));
        btnAddSequentialTransition.setText("Add Seq Transition");
        btnAddSequentialTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSequentialTransitionActionPerformed(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(51, 235, 224));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Properties");
        jLabel10.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jLabel10.setOpaque(true);

        tblSequentialTransitions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblSequentialTransitions);
        if (tblSequentialTransitions.getColumnModel().getColumnCount() > 0) {
            tblSequentialTransitions.getColumnModel().getColumn(0).setPreferredWidth(20);
        }

        btnDeleteSelectedSeqTransition.setBackground(new java.awt.Color(27, 38, 61));
        btnDeleteSelectedSeqTransition.setForeground(new java.awt.Color(255, 255, 255));
        btnDeleteSelectedSeqTransition.setText("Delete Seq Transition");
        btnDeleteSelectedSeqTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSelectedSeqTransitionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnDeleteSelectedSeqTransition)
                .addGap(90, 90, 90))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnDeleteTransition, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdateTransition, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTransitionName, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                            .addComponent(jLabel3)
                            .addComponent(txtCombinatorialTransitionID, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnUpdateSelectedInput)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnDeleteSelectedInput)
                    .addComponent(jLabel5)
                    .addComponent(txtTransitionInputValue, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addComponent(btnAddTransitionInput, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addComponent(cmbSequentialTransitions, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAddSequentialTransition, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbTransitionInput, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {txtCombinatorialTransitionID, txtTransitionName});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnAddTransitionInput, txtTransitionInputValue});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDeleteSelectedSeqTransition)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDeleteSelectedInput)
                    .addComponent(btnUpdateSelectedInput))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel9))
                .addGap(9, 9, 9)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbTransitionInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel2))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTransitionInputValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCombinatorialTransitionID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(btnAddTransitionInput))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTransitionName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addComponent(jLabel1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbSequentialTransitions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdateTransition, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteTransition)
                    .addComponent(btnAddSequentialTransition, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18))
        );

        getAccessibleContext().setAccessibleName("Combinatorial Transition: Inputs and Sequential Transitions");
        getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateTransitionActionPerformed
         testcontroller.updateTransition(txtCombinatorialTransitionID.getText(), txtTransitionName.getText(), 
                                         String.valueOf(false));
    }//GEN-LAST:event_btnUpdateTransitionActionPerformed

    private void btnDeleteTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteTransitionActionPerformed

            Command deletetransitioncommand = new DeleteTransitionCommand(testcontroller, txtCombinatorialTransitionID.getText());
            deletetransitioncommand.execute(); 
            testcontroller.addCommand(deletetransitioncommand);
    }//GEN-LAST:event_btnDeleteTransitionActionPerformed

    private void btnAddTransitionInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddTransitionInputActionPerformed
       String inputIDname = (String)cmbTransitionInput.getSelectedItem();
       String[] parts = inputIDname.split(" ", 2);
       testcontroller.addInputToTransition(txtCombinatorialTransitionID.getText(), parts[0], 
                                           txtTransitionInputValue.getText());
       txtTransitionInputValue.setText("");
    }//GEN-LAST:event_btnAddTransitionInputActionPerformed

    private void btnUpdateSelectedInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateSelectedInputActionPerformed
        DefaultTableModel defaulttablemodel = (DefaultTableModel) tblCombinatorialTransitionInputs.getModel();
        int selectedRow = tblCombinatorialTransitionInputs.getSelectedRow();
        if (selectedRow != -1) {
            String transitioninputID = (String)defaulttablemodel.getValueAt(selectedRow, 0);
            String value = (String)defaulttablemodel.getValueAt(selectedRow, 2);
            testcontroller.updateTransitionInput(txtCombinatorialTransitionID.getText(),transitioninputID, value);
        } 
    }//GEN-LAST:event_btnUpdateSelectedInputActionPerformed

    private void btnDeleteSelectedInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSelectedInputActionPerformed
         int selectedRow = tblCombinatorialTransitionInputs.getSelectedRow();
            if (selectedRow != -1) {
                String transitioninputID = (String)tblCombinatorialTransitionInputs.getValueAt(selectedRow, 0);
                testcontroller.deleteTransitionInput(txtCombinatorialTransitionID.getText(), transitioninputID);
            } 
    }//GEN-LAST:event_btnDeleteSelectedInputActionPerformed

    private void btnDeleteSelectedSeqTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSelectedSeqTransitionActionPerformed
          int selectedRow = tblSequentialTransitions.getSelectedRow();
            if (selectedRow != -1) {
                String sequentialTransitionID = (String)tblSequentialTransitions.getValueAt(selectedRow, 0);
                testcontroller.deleteSequentialTransitionFromCombinatorialTransition(txtCombinatorialTransitionID.getText(),sequentialTransitionID);
            } 
    }//GEN-LAST:event_btnDeleteSelectedSeqTransitionActionPerformed

    private void btnAddSequentialTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSequentialTransitionActionPerformed
         String sequentielTransitionID = (String)cmbSequentialTransitions.getSelectedItem();
       String[] parts = sequentielTransitionID.split(" ", 2);
        testcontroller.addSequentialToCombinatorialTransition(txtCombinatorialTransitionID.getText(), parts[0]);
    }//GEN-LAST:event_btnAddSequentialTransitionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddSequentialTransition;
    private javax.swing.JButton btnAddTransitionInput;
    private javax.swing.JButton btnDeleteSelectedInput;
    private javax.swing.JButton btnDeleteSelectedSeqTransition;
    private javax.swing.JButton btnDeleteTransition;
    private javax.swing.JButton btnUpdateSelectedInput;
    private javax.swing.JButton btnUpdateTransition;
    private javax.swing.JComboBox<String> cmbSequentialTransitions;
    private javax.swing.JComboBox<String> cmbTransitionInput;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblCombinatorialTransitionInputs;
    private javax.swing.JTable tblSequentialTransitions;
    private javax.swing.JTextField txtCombinatorialTransitionID;
    private javax.swing.JTextField txtTransitionInputValue;
    private javax.swing.JTextField txtTransitionName;
    // End of variables declaration//GEN-END:variables
}
