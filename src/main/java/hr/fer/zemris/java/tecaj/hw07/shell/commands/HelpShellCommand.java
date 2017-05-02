package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;

/**
 * Class which represents help command in shell.
 * Command is used to list all commands an it's
 * decriptions.
 *
 * @author Boris
 */
public class HelpShellCommand extends AbstractCommand {

    /**
     * Creates new instance of help command.
     */
    public HelpShellCommand() {
        super("HELP", "Lists all commands names and descriptions.");
    }

    @Override
    public ShellStatus execute(Environment e, String args) {

        if (Utility.customSplit(args)[0] != null) {
            throw new IllegalArgumentException(
                "No arguments required for " + super.getCommandName() + " command.");
        }

        e.commands()
            .forEach(t -> e.writeln(t.getCommandName() + " - " + t.getCommandDescription()));

        return ShellStatus.CONTINUE;
    }
}
