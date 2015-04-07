package DataModel;
public class Product {
	String service;// Fund,Loan,CD,Bank_Account,Mortgage
	String customer;// {Student,Business,Other,Doctor,Professional
	double fee;// 0.5 - 20
	double ads;// 0 - 10
	String size;// Small,Large,Medium
	String promo;// Web&Email,Full,Web,None
	double interest;// 0 - 100
	double period;// 0 - 20
	public double label;

	public Product(String service, String customer, double fee, double ads,
			String size, String promo, double interest, double period,
			double label) {
		this.service = service;
		this.customer = customer;
		this.fee = fee;
		this.ads = ads;
		this.size = size;
		this.promo = promo;
		this.interest = interest;
		this.period = period;
		this.label = label;
	}

	public double getNormalizedFee() {
		return (fee - 0.5) / 20;
	}

	public double getNormalizedAds() {
		return ads / 10;
	}

	public double getNormalizedInterest() {
		return interest / 100;
	}

	public double getNormalizedPeriod() {
		return period / 20;
	}

	public int getNormalizedService() {
		switch (service) {
		case "Loan":
			return 0;
		case "Bank_Account":
			return 1;
		case "CD":
			return 2;
		case "Mortgage":
			return 3;
		case "Fund":
			return 4;
		default:
			return -1;
		}
	}

	public int getNormalizedCustomer() {
		switch (customer) {
		case "Business":
			return 0;
		case "Professional":
			return 1;
		case "Student":
			return 2;
		case "Doctor":
			return 3;
		case "Other":
			return 4;
		default:
			return -1;
		}
	}

	public int getNormalizedSize() {
		switch (size) {
		case "Small":
			return 0;
		case "Medium":
			return 1;
		case "Large":
			return 2;
		default:
			return -1;
		}
	}

	public int getNormalizedPromo() {
		switch (promo) {
		case "Full":
			return 0;
		case "Web&Email":
			return 1;
		case "Web":
			return 2;
		case "None":
			return 3;
		default:
			return -1;
		}
	}
	
	public String getLabel () {
		if (this.label >= 20) return "1"; 
		else return "0";
	}

	public String toString() {
		return service + "," + customer + "," + fee + "," + ads + "," + size
				+ "," + promo + "," + interest + "," + period + "," + label;
	}

}
