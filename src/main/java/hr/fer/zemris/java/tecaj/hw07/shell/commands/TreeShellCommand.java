package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.MyShell;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * Used in the {@link MyShell} class to print a tree like formation of the directory structure.
 *
 * @author Boris
 * @version 1.0
 */
public class TreeShellCommand extends AbstractCommand {

    /**
     * Creates new instance of tree command.
     */
    public TreeShellCommand() {
        super("TREE", "tree <dir> -> Generates prints a tree from given <dir>");
    }

    @Override
    public ShellStatus execute(final Environment e, final String args) {

        final String[] arguments = Utility.customSplit(args);

        if (!(arguments[0] != null && arguments[1] == null)) {
            throw new IllegalArgumentException(
                "TREE command expects a single argument: directory name."
            );
        }

        final Path dir = Paths.get(Utility.customSplit(args)[0])
            .toAbsolutePath();

        if (!Files.exists(dir)) {
            e.writeln("Directory " + dir + " does not exists.");
        } else if (!Files.isDirectory(dir)) {
            e.writeln(dir + " is not an directory.");
        } else {
            final FileVisitor<Path> fv = new FileVisitor<Path>() {

                private int indentLevel;

                @Override
                public FileVisitResult preVisitDirectory(final Path dir,
                    final BasicFileAttributes attrs) throws IOException {
                    if (indentLevel == 0) {
                        e.writeln(dir.toAbsolutePath().toString());
                    } else {
                        printFileName(e, indentLevel, dir);
                    }
                    indentLevel += 2;
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(final Path file,
                    final BasicFileAttributes attrs) throws IOException {
                    printFileName(e, indentLevel, file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(final Path file,
                    final IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(final Path dir,
                    final IOException exc) throws IOException {
                    indentLevel -= 2;
                    return FileVisitResult.CONTINUE;
                }
            };

            try {
                Files.walkFileTree(dir, fv);
            } catch (final IOException e1) {
                e.writeln("Error while going through directories.");
            }
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Prints file name.
     *
     * @param e environment
     * @param indentLevel indentation
     * @param file file
     */
    private static void printFileName(final Environment e, final int indentLevel, final Path file) {
        for (int i = 0; i < indentLevel; i++) {
            e.write(" ");
        }
        e.writeln(file.getFileName().toString());
    }
}
