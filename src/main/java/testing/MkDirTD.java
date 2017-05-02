package testing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MkDirTD {

    public static void main(String[] args) {

        String path = "C:/Java/noviDir/josJedanDir/jos/gotovo/";

        Path p = Paths.get(path);
        if (!Files.isDirectory(p)) {
            throw new IllegalArgumentException("Given path does not represent a directory.");
        }

        try {
            Files.createDirectories(p);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("jesam");
    }
}
