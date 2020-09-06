/*
 * @author Amaury Chabane
 */
package Controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;

import Contract.IResultQueryObject;

public class TMDbAPIQuery {

    public class QueryResultObject implements IResultQueryObject {

        private String poster_url = null;

        private String title = null;

        private String date = null;

        @Override
        public String getPoster_url() {
            return this.poster_url;
        }

        @Override
        public void setPoster_url(String poster_url) {
            this.poster_url = poster_url;
        }

        @Override
        public String getTitle() {
            return this.title;
        }

        @Override
        public void setTitle(String title) {
            this.title = title;
        }

        @Override
        public String getDate() {
            return this.date;
        }

        @Override
        public void setDate(String date) {
            this.date = date;
        }

    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
}
