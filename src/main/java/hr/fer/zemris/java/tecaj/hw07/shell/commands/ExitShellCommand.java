package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;

/**
 * Used in {@link MyShell} class to shutdown the shell.
 *
 * @author Boris
 * @version 1.0
 */
public class ExitShellCommand extends AbstractCommand {

    /**
     * Creates new instance of exit command.
     */
    public ExitShellCommand() {
        super("EXIT", "Exits from shell.");
    }

    @Override
    public ShellStatus execute(final Environment environment, final String args) {

        if (Utility.customSplit(args)[0] != null) {
            throw new IllegalArgumentException(
                "No arguments required for " + super.getCommandName() + " command.");
        }
        return ShellStatus.EXIT;
    }
}
