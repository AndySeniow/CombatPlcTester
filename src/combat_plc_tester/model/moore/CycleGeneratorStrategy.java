// Package
package combat_plc_tester.model.moore;

// Imports
import java.util.List;
import java.util.Set;

/**
 * Interface: CycleGeneratorStrategy
 *
 * Purpose: Defines the contract for generating cycles in a modified Moore model.
 *
 * Design Pattern:
 * - Strategy Pattern: This interface serves as the abstraction for various algorithms 
 *   that generate test cycles. Concrete implementations define specific strategies 
 *   for generating and categorizing sequential and combinatorial test cycles.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public interface CycleGeneratorStrategy {
    
     /**
     * Generates categorized test cycles based on the input graph elements.
     *
     * @param stategraphelementList List<GraphElement> - A list of state graph elements.
     * @param transitiongraphelementList List<GraphElement> - A list of transition graph elements.
     * @return Set<List<String>> - A set of categorized test cycles.
     */
    Set<List<String>> generateCycles(List<GraphElement> stategraphelementList, List<GraphElement> transitiongraphelementList);
}
