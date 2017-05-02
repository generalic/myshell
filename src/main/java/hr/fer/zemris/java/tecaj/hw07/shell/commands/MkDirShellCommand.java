package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Used in {@link MyShell} class for creating directories.
 *
 * @author Boris
 * @version 1.0
 */
public class MkDirShellCommand extends AbstractCommand {

    /**
     * Creates new instance of mkdir command.
     */
    public MkDirShellCommand() {
        super("MKDIR", "mkdir <dir> -> Creates the directory structure <dir>");
    }

    @Override
    public ShellStatus execute(Environment e, String args) {

        String[] arguments = Utility.customSplit(args);

        if (!(arguments[0] != null && arguments[1] == null)) {
            throw new IllegalArgumentException(
                "MKDIR command expects a single argument: directory name.");
        }

        Path dir = Paths.get(Utility.customSplit(args)[0]).toAbsolutePath();

        if (Files.exists(dir)) {
            e.writeln("Directory " + dir + " already exists.");
        } else {
            try {
                Files.createDirectories(dir);
            } catch (IOException e1) {
                e.writeln("Error occurred while creating the directory.");
            }
        }

        return ShellStatus.CONTINUE;
    }
}
