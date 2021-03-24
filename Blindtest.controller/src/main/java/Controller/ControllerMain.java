/*
 * @author Amaury Chabane
 */
package Controller;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import javax.imageio.ImageIO;

import Contract.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The Class ControllerMain.
 *
 * @author Amaury Chabane
 */
@SuppressWarnings("deprecation")
public class ControllerMain extends Observable implements IControllerMain {

    /** The view. */
    private IView view;

    /** The model. */
    private IModel model;

    /** The allowed time. */
    private static int allowedTime = 20;

    /** The time left. */
    private int timeLeft = ControllerMain.allowedTime;

    /** The theme. */
    private IEntity theme = null;

    /** The theme index. */
    private int themeIndex = 0;

    /** The not choosen types. */
    private ArrayList<String> notChoosenTypes = new ArrayList<>();

    /** The tmp list. */
    private ArrayList<IEntity> tmpList = new ArrayList<>();

    /** The timer. */
    private Thread timer = null;

    private Map<String, String> releaseMap = new HashMap<>();

    /**
     * Instantiates a new controller main.
     *
     * @param view
     *                  the view
     * @param model
     *                  the model
     */
    public ControllerMain(IView view, IModel model) {
        this.setModel(model);
        this.setView(view);

        this.releaseMap.put("Movie", "release_date");
        this.releaseMap.put("TV Show", "first_air_date");
    }

