/*
 * @author Amaury Chabane
 */
package Model.connection;

/*
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import Contract.IModel;

/**
 * The Class FileServer.
 *
 * @author Amaury Chabane
 */
public class FileServer implements Runnable {

    /** The model. */
    private IModel IModel = null;

    /** The server socket. */
    private ServerSocket serverSocket = null;

    /** The socket. */
    private Socket socket = null;

    /** The clients themes. */
    private ArrayList<File> clientsThemes = new ArrayList<>();

    /** The folders to send. */
    private final ArrayList<File> foldersToSend = new ArrayList<>();

    /**
     * Zip file.
     *
     * @param fileToZip
     *                      the file to zip
     * @param fileName
     *                      the file name
     * @param zipOut
     *                      the zip out
     * @throws IOException
     *                         Signals that an I/O exception has occurred.
     */
    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
            }
            zipOut.closeEntry();
            File[] children = fileToZip.listFiles();
            assert children != null;
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }

    /**
     * Instantiates a new file server.
     *
     * @param IModel
     *                  the model
     */
    public FileServer(IModel IModel) {
        this.setModel(IModel);
    }

    /**
     * Start server.
     */
    public void startServer() {
        System.out.println("[Server] Server is running on port 15125...");
        this.waitForClient();

        try {
            this.sendArrayList(this.getModel().getTypes());
            System.out.println("[Server] Accepted connection : " + this.socket);
            this.setClientsThemes(this.getStreamedClientsThemes());
            this.findFoldersToSend();
            System.out.println(this.getFoldersToSend());

            this.sendArrayList(this.getFoldersToSend());

            if (this.getFoldersToSend().size() > 0) {
                for (File folder : this.getFoldersToSend()) {
                    System.out.println("[Server] Sending " + folder + "...");
                    this.sendTheme(folder.getAbsolutePath());
                    System.out.println("[Server] Done!");
                }
            }
        } catch (NullPointerException ignored) {
        }

        try {
            this.socket.close();
            this.serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("[Server] Server stopped !");
        this.startServer();
    }

    /**
     * Send array list.
     *
     * @param arrayList
     *                      the array list
     */
    public void sendArrayList(ArrayList<?> arrayList) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(this.socket.getOutputStream());
            os.writeObject(arrayList);
        } catch (IOException ignored) {
        }
    }

    /**
     * Find folders to send.
     */
    public void findFoldersToSend() {
        this.getFoldersToSend().clear();
        for (File folder : this.getModel().getFolders()) {
            boolean checked = false;
            for (File clientsFolder : this.getClientsThemes()) {
                if (clientsFolder.getName().toLowerCase().equals(folder.getName().toLowerCase())) {
                    checked = true;
                    break;
                }
            }
            if (!checked) {
                this.getFoldersToSend().add(folder);
            }
        }
    }

    /**
     * Wait for client.
     */
    public void waitForClient() {
        try {
            this.serverSocket = new ServerSocket(15125);
            this.socket = this.serverSocket.accept();
            this.socket.setSoTimeout(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the streamed clients themes.
     *
     * @return the streamed clients themes
     */
    public ArrayList<File> getStreamedClientsThemes() {
        ArrayList<File> clientsThemes = null;

        ObjectInputStream is = null;
        try {
            is = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException ignored) {
        }
        try {
            assert is != null;
            clientsThemes = new ArrayList<>(Arrays.asList((File[]) is.readObject()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ignored) {
        }

        return clientsThemes;
    }

    /**
     * Send theme.
     *
     * @param themeFolder
     *                        the theme folder
     */
    public void sendTheme(String themeFolder) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ZipOutputStream zipOut = new ZipOutputStream(baos);
            File fileToZip = new File(themeFolder);
            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();

            ObjectOutputStream ous = new ObjectOutputStream(this.socket.getOutputStream());
            ous.writeObject(baos.toByteArray());
            ous.flush();
            zipOut.close();
            baos.close();
            System.out.println("File transfer complete");
        } catch (IOException e) {
            e.printStackTrace();
        }

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
     * @param IModel
     *                  the new model
     */
    public void setModel(IModel IModel) {
        this.IModel = IModel;
    }

    /**
     * Sets the clients themes.
     *
     * @param clientsThemes
     *                          the new clients themes
     */
    public void setClientsThemes(ArrayList<File> clientsThemes) {
        this.clientsThemes = clientsThemes;
    }

    /**
     * Gets the clients themes.
     *
     * @return the clients themes
     */
    public ArrayList<File> getClientsThemes() {
        return this.clientsThemes;
    }

    /**
     * Gets the folders to send.
     *
     * @return the folders to send
     */
    public ArrayList<File> getFoldersToSend() {
        return this.foldersToSend;
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        this.startServer();
    }
}