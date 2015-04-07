package DataModel;

import java.util.HashMap;

public class Attributes {
	
	public HashMap<String, String[]> getCustomerAttributes () {
		HashMap<String, String[]> attrs = new HashMap<String, String[]>();
		String[] types = {"student","engineer","librarian","professor","doctor"};
		String[] lifeStyles = {"spend<<saving", "spend<saving", "spend>saving", "spend>>saving"};
		attrs.put("type", types);
		attrs.put("lifeStyle", lifeStyles);
		attrs.put("vacation", null);
		attrs.put("eCredit", null);
		attrs.put("salary", null);
		attrs.put("property", null);
		
		
		return attrs;
	}

}
