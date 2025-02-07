// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.IO.InputDWord;
import combat_plc_tester.model.IO.InputReal;
import combat_plc_tester.model.IO.InputByte;
import combat_plc_tester.model.IO.InputFactory;
import combat_plc_tester.model.IO.InputWord;
import combat_plc_tester.model.IO.InputDInt;
import combat_plc_tester.model.IO.Input;
import combat_plc_tester.model.IO.OutputInt;
import combat_plc_tester.model.IO.PlcDataType;
import combat_plc_tester.model.IO.OutputWord;
import combat_plc_tester.model.IO.OutputFactory;
import combat_plc_tester.model.IO.OutputDInt;
import combat_plc_tester.model.IO.OutputByte;
import combat_plc_tester.model.IO.Output;
import combat_plc_tester.model.IO.OutputDWord;
import combat_plc_tester.model.IO.InputBit;
import combat_plc_tester.model.IO.InputInt;
import combat_plc_tester.model.IO.OutputReal;
import combat_plc_tester.model.IO.OutputBit;
import combat_plc_tester.model.ModelFacade;
import combat_plc_tester.controller.exceptions.BusinessModelException;
import combat_plc_tester.model.moore.GraphElementRenderer;
import combat_plc_tester.model.moore.SequentialTransition;
import combat_plc_tester.model.moore.CombinatorialTransition;
import combat_plc_tester.model.moore.Transition;
import combat_plc_tester.model.moore.Label;
import combat_plc_tester.model.moore.State;
import combat_plc_tester.model.moore.GraphElement;
import Moka7.IntByRef;
import Moka7.S7;
import Moka7.S7Client;
import static combat_plc_tester.model.IO.PlcDataType.*;
import combat_plc_tester.controller.exceptions.IPAddressChecker;
import combat_plc_tester.controller.exceptions.ValidationModelException;
import combat_plc_tester.view.CombatPlcTesterView;
import combat_plc_tester.view.FlowType;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import javax.swing.SwingUtilities;


