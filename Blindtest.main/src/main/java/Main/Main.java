/*
 * @author Amaury Chabane
 */
package Main;

import Contract.IModel;
import Controller.ControllerMain;
import Model.Model;
import View.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Class Main.
 *
 * @author Amaury Chabane
 */
public class Main {

    private static final String logsFolder = "./logs/";

    /**
     * The main method.
     *
     * @param args
     *                 the arguments
     */
    public static void main(String[] args) {

        try
        {
            File theDir = new File(logsFolder);
            if (!theDir.exists()){
                theDir.mkdirs();
            }

            FileOutputStream fout = new FileOutputStream(logsFolder + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date())+".log");

            MultiOutputStream multiOut= new MultiOutputStream(System.out, fout);
            MultiOutputStream multiErr= new MultiOutputStream(System.err, fout);

            PrintStream stdout= new PrintStream(multiOut);
            PrintStream stderr= new PrintStream(multiErr);

            System.setOut(stdout);
            System.setErr(stderr);
        } catch (FileNotFoundException ex)
        {
            //Could not create/open the file
        }

        IModel IModel = new Model();
        View view = new View(IModel);
        ControllerMain controller = new ControllerMain(view, IModel);
        view.setController(controller);
    }



}
