package mandelbrot;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 */
public class MandelbrotController implements Initializable {
    /**
     * Textfield for aa.
     * Mandelbrot-Set should be "applied to an interval [Ca; Ce].
     * aa is real part of left, opening border of Ca.
     */
    @FXML
    TextField tfAa;

    /**
     * Textfield for ba.
     * Mandelbrot-Set should be "applied to an interval [Ca; Ce].
     * ba is imaginary part of left, opening border of Ca.
     */
    @FXML
    TextField tfBa;

    /**
     * Textfield for ae.
     * Mandelbrot-Set should be "applied to an interval [Ca; Ce].
     * ae is real part of right, closing border of interval - Ce.
     */
    @FXML
    TextField tfAe;

    /**
     * Textfield for be.
     * Mandelbrot-Set should be "applied to an interval [Ca; Ce].
     * be is imaginary part of right, closing border of interval - Ce.
     */
    @FXML
    TextField tfBe;

    /**
     * Textfield for depth of iterations. (= "Iterationstiefe")
     */
    @FXML
    TextField tfDepth;

    /**
     * Textfield for filename.
     */
    @FXML
    TextField tfFileName;

    /**
     * Button for drawing stuff from the user input.
     */
    @FXML
    Button btUebernehmen;

    /**
     * Button for drawing stuff via file.
     */
    @FXML
    Button btLaden;

    /**
     * Label for errorhandling.
     */
    @FXML
    Label lblErrorLoading;

    /**
     * Label for parameter errors.
     */
    @FXML
    Label lblErrorParams;

    /**
     * Anchorpane for the drawing.
     */
    @FXML
    AnchorPane apMandelbrot;

    /**
     * Canvas to draw the stuff.
     */
    @FXML
    Canvas canvas;

    /**
     * Calculations that are needed to understand, what point is in Mandelbrot-Set and what doesn't.
     */
    private Calculations calculations = new Calculations();

    /**
     * Craphics include methods to draw the Mandelbrot-set.
     */
    private Graphics graphics;

    /**
     * Tool to draw on canvas.
     */
    private GraphicsContext graphicsContext;

    /**
     * Thread is needed to create "slide-show" (when loading pictures from the file.
     */
    private Thread th;

    /**
     * Filename of loaded file.
     */
    private String fileName;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        graphics = new Graphics(canvas, calculations);
        graphicsContext = canvas.getGraphicsContext2D();

    }

    /**
     * Decides what has happened.
     *
     * @param ae actionEvent
     */
    public void draw(ActionEvent ae) {
        if (ae.getSource() == btUebernehmen) {
            handleUebernehmen();
        } else if (ae.getSource() == btLaden) {
            handleLaden();
        }
    }

    /**
     * Reading parameters from the file and calling draw-function.
     */
    public void readParameters() {

        ArrayList<Calculations> calcus = new ArrayList<>();
        calcus.add(new Calculations());
        calcus.add(new Calculations());
        calcus.add(new Calculations());


        String aaString;
        String baString;
        String aeString;
        String beString;
        String depthString;

        try (FileReader fr = new FileReader(fileName); BufferedReader br = new BufferedReader(fr)) {

            for (int i = 0; i < 3; i++) {

                aaString = br.readLine();
                baString = br.readLine();
                aeString = br.readLine();
                beString = br.readLine();
                depthString = br.readLine();

                calcus.get(i).setAa(Double.parseDouble(aaString));
                calcus.get(i).setBa(Double.parseDouble(baString));
                calcus.get(i).setAe(Double.parseDouble(aeString));
                calcus.get(i).setBe(Double.parseDouble(beString));
                calcus.get(i).setDepth(Integer.parseInt(depthString));

            }
        } catch (FileNotFoundException fnfEx) {
            fnfEx.printStackTrace();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();

        }

        schedule(calcus);

    }

    /**
     * Calling draw-method when loading data from file.
     *
     * @param calcus Array-list of data for Mandelbrot-sets
     */
    private void drawFromFile(ArrayList<Calculations> calcus) {
        graphics.setCalculations(calcus.get(0));
        graphics.draw();
        calcus.add(calcus.remove(0));
    }

    /**
     * Allows to show 3 Mandelbrot-sets in a row.
     *
     * @param calcus Arrray-list of data for Mandelbrot-sets
     */
    private void schedule(ArrayList<Calculations> calcus) {
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                for (int i = 0; i < 100; i++) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            drawFromFile(calcus);
                        }
                    });

                    Thread.sleep(3200);

                }
                return null;
            }
        };
        th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    /**
     * Drawing Mandelbrot-set with user-parameters
     */
    @FXML
    public void handleUebernehmen() {

        //works only when first load from file
        if (th != null && th.isAlive() && !th.isInterrupted()) {
            th.interrupt();
        }

        int hoehe = (int) canvas.getHeight();
        int breite = (int) canvas.getWidth();
        graphicsContext.clearRect(0, 0, breite, hoehe);


        String aaString = tfAa.getText();
        String baString = tfBa.getText();
        String aeString = tfAe.getText();
        String beString = tfBe.getText();
        String depthString = tfDepth.getText();

        String[] params = {aaString, baString, aeString, beString};

        for (int i = 0; i < 4; i++) {
            if (params[i].isEmpty()) {
                lblErrorParams.setText("Parameter fehlen");
                return;
            }
        }

        if (depthString.isEmpty()) {
            lblErrorParams.setText("Parameter fehlen");
            return;
        } else if (!allFloats(params)) {
            lblErrorParams.setText("Falsche Eingabewerte");
            return;
        } else {

            lblErrorParams.setText("Parameter uebernommen");

            calculations.setAa(Double.parseDouble(aaString));
            calculations.setBa(Double.parseDouble(baString));
            calculations.setAe(Double.parseDouble(aeString));
            calculations.setBe(Double.parseDouble(beString));
            calculations.setDepth(Integer.parseInt(depthString));

            graphics.setCalculations(calculations);
            graphics.draw();
        }

    }

    /**
     * Drawing Mandelbrot-set with parameters from file.
     */
    @FXML
    public void handleLaden() {
        if (th != null && th.isAlive() && !th.isInterrupted()) {
            th.interrupt();
        }
        fileName = tfFileName.getText();

        if (fileName.equals("")) {
            lblErrorLoading.setText("Kein Dateiname eingegeben");
        } else if (!checkIfFileExists(fileName)) {
            lblErrorLoading.setText("Datei nicht gefunden");
        } else {
            lblErrorLoading.setText("");
            readParameters();
        }
    }

    /**
     * Checks if file exists in project directory.
     *
     * @param fileName Filename mit Extension, without path
     * @return true or false
     */
    public static boolean checkIfFileExists(String fileName) {
        boolean fileExists = false;
        String path = null;
        try {
            path = new File("").getCanonicalPath();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        // loop through each of the files looking for filenames that match
        for (int i = 0; i < listOfFiles.length; i++) {
            String filename = listOfFiles[i].getName();
            if (listOfFiles[i].getName().equals(fileName)) {
                fileExists = true;
                break;
            } else {
                fileExists = false;
            }

        }
        return fileExists;
    }

    /**
     * Checks if all entries of an array of strings can be parsed to Float
     *
     * @param floatStrings - Array of floats
     * @return true, if every entry represents a float-number
     */

    public boolean allFloats(String[] floatStrings) {

        for (int i = 0; i < floatStrings.length; i++) {
            if (!floatStrings[i].matches("^([+-]?(\\d+\\.)?\\d+)$")) {
                return false;
            }
        }

        return true;

    }


}
