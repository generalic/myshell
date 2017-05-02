package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is used to print out the contet of a file, using a
 * specified charset (if none is provided, default is used) in {@link MyShell} class.
 *
 * @author Boris
 * @version 1.0
 */
public class CatShellCommand extends AbstractCommand {

    /**
     * Default capacity for the buffer.
     */
    private static final int DEFAULT_BUFFER_CAPACITY = 4096;

    /**
     * Creates new instance of cat command.
     */
    public CatShellCommand() {
        super(
            "CAT",
            "cat <f1> <optional_charset> -> "
                + "Opens given file and writes its content <f1> <optional_charset>"
        );
    }

    @Override
    public ShellStatus execute(Environment e, String args) {

        String[] arguments = Utility.customSplit(args);
        if (arguments[0] == null) {
            throw new IllegalArgumentException(
                "CAT command expects at least one argument: "
                    + "file path and optional argument is charset."
            );
        }

        Path path = Paths.get(arguments[0]).toAbsolutePath();

        if (!Files.exists(path)) {
            throw new IllegalArgumentException("File " + path.toString()
                + " does not exists.");
        }

        if (!Files.isRegularFile(path)) {
            throw new IllegalArgumentException("File " + path.toString()
                + " is a regular file.");
        }

        Charset charset = Charset.defaultCharset();

        if (arguments[1] != null) {
            try {
                charset = Charset.forName(arguments[1]);
            } catch (IllegalCharsetNameException cn) {
                e.writeln("Invalid charset name given: " + arguments[1]);
            }
        }

        writeFile(e, path, charset);

        return ShellStatus.CONTINUE;
    }

    /**
     * Reads and writes file to the console.
     *
     * @param e environment
     * @param p path to the file
     * @param c charset
     */
    private void writeFile(Environment e, Path path, Charset charset) {

        try (
            BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                    new BufferedInputStream(Files.newInputStream(path)), charset));

            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new BufferedOutputStream(System.out), charset))
        ) {
            char[] buffer = new char[DEFAULT_BUFFER_CAPACITY];
            int charsRead;
            while ((charsRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, charsRead);
            }
            writer.newLine();
            writer.flush();
        } catch (IOException e1) {
            e.writeln("Error while reading and writing the file to the console.");
        }
    }
}
