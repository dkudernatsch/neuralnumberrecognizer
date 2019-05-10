package ObjekErkennung;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageProcessor {
    private final int MAXCOLOR = 200;
    private final int NORMALIZECOLOR = 250;
    private final int MINNUM = 30;
    private final int SEARCHRADIUS = 6;

    private IntImage grayImage;
    private BufferedImage image;

    private ArrayList<IntImage> subImages;
    private ArrayList<Index> indices;
    private ArrayList<BoundingBox> boundingBoxes;


    private ArrayList<Result> resultImages;

    /**
     * Alle Bildmanipulationen finden in dieser Klasse statt.
     *
     * @param image das Bild, dass manipuliert wird.
     */

    public ImageProcessor(BufferedImage image) {
        this.image = image;
        grayImage = new IntImage(image);
        indices = new ArrayList<>();

        /*subImages = new ArrayList<>();
        boundingBoxes = new ArrayList<>();*/
        resultImages = new ArrayList<>();
        grayImage.mask(NORMALIZECOLOR);
        searchDigits(grayImage);
        extractSubImages(grayImage,resultImages);
    }

    /**
     * Führt einen Floodfill-Algorithmus von den Koordinaten <code>x</code>, <code>y</code>  durch.
     * Speichert den Index von allen benachbarten Pixel, die dünkler als MAXCOLOR sind.
     *
     * @param image   Bild in dem gesucht werden soll
     * @param x       Start-X-Koordinate
     * @param y       Start-X-Koordinate
     * @param visited Liste aller bereits besichtigter Pixel, ist am Anfang leer
     * @param indices Liste aller Indices, deren Pixel gefunden wurde
     * @return ArrayList von allen benachbarten Index der Pixel
     */

    private ArrayList<Index> floodFill(IntImage image, int x, int y, boolean[][] visited, ArrayList<Index> indices) {

        if (x < 0 || x >= image.getWidth()) return indices; // return if the index is out of Bounds
        if (y < 0 || y >= image.getHeight()) return indices;
        if (visited[y][x]) return indices;                  // retrun if this Pixel was already visisted
        visited[y][x] = true;                               // if it wasn't set it to visited
        if (image.get(x,y) > MAXCOLOR) return indices;      // return if the color is to light
        Index in = new Index(x, y);
        indices.add(in);

        for (int i = 1; i < SEARCHRADIUS; i++) {            //search in a radius of 5 Pixel for similar ones
            for (int j = 1; j < SEARCHRADIUS; j++) {
                floodFill(image, x + i, y, visited, indices);
                floodFill(image, x + i, y + j, visited, indices);
                floodFill(image, x, y + j, visited, indices);
                floodFill(image, x - i, y + j, visited, indices);
                floodFill(image, x - i, y, visited, indices);
                floodFill(image, x - i, y + j, visited, indices);
                floodFill(image, x, y - j, visited, indices);
                floodFill(image, x - i, y - j, visited, indices);
            }
        }
        return indices;
    }


    /**
     * Erzeugt ein BoundingBox Objekt aus einer ArrayListe von Indices
     *
     * @param indices
     * @return speichert den Max und Min Index in x und y Richtungen
     */
    public BoundingBox getBounds(ArrayList<Index> indices) {
        boolean set = false;
        int minX = 99999;
        int maxX = -1;
        int minY = 99999;
        int maxY = -1;
        System.out.println("Size :" + indices.size() + "-----------------");
        for (Index i : indices) {
            if (i.getX() < minX) {
                set = true;
                minX = i.getX();
            }
            if (i.getX() > maxX) {
                set = true;
                maxX = i.getX();
            }
            if (i.getY() < minY) {
                set = true;
                minY = i.getY();
            }
            if (i.getY() > maxY) {
                set = true;
                maxY = i.getY();
            }
        }
        if (set) {
            indices.clear();
            return new BoundingBox(minX, maxX, minY, maxY);
        } else {
            return null;
        }
    }

    /**
     * Führt einen FloodFill Algorithmus für alle ersten Vorkommnissen von nicht weißen Pixel durch,
     * und erzeugt BoundingBox Objekte für alle zusammenhägenden Pixel.
     * wird in dem Feld boundingBoxes abgespeichert.
     * Bei vorgesehener Verwendung sollte genau eine BoundingBox pro Ziffer im Bild erzeugt werden
     *
     * @param grayImage
     */
    public void searchDigits(IntImage grayImage) {
        for (int i = 0; i < grayImage.getHeight(); i++) {
            for (int j = 0; j < grayImage.getWidth(); j++) {
                if (grayImage.get(j, i) < MAXCOLOR) {
                    BoundingBox b = isInBoundary(j, i);
                    if (b == null) {
                        ArrayList<Index> index = floodFill(grayImage, j, i, new boolean[grayImage.getHeight()][grayImage.getWidth()], indices);
                        if (index.size() > MINNUM) {
                            resultImages.add(new Result(image ,getBounds(index)));
                        }else{
                            index.clear();
                        }
                    } else {
                        j = b.getMaxX();
                    }
                }

            }
        }
    }

    /**
     * Gibt an ob eine Position schon innerhalb einer gefundenen BoundingBox liegt
     *
     * @param x
     * @param y
     * @return Die BoundingBox in der die Position liegt oder null wenn die Position in keiner BoundingBox liegt
     */
    private BoundingBox isInBoundary(int x, int y) {
        for (Result result : resultImages) {
            if (x <= result.getBound().getMaxX() && x >= result.getBound().getMinX() && y <= result.getBound().getMaxY() && y >= result.getBound().getMinY()) {
                return result.getBound();
            }
        }
        return null;
    }

    private void extractSubImages(IntImage grayImage, ArrayList<Result> resultImages) {
        for(Result result: resultImages){
            result.setImage(grayImage.getSubImage(result.getBound()));
        }
    }


    public void maskImages(int mask){
        for(Result result: resultImages) {
            if(result.getImage() != null) {
                result.getImage().mask(mask);
            }
        }
    }

    /*public void maxDarkenImages(){
        for(IntImage img:subImages){
            int[] data = img.getData();
            int max = 0;
            for(int i = 0; i < data.length; i++){
                if(255-data[i] > max){
                    max = 255-data[i];
                }
            }
            double r = (double)255/max;
            for(int i = 0; i< data.length; i++){
                data[i] = (int)(-1*((255-data[i])*r)+255);
            }
        }
    }*/

    /*public void avgDarkenImages(){
        for(IntImage img:subImages){
            int[] data = img.getData();
            int sum = 0;
            int count = 0;
            for(int i = 0; i < data.length; i++){
                if(data[i] < 255){
                    sum += (255-data[i]);
                    count++;
                }
            }
            double avg = (double)sum/count;
            double r = (double)255/avg;
            for(int i = 0; i< data.length; i++){
                data[i] = (int)(-1*((255-data[i])*r)+255) >= 0?(int)(-1*((255-data[i])*r)+255):0;
            }
        }
    }*/

    public void darkenImages(){
        for(Result result : resultImages){

            for(int y = 0; y < result.getImage().getHeight(); y++){
                for(int x = 0; x < result.getImage().getWidth(); x++){
                    if(result.getImage().get(x, y) < 230) result.getImage().set(0, x, y);
                }

            }
        }
    }

//    public ArrayList<IntImage> getSubImages(){
//        return subImages;
//    }

    public void scaleImages(){
        for (Result result: resultImages){
            if(result.getImage().getHeight()<result.getImage().getWidth()){
                double r = (double)20/result.getImage().getWidth();
                result.getImage().resizeBilinearGray(20, (int)(result.getImage().getHeight()*r));
            }else{
                double r = (double)20/result.getImage().getHeight();
                result.getImage().resizeBilinearGray((int)(result.getImage().getWidth()*r), 20);
            }
        }
    }

    public void addBoders(){
        for(Result result: resultImages) {

            IntImage img1 = new IntImage(new int[28 * 28], 28, 28);

            Arrays.fill(img1.getData(), 255);
            int xBorder = (img1.getWidth()  - result.getImage().getWidth())  / 2;
            int yBorder = (img1.getHeight() - result.getImage().getHeight()) / 2;

            for(int y = 0; y<result.getImage().getHeight(); y++){
                for(int x = 0; x < result.getImage().getWidth(); x++){
                    img1.set(result.getImage().get(x,y), xBorder+x, yBorder+y);
                }
            }
            result.setImage(img1);
        }
    }

    public ArrayList<Result> getResultImages() {
        return resultImages;
    }

    public void setResultImages(ArrayList<Result> resultImages) {
        this.resultImages = resultImages;
    }

}
