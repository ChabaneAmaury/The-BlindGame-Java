/*
 *
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import Model.Model;

/**
 * The Class FileClient.
 *
 * @author Amaury Chabane
 */
public class FileClient {

    /** The model. */
    private Model model = null;

    /** The socket. */
    private Socket socket = null;

    /** The folders to receive. */
    private ArrayList<File> foldersToReceive = new ArrayList<>();

    /** The server types. */
    private ArrayList<String> serverTypes = new ArrayList<>();

    /** The ip. */
    private String ip = null;

    /** The Constant BUFFER_SIZE. */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Unzip.
     *
     * @param zipFilePath
     *                          the zip file path
     * @param destDirectory
     *                          the dest directory
     * @throws IOException
     *                         Signals that an I/O exception has occurred.
     */
    private void unzip(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);
        if (!destDir.exists()) {
            destDir.mkdir();
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
                dir.mkdir();
            }
            zipIn.closeEntry();
            entry = zipIn.getNextEntry();
        }
        zipIn.close();
    }

    /**
     * Extract file.
     *
     * @param zipIn
     *                     the zip in
     * @param filePath
     *                     the file path
     * @throws IOException
     *                         Signals that an I/O exception has occurred.
     */
    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
        File yourFile = new File(filePath);
        if (!yourFile.exists()) {
            yourFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath, false));
        byte[] bytesIn = new byte[BUFFER_SIZE];
        int read = 0;
        while ((read = zipIn.read(bytesIn)) != -1) {
            bos.write(bytesIn, 0, read);
        }
        bos.close();
    }

    /**
     * Instantiates a new file client.
     *
     * @param model
     *                  the model
     * @param ip
     *                  the ip
     */
    public FileClient(Model model, String ip) {
        this.setIp(ip);
        this.setModel(model);
        try {
            this.startClient();
        } catch (Exception e) {
        }
    }

    /**
     * Start client.
     */
    public void startClient() {
        try {
            this.connectToServer(this.getIp());
            System.out.println("[Client] Connected to : " + this.socket);
            this.setServerTypes(this.getStreamedTypes());
            this.addMissingTypes();
            this.getModel().loadTypes();

            this.sendFolders();
            this.setFoldersToReceive(this.getStreamedFoldersToReceive());

            if (this.getFoldersToReceive().size() > 0) {
                for (File folderToReceive : this.getFoldersToReceive()) {
                    System.out.println("[Client] Receiving " + folderToReceive + "...");
                    this.receiveTheme();
                    this.getModel().fillThemesList();
                    System.out.println("[Client] Done!");
                }

            }
        } catch (Exception e1) {
        }

        try {
            this.socket.close();
        } catch (IOException e) {
        }
        System.out.println("[Client] Client stopped!");
    }

    /**
     * Adds the missing types.
     */
    public void addMissingTypes() {
        for (String serverType : this.getServerTypes()) {
            boolean checked = false;
            for (String type : this.getModel().getTypes()) {
                if (serverType.equalsIgnoreCase(type)) {
                    checked = true;
                    break;
                }
            }
            if (!checked) {
                this.appendNewLineToTypes(serverType);
            }
        }
    }

    /**
     * Append new line to types.
     *
     * @param value
     *                  the value
     */
    public void appendNewLineToTypes(String value) {

        try {
            Files.write(Paths.get("bin\\types.txt"), ("\n" + value).getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Send folders.
     */
    private void sendFolders() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(this.socket.getOutputStream());
            os.writeObject(this.getModel().getFolders());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Connect to server.
     *
     * @param ip
     *               the ip
     */
    private void connectToServer(String ip) {
        try {
            this.socket = new Socket(ip, 15125, null, 15120);
        } catch (IOException e) {
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return folders;
    }

    /**
     * Gets the streamed types.
     *
     * @return the streamed types
     */
    @SuppressWarnings("unchecked")
    private ArrayList<String> getStreamedTypes() {
        ArrayList<String> folders = null;
        try {
            ObjectInputStream is = new ObjectInputStream(this.socket.getInputStream());
            folders = (ArrayList<String>) is.readObject();
        } catch (ClassNotFoundException | IOException e) {
        }
        return folders;
    }

    /**
     * Receive theme.
     */
    private void receiveTheme() {
        String zipName = "files\\theme.zip";
        try {
            FileOutputStream fos = new FileOutputStream(zipName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            fos.write((byte[]) ois.readObject());
            bos.flush();
            bos.close();
            String destDir = new File(zipName).getAbsolutePath().replace(zipName, "files");
            System.out.println(destDir);
            this.unzip(zipName, destDir);

        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            File file = new File(zipName);
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
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
     * @param foldersToReceive
     *                             the new folders to receive
     */
    public void setFoldersToReceive(ArrayList<File> foldersToReceive) {
        this.foldersToReceive = foldersToReceive;
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public Model getModel() {
        return this.model;
    }

    /**
     * Sets the model.
     *
     * @param model
     *                  the new model
     */
    public void setModel(Model model) {
        this.model = model;
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
     * @param ip
     *               the new ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Gets the server types.
     *
     * @return the server types
     */
    public ArrayList<String> getServerTypes() {
        return this.serverTypes;
    }

    /**
     * Sets the server types.
     *
     * @param serverTypes
     *                        the new server types
     */
    public void setServerTypes(ArrayList<String> serverTypes) {
        this.serverTypes = serverTypes;
    }
}