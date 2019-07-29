/*
 *
 */
package Entity;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.imageio.ImageIO;
import Contract.IEntity;
import Contract.ImageResizer;
import Contract.Types;

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
    private String[] fileExtensions = new String[] { ".wav" };
    
    /** The cover extensions. */
    private String[] coverExtensions = new String[] { ".png", ".jpg", ".jpeg" };
    
    /** The cover image. */
    private Image coverImage = null;
    
    /** The resized cover image. */
    private Image resizedCoverImage = null;

    /** The has error. */
    private boolean hasError = false;

    /** The timecode. */
    private int timecode = 0;
    
    /** The type. */
    private Types type = Types.UNDEFINED;
    
    /** The Constant PROPERTIES_FILE_NAME. */
    private final static String PROPERTIES_FILE_NAME = "theme.properties";

    /**
     * Instantiates a new theme.
     *
     * @param folder the folder
     */
    public Theme(File folder) {
        try {
            this.setCover(this.FindFileByExtension(folder, this.getCoverExtensions()));
            this.setCoverImage(this.loadImage(this.getCover()));
            this.setResizedCoverImage(this.loadResizedImage(this.getCoverImage()));
        } catch (Exception e2) {
            e2.printStackTrace();
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
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (inputStream != null) {
            try {
                this.load(inputStream);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            this.setTitle(this.getProperty("title"));
            this.setComposer(this.getProperty("composer"));
            this.setReleaseDate(this.getProperty("release"));
            this.setInfos(this.getProperty("infos"));

            for (Types type : Types.values()) {
                if (this.getProperty("type").toUpperCase().equals(type.toString())) {
                    this.setType(type);
                    break;
                }
            }
            try {
                this.setTimecode(Integer.parseInt(this.getProperty("timecode")));
            } catch (NumberFormatException e) {
            }
        }
    }

    /**
     * Load image.
     *
     * @param path the path
     * @return the image
     */
    public Image loadImage(String path) {
        Image img = null;
        try {
            img = ImageIO.read(new File(path));
            // img = ImageResizer.resizeTrick((BufferedImage) img, rectW, rectH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return img;
    }

    /**
     * Load resized image.
     *
     * @param img the img
     * @return the image
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public Image loadResizedImage(Image img) throws IOException {
        int rectW = 1980 / 11;
        int rectH = (rectW * 160) / 120;
        return ImageResizer.resizeTrick((BufferedImage) img, rectW, rectH);
    }

    /**
     * Find file by extension.
     *
     * @param folder the folder
     * @param extensions the extensions
     * @return the string
     */
    public String FindFileByExtension(File folder, String[] extensions) {
        File[] files = folder.listFiles(new FilenameFilter() {

            @Override
            public boolean accept(File dir, String name) {
                for (String extension : extensions) {
                    if (name.toLowerCase().endsWith(extension)) {
                        return true;
                    }
                }
                return false;
            }
        });
        if (files.length > 0) {
            return files[0].getAbsolutePath();
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
     * @param composer the new composer
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
     * @param file the new file
     */
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
     * @param cover the new cover
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
    public Types getType() {
        return this.type;
    }

    /**
     * Sets the type.
     *
     * @param type the new type
     */
    @Override
    public void setType(Types type) {
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
     * @param timecode the new timecode
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
     * @param title the new title
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
    public String[] getFileExtensions() {
        return this.fileExtensions;
    }

    /**
     * Sets the file extensions.
     *
     * @param fileExtensions the new file extensions
     */
    public void setFileExtensions(String[] fileExtensions) {
        this.fileExtensions = fileExtensions;
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
     * Sets the cover extensions.
     *
     * @param coverExtensions the new cover extensions
     */
    public void setCoverExtensions(String[] coverExtensions) {
        this.coverExtensions = coverExtensions;
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
     * @param releaseDate the new release date
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
     * @param infos the new infos
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
     * @param hasError the new checks for error
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
     * @param coverImage the new cover image
     */
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
     * @param resizedCoverImage the new resized cover image
     */
    public void setResizedCoverImage(Image resizedCoverImage) {
        this.resizedCoverImage = resizedCoverImage;
    }

}