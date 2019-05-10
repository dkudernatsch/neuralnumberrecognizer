package ObjekErkennung;

/**
 * Created by dkude on 27.04.2016.
 */
public class BoundingBox {

    /**
     * Speichert Minimum und Maximum Werte in X und Y Richtung um ein Rechteck darzustellen
     */


    private int minX;
    private int minY;
    private int maxX;
    private int maxY;

    public BoundingBox(int minX, int maxX, int minY, int maxY){
        setMinX(minX);
        setMinY(minY);
        setMaxX(maxX);
        setMaxY(maxY);
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }



}
