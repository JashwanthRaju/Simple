package comp2402a2;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SuperSlow implements SuperStack {
	protected List<Integer> ds;

	public SuperSlow() {
		ds = new ArrayList<>();
	}

	public void push(Integer x) {
		ds.add(x);
	}

	public Integer pop() {
		if(size() <= 0)
			return null;
		else
			return ds.remove(ds.size()-1);
	}

	public void doubleTop(){
		if (size() > 0)
			ds.add(ds.get(ds.size()-1) * 2);
	}

	public void swapTop() {
		if (size() > 1) {
			Integer temp = ds.get(ds.size()-1);
			ds.set(ds.size() - 1, ds.get(ds.size() - 2));
			ds.set(ds.size() - 2, temp);
		}
	}

	public int topKodd(int k) {
		int num_odd = 0;
		for(int i=0; i<k && i<ds.size(); i++)
			if(ds.get(ds.size() - 1 - i) % 2 != 0)
				num_odd++;
		return num_odd;
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
