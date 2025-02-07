// Package
package combat_plc_tester.view;

/**
 * Enum: FlowType
 * 
 * Purpose:
 * Represents the different views in the application, each corresponding to a distinct section of the user interface. 
 * 
 * Enum Values:
 * - `PROPERTIES`: Represents the view for managing and editing properties.
 * - `INPUTS`: Represents the view for configuring and managing input variables.
 * - `OUTPUTS`: Represents the view for configuring and managing output variables.
 * - `MODEL`: Represents the view for managing the model, including states and transitions.
 * - `TEST`: Represents the view for running and analyzing tests on the model.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public enum FlowType {
    PROPERTIES, INPUTS, OUTPUTS, MODEL, TEST
}
