package testing;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class FileAtributesTD {

    public static void main(final String[] args) throws IOException {

        final Path path = Paths.get("C:/Intel/Logs");

        final DirectoryStream<Path> dirStream = Files.newDirectoryStream(path);
        dirStream.forEach(t -> System.out.println(t.getFileName()));

        //		Files.walk(path).forEach(t -> System.out.println(t));
        //
        //		path.forEach(t -> System.out.println(t));

        final String atributes = getFileAtributes(path);

        final long length = getFileLength(path);

        final String formttedDateTime = getFileDateCreation(path);

        final String fileName = getFileName(path);

        final String output =
            String.format("%s %10d %s %s", atributes, length, formttedDateTime, fileName);

        System.out.println(output);
    }

    private static void printDirectoryListing(final Path dir) throws IOException {
        Files.newDirectoryStream(dir).forEach(f -> System.out.println(getFileDetails(f)));
    }

    private static String getFileDetails(final Path path) {
        return String.format(
            "%s %10d %s %s",
            getFileAtributes(path),
            path.toFile().length(),
            getFileDateCreation(path),
            path.getFileName()
        );
    }

    private static String getFileAtributes(final Path path) {
        final StringBuilder sb = new StringBuilder();
        sb.append(Files.isDirectory(path) ? "d" : "-");
        sb.append(Files.isReadable(path) ? "r" : "-");
        sb.append(Files.isWritable(path) ? "w" : "-");
        sb.append(Files.isExecutable(path) ? "x" : "-");
        return sb.toString();
    }

    private static String getFileDateCreation(final Path path) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final BasicFileAttributeView faView = Files.getFileAttributeView(
            path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
        );
        BasicFileAttributes attributes;
        try {
            attributes = faView.readAttributes();
        } catch (final IOException e) {
            return "Creation date and time of the file not available.";
        }
        final FileTime fileTime = attributes.creationTime();
        return sdf.format(new Date(fileTime.toMillis()));
    }

    private static long getFileLength(final Path path) {
        return path.toFile().length();
    }

    private static String getFileName(final Path path) {
        return path.getFileName().toString();
    }
}
