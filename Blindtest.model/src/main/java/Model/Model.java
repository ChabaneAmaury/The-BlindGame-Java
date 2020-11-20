/*
 * @author Amaury Chabane
 */

package Model;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;

import Contract.IEntity;
import Contract.IModel;
import Entity.Theme;
import Model.connection.FileClient;
import Model.connection.FileServer;

/**
 * The Class Model.
 *
 * @author Amaury Chabane
 */
public class Model implements IModel {

    /** The folders. */
    private File[] folders = null;

    /** The types. */
    private final ArrayList<String> types = new ArrayList<>();

    /** The themes. */
    private final ArrayList<IEntity> themes = new ArrayList<>();

    /** The I ps to scan. */
    private final ArrayList<String> IPsToScan = new ArrayList<>();

    /**
     * Instantiates a new model.
     */
    public Model() {
        this.fillThemesList();
        this.loadTypes();

        try {
            Thread server = new Thread(new FileServer(this));
            server.setDaemon(true);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Thread clientSearching = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(30000);
                    Model.this.scanIPsInSubnet();
                    for (String addr : Model.this.getIPsToScan()) {
                        System.out.println("Trying " + addr);
                        @SuppressWarnings("unused")
                        FileClient client = new FileClient(Model.this, addr);
                    }
                }
            } catch (Exception ignored) {
            }

        });
        clientSearching.setDaemon(true);
        clientSearching.start();

    }

    /**
     * Scan I ps in subnet.
     */
    public void scanIPsInSubnet() {
        this.getIPsToScan().clear();
        Enumeration<NetworkInterface> nets = null;
        try {
            nets = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        assert nets != null;
        for (NetworkInterface netint : Collections.list(nets)) {
            Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();

            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();

                String ip = inetAddress.getHostAddress();

                String sip = ip.substring(0, ip.indexOf('.', ip.indexOf('.', ip.indexOf('.') + 1) + 1) + 1);
                if (!sip.equals("127.0.0.")) {

                    new Thread(() -> {
                        for (int it = 1; it <= 255; it++) {
                            try {
                                String ipToTest = sip + it;
                                boolean online = InetAddress.getByName(ipToTest).isReachable(50);
                                if (online) {
                                    Socket s = new Socket();
                                    s.connect(new InetSocketAddress(ipToTest, 15125), 50);
                                    System.out.println("Server is listening on port " + 15125 + " of " + ipToTest);
                                    s.close();

                                    Model.this.getIPsToScan().add(ipToTest);
                                }

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        }
    }

    /**
     * Load types.
     */
    @Override
    public void loadTypes() {
        this.getTypes().clear();
        for (IEntity theme : this.getThemes()) {
            if (this.getTypes().isEmpty()) {
                this.getTypes().add(theme.getType());
            } else {
                if (!this.getTypes().contains(theme.getType())) {
                    this.getTypes().add(theme.getType());
                }
            }
        }

    }

    /**
     * Fill themes list.
     */
    @Override
    public void fillThemesList() {
        this.loadFolders();
        this.getThemes().clear();
        for (File theme : this.getFolders()) {
            if (theme.isDirectory()) {
                Theme loadedTheme = new Theme(theme);
                this.getThemes().add(loadedTheme);
            }
        }
    }

    /**
     * Load folders.
     */
    @Override
    public void loadFolders() {
        try {
            File file = new File("files\\theme.zip");
            if (file.delete()) {
                System.out.println("File deleted successfully");
            } else {
                System.out.println("Failed to delete the file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            this.setFolders(new File("files/").listFiles());
        } catch (Exception e) {
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

    /**
     * Gets the types.
     *
     * @return the types
     */
    @Override
    public ArrayList<String> getTypes() {
        return this.types;
    }

    /**
     * Gets the i ps to scan.
     *
     * @return the i ps to scan
     */
    public ArrayList<String> getIPsToScan() {
        return this.IPsToScan;
    }

}
