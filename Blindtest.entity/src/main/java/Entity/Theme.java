/*
 * @author Amaury Chabane
 */
package Entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Random;
import javax.imageio.ImageIO;
import Contract.IEntity;
import Contract.TimeFormatter;

/**
 * The Class Theme.
 *
 * @author Amaury Chabane
 */
public class Theme extends Properties implements IEntity {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 4982921459536513037L;

    /** The title. */
    private String title = "Undefined";

    /** The folder. */
    private File folder = null;

    /** The composer. */
    private String composer = "Undefined";

    /** The file. */
    private String file = "theme.wav";

    /** The cover. */
    private String cover = "cover.png";

    /** The release date. */
    private String releaseDate = "Undefined";

    /** The infos. */
    private String infos = "";

    /** The file extensions. */
    private final String[] fileExtensions = new String[] { ".mp3", ".wav" };

    /** The cover extensions. */
    private final String[] coverExtensions = new String[] { ".png", ".jpg", ".jpeg" };

    /** The cover image. */
    private Image coverImage = null;

    /** The resized cover image. */
    private Image resizedCoverImage = null;

    /** The thumbnail cover image. */
    private Image thumbnailCoverImage = null;

    /** The has error. */
    private boolean hasError = false;

    /** The timecode. */
    private int timecode = 0;

    /** The type. */
    private String type = "UNDEFINED";

    /** The Constant PROPERTIES_FILE_NAME. */
    private final static String PROPERTIES_FILE_NAME = "theme.properties";

