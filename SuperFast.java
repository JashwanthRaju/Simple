package comp2402a2;

import java.util.ArrayList;
import java.util.Iterator;

public class SuperFast implements SuperStack {
    protected ArrayList<Integer> ds;
    protected ArrayList<Integer> cumOdd;
    protected ArrayList<Integer> minPref;
    protected ArrayList<Integer> maxPref;

    public SuperFast() {
        ds = new ArrayList<>();
        cumOdd = new ArrayList<>();
        minPref = new ArrayList<>();
        maxPref = new ArrayList<>();
    }

    public void push(Integer x) {
        ds.add(x);
        if (cumOdd.isEmpty()) {
            cumOdd.add((x % 2 != 0) ? 1 : 0);
            minPref.add(x);
            maxPref.add(x);
        } else {
            cumOdd.add(cumOdd.get(cumOdd.size() - 1) + ((x % 2 != 0) ? 1 : 0));
            minPref.add(Math.min(minPref.get(minPref.size() - 1), x));
            maxPref.add(Math.max(maxPref.get(maxPref.size() - 1), x));
        }
    }

    public Integer pop() {
        if (ds.isEmpty())
            return null;
        int n = ds.size();
        Integer v = ds.remove(n - 1);
        cumOdd.remove(n - 1);
        minPref.remove(n - 1);
        maxPref.remove(n - 1);
        return v;
    }

    public void doubleTop() {
        if (size() <= 0) return;
        Integer x = ds.get(ds.size() - 1);
        push(x * 2);
    }
    public void swapTop() {
        int n = ds.size();
        if (n < 2) return;
        Integer a = ds.get(n - 1);
        Integer b = ds.get(n - 2);
        ds.set(n - 1, b);
        ds.set(n - 2, a);

        if (n == 2) {
            minPref.set(0, ds.get(0));
            maxPref.set(0, ds.get(0));
            cumOdd.set(0, (ds.get(0) % 2 != 0) ? 1 : 0);

            minPref.set(1, Math.min(minPref.get(0), ds.get(1)));
            maxPref.set(1, Math.max(maxPref.get(0), ds.get(1)));
            cumOdd.set(1, cumOdd.get(0) + ((ds.get(1) % 2 != 0) ? 1 : 0));
            return;
        }
        // n >= 3
        int prevIndex = n - 3;
        int prevMin = minPref.get(prevIndex);
        int prevMax = maxPref.get(prevIndex);
        int prevCum = cumOdd.get(prevIndex);

        // index n-2
        int val_n2 = ds.get(n - 2);
        int newMin_n2 = Math.min(prevMin, val_n2);
        int newMax_n2 = Math.max(prevMax, val_n2);
        int newCum_n2 = prevCum + ((val_n2 % 2 != 0) ? 1 : 0);

        minPref.set(n - 2, newMin_n2);
        maxPref.set(n - 2, newMax_n2);
        cumOdd.set(n - 2, newCum_n2);

        // index n-1
        int val_n1 = ds.get(n - 1);
        int newMin_n1 = Math.min(newMin_n2, val_n1);
        int newMax_n1 = Math.max(newMax_n2, val_n1);
        int newCum_n1 = newCum_n2 + ((val_n1 % 2 != 0) ? 1 : 0);

        minPref.set(n - 1, newMin_n1);
        maxPref.set(n - 1, newMax_n1);
        cumOdd.set(n - 1, newCum_n1);
    }

    public Integer maxDiff() {
        if (size() < 2) return null;
        int mn = minPref.get(minPref.size() - 1);
        int mx = maxPref.get(maxPref.size() - 1);
        return Math.abs(mx - mn);
    }

    public int topKodd(int k) {
        if (k <= 0 || size() == 0) return 0;
        int n = size();
        if (k >= n) {
            return cumOdd.get(n - 1);
        } else {
            int total = cumOdd.get(n - 1);
            int before = cumOdd.get(n - 1 - k);
            return total - before;
        }
    }

    public int size() {
        return ds.size();
    }

    public Iterator<Integer> iterator() {
        return ds.iterator();
    }
}
