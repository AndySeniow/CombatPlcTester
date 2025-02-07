// Package
package combat_plc_tester.model.moore;

// Imports
import combat_plc_tester.model.IO.Input;
import combat_plc_tester.model.IO.InputBit;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class: CombinatorialTransition
 *
 * Purpose: Represents a combinatorial transition in the modified Moore-machine.
 * - A combinatorial transition generates all possible combinations of bit-type 
 *   inputs and represents them as sequential transitions.
 * - It allows the exclusion of specific sequential transitions in the model 
 *   and supports non-bit-type inputs.
 * - This enables the automated generation of all combinatorial bit-type input 
 *   possibilities in the form of sequential transitions, streamlining the 
 *   testing and modeling process.
 * - Using a combinatorial transition for timing purposes is not possible.
 * 
 * Notes: 
 * - This class implements the Serializable interface to enable the storage of 
 *   fields and objects as a bytestream.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/
*/
public class CombinatorialTransition extends Transition implements Serializable{
    
    private static final long serialVersionUID = 122L;
    // List of all inputs.
    private List<Input> allinputs;
    // List of all bit-type input combinations.
    private List<List<Input>> allInputBitCombinations;
    // List of all generated sequential transitions based on all combinatorial possibilities of the bit-type inputs.
    private List<SequentialTransition> allSequentialTransitions;
    // List of transitions to test: allSequentialTransitions minus transitions to exclude.
    private List<SequentialTransition> transitionsToTest;
    // List of sequential transitions in the model to exclude.
    private List<SequentialTransition> transitionsToExclude;


    /**
     * Constructor: CombinatorialTransition
     *
     * Initializes a new CombinatorialTransition object, representing a
     * combinatorial transition in the modified Moore-machine. 
     *
     * @param startStateID The ID of the state where the transition starts.
     * @param endStateID The ID of the state where the transition ends.
     * @param startX The x-coordinate of the starting point of the transition.
     * @param startY The y-coordinate of the starting point of the transition.
     * @param ctrlX1 The x-coordinate of the first control point for the path.
     * @param ctrlY1 The y-coordinate of the first control point for the path.
     * @param ctrlX2 The x-coordinate of the second control point for the path.
     * @param ctrlY2 The y-coordinate of the second control point for the path.
     * @param endX The x-coordinate of the ending point of the transition.
     * @param endY The y-coordinate of the ending point of the transition.
     */
    public CombinatorialTransition(String startStateID, String endStateID, double startX, double startY, 
                      double ctrlX1, double ctrlY1, double ctrlX2, double ctrlY2, 
                      double endX, double endY)
    {
        super(startStateID,endStateID,"cT",startX,startY,ctrlX1,ctrlY1,ctrlX2,ctrlY2,endX,endY);
        allSequentialTransitions = new ArrayList<>();
        transitionsToTest = new ArrayList<>();
        transitionsToExclude = new ArrayList<>();
        allInputBitCombinations = new ArrayList<>(); 
        setAfterTimeCondition(false);               // No timing purpose possible.
    }
   
    /**
     * Adds a sequential transition to the list of transitions to exclude.
     *
     * @param excludeTransition The sequential transition to be excluded.
     */
    public void addTransitionToExclude(SequentialTransition excludeTransition) {
        transitionsToExclude.add(excludeTransition);
    }

    public List<SequentialTransition> getTransitionsToExlcude() {
        return transitionsToExclude;
    }

    /**
     * Deletes a sequential transition to the list of transitions to exclude.
     *
     * @param sequentialTransition The sequential transition to delete.
     */
    public void deleteSequentialTransitionToExclude(SequentialTransition sequentialTransition) {
        if (transitionsToExclude.contains(sequentialTransition)) {
            transitionsToExclude.remove(sequentialTransition);
        }
    }

    public List<SequentialTransition> getSequentialTransitionsToExclude() {

        return this.transitionsToExclude;
    }

    public List<SequentialTransition> getSequentialTransitionsToTest() {
        return this.transitionsToTest;
    }
    
     /**
     * Adds an input to the current transition and updates all sequential
     * transitions to include the new input if they do not already contain it.
     *
     * @param input The input to be added to the transition.
     */
    @Override
    public void addInput(Input input) {
        this.getInputs().add(input);
        if (!allSequentialTransitions.isEmpty()) {
            for (SequentialTransition sequentialtransition : allSequentialTransitions) {
                if (!sequentialtransition.getInputs().contains(input)) {
                    sequentialtransition.addInput(input);
                }
            }
        }
    }

