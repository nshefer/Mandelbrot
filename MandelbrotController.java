package mandelbrot;


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
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author Baerbel
 */
public class MandelbrotController implements Initializable {
    
    @ FXML TextField tfAa;
    @ FXML TextField tfBa;
    @ FXML TextField tfAe;
    @ FXML TextField tfBe;
    @ FXML TextField tfDepth;   
    @ FXML TextField tfFileName;
    @ FXML Button btUebernehmen;
    @ FXML Button btLaden;
    @ FXML Label lblErrorLoading;
    @ FXML Label lblErrorParams;
    
    @ FXML AnchorPane apMandelbrot;
    @ FXML Canvas canvas;
    private Calculations calculations = new Calculations();
    private Graphics graphics;// = new Graphics(canvas, calculations);
    private GraphicsContext graphicsContext; //= canvas.getGraphicsContext2D();
   
    
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
        
        //canvas.widthProperty().bind(apMandelbrot.widthProperty());
        //canvas.heightProperty().bind(apMandelbrot.heightProperty());
        
    }  
    
    public void draw(ActionEvent ae){
        if(ae.getSource() == btUebernehmen){
            handleUebernehmen();
        }else if(ae.getSource() == btLaden){
            handleLaden();
        }
    }
    
    public void readParameters(){
        
        String aaString = "";
        String baString = "";
        String aeString = "";
        String beString = "";
        String depthString = "";
        
        //File file = new File(fileName);
        
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
        /*    
        } catch (InterruptedException intEx){
            intEx.getMessage();
*/
        }
       
    }
    
    @ FXML 
    public void handleUebernehmen(){
        
        int hoehe = (int) canvas.getHeight();
        int breite = (int) canvas.getWidth();
        graphicsContext.clearRect(0, 0, breite, hoehe);
        
        String aaString = tfAa.getText();
        String baString = tfBa.getText();
        String aeString = tfAe.getText();
        String beString = tfBe.getText();
        String depthString = tfDepth.getText();
        //System.out.println(aaString);
        
        if(aaString.isEmpty() 
                || baString.isEmpty()
                || aeString.isEmpty()
                || beString.isEmpty()
                || depthString.isEmpty()){
            
            lblErrorParams.setText("Parameter fehlen");
            
        }else{
        
            calculations.setAa(Double.parseDouble(aaString));
            calculations.setBa(Double.parseDouble(baString));
            calculations.setAe(Double.parseDouble(aeString));
            calculations.setBe(Double.parseDouble(beString));
            calculations.setDepth(Integer.parseInt(depthString));

            graphics.setCalculations(calculations);
            graphics.draw();//(calculations);
            
        }
    }
    
    @ FXML 
    public void handleLaden(){   
        
        fileName = tfFileName.getText();
        
        if(fileName.equals("")){
            lblErrorLoading.setText("Kein Dateiname eingegeben");           
        } else if(!checkIfFileExists(fileName)){
            lblErrorLoading.setText("Datei nicht gefunden");        
        } else {
            lblErrorLoading.setText("");
            readParameters();            
        }
    }
    
    /**
     * Ueberprueft ob der File existiert in Projekt Directory.
     * @param file Filename mit Extension, den Pfad braucht man nicht
     * @return true or false
     */
    public static boolean checkIfFileExists(String fileName) {
        boolean fileExists=false;
        String path = null;
        try {
            path = new File("").getCanonicalPath();
        } catch (IOException ioEx) {
            //do smth
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
    
    
}
