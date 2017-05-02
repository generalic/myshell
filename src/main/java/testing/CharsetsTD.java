package testing;

import java.nio.charset.Charset;

public class CharsetsTD {

    public static void main(String[] args) {

        String line = null;

        Charset.availableCharsets().forEach((a, b) -> System.out.println(a));
    }
}
