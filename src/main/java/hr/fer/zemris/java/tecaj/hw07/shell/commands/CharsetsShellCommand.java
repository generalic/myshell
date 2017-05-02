package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;
import java.nio.charset.Charset;

/**
 * Used in {@link MyShell} class for listing names of supported charsets for your Java platform.
 *
 * @author Boris
 * @version 1.0
 */
public class CharsetsShellCommand extends AbstractCommand {

    /**
     * Creates new instance of charsets command.
     */
    public CharsetsShellCommand() {
        super("CHARSETS", "Lists names of supported charsets for your Java platform.");
    }

    @Override
    public ShellStatus execute(Environment e, String args) {

        if (Utility.customSplit(args)[0] != null) {
            throw new IllegalArgumentException(
                "No arguments required for " + super.getCommandName() + " command.");
        }

        Charset.availableCharsets().keySet().forEach(t -> e.writeln(t));

        return ShellStatus.CONTINUE;
    }
}