    @Override
    public void downloadYtVideoToMP3(String filepath, String youtubeUrl) {
        try {
            ArrayList<Object> mp3_raw = YoutubeToMP3.youtubeToMP3(youtubeUrl);
            YoutubeToMP3.saveFile(filepath + "\\" + (String) mp3_raw.get(0) + ".mp3", (byte[]) mp3_raw.get(1));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the type.
     *
     * @param strType
     *                    the str type
     */
    @Override
    public void removeType(String strType) {
        for (String type : this.getModel().getTypes()) {
            if (type.toString().equalsIgnoreCase(strType)) {
                this.getNotChoosenTypes().remove(type);
            }
        }
    }

    /**
     * Adds the type.
     *
     * @param strType
     *                    the str type
     */
    @Override
    public void addType(String strType) {
        for (String type : this.getModel().getTypes()) {
            if (type.toString().equalsIgnoreCase(strType)) {
                this.getNotChoosenTypes().add(type);
            }
        }
    }

    /**
     * Start game.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void startGame() {
        this.setTmpList((ArrayList<IEntity>) this.getModel().getThemes().clone());

        ArrayList<IEntity> toRemove = new ArrayList<>();
        for (IEntity theme : this.getTmpList()) {
            for (String type : this.notChoosenTypes) {
                if (theme.getType().equalsIgnoreCase(type)) {
                    toRemove.add(theme);
                }
            }
        }
        this.getTmpList().removeAll(toRemove);
        Collections.shuffle(this.getTmpList());

        this.timer = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < ControllerMain.this.getTmpList().size(); i++) {
                    if (ControllerMain.this.getTmpList().get(i).isHasError()) {
                        ControllerMain.this.getTmpList().remove(ControllerMain.this.getTmpList().get(i));
                    }
                }
                for (int i = 0; i < ControllerMain.this.getTmpList().size(); i++) {
                    ControllerMain.this.setThemeIndex(i);
                    ControllerMain.this.setTheme(ControllerMain.this.getTmpList().get(i));
                    ControllerMain.this.setTimeLeft(ControllerMain.allowedTime);
                    ControllerMain.this.setChanged();
                    ControllerMain.this.notifyObservers();
                    while (ControllerMain.this.getTimeLeft() >= -1) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ControllerMain.this.setTimeLeft(ControllerMain.this.getTimeLeft() - 1);
                        ControllerMain.this.setChanged();
                        ControllerMain.this.notifyObservers();
                    }
                    while (ControllerMain.this.getTimeLeft() >= -6) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        ControllerMain.this.setTimeLeft(ControllerMain.this.getTimeLeft() - 1);
                        ControllerMain.this.setChanged();
                        ControllerMain.this.notifyObservers();
                    }
                }
            }
        };
        this.getTimer().setDaemon(true);
        this.getTimer().start();
    }

    /**
     * Gets the timer.
     *
     * @return the timer
     */
    @Override
    public Thread getTimer() {
        return this.timer;
    }

    /**
     * Sets the view.
     *
     * @param view
     *                 the new view
     */
    public void setView(IView view) {
        this.view = view;
    }

    /**
     * Sets the model.
     *
     * @param model
     *                  the new model
     */
    public void setModel(IModel model) {
        this.model = model;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    @Override
    public IModel getModel() {
        // TODO Auto-generated method stub
        return this.model;
    }

    /**
     * Gets the view.
     *
     * @return the view
     */
    @Override
    public IView getView() {
        // TODO Auto-generated method stub
        return this.view;
    }

    /**
     * Gets the time left.
     *
     * @return the time left
     */
    @Override
    public int getTimeLeft() {
        // TODO Auto-generated method stub
        return this.timeLeft;
    }

    /**
     * Sets the time left.
     *
     * @param timeLeft
     *                     the new time left
     */
    @Override
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    /**
     * Gets the observable.
     *
     * @return the observable
     */
    @Override
    public Observable getObservable() {
        // TODO Auto-generated method stub
        return this;
    }

    /**
     * Gets the theme.
     *
     * @return the theme
     */
    @Override
    public IEntity getTheme() {
        return this.theme;
    }

    /**
     * Sets the theme.
     *
     * @param theme
     *                  the new theme
     */
    @Override
    public void setTheme(IEntity theme) {
        this.theme = theme;
    }

    /**
     * Gets the theme index.
     *
     * @return the theme index
     */
    @Override
    public int getThemeIndex() {
        return this.themeIndex;
    }

    /**
     * Sets the theme index.
     *
     * @param themeIndex
     *                       the new theme index
     */
    public void setThemeIndex(int themeIndex) {
        this.themeIndex = themeIndex;
    }

    /**
     * Gets the allowed time.
     *
     * @return the allowed time
     */
    @Override
    public int getAllowedTime() {
        return allowedTime;
    }

    /**
     * Sets the allowed time.
     *
     * @param aLLOWED_TIME
     *                         the new allowed time
     */
    @Override
    public void setAllowedTime(int aLLOWED_TIME) {
        ControllerMain.allowedTime = aLLOWED_TIME;
    }

    /**
     * Gets the not choosen types.
     *
     * @return the not choosen types
     */
    @Override
    public ArrayList<String> getNotChoosenTypes() {
        return this.notChoosenTypes;
    }

    /**
     * Sets the not choosen types.
     *
     * @param notChoosenTypes
     *                            the new not choosen types
     */
    @Override
    public void setNotChoosenTypes(ArrayList<String> notChoosenTypes) {
        this.notChoosenTypes = notChoosenTypes;
    }

    /**
     * Gets the tmp list.
     *
     * @return the tmp list
     */
    @Override
    public ArrayList<IEntity> getTmpList() {
        return this.tmpList;
    }

    /**
     * Sets the tmp list.
     *
     * @param tmpList
     *                    the new tmp list
     */
    public void setTmpList(ArrayList<IEntity> tmpList) {
        this.tmpList = tmpList;
    }

    @Override
    public JSONArray requestTMDbMovie(String type, String searchString) {
        String api_key = "1abb592ea8abdca659fe768691191c91";
        int page = 1;
        String query = URLEncoder.encode(searchString, StandardCharsets.UTF_8);
        JSONObject json = null;
        try {
            json = TMDbAPIQuery.readJsonFromUrl("https://api.themoviedb.org/3/search/" + type + "?api_key=" + api_key
                    + "&page=" + page + "&query=" + query);
        } catch (JSONException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return json.getJSONArray("results");
    }

    @Override
    public BufferedImage loadTMDbImage(String url) {
        URL url2;
        BufferedImage img = null;
        try {
            url2 = new URL("https://image.tmdb.org/t/p/w500" + url);
            img = ImageIO.read(url2);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return img;
    }

    @Override
    public void createThemeFromSearch(JSONObject theme, String type, Map<String, String> titlesMap) {
        String title = theme.getString(titlesMap.get(type));
        System.out.println(TimeFormatter.getTimestamp() + title);
        System.out.println(TimeFormatter.getTimestamp() + type);
        try {
            Files.createDirectories(Paths.get("files/" + title));
        } catch (IOException e) {
            System.err.println(TimeFormatter.getTimestamp() + "Failed to create directory!" + e.getMessage());
        }

        try {
            // retrieve image
            BufferedImage bi = this.loadTMDbImage(theme.getString("poster_path"));
            File outputfile = new File("files/" + title + "/poster.png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {
        }

        File properties = new File("files/" + title + "/theme.properties");
        InputStream inputStream = null;
        try {
            properties.createNewFile();

            try (FileWriter fw = new FileWriter(properties);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)) {
                out.println("title=" + title);
                out.println("composer=");
                out.println("release=" + theme.getString(this.releaseMap.get(type)).substring(0, 4));
                out.println("timecode=");
                out.println("type=" + type);
                out.println("infos=");
            }

            inputStream = new FileInputStream(properties);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}