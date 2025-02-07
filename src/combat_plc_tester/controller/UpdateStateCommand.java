// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElement;
import combat_plc_tester.model.moore.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class: UpdateStateCommand
 * 
 * Purpose:
 * Implements the `Command` interface to handle the update of a state and its connected transitions 
 * within a graphical testing environment.
 *
 * Design Patterns:
 * - **Command Pattern:**
 *   - Encapsulates state and transition updates as commands, enabling execution, undo, and redo functionality.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class UpdateStateCommand implements Command{

    private TestController testcontroller;                                      // The test controller.
    private GraphElement graphstate;                                            // The graph state.
    private List<GraphElement> selectedgraphtransitions = new ArrayList<>();    // All selected transitions (when the state is selected)
    private double firststateX;                                                 // The X-coordinate of the state before undo.
    private double firststateY;                                                 // The Y-coordinate of the state before undo.
    private double secondstateX;                                                // The X-coordinate of the state after undo.
    private double secondstateY;                                                // The Y-coordinate of the state after undo.
    private Stack<Command> undoStack = new Stack<>();                           // The (local) undo stack for the selected transitions.
    private Stack<Command> redoStack = new Stack<>();                           // The (local) redo stack for the selected transitions.
    
    public UpdateStateCommand(TestController testcontroller, GraphElement graphstate, List<GraphElement> selectedgraphtransitions) {
        this.testcontroller = testcontroller;
        this.graphstate = graphstate;
        for (GraphElement graphtransition : selectedgraphtransitions) {
            this.selectedgraphtransitions.add(graphtransition);
        }
    }

    @Override
    public void execute() {
        State state = (State) graphstate;
        this.firststateX = state.getX();
        this.firststateY = state.getY();
        if (!selectedgraphtransitions.isEmpty()) {
            for (GraphElement graphtransition : selectedgraphtransitions) {
                Command updatetransitioncommand = new UpdateTransitionCommand(testcontroller, graphtransition);
                updatetransitioncommand.execute();
                addCommand(updatetransitioncommand);
            }
        }
    }

    // When undoing the update of the state, all transitions connected to this state must also revert their updates.
    @Override
    public void undo() {
        State state = (State) graphstate;
        this.secondstateX = state.getX();
        this.secondstateY = state.getY();
        state.setX(firststateX);
        state.setY(firststateY);
        for (GraphElement graphtransition : selectedgraphtransitions) {
            undoupdatetransition();
        }
    }

    // When redoing the update of the state, all transitions connected to this state must also reapply their updates.
    @Override
    public void redo() {
        State state = (State) graphstate;
        state.setX(secondstateX);
        state.setY(secondstateY);
        for (GraphElement graphtransition : selectedgraphtransitions) {
            redoupdatetransition();
        }
    }

    public void addCommand(Command command) {
        undoStack.push(command);
        redoStack.clear();
    }

    public void undoupdatetransition() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public void redoupdatetransition() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.redo();
            undoStack.push(command);
        }
    }
}
