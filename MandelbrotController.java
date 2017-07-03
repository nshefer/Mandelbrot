package mandelbrot;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * FXML MandelbrotController class
 *
 * @author Baerbel
 */
public class MandelbrotController implements Initializable {

    @FXML
    TextField tfAa;
    @ FXML TextField tfBa;
    @ FXML TextField tfAe;
    @ FXML TextField tfBe;
    @ FXML TextField tfDepth;
    @ FXML TextField tfFileName;
    @ FXML
    Button btUebernehmen;
    @ FXML Button btLaden;

    @ FXML
    AnchorPane apMandelbrot;
    @ FXML
    Canvas canvas;
    private Calculations calculations = new Calculations();
    private Graphics graphics;
    private GraphicsContext graphicsContext; //= canvas.getGraphicsContext2D();
    private Thread th;
    private String fileName;




    public void setGraphicsContext(GraphicsContext graphicsContext){
        this.graphicsContext = graphicsContext;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        graphics = new Graphics(canvas, calculations);
        graphicsContext = canvas.getGraphicsContext2D();
    }

    public void draw(ActionEvent ae){
        if(ae.getSource() == btUebernehmen){
            handleUebernehmen();
        }else if(ae.getSource() == btLaden){
            handleLaden();
        }
    }
    /*
    public void readParameters(){



        String aaString = "";
        String baString = "";
        String aeString = "";
        String beString = "";
        String depthString = "";

        try (FileReader fr = new FileReader(fileName); BufferedReader br = new BufferedReader(fr)) {

            for (int i = 0; i < 3; i++){

                aaString = br.readLine();
                baString = br.readLine();
                aeString = br.readLine();
                beString = br.readLine();
                depthString = br.readLine();

                calculations.setAa(Double.parseDouble(aaString));
                calculations.setBa(Double.parseDouble(baString));
                calculations.setAe(Double.parseDouble(aeString));
                calculations.setBe(Double.parseDouble(beString));
                calculations.setDepth(Integer.parseInt(depthString));

                graphics.setCalculations(calculations);
                graphics.draw();//(calculations);
                //wait(60);

            }
        } catch (FileNotFoundException fnfEx) {
            fnfEx.printStackTrace();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
            //} catch (InterruptedException intEx){
            //intEx.getMessage();
        }
    }
    */
    public void readParameters(){

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

            for (int i = 0; i < 3; i++){

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

    private void drawFromFile(ArrayList<Calculations> calcus){
            graphics.setCalculations(calcus.get(0));
            graphics.draw();//(calculations);
            calcus.add(calcus.remove(0));
    }

    private void schedule(ArrayList<Calculations> calcus){
        Task task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                for (int i = 0; i <100; i++) {
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

    };




    @ FXML
    public void handleUebernehmen(){
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
        //System.out.println(aaString);

        calculations.setAa(Double.parseDouble(aaString));
        calculations.setBa(Double.parseDouble(baString));
        calculations.setAe(Double.parseDouble(aeString));
        calculations.setBe(Double.parseDouble(beString));
        calculations.setDepth(Integer.parseInt(depthString));

        graphics.setCalculations(calculations);
        graphics.draw();//(calculations);
    }

    @ FXML
    public void handleLaden(){
        if (th != null && th.isAlive() && !th.isInterrupted()) {
            th.interrupt();
        }
        fileName = tfFileName.getText();
        readParameters();
    }


}
