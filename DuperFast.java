package comp2402a2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class DuperFast implements DuperDeque {
    protected ArrayList<Integer> front;
    protected ArrayList<Integer> back;

    protected ArrayList<Integer> frontCumEven, backCumEven;
    protected ArrayList<Integer> frontMin, backMin;
    protected ArrayList<Integer> frontMax, backMax;

    public DuperFast() {
        front = new ArrayList<>();
        back = new ArrayList<>();
        frontCumEven = new ArrayList<>();
        backCumEven = new ArrayList<>();
        frontMin = new ArrayList<>();
        backMin = new ArrayList<>();
        frontMax = new ArrayList<>();
        backMax = new ArrayList<>();
    }


    protected void appendFront(Integer x) {
        front.add(x);
        if (frontCumEven.isEmpty()) {
            frontCumEven.add((x % 2 == 0) ? 1 : 0);
            frontMin.add(x);
            frontMax.add(x);
        } else {
            int prevCum = frontCumEven.get(frontCumEven.size() - 1);
            frontCumEven.add(prevCum + ((x % 2 == 0) ? 1 : 0));
            frontMin.add(Math.min(frontMin.get(frontMin.size() - 1), x));
            frontMax.add(Math.max(frontMax.get(frontMax.size() - 1), x));
        }
    }

    protected void appendBack(Integer x) {
        back.add(x);
        if (backCumEven.isEmpty()) {
            backCumEven.add((x % 2 == 0) ? 1 : 0);
            backMin.add(x);
            backMax.add(x);
        } else {
            int prev = backCumEven.get(backCumEven.size() - 1);
            backCumEven.add(prev + ((x % 2 == 0) ? 1 : 0));
            backMin.add(Math.min(backMin.get(backMin.size() - 1), x));
            backMax.add(Math.max(backMax.get(backMax.size() - 1), x));
        }
    }

    protected Integer removeLastFront() {
        if (front.isEmpty()) return null;
        int n = front.size();
        Integer v = front.remove(n - 1);
        frontCumEven.remove(n - 1);
        frontMin.remove(n - 1);
        frontMax.remove(n - 1);
        return v;
    }

    protected Integer removeLastBack() {
        if (back.isEmpty()) return null;
        int n = back.size();
        Integer v = back.remove(n - 1);
        backCumEven.remove(n - 1);
        backMin.remove(n - 1);
        backMax.remove(n - 1);
        return v;
    }

    protected void rebuildFromCombined(ArrayList<Integer> all) {
        int n = all.size();
        int f = n / 2;
        front.clear();
        back.clear();
        frontCumEven.clear();
        backCumEven.clear();
        frontMin.clear();
        backMin.clear();
        frontMax.clear();
        backMax.clear();
        for (int i = 0; i < f; i++) {
            int idx = f - 1 - i;
            appendFront(all.get(idx));
        }
        for (int i = f; i < n; i++) {
            appendBack(all.get(i));
        }
    }

    protected void rebalanceIfNeeded() {
        if (front.isEmpty() && !back.isEmpty()) {
            ArrayList<Integer> all = new ArrayList<>(back.size());
            for (int i = 0; i < back.size(); i++) all.add(back.get(i));
            rebuildFromCombined(all);
        } else if (back.isEmpty() && !front.isEmpty()) {
            ArrayList<Integer> all = new ArrayList<>(front.size());
            for (int i = front.size() - 1; i >= 0; i--) all.add(front.get(i));
            rebuildFromCombined(all);
        }
    }

    public void addFirst(Integer x) {
        appendFront(x);
    }

    public void addLast(Integer x) {
        appendBack(x);
    }

    public Integer removeFirst() {
        if (size() == 0) return null;
        if (!front.isEmpty()) {
            return removeLastFront();
        } else {
            rebalanceIfNeeded();
            if (!front.isEmpty()) return removeLastFront();
            if (!back.isEmpty()) {
                Integer v = back.remove(0);
                backCumEven.clear();
                backMin.clear();
                backMax.clear();
                return v;
            }
            return null;
        }
    }

    public Integer removeLast() {
        if (size() == 0) return null;
        if (!back.isEmpty()) {
            return removeLastBack();
        } else {
            rebalanceIfNeeded();
            if (!back.isEmpty()) return removeLastBack();
            if (!front.isEmpty()) {
                Integer v = front.remove(0);
                frontCumEven.clear();
                frontMin.clear();
                frontMax.clear();
                return v;
            }
            return null;
        }
    }

    public void swapEnds() {
        if (size() <= 1) return;
        rebalanceIfNeeded();
        if (front.isEmpty() || back.isEmpty()) {
            ArrayList<Integer> all = new ArrayList<>(size());
            for (int i = front.size() - 1; i >= 0; i--) all.add(front.get(i));
            for (int i = 0; i < back.size(); i++) all.add(back.get(i));
            rebuildFromCombined(all);
        }
        int fi = front.size() - 1;
        int bi = back.size() - 1;
        Integer a = front.get(fi);
        Integer b = back.get(bi);
        front.set(fi, b);
        back.set(bi, a);

        if (fi == 0) {
            frontCumEven.set(0, (front.get(0) % 2 == 0) ? 1 : 0);
            frontMin.set(0, front.get(0));
            frontMax.set(0, front.get(0));
        } else {
            int prevCum = frontCumEven.get(fi - 1);
            frontCumEven.set(fi, prevCum + ((front.get(fi) % 2 == 0) ? 1 : 0));
            frontMin.set(fi, Math.min(frontMin.get(fi - 1), front.get(fi)));
            frontMax.set(fi, Math.max(frontMax.get(fi - 1), front.get(fi)));
        }

        if (bi == 0) {
            backCumEven.set(0, (back.get(0) % 2 == 0) ? 1 : 0);
            backMin.set(0, back.get(0));
            backMax.set(0, back.get(0));
        } else {
            int prevCum = backCumEven.get(bi - 1);
            backCumEven.set(bi, prevCum + ((back.get(bi) % 2 == 0) ? 1 : 0));
            backMin.set(bi, Math.min(backMin.get(bi - 1), back.get(bi)));
            backMax.set(bi, Math.max(backMax.get(bi - 1), back.get(bi)));
        }
    }

    public int firstKeven(int k) {
        if (k <= 0 || size() == 0) return 0;
        int fs = front.size();
        if (k <= fs) {
            int totalFront = frontCumEven.get(fs - 1);
            int idxBefore = fs - k - 1;
            int before = (idxBefore >= 0) ? frontCumEven.get(idxBefore) : 0;
            return totalFront - before;
        } else {
            int total = 0;
            if (fs > 0) total += frontCumEven.get(fs - 1);
            int need = k - fs;
            if (need > 0) {
                if (back.size() == 0) return total;
                int m = Math.min(need, back.size());
                total += backCumEven.get(m - 1);
            }
            return total;
        }
    }

    public int lastKeven(int k) {
        if (k <= 0 || size() == 0) return 0;
        int bs = back.size();
        if (k <= bs) {
            int totalBack = backCumEven.get(bs - 1);
            int beforeIdx = bs - k - 1;
            int before = (beforeIdx >= 0) ? backCumEven.get(beforeIdx) : 0;
            return totalBack - before;
        } else {
            int total = 0;
            if (bs > 0) total += backCumEven.get(bs - 1);
            int need = k - bs;
            if (need > 0) {
                if (front.size() == 0) return total;
                int m = Math.min(need, front.size());
                total += frontCumEven.get(m - 1);
            }
            return total;
        }
    }

    public Integer maxDiff() {
        if (size() < 2) return null;
        Integer mn = null, mx = null;
        if (!frontMin.isEmpty()) {
            mn = frontMin.get(frontMin.size() - 1);
            mx = frontMax.get(frontMax.size() - 1);
        }
        if (!backMin.isEmpty()) {
            int bmn = backMin.get(backMin.size() - 1);
            int bmx = backMax.get(backMax.size() - 1);
            if (mn == null || bmn < mn) mn = bmn;
            if (mx == null || bmx > mx) mx = bmx;
        }
        if (mn == null || mx == null) return null;
        return Math.abs(mx - mn);
    }

    public int size() {
        return front.size() + back.size();
    }

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            int idxFront = front.size() - 1;
            int idxBack = 0;

            public boolean hasNext() {
                return idxFront >= 0 || idxBack < back.size();
            }

            public Integer next() {
                if (idxFront >= 0) {
                    return front.get(idxFront--);
                } else if (idxBack < back.size()) {
                    return back.get(idxBack++);
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }
}
