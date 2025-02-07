// Package
package combat_plc_tester.model.moore;

// Imports
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * Class: CycleGeneratorAlgorithm
 *
 * Purpose: Implements the strategy for generating sequential and combinatorial test cycles
 * based on transitions and states derived from the modified Moore model.
 *
 * Design Patterns:
 * - Strategy Pattern: This concrete algorithm implements the `CycleGeneratorStrategy` interface.
 *   It defines the specific logic for generating and categorizing test cycles.
 *
 * Functionality:
 * 1. Generates first-level paths for all transitions in the model.
 * 2. Categorizes paths into:
 *    - Cycles: Paths that start and end with the initial state ("S0").
 *    - Paths starting from "S0" but not returning to it.
 *    - Second-level paths that do not start at "S0".
 * 3. Avoids redundant paths by eliminating excessive loops (double self consecutive loops, triple consecutive loops).
 * 4. Converts combinatorial transitions (IDs starting with "c") into sequential transitions IDs
 *    for ease of processing and testing.
 * 5. Returns a set of test cycles ready for further processing.
 *
 *
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class CycleGeneratorAlgorithm implements CycleGeneratorStrategy{
    // List of state graph elements.
    private List<GraphElement> stategraphelementList;
    // List of transition graph elements.
    private List<GraphElement> transitiongraphelementList;
    // Maximum depth from the start state.
    private int maxdepth = 0;
      
    // Next members are sets of string lists, where each list represents a path.
    // Each path is the result of path concatenation with an overlapping node, 
    // where the last element of the first path matches the first element of the second path.
    // --------------------------------------------------------------------------------------
    // A set of string lists, where each list represents a path, 
    // where the path does not start with the start state "S0".
    private Set<List<String>> intermediatepaths;
    // A set of string lists, where each list represents a path 
    // that starts with the initial state "S0" but does not end with it.
    private Set<List<String>> openpaths;
    // A set of string lists, where each list represents a path, 
    // where the path starts and ends with the start state "S0".
    private Set<List<String>> generatedcyles;
    private Set<List<String>> generatedcylesToTest;
    // Minimum path size ([S1, T1, S2]).
    private static int MIN_PATH_SIZE = 3;
    // Minimum loop size ([S1, T1, S2, T2, ]).
    private static int MIN_LOOP_SIZE = 5;
    // Minimum loop size for an triple loop
    private static int MIN_LOOP_SIZE_FOR_TRIPLE_LOOP = 17;
    // Factor used to calculate the next block size for detecting triple loops larger than the minimum loop size.
    private static int NEXT_LOOP_FACTOR = 4;
    // Factor used to detect triple loops larger than the minimum loop size (`MIN_LOOP_SIZE_FOR_TRIPLE_LOOP`).
    private static int NEXT_LOOP_FACTOR_FOR_TRIPLE_LOOP = 12;
    
    public CycleGeneratorAlgorithm(){ 
      this.intermediatepaths = new HashSet<>();  
      this.openpaths = new HashSet<>();
      this.generatedcyles = new HashSet<>(); 
      this.generatedcylesToTest = new HashSet<>();
    }

    /**
     * Generates and categorize first paths by processing all transitions in the model.
     *
     * Creates paths from each transition and extends them by
     * concatenating overlapping transitions. Categorizes the resulting paths
     * into:
     * - First-level paths (`firstpaths`): Paths that do not start at "S0".
     * - Cycles (`generatedcycles`): Paths that start and end with the initial state "S0". 
     * - Paths starting from the initial state (`generatedpaths`): Paths that start at "S0" but do not return to it.  
     *
     * Examples thas sets can contain: 
     * - `firstlevelpaths`: [S1, T2, S2, T3, S3] ...
     * - `generatedpaths`: [S0, T1, S1, T2, S2] ...
     * - `generatedcyles`: [S0, T1, S1, T2, S0] ..
     * 
     */
    public void generateAndCategorizeFirstPaths() {
        // Generate a first level paths from each transition and add it to the intermediatepaths set.
        for (int i = 0; i < transitiongraphelementList.size(); i++) {
            // If the transition path starts and ends with "S0", then it is a cycle and should be added to the set of generated cycles.
            Transition startTransition = (Transition) transitiongraphelementList.get(i);
             if(startTransition.getStartStateID().equals("S0")&&startTransition.getEndStateID().equals("S0"))
                {
                List<String> newPath = Arrays.asList(
                            startTransition.getStartStateID(),
                            startTransition.getTransitionID(),
                            startTransition.getEndStateID()
                    );
                generatedcyles.add(newPath);
                continue; 
                }
            // Generate first level paths.
            // Perform path concatenation with overlapping node if the last element of the first path
            // matches the first element of the second path.
            for (int j = 0; j < transitiongraphelementList.size(); j++) {
                if (i == j) {
                    continue; // Avoid equal transition.
                }
                Transition nextTransition = (Transition) transitiongraphelementList.get(j);
                // Check if the end state of the first transition matches the start state of the next transition.
                if (startTransition.getEndStateID().equals(nextTransition.getStartStateID())) {
                    // Add a new path by concatenating the path of the start transition with the path of the next transition.
                    List<String> newPath = Arrays.asList(
                            startTransition.getStartStateID(),
                            startTransition.getTransitionID(),
                            startTransition.getEndStateID(),
                            nextTransition.getTransitionID(),
                            nextTransition.getEndStateID()
                    );
                    // If the path starts and ends with the initial state "S0", add it to the generatedcycles set.
                    if (startTransition.getStartStateID().equals("S0") && nextTransition.getEndStateID().equals("S0")) {
                        generatedcyles.add(newPath);
                    // Else if only the path starts with the initial state "S0", add it to the openpaths set.
                    } else if (startTransition.getStartStateID().equals("S0")) {
                        openpaths.add(newPath);
                    // Else add the path to the intermediatepaths set.
                    } else {
                        intermediatepaths.add(newPath);
                    }
                }
            }
        }
    }
    
    /**
     * Finds the longest path starting from a given state.
     *
     * This method initializes a depth-first search (DFS) to traverse the graph
     * and calculate the maximum depth of transitions starting from the
     * specified state ID.
     *
     * @param startID String - The ID of the starting state for the path search.
     * @return int - The maximum depth of transitions from the given starting state.
     */
    public int findLongestPath(String startID) {
        Set<String> visited = new HashSet<>();
        depthFirstSearch(startID, visited, 0);
        return maxdepth;
    }
    
    /**
     * Performs a depth-first search (DFS) on the graph to calculate the maximum
     * depth.
     *
     * The implementation follows the principles described in:
     * Goodrich, M. T., Tamassia, R., & Goldwasser, M. H. (2015). 
     * Data Structures and Algorithms in Java (6th ed.). John Wiley & Sons. 
     * ISBN: 978-1-118-80857-3.
     * 
     * - Traverses the graph starting from the given state (`currentID`) to
     * determine the maximum depth of transitions. - Uses a recursive approach
     * to explore all possible paths, keeping track of visited states to avoid
     * infinite loops.
     *
     * Parameters:
     *
     * @param currentID The ID of the current state being visited.
     * @param visited A set of state IDs that have already been visited during
     * this recursive call to prevent revisiting.
     * @param currentdepth The current depth of the traversal.
     *
     * Method Details: 1. Adds the current state ID to the `visited` set to mark
     * it as visited. 2. Updates `maxdepth` by comparing the current depth with
     * the previously recorded maximum depth. 3. Iterates through all graph
     * elements in `transitiongraphelementList`: - If a transition starts at the
     * `currentID` and leads to an unvisited state, recursively calls
     * `depthFirstSearch` for that next state. 4. Removes the `currentID` from
     * the `visited` set before returning (backtracking) to allow other paths to
     * be explored.
     * 
     * @see <a href="https://en.wikipedia.org/wiki/Depth-first_search">Wikipedia: Depth-First Search</a>
     * @see <a href="https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/">GeeksforGeeks: Depth First Search</a>
     */
    private void depthFirstSearch(String currentID, Set<String> visited, int currentdepth) {
        visited.add(currentID);
        maxdepth = Math.max(maxdepth, currentdepth);
        for (GraphElement graphelement : transitiongraphelementList) {
            Transition transition = (Transition) graphelement;
            if (transition.getStartStateID().equals(currentID) && !visited.contains(transition.getEndStateID())) {
                depthFirstSearch(transition.getEndStateID(), visited, currentdepth + 1);
            }
        }
        visited.remove(currentID); 
    }

    /**
    * Generates and categorizes next-level paths based on transitions, while avoiding redundant loops.
    *
    * This method processes the existing set of generated paths and combines them with 
    * next-level paths to create new paths. It categorizes these new paths into:
    * 1. **Cycles (`newInitialCycles`)** - Paths that start and end with the initial state "S0".
    * 2. **Paths (`newInitialPaths`)** - Paths that start with "S0" but do not form a cycle.
    *
    * The method uses recursion to process paths up to a specified maximum depth + 1 (`maxdepth`).
    *
    * @param maxdepth int - The maximum depth for recursively generating and categorizing paths.

 Logic:
 1. **Iteration through existing paths:**
    - Combines each path (`iteratorpath`) with every second-level path (`firstlevelpath`) 
      if the end of `iteratorpath` matches the start of `firstlevelpath`.
    - Avoids redundant loops by calling `checkLoops` on the combined path.

 2. **Cycle Detection:**
    - Extracts cycles using `extractTestCycleFromPath`, focusing on paths that start and end with "S0".

 3. **Categorization:**
    - If a path forms a cycle, it is added to `newInitialCycles`.
    - Otherwise, if it starts with "S0", it is added to `newInitialPaths`.

 4. **Updating Generated Sets:**
    - Adds the new cycles and paths to the respective sets (`generatedcycles` and `openpaths`).

 5. **Recursion:**
    - Decreases `maxdepth + 1` by 1 and recursively calls the method to process further levels.

 Notes:
 - Removes processed paths from `openpaths` to avoid re-processing.
 - Ensures no redundant loops are included in the generated paths.
    */
    public void generateAndCategorizeNextPaths(int maxdepth) {  
        Set<List<String>> newInitialCycles = new HashSet<>();
        Set<List<String>> newInitialPaths = new HashSet<>();
        Iterator<List<String>> iterator = openpaths.iterator();
        while (iterator.hasNext()) {
            List<String> iteratorpath = iterator.next();
            for (List<String> firstlevelpath : intermediatepaths) {
                if (iteratorpath.get(iteratorpath.size() - 1).equals(firstlevelpath.get(0))) {
                    List<String> combinedPath = new ArrayList<>(iteratorpath);
                    combinedPath.addAll(firstlevelpath.subList(1, firstlevelpath.size()));
                    if (!checkLoops(combinedPath)) {
                        List<String> cycle = extractTestCycleFromPath(combinedPath,"S0");
                        if (cycle != null) {
                            newInitialCycles.add(cycle);
                        } else if (combinedPath.get(0).equals("S0")) {
                            newInitialPaths.add(combinedPath);
                        }
                    }
                }
            }
            iterator.remove();
        }
        generatedcyles.addAll(newInitialCycles);
        openpaths.addAll(newInitialPaths);
        if (maxdepth != 0) {
            generateAndCategorizeNextPaths(maxdepth - 1);
        } 
    }
    
    /**
     * Extracts a test cycle from the given path, starting and ending with the
     * specified state.
     *
     * This method identifies and returns a cycle of the path, 
     * starting and ending at the specified `stateID`. If no such cycle
     * exists, the method returns `null`.
     *
     * @param path List<String> - The path from which the cycle is to be
     * extracted.
     * @param stateID String - The ID of the state where the cycle should start
     * and end.
     * @return List<String> - A list representing the extracted cycle if found,
     * otherwise null.
     */
    public List<String> extractTestCycleFromPath(List<String> path, String stateID) {
        if (path == null || path.isEmpty() || !path.get(0).equals(stateID)) {
            return null;
        }
        List<String> cycle = new ArrayList<>();
        for (String element : path) {
            cycle.add(element);
            if (cycle.size() > 1 && element.equals(stateID)) {
                return cycle;
            }
        }
        return null;
    }
    
    /**
     * Prevents multiple occurrences of self-loops within a sequence, such as
     * [S1, T1, S1, T1, S1]. Example: The sequence [S1, T1, S1] must not repeat
     * consecutively within the list.
     *
     * @param list List<String> - The list containing nodes and transitions to
     * check for repeating patterns.
     * @param blocksize int - The number of consecutive elements (nodes and
     * transitions) to compare as a block. Example: For a blocksize of 3, the
     * sequence [S1, T1, S1] will be compared with the subsequent [S1, T1, S1]
     * to detect repetition.
     * @return boolean - Returns true if a repeating self-loop pattern is
     * detected, otherwise returns false.
     */
    public boolean compareFirstBlockWithNext(List<String> list, int blocksize) {
        // Check list size 
        if (list.size() < blocksize * 2 - 2) {
            return false;
        }
        // Iterate over the path using the specified blocksize (3 in this case).
        // Each iteration compares two consecutive blocks of the given blocksize.
        // The next block starts at the end node of the previous block.
        for (int i = 0; i <= list.size() - (blocksize * 2) + 2; i += 2) {
            List<String> firstBlock = list.subList(i, i + blocksize);
            List<String> secondBlock = list.subList(i + blocksize - 1, i + (blocksize * 2) - 1);
            if (firstBlock.equals(secondBlock)) {
                return true;
            }
        }
        return false;
    }
 
    /**
     * Checks for repeating loops within a list, avoiding loops that occur more
     * than twice consecutively.
     *
     * Purpose: - Identifies if a specific block of elements
     * (defined by `blockSize`) repeats itself three times consecutively within
     * the list.
     *
     * Logic: 1. The list is divided into three consecutive blocks: -
     * `firstBlock`: The initial block to compare. - `secondBlock`: The block
     * immediately following `firstBlock`, starting from the overlapping
     * element. - `thirdBlock`: The block immediately following `secondBlock`,
     * again starting from the overlapping element. 2. The method checks if
     * `firstBlock` is equal to both `secondBlock` and `thirdBlock`. 3. If all
     * three blocks are identical, the method returns `true`, indicating a
     * repeating loop.
     *
     * Parameters:
     *
     * @param list List<String> - The list of elements (nodes and transitions)
     * to check for repeating patterns.
     * @param blocksize int - The size of the block (number of consecutive
     * elements) to compare.
     *
     * Edge Case: - If the size of the list is insufficient to form three blocks
     * of the given size, the method returns `false`.
     *
     * Example: The path [S0, T0, S1, T1, S2, T2, S1, T1, S2, T2, S1, T1, S2, T2, S1, ...] 
     * contains a repetition of 3 identical loops: [S1, T1, S2, T2, S1].
     * This is considered a redundant path because it is only necessary to generate a loop at most twice.
     * @return boolean - Returns true if the list contains a block that repeats
     * itself three times consecutively; otherwise, false.
     */
    public boolean compareFirstBlockWithNextTwo(List<String> list, int blocksize) {
        // Check list size 
        if (list.size() < blocksize * 3 - 2) {
            return false;
        }
        // Iterate over the path, growing the blocksize with each iteration.
        // Each iteration compares three consecutive blocks of increasing size.
        for (int i = 0; i <= list.size() - (blocksize * 3) + 2; i += 2) {
            List<String> firstblock = list.subList(i, i + blocksize);
            List<String> secondblock = list.subList(i + blocksize - 1, i + (blocksize * 2) - 1);
            List<String> thirdblock = list.subList(i + (blocksize * 2) - 2, i + (blocksize * 3) - 2);
            // Compare the three blocks for equality.
            if (firstblock.equals(secondblock) && firstblock.equals(thirdblock)) {
                return true;
            }
        }
        return false;
    }
  
    /**
     * Checks for invalid loops in a given path.
     *
     * Detects specific types of loops in a path and determines if
     * any invalid loop patterns exist. It performs two types of checks:
     *
     * 1. **Self-loops:** - Checks if a block of elements repeats immediately
     * after itself ([S1, T1, S1, T1, S1]).
     *
     * 2. **Triple consecutive loops:** - Calculates the block size dynamically
     * based on the path length and checks if exactly three consecutive
     * identical loops exist. These are considered invalid.
     *
     * @param list List<String> - The list representing the path to be checked.
     * @return boolean - Returns true if an invalid loop pattern is found,
     * otherwise false.
     *
     * Logic: - **Self-loop Check:** - Calls `compareFirstBlockWithNext` with a
     * fixed block size of 3 to check for immediate self-loops. - If such a loop
     * is found, the method returns `true`.
     *
     * - **Triple Loop Check:** - Dynamically calculates the block size using
     * the formula: `block size = MIN_LOOP_SIZE + NEXT_LOOP_FACTOR * result`,
     * where `result` is based on the path size and constants. - Calls
     * `compareFirstBlockWithNextTwo` to verify if exactly three consecutive
     * loops exist. - Returns `true` if this pattern is detected.
     */
    public boolean checkLoops(List<String> list) {
        // Check for self loops example.
        if (compareFirstBlockWithNext(list, MIN_PATH_SIZE)) {
            return true;
        } // Calculates the block size to compare.
        // Checks if there are exactly three consecutive loops, which are not allowed (maximum of two is permitted).
        else {
            int result = (list.size() - MIN_LOOP_SIZE_FOR_TRIPLE_LOOP) / NEXT_LOOP_FACTOR_FOR_TRIPLE_LOOP;
            return (compareFirstBlockWithNextTwo(list, (MIN_LOOP_SIZE + NEXT_LOOP_FACTOR * result)));
        }
    }

    /**
 * Converts combinatorial transitions in the given path into sequential transitions.
 *
 * Processes a path containing transition IDs and replaces transitions 
 * of type `CombinatorialTransition` (with IDs starting with "c") by their corresponding 
 * sequential transitions. The resulting path includes the sequential transitions 
 * and their associated state.
 *
 * @param path List<String> - The path containing transition IDs to be processed.
 * @return List<String> - The updated path where combinatorial transitions are replaced 
 *                        by their sequential counterparts.
 *
 * Logic:
 * 1. Creates a copy of the input path to store the transformed result.
 * 2. Iterates through the path to identify combinatorial transitions:
 *    - Checks if a transition ID starts with "c".
 *    - If found, retrieves the corresponding `CombinatorialTransition` from 
 *      `transitiongraphelementList` and obtains its sequential transitions using 
 *      `getSequentialTransitionsToTest()`.
 * 3. Replaces the combinatorial transition in the path:
 *    - Removes the combinatorial transition ID.
 *    - Adds sequential transition IDs and their end states (if applicable) in its place.
 * 4. Returns the updated path.
 *
 */
    public List<String> convertCombinatorialTransition(List<String> path) {
        List<String> result = new ArrayList<>(path);
        ListIterator<String> iterator = result.listIterator();
        List<SequentialTransition> sequentialTransitionToTest = new ArrayList<>();
        while (iterator.hasNext()) {
            String current = iterator.next();
            for (GraphElement graphElement : transitiongraphelementList) {
                if (graphElement instanceof CombinatorialTransition) {
                    CombinatorialTransition combinatorialTransition = (CombinatorialTransition) graphElement;
                    if (combinatorialTransition.getTransitionID().equals(current)) {
                        sequentialTransitionToTest = combinatorialTransition.getSequentialTransitionsToTest();
                    }
                }
            }
            if (current.startsWith("c")) {
                iterator.remove();
                List<String> newItems = new ArrayList<>();
                for (int i = 0; i < sequentialTransitionToTest.size(); i++) {
                    if (i == sequentialTransitionToTest.size() - 1) {
                        newItems = List.of(sequentialTransitionToTest.get(i).getTransitionID());
                    } else {
                        newItems = List.of(
                                sequentialTransitionToTest.get(i).getTransitionID(),
                                sequentialTransitionToTest.get(i).getEndStateID()
                        );
                    }
                    for (String newItem : newItems) {
                        iterator.add(newItem);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Processes generated cycles by converting combinatorial transitions into
     * sequential transitions.
     *
     * Clears the current list of cycles to test (`generatedcylesToTest`) and
     * processes all generated cycles (`generatedcyles`). Each cycle is
     * transformed by replacing combinatorial transitions with their
     * corresponding sequential transitions. The updated cycles are then added
     * to `generatedcylesToTest` for further testing or processing.
     *
     */
    public void processCycles() {
        generatedcylesToTest.clear();     
        for (List<String> path : generatedcyles) {
            List<String> convertedPath = convertCombinatorialTransition(path);
            generatedcylesToTest.add(convertedPath);
        }
    } 

    // Strategy for generating sequential and combinatorial test cycles.
    @Override
    public Set<List<String>> generateCycles(List<GraphElement> stategraphelementList, List<GraphElement> transitiongraphelementList) {
        this.intermediatepaths.clear();
        this.openpaths.clear();
        this.generatedcyles.clear();
        this.generatedcylesToTest.clear();
        this.stategraphelementList = stategraphelementList;
        this.transitiongraphelementList = transitiongraphelementList;
        generateAndCategorizeFirstPaths();
        generateAndCategorizeNextPaths(findLongestPath("S0"));
        processCycles();
        return generatedcylesToTest;
    }
}
    
    
    
    
    
    
    
