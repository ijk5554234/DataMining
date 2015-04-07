package DataModel;
public class Customer {
	String type; // student,engineer,librarian,professor,doctor
	String lifeStyle; // spend<<saving,spend<saving,spend>saving,spend>>saving
	double vacation; // 0 每 60
	double eCredit; // 5 每 3000
	double salary; // 10.0 每 40.0.
	double property; // 0.00 每 20.00
	public String label; // C1,C2,C3,C4,C5

	public Customer(String type, String lifeStyle, double vacation,
			double eCredit, double salary, double property, String label) {
		this.type = type;
		this.lifeStyle = lifeStyle;
		this.vacation = vacation;
		this.eCredit = eCredit;
		this.salary = salary;
		this.property = property;
		this.label = label;
	}

	// Unclassified customer
	public Customer(String type, String lifeStyle, double vacation,
			double eCredit, double salary, double property) {
		this.type = type;
		this.lifeStyle = lifeStyle;
		this.vacation = vacation;
		this.eCredit = eCredit;
		this.salary = salary;
		this.property = property;
	}

	public double getNormalizedVacation() {
		return vacation / 60;
	}

	public double getNormalizedCredit() {
		return (eCredit - 5) / (3000 - 5);
	}

	public double getNormalizedSalary() {
		return (salary) - 10 / (40 - 10);
	}

	public double getNormalizedProperty() {
		return property / 20;
	}

	//student: 0, engineer: 1, librarian: 2, professor: 3,doctor: 4
	public int getNormalizedType() {
		switch (type) {
		case "student":
			return 0;
		case "engineer":
			return 1;
		case "librarian":
			return 2;
		case "professor":
			return 3;
		case "doctor":
			return 4;
		default:
			return -1;
		}
	}
	//spend<<saving: 0, spend<saving: 1, spend>saving: 2, spend>>saving: 3
	public int getNormalizedLifeStyle() {
		switch (lifeStyle) {
		case "spend<<saving":
			return 0;
		case "spend<saving":
			return 1;
		case "spend>saving":
			return 2;
		case "spend>>saving":
			return 3;
		default:
			return -1;
		}
	}

	public String getLabel() {
		return label;
	}

	public String toString() {
		return type + "," + lifeStyle + ", " + vacation + "," + eCredit + ","
				+ salary + "," + property + "," + label;
	}

}
