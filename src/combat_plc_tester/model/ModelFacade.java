// Package
package combat_plc_tester.model;

// Imports
import Moka7.S7Client;
import combat_plc_tester.model.moore.SequentialTransition;
import combat_plc_tester.model.moore.CycleGenerator;
import combat_plc_tester.model.moore.CombinatorialTransition;
import combat_plc_tester.model.moore.Transition;
import combat_plc_tester.model.moore.State;
import combat_plc_tester.model.moore.StateTest;
import combat_plc_tester.model.moore.TransitionTest;
import combat_plc_tester.model.moore.GraphElement;
import combat_plc_tester.controller.StateTestObserver;
import combat_plc_tester.controller.TransitionTestObserver;
import combat_plc_tester.model.IO.Input;
import combat_plc_tester.model.IO.Output;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class: ModelFacade
 *
 * Purpose: This class serves as the single point of access to the model layer
 * in an MVC pattern.
 *
 * Design Patterns: - Facade: Provides a simplified and centralized interface
 * for interacting with the model. 
 *                  - Singleton: Ensures only one instance of
 * this class exists during the application's lifecycle.
 * 
 * Notes: - This class implements the Serializable interface to enable the
 * storage of fields and objects as a bytestream.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class ModelFacade implements Serializable {

    private static final long serialVersionUID = 1L;
    private transient static ModelFacade modelfacade = new ModelFacade();

    private String projectname;                  // Name of the project.
    private String IPAddress;                    // IP address of the PLC.
    /**
     * Rack number of the PLC.
     * Must be between 0 and 31.
     */
    private short rack;                      
    /**
     * Slot number of the PLC.  
     * Must be between 0 and 31.
     */      
    private short slot;                          
    /**
     * Cycletime of the PLC, measured in milliseconds. Represents the time
     * needed by the PLC to execute its code. Must be between 1 and 65535.
     */
    private int cycletime;
    /**
     * Cycletime scaling factor in milliseconds. This scaling factor delays the
     * execution of writing inputs and reading outputs by the application. Must
     * be between 1 and 5000.
     */
    private short cycletimescalingfactor;
    /**
     * Number of the databuilding block in the PLC used to write the inputs.
     * Must be between 1 and 59999.
     */
    private short databuildingblockinputs;
    /**
     * Number of the databuilding block in the PLC used to read the outputs.
     * Must be between 1 and 59999.
     */
    private short databuildingblockoutputs;
    private transient S7Client plcclient;        // The S7 client used to connect to the PLC.
    /**
     * The cycle generator responsible for generating test cycles.
     */
    private transient CycleGenerator cyclegenerator;
    private List<GraphElement> stategraphelementList;        // List of state graph elements.
    private List<GraphElement> transitiongraphelementList;   // List of transition graph elements.   
    private List<GraphElement> labelgraphelementList;        // List of label graph elements.
    /**
     * ID number counts for states, transitions, inputs, and outputs. Used for
     * reading and saving purposes.
     */
    private int stateIDnumbercount;
    private int transitionIDnumbercount;
    private int inputIDnumbercount;
    private int outputIDnumbercount;
    private List<Input> inputList;               // List of input elements.
    private List<Output> outputList;             // List of output elements.
    /**
     * The StateTest determines the method of reading outputs from the PLC and
     * comparing these outputs with the state-defined outputs.
     */
    private transient StateTest statetest;
    /**
     * The TransitionTest determines the method of writing inputs to the PLC.
     */
    private transient TransitionTest transitiontest;

    private ModelFacade() {
        stategraphelementList = new ArrayList<>();
        transitiongraphelementList = new ArrayList<>();
        labelgraphelementList = new ArrayList<>();
        inputList = new ArrayList<>();
        outputList = new ArrayList<>();
        
        // Initialize the PLC properties.
        IPAddress = "192.168.0.1";
        rack = 0;
        slot = 1;
        cycletime = 100;
        cycletimescalingfactor = 1;
        databuildingblockinputs = 1;
        databuildingblockoutputs = 2;
    }

    public static ModelFacade getInstance() {
        return modelfacade;
    }

    public static void setInstance(ModelFacade newInstance) {
        if (newInstance != null) {
            modelfacade = newInstance;
        }
    }

    /**
     * Resets all elements in the ModelFacade. 
     */
    public void resetAllElementsInModelFacade() {
        projectname = "TestProject";
        this.plcclient = null;
        cyclegenerator = null;
        stategraphelementList.clear();
        transitiongraphelementList.clear();
        labelgraphelementList.clear();
        stateIDnumbercount = 0;
        transitionIDnumbercount = 0;
        inputIDnumbercount = 0;
        outputIDnumbercount = 0;
        inputList.clear();
        outputList.clear();
        statetest = null;
        transitiontest = null;
        State.setObjectcount(0);
        Transition.setObjectcount(0);
        Input.setObjectcount(0);
        Output.setObjectcount(0);
    }

    public void setProjectName(String projectname) {
        this.projectname = projectname;
    }

    public String getProjectName() {
        return this.projectname;
    }

    public String getIPAddress() {
        return IPAddress;
    }

    /**
     * Sets the IP address of the PLC.
     *
     * @param IPAddress The IP address to be set for the PLC.
     */
    public void setIPAddress(String IPAddress) {
        this.IPAddress = IPAddress;
    }

    public short getRack() {
        return rack;
    }

    /**
     * Sets the rack number of the PLC. If the rack number is valid (0 to 31).
     *
     * @param rack The rack number to be set for the PLC. Must be in the range
     * of 0 to 31.
     */
    public void setRack(short rack) {
        this.rack = rack;
    }

    public short getSlot() {
        return slot;
    }

    /**
     * Sets the slot number of the PLC. If the slot number is valid (0 to 31).
     *
     * @param slot The slot number to be set for the PLC. Must be in the range
     * of 0 to 31.
     */
    public void setSlot(short slot) {

        this.slot = slot;
    }

    public int getCycleTime() {
        return cycletime;
    }

    /**
     * Sets the cycletime. If the cycletime is valid (1 to 65535).
     *
     * @param cycletime The cycletime represents the time needed by the PLC to
     * execute its code. Must be between 1 and 65535.
     */
    public void setCycleTime(int cycletime) {

        this.cycletime = cycletime;
    }

    public short getCycleTimeScalingFactor() {

        return cycletimescalingfactor;
    }

    /**
     * Sets the cycletimescalingfactor. If the cycletimescalingfactor is valid
     * (1 to 5000).
     *
     * @param cycletimescalingfactor This scaling factor delays the execution of
     * writing inputs and reading outputs by the application. Must be between 1
     * and 5000.
     */
    public void setCycleTimeScalingFactor(short cycletimescalingfactor) {
        this.cycletimescalingfactor = cycletimescalingfactor;
    }

    public void setPlcClient(S7Client plcclient) {
        this.plcclient = plcclient;
    }

    public S7Client getPlcClient() {
        return this.plcclient;
    }

    public short getDataBuildingBlockNumberInputs() {
        return databuildingblockinputs;
    }

    /**
     * Sets the number of the databuilding block in the PLC used to write the
     * inputs. If the the number is valid (1 to 59999).
     *
     * @param databuildingblockinputs The number of the databuilding block in
     * the PLC used to write the inputs. Must be between 1 and 59999.
     */
    public void setDataBuildingBlockNumberInputs(short databuildingblockinputs) {
        this.databuildingblockinputs = databuildingblockinputs;
    }

    public short getDataBuildingBlockNumberOutputs() {
        return databuildingblockoutputs;
    }

     /**
     * Sets the number of the databuilding block in the PLC used to read the outputs.
     * If the the number is valid (1 to 59999).
     *
     * @param databuildingblockoutputs The number of the databuilding block in the 
     * PLC used to read the outputs. Must be between 1 and 59999.
     */
    public void setDataBuildingBlockNumberOutputs(short databuildingblockoutputs){      
            this.databuildingblockoutputs = databuildingblockoutputs;
    }

    public List<GraphElement> getStateGraphElementList() {
        return stategraphelementList;
    }

    public void setStateGraphElementList(List<GraphElement> stategraphelementList) {
        this.stategraphelementList = stategraphelementList;
    }

    public List<GraphElement> getTransitionGraphElementList() {
        return transitiongraphelementList;
    }

    public void setTransitionGraphElementList(List<GraphElement> transitiongraphelementList) {
        this.transitiongraphelementList = transitiongraphelementList;
    }

    public List<GraphElement> getLabelGraphElementList() {
        return labelgraphelementList;
    }

    public void setLabelGraphElementList(List<GraphElement> labelgraphelementList) {
        this.labelgraphelementList = labelgraphelementList;
    }

    public void setStateClassCount() {
        State.setObjectcount(stateIDnumbercount);
    }

    public void setStateObjectCount(int stateIDnumbercount) {
        this.stateIDnumbercount = stateIDnumbercount;
    }

    public void setTransitionClassCount() {
        Transition.setObjectcount(transitionIDnumbercount);
    }

    public void setTransitionObjectCount(int transitionIDnumbercount) {
        this.transitionIDnumbercount = transitionIDnumbercount;
    }

    public void setInputClassCount() {
        Input.setObjectcount(inputIDnumbercount);
    }

    public void setInputObjectCount(int inputIDnumbercount) {
        this.inputIDnumbercount = inputIDnumbercount;
    }

    public void setOutputClassCount() {
        Output.setObjectcount(outputIDnumbercount);
    }

    public void setOutputObjectCount(int outputIDnumbercount) {
        this.outputIDnumbercount = outputIDnumbercount;
    }

    public CycleGenerator getCycleGenerator() {
        return cyclegenerator;
    }

    public void setCycleGenerator(CycleGenerator cyclegenerator) {
        this.cyclegenerator = cyclegenerator;
    }

    public List<Input> getInputList() {
        return inputList;
    }

    public void setInputList(List<Input> inputList) {
        this.inputList = inputList;
    }

    public List<Output> getOutputList() {
        return outputList;
    }

    public void setOutputList(List<Output> outputList) {
        this.outputList = outputList;
    }

    public StateTest getStatetest() {
        return statetest;
    }

    public void setStatetest(StateTest statetest) {
        this.statetest = statetest;
    }

    public TransitionTest getTransistiontest() {
        return transitiontest;
    }

    public void setTransistiontest(TransitionTest transistiontest) {
        this.transitiontest = transistiontest;
    }

    /**
     * Gets a state object by its ID.
     *
     * @param stateID The unique identifier of the state to retrieve.
     * @return The {@State} object with the matching ID, or {@code null} if
     * no such state exists in the list.
     */
    public State getStateByID(String stateID) {
        for (GraphElement stategraphelement : stategraphelementList) {
            State state = (State) stategraphelement;
            if (state.getStateID().equals(stateID)) {
                return state;
            }
        }
        return null; 
    }

    /**
     * Gets a Transition object by its ID.
     *
     * @param transitionID The unique identifier of the transtion to retrieve.
     * @return The {@Transtion} object with the matching ID, or {@code null} if
     * no such transition exists in the list.
     */
    public Transition getTransitionByID(String transitionID) {
        for (GraphElement transitiongraphelement : transitiongraphelementList) {
            Transition transition = (Transition) transitiongraphelement;
            if (transition.getTransitionID().equals(transitionID)) {
                return transition;
            } else if (transition instanceof CombinatorialTransition) {
                CombinatorialTransition combinatorialTransition = (CombinatorialTransition) transitiongraphelement;
                for (SequentialTransition sequentialTransition : combinatorialTransition.getSequentialTransitionsToTest()) {
                    if (sequentialTransition.getTransitionID().equals(transitionID)) {
                        return sequentialTransition;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Gets an Input by its ID.
     *
     * @param inputID The unique identifier of the input to retrieve.
     * @return The {@Input} object with the matching ID, or {@code null} if
     * no such input exists in the list.
     */
    public Input getInputByID(String inputID) {
        for (Input input : inputList) {
            if (input.getInputID().equals(inputID)) {
                return input;
            }
        }
        return null;
    }

    /**
     * Gets an Output by its ID.
     *
     * @param outputID The unique identifier of the output to retrieve.
     * @return The {@Output} object with the matching ID, or {@code null} if
     * no such output exists in the list.
     */
    public Output getOutputByID(String outputID) {
        for (Output output : outputList) {
            if (output.getOutputID().equals(outputID)) {
                return output;
            }
        }
        return null;
    }

    /*
     * Observer management for state and transition tests.
     *
     * These methods allow adding and removing observers to monitor the results
     * and execution status of state and transition tests. 
     */
    public void addStateTestObserver(StateTestObserver statetestobserver) {
        statetest.addStateTestObserver(statetestobserver);
    }
    
    public void removeStateTestObserver(StateTestObserver statetestobserver) {
        statetest.removeStateTestObserver(statetestobserver);
    }

    public void addTransitionTestObserver(TransitionTestObserver transitiontestobserver) {
        transitiontest.addTransitionTestObserver(transitiontestobserver);
    }

    public void removeTransitionTestObserver(TransitionTestObserver transitiontestobserver) {
        transitiontest.removeTransitionTestObserver(transitiontestobserver);
    }

    public Set<List<String>> generateCycles() {
        return cyclegenerator.generateCycles(stategraphelementList, transitiongraphelementList);
    }

    public int ConnectToPlc() {
        return this.plcclient.ConnectTo(IPAddress, rack, slot);
    }

    public void DisconnectFromPlc() {
        this.plcclient.Disconnect();
    }

    /**
     * Executes a state test for a given state (reading outputs from the PLC
     * and compares them with the outputs of the state).
     *
     * @param state The state to be tested. Must not be {@code null}.
     * @param aftertimecondition A boolean value indicating whether to apply the
     * "after time condition" during the test.
     * @return {@code true} if the state test passes, {@code false} otherwise.
     */
    public boolean executeStateTest(State state, boolean aftertimecondition) {
        statetest.setDataBuildingBlockNumberOutputs(databuildingblockoutputs); 
        statetest.setAftertimecondition(aftertimecondition);
        statetest.setOutputList(outputList);
        statetest.setPlcClient(plcclient);
        statetest.setState(state);
        statetest.setCycleTime(cycletime);
        return statetest.doStateTest();
    }

    /**
     * Uses a transition for the test (Writing inputs to the PLC).
     *
     * @param transition The transition to use for the test. Must not be {@code null}.
     */
    public void executeTransitionTest(Transition transition) {
        transitiontest.setDataBuildingBlockNumberInputs(databuildingblockinputs);
        transitiontest.setInputList(inputList);
        transitiontest.setPlcClient(plcclient);
        transitiontest.setTransition(transition);
        transitiontest.doTransitionTest();
    }
}
