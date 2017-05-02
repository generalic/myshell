package testing;

import java.util.Arrays;

public class SplitTD {

    public static void main(String[] args) {

        String path = "\"C:/Documents and \\\"Settings/Users/javko\"";
        //		String path = "bok";
        //		String path = "bracek \"bracek\"";

        Arrays.asList(customSplit(path)).forEach(t -> System.out.println(t));
    }

    public static String[] customSplit(String line) {

        if (line == null) return null;

        String[] text = new String[2];
        int index = 0;

        int qm = 0;
        if (line.indexOf('\"') == 0) {
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                char prev = (i != 0) ? line.charAt(i - 1) : '0';
                if (c == '\"' && prev != '\\') qm++;
                if (qm == 2) break;
                index++;
            }
            text[0] = line.substring(1, index);
            if (index + 1 < line.length()) {
                text[1] = line.substring(index + 2);
                if (text[1].indexOf('\"') == 0) {
                    text[1] = text[1].substring(1, text[1].length() - 1);
                }
            }
            return text;
        }

        for (char c : line.toCharArray()) {
            if (c == ' ') break;
            index++;
        }
        text[0] = line.substring(0, index);
        if (index + 1 > line.length()) {
            text[1] = null;
        } else {
            text[1] = line.substring(index + 1);
            if (text[1].indexOf('\"') == 0) {
                text[1] = text[1].substring(1, text[1].length() - 1);
            }
        }
        return text;
    }
}
