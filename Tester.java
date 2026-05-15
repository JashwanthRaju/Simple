package comp2402a2;

import java.util.Iterator;
import java.util.Random;

public class Tester {
	/*
	Set useSeed to true if you want the same values to be
	used in your tests all the time. This may help when
	debugging your solution.
	*/
	static boolean useSeed = false;

	static void showContents(Iterable<Integer> ds) {
		System.out.print("[");
		Iterator<Integer> it = ds.iterator();
		while (it.hasNext()) {
			System.out.print(it.next());
			if (it.hasNext()) {
				System.out.print(",");
			}
		}
		System.out.println("]");
	}

	static void superTest(SuperStack css, int n) {
		System.out.println(css.getClass().getName());
		Random rand;
		if(useSeed)
			rand = new Random(2402);
		else
			rand = new Random();

		for (int i = 0; i < n; i++) {
			int x = rand.nextInt(3*n/2);
			System.out.println("push(" + x + ")");
			css.push(x);
			showContents(css);

			int k = rand.nextInt(css.size()+1);
			System.out.println("topKodd("+ k +") = " + css.topKodd(k));
			System.out.println("maxDiff() = " + css.maxDiff());
		}

		while (css.size() > 0) {
			System.out.println("pop() = " + css.pop());
			showContents(css);
			if(css.size() > 0){
				int k = rand.nextInt(css.size()+1);
				System.out.println("topKodd("+ k +") = " + css.topKodd(k));
				System.out.println("maxDiff() = " + css.maxDiff());
			}
		}
	}

	static void duperTest(DuperDeque wtd, int n) {
		System.out.println(wtd.getClass().getName());
		Random rand;
		if(useSeed)
			rand = new Random(2402);
		else
			rand = new Random();

		for (int i = 0; i < n/2; i++) {
			int x = rand.nextInt(3*n/2);
			if (rand.nextBoolean()) {
				System.out.println("addFirst(" + x + ")");
				wtd.addFirst(x);
			} else {
				System.out.println("addLast(" + x + ")");
				wtd.addLast(x);
			}
			showContents(wtd);
			int k = rand.nextInt(wtd.size()+1);
			if(rand.nextBoolean())
				System.out.println("firstKeven(("+ k +") = " + wtd.firstKeven(k));
			else
				System.out.println("lastKeven("+ k +") = " + wtd.lastKeven(k));
			System.out.println("maxDiff() = " + wtd.maxDiff());
		}

		while (wtd.size() > 0) {
			if (rand.nextBoolean())
				System.out.println("removeFirst() = " + wtd.removeFirst());
			else
				System.out.println("removeLast() = " + wtd.removeLast());
			showContents(wtd);
			if(wtd.size() > 0){
				int k = rand.nextInt(wtd.size()+1);
				if(rand.nextBoolean())
					System.out.println("firstKeven(("+ k +") = " + wtd.firstKeven(k));
				else
					System.out.println("lastKeven("+ k +") = " + wtd.lastKeven(k));
				System.out.println("maxDiff() = " + wtd.maxDiff());
			}
		}
	}

	public static void main(String[] args) {
		superTest(new SuperSlow(), 20);
		duperTest(new DuperSlow(), 20);
		/*
		The next two tests will spit out exception
		until they are fully implemented.
		*/
		superTest(new SuperFast(), 20);
		duperTest(new DuperFast(), 20);
	}
}