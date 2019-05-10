package NeuronalesNetz;

import java.io.*;


/**
 * Liest die Daten aus den MNIST Datenbank-Datein aus um sie NN-freundlich darzustellen
 * Zusätzlich gibt es
 */



public class MnistLoader{
    private final String TRAINING_LABLES = "Database/train-labels.idx1-ubyte";
    private final String TRAINING_IMAGES = "Database/train-images.idx3-ubyte";

    private final String TEST_LABELS = "Database/t10k-labels.idx1-ubyte";
    private final String TEST_IMAGES = "Database/t10k-images.idx3-ubyte";

    public MnistLoader(){

    }

    /*public static void  main(String[] args) throws Exception{
        MnistLoader m = new MnistLoader();
        byte[][] b = readImages(m.TRAINING_IMAGES);
        double[][] d = m.normalize(b);

        printDigit(0, d);

        System.out.print("a");
    }*/

    public double[][] getTrainingsImages(){
        try {
            return normalize(readImages(TRAINING_IMAGES));
        }catch (Exception e){
            return null;
        }
    }

    public  double[][] getTrainingsLabels(){
        try {
            return readLabels(TRAINING_LABLES);
        }catch (Exception e){
            return null;
        }
    }

    public double[][] getTestImages(){
        try {
            return normalize(readImages(TEST_IMAGES));
        }catch (Exception e){
            return null;
        }
    }

    public  double[][] getTestLabels(){
        try {
            return readLabels(TEST_LABELS);
        }catch (Exception e){
            return null;
        }
    }

    private byte[][] readImages(String file) throws IOException{
        FileInputStream f = null;

        int magicNumber;        //0-3 byte
        int size;               //4-7 byte
        int rows;               //8-11
        int columns;            //12-15
        byte[][]images;         //jedes n�chste byte ein pixel, jedes bild enh�lt rows*columns px, es gibt size viele bilder

        try {
            f = new FileInputStream(file);
        }catch (FileNotFoundException e) {
            System.err.println("File: " + file + " not found.");
        }
        final byte[] integer = new byte[4];

        f.read(integer);
        magicNumber = byteArrayToInt(integer);

        if(magicNumber != 2051){
            throw new IOException("Not a valid file");
        }

        f.read(integer);
        size = byteArrayToInt(integer);

        f.read(integer);
        rows = byteArrayToInt(integer);

        f.read(integer);
        columns = byteArrayToInt(integer);

        images = new byte[size][rows*columns];


        int nrImg = 0;

        while(nrImg < size){

            byte[] image = new byte[rows*columns];
            f.read(image);
            images[nrImg] = image;
            if(images[nrImg][0] == -1){
                return images;
            }

            nrImg++;
        }
        return images;
    }

    private double[][] readLabels(String file) throws  IOException{
        FileInputStream f = null;

        int magicNumber;
        int length;
        double[][] labels;
        byte[] integer = new byte[4];
        f = new FileInputStream(new File(file));

        f.read(integer);
        magicNumber = byteArrayToInt(integer);

        if(magicNumber != 2049){
            throw new RuntimeException("Wrong File");
        }
        f.read(integer);
        length = byteArrayToInt(integer);

        labels = new double[length][10];
        for(int i = 0; i < labels.length; i++){
            double[] label = new double[10];
            label[f.read() & 0xff] = 1;
            labels[i] = label;
        }
        return labels;

    }

    private int byteArrayToInt(byte[] b) {
        if (b.length == 4)
            return b[0] << 24 | (b[1] & 0xff) << 16 | (b[2] & 0xff) << 8
                    | (b[3] & 0xff);
        else if (b.length == 2)
            return 0x00 << 24 | 0x00 << 16 | (b[0] & 0xff) << 8 | (b[1] & 0xff);

        return 0;
    }

    public static void printDigit(int dig, double[][] img){
       printDigit(img[dig]);
    }

    public static void printDigit(double[] img){
        System.out.println(imageToString(img));
    }

    private double[][] normalize(byte[][] img){
        double[][] dImg = new double[img.length][img[0].length];
        for(int i = 0; i < img.length; i++){
            for(int j = 0; j < img[i].length; j++){
                dImg[i][j] = (double)( (int) img[i][j]& 0xff)/255;
            }
        }
        return dImg;
    }
    
    private double[] normalize(byte[] img){
        double[] dImg = new double[img.length];
        for(int i = 0; i < img.length; i++){
                dImg[i] = (double)( (int) img[i]& 0xff)/255;
        }
        return dImg;
    }
    
    public static String imageToString(double[] img){
        String s = "";
        for(int i = 0; i < img.length; i++){
            if(img[i] < .1) {
                s += " ";
            }else if(img[i] < .5){
                s+=".";
            }else if (img[i] < .7){
                s+="*";
            }else{
                s+="#";
            }
            if(i%28 == 27){
                s+="\n";
            }
        }
        return s;
    }

//    public byte[] readArrayOfBytes(FileInputStream f, int len){
//        byte[] byteArray = new byte[len];
//        try {
//            for(int i =0; i < len; i++){
//                byteArray[i] = f.read();        //
//            }
//        }catch (IOException e){
//
//        }
//
//    }

}

