package ObjekErkennung;

/**
 * Created by dkude on 27.04.2016.
 */
public class Index {
    /**
     * Repräsentiert einen Punkt in einem zwei-Dimensionalen Koordinatensystem
     */

    private int x;
    private int y;

    public Index(int x, int y){
        setX(x);
        setY(y);
    }


    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
}
