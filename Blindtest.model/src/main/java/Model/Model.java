/*
 *
 */

package Model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import Contract.IEntity;
import Contract.IModel;
import Entity.Theme;

/**
 * The Class Model.
 *
 * @author Amaury Chabane
 */
public class Model implements IModel {

    /** The folders. */
    private File[] folders = null;

    private ArrayList<String> types = new ArrayList<>();

    /** The themes. */
    private ArrayList<IEntity> themes = new ArrayList<>();

    /**
     * Instantiates a new model.
     */
    public Model() {
        this.loadTypes();
        this.loadFolders();
        this.fillThemesList();
    }

    @Override
    public void loadTypes() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("bin\\types.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                this.getTypes().add(line);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Fill themes list.
     */
    @Override
    public void fillThemesList() {
        this.getThemes().removeAll(this.themes);
        for (int i = 0; i < this.getFolders().length; i++) {
            Theme theme = new Theme(this, this.getFolders()[i]);
            this.getThemes().add(theme);
        }
    }

    /**
     * Load folders.
     */
    @Override
    public void loadFolders() {
        try {
            this.setFolders(new File("files\\").listFiles());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Gets the folders.
     *
     * @return the folders
     */
    @Override
    public File[] getFolders() {
        return this.folders;
    }

    /**
     * Sets the folders.
     *
     * @param folders
     *                    the new folders
     */
    @Override
    public void setFolders(File[] folders) {
        this.folders = folders;
    }

    /**
     * Gets the themes.
     *
     * @return the themes
     */
    @Override
    public ArrayList<IEntity> getThemes() {
        return this.themes;
    }

    @Override
    public ArrayList<String> getTypes() {
        return this.types;
    }

    public void setTypes(ArrayList<String> types) {
        this.types = types;
    }
}
