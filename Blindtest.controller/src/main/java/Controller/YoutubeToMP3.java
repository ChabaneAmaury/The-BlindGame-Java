/*
 * @author Amaury Chabane
 */
package Controller;

/*
 * @author Amaury Chabane
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class YoutubeToMP3.
 *
 * @author Amaury Chabane
 */
public class YoutubeToMP3 {

    /** The Constant VID_ID_PATTERN. */
    private static final Pattern VID_ID_PATTERN = Pattern.compile("(?<=v\\=|youtu\\.be\\/)\\w+");

    /** The Constant MP3_URL_PATTERN. */
    private static final Pattern MP3_URL_PATTERN = Pattern
            .compile("(?<=href=\\\")https{0,1}\\:\\/\\/([a-zA-Z0-9])+\\.ytapivmp3\\.com.+\\/1(?=\\\")");

    /** The Constant MP3_TITLE_PATTERN. */
    private static final Pattern MP3_TITLE_PATTERN = Pattern
            .compile("(?<=<b>Title\\: <\\/b>)(\\w|\\d|\\s|[()\\-\\_])+(?=<\\/p>)");

    /**
     * Youtube to MP 3.
     *
     * @param youtubeUrl
     *                       the youtube url
     * @return the array list
     * @throws IOException
     *                         Signals that an I/O exception has occurred.
     */
    public static ArrayList<Object> youtubeToMP3(String youtubeUrl) throws IOException {
        String id = getID(youtubeUrl);
        String converter = loadConverter(id);
        String mp3url = getMP3URL(converter);
        String mp3Title = getMP3Title(converter);
        byte[] mp3 = load(mp3url);
        ArrayList<Object> returnedMP3 = new ArrayList<>(2);
        returnedMP3.add(mp3Title);
        returnedMP3.add(mp3);
        return returnedMP3;
    }

    /**
     * Gets the MP3 title.
     *
     * @param html
     *                 the html
     * @return the MP 3 title
     */
    private static String getMP3Title(String html) {
        Matcher m = MP3_TITLE_PATTERN.matcher(html);
        if (!m.find()) {
            System.out.println(html);
            throw new IllegalArgumentException("Invalid Title.");
        }
        return m.group();
    }

    /**
     * Load.
     *
     * @param url
     *                the url
     * @return the byte[]
     * @throws IOException
     *                         Signals that an I/O exception has occurred.
     */
    private static byte[] load(String url) throws IOException {
        URL url2 = new URL(url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream is = url2.openStream();
        byte[] byteChunk = new byte[2500];
        int n;

        while ((n = is.read(byteChunk)) > 0) {
            baos.write(byteChunk, 0, n);
        }

        is.close();
        baos.flush();
        baos.close();

        return baos.toByteArray();
    }

    /**
     * Gets the mp3url.
     *
     * @param html
     *                 the html
     * @return the mp3url
     */
    private static String getMP3URL(String html) {
        Matcher m = MP3_URL_PATTERN.matcher(html);
        if (!m.find()) {
            System.out.println(html);
            throw new IllegalArgumentException("Invalid MP3 URL.");
        }
        return m.group();
    }

    /**
     * Load converter.
     *
     * @param id
     *               the id
     * @return the string
     * @throws IOException
     *                         Signals that an I/O exception has occurred.
     */
    private static String loadConverter(String id) throws IOException {
        String url = "https://www.320youtube.com/watch?v=" + id;
        byte[] bytes = load(url);
        return new String(bytes);
    }

    /**
     * Gets the id.
     *
     * @param youtubeUrl
     *                       the youtube url
     * @return the id
     */
    private static String getID(String youtubeUrl) {
        Matcher m = VID_ID_PATTERN.matcher(youtubeUrl);
        if (!m.find()) {
            throw new IllegalArgumentException("Invalid YouTube URL.");
        }
        return m.group();
    }

    /**
     * Save file.
     *
     * @param FILEPATH
     *                     the filepath
     * @param mp3
     *                     the mp 3
     */
    public static void saveFile(String FILEPATH, byte[] mp3) {
        File file = new File(FILEPATH);
        // Initialize a pointer
        // in file using OutputStream
        OutputStream os;
        try {
            os = new FileOutputStream(file);
            // Starts writing the bytes in it
            os.write(mp3);
            os.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
