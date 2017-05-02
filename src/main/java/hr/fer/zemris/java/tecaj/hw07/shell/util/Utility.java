package hr.fer.zemris.java.tecaj.hw07.shell.util;

/**
 * Class which contains static methods used for mainpulation
 * through shell commands.
 *
 * @author Boris
 * @version 1.0
 */

public final class Utility {

    /**
     * Don't let anyone instantiate this class.
     */
    private Utility() {
    }

    /**
     * Custom split method which splits words with <code>" "</code>
     * between them into two separate strings and returns array
     * of strings.
     *
     * @param line given string of words
     * @return string array of separated words
     */
    public static String[] customSplit(String line) {

        String[] text = new String[2];

        if (line == null) return text;
        line = line.trim();
        if (line.isEmpty()) return text;

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

    /**
     * Returns <code>true</code> if given string is empty or null,
     * <code>false</code> otherwise.
     *
     * @param args string which is required to check
     * @return            <code>true</code> if given string is empty or null,
     * <code>false</code> otherwise
     */
    public static boolean emptyArgs(String args) {
        return args == null || args.trim().isEmpty();
    }
}
