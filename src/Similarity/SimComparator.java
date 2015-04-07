package Similarity;
import java.util.Comparator;
/*
 * This comparator is used for PriorityQueue to find the data with 
 * largest similarity, therefore, the order is in reverse.
 */
public class SimComparator implements Comparator<Sim> {
	@Override

	public int compare(Sim o1, Sim o2) {
		if (o1.getSim() >= o2.getSim())
			return -1;
		else
			return 1;
	}
}
