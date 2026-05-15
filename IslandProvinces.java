package comp2402a5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IslandProvinces {
    /**
     * @param r the reader to read from
     * @param w the writer to write to
     * @throws IOException
     */
    public static void doIt(BufferedReader r, PrintWriter w) throws IOException {

        // Read all input lines (skip empty lines, just in case)
        ArrayList<String> lines = new ArrayList<String>();
        String line;
        while ((line = r.readLine()) != null) {
            line = line.trim();
            if (line.length() == 0) continue;
            lines.add(line);
        }
        if (lines.size() == 0) return;

        int R = lines.size();
        int C = lines.get(0).length();

        // Store grid in a byte[][] for memory efficiency
        byte[][] grid = new byte[R][C];
        for (int i = 0; i < R; i++) {
            String s = lines.get(i);
            // assume all lines same length as per problem statement
            for (int j = 0; j < C; j++) {
                grid[i][j] = (byte)(s.charAt(j) - '0');
            }
        }

        ArrayList<Integer> islandSizes = new ArrayList<Integer>();
        ArrayList<Integer> islandDigitSums = new ArrayList<Integer>();

        // 8-direction offsets
        int[] dr = {-1,-1,-1,0,0,1,1,1};
        int[] dc = {-1,0,1,-1,1,-1,0,1};

        // Find islands via BFS (iterative)
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                if (grid[i][j] == 1) {
                    int size = 0;
                    ArrayDeque<int[]> q = new ArrayDeque<int[]>();
                    q.add(new int[]{i, j});
                    grid[i][j] = 0; // mark visited by clearing

                    while (!q.isEmpty()) {
                        int[] cur = q.poll();
                        int rr = cur[0], cc = cur[1];
                        size++;
                        for (int k = 0; k < 8; k++) {
                            int nr = rr + dr[k];
                            int nc = cc + dc[k];
                            if (nr >= 0 && nr < R && nc >= 0 && nc < C && grid[nr][nc] == 1) {
                                grid[nr][nc] = 0;
                                q.add(new int[]{nr, nc});
                            }
                        }
                    }

                    islandSizes.add(size);
                    islandDigitSums.add(sumDigits(size));
                }
            }
        }

        int n = islandSizes.size();
        if (n == 0) return; // nothing to print (though problem says at least one island exists)

        // Group island indices by digit-sum
        HashMap<Integer, ArrayList<Integer>> groups = new HashMap<Integer, ArrayList<Integer>>();
        for (int idx = 0; idx < n; idx++) {
            int ds = islandDigitSums.get(idx);
            ArrayList<Integer> list = groups.get(ds);
            if (list == null) {
                list = new ArrayList<Integer>();
                groups.put(ds, list);
            }
            list.add(idx);
        }

        // Union-Find (DSU) over islands
        int[] parent = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            rank[i] = 0;
        }

        // union helper
        for (Map.Entry<Integer, ArrayList<Integer>> e : groups.entrySet()) {
            ArrayList<Integer> g = e.getValue();
            if (g.size() <= 1) continue;
            int a = g.get(0);
            for (int k = 1; k < g.size(); k++) {
                int b = g.get(k);
                union(parent, rank, a, b);
            }
        }

        // Aggregate province sizes by root
        HashMap<Integer, Integer> provinceSum = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            int root = find(parent, i);
            int old = provinceSum.containsKey(root) ? provinceSum.get(root) : 0;
            provinceSum.put(root, old + islandSizes.get(i));
        }

        // Collect results and sort
        ArrayList<Integer> result = new ArrayList<Integer>(provinceSum.values());
        Collections.sort(result);

        // Print each size on its own line
        for (int x : result) {
            w.println(x);
        }
    }

    // DSU find with path compression
    private static int find(int[] parent, int x) {
        if (parent[x] != x) parent[x] = find(parent, parent[x]);
        return parent[x];
    }

    // DSU union by rank
    private static void union(int[] parent, int[] rank, int a, int b) {
        int ra = find(parent, a);
        int rb = find(parent, b);
        if (ra == rb) return;
        if (rank[ra] < rank[rb]) {
            parent[ra] = rb;
        } else if (rank[rb] < rank[ra]) {
            parent[rb] = ra;
        } else {
            parent[rb] = ra;
            rank[ra]++;
        }
    }

    // sum of digits (non-recursive as required)
    private static int sumDigits(int x) {
        int s = 0;
        while (x > 0) {
            s += x % 10;
            x /= 10;
        }
        return s;
    }

    /**
     * The driver.  Open a BufferedReader and a PrintWriter, either from System.in
     * and System.out or from filenames specified on the command line, then call doIt.
     * @param args
     */
    public static void main(String[] args) {
        try {
            BufferedReader r;
            PrintWriter w;
            if (args.length == 0) {
                r = new BufferedReader(new InputStreamReader(System.in));
                w = new PrintWriter(System.out);
            } else if (args.length == 1) {
                r = new BufferedReader(new FileReader(args[0]));
                w = new PrintWriter(System.out);
            } else {
                r = new BufferedReader(new FileReader(args[0]));
                w = new PrintWriter(new FileWriter(args[1]));
            }
            long start = System.nanoTime();
            doIt(r, w);
            w.flush();
            long stop = System.nanoTime();
            System.out.println("Execution time: " + 1e-9 * (stop-start));
        } catch (IOException e) {
            System.err.println(e);
            System.exit(-1);
        }
    }
}
