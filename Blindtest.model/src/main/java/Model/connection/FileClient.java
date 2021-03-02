/*
 * @author Amaury Chabane
 */
package Model.connection;

/*
 *
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import Contract.IModel;
import Contract.TimeFormatter;

/**
 * The Class FileClient.
 *
 * @author Amaury Chabane
 */
public class FileClient {

    /** The model. */
    private IModel IModel = null;

    /** The socket. */
    private Socket socket = null;

    /** The folders to receive. */
    private ArrayList<File> foldersToReceive = new ArrayList<>();

    /** The ip. */
    private String ip = null;

    /** The Constant BUFFER_SIZE. */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Unzip.
     *
     * @param zipFilePath the zip file path
     * @param destDirectory the dest directory
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            if (!destDir.mkdir()){
                throw new IllegalArgumentException("Cannot create directory: " + destDirectory);
            }
        }
        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(zipFilePath));
        ZipEntry entry = zipIn.getNextEntry();
        // iterates over entries in the zip file
        while (entry != null) {
            String filePath = destDirectory + File.separator + entry.getName();
            if (!entry.isDirectory()) {
                // if the entry is a file, extracts it
                this.extractFile(zipIn, filePath);
            } else {
                // if the entry is a directory, make the directory
                File dir = new File(filePath);
                if (!dir.mkdir()){
                    throw new IllegalArgumentException("Cannot create directory: " + filePath);
                }
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extract file.
     *
     * @param zipIn the zip in
     * @param filePath the file path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        File yourFile = new File(filePath);
        if (!yourFile.exists()) {
            if (!yourFile.createNewFile()){
                throw new IllegalArgumentException("Cannot create file: " + filePath);
            }

        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath, false));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    /**
     * Instantiates a new file client.
     *
     * @param IModel the model
     * @param ip the ip
     */
    public FileClient(IModel IModel, String ip) {
        this.setIp(ip);
        this.setModel(IModel);
        try {
            this.startClient();
        } catch (Exception ignored) {
        }
    }

    /**
     * Start client.
     */
    public void startClient() {
        try {
            this.connectToServer(this.getIp());
            System.out.println(TimeFormatter.getTimestamp() + "[Client] Connected to : " + this.socket);

            this.sendFolders();
            this.setFoldersToReceive(this.getStreamedFoldersToReceive());

            if (this.getFoldersToReceive().size() > 0) {
                for (File folderToReceive : this.getFoldersToReceive()) {
                    System.out.println(TimeFormatter.getTimestamp() + "[Client] Receiving " + folderToReceive + "...");
                    this.receiveTheme();
                    this.getModel().fillThemesList();
                    System.out.println(TimeFormatter.getTimestamp() + "[Client] Done!");
                }

            }
        } catch (Exception ignored) {
        }

        try {
            this.socket.close();
        } catch (IOException ignored) {
        }
        System.out.println(TimeFormatter.getTimestamp() + "[Client] Client stopped!");
    }

    /**
     * Send folders.
     */
    private void sendFolders() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(this.socket.getOutputStream());
            os.writeObject(this.getModel().getFolders());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Connect to server.
     *
     * @param ip the ip
     */
    private void connectToServer(String ip) {
        try {
            this.socket = new Socket(ip, 15125, null, 15120);
        } catch (IOException ignored) {
        }
    }

    /**
     * Gets the streamed folders to receive.
     *
     * @return the streamed folders to receive
     */
    @SuppressWarnings("unchecked")
    private ArrayList<File> getStreamedFoldersToReceive() {
        ArrayList<File> folders = null;
        try {
            ObjectInputStream is = new ObjectInputStream(this.socket.getInputStream());
            folders = (ArrayList<File>) is.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return folders;
    }

    /**
     * Receive theme.
     */
    private void receiveTheme() {
        String zipName = "files\\rcv_theme.zip";
        try {
            FileOutputStream fos = new FileOutputStream(zipName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            fos.write((byte[]) ois.readObject());
            bos.flush();
            bos.close();
            String destDir = new File(zipName).getAbsolutePath().replace(zipName, "files");
            System.out.println(TimeFormatter.getTimestamp() + destDir);
            this.unzip(zipName, destDir);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            File file = new File(zipName);
            if (file.delete()) {
                System.out.println(TimeFormatter.getTimestamp() + "File deleted successfully");
            } else {
                System.out.println(TimeFormatter.getTimestamp() + "Failed to delete the file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets the folders to receive.
     *
     * @return the folders to receive
     */
    public ArrayList<File> getFoldersToReceive() {
        return this.foldersToReceive;
    }

    /**
     * Sets the folders to receive.
     *
     * @param foldersToReceive the new folders to receive
     */
    public void setFoldersToReceive(ArrayList<File> foldersToReceive) {
        this.foldersToReceive = foldersToReceive;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public IModel getModel() {
        return this.IModel;
    }

    /**
     * Sets the model.
     *
     * @param IModel the new model
     */
    public void setModel(IModel IModel) {
        this.IModel = IModel;
    }

    /**
     * Gets the ip.
     *
     * @return the ip
     */
    public String getIp() {
        return this.ip;
    }

    /**
     * Sets the ip.
     *
     * @param ip the new ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }
}