package ObjekErkennung;
import java.awt.image.BufferedImage;

public class Result {
    /**
     * Repräsentetiert eine gefundene Ziffer und speichert alle ihre relevanten Daten ab
     */

    //Bild woher es kommt
    private BufferedImage parent;
    //IntImage repräsentation der Ziffer
    private IntImage image;
    //BoundingBox der Ziffer
    private BoundingBox bound;
    //Index der rechten Oberen Ecke
    private Index upperRightCorner;
    //Klassifizierung der Ziffer
    private double[] classificationArray;
    private int calsssification;
    
    
    public Result(){}

    public double[] getClassificationArray() {
        return classificationArray;
    }

    public void setClassificationArray(double[] classificationArray) {
        this.classificationArray = classificationArray;
    }

    public int getCalsssification() {
        return calsssification;
    }

    public void setCalsssification(int calsssification) {
        this.calsssification = calsssification;
    }

    public Result(BufferedImage parent){
        setParent(parent);
    }

    public Result(BufferedImage parent, BoundingBox bound){
        setParent(parent);
        setBound(bound);
        setUpperRightCorner( new Index(bound.getMinX(), getBound().getMinY()));
    }

    public BufferedImage getParent() {
        return parent;
    }

    public void setParent(BufferedImage parent) {
        this.parent = parent;
    }

    public IntImage getImage() {
        return image;
    }

    public void setImage(IntImage image) {
        this.image = image;
    }

    public BoundingBox getBound() {
        return bound;
    }

    public void setBound(BoundingBox bound) {
        this.bound = bound;
    }

    public Index getUpperRightCorner() {
        return upperRightCorner;
    }

    public void setUpperRightCorner(Index upperRightCorner) {
        this.upperRightCorner = upperRightCorner;
    }
}
