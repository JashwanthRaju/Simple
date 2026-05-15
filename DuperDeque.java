package comp2402a2;

public interface DuperDeque extends Iterable<Integer> {
	public void addFirst(Integer x);
	public void addLast(Integer x);
	public Integer removeFirst();
	public Integer removeLast();
	public void swapEnds();
	public int firstKeven(int k);
	public int lastKeven(int k);
	public Integer maxDiff();
	public int size();
}