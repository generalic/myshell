package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Used in {@link MyShell} class for copying files.
 *
 * @author Boris
 * @version 1.0
 */
public class CopyShellCommand extends AbstractCommand {

    /**
     * Default capacity for the buffer.
     */
    private static final int DEFAULT_BUFFER_CAPACITY = 4096;

    /**
     * Creates new instance of copy command.
     */
    public CopyShellCommand() {
        super("COPY", "copy <f1> <f2> -> Copies the file <f1> to file <f2>");
    }

    @Override
    public ShellStatus execute(Environment e, String args) {

        String[] paths = Utility.customSplit(args);

        if (paths[0] == null || paths[1] == null) {
            throw new IllegalArgumentException(
                "COPY command expects two arguments: "
                    + "source file name and destination file name."
            );
        }

        Path p1 = Paths.get(paths[0]);
        Path p2 = Paths.get(paths[1]);

        copyFile(e, p1, p2);

        return ShellStatus.CONTINUE;
    }

    /**
     * Copies a file from given location 'arg0' to 'arg1'
     *
     * @param environment environment
     * @param arg0 source
     * @param arg1 destination
     */
    private void copyFile(Environment e, Path p1, Path p2) {

        if (!Files.exists(p1)) {
            throw new IllegalArgumentException("File " + p1 + " doesn't exist.");
        }
        if (!Files.isRegularFile(p1)) {
            throw new IllegalArgumentException("File " + p1
                + " is not an regular file.");
        }

        if (Files.isDirectory(p2)) {
            p2 = Paths.get(p2.toString() + File.separator + p1.getFileName());
        } else if (Files.exists(p2)) {
            try {
                if (Files.isSameFile(p1, p2)) {
                    e.writeln(p1 + " and " + p2 + " are same files.");
                    return;
                }
            } catch (IOException e2) {
                e.writeln("Error while reading source file.");
                return;
            }
            e.writeln("Do you want to overwrite the file? (Y/N)");
            while (true) {
                String line = e.readLine();
                if (line == null) {
                    continue;
                }
                line = line.trim();
                if (line.equals("Y")) {
                    e.writeln("Overwrite will be performed.");
                    break;
                } else if (line.equals("N")) {
                    e.writeln("No copying will be performed.");
                    return;
                } else {
                    continue;
                }
            }
        }

        // copying from p1 to p2
        try {
            copyFileUsingFileStreams(p1, p2);
            e.writeln("Successfully copied " + p1 + " to " + p2);
        } catch (IOException ioe) {
            e.writeln("Error occurred while copying files.");
        }
    }

    /**
     * Used for copying a file (source) to a new file (destination)
     *
     * @param source source file
     * @param dest destination file
     * @throws IOException error with streams
     */
    private void copyFileUsingFileStreams(Path src, Path dest)
        throws IOException {

        try (InputStream is = new BufferedInputStream(Files.newInputStream(src));
             OutputStream os = new BufferedOutputStream(
                 Files.newOutputStream(dest))) {
            byte[] buffer = new byte[DEFAULT_BUFFER_CAPACITY];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        }
    }
}
