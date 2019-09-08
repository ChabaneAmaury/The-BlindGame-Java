/*
 *
 */
package Contract;

import java.awt.Image;

/**
 * The Interface IEntity.
 *
 * @author Amaury Chabane
 */
public interface IEntity {

    /**
     * Gets the timecode.
     *
     * @return the timecode
     */
    int getTimecode();

    /**
     * Gets the composer.
     *
     * @return the composer
     */
    String getComposer();

    /**
     * Sets the composer.
     *
     * @param composer
     *                     the new composer
     */
    void setComposer(String composer);

    /**
     * Gets the type.
     *
     * @return the type
     */
    String getType();

    /**
     * Sets the timecode.
     *
     * @param timecode
     *                     the new timecode
     */
    void setTimecode(int timecode);

    /**
     * Gets the title.
     *
     * @return the title
     */
    String getTitle();

    /**
     * Sets the title.
     *
     * @param title
     *                  the new title
     */
    void setTitle(String title);

    /**
     * Gets the file.
     *
     * @return the file
     */
    String getFile();

    /**
     * Gets the cover.
     *
     * @return the cover
     */
    String getCover();

    /**
     * Gets the release date.
     *
     * @return the release date
     */
    String getReleaseDate();

    /**
     * Sets the release date.
     *
     * @param releaseDate
     *                        the new release date
     */
    void setReleaseDate(String releaseDate);

    /**
     * Gets the infos.
     *
     * @return the infos
     */
    String getInfos();

    /**
     * Sets the infos.
     *
     * @param infos
     *                  the new infos
     */
    void setInfos(String infos);

    /**
     * Sets the type.
     *
     * @param type
     *                 the new type
     */
    void setType(String type);

    /**
     * Checks if is checks for error.
     *
     * @return true, if is checks for error
     */
    boolean isHasError();

    /**
     * Sets the checks for error.
     *
     * @param hasError
     *                     the new checks for error
     */
    void setHasError(boolean hasError);

    /**
     * Gets the cover image.
     *
     * @return the cover image
     */
    Image getCoverImage();

    /**
     * Gets the resized cover image.
     *
     * @return the resized cover image
     */
    Image getResizedCoverImage();

    /**
     * Sets the property value.
     *
     * @param key
     *                  the key
     * @param value
     *                  the value
     */
    void setPropertyValue(String key, String value);

    /**
     * Sets the cover image.
     *
     * @param coverImage
     *                       the new cover image
     */
    void setCoverImage(Image coverImage);

    void setResizedCoverImage(Image resizedCoverImage);

}
