package testing;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class OverwriteFileTD {

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String path1 = "C:/Java/bb/foto.jpg";
        String path2 = "C:/Java/bb/aa/foto.jpg";

        Path p1 = Paths.get(path1).normalize().toAbsolutePath();
        Path p2 = Paths.get(path2).normalize().toAbsolutePath();

        //		System.out.println(Files.isSameFile(p1, p2));
        if (Files.exists(p2)) {
            while (true) {
                System.out.println("Do you want to overwrite the file? (Y/N)");
                String line = reader.readLine();
                if (line == null) continue;
                line = line.trim().toUpperCase();
                if (line.equals("Y")) {
                    copyFileUsingFileStreams(p1, p2);
                    break;
                } else if (line.equals("N")) {
                    return;
                } else {
                    continue;
                }
            }
        } else {
            copyFileUsingFileStreams(p1, p2);
        }
        try {
            copyFileUsingFileStreams(p1, p2);
        } catch (IOException e) {
            //			 TODO Auto-generated catch block
            e.printStackTrace();
        }

        //		try {
        //			Files.createDirectories(p.getParent());
        //			Files.createFile(p);
        //		} catch (IOException e) {
        //			// TODO Auto-generated catch block
        //			e.printStackTrace();
        //		}

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
