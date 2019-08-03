/*
 *
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

import Model.Model;

public class FileServer implements Runnable {

    private Model model = null;

    private ServerSocket serverSocket = new ServerSocket(15125);
    private Socket socket = null;

    private ArrayList<File> clientsThemes = new ArrayList<>();

    private ArrayList<File> foldersToSend = new ArrayList<>();

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
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

    public FileServer(Model model) throws Exception {
        this.setModel(model);
    }

    public void startServer() {
        System.out.println("Server is running...");
        this.waitForClient();
        this.setClientsThemes(this.getStreamedClientsThemes());
        System.out.println(this.getClientsThemes());
        this.findFoldersToSend();

        this.sendFoldersToSend();

        for (File folder : this.getFoldersToSend()) {
            this.sendTheme(folder.getAbsolutePath());
        }
    }

    public void sendFoldersToSend() {
        try {
            ObjectOutputStream os = new ObjectOutputStream(this.socket.getOutputStream());
            os.writeObject(this.getFoldersToSend());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void findFoldersToSend() {
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

    public void waitForClient() {
        try {
            this.socket = this.serverSocket.accept();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public ArrayList<File> getStreamedClientsThemes() {
        ArrayList<File> clientsThemes = null;
        try {
            ObjectInputStream is = new ObjectInputStream(this.socket.getInputStream());
            clientsThemes = new ArrayList<>(Arrays.asList((File[]) is.readObject()));
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return clientsThemes;
    }

    public void sendTheme(String themeFolder) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            String sourceFile = themeFolder;
            ZipOutputStream zipOut = new ZipOutputStream(baos);
            File fileToZip = new File(sourceFile);
            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            System.out.println("Accepted connection : " + this.socket);
            ObjectOutputStream ous = new ObjectOutputStream(this.socket.getOutputStream());
            ous.writeObject(baos.toByteArray());
            ous.flush();
            zipOut.close();
            baos.close();
            System.out.println("File transfer complete");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Model getModel() {
        return this.model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setClientsThemes(ArrayList<File> clientsThemes) {
        this.clientsThemes = clientsThemes;
    }

    public ArrayList<File> getClientsThemes() {
        return this.clientsThemes;
    }

    public ArrayList<File> getFoldersToSend() {
        return this.foldersToSend;
    }

    public void setFoldersToSend(ArrayList<File> foldersToSend) {
        this.foldersToSend = foldersToSend;
    }

    @Override
    public void run() {
        this.startServer();
    }
}