    /**
     * Deletes an input from the sequential transitions.
     *
     * @param input The input to be added to the transition.
     */
    @Override
    public void deleteInput(Input input) {
        this.getInputs().remove(input);
        if (!allSequentialTransitions.isEmpty()) {
            for (SequentialTransition sequentialtransition : allSequentialTransitions) {
                sequentialtransition.deleteInput(input);
            }
        }
    }

    // Generates all combinatorial bit-type input combinations.
    private void generateAllInputBitCombinations() {
        allInputBitCombinations.clear();
        List<Input> bitinputs = new ArrayList<>();
        for (Input input : allinputs) {
            if (input instanceof InputBit) {
                bitinputs.add(input);
            }
        }
        int n = bitinputs.size();
        int combinations = 1 << n;

        for (int i = 0; i < combinations; i++) {
            List<Input> combination = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                boolean bitValue = ((i >> j) & 1) == 1;
                Input<Boolean> input = new InputBit(bitinputs.get(j));
                input.setValue(bitValue);
                combination.add(input);
            }
            allInputBitCombinations.add(combination);
        }
    }
    
    // Generates all sequential transitions based on the combinatorial bit-type combinations.
    private void generateSequentialTransitions() {
        for (List<Input> inputList : allInputBitCombinations) {
            SequentialTransition sequentialTransition = new SequentialTransition(this.getStartStateID(), this.getEndStateID());
            for (Input input : inputList) {
                sequentialTransition.addInput(input);
            }
            for (Input input : this.getInputs()) {
                if (!sequentialTransition.getInputs().contains(input)) {
                    sequentialTransition.addInput(input);
                }
            }
            allSequentialTransitions.add(sequentialTransition);
        }
    }
      
    /**
     * Updates the list of transitions to exclude based on the provided inputs.
     *
     * @param inputs The list of inputs used to update the transitions to
     * exclude.
     */
    public void updateTransitionToExclude(List<Input> inputs) {
        this.allinputs = inputs;
        allSequentialTransitions.clear();
        transitionsToTest.clear();
        generateAllInputBitCombinations();
        generateSequentialTransitions();
        filterTransitions();
    }

    /**
     * Filters sequential transitions to determine which transitions should be
     * tested.
     *
     * Notes: - Ensures that only transitions not explicitly excluded are included 
     * in the testing process, based on the equality of their inputs.
     */
    public void filterTransitions() {
        boolean exclude = false;
        for (SequentialTransition sequentialtransition : allSequentialTransitions) {
            exclude = false;
            for (SequentialTransition sequentialtransitionToExclude : transitionsToExclude) {
                // If the inputs of the transitions are equal, exclude the sequential transition.
                // It is important to maintain the order of the parameters, as the sequential 
                // transition only contains inputs configured by the model.
                // All inputs in the input list must be present in the generated sequential transition.
                if (areInputsEqual(sequentialtransition.getInputs(), sequentialtransitionToExclude.getInputs())) {
                    exclude = true;
                    break;
                }
            }
            // If not excluded, add the sequential transition to the list of transitions to test.
            if (!exclude) {
                transitionsToTest.add(sequentialtransition);
            }
        }
    }
 
    /**
     * Compares two lists of inputs to determine if they are logically equal.
     *
     * Purpose: This method iterates through two lists of inputs and checks if
     * every `InputBit` in the first list (`inputs2`) has a matching `InputBit`
     * in the second list (`inputs1`). A match is defined as having the same
     * `InputID` and `value`.
     * 
     * @param inputs1 The first list of inputs to compare.
     * @param inputs2 The second list of inputs to compare.
     * @return true if all `InputBit` objects in `inputs2` have a corresponding
     * match in `inputs1`, otherwise false.
     */
    private boolean areInputsEqual(List<Input> inputs1, List<Input> inputs2) {
        for (Input input1 : inputs2) {
            if (input1 instanceof InputBit) {
                boolean isequal = false;
                for (Input input2 : inputs1) {
                    if (input2 instanceof InputBit
                            && input1.getInputID().equals(input2.getInputID())
                            && input1.getValue() == input2.getValue()) {
                        isequal = true;
                        break;
                    }
                }
                if (!isequal) {
                    return false;
                }
            }
        }
        return true;
    }
}
