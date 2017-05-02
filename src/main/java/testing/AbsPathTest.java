package testing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AbsPathTest {

    public static void main(String[] args) {

        String path1 = "C:/Java/datoteka.txt";
        String path2 = "C:/Java/bb/a/a";

        Path p1 = Paths.get(path1).normalize().toAbsolutePath();
        Path p2 = Paths.get(path2).normalize().toAbsolutePath();

        System.out.println(p2);
        try {
            Files.createDirectories(p2);
            p2 = Paths.get(p2.toString() + File.separator + p1.getFileName());
            copyFileUsingFileStreams(p1, p2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //		try {
        //			Files.createDirectories(p.getParent());
        //			Files.createFile(p);
        //		} catch (IOException e) {
        //			// TODO Auto-generated catch block
        //			e.printStackTrace();
        //		}

        System.out.println(Files.isDirectory(p1));

        System.out.println(Files.exists(p1));

        System.out.println(Files.isRegularFile(p1));

        System.out.println(p1);
    }

    private static void copyFileUsingFileStreams(Path src, Path dest) throws IOException {

        try (InputStream is = new BufferedInputStream(Files.newInputStream(src));
             OutputStream os = new BufferedOutputStream(Files.newOutputStream(dest))) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) > 0) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();
        }
    }
}
