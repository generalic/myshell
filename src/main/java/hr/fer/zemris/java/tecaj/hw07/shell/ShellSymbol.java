package hr.fer.zemris.java.tecaj.hw07.shell;

/**
 * Used in {@link MyShell} class as symbol representation.
 *
 * @author Boris
 * @version 1.0
 */
public class ShellSymbol {

    /**
     * Used on the start of every shell line that expects prompt.
     */
    private static char prompt = '>';
    /**
     * Used when user wants to input something in more than one line.
     */
    private static char moreLines = '\\';
    /**
     * Used on the start of every shell line that is part of a multilne input.
     */
    private static char multiLine = '|';

    /**
     * Returns the current value that is set on symbol defined by name paramter.
     *
     * @param symbolName which symbol
     * @return symbol
     * @throws IllegalArgumentException if given name does not match any of the
     * supported symbols
     */
    public static char getSymbol(String symbolName) {

        if (symbolName.equals("PROMPT")) {
            return prompt;
        } else if (symbolName.equals("MORELINES")) {
            return moreLines;
        } else if (symbolName.equals("MULTILINE")) {
            return multiLine;
        } else {
            throw new IllegalArgumentException("Symbol " + symbolName + " is not supported.");
        }
    }

    /**
     * Sets symbol defined by given name on given value.
     *
     * @param symbolName name of the symbol
     * @param symbol new value of the symbol
     * @throws IllegalArgumentException if given name does not match any of the
     * supported symbols
     */
    public static void setSymbol(String symbolName, char symbol) {

        if (symbolName.equals("PROMPT")) {
            prompt = symbol;
        } else if (symbolName.equals("MORELINES")) {
            moreLines = symbol;
        } else if (symbolName.equals("MULTILINE")) {
            multiLine = symbol;
        } else {
            throw new IllegalArgumentException("Symbol " + symbolName + " is not supported.");
        }
    }
}
