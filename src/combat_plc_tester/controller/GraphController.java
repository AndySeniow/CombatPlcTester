/**
 * This class contains the implementation of graphical user interface (GUI) concepts
 * used in the modeling tool, including rendering of states (as circles), transitions (as lines),
 * snapping to a grid, point manipulation, and interactive visual editing.
 *
 * The design and techniques were developed independently and are based on:
 * - Concepts from the official Oracle Java 2D Graphics documentation [1]
 * - Practical examples and explanations from Liang & Zhang [2] and Haase & Guy [3]
 * - Community-driven discussions from Stack Overflow (tag: java-2d) [4]
 *
 * These sources were consulted for conceptual understanding and inspiration.
 *
 * Features include:
 * - Shape rendering and transformations
 * - Custom painting techniques and layering
 * - Mouse interaction and snapping logic
 * - Grid drawing, selection, and element manipulation
 *
 * References:
 * [1] Oracle. *Trail: 2D Graphics*. Available at: https://docs.oracle.com/javase/tutorial/2d/index.html. Accessed: 2024-05-01.
 * [2] Y. D. Liang and H. Zhang, *Computer Graphics Using Java 2D and 3D*, Pearson Prentice Hall, 2006.
 * [3] C. Haase and R. Guy, *Filthy Rich Clients: Developing Animated and Graphical Effects for Desktop Java Applications*, Addison-Wesley, 2007.
 * [4] Stack Overflow. *Search results for "Java 2D"*. https://stackoverflow.com/questions/tagged/java-2d. Accessed: 2024-05-01.
 */

// Package
package combat_plc_tester.controller;

