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
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import Model.Model;

public class FileClient {

    private Model model = null;

    private Socket socket = null;
    private ArrayList<File> foldersToReceive = new ArrayList<>();

    /**
     * Size of the buffer to read/write data
     */
    private static final int BUFFER_SIZE = 4096;

    /**
     * Extracts a zip file specified by the zipFilePath to a directory specified by
     * destDirectory (will be created if does not exists)
     *
     * @param zipFilePath
     * @param destDirectory
     * @throws IOException
     */
    public void unzip(String zipFilePath, String destDirectory) throws IOException {
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
     * Extracts a zip entry (file entry)
     *
     * @param zipIn
     * @param filePath
     * @throws IOException
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

    public FileClient(Model model) {
        this.setModel(model);
        this.connectToServer();
        this.sendFolders();
        this.setFoldersToReceive(this.getStreamedFoldersToReceive());

        for (@SuppressWarnings("unused")
        File folderToReceive : this.getFoldersToReceive()) {
            System.out.println(folderToReceive.getName());
            this.receiveTheme();
        }
    }

    public void sendFolders() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(this.socket.getOutputStream());
            os.writeObject(this.getModel().getFolders());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void connectToServer() {
        try {
            this.socket = new Socket("127.0.0.1", 15125, null, 15120);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public ArrayList<File> getStreamedFoldersToReceive() {
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

    public void receiveTheme() {
        try {
            String zipName = "files\\theme.zip";
            FileOutputStream fos = new FileOutputStream(zipName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);

            ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
            fos.write((byte[]) ois.readObject());
            bos.flush();
            bos.close();
            String destDir = new File(zipName).getAbsolutePath().replace(zipName, "files");
            System.out.println(destDir);
            this.unzip(zipName, destDir);

            File file = new File(zipName);
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }

        } catch (IOException | ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ArrayList<File> getFoldersToReceive() {
        return this.foldersToReceive;
    }

    public void setFoldersToReceive(ArrayList<File> foldersToReceive) {
        this.foldersToReceive = foldersToReceive;
    }

    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}