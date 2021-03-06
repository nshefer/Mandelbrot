package mandelbrot;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.*;


/**
 * This class is responsible for graphical view of the class.
 */
public class Graphics {

    /**
     * Calculations needed to draw Mandelbrot-set.
     */
    private Calculations calculations;

    /**
     * Colors, in which Mandelbrot-set should be drawn.
     */
    private Color[] colors;

    /**
     * Canvas, that is got from FXML. It's needed to draw on it.
     */
    private Canvas canvas;

    /**
     * GraphicsContext of the FXML canvas.
     */
    private GraphicsContext graphicsContext;

    /**
     * Constructor of the class.
     *
     * @param canvas       FXML canvas
     * @param calculations calculations, needed to draw Mandelbrot-set
     */
    public Graphics(Canvas canvas, Calculations calculations) {
        this.canvas = canvas;
        graphicsContext = canvas.getGraphicsContext2D();
        this.calculations = calculations;
        //setColors(calculations.getDepth());
    }

    /**
     * "Constructor" for the colors, in which Mandelbrot-set will be drawn.
     * Each array-item represents an iteration depth of Mandelbrot-set (modulo).
     */
    /*
    private void setColors() {
        colors = new Color[23];

        colors[0] = DARKMAGENTA;
        colors[1] = DARKOLIVEGREEN;
        colors[2] = DARKORANGE;
        colors[3] = DARKORCHID;
        colors[4] = DARKRED;
        colors[5] = DARKSALMON;
        colors[6] = DARKSEAGREEN;
        colors[7] = DARKSLATEBLUE;
        colors[8] = DARKSLATEGRAY;
        colors[9] = DARKSLATEGREY;
        colors[10] = DARKTURQUOISE;
        colors[11] = DARKVIOLET;
        colors[12] = DEEPPINK;
        colors[13] = DEEPSKYBLUE;
        colors[14] = DIMGRAY;
        colors[15] = DIMGREY;
        colors[16] = DODGERBLUE;
        colors[17] = FIREBRICK;
        colors[18] = FLORALWHITE;
        colors[19] = FORESTGREEN;
        colors[20] = FUCHSIA;
        colors[21] = GAINSBORO;
        colors[22] = INDIGO;

    }
    */

    /**
     * sets colors seperatly for 3 ranges within the color-array
     * @param depth - depth of recursion
     */
    private void setColors(int depth){
        
        colors = new Color[depth];
        
        int b1 = depth/15;
        int b2 = depth/2;
        
        
        for(int i = 0; i < b1; i++){
            colors[i] = colorGradient(i, 0, b1, 0);
        }
        
        for(int i = b1; i < b2; i++){
            colors[i] = colorGradient(i, b1, b2, 1);
        }
        
        for(int i = b2; i < depth; i++){
            colors[i] = colorGradient(i, b2, depth, 2);
        }

    }

    /**
     * calculates a color for a given index i between upper and lower
     *
     * @param i - index in color-array
     * @param lower - lower bound for this gradient
     * @param higher - upper bound for this gradient
     * @param range - index for different ranges in color-array
     * @return Color of color-array index i
     */
    private Color colorGradient(int i, int lower, int higher, int range){
        
        Color result;
        
        double ratio1 = (double)(i-lower)/(double)(higher - lower);
        double ratio2 = 1 - ratio1;
      
        int c1 = (int)(255*ratio1);
        int c2 = (int)(255*ratio2);
        
        if(range == 0){
            return (Color.rgb(c2, c2-c2/4, c2));
        }else if(range == 1){
            return (Color.rgb(c2, c2-c2/4, c2));
        }else if(range == 2){
            return (Color.rgb(c2, c2-c2/4, c2));
        } else {
            return (Color.rgb(1,1,1));
        }
        
    }

    /**
     * Setter for Calculations
     *
     * @param calculations calculations, needed for Mandelbrot-set
     */
    public void setCalculations(Calculations calculations) {
        this.calculations = calculations;
    }

    /**
     * Function to draw Mandelbrot-set. It goes through each pixel of the screen and
     * colors it with the according color from "colors" array.
     */
    public void draw() {

        setColors(calculations.getDepth());

        double aa = calculations.getAa();
        double ba = calculations.getBa();
        double ae = calculations.getAe();
        double be = calculations.getBe();

        int width = (int) canvas.getWidth();
        int height = (int) canvas.getHeight();

        // "Man wählt einen Punkt C aus der komplexen Ebene.
        // C ist nun eine komplexe Zahl,
        // mit dem Realteil als x-Koordinate und dem Imaginärteil als y-Koordinate: c = x + i*y."
        // real and imaginary parts of screen point

        PixelWriter pw = canvas.getGraphicsContext2D().getPixelWriter();
        int colorCode;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                colorCode = calculations.isElement(x, y, width, height) % 22;
                pw.setColor(x, y, colors[colorCode]);
            }
        }

    }
}

