package ObjekErkennung;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * Ein GraustufenBild
 */
public class IntImage{
    private int[] data;
    private int width;
    private int height;

    public  IntImage(int[] data, int width, int height){
        setHeight(height);
        setWidth(width);
        this.data = data;
    }

    public IntImage(BufferedImage image){
        setWidth(image.getWidth());
        setHeight(image.getHeight());
        data = getGrayImage(image);
    }

    /**
     * Erzeugt ein Integer Array welches die Grauwerte aller Pixel speichert.
     * Alle weiteren Methoden dieser Klasse verlangen ein solches Array
     *
     * @return
     */
    private int[] getGrayImage(BufferedImage image) {
        int[] grayImage = new int[height*width];
        for (int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                grayImage[getDataIndex(x, y)] = getGrayAt(image, x, y);
            }
        }
        return grayImage;
    }

    private int getGrayAt(BufferedImage image, int x, int y) {
        int colorInt = image.getRGB(x, y);
        Color color = new Color(colorInt);
        return (color.getRed() + color.getGreen() + color.getBlue()) / 3;

    }

    public int get(int x, int y){
        if(x < 0 || x >= width){
            throw new ArrayIndexOutOfBoundsException();
        }
        if(y < 0 || y >= height){
            throw new ArrayIndexOutOfBoundsException();
        }
        return data[getDataIndex(x, y)];
    }

    private int getDataIndex(int x, int y){
        return y*(width)+x;
    }

    /**
     *
     * @return Eindimesinales Daten Array des Bildes
     */
    public int[] getData(){
        return data;
    }

    /**
     * Erzuegt ein neues IntImage aus einem unterbereich dieses Bildes
     * @param bound BoundingBox welche ausgeschnitten wird
     * @return Unterbild das erzeugt wird
     */
    public IntImage getSubImage(BoundingBox bound){
        if(bound.getMaxX() >= width || bound.getMaxY() >= height){
            throw new IndexOutOfBoundsException();
        }
        int width1 =  bound.getMaxX()-bound.getMinX()+1;
        int height1 = bound.getMaxY()-bound.getMinY()+1;
        int[] data1 = new int[width1*height1];

        for (int i = 0; i < height1; i++){
            System.arraycopy(data, getDataIndex(bound.getMinX(), i+bound.getMinY()), data1, i*width1, width1);
        }
        return new IntImage(data1, width1, height1);
    }

    /**
     * Skaliert das Bild auf eine gewünschte größe
     * @param w2 neue Breite
     * @param h2 neue Höhe
     */
    public void resizeBilinearGray(int w2, int h2) {
        int[] temp = new int[w2 * h2];
        int A, B, C, D, x, y, index, gray;
        float x_ratio = ((float) (width - 1)) / w2;
        float y_ratio = ((float) (height - 1)) / h2;
        float x_diff, y_diff, ya, yb;
        int offset = 0;
        for (int i = 0; i < h2; i++) {
            for (int j = 0; j < w2; j++) {
                x = (int) (x_ratio * j);
                y = (int) (y_ratio * i);
                x_diff = (x_ratio * j) - x;
                y_diff = (y_ratio * i) - y;
                index = y * width + x;

                // range is 0 to 255 thus bitwise AND with 0xff
                A = data[index] & 0xff;
                B = data[index + 1] & 0xff;
                C = data[index + width] & 0xff;
                D = data[index + width + 1] & 0xff;

                // Y = A(1-w)(1-h) + B(w)(1-h) + C(h)(1-w) + Dwh
                gray = (int) (
                        A * (1 - x_diff) * (1 - y_diff) + B * (x_diff) * (1 - y_diff) +
                                C * (y_diff) * (1 - x_diff) + D * (x_diff * y_diff)
                );
                temp[offset++] = gray;
            }
        }
        data = temp;
        setHeight(h2);
        setWidth(w2);
    }

    /**
     * Erzeugt eine Eindimesionales Double Array aus den Bilddaten, welches für eine Neuronales Netz verwendet werden kann.
     * @return
     */
    public double[] toDoubleArray(){
        double[] d = new double[height*width];
        for(int i = 0; i< d.length; i++){
            d[i]=(double)(255-data[i])/255;
        }
        return d;
    }

    /**
     * Setzt alle Pixel, welche über dem Mask wert liegen auf Weiß
     * @param mask
     */
    public void mask(int mask){
        for (int i = 0; i < data.length; i++) {
            if(data[i] > mask){
                data[i] = 255;
            }
        }
    }

    public String toString(){
        String s= "Image: width= "+getWidth()+"; height= "+getHeight()+"\n\t";
        for(int i = 0; i < data.length; i++){
            if(data[i] >= 220){
                s+=" ";
            }else if(data[i]>=150){
                s+=".";
            }else if(data[i]>=70){
                s+="*";
            }else {
                s+="#";
            }
            if(i%width == width -1){
                s+="\n\t";
            }
        }
        return s;
    }

    public void set(int value,int x, int y){
        data[getDataIndex(x, y)] = value;
    }
    public int[] getRow(int rowNum){
        int[] row = new int[width];
        System.arraycopy(data, getDataIndex(0,rowNum), row, 0, width);
        return row;
    }
    public int[] getColumn(int colNum){
        int[] col = new int[height];
        int index = 0;
        for(int i = colNum; i < height; i+=width+1){
            col[index] = data[i];
            index++;
        }
        return col;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

}
