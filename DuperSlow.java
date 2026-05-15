package comp2402a2;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class DuperSlow implements DuperDeque {
	protected List<Integer> ds;

	public DuperSlow() {
		ds = new ArrayList<>();
	}

	public void addFirst(Integer x) {
		ds.add(0, x);
	}

	public void addLast(Integer x) {
		ds.add(x);
	}

	public Integer removeFirst() {
		if(size() <= 0)
			return null;
		else
			return ds.remove(0);
	}

	public Integer removeLast() {
		if(size() <= 0)
			return null;
		else
			return ds.remove(ds.size()-1);
	}

	public void swapEnds() {
		if (size() > 0) {
			Integer temp = ds.get(0);
			ds.set(0, ds.get(ds.size() - 1));
			ds.set(ds.size() - 1, temp);
		}
	}

	public int firstKeven(int k) {
		int num_even = 0;
		for(int i=0; i<k && i<ds.size(); i++)
			if(ds.get(i) % 2 == 0)
				num_even++;
		return num_even;
	}

	public int lastKeven(int k){
		int num_even = 0;
		for(int i=0; i<k && i<ds.size(); i++)
			if(ds.get(ds.size() - 1 - i) % 2 == 0)
				num_even++;
		return num_even;
	}

	public Integer maxDiff() {
		if(size() < 2)
			return null;
		int max = Integer.MIN_VALUE;
		for (int i =  0; i < ds.size() - 1; i++) {
			for (int j = i+1; j < ds.size(); j++) {
				int  diff = Math.abs(ds.get(i)-ds.get(j));
				if (diff > max)
					max = diff;
			}
		}
		return max;
	}

	public int size() {
		return ds.size();
	}

	public Iterator<Integer> iterator() {
		return ds.iterator();
	}
}