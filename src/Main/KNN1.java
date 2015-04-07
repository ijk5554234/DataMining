package Main;
/**Author: Jike Li
 * Team 16
 * This class is to classify the data for Task11 partA.
 * It will print each column of data in test set with
 * the predicted label(class) 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.lang.Math;

import DataModel.Customer;
import DataModel.DataScanner;
import Similarity.Sim;
import Similarity.SimComparator;
import Similarity.SimMatrix;

public class KNN1 {
	private static final String Training_DATA = "trainProdSelection.arff";
	private static final String New_DATA = "testProdSelection.arff";
	int k;
	ArrayList<Customer> trainData;
	ArrayList<Customer> testData;
	double[] weights;
	SimMatrix matrix;
	String[] predictLabels;

	/*
	 * Input: String, file_path of training data and newData Integer, k as the
	 * number of nearest neighbors
	 */
	public KNN1(String traningData, String newData, int k, double[] weights) {
		DataScanner scanner = new DataScanner();
		this.trainData = scanner.getCustomer(traningData);
		this.testData = scanner.getCustomer(newData);
		this.k = k;
		this.weights = weights;
		matrix = new SimMatrix();
		predictLabels = classify();
	}

	/*
	 * If not input weights of attributes, all attribute will have same weight
	 * as default
	 */
	public KNN1(String traningData, String newData, int k) {
		DataScanner scanner = new DataScanner();
		this.trainData = scanner.getCustomer(traningData);
		this.testData = scanner.getCustomer(newData);
		this.k = k;
		double[] weights = { 1, 1, 1, 1, 1, 1 };
		this.weights = weights;
		matrix = new SimMatrix();
		predictLabels = classify();
	}

	/* Constructor with default Filename
	 */
	public KNN1(int k, double[] weights) {
		DataScanner scanner = new DataScanner();
		this.trainData = scanner.getCustomer(Training_DATA);
		this.testData = scanner.getCustomer(New_DATA);
		this.k = k;
		this.weights = weights;
		matrix = new SimMatrix();
		predictLabels = classify();
	}

	/* This method will run the cross validate with shuffled train data
	  * for 10 times and get the average accuracy.
	  */  
	public double crossValidate(int k) { 
		double sum = 0;
		for (int i = 0 ; i < 20; i++) {
			sum += validate(k);
		}
		return sum / 20;
	}

	// Print the predicted labels with raw data
	public void printPrediction() {
		for (int i = 0; i < testData.size(); i++) {
			System.out.println(testData.get(i) + ", predicted label:"
					+ predictLabels[i]);
		}
	}

	/*
	 * c1 is the new data, c2 is the existed training data array of doubles is
	 * the weight of each attribute
	 */
	private Sim getSimilarity(Customer c1, Customer c2, double[] weights) {
		double d1 = 1 - matrix.getTypeMatrix()[c1.getNormalizedType()][c2
				.getNormalizedType()];
		double d2 = 1 - matrix.getLifeStyleMatrix()[c1.getNormalizedLifeStyle()][c2
				.getNormalizedLifeStyle()];
		double d3 = c1.getNormalizedVacation() - c2.getNormalizedVacation();
		double d4 = c1.getNormalizedCredit() - c2.getNormalizedCredit();
		double d5 = c1.getNormalizedSalary() - c2.getNormalizedSalary();
		double d6 = c1.getNormalizedProperty() - c2.getNormalizedProperty();
		double sum = weights[0] * Math.pow(d1, 2) + weights[1]
				* Math.pow(d2, 2) + weights[2] * Math.pow(d3, 2) + weights[3]
				* Math.pow(d4, 2) + weights[4] * Math.pow(d5, 2) + weights[5]
				* Math.pow(d6, 2);
		double simValue = 1 / Math.sqrt(sum);
		Sim sim = new Sim(c2, simValue);
		return sim;
	}

	// Return the k-nearest neighbor and the similarity
	private Sim[] findNeighbors(Customer c, ArrayList<Customer> trainingData,
			int k) {
		SimComparator sComparator = new SimComparator();
		PriorityQueue<Sim> pq = new PriorityQueue<Sim>(k, sComparator);
		for (Customer td : trainingData) {
			Sim sim = getSimilarity(c, td, weights);
			pq.offer(sim);
		}
		Sim[] neighbors = new Sim[k];
		for (int i = 0; i < k; i++)
			neighbors[i] = pq.poll();
		return neighbors;
	}

	// Return the predicted class of all new data
	private String[] classify(ArrayList<Customer> newData,
			ArrayList<Customer> trainingData) {
		String[] labels = new String[newData.size()];
		for (int i = 0; i < newData.size(); i++) {
			Sim[] kNearest = findNeighbors(newData.get(i), trainingData, k);
			HashMap<String, Double> labelMap = new HashMap<String, Double>();

			// Weighted voting, set similarity as the weight
			for (Sim nb : kNearest) {
				if (labelMap.containsKey(nb.getCustomer().label)) {
					labelMap.put(nb.getCustomer().label,
							labelMap.get(nb.getCustomer().label) + nb.getSim());
				} else {
					labelMap.put(nb.getCustomer().label, nb.getSim());
				}
			}

			// Find the domained label as the class
			String maxLabel = null;
			double maxNum = 0;
			for (String key : labelMap.keySet()) {
				if (labelMap.get(key) > maxNum) {
					maxLabel = key;
					maxNum = labelMap.get(key);
				}
			}
			labels[i] = maxLabel;
		}
		return labels;
	}

	/*
	 * If no input variable, use the defalut testData and trainingData
	 */
	private String[] classify() {
		return classify(testData, trainData);
	}

	// Return the accuracy of predication if the acutal labels are provided
	private double getAccuracy(String[] predictLabels, String[] realLabels) {
		if (predictLabels.length != realLabels.length) return -1;
		double correct = 0;
		double wrong = 0;
		for (int i = 0; i < predictLabels.length; i++) {
			if (predictLabels[i].equals(realLabels[i])) {
				correct++;
			} else
				wrong++;
		}
		//System.out.println("Correct:" + correct + " Wrong:" + wrong);
		double acur = correct / (correct + wrong);
		return acur;
	}
	
	 /*
	 * K-fold cross-validation, return the average accuracy of all the folds.
	 * ArrayList accurs records all the accurracy
	 */
	private double validate(int k) {
		ArrayList<Customer> allData = trainData;
		shuffle(allData);
		ArrayList<Double> accurs = new ArrayList<Double>();
		if (k < 1 || k > allData.size()) return -1;
		int foldLength = allData.size() / k;
		for (int i = 0; i < k; i++) {
			ArrayList<Customer> fold = new ArrayList<Customer>();
			ArrayList<Customer> rest = new ArrayList<Customer>();
			fold.addAll(allData.subList(i * foldLength, (i + 1) * foldLength));
			rest.addAll(allData.subList(0, i * foldLength));
			rest.addAll(allData.subList((i + 1) * foldLength, allData.size()));
			String[] predict = classify(fold, rest);
			String[] real = getLabels(fold);
			accurs.add(getAccuracy(predict, real));
		}
		double accur = 0;
		for (int i = 0; i < accurs.size(); i++) {
			accur += accurs.get(i);
		}
		accur /= accurs.size();
		return accur;
	}

	// Return the array of labels from a ArrayList of Customer
	private String[] getLabels(ArrayList<Customer> cusomters) {
		String[] labels = new String[cusomters.size()];
		for (int i = 0; i < cusomters.size(); i++)
			labels[i] = cusomters.get(i).label;
		return labels;
	}
	
	//Shuffle the data set.
	private void shuffle(ArrayList<Customer> dataSet) {
		for (int i = 0; i < dataSet.size(); i++) {
			int k = rand(0, i);
			Customer temp = dataSet.get(k);
			dataSet.set(k, dataSet.get(i));
			dataSet.set(i, temp);
		}	
	}
	
	//Return a random integer between lower(inclusive) and higher(exclusive);
	private int rand(int lower, int higher) {
		return lower + (int) ((higher -lower + 1) * Math.random());
		
	}

}
