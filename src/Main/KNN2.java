package Main;
/**Author: Jike Li
 * Team 16
 * This class is to classify the data for Task11 partB.
 * It will print each column of data in test set with
 * the predicted label(class) 
 */
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.lang.Math;

import DataModel.DataScanner;
import DataModel.Product;
import Similarity.Sim;
import Similarity.SimComparator;
import Similarity.SimMatrix;

public class KNN2 {
	private static final String Training_DATA = "trainProdIntro.real.arff";
	private static final String New_DATA = "testProdIntro.real.arff";
	int k;
	ArrayList<Product> trainData;
	ArrayList<Product> testData;
	double[] weights;
	SimMatrix matrix;
	double[] predictLabels;

	/*
	 * Input: String, file_path of training data and newData Integer, k as the
	 * number of nearest neighbors
	 */
	public KNN2(String traningData, String newData, int k, double[] weights) {
		DataScanner scanner = new DataScanner();
		this.trainData = scanner.getProduct(traningData);
		this.testData = scanner.getProduct(newData);
		this.k = k;
		this.weights = weights;
		matrix = new SimMatrix();
		predictLabels = classify();
	}
	
	public KNN2(int k, double[] weights) {
		DataScanner scanner = new DataScanner();
		this.trainData = scanner.getProduct(Training_DATA);
		this.testData = scanner.getProduct(New_DATA);
		this.k = k;
		this.weights = weights;
		matrix = new SimMatrix();
		predictLabels = classify();
	}

	// Print the predicted labels with raw data
	public void printPrediction() {
		for (int i = 0; i < testData.size(); i++) {
	
			System.out.print(testData.get(i) + ", Predicted label:"
					+ predictLabels[i]);
			if (predictLabels[i] >= 20) System.out.println(" Successful");
			else System.out.println(" Failed");
		}
	}

	/*
	 * c1 is the new data, c2 is the existed training data array of doubles is
	 * the weight of each attribute
	 */
	private Sim getSimilarity(Product p1, Product p2, double[] weights) {
		double d1 = 1 - matrix.getServiceMatrix()[p1.getNormalizedService()][p2
				.getNormalizedService()];
		double d2 = 1 - matrix.getCustomerMatrix()[p1.getNormalizedCustomer()][p2
				.getNormalizedCustomer()];
		double d3 = p1.getNormalizedFee() - p2.getNormalizedFee();
		double d4 = p1.getNormalizedAds() - p2.getNormalizedAds();
		double d5 = 1 - matrix.getSizeMatrix()[p1.getNormalizedSize()][p2
				.getNormalizedSize()];
		double d6 = 1 - matrix.getPromotionMatrix()[p1.getNormalizedPromo()][p2
				.getNormalizedPromo()];
		double d7 = p1.getNormalizedInterest() - p2.getNormalizedInterest();
		double d8 = p1.getNormalizedPeriod() - p2.getNormalizedPeriod();
		double sum = weights[0] * Math.pow(d1, 2) + weights[1]
				* Math.pow(d2, 2) + weights[2] * Math.pow(d3, 2) + weights[3]
				* Math.pow(d4, 2) + weights[4] * Math.pow(d5, 2) + weights[5]
				* Math.pow(d6, 2) + weights[6] * Math.pow(d7, 2) + weights[7]
				* Math.pow(d8, 2);
		double simValue = 1 / Math.sqrt(sum);
		Sim sim = new Sim(p2, simValue);
		return sim;
	}

	// return the k-nearest neighbor and the similarity
	private Sim[] findNeighbors(Product p, ArrayList<Product> trainData, int k) {
		SimComparator sComparator = new SimComparator();
		PriorityQueue<Sim> pq = new PriorityQueue<Sim>(k, sComparator);
		for (Product tp : trainData) {
			Sim sim = getSimilarity(p, tp, weights);
			pq.offer(sim);
		}
		Sim[] neighbors = new Sim[k];
		for (int i = 0; i < k; i++)
			neighbors[i] = pq.poll();
		return neighbors;
	}

	// return the predicted class of all new data
	private double[] classify(ArrayList<Product> newData,
			ArrayList<Product> trainData) {
		double[] labels = new double[newData.size()];
		for (int i = 0; i < newData.size(); i++) {
			Sim[] kNearest = findNeighbors(newData.get(i), trainData, k);
			double weightedVal = 0;
			double sumWeight = 0;
			for (Sim sim : kNearest) {
				weightedVal += (sim.getSim() * sim.getProduct().label);
				sumWeight += sim.getSim();
			}

			DecimalFormat df = new DecimalFormat(".00");
			double formattedLabel = Double.parseDouble(df.format(weightedVal
					/ sumWeight));
			labels[i] = formattedLabel;
		}
		return labels;
	}

	private double[] classify() {
		return classify(testData, trainData);
	}
	
	/*
	 *  Return the accuracy of predication if the acutal labels are provided
	 *  If both predict and real is above or lower than 20, it is a correct
	 *  Predication. Otherwise, it is wrong.
	 */
	private double getAccuracyBinary(double[] predict, double[] real) {
		if (predict.length != real.length) return -1;
		double wrong = 0;
		double correct = 0;
		for (int i = 0; i < predict.length; i++) {
			if ((predict[i] > 20 && real[i] > 20) ||
			(predict[i] < 20 && real[i] < 20))
			correct++;
			else wrong++;
		}
		double acur = correct / (correct + wrong);
		return acur;
	}
	
	 /* This method will run the cross validate with shuffled train data
	  * for 20 times and get the average accuracy.
	  */  
	public double crossValidate(int k) { 
		double sum = 0;
		for (int i = 0 ; i < 20; i++) {
			sum += validate(k);
		}
		return sum / 20;
	}
	
	
	/*
	 * K-fold cross-validation, return the average accuracy of all the folds.
	 * ArrayList accurs records all the accurracy. This is to validate the 
	 * model by binary accuracy.
	 */
	private double validate(int k) {
		ArrayList<Product> allData = trainData;
		shuffle(allData);
		ArrayList<Double> accurs = new ArrayList<Double>();
		if (k < 1 || k > allData.size()) return -1;
		int foldLength = allData.size() / k;
		for (int i = 0; i < k; i++) {
			ArrayList<Product> fold = new ArrayList<Product>();
			ArrayList<Product> rest = new ArrayList<Product>();
			fold.addAll(allData.subList(i * foldLength, (i + 1) * foldLength));
			rest.addAll(allData.subList(0, i * foldLength));
			rest.addAll(allData.subList((i + 1) * foldLength, allData.size()));
			double[] predict = classify(fold, rest);
			double[] real = getLabels(fold);
			accurs.add(getAccuracyBinary(predict, real));
		}
		double accur = 0;
		for (int i = 0; i < accurs.size(); i++) {
			accur += accurs.get(i);
		}
		accur /= accurs.size();
		return accur;
	}
	
	//Shuffle the data set.
	private void shuffle(ArrayList<Product> dataSet) {
		for (int i = 0; i < dataSet.size(); i++) {
			int k = rand(0, i);
			Product temp = dataSet.get(k);
			dataSet.set(k, dataSet.get(i));
			dataSet.set(i, temp);
		}	
	}
	
	//Return a random integer between lower(inclusive) and higher(exclusive);
	private int rand(int lower, int higher) {
		return lower + (int) ((higher - lower + 1) * Math.random());
		
	}

	// Return the array of labels from a ArrayList of Product
	private double[] getLabels(ArrayList<Product> products) {
		double[] labels = new double[products.size()];
		for (int i = 0; i < products.size(); i++)
			labels[i] = products.get(i).label;
		return labels;
	}

}
