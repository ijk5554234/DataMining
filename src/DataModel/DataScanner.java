package DataModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class DataScanner {
	public ArrayList<Customer> getCustomer(String fileName) {
		ArrayList<Customer> customers = new ArrayList<Customer>();
		Scanner scanner = null;

		try {
			scanner = new Scanner(new File(fileName));
			boolean isData = false;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				// Find the data columns
				if (line.startsWith("@data")) {
					isData = true;
					continue;
				}
				if (!isData || line.length() == 0) continue;
				String[] col = line.split(",");
				String type = col[0];
				String lifeStyle = col[1];
				double vacation = Double.parseDouble(col[2]);
				double eCredit = Double.parseDouble(col[3]);
				double salary = Double.parseDouble(col[4]);
				double property = Double.parseDouble(col[5]);
				String label = col[6];
				Customer customer = new Customer(type, lifeStyle, vacation,
						eCredit, salary, property, label);
				customers.add(customer);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null) scanner.close();
		}
		return customers;
	}

	public ArrayList<Product> getProduct(String fileName) {
		ArrayList<Product> products = new ArrayList<Product>();
		Scanner scanner = null;

		try {
			scanner = new Scanner(new File(fileName));
			boolean isData = false;
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				// Find the data columns
				if (line.startsWith("@data")) {
					isData = true;
					continue;
				}
				if (!isData || line.length() == 0) continue;
				String[] col = line.split(",");

				String service = col[0];
				String customer = col[1];
				double fee = Double.parseDouble(col[2]);
				double ads = Double.parseDouble(col[3]);
				String size = col[4];
				String promo = col[5];
				double interest = Double.parseDouble(col[6]);
				double period = Double.parseDouble(col[7]);
				double label = 0;
				if (col.length > 8) label = Double.parseDouble(col[8]);

				Product product = new Product(service, customer, fee, ads,
						size, promo, interest, period, label);
				products.add(product);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null) scanner.close();
		}
		return products;
	}

	// Get all the possibel labels
	public String[] getLabels(String fileName) {
		Scanner scanner = null;
		String[] labels;

		try {
			scanner = new Scanner(new File(fileName));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				// @attribute label {C1,C2,C3,C4,C5}
				if (line.startsWith("@attribute label") ||
						line.startsWith("@attribute Label") 	) {
					int l = line.indexOf("{");
					int r = line.indexOf("}");
					String subline = line.substring(l + 1, r);
					labels = subline.split(",");
					return labels;
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null) scanner.close();
		}
		return null;

	}

	// Get all theattributes
	public HashMap<String, String[]> getAttributes(String fileName) {
		Scanner scanner = null;
		HashMap<String, String[]> attrs = new HashMap<String, String[]>();

		try {
			scanner = new Scanner(new File(fileName));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("@attribute")) {
					if (line.contains("Label") ||
							line.contains("label")) break;

					// This is a real type attribute
					if (line.endsWith("real")) {
						int l = line.indexOf("e") + 1;
						int r = line.lastIndexOf("r");
						String currAttr = line.substring(l, r).trim();
						attrs.put(currAttr, null);
					} else { // This is a discrete type attribute
						int first = line.indexOf("e") + 1;
						int l = line.indexOf("{");
						int r = line.indexOf("}");
						String currAttr = line.substring(first, l).trim();
						String subline = line.substring(l + 1, r).trim();
						String[] attrTypes = subline.split(",");
						attrs.put(currAttr, attrTypes);
					}
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null) scanner.close();
		}
		return attrs;
	}
	
	public String[] getAttributesArray(String fileName) {
		Scanner scanner = null;
		ArrayList<String> attrs = new ArrayList<String>();

		try {
			scanner = new Scanner(new File(fileName));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.startsWith("@attribute")) {
					if (line.contains("label") ||
							line.contains("Label") ) break;

					String currAttr = null;
					// This is a real type attribute
					if (line.endsWith("real")) {
						int l = line.indexOf("e") + 1;
						int r = line.lastIndexOf("r");
						currAttr = line.substring(l, r).trim();
					} else { // This is a discrete type attribute
						int l = line.indexOf("e") + 1;
						int r = line.indexOf("{");
						currAttr = line.substring(l, r).trim();
					}
					attrs.add(currAttr);
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Cannot find the file");
		} finally {
			if (scanner != null) scanner.close();
		}
		return (String[]) attrs.toArray(new String[1]);
	}

}