/**
 * Class: TestController
 *
 * Purpose: 
 * This class serves as the Controller in the Model-View-Controller (MVC) pattern. 
 * It manages the interaction between the model and the view, orchestrates the execution 
 * of test cycles, and updates the view with test results. Additionally, it utilizes 
 * the Factory, Command, and Adapter design patterns to ensure modularity, flexibility, 
 * and reusability in handling inputs, outputs, actions, and data storage.
 *
 * Design Patterns: 
 * - Model-View-Controller (MVC): Acts as the Controller, mediating between the Model 
 *   (data and business logic) and the View (user interface).
 * - Factory Pattern: Used for creating and managing `Input` and `Output` objects.
 * - Command Pattern: Provides support for undoable actions.
 * - Adapter Pattern: Facilitates the storage and retrieval of data.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class TestController {
    
    private CombatPlcTesterView combatplctesterview;                                    // The combatplc view. 
    private InputFactory inputfactory;                                                  // The input factory.
    private OutputFactory outputfactory;                                                // The output factory.
    private Set<List<String>> testcyles;                                                // The cycles to test.                                                  
    private Stack<Command> undoStack = new Stack<>();                                   // The undo stack.
    private Stack<Command> redoStack = new Stack<>();                                   // The redo stack.
    private String filepath;                                                            // The file path.
    private FileAdapter<ModelFacade> fileadapter = new SerializableFileAdapter<>();     // The file adapter.
    private volatile boolean stoprequested = false;                                     // The stoprequest test flag.
    
    /**
    * Constructor: TestController
    * 
    * Initializes the `TestController` with the necessary view and factory components for handling inputs and outputs.
    * 
    * @param combatplctesterview The view component responsible for user interface interactions.
    * @param inputfactory The factory for creating input-related components or configurations.
    * @param outputfactory The factory for creating output-related components or configurations.
    */
    public TestController(CombatPlcTesterView combatplctesterview, InputFactory inputfactory, OutputFactory outputfactory){
        this.combatplctesterview = combatplctesterview;  
        this.inputfactory = inputfactory;
        this.outputfactory = outputfactory;
    }
     
    public void setProjectName(String projectname){
        ModelFacade.getInstance().setProjectName(projectname);
    }
    
    public String getProjectName(){
        return ModelFacade.getInstance().getProjectName();
    }
    
    public void setFilePath(String filepath){
       this.filepath = filepath; 
    }
    
    public String getFilePath(){
        return filepath;
    }
    
    public void saveToFile() {
        try {
            fileadapter.save(ModelFacade.getInstance(), filepath);
        } catch (Exception e) {
            combatplctesterview.exceptionErrorView("An error occurred while saving the file: " + e.getMessage());
        }
    }

    public void saveAsToFile() {
        try {
            fileadapter.save(ModelFacade.getInstance(), this.filepath);
        } catch (Exception e) {
            combatplctesterview.exceptionErrorView("An error occurred while saving the file: " + e.getMessage());
        }
    }

    /**
    * Reads all data from a file using the `fileadapter` and updates the relevant components of the application 
    * with the loaded data. This ensures that the user interface and underlying model are synchronized with the data in the file.
    *
    * Workflow:
    * - Loads the `ModelFacade` instance from the specified file path.
    * - Initializes the `CombatPlcTesterView` with the loaded `ModelFacade`.
    * - Updates the properties view with key model attributes such as IP address, rack, slot, cycle time, and others.
    * - Populates the input and output views with their respective data from the model.
    * - Updates transition views, separating sequential and combinatorial transitions.
    * - Ensures that all graphical components (states, transitions, and labels) in the `ModelView` are updated.
    * - Sets class counters in the `ModelFacade` for state, transition, input, and output elements.
    */
    public void readFromFile() {
        try {
            ModelFacade.setInstance(fileadapter.load(this.filepath));
            CombatPlcTesterView.initializeModelFacade(combatplctesterview);
            combatplctesterview.getPropertiesView().viewProperties(ModelFacade.getInstance().getIPAddress(),
                    String.valueOf(ModelFacade.getInstance().getRack()), String.valueOf(ModelFacade.getInstance().getSlot()), String.valueOf(ModelFacade.getInstance().getCycleTime()),
                    String.valueOf(ModelFacade.getInstance().getCycleTimeScalingFactor()), String.valueOf(ModelFacade.getInstance().getDataBuildingBlockNumberInputs()),
                    String.valueOf(ModelFacade.getInstance().getDataBuildingBlockNumberOutputs()));
            combatplctesterview.getInputListView().viewInputs(ModelFacade.getInstance().getInputList());
            combatplctesterview.getOutputListView().viewOutputs(ModelFacade.getInstance().getOutputList());
            combatplctesterview.getStateView().viewOutputs(ModelFacade.getInstance().getOutputList());
            combatplctesterview.getSequentialTransitionView().viewInputs(ModelFacade.getInstance().getInputList());
            combatplctesterview.getCombinatorialTransitionView().viewInputs(ModelFacade.getInstance().getInputList());
            List<GraphElement> sequentialtransitiongraphelementList = new ArrayList<>();
            for (GraphElement graphElement : ModelFacade.getInstance().getTransitionGraphElementList()) {
                Transition transition = (Transition) graphElement;
                if (transition instanceof SequentialTransition) {
                    sequentialtransitiongraphelementList.add(transition);
                }
            }
            combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitions(sequentialtransitiongraphelementList);
            ModelFacade.getInstance().setStateClassCount();
            ModelFacade.getInstance().setTransitionClassCount();
            ModelFacade.getInstance().setInputClassCount();
            ModelFacade.getInstance().setOutputClassCount();
            combatplctesterview.getModelView().setStateGraphElementList(ModelFacade.getInstance().getStateGraphElementList());
            combatplctesterview.getModelView().setTransitionGraphElementList(ModelFacade.getInstance().getTransitionGraphElementList());
            combatplctesterview.getModelView().setLabelGraphElementList(ModelFacade.getInstance().getLabelGraphElementList());
        } catch (Exception e) {
            combatplctesterview.exceptionErrorView("An error occurred while reading the file: " + e.getMessage());
        }
    }
    
    /**
    * Updates the PLC properties in the `ModelFacade` with validated values for IP address, rack, slot, cycle time, 
    * and cycle time scaling factor. Ensures all input values are properly checked for validity and updates the model accordingly.
    *
    * Workflow:
    * - Validates and sets each property:
    *   - **IP Address**: Uses `IPAddressChecker` to validate the format.
    *   - **Rack and Slot**: Ensures they fall within valid ranges (0–31).
    *   - **Cycle Time**: Validates as an integer between 1 and 65535.
    *   - **Cycle Time Scaling Factor**: Ensures the value is between 1 and 5000.
    * - Throws specific exceptions (`ValidationModelException`, `BusinessModelException`) for invalid inputs.
    * - Catches and displays errors in the user interface via `CombatPlcTesterView`.
    *
    * @param IPaddress The IP address of the PLC to be validated and set.
    * @param plcrack The rack number of the PLC, must be a valid short within 0–31.
    * @param plcslot The slot number of the PLC, must be a valid short within 0–31.
    * @param cycletime The cycle time for the PLC, must be an integer within 1–65535.
    * @param cycletimescalingfactor The scaling factor for the cycle time, must be a short within 1–5000.
    */
    public void setPLCproperties(String IPaddress, String plcrack, String plcslot, String cycletime, String cycletimescalingfactor) {
        try {
            if (IPAddressChecker.checkIPAddress(IPaddress)) {
                ModelFacade.getInstance().setIPAddress(IPaddress);
            } else {
                throw new ValidationModelException("IP address not valid.");
            }
            short rack = Short.parseShort(plcrack);
            if (rack == 0 || rack <= 31) {
                ModelFacade.getInstance().setRack(rack);
            } else {
                throw new BusinessModelException("Rack number is valid from 0 to 31.");
            }
            short slot = Short.parseShort(plcslot);
            if (slot >= 0 && slot <= 31) {
                ModelFacade.getInstance().setSlot(slot);
            } else {
                throw new BusinessModelException("Slot number is valid from 0 to 31.");
            }
            int _cycletime = Integer.parseInt(cycletime);
            if (_cycletime >= 1 && _cycletime <= 65535) {
                ModelFacade.getInstance().setCycleTime(_cycletime);
            } else {
                throw new BusinessModelException("Cycletime is valid from 1 to 65535.");
            }
            short _cycletimescalingfactor = Short.parseShort(cycletimescalingfactor);
            if (_cycletimescalingfactor >= 1 && _cycletimescalingfactor <= 5000) {
                ModelFacade.getInstance().setCycleTimeScalingFactor(_cycletimescalingfactor);
            } else {
                throw new BusinessModelException("Cycletime Scalingfactor is valid from 1 to 5000.");
            }
        } catch (NumberFormatException e) {
            combatplctesterview.getPropertiesView().exceptionErrorView("Number format error: " + e.getMessage());
        } catch (ValidationModelException e) {
            combatplctesterview.getPropertiesView().exceptionErrorView(e.getMessage());
        } catch (BusinessModelException e) {
            combatplctesterview.getPropertiesView().exceptionErrorView(e.getMessage());
        } catch (Exception e) {
            combatplctesterview.getPropertiesView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
        }
    }
    
    /**
    * Updates the input and output data building block numbers in the `ModelFacade` with validated values. 
    * Ensures that the provided numbers are within the acceptable range and displays error messages for invalid inputs.
    *
    * Workflow:
    * - Parses the input and output data building block numbers as short values.
    * - Validates the range of the numbers (1–59999):
    *   - Sets the input data building block number if valid.
    *   - Sets the output data building block number if valid.
    * - Throws specific exceptions for invalid ranges or parsing errors.
    * - Displays appropriate error messages in the user interface.
    *
    * @param DBinputs The data building block number for inputs, must be a short within the range 1–59999.
    * @param DBoutputs The data building block number for outputs, must be a short within the range 1–59999.
    */
    public void setDataBuildingBlocknumbers(String DBinputs, String DBoutputs) {
        try {
            short _DBinputs = Short.parseShort(DBinputs);
            if (_DBinputs >= 1 && _DBinputs <= 59999) {
                ModelFacade.getInstance().setDataBuildingBlockNumberInputs(_DBinputs);
            } else {
                throw new BusinessModelException("Data buildingblock input number is valid from 1 to 59999.");
            }
            short _DBoutputs = Short.parseShort(DBoutputs);
            if (_DBoutputs >= 1 && _DBoutputs <= 59999) {
                ModelFacade.getInstance().setDataBuildingBlockNumberOutputs(_DBoutputs);
            } else {
                throw new BusinessModelException("Data buildingblock output number is valid from 1 to 59999.");
            }
        } catch (NumberFormatException e) {
            combatplctesterview.getPropertiesView().exceptionErrorView("Number format error: " + e.getMessage());

        } catch (BusinessModelException e) {
            combatplctesterview.getPropertiesView().exceptionErrorView(e.getMessage());

        } catch (Exception e) {
            combatplctesterview.getPropertiesView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
        }
    }

    // Signals that the ongoing test should stop by setting the `stoprequested` flag to true.
    public void stopRequest(){
        stoprequested = true;
    }
    
    /**
    * Adds a new input to the system by validating and setting its properties such as name, data type, 
    * start address, bit address, and initial value. Ensures all inputs meet the required constraints and 
    * updates the system model and views accordingly.
    *
    * Workflow:
    * - Validates the provided parameters:
    *   - **PLC Data Type**: Ensures the type is supported.
    *   - **Start Address**: Validates as a non-negative value.
    *   - **Bit Address** (for BIT type): Checks that the value is between 0 and 7.
    *   - **Initial Value**: Ensures the value is within the range specified by the PLC data type.
    * - Creates and configures the input using the `InputFactory`.
    * - Checks for duplicate inputs in the list based on start and bit addresses.
    * - Updates the model (`ModelFacade`) and views (`CombatPlcTesterView`) with the new input.
    * - Catches and displays errors related to validation or unexpected issues.
    *
    * @param name The name of the input.
    * @param plcdatatype The PLC data type of the input (e.g., BIT, BYTE, WORD).
    * @param startaddress The starting address of the input, must be a valid short.
    * @param bitaddress The bit address for BIT types, must be between 0 and 7.
    * @param initialvalue The initial value of the input, validated based on its PLC data type.
    */
    public void addInput(String name, String plcdatatype, String startaddress,
            String bitaddress, String initialvalue) {
        try {
            PlcDataType plcdtype = PlcDataType.valueOf(plcdatatype);
            inputfactory.setName(name);
            inputfactory.setPlcDataType(plcdtype);
            if (Short.parseShort(startaddress) >= 0) {
                inputfactory.setStartAddress(Short.parseShort(startaddress));
            } else {
                throw new BusinessModelException("Start address is not valid.");
            }
            switch (plcdtype) {
                case BIT:
                    if (Byte.parseByte(bitaddress) >= 0 && Byte.parseByte(bitaddress) <= 7) {
                        inputfactory.setBitAddress(Byte.parseByte(bitaddress));
                    } else {
                        throw new BusinessModelException("Bit address is valid from 0 to 7.");
                    }
                    if (initialvalue.equals("true") || (initialvalue.equals("false"))) {
                        inputfactory.setBitInitialValue(Boolean.parseBoolean(initialvalue));
                    } else {

                        throw new BusinessModelException("Initial value must be true or false");
                    }
                    break;
                case BYTE:
                    if (Short.parseShort(initialvalue) >= 0 && Short.parseShort(initialvalue) <= 255) {
                        inputfactory.setByteInitialValue(Short.parseShort(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from 0 to 255.");
                    }
                    break;
                case WORD:
                    if (Integer.parseInt(initialvalue) >= 0 && Integer.parseInt(initialvalue) <= 65535) {
                        inputfactory.setWordInitialValue(Integer.parseInt(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from  0 to 65535.");
                    }
                    break;
                case DWORD:
                    if (Long.parseLong(initialvalue) >= 0 && Long.parseLong(initialvalue) <= 4294967295L) {
                        inputfactory.setDWordInitialValue(Long.parseLong(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from  0 to 4294967295.");
                    }
                    break;
                case INT:
                    if (Short.parseShort(initialvalue) >= -32768 && Short.parseShort(initialvalue) <= 32767) {
                        inputfactory.setIntInitialValue(Short.parseShort(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from  -32768 to 32767.");
                    }
                    break;
                case DINT:
                    if (Integer.parseInt(initialvalue) >= Integer.MIN_VALUE && Integer.parseInt(initialvalue) <= Integer.MAX_VALUE) {
                        inputfactory.setDIntInitialValue(Integer.parseInt(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".");
                    }
                    break;
                case REAL:
                    float minRealValue = -3.40e38f; // Siemens S7 REAL min value
                    float maxRealValue = 3.40e38f;  // Siemens S7 REAL max value
                    float smallestNormalizedReal = 1.18e-38f; // Smallest normalized value Siemens
                    float value = Float.parseFloat(initialvalue);
                    if ((value >= minRealValue && value <= -smallestNormalizedReal)
                            || (value >= smallestNormalizedReal && value <= maxRealValue)
                            || (value == 0.0f)) {
                        inputfactory.setRealInitialValue(Float.parseFloat(initialvalue));
                    } else {
                        throw new BusinessModelException(
                                "Initial value must be within the range: " + minRealValue + " to " + -smallestNormalizedReal
                                + " or " + smallestNormalizedReal + " to " + maxRealValue + "."
                        );
                    }
                    break;
                default:
                    throw new BusinessModelException("Unsupported PLC-type.");

            }
            Input input = inputfactory.getInput();
            ModelFacade.getInstance().setInputObjectCount(Input.getObjectCount());
            ModelFacade.getInstance().getInputList().add((Input) input);
            for (Input inputinlist : ModelFacade.getInstance().getInputList()) {
                if (!input.getInputID().equals(inputinlist.getInputID()) && inputinlist.getStartAddress() == (input.getStartAddress()) && inputinlist.getBitAddress() == (input.getBitAddress())) {
                    throw new BusinessModelException("Input added to the list, but an input with the same StartAddress and BitAddress already exists.");
                }
            }
        } catch (NumberFormatException e) {
            combatplctesterview.getPropertiesView().exceptionErrorView("Number format error: " + e.getMessage());

        } catch (BusinessModelException e) {
            combatplctesterview.getInputListView().exceptionErrorView(e.getMessage());

        } catch (Exception e) {
            combatplctesterview.getInputListView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
        }
        combatplctesterview.getSequentialTransitionView().viewInputs(ModelFacade.getInstance().getInputList());
        combatplctesterview.getCombinatorialTransitionView().viewInputs(ModelFacade.getInstance().getInputList());
        combatplctesterview.getInputListView().viewInputs(ModelFacade.getInstance().getInputList());
    }
    
    /**
    * Updates the properties of an existing input identified by its `inputID`. Validates and applies changes to the name, 
    * start address, bit address (if applicable), and initial value, ensuring the input meets all required constraints. 
    * Additionally, checks for duplicate inputs with the same start and bit addresses.
    *
    * Workflow:
    * - Searches the input list in the `ModelFacade` for the input matching the given `inputID`.
    * - Updates the input's properties:
    *   - **Name**: Sets the name of the input.
    *   - **Start Address**: Validates as a non-negative value.
    *   - **Bit Address**: For `InputBit` types, ensures the value is between 0 and 7.
    *   - **Initial Value**: Validates based on the input type (BIT, BYTE, WORD, etc.).
    * - Checks for duplicate inputs with the same start and bit addresses in the list.
    * - Displays appropriate error messages for invalid inputs or unexpected errors.
    *
    * @param inputID The unique identifier of the input to update.
    * @param name The new name for the input.
    * @param startaddress The updated start address for the input, must be a valid short.
    * @param bitaddress The updated bit address (applicable for `InputBit` types), must be between 0 and 7.
    * @param initialvalue The new initial value, validated based on the input's type.
    */
    public void updateInput(String inputID, String name, String startaddress, String bitaddress, String initialvalue) {
        Input updatedinput = null;
        for (Input input : ModelFacade.getInstance().getInputList()) {
            if (input.getInputID().equals(inputID)) {
                try {
                    input.setName(name);
                    if (Short.parseShort(startaddress) >= 0) {
                        input.setStartAddress(Short.parseShort(startaddress));
                    } else {
                        throw new BusinessModelException("Start address is not valid.");
                    }
                    if (input instanceof InputBit) {
                        if (Byte.parseByte(bitaddress) >= 0 && Byte.parseByte(bitaddress) <= 7) {
                            input.setBitAddress(Byte.parseByte(bitaddress));
                        } else {
                            throw new BusinessModelException("Bit address is valid from 0 to 7.");
                        }
                        if (initialvalue.equals("true") || (initialvalue.equals("false"))) {
                            input.setInitialValue(Boolean.parseBoolean(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value must be true or false");
                        }
                    } else if (input instanceof InputByte) {
                        if (Short.parseShort(initialvalue) >= 0 && Short.parseShort(initialvalue) <= 255) {
                            input.setInitialValue(Short.parseShort(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from  0 to 255.");
                        }
                    } else if (input instanceof InputWord) {
                        if (Integer.parseInt(initialvalue) >= 0 && Integer.parseInt(initialvalue) <= 65535) {
                            input.setInitialValue(Integer.parseInt(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from 0 to 65535.");
                        }
                    } else if (input instanceof InputDWord) {
                        if (Long.parseLong(initialvalue) >= 0 && Long.parseLong(initialvalue) <= 4294967295L) {
                            input.setInitialValue(Long.parseLong(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from 0 to 4294967295.");
                        }
                    } else if (input instanceof InputInt) {
                        if (Short.parseShort(initialvalue) >= -32768 && Short.parseShort(initialvalue) <= 32767) {
                            input.setInitialValue(Short.parseShort(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from -32768 to 32767.");
                        }
                    } else if (input instanceof InputDInt) {
                        if (Integer.parseInt(initialvalue) >= Integer.MIN_VALUE && Integer.parseInt(initialvalue) <= Integer.MAX_VALUE) {
                            input.setInitialValue(Integer.parseInt(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".");
                        }
                    } else if (input instanceof InputReal) {
                        float minRealValue = -3.40e38f; // Siemens S7 REAL min value
                        float maxRealValue = 3.40e38f;  // Siemens S7 REAL max value
                        float smallestNormalizedReal = 1.18e-38f; // Smallest normalized value Siemens
                        float value = Float.parseFloat(initialvalue);
                        if ((value >= minRealValue && value <= -smallestNormalizedReal)
                                || (value >= smallestNormalizedReal && value <= maxRealValue)
                                || (value == 0.0f)) { 
                            input.setInitialValue(value);
                        } else {
                            throw new BusinessModelException(
                                    "Initial value must be within the range: " + minRealValue + " to " + -smallestNormalizedReal
                                    + " or " + smallestNormalizedReal + " to " + maxRealValue + "."
                            );
                        }
                    }
                    updatedinput = input;
                } catch (NumberFormatException e) {
                    combatplctesterview.getInputListView().exceptionErrorView("Number format error: " + e.getMessage());
                } catch (BusinessModelException e) {
                    combatplctesterview.getInputListView().exceptionErrorView(e.getMessage());
                } catch (Exception e) {
                    combatplctesterview.getInputListView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
                }
                for (Input inputinlist : ModelFacade.getInstance().getInputList()) {
                    try {
                        if (!input.getInputID().equals(inputinlist.getInputID()) && inputinlist.getStartAddress() == (input.getStartAddress()) && inputinlist.getBitAddress() == (input.getBitAddress())) {
                            throw new BusinessModelException("Input updated, but an input with the same StartAddress and BitAddress already exists.");
                        }
                    } catch (BusinessModelException e) {
                        combatplctesterview.getInputListView().exceptionErrorView(e.getMessage());
                    } catch (Exception e) {
                        combatplctesterview.getInputListView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
                    }
                }
            }
        }
        if (!ModelFacade.getInstance().getTransitionGraphElementList().isEmpty() && updatedinput != null) {
            for (GraphElement transitiongraphelement : ModelFacade.getInstance().getTransitionGraphElementList()) {
                Transition transition = (Transition) transitiongraphelement;
                if (!transition.getInputs().isEmpty()) {
                    for (Input input : transition.getInputs()) {
                        if (input.getInputID().equals(updatedinput.getInputID())) {
                            input.setName(updatedinput.getName());
                            input.setStartAddress(updatedinput.getStartAddress());
                            input.setBitAddress(updatedinput.getBitAddress());
                            input.setInitialValue(updatedinput.getInitialValue());
                        }
                    }
                }
                combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(transition.getInputs());
                combatplctesterview.getSequentialTransitionView().viewTransitionInputs(transition.getInputs());
            }
        }
        combatplctesterview.getCombinatorialTransitionView().viewInputs(ModelFacade.getInstance().getInputList());
        combatplctesterview.getSequentialTransitionView().viewInputs(ModelFacade.getInstance().getInputList());
        combatplctesterview.getInputListView().viewInputs(ModelFacade.getInstance().getInputList());
    }

    /**
    * Deletes an input from the model based on its unique `inputID`. Ensures the input is removed from the input list 
    * as well as from all transitions that reference it. Updates the user interface to reflect the changes.
    *
    * Workflow:
    * 1. Searches the `InputList` in the `ModelFacade` for the input matching the given `inputID`.
    * 2. Removes the input from the list of inputs.
    * 3. Iterates through all transitions in the `TransitionGraphElementList` to:
    *    - Remove references to the deleted input from transitions.
    * 4. Updates the relevant views in the `CombatPlcTesterView`.
    *
    * @param inputID The unique identifier of the input to be deleted.
    */
    public void deleteInputByID(String inputID) {
        Input deleteinput = null;
        for (Input input : ModelFacade.getInstance().getInputList()) {
            if (input.getInputID().equals(inputID)) {
                deleteinput = input;
            }
        }
        for (GraphElement transitiongraphelement : ModelFacade.getInstance().getTransitionGraphElementList()) {
            Transition transition = (Transition) transitiongraphelement;
            Iterator<Input> interator = transition.getInputs().iterator();
            while (interator.hasNext()) {
                Input input = interator.next();
                if (input.getInputID().equals(deleteinput.getInputID())) {
                    interator.remove();
                }
            }
        }
        ModelFacade.getInstance().getInputList().remove(deleteinput);
        combatplctesterview.getInputListView().viewInputs(ModelFacade.getInstance().getInputList());
        combatplctesterview.getSequentialTransitionView().viewInputs(ModelFacade.getInstance().getInputList());
        combatplctesterview.getSequentialTransitionView().viewTransitionInputs(ModelFacade.getInstance().getInputList());
        combatplctesterview.getCombinatorialTransitionView().viewInputs(ModelFacade.getInstance().getInputList());
        combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(ModelFacade.getInstance().getInputList());
    }

    /**
    * Adds a new output to the system by validating and setting its properties such as name, data type, 
    * start address, bit address, and initial value. Ensures that all outputs meet the required constraints and 
    * updates the model and views accordingly.
    *
    * Workflow:
    * - Validates the provided parameters:
    *   - **PLC Data Type**: Ensures the type is supported.
    *   - **Start Address**: Validates as a non-negative value.
    *   - **Bit Address** (for BIT type): Checks that the value is between 0 and 7.
    *   - **Initial Value**: Validates based on the output's PLC data type (BIT, BYTE, WORD, etc.).
    * - Creates and configures the output using the `OutputFactory`.
    * - Checks for duplicate outputs in the list based on start and bit addresses.
    * - Updates the model (`ModelFacade`) and views (`CombatPlcTesterView`) with the new output.
    * - Catches and displays errors related to validation or unexpected issues.
    *
    * @param name The name of the output.
    * @param plcdatatype The PLC data type of the output (BIT, BYTE, WORD).
    * @param startaddress The starting address of the output, must be a valid short.
    * @param bitaddress The bit address for BIT types, must be between 0 and 7.
    * @param initialvalue The initial value of the output, validated based on its PLC data type.
    */
    public void addOutput(String name, String plcdatatype, String startaddress, String bitaddress, String initialvalue) {
        try {
            PlcDataType plcdtype = PlcDataType.valueOf(plcdatatype);
            outputfactory.setName(name);
            outputfactory.setPlcDataType(plcdtype);
            if (Short.parseShort(startaddress) >= 0) {
                outputfactory.setStartAddress(Short.parseShort(startaddress));
            } else {
                throw new BusinessModelException("Start address is not valid.");
            }
            switch (plcdtype) {
                case BIT:
                    if (Byte.parseByte(bitaddress) >= 0 && Byte.parseByte(bitaddress) <= 7) {
                        outputfactory.setBitAddress(Byte.parseByte(bitaddress));
                    } else {
                        throw new BusinessModelException("Bit address is valid from 0 to 7.");
                    }
                    if (initialvalue.equals("true") || (initialvalue.equals("false"))) {
                        outputfactory.setBitInitialValue(Boolean.parseBoolean(initialvalue));
                    } else {

                        throw new BusinessModelException("Initial value must be true or false");
                    }
                    break;
                case BYTE:
                    if (Short.parseShort(initialvalue) >= 0 && Short.parseShort(initialvalue) <= 255) {
                        outputfactory.setByteInitialValue(Short.parseShort(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from 0 to 255.");
                    }
                    break;
                case WORD:
                    if (Integer.parseInt(initialvalue) >= 0 && Integer.parseInt(initialvalue) <= 65535) {
                        outputfactory.setWordInitialValue(Integer.parseInt(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from 0 to 65535.");
                    }
                    break;
                case DWORD:
                    if (Long.parseLong(initialvalue) >= 0 && Long.parseLong(initialvalue) <= 4294967295L) {
                        outputfactory.setDWordInitialValue(Long.parseLong(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from 0 to 4294967295.");
                    }
                    break;
                case INT:
                    if (Short.parseShort(initialvalue) >= -32768 && Short.parseShort(initialvalue) <= 32767) {
                        outputfactory.setIntInitialValue(Short.parseShort(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from -32768 to 32767.");
                    }
                    break;
                case DINT:
                    if (Integer.parseInt(initialvalue) >= Integer.MIN_VALUE && Integer.parseInt(initialvalue) <= Integer.MAX_VALUE) {
                        outputfactory.setDIntInitialValue(Integer.parseInt(initialvalue));
                    } else {
                        throw new BusinessModelException("Initial value is valid from " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".");
                    }
                    break;
                case REAL:
                    float minRealValue = -3.40e38f; // Siemens S7 REAL min value
                    float maxRealValue = 3.40e38f;  // Siemens S7 REAL max value
                    float smallestNormalizedReal = 1.18e-38f; // Smallest normalized value Siemens
                    float value = Float.parseFloat(initialvalue);
                    if ((value >= minRealValue && value <= -smallestNormalizedReal)
                            || (value >= smallestNormalizedReal && value <= maxRealValue)
                            || (value == 0.0f)) {
                        outputfactory.setRealInitialValue(Float.parseFloat(initialvalue));
                    } else {
                        throw new BusinessModelException(
                                "Initial value must be within the range: " + minRealValue + " to " + -smallestNormalizedReal
                                + " or " + smallestNormalizedReal + " to " + maxRealValue + "."
                        );
                    }
                    break;
                default:
                    throw new BusinessModelException("Unsupported PLC-type.");
            }
            Output output = outputfactory.getOutput();
            ModelFacade.getInstance().setOutputObjectCount(Output.getObjectCount());
            ModelFacade.getInstance().getOutputList().add((Output) output);
            for (Output outputinlist : ModelFacade.getInstance().getOutputList()) {
                if (!output.getOutputID().equals(outputinlist.getOutputID()) && outputinlist.getStartAddress() == (output.getStartAddress()) && outputinlist.getBitAddress() == (output.getBitAddress())) {
                    throw new BusinessModelException("output added to the list, but an output with the same StartAddress and BitAddress already exists.");
                }
            }
        } catch (NumberFormatException e) {
            combatplctesterview.getOutputListView().exceptionErrorView("Number format error: " + e.getMessage());

        } catch (BusinessModelException e) {
            combatplctesterview.getOutputListView().exceptionErrorView(e.getMessage());

        } catch (Exception e) {
            combatplctesterview.getOutputListView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
        }
        combatplctesterview.getStateView().viewOutputs(ModelFacade.getInstance().getOutputList());
        combatplctesterview.getOutputListView().viewOutputs(ModelFacade.getInstance().getOutputList());
    }
    
    /**
    * Updates the properties of an existing output identified by its `outputID`. Validates and applies changes to the name, 
    * start address, bit address (if applicable), and initial value, ensuring the output meets all required constraints. 
    *
    * Workflow:
    * 1. Searches the `OutputList` in the `ModelFacade` for the output matching the given `outputID`.
    * 2. Validates and updates the output's properties:
    *    - **Name**: Sets the new name for the output.
    *    - **Start Address**: Validates as a non-negative value.
    *    - **Bit Address**: For `OutputBit` types, ensures the value is between 0 and 7.
    *    - **Initial Value**: Validates based on the output type (BIT, BYTE, WORD, etc.).
    * 3. Checks for duplicate outputs in the list based on start and bit addresses.
    * 4. Updates any states that reference the modified output with the new values.
    * 5. Refreshes the relevant views in the `CombatPlcTesterView`.
    *
    * @param outputID The unique identifier of the output to update.
    * @param name The new name for the output.
    * @param startaddress The updated start address for the output, must be a valid short.
    * @param bitaddress The updated bit address (applicable for `OutputBit` types), must be between 0 and 7.
    * @param initialvalue The new initial value, validated based on the output's type.
    */
    public void updateOutput(String outputID, String name, String startaddress, String bitaddress, String initialvalue) {
        Output updatedoutput = null;
        for (Output output : ModelFacade.getInstance().getOutputList()) {
            if (output.getOutputID().equals(outputID)) {
                try {
                    output.setName(name);
                    if (Short.parseShort(startaddress) >= 0) {
                        output.setStartAddress(Short.parseShort(startaddress));
                    } else {
                        throw new BusinessModelException("Start address is not valid.");
                    }
                    if (output instanceof OutputBit) {
                        if (Byte.parseByte(bitaddress) >= 0 && Byte.parseByte(bitaddress) <= 7) {
                            output.setBitAddress(Byte.parseByte(bitaddress));
                        } else {
                            throw new BusinessModelException("Bit address is valid from 0 to 7.");
                        }
                        if (initialvalue.equals("true") || (initialvalue.equals("false"))) {
                            output.setInitialValue(Boolean.parseBoolean(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value must be true or false");
                        }
                    } else if (output instanceof OutputByte) {
                        if (Short.parseShort(initialvalue) >= 0 && Short.parseShort(initialvalue) <= 255) {
                            output.setInitialValue(Short.parseShort(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from 0 to 255.");
                        }
                    } else if (output instanceof OutputWord) {
                        if (Integer.parseInt(initialvalue) >= 0 && Integer.parseInt(initialvalue) <= 65535) {
                            output.setInitialValue(Integer.parseInt(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from 0 to 65535.");
                        }
                    } else if (output instanceof OutputDWord) {
                        if (Long.parseLong(initialvalue) >= 0 && Long.parseLong(initialvalue) <= 4294967295L) {
                            output.setInitialValue(Long.parseLong(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from 0 to 4294967295.");
                        }
                    } else if (output instanceof OutputInt) {
                        if (Short.parseShort(initialvalue) >= -32768 && Short.parseShort(initialvalue) <= 32767) {
                            output.setInitialValue(Short.parseShort(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from -32768 to 32767.");
                        }
                    } else if (output instanceof OutputDInt) {
                        if (Integer.parseInt(initialvalue) >= Integer.MIN_VALUE && Integer.parseInt(initialvalue) <= Integer.MAX_VALUE) {
                            output.setInitialValue(Integer.parseInt(initialvalue));
                        } else {
                            throw new BusinessModelException("Initial value is valid from " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".");
                        }
                    } else if (output instanceof OutputReal) {
                        float minRealValue = -3.40e38f; // Siemens S7 REAL min value
                        float maxRealValue = 3.40e38f;  // Siemens S7 REAL max value
                        float smallestNormalizedReal = 1.18e-38f; // Smallest normalized value Siemens
                        float value = Float.parseFloat(initialvalue);
                        if ((value >= minRealValue && value <= -smallestNormalizedReal)
                                || (value >= smallestNormalizedReal && value <= maxRealValue)
                                || (value == 0.0f)) {
                            output.setInitialValue(value);
                        } else {
                            throw new BusinessModelException(
                                    "Initial value must be within the range: " + minRealValue + " to " + -smallestNormalizedReal
                                    + " or " + smallestNormalizedReal + " to " + maxRealValue + "."
                            );
                        }
                    }
                    updatedoutput = output;
                } catch (NumberFormatException e) {
                    combatplctesterview.getOutputListView().exceptionErrorView("Number format error: " + e.getMessage());
                } catch (BusinessModelException e) {
                    combatplctesterview.getOutputListView().exceptionErrorView(e.getMessage());
                } catch (Exception e) {
                    combatplctesterview.getOutputListView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
                }
                for (Output outputinlist : ModelFacade.getInstance().getOutputList()) {
                    try {
                        if (!output.getOutputID().equals(outputinlist.getOutputID()) && outputinlist.getStartAddress() == (output.getStartAddress()) && outputinlist.getBitAddress() == (output.getBitAddress())) {
                            throw new BusinessModelException("Output updated, but an output with the same StartAddress and BitAddress already exists.");
                        }
                    } catch (BusinessModelException e) {
                        combatplctesterview.getInputListView().exceptionErrorView(e.getMessage());

                    } catch (Exception e) {
                        combatplctesterview.getInputListView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
                    }
                }
            }
        }
        if (!ModelFacade.getInstance().getStateGraphElementList().isEmpty() && updatedoutput != null) {
            for (GraphElement stategraphelement : ModelFacade.getInstance().getStateGraphElementList()) {
                State state = (State) stategraphelement;
                if (!state.getOutputs().isEmpty()) {
                    for (Output output : state.getOutputs()) {
                        if (output.getOutputID().equals(updatedoutput.getOutputID())) {
                            output.setName(updatedoutput.getName());
                            output.setStartAddress(updatedoutput.getStartAddress());
                            output.setBitAddress(updatedoutput.getBitAddress());
                            output.setInitialValue(updatedoutput.getInitialValue());
                        }
                    }
                }
            }
        }
        combatplctesterview.getOutputListView().viewOutputs(ModelFacade.getInstance().getOutputList());
        combatplctesterview.getStateView().viewOutputs(ModelFacade.getInstance().getOutputList());
    }
    
    /**
    * Deletes an output identified by its `outputID` from the system. Removes the output from the main list and any states 
    * that reference it, ensuring the model remains consistent. Updates the user interface to reflect the deletion.
    *
    * Workflow:
    * 1. Locates the output in the `OutputList` of the `ModelFacade` by matching the `outputID`.
    * 2. Iterates through all states in the `StateGraphElementList`:
    *    - Removes references to the deleted output from the states' output lists.
    * 3. Deletes the output from the `OutputList` in the `ModelFacade`.
    * 4. Refreshes views in the `CombatPlcTesterView` to reflect the updated output and state data.
    *
    * @param outputID The unique identifier of the output to delete.
    */
    public void deleteOutputByID(String outputID) {
        Output deleteoutput = null;
        for (Output output : ModelFacade.getInstance().getOutputList()) {
            if (output.getOutputID().equals(outputID)) {
                deleteoutput = output;
            }
        }
        for (GraphElement stategraphelement : ModelFacade.getInstance().getStateGraphElementList()) {
            State state = (State) stategraphelement;
            Iterator<Output> interator = state.getOutputs().iterator();
            while (interator.hasNext()) {
                Output output = interator.next();
                if (output.getOutputID().equals(deleteoutput.getOutputID())) {
                    interator.remove();
                }
            }
        }
        ModelFacade.getInstance().getOutputList().remove(deleteoutput);
        combatplctesterview.getOutputListView().viewOutputs(ModelFacade.getInstance().getOutputList());
        combatplctesterview.getStateView().viewOutputs(ModelFacade.getInstance().getOutputList());
        combatplctesterview.getStateView().viewStateOutputs(ModelFacade.getInstance().getOutputList());
    }
    
    /**
    * Adds a new state to the system at the specified coordinates and dimensions. Configures the state with a given renderer, 
    * updates the model, and refreshes the relevant views to reflect the addition.
    *
    * Workflow:
    * 1. Creates a new `State` object with the specified parameters.
    * 2. Updates the model:
    *    - Sets the state object count in the `ModelFacade`.
    *    - Adds the new state to the `StateGraphElementList` in the `ModelFacade`.
    * 3. Configures the state with the provided `GraphElementRenderer`.
    * 4. Updates the `CombatPlcTesterView` to display the new state and its outputs.
    * 5. Handles any exceptions during the process:
    *    - Displays appropriate error messages for number format issues or unexpected exceptions.
    *
    * @param x The X-coordinate for the state's position.
    * @param y The Y-coordinate for the state's position.
    * @param width The width of the state.
    * @param height The height of the state.
    * @param graphelementrenderer The renderer used to visualize the state.
    *
    * @return The newly created `State` object, or `null` if an error occurs during the creation process.
    */
    public State addState(double x, double y, double width, double height, GraphElementRenderer graphelementrenderer) {
        State state = null;
        try {
            state = new State(x, y, width, height);
            ModelFacade.getInstance().setStateObjectCount(State.getObjectCount());
            state.setGraphElementRenderer(graphelementrenderer);
            ModelFacade.getInstance().getStateGraphElementList().add(state);
            combatplctesterview.getStateView().viewState(state);
            combatplctesterview.getStateView().viewStateOutputs(state.getOutputs());
        } catch (NumberFormatException e) {
            combatplctesterview.getModelView().exceptionErrorView("Number format error: " + e.getMessage());
        } catch (Exception e) {
            combatplctesterview.getModelView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
        }
        return state;
    }

    /**
    * Adds an existing `State` object to the model and updates the user interface to reflect its addition.
    *
    * @param state The `State` object to be added to the system.
    */
    public void addState(State state) {
        ModelFacade.getInstance().getStateGraphElementList().add(state);
        combatplctesterview.getStateView().viewState(state);
    }
    
    /**
    * Handles the selection of a state identified by its `stateID`. Updates the user interface to display the 
    * selected state's details and outputs while hiding other irrelevant views.
    *
    * Workflow:
    * 1. Iterates through the `StateGraphElementList` in the `ModelFacade` to find a state with the matching `stateID`.
    * 2. If a match is found:
    *    - Makes the `StateView` visible.
    *    - Hides the `SequentialTransitionView` and `CombinatorialTransitionView`.
    *    - Updates the `StateView` with the selected state's details and outputs.
    * 3. Stops further processing after the matching state is found and handled.
    *
    * @param stateID The unique identifier of the state to handle.
    */
    public void handleSelectedState(String stateID) {
        for (GraphElement stategraphelement : ModelFacade.getInstance().getStateGraphElementList()) {
            State state = (State) stategraphelement;
            if (state.getStateID().equals(stateID)) {
                combatplctesterview.getStateView().setVisible(true);
                combatplctesterview.getSequentialTransitionView().setVisible(false);
                combatplctesterview.getCombinatorialTransitionView().setVisible(false);
                combatplctesterview.getStateView().viewState(state);
                combatplctesterview.getStateView().viewStateOutputs(state.getOutputs());
                break;
            }
        }
    }
    
    /**
    * Updates the properties of a state identified by its `stateID`, including its name, timer condition, 
    * and timer condition tolerance. Refreshes the user interface to reflect the changes.
    *
    * Workflow:
    * 1. Iterates through the `StateGraphElementList` in the `ModelFacade` to find a state matching the `stateID`.
    * 2. If a match is found:
    *    - Updates the state's name.
    *    - Validates and updates the timer condition and timer condition tolerance.
    *    - Refreshes the `StateView` to display the updated state and its outputs.
    * 3. Handles any exceptions that occur during validation or updates:
    *    - Displays appropriate error messages for number format issues or business logic violations.
    *    - Catches unexpected errors and provides feedback to the user.
    * 4. Stops further processing after the matching state is updated.
    *
    * @param stateID The unique identifier of the state to update.
    * @param name The new name for the state.
    * @param timercondition The new timer condition for the state, which must be a non-negative integer.
    * @param timerconditiontolerance The new timer condition tolerance for the state, which must be a non-negative integer.
    */
    public void updateState(String stateID, String name, String timercondition, String timerconditiontolerance) {
        for (GraphElement stategraphelement : ModelFacade.getInstance().getStateGraphElementList()) {
            State state = (State) stategraphelement;
            if (state.getStateID().equals(stateID)) {
                state.setName(name);
                try {
                    if (Integer.parseInt(timercondition) >= 0) {
                        state.setTimerCondition(Integer.parseInt(timercondition));
                    } else {
                        throw new BusinessModelException("Timercondition not valid lower then 0.");
                    }
                    if (Integer.parseInt(timercondition) >= 0) {
                        state.setTimerConditionTolerance(Integer.parseInt(timerconditiontolerance));
                    } else {
                        throw new BusinessModelException("Timerconditiontolerance not valid lower then 0.");
                    }
                    combatplctesterview.getStateView().viewState(state);
                    combatplctesterview.getStateView().viewStateOutputs(state.getOutputs());
                } catch (NumberFormatException e) {
                    combatplctesterview.getStateView().exceptionErrorView("Number format error: " + e.getMessage());
                } catch (BusinessModelException e) {
                    combatplctesterview.getStateView().exceptionErrorView(e.getMessage());
                } catch (Exception e) {
                    combatplctesterview.getStateView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
                }
                break;
            }
        }
    }
    
    /**
    * Deletes a state identified by its `stateID` if it meets the necessary conditions. 
    * Ensures that the initial state `S0` or states with associated transitions cannot be deleted.
    *
    * Workflow:
    * 1. Validates the `stateID`:
    *    - Throws a `BusinessModelException` if the state is `S0` as it cannot be deleted.
    * 2. Locates the state to delete from the `StateGraphElementList`.
    * 3. Checks if the state has associated transitions:
    *    - Iterates through the `TransitionGraphElementList` to find transitions linked to the state.
    *    - Throws a `BusinessModelException` if transitions are found.
    * 4. If the state is eligible for deletion:
    *    - Removes the state from the `StateGraphElementList`.
    *    - Sets the selected state to `S0`.
    *    - Triggers a repaint of the model view.
    * 5. Handles exceptions:
    *    - Displays error messages for invalid operations or business logic violations.
    *
    * @param stateID The unique identifier of the state to be deleted.
    */
    public void deleteState(String stateID) {
        boolean statehastransitions = false;
        State statetodelete = null;
        try {
            if (stateID.equals("S0")) {
                throw new BusinessModelException("Initial State S0 cannot be deleted.");
            }
        } catch (BusinessModelException e) {
            combatplctesterview.getStateView().exceptionErrorView(e.getMessage());
            handleSelectedState("S0");
            return;
        }
        try {
            Iterator<GraphElement> interator = ModelFacade.getInstance().getTransitionGraphElementList().iterator();
            for (GraphElement stategraphelement : ModelFacade.getInstance().getStateGraphElementList()) {
                State state = (State) stategraphelement;
                if (state.getStateID().equals(stateID) && !state.getStateID().equals("S0")) {
                    statetodelete = state;
                    break;
                }
            }
            if (statetodelete != null) {
                while (interator.hasNext()) {
                    GraphElement transitiongraphelement = interator.next();
                    Transition transition = (Transition) transitiongraphelement;
                    if (transition.getStartStateID().equals(statetodelete.getStateID()) || transition.getEndStateID().equals(statetodelete.getStateID())) {
                        statehastransitions = true;
                    }
                }
            }
            if (statehastransitions) {
                throw new BusinessModelException("State " + statetodelete.getStateID() + " has transitions and cannot be deleted.");
            } else {
                ModelFacade.getInstance().getStateGraphElementList().remove(statetodelete);
                handleSelectedState("S0");
                combatplctesterview.getModelView().repaint();
            }
        } catch (BusinessModelException e) {
            combatplctesterview.getStateView().exceptionErrorView(e.getMessage());
        }
    }
    
    /**
    * Adds an output to a specified state, ensuring the output is not already present in the state and validating 
    * the values for before and after the time condition based on the output type.
    *
    * Workflow:
    * 1. Retrieves the state and output by their respective IDs.
    * 2. Validates that the output is not already linked to the state.
    * 3. Depending on the output type:
    *    - Ensures that the `valueBeforeTimeCondition` and `valueAfterTimeCondition` meet type-specific constraints.
    *    - Creates a new state-specific output with the provided conditions.
    *    - Adds the output to the state.
    * 4. Handles exceptions:
    *    - Displays appropriate error messages for invalid input formats or business logic violations.
    * 5. Updates the user interface to reflect changes to the state's outputs.
    *
    * @param stateID               The unique identifier of the state to which the output will be added.
    * @param outputID              The unique identifier of the output to be added.
    * @param valuebeforetimecondition The value of the output before the time condition is met.
    * @param valueaftertimecondition  The value of the output after the time condition is met.
    */
    public void addOutputToState(String stateID, String outputID, String valuebeforetimecondition, String valueaftertimecondition) {
        State state = ModelFacade.getInstance().getStateByID(stateID);
        Output output = ModelFacade.getInstance().getOutputByID(outputID);
        try {
            for (Output _output : state.getOutputs()) {
                if (_output.getOutputID().equals(output.getOutputID())) {
                    throw new BusinessModelException("State already contains this output.");
                }
            }
            if (output instanceof OutputBit) {
                if ((valuebeforetimecondition.equals("true") || valuebeforetimecondition.equals("false"))
                        && (valueaftertimecondition.equals("true") || valueaftertimecondition.equals("false"))) {
                    Output<Boolean> stateoutput = new OutputBit(output);
                    stateoutput.setValueBeforeTimeCondition(Boolean.parseBoolean(valuebeforetimecondition));
                    stateoutput.setValueAfterTimeCondition(Boolean.parseBoolean(valueaftertimecondition));
                    state.addOutput(stateoutput);
                } else {
                    throw new BusinessModelException("Values of time condition must be true or false");
                }
            } else if (output instanceof OutputByte) {
                Output<Short> stateoutput = new OutputByte(output);
                if (Short.parseShort(valuebeforetimecondition) >= 0 && Short.parseShort(valuebeforetimecondition) <= 255) {
                    stateoutput.setValueBeforeTimeCondition(Short.parseShort(valuebeforetimecondition));
                } else {
                    throw new BusinessModelException("Value before timecondition is valid from 0 to 255.");
                }
                if (Short.parseShort(valueaftertimecondition) >= 0 && Short.parseShort(valueaftertimecondition) <= 255) {
                    stateoutput.setValueAfterTimeCondition(Short.parseShort(valueaftertimecondition));
                } else {
                    throw new BusinessModelException("Values after timecondition is valid from 0 to 255.");
                }
                state.addOutput(stateoutput);
            } else if (output instanceof OutputWord) {
                Output<Integer> stateoutput = new OutputWord(output);
                if (Integer.parseInt(valuebeforetimecondition) >= 0 && Integer.parseInt(valuebeforetimecondition) <= 65535) {
                    stateoutput.setValueBeforeTimeCondition(Integer.parseInt(valuebeforetimecondition));
                } else {
                    throw new BusinessModelException("Initial value is valid from 0 to 65535.");
                }
                if (Integer.parseInt(valueaftertimecondition) >= 0 && Integer.parseInt(valueaftertimecondition) <= 65535) {
                    stateoutput.setValueAfterTimeCondition(Integer.parseInt(valueaftertimecondition));
                } else {
                    throw new BusinessModelException("Initial value is valid from  0 to 65535.");
                }
                state.addOutput(stateoutput);
            } else if (output instanceof OutputDWord) {
                Output<Long> stateoutput = new OutputDWord(output);
                if (Long.parseLong(valuebeforetimecondition) >= 0 && Long.parseLong(valuebeforetimecondition) <= 4294967295L) {
                    stateoutput.setValueBeforeTimeCondition(Long.parseLong(valuebeforetimecondition));
                } else {
                    throw new BusinessModelException("Initial value is valid from 0 to 4294967295.");
                }
                if (Long.parseLong(valueaftertimecondition) >= 0 && Long.parseLong(valueaftertimecondition) <= 4294967295L) {
                    stateoutput.setValueAfterTimeCondition(Long.parseLong(valueaftertimecondition));
                } else {
                    throw new BusinessModelException("Initial value is valid from  0 to 4294967295.");
                }
                state.addOutput(stateoutput);
            } else if (output instanceof OutputInt) {
                Output<Short> stateoutput = new OutputInt(output);
                if (Short.parseShort(valuebeforetimecondition) >= -32768 && Short.parseShort(valuebeforetimecondition) <= 32767) {
                    stateoutput.setValueBeforeTimeCondition(Short.parseShort(valuebeforetimecondition));
                } else {
                    throw new BusinessModelException("Initial value is valid from -32768 to 32767.");
                }
                if (Short.parseShort(valueaftertimecondition) >= 0 && Short.parseShort(valueaftertimecondition) <= 32767) {
                    stateoutput.setValueAfterTimeCondition(Short.parseShort(valueaftertimecondition));
                } else {
                    throw new BusinessModelException("Initial value is valid from -32768 to 32767.");
                }
                state.addOutput(stateoutput);
            } else if (output instanceof OutputDInt) {
                Output<Integer> stateoutput = new OutputDInt(output);
                if (Integer.parseInt(valuebeforetimecondition) >= Integer.MIN_VALUE && Integer.parseInt(valuebeforetimecondition) <= Integer.MAX_VALUE) {
                    stateoutput.setValueBeforeTimeCondition(Integer.parseInt(valuebeforetimecondition));
                } else {
                    throw new BusinessModelException("Initial value is valid from " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".");
                }
                if (Integer.parseInt(valueaftertimecondition) >= Integer.MIN_VALUE && Integer.parseInt(valueaftertimecondition) <= Integer.MAX_VALUE) {
                    stateoutput.setValueAfterTimeCondition(Integer.parseInt(valueaftertimecondition));
                } else {
                    throw new BusinessModelException("Initial value is valid from " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".");
                }
                state.addOutput(stateoutput);
            } else if (output instanceof OutputReal) {
    Output<Float> stateoutput = new OutputReal(output); 
    float minRealValue = -3.40e38f; // Siemens S7 REAL min value
    float maxRealValue = 3.40e38f;  // Siemens S7 REAL max value
    float smallestNormalizedReal = 1.18e-38f; // Smallest normalized value Siemens
    float valueBefore = Float.parseFloat(valuebeforetimecondition);
    float valueAfter = Float.parseFloat(valueaftertimecondition);
    if ((valueBefore >= minRealValue && valueBefore <= -smallestNormalizedReal) || 
        (valueBefore >= smallestNormalizedReal && valueBefore <= maxRealValue) || 
        (valueBefore == 0.0f)) {
        stateoutput.setValueBeforeTimeCondition(valueBefore);
    } else {
        throw new BusinessModelException(
            "Value before time condition must be within the range: " 
            + minRealValue + " to " + -smallestNormalizedReal + 
            " or " + smallestNormalizedReal + " to " + maxRealValue + "."
        );
    }
                if ((valueAfter >= minRealValue && valueAfter <= -smallestNormalizedReal)
                        || (valueAfter >= smallestNormalizedReal && valueAfter <= maxRealValue)
                        || (valueAfter == 0.0f)) {
                    stateoutput.setValueAfterTimeCondition(valueAfter);
                } else {
                    throw new BusinessModelException(
                            "Value after time condition must be within the range: "
                            + minRealValue + " to " + -smallestNormalizedReal
                            + " or " + smallestNormalizedReal + " to " + maxRealValue + "."
                    );
                }
                state.addOutput(stateoutput);
            }

        } catch (NumberFormatException e) {
            combatplctesterview.getStateView().exceptionErrorView("Number format error: " + e.getMessage());
        } catch (BusinessModelException e) {
            combatplctesterview.getStateView().exceptionErrorView(e.getMessage());
        } catch (Exception e) {
            combatplctesterview.getStateView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
        }
        combatplctesterview.getStateView().viewStateOutputs(state.getOutputs());
    }

    /**
     * Updates the values of an output associated with a specific state,
     * ensuring the new values are valid according to the output type's constraints.
    * 
    * Workflow:
    * 1. Retrieves the state and its outputs using the provided state ID.
    * 2. Identifies the target output using its ID.
    * 3. Based on the output type:
    *    - Validates `valuebeforetimecondition` and `valueaftertimecondition`.
    *    - Updates the values for the output.
    * 4. Handles exceptions for invalid input formats and logical errors:
    *    - `BusinessModelException`: Thrown for business rule violations, such as invalid value ranges.
    *    - `NumberFormatException`: Thrown for improper numeric input formats.
    *    - Other unexpected exceptions are caught to prevent application crashes.
    * 5. Updates the UI to display the updated outputs.
    * 
    * @param stateID                The unique identifier of the state to update.
    * @param outputID               The unique identifier of the output to update.
    * @param valuebeforetimecondition The new value for the output before the time condition is met.
    * @param valueaftertimecondition  The new value for the output after the time condition is met.
    */
    public void updateStateOutput(String stateID, String outputID, String valuebeforetimecondition, String valueaftertimecondition) {
        State state = ModelFacade.getInstance().getStateByID(stateID);
        List<Output> stateoutputs = state.getOutputs();
        for (Output output : stateoutputs) {
            if (output.getOutputID().equals(outputID)) {
                try {
                    if (output instanceof OutputBit) {
                        if ((valuebeforetimecondition.equals("true") || valuebeforetimecondition.equals("false"))
                                && (valueaftertimecondition.equals("true") || valueaftertimecondition.equals("false"))) {
                            output.setValueBeforeTimeCondition(Boolean.parseBoolean(valuebeforetimecondition));
                            output.setValueAfterTimeCondition(Boolean.parseBoolean(valueaftertimecondition));
                        } else {
                            throw new BusinessModelException("Values of time condition must be true or false");
                        }
                    } else if (output instanceof OutputByte) {
                        //Output<Short> stateoutput = new OutputByte(output);
                        if (Short.parseShort(valuebeforetimecondition) >= 0 && Short.parseShort(valuebeforetimecondition) <= 255) {
                            output.setValueBeforeTimeCondition(Short.parseShort(valuebeforetimecondition));
                        } else {
                            throw new BusinessModelException("Value before timecondition is valid from 0 to 255.");
                        }
                        if (Short.parseShort(valueaftertimecondition) >= 0 && Short.parseShort(valueaftertimecondition) <= 255) {
                            output.setValueAfterTimeCondition(Short.parseShort(valueaftertimecondition));
                        } else {
                            throw new BusinessModelException("Value after timecondition is valid from 0 to 255.");
                        }
                    } else if (output instanceof OutputWord) {
                        if (Integer.parseInt(valuebeforetimecondition) >= 0 && Integer.parseInt(valuebeforetimecondition) <= 65535) {
                            output.setValueBeforeTimeCondition(Integer.parseInt(valuebeforetimecondition));
                        } else {
                            throw new BusinessModelException("Initial value is valid from 0 to 65535.");
                        }
                        if (Integer.parseInt(valueaftertimecondition) >= 0 && Integer.parseInt(valueaftertimecondition) <= 65535) {
                            output.setValueAfterTimeCondition(Integer.parseInt(valueaftertimecondition));
                        } else {
                            throw new BusinessModelException("Initial value is valid from 0 to 65535.");
                        }
                    } else if (output instanceof OutputDWord) {
                        if (Long.parseLong(valuebeforetimecondition) >= 0 && Long.parseLong(valuebeforetimecondition) <= 4294967295L) {
                            output.setValueBeforeTimeCondition(Long.parseLong(valuebeforetimecondition));
                        } else {
                            throw new BusinessModelException("Initial value is valid from 0 to 4294967295.");
                        }
                        if (Long.parseLong(valueaftertimecondition) >= 0 && Long.parseLong(valueaftertimecondition) <= 4294967295L) {
                            output.setValueAfterTimeCondition(Long.parseLong(valueaftertimecondition));
                        } else {
                            throw new BusinessModelException("Initial value is valid from 0 to 4294967295.");
                        }
                    } else if (output instanceof OutputInt) {
                        if (Short.parseShort(valuebeforetimecondition) >= -32768 && Short.parseShort(valuebeforetimecondition) <= 32767) {
                            output.setValueBeforeTimeCondition(Short.parseShort(valuebeforetimecondition));
                        } else {
                            throw new BusinessModelException("Initial value is valid from -32768 to 32767.");
                        }
                        if (Short.parseShort(valueaftertimecondition) >= -32768 && Short.parseShort(valueaftertimecondition) <= 32767) {
                            output.setValueAfterTimeCondition(Short.parseShort(valueaftertimecondition));
                        } else {
                            throw new BusinessModelException("Initial value is valid from -32768 to 32767.");
                        }
                    } else if (output instanceof OutputDInt) {
                        if (Integer.parseInt(valuebeforetimecondition) >= Integer.MIN_VALUE && Integer.parseInt(valuebeforetimecondition) <= Integer.MAX_VALUE) {
                            output.setValueBeforeTimeCondition(Integer.parseInt(valuebeforetimecondition));
                        } else {
                            throw new BusinessModelException("Initial value is valid from -32768 to 32767.");
                        }
                        if (Integer.parseInt(valueaftertimecondition) >= Integer.MIN_VALUE && Integer.parseInt(valueaftertimecondition) <= Integer.MAX_VALUE) {
                            output.setValueAfterTimeCondition(Integer.parseInt(valueaftertimecondition));
                        } else {
                            throw new BusinessModelException("Initial value is valid from " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".");
                        }
                    } else if (output instanceof OutputReal) {
                        float minRealValue = -3.40e38f; // Siemens S7 REAL min value 
                        float maxRealValue = 3.40e38f;  // Siemens S7 REAL max value
                        float smallestNormalizedReal = 1.18e-38f; // Smallest normalized value Siemens
                        float valueBefore = Float.parseFloat(valuebeforetimecondition);
                        float valueAfter = Float.parseFloat(valueaftertimecondition);
                        if ((valueBefore >= minRealValue && valueBefore <= -smallestNormalizedReal)
                                || (valueBefore >= smallestNormalizedReal && valueBefore <= maxRealValue)
                                || (valueBefore == 0.0f)) {
                            output.setValueBeforeTimeCondition(valueBefore);
                        } else {
                            throw new BusinessModelException(
                                    "Value before time condition must be within the range: "
                                    + minRealValue + " to " + -smallestNormalizedReal
                                    + " or " + smallestNormalizedReal + " to " + maxRealValue + "."
                            );
                        }
                        if ((valueAfter >= minRealValue && valueAfter <= -smallestNormalizedReal)
                                || (valueAfter >= smallestNormalizedReal && valueAfter <= maxRealValue)
                                || (valueAfter == 0.0f)) {
                            output.setValueAfterTimeCondition(valueAfter);
                        } else {
                            throw new BusinessModelException(
                                    "Value after time condition must be within the range: "
                                    + minRealValue + " to " + -smallestNormalizedReal
                                    + " or " + smallestNormalizedReal + " to " + maxRealValue + "."
                            );
                        }
                    }
                } catch (NumberFormatException e) {
                    combatplctesterview.getStateView().exceptionErrorView("Number format error: " + e.getMessage());
                } catch (BusinessModelException e) {
                    combatplctesterview.getStateView().exceptionErrorView(e.getMessage());
                } catch (Exception e) {
                    combatplctesterview.getStateView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
                }
                break;
            }
        }
        combatplctesterview.getStateView().viewStateOutputs(state.getOutputs());
    }

    /**
    * Deletes a specific output associated with a given state.
    *
    * Workflow:
    * 1. Retrieves the state using the provided `stateID`.
    * 2. Locates the output to be removed from the state's output list by matching the `outputID`.
    * 3. Removes the identified output from the state's output list.
    * 4. Updates the UI to reflect the modified state outputs.
    *
    * @param stateID  The unique identifier of the state from which the output will be removed.
    * @param outputID The unique identifier of the output to be removed.
    */ 
    public void deleteStateOutput(String stateID, String outputID){
        State state = ModelFacade.getInstance().getStateByID(stateID);
        List<Output> stateoutputs = state.getOutputs();
        Output stateoutput = null;       
         for (Output output : stateoutputs) {
              if (output.getOutputID().equals(outputID)) {
                  stateoutput = output;
                  break;
              }
         }
        stateoutputs.remove(stateoutput);
        combatplctesterview.getStateView().viewStateOutputs(state.getOutputs());
    }
    
    /**
    * Adds a transition to the application and updates the UI accordingly.
    *
    * Workflow:
    * 1. Adds the given transition to the list of transitions managed by the `ModelFacade`.
    * 2. Updates the `SequentialTransitionView` to display the new transition and its associated inputs.
    * 3. Updates the `CombinatorialTransitionView` to display the new transition, the sequential transitions, and their inputs.
    *
    * @param transition The transition object to be added.
    */ 
    public void addTransition(Transition transition) {
        ModelFacade.getInstance().getTransitionGraphElementList().add(transition);
        combatplctesterview.getSequentialTransitionView().viewTransition(transition);
        combatplctesterview.getSequentialTransitionView().viewTransitionInputs(transition.getInputs());
        combatplctesterview.getCombinatorialTransitionView().viewTransition(transition);
        combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitions(ModelFacade.getInstance().getTransitionGraphElementList());
        combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(transition.getInputs());
    }
    
    /**
    * Adds a sequential transition between two states and updates the UI accordingly.
    *
    * Workflow:
    * 1. Validates the input parameters, ensuring `startStateID` and `endStateID` are not empty.
    * 2. Creates a new `SequentialTransition` with the given parameters.
    * 3. Updates the transition count in the `ModelFacade`.
    * 4. Adds the transition to the `TransitionGraphElementList` managed by the `ModelFacade`.
    * 5. Updates the `SequentialTransitionView` and `CombinatorialTransitionView` to reflect the new transition.
    *
    * @param startStateID The ID of the starting state for the transition.
    * @param endStateID The ID of the ending state for the transition.
    * @param startX The X-coordinate of the transition's starting point.
    * @param startY The Y-coordinate of the transition's starting point.
    * @param ctrlX1 The X-coordinate of the first control point for the transition curve.
    * @param ctrlY1 The Y-coordinate of the first control point for the transition curve.
    * @param ctrlX2 The X-coordinate of the second control point for the transition curve.
    * @param ctrlY2 The Y-coordinate of the second control point for the transition curve.
    * @param endX The X-coordinate of the transition's endpoint.
    * @param endY The Y-coordinate of the transition's endpoint.
    * @param graphelementrenderer The renderer responsible for visualizing the transition.
    * @return The newly created `Transition` object, or null if an exception occurred.
    */
    public Transition addSequentialTransition(String startStateID, String endStateID, double startX, double startY,
            double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2,
            double endX, double endY, GraphElementRenderer graphelementrenderer) {
        Transition transition = null;
        try {
            if (startStateID.equals("")) {
                throw new BusinessModelException("Transition startstate is empty.");
            } else if (endStateID.equals("")) {
                throw new BusinessModelException("Transition endstate is empty.");
            }
            transition = new SequentialTransition(startStateID, endStateID, startX, startY,
                    ctrlX1, ctrlY1, ctrlX2, ctrlY2,
                    endX, endY);
            ModelFacade.getInstance().setTransitionObjectCount(Transition.getObjectCount());
            transition.setGraphElementRenderer(graphelementrenderer);
            ModelFacade.getInstance().getTransitionGraphElementList().add(transition);
            combatplctesterview.getSequentialTransitionView().viewTransition(transition);
            combatplctesterview.getSequentialTransitionView().viewTransitionInputs(transition.getInputs());
            combatplctesterview.getCombinatorialTransitionView().viewTransition(transition);
            combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitions(ModelFacade.getInstance().getTransitionGraphElementList());
            combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(transition.getInputs());
        } catch (NumberFormatException e) {
            combatplctesterview.getModelView().exceptionErrorView("Number format error: " + e.getMessage());
        } catch (BusinessModelException e) {
            combatplctesterview.getModelView().exceptionErrorView(e.getMessage());
        } catch (Exception e) {
            combatplctesterview.getModelView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
        }
        return transition;
    }
    
    /**
    * Adds a combinatorial transition between two states and updates the UI accordingly.
    *
    * Workflow:
    * 1. Validates the input parameters, ensuring `startStateID` and `endStateID` are not empty.
    * 2. Creates a new `CombinatorialTransition` with the specified parameters.
    * 3. Updates the transition count in the `ModelFacade`.
    * 4. Adds the transition to the `TransitionGraphElementList` managed by the `ModelFacade`.
    * 5. Updates the `CombinatorialTransitionView` to reflect the new transition.
    *
    * @param startStateID The ID of the starting state for the transition.
    * @param endStateID The ID of the ending state for the transition.
    * @param startX The X-coordinate of the transition's starting point.
    * @param startY The Y-coordinate of the transition's starting point.
    * @param ctrlX1 The X-coordinate of the first control point for the transition curve.
    * @param ctrlY1 The Y-coordinate of the first control point for the transition curve.
    * @param ctrlX2 The X-coordinate of the second control point for the transition curve.
    * @param ctrlY2 The Y-coordinate of the second control point for the transition curve.
    * @param endX The X-coordinate of the transition's endpoint.
    * @param endY The Y-coordinate of the transition's endpoint.
    * @param graphelementrenderer The renderer responsible for visualizing the transition.
    * @return The created `Transition` object, or null if an exception occurred.
    */
    public Transition addCombinatorialTransition(String startStateID, String endStateID, double startX, double startY,
            double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2,
            double endX, double endY, GraphElementRenderer graphelementrenderer) {
        Transition transition = null;
        try {
            if (startStateID.equals("")) {
                throw new BusinessModelException("Transition startstate is empty.");
            } else if (endStateID.equals("")) {
                throw new BusinessModelException("Transition endstate is empty.");
            }
            transition = new CombinatorialTransition(startStateID, endStateID, startX, startY,
                    ctrlX1, ctrlY1, ctrlX2, ctrlY2,
                    endX, endY);
            ModelFacade.getInstance().setTransitionObjectCount(Transition.getObjectCount());
            transition.setGraphElementRenderer(graphelementrenderer);
            ModelFacade.getInstance().getTransitionGraphElementList().add(transition);
            combatplctesterview.getCombinatorialTransitionView().viewTransition(transition);
            combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitions(ModelFacade.getInstance().getTransitionGraphElementList());
            combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(transition.getInputs());
        } catch (NumberFormatException e) {
            combatplctesterview.getModelView().exceptionErrorView("Number format error: " + e.getMessage());
        } catch (BusinessModelException e) {
            combatplctesterview.getModelView().exceptionErrorView(e.getMessage());
        } catch (Exception e) {
            combatplctesterview.getModelView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
        }
        return transition;
    }
    
    /**
    * Handles the UI updates after a transition has been deleted.
    *
    * Workflow:
    * 1. Clears the current transition and transition inputs in both the sequential and combinatorial transition views.
    * 2. Clears sequential transitions associated with combinatorial transitions in the combinatorial transition view.
    * 3. Refreshes the combinatorial transition view with the updated list of transitions from the `ModelFacade`.
    */
    public void handleDeletedTransition() {
        combatplctesterview.getSequentialTransitionView().viewTransition(null);
        combatplctesterview.getSequentialTransitionView().viewTransitionInputs(null);
        combatplctesterview.getCombinatorialTransitionView().viewTransition(null);
        combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(null);
        combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitionsFromCombinatorialTransition(null);
        combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitions(ModelFacade.getInstance().getTransitionGraphElementList());
    }

    /**
    * Handles the selection of a transition and updates the UI accordingly.
    *
    * Workflow:
    * 1. Iterates through all transitions in the `ModelFacade` to find the transition matching the provided `transitionID`.
    * 2. Hides the state view in the UI as transitions are the current focus.
    * 3. Based on the transition type:
    *    - If the transition is a `SequentialTransition`:
    *      - Displays the sequential transition view.
    *      - Hides the combinatorial transition view.
    *      - Updates the sequential transition view with the selected transition and its inputs.
    *    - If the transition is a `CombinatorialTransition`:
    *      - Displays the combinatorial transition view.
    *      - Hides the sequential transition view.
    *      - Updates the combinatorial transition view with the selected transition, its inputs, and the sequential transitions to exclude.
    *
    * @param transitionID The unique identifier of the transition to be handled.
    */
    public void handleSelectedTransition(String transitionID) {
        for (GraphElement transitiongraphelement : ModelFacade.getInstance().getTransitionGraphElementList()) {
            Transition transition = (Transition) transitiongraphelement;
            if (transition.getTransitionID().equals(transitionID)) {
                combatplctesterview.getStateView().setVisible(false);
                if (transition instanceof SequentialTransition) {
                    combatplctesterview.getSequentialTransitionView().setVisible(true);
                    combatplctesterview.getCombinatorialTransitionView().setVisible(false);
                    combatplctesterview.getSequentialTransitionView().viewTransition(transition);
                    combatplctesterview.getSequentialTransitionView().viewTransitionInputs(transition.getInputs());
                } else if (transition instanceof CombinatorialTransition) {
                    CombinatorialTransition combinatorialtransition = (CombinatorialTransition) transition;
                    combatplctesterview.getSequentialTransitionView().setVisible(false);
                    combatplctesterview.getCombinatorialTransitionView().setVisible(true);
                    combatplctesterview.getCombinatorialTransitionView().viewTransition(transition);
                    combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitionsFromCombinatorialTransition(combinatorialtransition.getTransitionsToExlcude());
                    combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(transition.getInputs());
                }
            }
        }
    }
    
    /**
    * Updates the properties of a specific transition and refreshes the corresponding UI components.
    *
    * Workflow:
    * 1. Iterates through the list of transitions in the `ModelFacade` to find the transition matching the provided `transitionID`.
    * 2. Validates the after-time condition:
    *    - If the start state is the same as the end state and `aftertimecondition` is `true`, an exception is thrown as it is not allowed.
    * 3. Updates the transition:
    *    - Sets the new name for the transition.
    *    - For `SequentialTransition`, sets the after-time condition and updates the sequential transition view and inputs.
    *    - For `CombinatorialTransition`, updates the combinatorial transition view and inputs.
    * 4. Refreshes the relevant UI components based on the transition type.
    * @param transitionID The unique identifier of the transition to be updated.
    * @param name The new name for the transition.
    * @param aftertimecondition A boolean value (as a string) indicating the after-time condition for the transition.
    */
    public void updateTransition(String transitionID, String name, String aftertimecondition) {
        for (GraphElement transitiongraphelement : ModelFacade.getInstance().getTransitionGraphElementList()) {
            Transition transition = (Transition) transitiongraphelement;
            if (transition.getTransitionID().equals(transitionID)) {
                try {
                    if (transition.getStartStateID().equals(transition.getEndStateID()) && Boolean.parseBoolean(aftertimecondition)) {
                        throw new BusinessModelException("When the transition start state is equal to the end state, the aftertime condition is not allowed.");
                    }
                } catch (BusinessModelException e) {
                    combatplctesterview.getModelView().exceptionErrorView(e.getMessage());
                    break;
                }
                try {
                    transition.setName(name);
                    if (transition instanceof SequentialTransition) {
                        transition.setAfterTimeCondition(Boolean.parseBoolean(aftertimecondition));
                        combatplctesterview.getSequentialTransitionView().viewTransition(transition);
                        combatplctesterview.getSequentialTransitionView().viewTransitionInputs(transition.getInputs());
                        combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitions(ModelFacade.getInstance().getTransitionGraphElementList());
                    } else if (transition instanceof CombinatorialTransition) {
                        combatplctesterview.getCombinatorialTransitionView().viewTransition(transition);
                        combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(transition.getInputs());
                    }
                } catch (NumberFormatException e) {
                    combatplctesterview.getModelView().exceptionErrorView("Number format error: " + e.getMessage());
                } catch (Exception e) {
                    combatplctesterview.getModelView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
                }
                break;
            }
        }
    }

    /**
    * Deletes a transition identified by its unique ID and refreshes the relevant UI components.
    *
    * Workflow:
    * 1. Searches the list of transitions in `ModelFacade` for the transition matching the given `transitionID`.
    * 2. If a matching transition is found:
    *    - For `SequentialTransition`, removes references from combinatorial transitions using the helper method `deleteSequentialTransitionFromCombinatorialTransitions`.
    *    - Removes the transition from the `TransitionGraphElementList` in `ModelFacade`.
    * 3. Invokes `handleDeletedTransition()` to update the transition-related views and inputs.
    * 4. Repaints the model view to reflect the deletion.
    *
    * @param transitionID The unique identifier of the transition to be deleted.
    */
    public void deleteTransition(String transitionID) {
        Transition transitiontodelete = null;
        for (GraphElement transitiongraphelement : ModelFacade.getInstance().getTransitionGraphElementList()) {
            Transition transition = (Transition) transitiongraphelement;
            if (transition.getTransitionID().equals(transitionID)) {
                transitiontodelete = transition;
                break;
            }
        }
        if (transitiontodelete != null) {
            if (transitiontodelete instanceof SequentialTransition) {
                SequentialTransition sequentialtransition = (SequentialTransition) transitiontodelete;
                deleteSequentialTransitionFromCombinatorialTransitions(sequentialtransition);
            }
            ModelFacade.getInstance().getTransitionGraphElementList().remove(transitiontodelete);
            handleDeletedTransition();
            combatplctesterview.getModelView().repaint();
        }
    }

    /**
    * Removes a sequential transition reference from all combinatorial transitions in the system.
    *
    * Workflow:
    * 1. Iterates through the list of transitions in `ModelFacade`.
    * 2. Identifies transitions that are instances of `CombinatorialTransition`.
    * 3. For each `CombinatorialTransition`, invokes `deleteSequentialTransitionToExclude` to remove the reference
    *    to the given sequential transition.
    *
    * @param sequentialtransition The sequential transition to be removed from the exclusion lists of combinatorial transitions.
    */
    private void deleteSequentialTransitionFromCombinatorialTransitions(SequentialTransition sequentialtransition) {
        for (GraphElement transitiongraphelement : ModelFacade.getInstance().getTransitionGraphElementList()) {
            if (transitiongraphelement instanceof CombinatorialTransition) {
                CombinatorialTransition combinatorialtransition = (CombinatorialTransition) transitiongraphelement;
                combinatorialtransition.deleteSequentialTransitionToExclude(sequentialtransition);
            }
        }
    }

    /**
    * Adds a sequential transition to the exclusion list of a specified combinatorial transition.
    *
    * Workflow:
    * 1. Retrieves the specified combinatorial transition and sequential transition using their IDs from `ModelFacade`.
    * 2. Checks if the sequential transition is already in the exclusion list of the combinatorial transition.
    *    - If yes, throws a `BusinessModelException` with a relevant message.
    *    - If no, adds the sequential transition to the exclusion list using `addTransitionToExclude`.
    * 3. Handles exceptions:
    *    - Displays appropriate error messages in the UI if an exception occurs.
    * 4. Updates the UI to reflect the updated exclusion list for the combinatorial transition.
    *
    * @param combinatorialTransitionID The ID of the combinatorial transition to which the sequential transition will be added.
    * @param sequentialTransitionID The ID of the sequential transition to be added to the exclusion list.
    */ 
    public void addSequentialToCombinatorialTransition(String combinatorialTransitionID, String sequentialTransitionID) {
        CombinatorialTransition combinatorialTransition = (CombinatorialTransition) ModelFacade.getInstance().getTransitionByID(combinatorialTransitionID);

        SequentialTransition sequentialTransition = (SequentialTransition) ModelFacade.getInstance().getTransitionByID(sequentialTransitionID);
        try {
            if (combinatorialTransition.getTransitionsToExlcude().contains(sequentialTransition)) {
                throw new BusinessModelException("Combinatorial Transition already contains this Sequential Transition.");
            } else {
                combinatorialTransition.addTransitionToExclude(sequentialTransition);
            }
        } catch (BusinessModelException e) {
            combatplctesterview.getStateView().exceptionErrorView(e.getMessage());
        } catch (Exception e) {
            combatplctesterview.getStateView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
        }
        combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitionsFromCombinatorialTransition(combinatorialTransition.getTransitionsToExlcude());
    }

    /**
    * Removes a sequential transition from the exclusion list of a specified combinatorial transition.
    *
    * Workflow:
    * 1. Retrieves the specified combinatorial transition and sequential transition using their IDs from `ModelFacade`.
    * 2. Calls `deleteSequentialTransitionToExclude` on the combinatorial transition to remove the sequential transition from its exclusion list.
    * 3. Updates the UI to reflect the updated exclusion list for the combinatorial transition.
    *
    *  @param combinatorialTransitionID The ID of the combinatorial transition from which the sequential transition will be removed.
    * @param sequentialTransitionID The ID of the sequential transition to be removed from the exclusion list.
    */ 
    public void deleteSequentialTransitionFromCombinatorialTransition(String combinatorialTransitionID, String sequentialTransitionID) {
        CombinatorialTransition combinatorialTransition = (CombinatorialTransition) ModelFacade.getInstance().getTransitionByID(combinatorialTransitionID);
        SequentialTransition sequentialTransition = (SequentialTransition) ModelFacade.getInstance().getTransitionByID(sequentialTransitionID);
        combinatorialTransition.deleteSequentialTransitionToExclude(sequentialTransition);
        combatplctesterview.getCombinatorialTransitionView().viewSequentialTransitionsFromCombinatorialTransition(combinatorialTransition.getTransitionsToExlcude());
    }
   
    /**
    * Adds an input to a specified transition with a provided value.
    *
    * Workflow:
    * 1. Retrieves the specified transition and input using their IDs from `ModelFacade`.
    * 2. Checks if the input is already associated with the transition.
    * 3. Validates and assigns the provided value based on the input type:
    *    - For `InputBit`, checks for true/false values.
    *    - For other types (`InputByte`, `InputWord`, etc.), ensures the value falls within the valid range.
    * 4. Adds the input with the assigned value to the transition.
    * 5. Updates the relevant views based on the type of transition (sequential or combinatorial).
    *
    * @param transitionID The ID of the transition to which the input will be added.
    * @param inputID The ID of the input to be added to the transition.
    * @param value The value assigned to the input.
    */ 
    public void addInputToTransition(String transitionID, String inputID, String value) {
        Transition transition = ModelFacade.getInstance().getTransitionByID(transitionID);
        Input input = ModelFacade.getInstance().getInputByID(inputID);
        try {
            for (Input _input : transition.getInputs()) {
                if (_input.getInputID().equals(input.getInputID())) {
                    throw new BusinessModelException("Transition already contains this input.");
                }
            }
            if (input instanceof InputBit) {
                if (value.equals("true") || (value.equals("false"))) {
                    Input<Boolean> transitioninput = new InputBit(input);
                    transitioninput.setValue(Boolean.parseBoolean(value));
                    transition.addInput(transitioninput);
                } else {
                    throw new BusinessModelException("Value of input must be true or false");
                }
            } else if (input instanceof InputByte) {
                Input<Short> transitioninput = new InputByte(input);
                if (Integer.parseInt(value) >= 0 && Integer.parseInt(value) <= 255) {
                    transitioninput.setValue(Short.parseShort(value));
                    transition.addInput(transitioninput);
                } else {
                    throw new BusinessModelException("Value is valid from 0 to 255.");
                }
            } else if (input instanceof InputWord) {
                Input<Integer> transitioninput = new InputWord(input);
                if (Integer.parseInt(value) >= 0 && Integer.parseInt(value) <= 65535) {
                    transitioninput.setValue(Integer.parseInt(value));
                    transition.addInput(transitioninput);
                } else {
                    throw new BusinessModelException("Initial value is valid from 0 to 65535.");
                }
            } else if (input instanceof InputDWord) {
                Input<Long> transitioninput = new InputDWord(input);
                if (Integer.parseInt(value) >= 0 && Integer.parseInt(value) <= 4294967295L) {
                    transitioninput.setValue(Long.parseLong(value));
                    transition.addInput(transitioninput);
                } else {
                    throw new BusinessModelException("Initial value is valid from  0 to 4294967295.");
                }
            } else if (input instanceof InputInt) {
                Input<Short> transitioninput = new InputInt(input);
                if (Integer.parseInt(value) >= -32768 && Integer.parseInt(value) <= 32767) {
                    transitioninput.setValue(Short.parseShort(value));
                    transition.addInput(transitioninput);
                } else {
                    throw new BusinessModelException("Initial value is valid from  -32768 to 32767.");
                }
            } else if (input instanceof InputDInt) {
                Input<Integer> transitioninput = new InputDInt(input);
                if (Integer.parseInt(value) >= Integer.MIN_VALUE && Integer.parseInt(value) <= Integer.MAX_VALUE) {
                    transitioninput.setValue(Integer.parseInt(value));
                    transition.addInput(transitioninput);
                } else {
                    throw new BusinessModelException("Initial value is valid from  " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".");
                }
            } else if (input instanceof InputReal) {
                Input<Float> transitioninput = new InputReal(input);
                float minRealValue = -3.40e38f; // Siemens S7 REAL min value
                float maxRealValue = 3.40e38f;  // Siemens S7 REAL max value
                float smallestNormalizedReal = 1.18e-38f; // Smallest normalized value
                float inputValue = Float.parseFloat(value);
                if ((inputValue >= minRealValue && inputValue <= -smallestNormalizedReal)
                        || (inputValue >= smallestNormalizedReal && inputValue <= maxRealValue)
                        || (inputValue == 0.0f)) {
                    transitioninput.setValue(inputValue);
                    transition.addInput(transitioninput);
                } else {
                    throw new BusinessModelException(
                            "Initial value must be within the range: "
                            + minRealValue + " to " + -smallestNormalizedReal
                            + " or " + smallestNormalizedReal + " to " + maxRealValue + "."
                    );
                }
            }
        } catch (NumberFormatException e) {
            if (transition instanceof SequentialTransition) {
                combatplctesterview.getSequentialTransitionView().exceptionErrorView("Number format error: " + e.getMessage());
            } else if (transition instanceof CombinatorialTransition) {
                combatplctesterview.getCombinatorialTransitionView().exceptionErrorView("Number format error: " + e.getMessage());
            }
        } catch (BusinessModelException e) {
            if (transition instanceof SequentialTransition) {
                combatplctesterview.getSequentialTransitionView().exceptionErrorView(e.getMessage());
            } else if (transition instanceof CombinatorialTransition) {
                combatplctesterview.getCombinatorialTransitionView().exceptionErrorView(e.getMessage());
            }
        } catch (Exception e) {
            if (transition instanceof SequentialTransition) {
                combatplctesterview.getSequentialTransitionView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
            } else if (transition instanceof CombinatorialTransition) {
                combatplctesterview.getCombinatorialTransitionView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
            }
        }
        if (transition instanceof SequentialTransition) {
            combatplctesterview.getSequentialTransitionView().viewTransitionInputs(transition.getInputs());
        } else if (transition instanceof CombinatorialTransition) {
            combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(transition.getInputs());
        }
    }

    /**
    * Updates the value of an input associated with a specific transition.
    *
    * Workflow:
    * 1. Retrieves the specified transition and its list of inputs from `ModelFacade`.
    * 2. Iterates through the inputs of the transition to find the one matching the provided `inputID`.
    * 3. Validates and updates the input's value based on its type:
    *    - For `InputBit`, ensures the value is "true" or "false".
    *    - For other input types (`InputByte`, `InputWord`), checks that the value falls within valid ranges.
    * 4. Updates the associated view to reflect the changes.
    *
    *  * @param transitionID The ID of the transition whose input needs to be updated.
    * @param inputID The ID of the input to be updated.
    * @param value The new value to be assigned to the input.
    */
    public void updateTransitionInput(String transitionID, String inputID, String value) {
        Transition transition = ModelFacade.getInstance().getTransitionByID(transitionID);
        List<Input> transitioninputs = transition.getInputs();
        for (Input input : transitioninputs) {
            if (input.getInputID().equals(inputID)) {
                try {
                    if (input instanceof InputBit) {
                        if (value.equals("true") || (value.equals("false"))) {
                            input.setValue(Boolean.parseBoolean(value));
                        } else {

                            throw new BusinessModelException("Value of input must be true or false");
                        }
                    } else if (input instanceof InputByte) {
                        if (Short.parseShort(value) >= 0 && Short.parseShort(value) <= 255) {
                            input.setValue(Short.parseShort(value));
                        } else {
                            throw new BusinessModelException("Value is valid from 0 to 255.");
                        }
                    } else if (input instanceof InputWord) {
                        if (Integer.parseInt(value) >= 0 && Integer.parseInt(value) <= 65535) {
                            input.setValue(Integer.parseInt(value));
                        } else {
                            throw new BusinessModelException("Initial value is valid from  0 to 65535.");
                        }
                    } else if (input instanceof InputDWord) {
                        if (Long.parseLong(value) >= 0 && Long.parseLong(value) <= 4294967295L) {
                            input.setValue(Long.parseLong(value));
                        } else {
                            throw new BusinessModelException("Initial value is valid from  0 to 4294967295.");
                        }
                    } else if (input instanceof InputInt) {
                        if (Short.parseShort(value) >= -32768 && Short.parseShort(value) <= 32767) {
                            input.setValue(Short.parseShort(value));
                        } else {
                            throw new BusinessModelException("Initial value is valid from -32768 to 32767.");
                        }
                    } else if (input instanceof InputDInt) {
                        if (Integer.parseInt(value) >= Integer.MIN_VALUE && Integer.parseInt(value) <= Integer.MAX_VALUE) {
                            input.setValue(Integer.parseInt(value));
                        } else {
                            throw new BusinessModelException("Initial value is valid from " + Integer.MIN_VALUE + " to " + Integer.MAX_VALUE + ".");
                        }
                    } else if (input instanceof InputReal) {
                        float minRealValue = -3.40e38f; // Siemens S7 REAL min value
                        float maxRealValue = 3.40e38f;  // Siemens S7 REAL max value
                        float smallestNormalizedReal = 1.18e-38f; // Smallest normalized value Siemens
                        float inputValue = Float.parseFloat(value);
                        if ((inputValue >= minRealValue && inputValue <= -smallestNormalizedReal)
                                || (inputValue >= smallestNormalizedReal && inputValue <= maxRealValue)
                                || (inputValue == 0.0f)) {
                            input.setValue(inputValue);
                        } else {
                            throw new BusinessModelException(
                                    "Initial value must be within the range: "
                                    + minRealValue + " to " + -smallestNormalizedReal
                                    + " or " + smallestNormalizedReal + " to " + maxRealValue + "."
                            );
                        }
                    }
                } catch (NumberFormatException e) {
                    if (transition instanceof SequentialTransition) {
                        combatplctesterview.getSequentialTransitionView().exceptionErrorView("Number format error: " + e.getMessage());
                    } else if (transition instanceof CombinatorialTransition) {
                        combatplctesterview.getCombinatorialTransitionView().exceptionErrorView("Number format error: " + e.getMessage());
                    }
                } catch (BusinessModelException e) {
                    if (transition instanceof SequentialTransition) {
                        combatplctesterview.getSequentialTransitionView().exceptionErrorView(e.getMessage());
                    } else if (transition instanceof CombinatorialTransition) {
                        combatplctesterview.getCombinatorialTransitionView().exceptionErrorView(e.getMessage());
                    }
                } catch (Exception e) {
                    if (transition instanceof SequentialTransition) {
                        combatplctesterview.getSequentialTransitionView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
                    } else if (transition instanceof CombinatorialTransition) {
                        combatplctesterview.getCombinatorialTransitionView().exceptionErrorView("An unexpected error occurred: " + e.getMessage());
                    }
                }
            }
        }
        if (transition instanceof SequentialTransition) {
            combatplctesterview.getSequentialTransitionView().viewTransitionInputs(transition.getInputs());
        } else if (transition instanceof CombinatorialTransition) {
            combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(transition.getInputs());
        }
    }
   
    /**
    * Deletes a specific input from a transition.
    *
    * Workflow:
    * 1. Retrieves the transition with the given `transitionID` from `ModelFacade`.
    * 2. Iterates through the inputs associated with the transition to find the one matching the provided `inputID`.
    * 3. Removes the identified input from the list of inputs for the transition.
    * 4. Updates the relevant view to reflect the removal:
    *    - For `SequentialTransition`, updates the `SequentialTransitionView`.
    *    - For `CombinatorialTransition`, updates the `CombinatorialTransitionView`.
    * @param transitionID The ID of the transition from which the input will be removed.
    * @param inputID The ID of the input to be deleted.
    */ 
    public void deleteTransitionInput(String transitionID, String inputID) {
        Transition transition = ModelFacade.getInstance().getTransitionByID(transitionID);
        List<Input> transitioninputs = transition.getInputs();
        Input transitioninput = null;
        for (Input input : transitioninputs) {
            if (input.getInputID().equals(inputID)) {
                transitioninput = input;
            }
        }
        transitioninputs.remove(transitioninput);
        if (transition instanceof SequentialTransition) {
            combatplctesterview.getSequentialTransitionView().viewTransitionInputs(transition.getInputs());
        } else if (transition instanceof CombinatorialTransition) {
            combatplctesterview.getCombinatorialTransitionView().viewTransitionInputs(transition.getInputs());
        }
    }
    
    /**
    * Adds a label to the system with the specified properties.
    *
    * Workflow:
    * 1. Creates a new `Label` instance with the specified text, position, dimensions, and renderer.
    * 2. Sets the graphical renderer for the label to define its appearance.
    * 3. Adds the label to the `LabelGraphElementList` in `ModelFacade` to include it in the system's data structure.
    * 4. Returns the created `Label` object for further usage or reference.
    *
    * @param text The text content of the label.
    * @param x The X-coordinate of the label's position.
    * @param y The Y-coordinate of the label's position.
    * @param width The width of the label.
    * @param height The height of the label.
    * @param graphelementrenderer The renderer used to define how the label is graphically represented.
    * @return The created `Label` object.
    */
    public Label addLabel(String text, double x, double y, double width, double height, GraphElementRenderer graphelementrenderer) {
        Label label = new Label(text, x, y, width, height);
        label.setGraphElementRenderer(graphelementrenderer);
        ModelFacade.getInstance().getLabelGraphElementList().add(label);
        return label;
    }
   
    /**
    * Adds an existing label to the system's data structure.
    *
    * @param label The `Label` object to be added.
    */ 
    public void addLabel(Label label){
        ModelFacade.getInstance().getLabelGraphElementList().add(label);
    }
    
    /**
    * Deletes a label from the system's data structure based on its unique ID.
    *
    * Workflow:
    * 1. Iterates through the `LabelGraphElementList` in the `ModelFacade`.
    * 2. Finds the label matching the provided `labelID`.
    * 3. Removes the identified label from the list.
    * 4. Triggers a repaint of the `ModelView` in the `CombatPlcTesterView` to reflect the removal graphically.
    * 
    * @param labelID The unique identifier of the label to be deleted.
    */
    public void deleteLabel(String labelID) {
        Label labeltodelete = null;
        for (GraphElement labelgraphelement : ModelFacade.getInstance().getLabelGraphElementList()) {
            Label label = (Label) labelgraphelement;
            if (label.getLabelID().equals(labelID)) {
                labeltodelete = label;
                break;
            }
        }
        ModelFacade.getInstance().getLabelGraphElementList().remove(labeltodelete);
        combatplctesterview.getModelView().repaint();
    }
    
    public List<GraphElement> getTransitionGraphElementList() {
        return ModelFacade.getInstance().getTransitionGraphElementList();
    }
    
    public List<GraphElement> getStateGraphElementList() {
        return ModelFacade.getInstance().getStateGraphElementList();
    }
    
    public List<GraphElement> getLabelGraphElementList() {
        return ModelFacade.getInstance().getLabelGraphElementList();
    }
    
    /**
    * Adds a command to the undo stack and clears the redo stack.
    *
    * @param command The command to be added to the undo stack.
    */
    public void addCommand(Command command) {
        undoStack.push(command);
        redoStack.clear();
    }

    /**
    * Undoes the last command in the undo stack and adds it to the redo stack.
    *
    * Workflow:
    * - Checks if the `undoStack` is not empty.
    * - Pops the last command from the `undoStack`.
    * - Executes the `undo()` operation of the popped command.
    * - Pushes the command onto the `redoStack` for potential redo operations.
    * - Triggers a repaint of the `ModelView` to reflect the state change.
    */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
            combatplctesterview.getModelView().repaint();
        }
    }

    /**
    * Redoes the last command in the redo stack and adds it back to the undo stack.
    *
    * Workflow:
    * - Checks if the `redoStack` is not empty.
    * - Pops the last command from the `redoStack`.
    * - Executes the `redo()` operation of the popped command.
    * - Pushes the command back onto the `undoStack` for potential undo operations.
    * - Triggers a repaint of the `ModelView` to reflect the state change.
    */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.redo();
            undoStack.push(command);
            combatplctesterview.getModelView().repaint();
        }
    }
 
    /**
    * Validates that all states involved in transitions are connected either as a start or end state.
    *
    * Workflow:
    * - Collects all unique states from the existing transitions in the `ModelFacade`.
    * - Counts the occurrences of each state as a start state and as an end state within the provided transitions.
    * - Iterates over all unique states and checks if each state has at least one connection as a start or end state.
    *
    * @param transitiongraphelements A list of transition graph elements to validate.
    * @return true if all states are connected as either a start or end state; false otherwise.
    */ 
    public boolean validateStates(List<GraphElement> transitiongraphelements) {
        Set<String> states = new HashSet<>();
        for (GraphElement graphtransition : ModelFacade.getInstance().getTransitionGraphElementList()) {
            Transition transition = (Transition) graphtransition;
            states.add(transition.getStartStateID());
            states.add(transition.getEndStateID());
        }
        Map<String, Integer> startStateCount = new HashMap<>();
        Map<String, Integer> endStateCount = new HashMap<>();
        for (GraphElement graphtransition : transitiongraphelements) {
            Transition transition = (Transition) graphtransition;
            startStateCount.put(transition.getStartStateID(), startStateCount.getOrDefault(transition.getStartStateID(), 0) + 1);
            endStateCount.put(transition.getEndStateID(), endStateCount.getOrDefault(transition.getEndStateID(), 0) + 1);
        }
        for (String state : states) {
            if (startStateCount.getOrDefault(state, 0) == 0 || endStateCount.getOrDefault(state, 0) == 0) {
                return false;
            }
        }
        return true;
    }
     
    /**
    * Waits for a specified amount of time based on the provided cycle time and scaling factor.
    * This method combines sleeping and active waiting to efficiently manage time delays.
    *
    * The total wait time is calculated as: {@code cycletime * cycletimescalingfactor}.
    * The method first uses {@code Thread.sleep()} for long wait durations to reduce CPU usage.
    * For the final milliseconds, it employs active waiting using {@code Thread.yield()} 
    *
    * @param cycletime the base cycle time in milliseconds.
    * @param cycletimescalingfactor the scaling factor applied to the cycle time.
    */
    private void waitForTime(long cycletime, long cycletimescalingfactor) {
        long startTime = System.currentTimeMillis();
        long endTime = startTime + (cycletime * cycletimescalingfactor);
        while (true) {
            long currentTime = System.currentTimeMillis();
            long timeLeft = endTime - currentTime;
            if (timeLeft <= 0) {
                break;
            }
            if (timeLeft > 10) {
                try {
                    Thread.sleep(timeLeft - 2);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } else {

                while (System.currentTimeMillis() < endTime) {
                    Thread.yield();
                }
            }
        }
    }
  
    /**
    * Retrieves the PDU (Protocol Data Unit) length of the connected PLC client.
    * The PDU length represents the maximum size of data that can be exchanged
    * in a single communication cycle between the client and the PLC.
    *
    * @return the PDU length as an integer.
    */
    public int getPDULength(){
      return  ModelFacade.getInstance().getPlcClient().PDULength();
    }
    
    /**
    * Retrieves the current status of the connected PLC as a descriptive string.
    * The method queries the PLC client for the status and converts it into a readable format.
    * If an error occurs during the query, an error message with the corresponding code is returned.
    *
    * @return a string representing the PLC status:
    *         - "PLC is in RUN mode." if the PLC is running.
    *         - "PLC is in STOP mode." if the PLC is stopped.
    *         - "PLC status is unknown." if the status cannot be determined.
    *         - An error message with the error code if there is an issue retrieving the status.
    */
    public String getPlcStatusString() {
        IntByRef statusRef = new IntByRef();
        int errorCode = ModelFacade.getInstance().getPlcClient().GetPlcStatus(statusRef);
        if (errorCode != 0) {
            return "Error retrieving PLC status. Error code: " + errorCode;
        }
        switch (statusRef.Value) {
            case S7.S7CpuStatusRun:
                return "PLC is in RUN mode.";
            case S7.S7CpuStatusStop:
                return "PLC is in STOP mode.";
            case S7.S7CpuStatusUnknown:
            default:
                return "PLC status is unknown.";
        }
    }
    
    /**
    * Executes the testing process for the PLC model by validating business rules, generating test cycles,
    * and sequentially testing states and transitions.
    * 
    * Workflow:
    * 1. **Pre-Test Validation:**
    *    - Updates combinatorial transitions with the current input list.
    *    - Validates required elements:
    *      - Ensures input and output lists are not empty.
    *      - Verifies the presence of transitions and at least two states.
    *      - Confirms all states have at least one incoming and one outgoing transition.
    *      - Checks each state for outputs and valid timer conditions.
    *      - Validates transitions for the presence of inputs and required sequential transitions.
    *    - If any validation fails, the process terminates and an error is displayed.
    * 
    * 2. **PLC Connection:**
    *    - Attempts to connect to the PLC.
    *    - Ensures the PLC is in RUN mode before proceeding.
    *    - If the PLC connection fails, displays an error and exits.
    * 
    * 3. **Test Execution:**
    *    - Generates test cycles from the validated model.
    *    - Iterates through each test path:
    *      - For each state in the path:
    *        - Executes the state test.
    *        - If the test fails, logs the failure and skips the path.
    *      - For each transition in the path:
    *        - Executes the transition test.
    *        - Waits using the cycle time and scaling factor.
    *      - Logs whether the path passed or failed.
    *    - Stops testing if requested by the user.
    * 
    * 4. **Post-Test Actions:**
    *    - Disconnects from the PLC once all paths are tested or the process is stopped.
    *    - Logs detailed results of the test execution.
    * 
    * Multi-threading:
    * - Executes in a separate thread to keep the application responsive during testing.
    */
    public void execute() {
        for (GraphElement graphelement : ModelFacade.getInstance().getTransitionGraphElementList()) {
            if (graphelement instanceof CombinatorialTransition) {
                CombinatorialTransition combinatorialtransition = (CombinatorialTransition) graphelement;
                combinatorialtransition.updateTransitionToExclude(ModelFacade.getInstance().getInputList());
            }
        }
        stoprequested = false;
        try {
            if (ModelFacade.getInstance().getInputList() == null || ModelFacade.getInstance().getInputList().isEmpty()) {
                throw new BusinessModelException("Inputlist cannot be empty.");
            }
            if (ModelFacade.getInstance().getOutputList() == null || ModelFacade.getInstance().getOutputList().isEmpty()) {
                throw new BusinessModelException("Outputlist cannot be empty.");
            }
            if (ModelFacade.getInstance().getTransitionGraphElementList().isEmpty()) {
                throw new BusinessModelException("There are no transitions.");
            }
            if (ModelFacade.getInstance().getStateGraphElementList().size() == 1) {
                throw new BusinessModelException("There must be at least two states.");
            }
            if (!validateStates(ModelFacade.getInstance().getTransitionGraphElementList())) {
                throw new BusinessModelException("The states in your model must have at least one incoming and one outgoing transition.");
            }
            for (GraphElement graphElement : ModelFacade.getInstance().getStateGraphElementList()) {
                State state = (State) graphElement;
                if (state.getOutputs().isEmpty()) {
                    throw new BusinessModelException("State with ID " + state.getStateID() + " does not contain any outputs.");
                } else if ((state.getTimerCondition() != 0 || state.getTimerConditionTolerance() != 0) && (!(state.getTimerConditionTolerance() >= ModelFacade.getInstance().getCycleTime()))) {
                    throw new BusinessModelException("TimerConditionTolerance of State with ID " + state.getStateID() + " must be equal to or greater than the cycle time.");
                } else if ((state.getTimerCondition() != 0 || state.getTimerConditionTolerance() != 0) && !((state.getTimerCondition() - state.getTimerConditionTolerance()) >= (2 * ModelFacade.getInstance().getCycleTime()))) {
                    throw new BusinessModelException("The difference between the TimerCondition value and the TimerConditionTolerance value of State with ID " + state.getStateID() + " must be equal to or greater than two times the cycle time.");
                }
            }
            for (GraphElement graphElement : ModelFacade.getInstance().getTransitionGraphElementList()) {
                if (graphElement instanceof SequentialTransition) {
                    Transition transition = (Transition) graphElement;
                    if (transition.getInputs().isEmpty()) {
                        throw new BusinessModelException("Transition with ID " + transition.getTransitionID() + " does not contain any inputs.");
                    }
                } else if (graphElement instanceof CombinatorialTransition) {
                    CombinatorialTransition transition = (CombinatorialTransition) graphElement;
                    if (transition.getSequentialTransitionsToExclude().isEmpty()) {
                        throw new BusinessModelException("CombinatorialTransition with ID " + transition.getTransitionID() + " does not contain any sequential transition to exlcude.");
                    }
                }
            }
        } catch (BusinessModelException e) {
            combatplctesterview.getTestView().exceptionErrorView(e.getMessage());
            combatplctesterview.setFlow(FlowType.MODEL);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int connectionMessage = ModelFacade.getInstance().ConnectToPlc();
                    if (connectionMessage != 0) {
                        throw new BusinessModelException("PLC ErrorMessage (please try again): " + S7Client.ErrorText(connectionMessage));
                    }
                } catch (BusinessModelException e) {
                    combatplctesterview.getTestView().exceptionErrorView(e.getMessage());
                    return;
                } catch (Exception e) {
                    combatplctesterview.getTestView().exceptionErrorView("Unkwown Error Message.");
                    return;
                }
                testcyles = ModelFacade.getInstance().generateCycles();
                String stateID = "S0";
                String transitionID = "T0";
                String transitionstateID = "";
                boolean statefailed = false;
                boolean pathfailed = false;
                
                // Capture the start and end times for measurement.
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter formatterTest = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                DateTimeFormatter formatterModel = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
                String formattedTime;
                
                LocalDateTime currentDateTime = LocalDateTime.now();
               
                final String title0 = "***************************************************";
                final String title1 = "*********Test on: " + currentDateTime.format(formatterTest) + "**********";
                final String title2 = "***************************************************";
                updateViewTestPaths(title0);
                updateViewTestPaths(title1);
                updateViewTestPaths(title2);
                updateViewTestPaths("-------------------------------------------------------------");
                updateViewTestPaths("PLC Status : " + getPlcStatusString());
                updateViewTestPaths("PDU Lenght : " + getPDULength());
                updateViewTestPaths("-------------------------------------------------------------");
                if (!getPlcStatusString().equals("PLC is in RUN mode.")) {
                    return;
                }
                updateViewTestPaths("Paths to test:");
                for (List<String> path : testcyles) {
                    if (stoprequested) {
                        break;
                    }
                    final String pathString = "path: " + path.toString();
                    updateViewTestPaths(pathString);
                }
                updateViewTestPaths("-------------------------------------------------------------");            
                updateViewTestPaths("");
                for (List<String> path : testcyles) {
                    if (pathfailed) {
                        break;
                    }
                    final String pathString = "test for path: " + path.toString();
                    updateViewTestPaths("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    updateViewTestPaths(pathString);
                    updateViewTestPaths("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    for (int i = 0; i <= path.size() - 1; i = i + 2) {
                        if (pathfailed) {
                            break;
                        }
                        stateID = path.get(i);
                        if (i < path.size() - 1) {
                            transitionID = path.get(i + 1);
                            if (stateID.equals("S0")) {
                                transitionstateID = path.get(path.size() - 2);
                            } else {
                                transitionstateID = path.get(i - 1);
                            }
                        }
                        if (stateID.equals("S0")) {
                            now = LocalDateTime.now();
                            formattedTime = now.format(formatterModel);
                            updateViewTestPaths("-Start Time for reading and comparing outputs : " + formattedTime);
                            statefailed = ModelFacade.getInstance().executeStateTest(ModelFacade.getInstance().getStateByID(stateID), false);
                            now = LocalDateTime.now();
                            formattedTime = now.format(formatterModel);
                            updateViewTestPaths("-End Time for reading and comparing outputs : " + formattedTime);
                        } else {
                            now = LocalDateTime.now();
                            formattedTime = now.format(formatterModel);
                            updateViewTestPaths("-Start Time for reading and comparing outputs : " + formattedTime);
                            statefailed = ModelFacade.getInstance().executeStateTest(ModelFacade.getInstance().getStateByID(stateID), ModelFacade.getInstance().getTransitionByID(transitionstateID).getAfterTimeCondition());
                            now = LocalDateTime.now();
                            formattedTime = now.format(formatterModel);
                            updateViewTestPaths("-End Time for reading and comparing outputs : " + formattedTime);
                        }
                        if (!statefailed) {
                            final String testFailedString = "Test for this path failed at stateID: " + stateID;
                            updateViewTestPaths(testFailedString);
                            pathfailed = true;
                            break;
                        }
                        if (stoprequested) {
                            break;
                        }
                        if (i < path.size() - 1) {
                            now = LocalDateTime.now();
                            formattedTime = now.format(formatterModel);
                            updateViewTestPaths("-Start Time for writing inputs : " + formattedTime);
                            ModelFacade.getInstance().executeTransitionTest(ModelFacade.getInstance().getTransitionByID(transitionID));
                            now = LocalDateTime.now();
                            formattedTime = now.format(formatterModel);
                            updateViewTestPaths("-End Time for writing inputs : " + formattedTime);
                            updateViewTestPaths("-Start wait cycletime * ScalingFactor : " + formattedTime);
                            waitForTime(ModelFacade.getInstance().getCycleTime(), ModelFacade.getInstance().getCycleTimeScalingFactor());
                            now = LocalDateTime.now();
                            formattedTime = now.format(formatterModel);
                            updateViewTestPaths("-End wait cycletime * ScalingFactor : " + formattedTime);
                        }
                    }
                    if (!pathfailed) {
                        final String testPassedString = "Test for this path passed:";
                        updateViewTestPaths(testPassedString);
                    }
                    updateViewTestPaths("-------------------------------------------------------------");
                }
                ModelFacade.getInstance().DisconnectFromPlc();
            }
        }).start();
    }

    /**
    * Updates the test paths view in the user interface.
    * 
    * This method ensures that the test path updates are executed on the Event Dispatch Thread (EDT),
    * which is required for thread-safe operations involving Swing components.
    * 
    * @param text The text to be displayed in the test paths view.
    *             Typically represents log messages or status updates during the test execution process.
    *
    */ 
    private void updateViewTestPaths(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                combatplctesterview.getTestView().viewTestPaths(text);
            }
        });
    }
}
