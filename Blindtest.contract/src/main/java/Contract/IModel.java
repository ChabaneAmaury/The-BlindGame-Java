/*
 *
 */
package Contract;

import java.io.File;
import java.util.ArrayList;

/**
 * The Interface IModel.
 *
 * @author Amaury Chabane
 */
public interface IModel {

    /**
     * Gets the folders.
     *
     * @return the folders
     */
    File[] getFolders();

    /**
     * Gets the themes.
     *
     * @return the themes
     */
    ArrayList<IEntity> getThemes();

    /**
     * Sets the folders.
     *
     * @param folders the new folders
     */
    void setFolders(File[] folders);

    /**
     * Load folders.
     */
    void loadFolders();

    /**
     * Fill themes list.
     */
    void fillThemesList();

    /**
     * Load types.
     */
    void loadTypes();

    /**
     * Gets the types.
     *
     * @return the types
     */
    ArrayList<String> getTypes();

}
