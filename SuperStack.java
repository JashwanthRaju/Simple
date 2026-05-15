package comp2402a2;

public interface SuperStack extends Iterable<Integer> {
	public void push(Integer x);
	public Integer pop();
	public void doubleTop();
	public void swapTop();
	public int topKodd(int k);
	public Integer maxDiff();
	public int size();
}
