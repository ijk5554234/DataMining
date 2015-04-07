package Similarity;
import DataModel.Customer;
import DataModel.Product;

/**
 * @author Jike Li Team 16
 * This the Class to record the similarity of two products,
 * containing a reference to refer to the product later.
 */
public class Sim implements Comparable<Sim>{
	private Customer customer;
	private Product product;
	private double sim;

	public Sim(Customer customer, double sim) {
		this.customer = customer;
		this.sim = sim;
	}
	
	public Sim(Product product, double sim) {
		this.product = product;
		this.sim = sim;
	}

	public double getSim() {
		return sim;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public Product getProduct() {
		return product;
	}

	@Override
	public int compareTo(Sim other) {
		// TODO Auto-generated method stub
		if (this.sim >= other.sim) return 1;	
		else return -1;
	}
}
