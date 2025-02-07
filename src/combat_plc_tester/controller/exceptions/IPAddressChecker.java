// Package
package combat_plc_tester.controller.exceptions;

/**
 * Class: IPAddressChecker
 *
 * Purpose: 
 * Provides utility methods to validate IP addresses. This class includes 
 * static methods that use regular expressions to check if a given string conforms 
 * to the format of a valid IPv4 address.
 * 
 * @author Seniow Andy
 * @version 1.0
 * @since 13/01/2025
 */
public class IPAddressChecker {

    /**
     * Validates if the given string is a valid IPv4 address.
     *
     * Checks whether the input string conforms to the standard format of an
     * IPv4 address using a regular expression.
     *
     * @param ipaddress The IP address string to validate.
     * @return true if the input string is a valid IPv4 address; false
     * otherwise.
     */
    public static boolean checkIPAddress(String ipaddress) {

        String regex = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
        return ipaddress.matches(regex);
    }
}
