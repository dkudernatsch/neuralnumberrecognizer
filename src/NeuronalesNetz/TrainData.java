package NeuronalesNetz;

import java.util.Random;

/**
 * Repräsentiert TestDaten für eine Neuronales Netz
 * Jeder Eintrag hat Daten, die in ein Neuronales Netz eingespeist werden und ein Label welches dem idealen Output des NNs entspricht.
 */

public class TrainData {
	private double[][] data;
	private double[][] label;
	private int length;
	
	public TrainData(String data){

	/*
		double[][] data = {
				{0,0},
				{1,0},
				{0,1},
				{1,1}

		};
		double[][] label = {
				{0,1},
				{1,0},
				{1,0},
				{0,1}

		};

		double[][] data = {
	            {
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,0,0,0
	             },{
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,0,0,0,1,0,0,0,0,
	                0,0,0,0,0,1,0,0,0,0,
	                0,0,0,0,0,1,0,0,0,0,
	                0,0,0,0,0,1,0,0,0,0,
	                0,0,0,0,0,1,0,0,0,0,
	                0,0,0,0,0,1,0,0,0,0,
	                0,0,0,0,0,1,0,0,0,0,
	                0,0,0,0,0,1,0,0,0,0,
	                0,0,0,0,0,0,0,0,0,0
	             }, {
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,1,0,0,0,0,0,0,0,
	                0,0,1,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,0,0,0
	            }, {
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,0,0,0
	            }, {
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,0,0,0
	            }, {
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,1,0,0,0,0,0,0,0,
	                0,0,1,0,0,0,0,0,0,0,
	                0,0,1,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,0,0,0
	            },{
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,1,0,0,0,0,0,0,0,
	                0,0,1,0,0,0,0,0,0,0,
	                0,0,1,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,0,0,0
	            },{
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,0,0,0
	            },{
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,0,0,0
	            },{
	                0,0,0,0,0,0,0,0,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,0,0,0,0,0,1,0,0,
	                0,0,1,1,1,1,1,1,0,0,
	                0,0,0,0,0,0,0,0,0,0
	            }
	        };

		double[][] label = {
							{1,0,0,0,0,0,0,0,0,0},
							{0,1,0,0,0,0,0,0,0,0},
							{0,0,1,0,0,0,0,0,0,0},
							{0,0,0,1,0,0,0,0,0,0},
							{0,0,0,0,1,0,0,0,0,0},
							{0,0,0,0,0,1,0,0,0,0},
							{0,0,0,0,0,0,1,0,0,0},
							{0,0,0,0,0,0,0,1,0,0},
							{0,0,0,0,0,0,0,0,1,0},
							{0,0,0,0,0,0,0,0,0,1},
						};
	*/
		MnistLoader loader = new MnistLoader();

        if (data.equals("test")) {
            setData(loader.getTestImages());
            setLabel(loader.getTestLabels());
        }else {
            setData(loader.getTrainingsImages());
            setLabel(loader.getTrainingsLabels());
        }
        setLength(this.data.length);
	}

    /**
     * Mischt die TestDaten neu, sollte bei längeren Daten nicht oft verwendent werden, da sehr ineffizient
     */
	public void shuffle(){
		int[] shuffleIndex = new int[length];
		for(int i = 0; i < length; i++){
			shuffleIndex[i] = i;
		}
		shuffleArray(shuffleIndex);
		double[][] data = new double[this.data.length][this.data[1].length];
		double[][]label = new double[this.label.length][this.label[1].length];
		for(int i = 0; i < length; i++){
			data [i] = this.data[shuffleIndex[i]];
			label[i] = this.label[shuffleIndex[i]];
			
			
		}
		this.data = data;
		this.label = label;
	}
	
	private void shuffleArray(int[] array){
	    int index, temp;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--){
	        index = random.nextInt(i + 1);
	        temp = array[index];
	        array[index] = array[i];
	        array[i] = temp;
	    }
	}
	
	
	public void setLength(int length) {
		this.length = length;
	}
	
	public int getLength(){
		return this.length;
	}
	
	public double[] getData(int index){
		return data[index];
	}
	
	public double[][] getData() {
		return data;
	}
	
	public void setData(double[][] data) {
		this.data = data;
	}

	public double[][] getLabel() {
		return label;
	}
	public double[] getLabel(int index){
		return label[index];
	}
	
	public void setLabel(double label[][]) {
		this.label = label;
	}
}