// Imports
import combat_plc_tester.model.moore.GraphElementRenderer;
import combat_plc_tester.view.LabeledEllipseRenderer;
import combat_plc_tester.view.ModelView;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Class: GraphController
 * 
 * Purpose: 
 * Serves as the central controller in the Model-View-Controller (MVC) pattern, 
 * managing user interactions within the graphical editor and delegating actions 
 * to the appropriate state handlers based on the current mode of the `ModelView`.
 * This class ensures seamless coordination between user input, model updates, and view rendering.
 * 
 * Design Patterns: 
 * - Model-View-Controller (MVC): Acts as the Controller, connecting the user actions 
 *   (View) with the underlying data and logic (Model).
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class GraphController {

    private TestController testcontroller;                      // The test controller.
    private StateHandler currentstate;                          // The current state.
    private StateHandler addstatehandler;                       // The add state handler.
    private StateHandler updatehandler;                         // The update handler.
    private StateHandler addsequentialtransitionhandler;        // The add sequential transition handler.
    private StateHandler addcombinatorialtransitionhandler;     // The add combinatorial transition handler.
    private StateHandler addlabelhandler;                       // The add label handler.
     
    public GraphController(TestController testcontroller){
        this.testcontroller = testcontroller;
        addStartState();
    }
    
    private void addStartState(){
        GraphElementRenderer labeledellipserenderer = new LabeledEllipseRenderer();
        labeledellipserenderer.setShapeColor(Color.DARK_GRAY);
        labeledellipserenderer.setLabeledTextColor(Color.WHITE);
        testcontroller.addState(105, 105, 30, 30, labeledellipserenderer);
    }
   
    public TestController getTestController(){
        return testcontroller;
    }
    
    public void handleSelectedState(String stateID){
       testcontroller.handleSelectedState(stateID);
    }
    
    public void handleSelectedTransition(String transitionID){
       testcontroller.handleSelectedTransition(transitionID);
    }
    
    /**
    * - Handles mouse press events and delegates the action to the appropriate handler based on the current mode.
    * - Initializes the `UpdateHandler` if in SELECTION mode.
    * 
    * @param currentMode the current interaction mode of the ModelView
    * @param e the MouseEvent that triggered the drag action
    */
    public void mousePressed(ModelView.Mode currentMode, MouseEvent e) {
        if (currentMode == ModelView.Mode.SELECTION) {
            if (updatehandler == null) {
                updatehandler = new UpdateDeleteHandler(testcontroller);
            }
            currentstate = updatehandler;
            currentstate.mousePressed(e);
        }
    }

    /**
    * - Handles mouse drag events for graphical elements in SELECTION mode.
    * - Ensures the `UpdateHandler` is initialized before delegating the action.
    * 
    * @param currentMode the current interaction mode of the ModelView
    * @param e the MouseEvent that triggered the drag action
    */
    public void mouseDragged(ModelView.Mode currentMode, MouseEvent e) {
        if (currentMode == ModelView.Mode.SELECTION) {
            if (updatehandler == null) {
                updatehandler = new UpdateDeleteHandler(testcontroller);
            }
            currentstate = updatehandler;
            currentstate.mouseDragged(e);
        }
    }

    /**
    * - Handles mouse movement events, primarily for adding transitions.
    * - Switches between handlers for sequential or combinatorial transitions based on the mode.
    * 
    * @param currentMode the current interaction mode of the ModelView
    * @param e the MouseEvent that triggered the drag action
    */
    public void mouseMoved(ModelView.Mode currentMode, MouseEvent e){      
       if(currentMode == ModelView.Mode.ADD_SEQUENTIAL_TRANSITION){
            if (addsequentialtransitionhandler == null) {
            addsequentialtransitionhandler = new AddSequentialTransitionHandler(testcontroller); 
            }
            currentstate = addsequentialtransitionhandler;
            currentstate.mouseMoved(e);
        }
       else if(currentMode == ModelView.Mode.ADD_COMBINATORIAL_TRANSITION){
            if (addcombinatorialtransitionhandler == null) {
            addcombinatorialtransitionhandler = new AddCombinatorialTransitionHandler(testcontroller); 
            }
            currentstate = addcombinatorialtransitionhandler;
            currentstate.mouseMoved(e);
        }
    }
    
    /**
    * - Handles mouse release events for SELECTION or transition addition modes.
    * - Delegates the event to the respective handler and initializes it if necessary.
    * 
    * @param currentMode the current interaction mode of the ModelView
    * @param e the MouseEvent that triggered the drag action
    */
    public void mouseReleased(ModelView.Mode currentMode, MouseEvent e) {
         if (currentMode == ModelView.Mode.SELECTION) {
            if (updatehandler == null) {
                updatehandler = new UpdateDeleteHandler(testcontroller);
            }
            currentstate = updatehandler;
            currentstate.mouseReleased(e);
        }else if(currentMode == ModelView.Mode.ADD_SEQUENTIAL_TRANSITION){
            if (addsequentialtransitionhandler == null) {
            addsequentialtransitionhandler = new AddSequentialTransitionHandler(testcontroller);
           
            }
            currentstate = addsequentialtransitionhandler;
            currentstate.mouseReleased(e);
        }
         else if(currentMode == ModelView.Mode.ADD_COMBINATORIAL_TRANSITION){
            if (addcombinatorialtransitionhandler == null) {
            addcombinatorialtransitionhandler = new AddCombinatorialTransitionHandler(testcontroller);
     
            }
            currentstate = addcombinatorialtransitionhandler;
            currentstate.mouseReleased(e);
        }
    }
    
    /**
    * - Handles mouse click events for various modes, including adding states, transitions, or labels.
    * - Supports double-click interactions in SELECTION mode.
    * 
    * @param currentMode the current interaction mode of the ModelView
    * @param e the MouseEvent that triggered the drag action
    */
    public void mouseClicked(ModelView.Mode currentMode, MouseEvent e) {
        if (currentMode == ModelView.Mode.ADD_STATE) {
            if (addstatehandler == null) {
                addstatehandler = new AddStateHandler(testcontroller);
            }
            currentstate = addstatehandler;
            currentstate.mouseClicked(e);
        } else if (currentMode == ModelView.Mode.ADD_SEQUENTIAL_TRANSITION) {
            if (addsequentialtransitionhandler == null) {
                addsequentialtransitionhandler = new AddSequentialTransitionHandler(testcontroller);
            }
            currentstate = addsequentialtransitionhandler;
            currentstate.mouseClicked(e);
        } 
        else if (currentMode == ModelView.Mode.ADD_COMBINATORIAL_TRANSITION) {
            if (addcombinatorialtransitionhandler == null) {
                addcombinatorialtransitionhandler = new AddCombinatorialTransitionHandler(testcontroller);
            }
            currentstate = addcombinatorialtransitionhandler;
            currentstate.mouseClicked(e);
        } 
        else if (currentMode == ModelView.Mode.ADD_LABEL) {
            if(addlabelhandler == null){
            addlabelhandler = new AddLabelHandler(testcontroller);
            }
             currentstate = addlabelhandler;
            currentstate.mouseClicked(e);
        } else if(e.getClickCount() == 2 && currentMode == ModelView.Mode.SELECTION){
             if (updatehandler == null) {
                updatehandler = new UpdateDeleteHandler(testcontroller);
            }
            currentstate = updatehandler;
            currentstate.mouseClicked(e);
        }       
    }
    
    /**
    * - Handles key press events in SELECTION mode, delegating actions to the `UpdateDeleteHandler`.
    * 
    * @param currentMode the current interaction mode of the ModelView
    * @param e the MouseEvent that triggered the drag action
    */
    public void keyPressed(ModelView.Mode currentMode, KeyEvent e){
       if(currentMode == ModelView.Mode.SELECTION){
             if (updatehandler == null) {
                updatehandler = new UpdateDeleteHandler(testcontroller);
            }
            currentstate = updatehandler;
            currentstate.keyPressed(e);
        }
    }
    
    /**
    * - Delegates rendering tasks to the appropriate handler based on the current mode.
    * - Ensures that handlers for transitions or SELECTION mode are initialized as needed.
    * 
    * @param currentMode the current interaction mode of the ModelView
    * @param g the Graphics2D object
    */
    public void paintComponent(ModelView.Mode currentMode, Graphics2D g) {
        if (currentMode == ModelView.Mode.ADD_SEQUENTIAL_TRANSITION) {
            if (addsequentialtransitionhandler == null) {
                addsequentialtransitionhandler = new AddSequentialTransitionHandler(testcontroller);
            }
            currentstate = addsequentialtransitionhandler;
            currentstate.paintComponent(g);
        } 
        else if (currentMode == ModelView.Mode.ADD_COMBINATORIAL_TRANSITION) {
            if (addcombinatorialtransitionhandler == null) {
                addcombinatorialtransitionhandler = new AddCombinatorialTransitionHandler(testcontroller);
            }
           currentstate = addcombinatorialtransitionhandler;
            currentstate.paintComponent(g);
        }
        
        else if (currentMode == ModelView.Mode.SELECTION) {
            if (updatehandler == null) {
                updatehandler = new UpdateDeleteHandler(testcontroller);
            }
            currentstate = updatehandler;
            currentstate.paintComponent(g);
        }
    }  
}
