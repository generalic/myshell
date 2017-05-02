package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import java.util.List;

/**
 * Every class which implements this interface knows
 * how to execute it self in given {@link Environment}.
 *
 * @author Boris
 * @version 1.0
 */
public interface ShellCommand {

    /**
     * Method which returns command's name.
     *
     * @return command's name.
     */
    String getCommandName();

    /**
     * Method which returns command's description.
     *
     * @return command's description
     */
    List<String> getCommandDescription();

    /**
     * Method used to execute command in given {@link Environment}.
     *
     * @param environment given {@link Environment}.
     * @param text given arguments
     * @return                {@link CommandStatus} enumeration
     */
    ShellStatus execute(Environment e, String args);
}