    /**
     * Instantiates a new theme.
     *
     * @param folder
     *                   the folder
     */
    public Theme(File folder) {
        this.setFolder(folder);
        try {
            this.setCover(this.FindFileByExtension(folder, this.getCoverExtensions()));
            this.setCoverImage(this.loadImage(this.getCover()));
        } catch (Exception e2) {
            e2.printStackTrace();
        }

        File thumb = new File(folder.getAbsolutePath() + "\\cover_thumb.jpg");
        if (thumb.exists()) {
            this.setThumbnailCoverImage(this.loadImage(thumb.getAbsolutePath()));
        }

        if (new File(this.FindFileByExtension(folder, this.getFileExtensions())).exists()) {
            this.setFile(this.FindFileByExtension(folder, this.getFileExtensions()));
        } else {
            this.setHasError(true);
        }
        File properties = new File(folder.getAbsolutePath() + "\\" + Theme.PROPERTIES_FILE_NAME);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(properties);
        } catch (FileNotFoundException e1) {
            try {
                if (!properties.createNewFile()){
                    throw new IllegalArgumentException("Cannot create file: " + folder.getAbsolutePath() + "\\" + Theme.PROPERTIES_FILE_NAME);
                }

                try (FileWriter fw = new FileWriter(properties);
                        BufferedWriter bw = new BufferedWriter(fw);
                        PrintWriter out = new PrintWriter(bw)) {
                    out.println("title=");
                    out.println("composer=");
                    out.println("release=");
                    out.println("timecode=");
                    out.println("type=");
                    out.println("infos=");
                }

                inputStream = new FileInputStream(properties);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (inputStream != null) {
            try {
                this.load(inputStream);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            if (!this.getProperty("title").isEmpty()) {
                this.setTitle(this.getProperty("title"));
            }
            if (!this.getProperty("composer").isEmpty()) {
                this.setComposer(this.getProperty("composer"));
            }
            if (!this.getProperty("release").isEmpty()) {
                this.setReleaseDate(this.getProperty("release"));
            }
            if (!this.getProperty("infos").isEmpty()) {
                this.setInfos(this.getProperty("infos"));
            }

            if (!this.getProperty("type").isEmpty()) {
                this.setType(this.getProperty("type"));
            }
            try {
                this.setTimecode(Integer.parseInt(this.getProperty("timecode")));
            } catch (NumberFormatException ignored) {
            }
        }
        try {
            assert inputStream != null;
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load image.
     *
     * @param path
     *                 the path
     * @return the image
     */
    public BufferedImage loadImage(String path) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Sets the property value.
     *
     * @param key
     *                  the key
     * @param value
     *                  the value
     */
    @Override
    public void setPropertyValue(String key, String value) {
        File properties = new File(this.getFolder().getAbsolutePath() + "\\" + Theme.PROPERTIES_FILE_NAME);
        InputStream inputStream = null;
        FileOutputStream fos;
        try {
            inputStream = new FileInputStream(properties);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        if (inputStream != null) {
            try {
                this.load(inputStream);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            this.setProperty(key, value);
            try {
                inputStream.close();
                fos = new FileOutputStream(this.getFolder().getAbsolutePath() + "\\" + Theme.PROPERTIES_FILE_NAME);
                this.store(fos, "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Find file by extension.
     *
     * @param folder
     *                       the folder
     * @param extensions
     *                       the extensions
     * @return the string
     */
    @Override
    public String FindFileByExtension(File folder, String[] extensions) {
        File[] files = folder.listFiles((dir, name) -> {
            for (String extension : extensions) {
                if (!name.toLowerCase().contains("cover_thumb") && name.toLowerCase().endsWith(extension)) {
                    return true;
                }
            }
            return false;
        });
        assert files != null;
        if (files.length > 0) {
            return files[new Random().nextInt(files.length)].getAbsolutePath();
        } else {
            return "";
        }
    }

    /**
     * Gets the composer.
     *
     * @return the composer
     */
    @Override
    public String getComposer() {
        return this.composer;
    }

    /**
     * Sets the composer.
     *
     * @param composer
     *                     the new composer
     */
    @Override
    public void setComposer(String composer) {
        this.composer = composer;
    }

    /**
     * Gets the file.
     *
     * @return the file
     */
    @Override
    public String getFile() {
        return this.file;
    }

    /**
     * Sets the file.
     *
     * @param file
     *                 the new file
     */
    @Override
    public void setFile(String file) {
        this.file = file;
    }

    /**
     * Gets the cover.
     *
     * @return the cover
     */
    @Override
    public String getCover() {
        return this.cover;
    }

    /**
     * Sets the cover.
     *
     * @param cover
     *                  the new cover
     */
    public void setCover(String cover) {
        this.cover = cover;
    }

    /**
     * Gets the type.
     *
     * @return the type
     */
    @Override
    public String getType() {
        return this.type;
    }

    /**
     * Sets the type.
     *
     * @param type
     *                 the new type
     */
    @Override
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the timecode.
     *
     * @return the timecode
     */
    @Override
    public int getTimecode() {
        return this.timecode;
    }

    /**
     * Sets the timecode.
     *
     * @param timecode
     *                     the new timecode
     */
    @Override
    public void setTimecode(int timecode) {
        this.timecode = timecode;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * Sets the title.
     *
     * @param title
     *                  the new title
     */
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the file extensions.
     *
     * @return the file extensions
     */
    @Override
    public String[] getFileExtensions() {
        return this.fileExtensions;
    }

    /**
     * Gets the cover extensions.
     *
     * @return the cover extensions
     */
    public String[] getCoverExtensions() {
        return this.coverExtensions;
    }

    /**
     * Gets the release date.
     *
     * @return the release date
     */
    @Override
    public String getReleaseDate() {
        return this.releaseDate;
    }

    /**
     * Sets the release date.
     *
     * @param releaseDate
     *                        the new release date
     */
    @Override
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * Gets the infos.
     *
     * @return the infos
     */
    @Override
    public String getInfos() {
        return this.infos;
    }

    /**
     * Sets the infos.
     *
     * @param infos
     *                  the new infos
     */
    @Override
    public void setInfos(String infos) {
        this.infos = infos;
    }

    /**
     * Checks if is checks for error.
     *
     * @return true, if is checks for error
     */
    @Override
    public boolean isHasError() {
        return this.hasError;
    }

    /**
     * Sets the checks for error.
     *
     * @param hasError
     *                     the new checks for error
     */
    @Override
    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    /**
     * Gets the cover image.
     *
     * @return the cover image
     */
    @Override
    public Image getCoverImage() {
        return this.coverImage;
    }

    /**
     * Sets the cover image.
     *
     * @param coverImage
     *                       the new cover image
     */
    @Override
    public void setCoverImage(Image coverImage) {
        this.coverImage = coverImage;
    }

    /**
     * Gets the resized cover image.
     *
     * @return the resized cover image
     */
    @Override
    public Image getResizedCoverImage() {
        return this.resizedCoverImage;
    }

    /**
     * Sets the resized cover image.
     *
     * @param resizedCoverImage
     *                              the new resized cover image
     */
    @Override
    public void setResizedCoverImage(Image resizedCoverImage) {
        this.resizedCoverImage = resizedCoverImage;
    }

    /**
     * Gets the folder.
     *
     * @return the folder
     */
    @Override
    public File getFolder() {
        return this.folder;
    }

    /**
     * Sets the folder.
     *
     * @param folder2
     *                    the new folder
     */
    public void setFolder(File folder2) {
        this.folder = folder2;
    }

    /**
     * Gets the thumbnail cover image.
     *
     * @return the thumbnail cover image
     */
    @Override
    public Image getThumbnailCoverImage() {
        return this.thumbnailCoverImage;
    }

    /**
     * Sets the thumbnail cover image.
     *
     * @param width
     *                   the width
     * @param height
     *                   the height
     */
    @Override
    public void setThumbnailCoverImage(int width, int height) {

        File outputFile;
        try {
            BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            img.createGraphics().drawImage(this.getCoverImage().getScaledInstance(width, height, Image.SCALE_SMOOTH), 0,
                    0, null);
            outputFile = new File(this.getFolder().getAbsolutePath() + "\\cover_thumb.jpg");
            ImageIO.write(img, "jpg", outputFile);
        } catch (IOException e) {
            System.out.println(TimeFormatter.getTimestamp() + "Exception while generating thumbnail " + e.getMessage());
        }

        this.setThumbnailCoverImage(this.loadImage(this.getFolder().getAbsolutePath() + "\\cover_thumb.jpg"));

        // this.setThumbnailCoverImage(this.getCoverImage().getScaledInstance(width,
        // height, Image.SCALE_SMOOTH));
    }

    /**
     * Sets the thumbnail cover image.
     *
     * @param thumbnailCoverImage
     *                                the new thumbnail cover image
     */
    @Override
    public void setThumbnailCoverImage(Image thumbnailCoverImage) {
        this.thumbnailCoverImage = thumbnailCoverImage;
    }
}
