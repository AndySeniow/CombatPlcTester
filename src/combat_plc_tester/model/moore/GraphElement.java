//Package
package combat_plc_tester.model.moore;

//Imports
import java.io.Serializable;

/**
 * Class: GraphElement
 *
 * Purpose: This abstract class serves as the foundation for graph elements such
 * as transitions and states. It encapsulates the shared behavior and delegates
 * the responsibility of graphical representation to a GraphElementRenderer
 * implementation.
 *
 * Design Patterns: - Bridge: Decouples the abstraction (GraphElement) from the
 * implementation of its graphical representation (GraphElementRenderer),
 * enabling flexible and independent extensions for both the logic and the
 * visual rendering.
 *
 * Notes: - This class implements the Serializable interface to enable the
 * storage of fields and objects as a bytestream.
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public abstract class GraphElement implements Serializable {

    private static final long serialVersionUID = 100L; 
    protected GraphElementRenderer renderer;                // Handles the graphical representation of this element, following the Bridge pattern.

    /**
     * Sets the renderer responsible for the graphical representation of this
     * graph element. 
     *
     * @param renderer the GraphElementRenderer implementation to be used for
     * rendering this element.
     */
    public void setGraphElementRenderer(GraphElementRenderer renderer) {
        this.renderer = renderer;
    }
    public abstract GraphElementRenderer getGraph();
}

