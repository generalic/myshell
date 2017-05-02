package hr.fer.zemris.java.tecaj.hw07.shell.commands;

import hr.fer.zemris.java.tecaj.hw07.shell.Environment;
import hr.fer.zemris.java.tecaj.hw07.shell.ShellStatus;
import hr.fer.zemris.java.tecaj.hw07.shell.util.Utility;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * This class is used in the {@link MyShell} class to list out the files contained in the directory.
 * File attributes listed are:
 * <ul>
 * <li>directory/readable/writeable/executable (i.e drw-)</li>
 * <li>size of the file/directory</li>
 * <li>date and time of creation</li>
 * <li>file/directory name</li>
 * </ul>
 *
 * @author Boris
 * @version 1.0
 */
public class LsShellCommand extends AbstractCommand {

    /**
     * Creates new instance of ls command.
     */
    public LsShellCommand() {
        super("LS", "ls <dir> -> Writes a directory listing <dir>");
    }

    @Override
    public ShellStatus execute(Environment e, String args) {

        String[] arguments = Utility.customSplit(args);

        if (!(arguments[0] != null && arguments[1] == null)) {
            throw new IllegalArgumentException(
                "LS command expects a single argument: directory name."
            );
        }

        Path dir = Paths.get(Utility.customSplit(args)[0]).toAbsolutePath();

        if (!Files.exists(dir)) {
            e.writeln("Directory " + dir + " does not exists.");
        } else if (!Files.isDirectory(dir)) {
            e.writeln(dir + " is not an directory.");
        } else {
            try {
                Files.newDirectoryStream(dir).forEach(f -> e.writeln(getFileDetails(f)));
            } catch (IOException e1) {
                e.writeln("Error while listing the directory.");
            }
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Returns string which contains: <br>
     * <ul>
     * <li>directory/readable/writeable/executable (i.e drw-)</li>
     * <li>size of the file/directory</li>
     * <li>date and time of creation</li>
     * <li>file/directory name</li>
     * </ul>
     *
     * @param path given path of the file/directory
     * @return details about file/directory
     */
    private static String getFileDetails(Path path) {
        return String.format(
            "%s %10d %s %s",
            getFileAtributes(path),
            path.toFile().length(),
            getFileDateCreation(path),
            path.getFileName()
        );
    }

    /**
     * Returns information if given path is:<br>
     * directory/readable/writeable/executable.<br>
     * (i.e drw-)
     *
     * @param path given path of the file/directory
     * @return atributes
     */
    private static String getFileAtributes(Path path) {
        StringBuilder sb = new StringBuilder();
        sb = Files.isDirectory(path) ? sb.append("d") : sb.append("-");
        sb = Files.isReadable(path) ? sb.append("r") : sb.append("-");
        sb = Files.isWritable(path) ? sb.append("w") : sb.append("-");
        sb = Files.isExecutable(path) ? sb.append("x") : sb.append("-");
        return sb.toString();
    }

    /**
     * Returns string which contains date and time of the file creation.
     *
     * @param path given path of the file/directory
     * @return date and time of the file creation
     */
    private static String getFileDateCreation(Path path) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BasicFileAttributeView faView = Files.getFileAttributeView(
            path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
        );
        BasicFileAttributes attributes;
        try {
            attributes = faView.readAttributes();
        } catch (IOException e) {
            return "Creation date and time of the file not available.";
        }
        FileTime fileTime = attributes.creationTime();
        return sdf.format(new Date(fileTime.toMillis()));
    }
}
