/*
 * @author Amaury Chabane
 */
package View;

/*
 *
 */
import java.awt.FontMetrics;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class StringUtils.
 *
 * @author Amaury Chabane
 */
public class StringUtils {
    
    /**
     * Wrap.
     *
     * @param str the str
     * @param fm the fm
     * @param maxWidth the max width
     * @return the list
     */
    public static List<String> wrap(String str, FontMetrics fm, int maxWidth) {
        List<String> lines = splitIntoLines(str);
        if (lines.size() == 0) {
            return lines;
        }

        ArrayList<String> strings = new ArrayList<>();
        for (String string : lines) {
            wrapLineInto(string, strings, fm, maxWidth);
        }
        return strings;
    }

    /**
     * Wrap line into.
     *
     * @param line the line
     * @param list the list
     * @param fm the fm
     * @param maxWidth the max width
     */
    public static void wrapLineInto(String line, List<String> list, FontMetrics fm, int maxWidth) {
        int len = line.length();
        int width;
        while ((len > 0) && ((width = fm.stringWidth(line)) > maxWidth)) {
            // Guess where to split the line. Look for the next space before
            // or after the guess.
            int guess = (len * maxWidth) / width;
            String before = line.substring(0, guess).trim();

            width = fm.stringWidth(before);
            int pos;
            if (width > maxWidth) {
                pos = findBreakBefore(line, guess);
            } else { // Too short or possibly just right
                pos = findBreakAfter(line, guess);
                if (pos != -1) { // Make sure this doesn't make us too long
                    before = line.substring(0, pos).trim();
                    if (fm.stringWidth(before) > maxWidth) {
                        pos = findBreakBefore(line, guess);
                    }
                }
            }
            if (pos == -1) {
                pos = guess; // Split in the middle of the word
            }

            list.add(line.substring(0, pos).trim());
            line = line.substring(pos).trim();
            len = line.length();
        }
        if (len > 0) {
            list.add(line);
        }
    }

    /**
     * Find break before.
     *
     * @param line the line
     * @param start the start
     * @return the int
     */
    public static int findBreakBefore(String line, int start) {
        for (int i = start; i >= 0; --i) {
            char c = line.charAt(i);
            if (Character.isWhitespace(c) || (c == '-')) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Find break after.
     *
     * @param line the line
     * @param start the start
     * @return the int
     */
    public static int findBreakAfter(String line, int start) {
        int len = line.length();
        for (int i = start; i < len; ++i) {
            char c = line.charAt(i);
            if (Character.isWhitespace(c) || (c == '-')) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Split into lines.
     *
     * @param str the str
     * @return the list
     */
    public static List<String> splitIntoLines(String str) {
        ArrayList<String> strings = new ArrayList<>();

        int len = str.length();
        if (len == 0) {
            strings.add("");
            return strings;
        }

        int lineStart = 0;

        for (int i = 0; i < len; ++i) {
            char c = str.charAt(i);
            if (c == '\r') {
                int newlineLength = 1;
                if (((i + 1) < len) && (str.charAt(i + 1) == '\n')) {
                    newlineLength = 2;
                }
                strings.add(str.substring(lineStart, i));
                lineStart = i + newlineLength;
                if (newlineLength == 2) {
                    ++i;
                }
            } else if (c == '\n') {
                strings.add(str.substring(lineStart, i));
                lineStart = i + 1;
            }
        }
        if (lineStart < len) {
            strings.add(str.substring(lineStart));
        }

        return strings;
    }

}