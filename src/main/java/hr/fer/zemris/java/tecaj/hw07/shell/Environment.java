package hr.fer.zemris.java.tecaj.hw07.shell;

import hr.fer.zemris.java.tecaj.hw07.shell.commands.ShellCommand;

/**
 * Environment for shell's runtime.
 *
 * @author Boris
 * @version 1.0
 */
public interface Environment {

    /**
     * Reads user input.
     *
     * @return string
     */
    String readLine();

    /**
     * Writes given text on display.
     *
     * @param text given string
     */
    void write(String text);

    /**
     * The same as write but it puts <code>\n</code> at
     * the end.
     *
     * @param text given string
     */
    void writeln(String text);

    /**
     * Method which returns iterable object of available
     * commands.
     *
     * @return iterable object
     */
    Iterable<? extends ShellCommand> commands();

    /**
     * Method returns symbol which is assigned to allow the command that it
     * can span across multiple lines in shell.
     *
     * @return multiple lines symbol
     */
    Character getMultilineSymbol();

    /**
     * Sets multiple lines symbol to given value.
     *
     * @param symbol new value for multiple lines symbol
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Method returns symbol which is represents prompt symbol in shell.
     *
     * @return prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Sets prompt symbol to given value.
     *
     * @param symbol new value for prompt symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * Returns the current value that is set on more lines symbol.
     *
     * @return value that is set on more lines symbol
     */
    Character getMorelinesSymbol();

    /**
     * Replaces the current value that is set on more lines symbol to new one.
     *
     * @param new value for more lines symbol
     */
    void setMorelinesSymbol(Character symbol);
}
