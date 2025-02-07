/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

// Package
package combat_plc_tester.view;

// Imports
import Moka7.S7Client;
import combat_plc_tester.controller.GraphController;
import combat_plc_tester.controller.StatePlcTestObserver;
import combat_plc_tester.controller.TestController;
import combat_plc_tester.controller.TransitionPlcTestObserver;
import combat_plc_tester.model.moore.CycleGeneratorAlgorithm;
import combat_plc_tester.model.IO.PlcInputFactory;
import combat_plc_tester.model.IO.PlcOutputFactory;
import combat_plc_tester.model.ModelFacade;
import combat_plc_tester.model.moore.CycleGenerator;
import combat_plc_tester.model.moore.StatePlcTest;
import combat_plc_tester.model.moore.TransitionPlcTest;
import static combat_plc_tester.view.FlowType.INPUTS;
import java.awt.Color;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * Class: CombatPlcTesterView
 *
 * Purpose:
 * This class serves as the main graphical interface in the Model-View-Controller (MVC) pattern 
 * for the Combat PLC Tester application. It enables users to configure properties, manage states 
 * and transitions, execute test cycles, and view the results of PLC testing. It acts as the View 
 * in the MVC design pattern, displaying data from the model and delegating user actions to the controller.
 *
 * Design Patterns:
 * - Model-View-Controller (MVC): Implements the `View` component, responsible for presenting the data 
 *   to the user and interacting with the `TestController`.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class CombatPlcTesterView extends javax.swing.JFrame {
  
    private TestController testcontroller;      //The test controller.
    
    /**
    * Initializes the Combat PLC Tester View with default settings.
    * 
    * - Calls `initComponents()` to initialize all graphical components.
    * - Sets the visibility of views:
    *   - Hides the `sequentialTransitionView` and `combinatorialTransitionView`.
    *   - Displays the `stateView` to prioritize state-related interactions by default.
    * - Configures the initial mode of `modelView` to `ADD_STATE`, enabling state addition as the first user action.
    */
    public CombatPlcTesterView() {
        initComponents();
        sequentialTransitionView.setVisible(false);
        combinatorialTransitionView.setVisible(false);
        stateView.setVisible(true);
        modelView.setMode(ModelView.Mode.ADD_STATE);
    }

    public void setTestController(TestController testcontroller){
        this.testcontroller = testcontroller;
    }
    
    public PropertiesView getPropertiesView(){
        return propertiesview;
    }
    
    public InputListView getInputListView(){
        return inputlistview;
    }
    
    public OutputListView getOutputListView(){
        return outputlistview;
    }
    
    public ModelView getModelView(){
        return modelView;
    }
    
    public StateView getStateView(){
        return stateView;
    }
    
    public SequentialTransitionView getSequentialTransitionView(){
        return sequentialTransitionView;
    }
    
    public CombinatorialTransitionView getCombinatorialTransitionView(){
        return combinatorialTransitionView;
    }
    
     public TestView getTestView(){
        return testview;
    }
    
     /**
    * Updates the current flow in the tabbed panel based on the provided flow type.
    * 
    * This method switches the active tab of `jtpFlow` to reflect the specified `flowtype`,
    * allowing the user to navigate between different sections of the application (properties, inputs, outputs, model, or test).
    * 
    * @param flowtype The type of flow to be set, represented by the `FlowType` enumeration.
    *                 - `PROPERTIES`: Switches to the properties tab (index 0).
    *                 - `INPUTS`: Switches to the inputs tab (index 1).
    *                 - `OUTPUTS`: Switches to the outputs tab (index 2).
    *                 - `MODEL`: Switches to the model tab (index 3).
    *                 - `TEST`: Switches to the test tab (index 4).
    *                 If the `flowtype` does not match any case, no action is taken.
    */
    public void setFlow(FlowType flowtype) {
        switch (flowtype) {
            case PROPERTIES:
                jtpFlow.setSelectedIndex(0);
                break;
            case INPUTS:
                jtpFlow.setSelectedIndex(1);
                break;
            case OUTPUTS:
                jtpFlow.setSelectedIndex(2);
                break;
            case MODEL:
                jtpFlow.setSelectedIndex(3);
                break;
            case TEST:
                jtpFlow.setSelectedIndex(4);
                break;
            default:
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

        tlbModel = new javax.swing.JToolBar();
        btnState = new javax.swing.JButton();
        btnSequentialTransition = new javax.swing.JButton();
        btnCombinatorialTransition = new javax.swing.JButton();
        btnLabel = new javax.swing.JButton();
        btnSelection = new javax.swing.JButton();
        btnUndo = new javax.swing.JButton();
        btnRedo = new javax.swing.JButton();
        jtpFlow = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        propertiesview = new combat_plc_tester.view.PropertiesView();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        inputlistview = new combat_plc_tester.view.InputListView();
        jPanel4 = new javax.swing.JPanel();
        outputlistview = new combat_plc_tester.view.OutputListView();
        jPanel1 = new javax.swing.JPanel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jLayeredPane2 = new javax.swing.JLayeredPane();
        stateView = new combat_plc_tester.view.StateView();
        sequentialTransitionView = new combat_plc_tester.view.SequentialTransitionView();
        combinatorialTransitionView = new combat_plc_tester.view.CombinatorialTransitionView();
        modelView = new combat_plc_tester.view.ModelView();
        jPanel5 = new javax.swing.JPanel();
        testview = new combat_plc_tester.view.TestView();
        jmbProject = new javax.swing.JMenuBar();
        mnProject = new javax.swing.JMenu();
        jmiNewProject = new javax.swing.JMenuItem();
        jmiProjectName = new javax.swing.JMenuItem();
        jmiSave = new javax.swing.JMenuItem();
        jmiSaveAs = new javax.swing.JMenuItem();
        jmiOpen = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tlbModel.setRollover(true);

        btnState.setText("State");
        btnState.setEnabled(false);
        btnState.setFocusable(false);
        btnState.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnState.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnState.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStateActionPerformed(evt);
            }
        });
        tlbModel.add(btnState);

        btnSequentialTransition.setText("Sequential Transition");
        btnSequentialTransition.setEnabled(false);
        btnSequentialTransition.setFocusable(false);
        btnSequentialTransition.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSequentialTransition.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSequentialTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSequentialTransitionActionPerformed(evt);
            }
        });
        tlbModel.add(btnSequentialTransition);

        btnCombinatorialTransition.setText("Combinatorial Transition");
        btnCombinatorialTransition.setEnabled(false);
        btnCombinatorialTransition.setFocusable(false);
        btnCombinatorialTransition.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCombinatorialTransition.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCombinatorialTransition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCombinatorialTransitionActionPerformed(evt);
            }
        });
        tlbModel.add(btnCombinatorialTransition);

        btnLabel.setText("Text");
        btnLabel.setEnabled(false);
        btnLabel.setFocusable(false);
        btnLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLabel.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLabelActionPerformed(evt);
            }
        });
        tlbModel.add(btnLabel);

        btnSelection.setText("Selection");
        btnSelection.setEnabled(false);
        btnSelection.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSelection.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectionActionPerformed(evt);
            }
        });
        tlbModel.add(btnSelection);

        btnUndo.setText("Undo");
        btnUndo.setEnabled(false);
        btnUndo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnUndo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnUndo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUndoActionPerformed(evt);
            }
        });
        tlbModel.add(btnUndo);

        btnRedo.setText("Redo");
        btnRedo.setEnabled(false);
        btnRedo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRedo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRedo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRedoActionPerformed(evt);
            }
        });
        tlbModel.add(btnRedo);

        jtpFlow.setPreferredSize(new java.awt.Dimension(1100, 700));
        jtpFlow.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jtpFlowStateChanged(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(27, 38, 61));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(propertiesview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(213, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(52, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(84, 84, 84))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(propertiesview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(57, 57, 57))))
        );

        jtpFlow.addTab("Properties", jPanel2);

        jPanel3.setBackground(new java.awt.Color(27, 38, 61));

        inputlistview.setMinimumSize(new java.awt.Dimension(745, 250));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(229, Short.MAX_VALUE)
                .addComponent(inputlistview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(214, 214, 214))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(inputlistview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
        );

        jtpFlow.addTab("Input List", jPanel3);

        jPanel4.setBackground(new java.awt.Color(27, 38, 61));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(231, Short.MAX_VALUE)
                .addComponent(outputlistview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(214, 214, 214))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addComponent(outputlistview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        jtpFlow.addTab("Output List", jPanel4);

        jPanel1.setBackground(new java.awt.Color(27, 38, 61));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 585));

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        jLayeredPane2.setLayout(new javax.swing.OverlayLayout(jLayeredPane2));
        jLayeredPane2.add(stateView);
        jLayeredPane2.add(sequentialTransitionView);
        jLayeredPane2.add(combinatorialTransitionView);

        javax.swing.GroupLayout modelViewLayout = new javax.swing.GroupLayout(modelView);
        modelView.setLayout(modelViewLayout);
        modelViewLayout.setHorizontalGroup(
            modelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 965, Short.MAX_VALUE)
        );
        modelViewLayout.setVerticalGroup(
            modelViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(86, 86, 86))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(modelView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(modelView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 621, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jtpFlow.addTab("Model", jPanel1);

        jPanel5.setBackground(new java.awt.Color(27, 38, 61));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(296, Short.MAX_VALUE)
                .addComponent(testview, javax.swing.GroupLayout.PREFERRED_SIZE, 787, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(285, 285, 285))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addComponent(testview, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jtpFlow.addTab("Test", jPanel5);

        mnProject.setLabel("TestProject");

        jmiNewProject.setText("New Project");
        jmiNewProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiNewProjectActionPerformed(evt);
            }
        });
        mnProject.add(jmiNewProject);

        jmiProjectName.setText("Set Project Name");
        jmiProjectName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiProjectNameActionPerformed(evt);
            }
        });
        mnProject.add(jmiProjectName);

        jmiSave.setText("Save");
        jmiSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSaveActionPerformed(evt);
            }
        });
        mnProject.add(jmiSave);

        jmiSaveAs.setText("Save As");
        jmiSaveAs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiSaveAsActionPerformed(evt);
            }
        });
        mnProject.add(jmiSaveAs);

        jmiOpen.setText("Open");
        jmiOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmiOpenActionPerformed(evt);
            }
        });
        mnProject.add(jmiOpen);

        jmbProject.add(mnProject);

        setJMenuBar(jmbProject);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(tlbModel, javax.swing.GroupLayout.PREFERRED_SIZE, 612, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jtpFlow, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(tlbModel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jtpFlow, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
    * Handles the action event triggered by the Sequential Transition button.
    * 
    * @param evt The action event triggered by pressing the Sequential Transition button.
    */
    private void btnSequentialTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSequentialTransitionActionPerformed
        sequentialTransitionView.setVisible(true);
        combinatorialTransitionView.setVisible(false);
        stateView.setVisible(false);
        modelView.setMode(ModelView.Mode.ADD_SEQUENTIAL_TRANSITION);
    }//GEN-LAST:event_btnSequentialTransitionActionPerformed

    /**
    * Handles the action event triggered by the State button.
    * 
    * @param evt The action event triggered by pressing the State button.
    */
    private void btnStateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStateActionPerformed
        sequentialTransitionView.setVisible(false);
        combinatorialTransitionView.setVisible(false);
        stateView.setVisible(true);
        modelView.setMode(ModelView.Mode.ADD_STATE);
    }//GEN-LAST:event_btnStateActionPerformed

    /**
    * Handles the action event triggered by the Selection button.
    * 
    * @param evt The action event triggered by pressing the Sequential Selection button.
    */
    private void btnSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectionActionPerformed
        modelView.setMode(ModelView.Mode.SELECTION);
    }//GEN-LAST:event_btnSelectionActionPerformed

    /**
    * Handles the action event triggered by the Undo button.
    * 
    * @param evt The action event triggered by pressing the Undo button.
    */
    private void btnUndoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUndoActionPerformed
        testcontroller.undo();
    }//GEN-LAST:event_btnUndoActionPerformed

    /**
    * Handles the action event triggered by the Redo button.
    * 
    * @param evt The action event triggered by pressing the Redo button.
    */
    private void btnRedoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRedoActionPerformed
       testcontroller.redo();
    }//GEN-LAST:event_btnRedoActionPerformed

    /**
    * Handles the action event triggered by the Label button.
    * 
    * @param evt The action event triggered by pressing the Label button.
    */
    private void btnLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLabelActionPerformed
       modelView.setMode(ModelView.Mode.ADD_LABEL);
    }//GEN-LAST:event_btnLabelActionPerformed

    /**
    * Handles the action event triggered by the Label button.
    * 
    * @param evt The action event triggered by pressing the Combinatorial Transition button.
    */
    private void btnCombinatorialTransitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCombinatorialTransitionActionPerformed
      sequentialTransitionView.setVisible(false);
      combinatorialTransitionView.setVisible(true);
      stateView.setVisible(false);
      modelView.setMode(ModelView.Mode.ADD_COMBINATORIAL_TRANSITION);// TODO add your handling code here:
    }//GEN-LAST:event_btnCombinatorialTransitionActionPerformed

    /**
    * Handles the state change event of the `jtpFlow` tabbed pane.
    * 
    * This method enables or disables buttons and adjusts their appearance based on the currently 
    * selected tab index in the `jtpFlow` tabbed pane. When the selected tab index is 3, the buttons 
    * are enabled, and their appearance is restored to default. For other tabs, the buttons are disabled, 
    * and their foreground color is set to light gray.
    * 
    * @param evt The change event triggered when the selected tab in `jtpFlow` changes.
    */
    private void jtpFlowStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jtpFlowStateChanged
        int selectedIndex = jtpFlow.getSelectedIndex();
        if (selectedIndex == 3) {
            btnState.setEnabled(true);
            btnState.setForeground(null);
            btnState.setForeground(null);
            btnSequentialTransition.setEnabled(true);
            btnSequentialTransition.setForeground(null);
            btnCombinatorialTransition.setEnabled(true);
            btnCombinatorialTransition.setForeground(null);
            btnSelection.setEnabled(true);
            btnSelection.setForeground(null);
            btnLabel.setEnabled(true);
            btnLabel.setForeground(null);
            btnUndo.setEnabled(true);
            btnUndo.setForeground(null);
            btnRedo.setEnabled(true);
            btnRedo.setForeground(null);
        } else {
            btnState.setEnabled(false);
            btnState.setForeground(Color.LIGHT_GRAY);
            btnSequentialTransition.setEnabled(false);
            btnSequentialTransition.setForeground(Color.LIGHT_GRAY);
            btnCombinatorialTransition.setEnabled(false);
            btnCombinatorialTransition.setForeground(Color.LIGHT_GRAY);
            btnSelection.setEnabled(false);
            btnSelection.setForeground(Color.LIGHT_GRAY);
            btnLabel.setEnabled(false);
            btnLabel.setForeground(Color.LIGHT_GRAY);
            btnUndo.setEnabled(false);
            btnUndo.setForeground(Color.LIGHT_GRAY);
            btnRedo.setEnabled(false);
            btnRedo.setForeground(Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_jtpFlowStateChanged

    /**
    * Handles the action event triggered by selecting the "Save" menu item.
    * 
    * Workflow:
    * - Checks if a file path is already set in the `TestController`:
    *   - If no path is set:
    *     - Opens a `JFileChooser` dialog for the user to select a file or directory.
    *     - If the user approves the selection:
    *       - Retrieves the selected file path.
    *       - Sets the file path in the `TestController`.
    *       - Calls the `saveToFile` method in the `TestController` to save the project.
    *   - If a path is already set:
    *     - Directly calls the `saveToFile` method in the `TestController` to save the project.
    * 
    * @param evt The action event triggered by selecting the "Save" menu item.
    */
    private void jmiSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSaveActionPerformed
        if (testcontroller.getFilePath() == null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String selectedPath = selectedFile.getAbsolutePath();
                testcontroller.setFilePath(selectedPath);
                testcontroller.saveToFile();
            }
        } else {
            testcontroller.saveToFile();
        }
    }//GEN-LAST:event_jmiSaveActionPerformed

    /**
    * Handles the action event triggered by selecting the "New Project" menu item.
    * 
    * Workflow:
    * - Displays a confirmation dialog to the user with a warning message.
    * - If the user selects "Yes," the application is reset:
    *   - The main application is restarted using `runCombatPlcTester`.
    *   - Properties, inputs, and outputs are reset to their default values.
    * - If the user selects "No," no action is taken.
    * 
    * @param evt The action event triggered by selecting the "New Project" menu item.
    */
    private void jmiNewProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiNewProjectActionPerformed
        int response = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to reset all data? Please ensure all changes have been saved.",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (response == JOptionPane.YES_OPTION) {
            runCombatPlcTester(this);
            this.getPropertiesView().resetPropertiesToDefault();
            this.getInputListView().resetInputToDefault();
            this.getOutputListView().resetOutputToDefault();
            this.getSequentialTransitionView().resetviewInputs();
            this.getCombinatorialTransitionView().resetviewInputs();
            this.getStateView().resetviewOutputs();
        }
    }//GEN-LAST:event_jmiNewProjectActionPerformed

    /**
    * Handles the action event triggered by selecting the "Save As" menu item.
    * 
    * Workflow:
    * - Opens a `JFileChooser` dialog to allow the user to select a file or directory.
    * - If the user approves the selection:
    *   - Retrieves the selected file path.
    *   - Sets the file path in the `TestController`.
    *   - Calls the `saveAsToFile` method in the `TestController` to save the project to the specified path.
    * - If the user cancels the file chooser, no action is performed.
    * 
    * @param evt The action event triggered by selecting the "Save As" menu item.
    */
    private void jmiSaveAsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiSaveAsActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String selectedPath = selectedFile.getAbsolutePath();
            testcontroller.setFilePath(selectedPath);
            testcontroller.saveAsToFile();
        }
    }//GEN-LAST:event_jmiSaveAsActionPerformed

    private void jmiOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiOpenActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String selectedPath = selectedFile.getAbsolutePath();
            testcontroller.setFilePath(selectedPath);
            testcontroller.readFromFile();
        }
    }//GEN-LAST:event_jmiOpenActionPerformed

    /**
    * Handles the action event triggered by selecting the "Open" menu item.
    * 
    * Workflow:
    * - Opens a `JFileChooser` dialog for the user to select a file or directory.
    * - If the user approves the selection:
    *   - Retrieves the selected file path.
    *   - Sets the file path in the `TestController` using the `setFilePath` method.
    *   - Calls the `readFromFile` method in the `TestController` to load the project data.
    * 
    * @param evt The action event triggered by selecting the "Open" menu item.
    */
    private void jmiProjectNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmiProjectNameActionPerformed
    String projectName = JOptionPane.showInputDialog(
                    this,
                    "Please enter the project name:",
                    "Project Name",
                    JOptionPane.QUESTION_MESSAGE
                );
                if (projectName != null && !projectName.trim().isEmpty()) {
                   testcontroller.setProjectName(projectName);
                    this.setTitle(projectName);
                } else {
                    JOptionPane.showMessageDialog(
                        this,
                        "No project name was entered.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }    
    }//GEN-LAST:event_jmiProjectNameActionPerformed
   
    /**
    * Entry point of the application.
    *
    *
    * Workflow:
    * - Creates and displays the main application window (`CombatPlcTesterView`) on the Event Dispatch Thread (EDT).
    * - Initializes the application logic using `runCombatPlcTester`.
    * 
    * @param args Command-line arguments for the application (not used).
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
            java.util.logging.Logger.getLogger(CombatPlcTesterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CombatPlcTesterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CombatPlcTesterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CombatPlcTesterView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            CombatPlcTesterView combatplctesterview = new CombatPlcTesterView();
            combatplctesterview.setVisible(true); 
                runCombatPlcTester(combatplctesterview);
            
            }
        });
    }
    
    /**
    * Initializes and configures the `CombatPlcTesterView` by resetting the model elements,
    * creating a new `TestController` instance, and linking the view components with the controller.
    *
    * Workflow:
    * 1. Resets all elements in the `ModelFacade` to their default state.
    * 2. Initializes the `ModelFacade` with data from the `CombatPlcTesterView`.
    * 3. Creates a new `TestController` with references to the view and necessary factories (`PlcInputFactory` and `PlcOutputFactory`).
    * 4. Links the `TestController` to all relevant view components (properties view, input/output list views).
    * 5. Creates and associates a `GraphController` to handle graph-based operations in the `ModelView`.
    * 6. Updates the `ModelView` with the current state, transition, and label graph element lists from the `TestController`.
    *
    * @param combatplctesterview The main application view responsible for user interaction and displaying data.
    */
    public static void runCombatPlcTester(CombatPlcTesterView combatplctesterview) {
        try {
            ModelFacade.getInstance().resetAllElementsInModelFacade();
            initializeModelFacade(combatplctesterview);

            TestController testcontroller = new TestController(combatplctesterview,
                    new PlcInputFactory(), new PlcOutputFactory());

            combatplctesterview.setTestController(testcontroller);
            combatplctesterview.getPropertiesView().setTestController(testcontroller);
            combatplctesterview.getInputListView().setTestController(testcontroller);
            combatplctesterview.getOutputListView().setTestController(testcontroller);
            combatplctesterview.getStateView().setTestController(testcontroller);
            combatplctesterview.getSequentialTransitionView().setTestController(testcontroller);
            combatplctesterview.getCombinatorialTransitionView().setTestController(testcontroller);
            combatplctesterview.getTestView().setTestController(testcontroller);

            GraphController graphcontroller = new GraphController(testcontroller);
            combatplctesterview.getModelView().setGraphController(graphcontroller);
            combatplctesterview.getModelView().setStateGraphElementList(testcontroller.getStateGraphElementList());
            combatplctesterview.getModelView().setTransitionGraphElementList(testcontroller.getTransitionGraphElementList());
            combatplctesterview.getModelView().setLabelGraphElementList(testcontroller.getLabelGraphElementList());

        } catch (Exception e) {
            combatplctesterview.exceptionErrorView(e.getMessage());
        }
    }
    
    /**
    * Configures and initializes the `ModelFacade` with the required components for the application's functionality.
    * This includes setting up the PLC client, cycle generator, test strategies, and observers for test execution.
    *
    * Workflow:
    * 1. Assigns an `S7Client` instance to the `ModelFacade` to facilitate communication with the PLC.
    * 2. Configures a `CycleGenerator` instance with the desired strategy (`CycleGeneratorAlgorithm`) and sets it in the `ModelFacade`.
    * 3. Sets up the state and transition test implementations (`StatePlcTest` and `TransitionPlcTest`) in the `ModelFacade`.
    * 4. Adds observers (`StatePlcTestObserver` and `TransitionPlcTestObserver`) to monitor and update the test view during test execution.
    * 5. Updates the main application window's title to reflect the current project name from the `ModelFacade`.
    *
    * @param combatplctesterview The main application view responsible for user interaction and displaying data.
    */
    public static void initializeModelFacade(CombatPlcTesterView combatplctesterview) {
        ModelFacade.getInstance().setPlcClient(new S7Client());
        CycleGenerator cyclegenerator = new CycleGenerator();
        cyclegenerator.setStrategy(new CycleGeneratorAlgorithm());
        ModelFacade.getInstance().setCycleGenerator(cyclegenerator);
        ModelFacade.getInstance().setStatetest(new StatePlcTest());
        ModelFacade.getInstance().setTransistiontest(new TransitionPlcTest());
        ModelFacade.getInstance().setTransistiontest(new TransitionPlcTest());
        ModelFacade.getInstance().addStateTestObserver(new StatePlcTestObserver(combatplctesterview.getTestView()));
        ModelFacade.getInstance().addTransitionTestObserver(new TransitionPlcTestObserver(combatplctesterview.getTestView()));
        combatplctesterview.setTitle(ModelFacade.getInstance().getProjectName());
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCombinatorialTransition;
    private javax.swing.JButton btnLabel;
    private javax.swing.JButton btnRedo;
    private javax.swing.JButton btnSelection;
    private javax.swing.JButton btnSequentialTransition;
    private javax.swing.JButton btnState;
    private javax.swing.JButton btnUndo;
    private combat_plc_tester.view.CombinatorialTransitionView combinatorialTransitionView;
    private combat_plc_tester.view.InputListView inputlistview;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JLayeredPane jLayeredPane2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JMenuBar jmbProject;
    private javax.swing.JMenuItem jmiNewProject;
    private javax.swing.JMenuItem jmiOpen;
    private javax.swing.JMenuItem jmiProjectName;
    private javax.swing.JMenuItem jmiSave;
    private javax.swing.JMenuItem jmiSaveAs;
    private javax.swing.JTabbedPane jtpFlow;
    private javax.swing.JMenu mnProject;
    private combat_plc_tester.view.ModelView modelView;
    private combat_plc_tester.view.OutputListView outputlistview;
    private combat_plc_tester.view.PropertiesView propertiesview;
    private combat_plc_tester.view.SequentialTransitionView sequentialTransitionView;
    private combat_plc_tester.view.StateView stateView;
    private combat_plc_tester.view.TestView testview;
    private javax.swing.JToolBar tlbModel;
    // End of variables declaration//GEN-END:variables
}
