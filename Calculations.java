package mandelbrot;


/**
 * Created by Nati on 22.06.2017.
 */
public class Calculations {
    /**
     * Mandelbrot-Set should be "applied to an interval [Ca; Ce].
     * aa is real part of left, opening border of Ca.
     */
    private double aa;

    /**
     * Mandelbrot-Set should be "applied to an interval [Ca; Ce].
     * ba is imaginary part of left, opening border of Ca.
     */
    private double ba;

    /**
     * Mandelbrot-Set should be "applied to an interval [Ca; Ce].
     * ae is real part of right, closing border of interval - Ce.
     */
    private double ae;

    /**
     * Mandelbrot-Set should be "applied to an interval [Ca; Ce].
     * be is imaginary part of right, closing border of interval - Ce.
     */
    private double be;

    /**
     * Depth of iterations. (= "Iterationstiefe")
     */
    private int depth;

    /**
     * Getter for aa
     * @return aa
     */
    public double getAa(){
        return aa;
    }

    /**
     * Getter for ba
     * @return ba
     */
    public double getBa(){
        return ba;
    }

    /**
     * Getter for ae
     * @return ae
     */
    public double getAe(){
        return ae;
    }

    /**
     * Getter for be
     * @return be
     */
    public double getBe(){
        return be;
    }

    /**
     * Setter for aa
     * @param aa - real part of ca
     */
    public void setAa(double aa){
        this.aa = aa;
    }

    /**
     * Setter for ba
     * @param ba - imaginary part of ca
     */
    public void setBa(double ba){
        this.ba = ba;
    }

    /**
     * Setter for ae
     * @param ae - real part of ce
     */
    public void setAe(double ae){
        this.ae = ae;
    }

    /**
     * Setter for be
     * @param be - imaginary part of ce
     */
    public void setBe(double be){
        this.be = be;
    }

    /**
     * Setter for depth
     * @param depth - Iteration depth
     */
    public void setDepth(int depth){
        this.depth = depth;
    }

    /**
     * This function says if the point is in Mandelbrot-Set or not. The point is in Set, if depth-1 is returned.
     * But if the function escapes from if-case, we need to know on which iteration it happened.
     * This will help us to make set colorful.
     * PS this function is simply "Nochmals die drei Schritte zusammengefasst, ergibt sich fuer das Verfahren: ..."
     *
     * @return number of a color in which the point should be drawn (not color itself!)
     */
    public int isElement(int x, int y, int width, int height) {
        double a = calculateA(width, x);
        double b = calculateB(height, y);
        double zx = 0, zx2 = 0, zy = 0;
        for (int i = 0; i < depth; i++) {
            zx2 = zx * zx - zy * zy + a;
            zy = 2 * zx * zy + b;
            zx = zx2;

            if (zx * zx + zy * zy > 4) {
                return i;
            }
        }
        if (depth!=0){
            // depth-1, because arrays are 0-based
            return depth - 1;
        } else {
            return 0;
        }
    }

    /**
     * A is a double number used in isElement(int, int, int, int)
     * @param width - Width of the canvas
     * @param x - x-coordinate on the canvas
     * @return A
     */
    private double calculateA(int width, int x){
        return ((ae - aa) / width) * x + aa;
    }

    /**
     * B is a double number used in isElement(int, int, int, int)
     * @param height - height of the canvas
     * @param y - y-coordinate on the canvas
     * @return B
     */
    private double calculateB(int height, int y){
        return ((be - ba) / height) * y + ba;
    }
}
