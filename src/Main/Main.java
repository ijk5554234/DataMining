package Main;
/*
 * Team 16
 * Test the predication here.
 */

public class Main {
	public static void main(String[] args) {
		System.out.println("**********PartA**********");
		PredictCustomer();
		System.out.println("\n\n**********PartB**********");
		PredictProduct();
	}
	
	public static void PredictCustomer() {
		// Set the weight of each attribute here
		double[] weights = { 10.1, 0.5, 19.5, 5.4 , 0.1, 10.6 };

		// Set value of K here
		KNN1 knn = new KNN1(4, weights);
		knn.printPrediction();
		System.out.println("Training data Num:" + knn.trainData.size());
		System.out.println("New data Num:" + knn.testData.size());
		
		// K-folds cross validation, Set k here
		System.out.println("Cross Accuracy:" + knn.crossValidate(5));
	}
	
	public static void PredictProduct() {
		// Set the weight of each attribute here
		double[] weights = { 1, 1, 1, 1, 1, 1, 1, 1 };
		
		// Set the value of K here
		KNN2 knn = new KNN2(5, weights);
		knn.printPrediction();

		System.out.println("Training data Num:" + knn.trainData.size());
		System.out.println("New data Num:" + knn.testData.size());

		// K-folds cross validation, Set k here
		System.out.println("Cross Accuracy:" + knn.crossValidate(5));
	}

}
