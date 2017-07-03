package mandelbrot;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import java.awt.image.BufferedImage;
import java.util.Random;

//import java.awt.Color;

/**
 * Created by Nati on 22.06.2017.
 */
public class Graphics {
    private Calculations calculations;
    private Color[] colors;
    private Canvas canvas;
    private GraphicsContext graphicsContext;

    public Graphics(Canvas canvas, Calculations calculations) {
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        this.calculations = calculations;
        setColors();
    }

    private void setColors() {
        colors = new Color[calculations.getDepth() + 1];
        Random random = new Random();
        for (int i = 0; i < calculations.getDepth(); i++){
            colors[i] = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1);
        }

    }
    
    public void setCalculations(Calculations calculations){
        this.calculations = calculations;
    }


/*
    public void draw(double aa, double ba, double ae, double be) {
        // this should be right canvas width
        int width = 600;
        // this should be right canvas height
        int height = 600;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // "Man wählt einen Punkt C aus der komplexen Ebene.
        // C ist nun eine komplexe Zahl,
        // mit dem Realteil als x-Koordinate und dem Imaginärteil als y-Koordinate: c = x + i*y."
        // real and imaginary parts of screen point

        int colorCode;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colorCode = calculations.isElement(x, y, width, height);

                // draw point using the coordinates of a pixel x and y with the color colors[colorCode]
            }
        }

    }
    */
    
    public void draw(){//(Calculations calculations) {
        
        setColors();
        
        double aa = calculations.getAa();
        double ba = calculations.getBa(); 
        double ae = calculations.getAe(); 
        double be = calculations.getBe();
                
        // this should be right canvas width
        int width = (int) canvas.getWidth();
        // this should be right canvas height
        int height = (int) canvas.getHeight();

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // "Man wählt einen Punkt C aus der komplexen Ebene.
        // C ist nun eine komplexe Zahl,
        // mit dem Realteil als x-Koordinate und dem Imaginärteil als y-Koordinate: c = x + i*y."
        // real and imaginary parts of screen point

        PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();
        int colorCode;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colorCode = calculations.isElement(x, y, width, height);
                pw.setColor(x, y,colors[colorCode]);
            }
        }

    }
}

