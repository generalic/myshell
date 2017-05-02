package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Used in {@link MyShell} class to create a hex dump a file and
 * to write it to the output stream.
 *
 * @author Boris
 * @version 1.0
 */
public class HexDumpShellCommand extends AbstractCommand {

    private static final int BYTES_LENGTH = 16;

    /**
     * Creates new instance of hexdump command.
     */
    public HexDumpShellCommand() {
        super("HEXDUMP", "hexdump <f> -> Prints hex-dump for given file <f>");
    }

    @Override
    public ShellStatus execute(Environment e, String args) {

        String[] arguments = Utility.customSplit(args);

        if (!(arguments[0] != null && arguments[1] == null)) {
            throw new IllegalArgumentException(
                "HEXDUMP command expects a single argument: path to the file.");
        }

        Path path = Paths.get(arguments[0]);

        if (!Files.exists(path)) {
            throw new IllegalArgumentException("Invalid path to the file: " + path);
        }

        printHexDum(e, path);

        return ShellStatus.CONTINUE;
    }

    /**
     * Prints hex-dump for given file.
     *
     * @param e environment
     * @param path path to the file
     */
    private void printHexDum(Environment e, Path path) {
        try (
            InputStream is = new BufferedInputStream(Files.newInputStream(path));
            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(
                    new BufferedOutputStream(System.out)))

        ) {

            byte[] buffer = new byte[BYTES_LENGTH];
            StringBuilder sb = new StringBuilder();
            int countPrinting = 0;
            int bytesRead;

            while ((bytesRead = is.read(buffer)) > 0) {
                sb.append(String.format("%08x: ", countPrinting));
                sb.append(byteToHex(buffer, bytesRead));
                sb.append(hexLetters(buffer, bytesRead));
                countPrinting += BYTES_LENGTH;

                writer.write(sb.toString());
                writer.newLine();
                writer.flush();
                sb.setLength(0);
            }
        } catch (IOException e1) {
            e.writeln("Error while reading the file.");
        }
    }

    /**
     * Returns a string representation of the byte array.
     * Bytes that aren't in <code>[32,127]</code> interval are
     * represented as a <code>'.'<code>.
     *
     * @param bytes bytes to represent
     * @param len how many of them are there
     * @return string representation
     */
    private static String hexLetters(byte[] bytes, int len) {

        StringBuffer buff = new StringBuffer();

        for (int i = 0; i < len; i++) {
            int value = bytes[i] & 0xff;
            if (value < 32 || value > 127) {
                buff.append('.');
            } else {
                buff.append((char) value);
            }
        }

        return buff.toString();
    }

    /**
     * Returns string representation of a byte array.
     *
     * @param bytes bytes to represent
     * @param len how many bytes are there
     * @return string representation
     */
    private static String byteToHex(byte[] bytes, int len) {
        StringBuffer buff = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            if (i < len) {
                if ((bytes[i] & 0xff) < 0x10) {
                    buff.append('0');
                }
                buff.append(Integer.toHexString(bytes[i] & 0xff).toUpperCase());
            } else {
                buff.append(" ");
            }

            if (i == bytes.length / 2 - 1) {
                buff.append("|");
            } else {
                buff.append(" ");
            }
        }
        return buff.append("| ").toString();
    }
}
