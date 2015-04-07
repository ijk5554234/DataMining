package Similarity;

/**
 * @author Jike Li Team 16
 * Save Similarity Matrix here
 * 
 */
public class SimMatrix {
	private double[][] type;
	private double[][] lifeStyle;
	private double[][] service;
	private double[][] customer;
	private double[][] size;
	private double[][] promotion;

	public SimMatrix() {
		type = new double[5][5];
		for (int i = 0; i < 5; i++) {
			type[i][i] = 1;
		}
		lifeStyle = new double[4][4];
		for (int i = 0; i < 4; i++) {
			lifeStyle[i][i] = 1;
		}

		/*1 0 0.1 0.3 0.2
		* 0 1 0 0 0
		* 0.1 0 1 0.2 0.2
		* 0.3 0 0.2 1 0.1
		* 0.2 0 0.2 0.1 1
		*/ 
		double[][] service = { { 1, 0, 0.1, 0.3, 0.2 }, { 0, 1, 0, 0, 0 },
				{ 0.1, 0, 1, 0.2, 0.2 }, { 0.3, 0, 0.2, 1, 0.1 },
				{ 0.2, 0, 0.2, 0.1, 1 } };
		this.service = service;

		// 1 0.2 0.1 0.2 0
		// 0.2 1 0.2 0.1 0
		// 0.1 0.2 1 0.1 0
		// 0.2 0.1 0.1 1 0
		// 0 0 0 0 1
		double[][] customer = { { 1, 0.2, 0.1, 0.2, 0 },
				{ 0.2, 1, 0.2, 0.1, 0 }, { 0.1, 0.2, 1, 0.1, 0 },
				{ 0.2, 0.1, 0.1, 1, 0 }, { 0, 0, 0, 0, 1 } };
		this.customer = customer;

		// 1 0.1 0
		// 0.1 1 0.1
		// 0 0.1 1
		double[][] size = { { 1, 0.1, 0 }, { 0.1, 1, 0.1 }, { 0, 0.1, 1 } };
		this.size = size;

		// 1 0.8 0 0
		// 0.8 1 0.1 0.5
		// 0 0.1 1 0.4
		// 0 0.5 0.4 1
		double[][] promotion = { { 1, 0.8, 0, 0 }, { 0.8, 1, 0.1, 0.5 },
				{ 0, 0.1, 1, 0.4 }, { 0, 0.5, 0.4, 1 } };
		this.promotion = promotion;

	}

	public double[][] getTypeMatrix() {
		return type;
	}

	public double[][] getLifeStyleMatrix() {
		return lifeStyle;
	}

	public double[][] getServiceMatrix() {
		return service;
	}

	public double[][] getCustomerMatrix() {
		return customer;
	}

	public double[][] getSizeMatrix() {
		return size;
	}

	public double[][] getPromotionMatrix() {
		return promotion;
	}

}
