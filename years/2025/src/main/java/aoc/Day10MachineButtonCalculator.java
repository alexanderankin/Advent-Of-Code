package aoc;

import java.math.BigInteger;
import java.util.*;

public class Day10MachineButtonCalculator {
    public long minPresses(String lightDiagrams) {
        return lightDiagrams.lines().map(LightDiagram::parse).mapToLong(this::minPresses).sum();
    }

    public long minPressesAccurate(String lightDiagrams) {
        return lightDiagrams.lines().map(LightDiagram::parse).mapToLong(this::minPressesAccurate)/*.peek(ignored -> System.out.println("found a new one"))*/.sum();
    }

    long minPressesAccurate(LightDiagram lightDiagram) {
        return new AiCode().minPressesAccurate(lightDiagram);
    }

    long minPresses(LightDiagram lightDiagram) {
        return minPresses(lightDiagram.machine(), 0, new HashMap<>());
    }

    long minPresses(Machine machine, int depth, Map<Integer, Long> cache) {
        int key = Objects.hash(machine.states, depth);
        Long hashResult;
        if ((hashResult = cache.get(key)) != null)
            return hashResult;

        long result;
        if (machine.states().equals(machine.lightDiagram.lightDiagram))
            result = depth;
        else if (depth > 7)
            result = Long.MAX_VALUE;
        else {
            var absMin = Long.MAX_VALUE;
            for (int i = 0; i < machine.lightDiagram.buttonWiringList().size(); i++) {
                var min = minPresses(machine.flip(i), depth + 1, cache);
                if (min < absMin)
                    absMin = min;
            }
            result = absMin;
        }

        cache.put(key, result);
        return result;
    }

    /*
    long minPressesAccurate(LightDiagram lightDiagram) {
        return minPressesAccurate(lightDiagram.accurateMachine(), 0, new HashMap<>());
    }
    */

    // ai code
    /*
    long minPressesAccurate(LightDiagram diagram) {
        var start = diagram.accurateMachine().states();
        var target = diagram.joltageRequirements();

        Queue<List<Integer>> q = new ArrayDeque<>();
        Map<List<Integer>, Integer> dist = new HashMap<>();

        q.add(start);
        dist.put(start, 0);

        while (!q.isEmpty()) {
            var cur = q.poll();
            int d = dist.get(cur);

            if (cur.equals(target)) {
                return d;
            }

            for (var wiring : diagram.buttonWiringList()) {
                var next = new ArrayList<>(cur);
                boolean tooFar = false;

                for (int i : wiring) {
                    int v = next.get(i) + 1;
                    if (v > target.get(i)) {
                        tooFar = true;
                        break;
                    }
                    next.set(i, v);
                }

                if (!tooFar && !dist.containsKey(next)) {
                    dist.put(next, d + 1);
                    q.add(next);
                }
            }
        }

        return Long.MAX_VALUE;
    }
    */
    /*
    long minPressesAccurate(LightDiagram diagram) {
        int n = diagram.joltageRequirements().size();
        int m = diagram.buttonWiringList().size();

        int[] target = diagram.joltageRequirements().stream().mapToInt(Integer::intValue).toArray();

        int[][] wiring = diagram.buttonWiringList().stream()
                .map(l -> l.stream().mapToInt(Integer::intValue).toArray())
                .toArray(int[][]::new);

        // reorder buttons: most constrained first
        Integer[] order = new Integer[m];
        for (int i = 0; i < m; i++) order[i] = i;

        Arrays.sort(order, Comparator.comparingInt(i ->
                Arrays.stream(wiring[i]).map(j -> target[j]).sum()
        ));

        int[][] w = new int[m][];
        for (int i = 0; i < m; i++) w[i] = wiring[order[i]];

        int[] maxPress = new int[m];
        Arrays.fill(maxPress, Integer.MAX_VALUE);
        for (int j = 0; j < m; j++) {
            for (int i : w[j]) {
                maxPress[j] = Math.min(maxPress[j], target[i]);
            }
        }

        int[] accum = new int[n];

        // precompute max coverage per remaining button set
        int[][] maxCover = new int[m + 1][n];
        for (int j = m - 1; j >= 0; j--) {
            maxCover[j] = Arrays.copyOf(maxCover[j + 1], n);
            for (int i : w[j]) {
                maxCover[j][i]++;
            }
        }

        return dfsAccurate(0, w, target, maxPress, accum, 0, Long.MAX_VALUE, maxCover);
    }


    long dfsAccurate(
            int button,
            int[][] wiring,
            int[] target,
            int[] maxPress,
            int[] accum,
            long presses,
            long best,
            int[][] maxCover
    ) {
        if (presses >= best) return best;

        // admissible lower bound
        int lb = 0;
        for (int i = 0; i < target.length; i++) {
            int rem = target[i] - accum[i];
            if (rem > 0) {
                int cap = maxCover[button][i];
                if (cap == 0) return best; // impossible
                lb = Math.max(lb, (rem + cap - 1) / cap);
            }
        }
        if (presses + lb >= best) return best;

        if (button == wiring.length) {
            return presses;
        }

        for (int k = 0; k <= maxPress[button]; k++) {
            boolean ok = true;
            for (int i : wiring[button]) {
                accum[i] += k;
                if (accum[i] > target[i]) ok = false;
            }

            if (ok) {
                best = dfsAccurate(
                        button + 1,
                        wiring,
                        target,
                        maxPress,
                        accum,
                        presses + k,
                        best,
                        maxCover
                );
            }

            for (int i : wiring[button]) {
                accum[i] -= k;
            }

            if (!ok) break; // monotonic overflow
        }

        return best;
    }
    */
    /*
    long minPressesAccurate(LightDiagram d) {
        int n = d.joltageRequirements().size();
        int m = d.buttonWiringList().size();

        // Build matrix A (n x m)
        BigInteger[][] A = new BigInteger[n][m];
        for (int i = 0; i < n; i++) {
            Arrays.fill(A[i], BigInteger.ZERO);
        }

        for (int j = 0; j < m; j++) {
            for (int i : d.buttonWiringList().get(j)) {
                A[i][j] = BigInteger.ONE;
            }
        }

        // Build vector b
        BigInteger[] b = d.joltageRequirements().stream()
                .map(BigInteger::valueOf)
                .toArray(BigInteger[]::new);

        // Solve A x = b
        BigInteger[] x = solveLinearSystem(A, b);
        if (x == null) return Long.MAX_VALUE;

        long sum = 0;
        for (BigInteger v : x) {
            if (v.signum() < 0) return Long.MAX_VALUE;
            sum += v.longValueExact();
        }

        return sum;
    }

    static BigInteger[] solveLinearSystem(BigInteger[][] A, BigInteger[] b) {
        int n = A.length;
        int m = A[0].length;

        // Augmented matrix
        BigInteger[][] M = new BigInteger[n][m + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(A[i], 0, M[i], 0, m);
            M[i][m] = b[i];
        }

        int row = 0;
        for (int col = 0; col < m && row < n; col++) {
            int pivot = row;
            while (pivot < n && M[pivot][col].equals(BigInteger.ZERO)) pivot++;
            if (pivot == n) continue;

            BigInteger[] tmp = M[row];
            M[row] = M[pivot];
            M[pivot] = tmp;

            BigInteger div = M[row][col];
            for (int j = col; j <= m; j++) {
                M[row][j] = M[row][j].divide(div);
            }

            for (int i = 0; i < n; i++) {
                if (i != row && !M[i][col].equals(BigInteger.ZERO)) {
                    BigInteger factor = M[i][col];
                    for (int j = col; j <= m; j++) {
                        M[i][j] = M[i][j].subtract(factor.multiply(M[row][j]));
                    }
                }
            }
            row++;
        }

        BigInteger[] x = new BigInteger[m];
        Arrays.fill(x, BigInteger.ZERO);

        for (int i = 0; i < n; i++) {
            int leading = -1;
            for (int j = 0; j < m; j++) {
                if (!M[i][j].equals(BigInteger.ZERO)) {
                    leading = j;
                    break;
                }
            }
            if (leading == -1) {
                if (!M[i][m].equals(BigInteger.ZERO)) return null;
            } else {
                x[leading] = M[i][m];
            }
        }

        return x;
    }
    */

    /*long minPressesAccurate(LightDiagram d) {
        int n = d.joltageRequirements().size();
        int m = d.buttonWiringList().size();

        int[] target = d.joltageRequirements().stream().mapToInt(Integer::intValue).toArray();
        int[][] wiring = d.buttonWiringList().stream()
                .map(l -> l.stream().mapToInt(Integer::intValue).toArray())
                .toArray(int[][]::new);

        // Upper bound per button
        int[] maxPress = new int[m];
        Arrays.fill(maxPress, Integer.MAX_VALUE);
        for (int j = 0; j < m; j++) {
            for (int i : wiring[j]) {
                maxPress[j] = Math.min(maxPress[j], target[i]);
            }
        }

        // Reorder buttons: most constrained first
        Integer[] order = new Integer[m];
        for (int i = 0; i < m; i++) order[i] = i;

        Arrays.sort(order, Comparator.comparingInt(j ->
                Arrays.stream(wiring[j]).map(i -> target[i]).sum()
        ));

        int[][] w = new int[m][];
        int[] mp = new int[m];
        for (int i = 0; i < m; i++) {
            w[i] = wiring[order[i]];
            mp[i] = maxPress[order[i]];
        }

        int[] accum = new int[n];
        return dfsILP(0, w, target, mp, accum, 0, Long.MAX_VALUE);
    }

    long dfsILP(
            int button,
            int[][] wiring,
            int[] target,
            int[] maxPress,
            int[] accum,
            long presses,
            long best
    ) {
        if (presses >= best) return best;

        // Feasibility lower bound
        long lb = 0;
        for (int i = 0; i < target.length; i++) {
            int rem = target[i] - accum[i];
            if (rem < 0) return best;
            int cover = 0;
            for (int j = button; j < wiring.length; j++) {
                for (int x : wiring[j]) if (x == i) cover++;
            }
            if (cover == 0 && rem > 0) return best;
            if (cover > 0) lb = Math.max(lb, (rem + cover - 1) / cover);
        }
        if (presses + lb >= best) return best;

        if (button == wiring.length) {
            for (int i = 0; i < target.length; i++) {
                if (accum[i] != target[i]) return best;
            }
            return presses;
        }

        for (int k = 0; k <= maxPress[button]; k++) {
            boolean ok = true;
            for (int i : wiring[button]) {
                accum[i] += k;
                if (accum[i] > target[i]) ok = false;
            }

            if (ok) {
                best = dfsILP(
                        button + 1,
                        wiring,
                        target,
                        maxPress,
                        accum,
                        presses + k,
                        best
                );
            }

            for (int i : wiring[button]) {
                accum[i] -= k;
            }

            if (!ok) break;
        }

        return best;
    }*/

    /*
    long minPressesAccurate(LightDiagram d) {
        int n = d.joltageRequirements().size();
        int m = d.buttonWiringList().size();

        int[] b = d.joltageRequirements().stream().mapToInt(Integer::intValue).toArray();

        List<int[]> covers = d.buttonWiringList().stream()
                .map(l -> l.stream().mapToInt(Integer::intValue).toArray())
                .toList();

        // Build light -> buttons incidence
        List<List<Integer>> lightToButtons = new ArrayList<>();
        for (int i = 0; i < n; i++) lightToButtons.add(new ArrayList<>());
        for (int j = 0; j < m; j++) {
            for (int i : covers.get(j)) {
                lightToButtons.get(i).add(j);
            }
        }

        int[] x = new int[m];
        int[] rem = Arrays.copyOf(b, n);

        return solveLights(0, lightToButtons, covers, rem, x);
    }

    long solveLights(
            int light,
            List<List<Integer>> lightToButtons,
            List<int[]> covers,
            int[] rem,
            int[] x
    ) {
        int n = rem.length;
        if (light == n) {
            long sum = 0;
            for (int v : x) sum += v;
            return sum;
        }

        if (rem[light] < 0) return Long.MAX_VALUE;

        List<Integer> buttons = lightToButtons.get(light);
        if (buttons.isEmpty()) {
            return rem[light] == 0 ? solveLights(light + 1, lightToButtons, covers, rem, x)
                                   : Long.MAX_VALUE;
        }

        // Choose first button as pivot
        int pivot = buttons.get(0);

        // Remaining buttons only shift degrees of freedom
        int max = rem[light];
        long best = Long.MAX_VALUE;

        for (int k = 0; k <= max; k++) {
            x[pivot] = k;

            // Apply pivot
            for (int i : covers.get(pivot)) rem[i] -= k;

            long res = solveLights(light + 1, lightToButtons, covers, rem, x);
            best = Math.min(best, res);

            // Undo
            for (int i : covers.get(pivot)) rem[i] += k;

            if (k > best) break;
        }

        x[pivot] = 0;
        return best;
    }
    */
    /*
    long minPressesAccurate(LightDiagram d) {
        int n = d.joltageRequirements().size();
        int m = d.buttonWiringList().size();

        int[] start = d.joltageRequirements().stream().mapToInt(Integer::intValue).toArray();
        int[][] wiring = d.buttonWiringList().stream()
                .map(l -> l.stream().mapToInt(Integer::intValue).toArray())
                .toArray(int[][]::new);

        int[] degree = new int[n];
        for (int[] w : wiring) for (int i : w) degree[i]++;

        record State(int[] r, int g, int f) {}

        PriorityQueue<State> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.f));
        Map<StateKey, Integer> best = new HashMap<>();

        int h0 = heuristic(start, degree);
        pq.add(new State(start, 0, h0));
        best.put(new StateKey(start), 0);

        while (!pq.isEmpty()) {
            State cur = pq.poll();

            if (isZero(cur.r)) return cur.g;

            for (int[] w : wiring) {
                int[] next = cur.r.clone();
                boolean ok = true;
                for (int i : w) {
                    if (--next[i] < 0) {
                        ok = false;
                        break;
                    }
                }
                if (!ok) continue;

                int g2 = cur.g + 1;
                StateKey key = new StateKey(next);

                Integer seen = best.get(key);
                if (seen != null && seen <= g2) continue;

                int f2 = g2 + heuristic(next, degree);
                best.put(key, g2);
                pq.add(new State(next, g2, f2));
            }
        }

        return Long.MAX_VALUE;
    }

    static int heuristic(int[] r, int[] degree) {
        int h = 0;
        for (int i = 0; i < r.length; i++) {
            if (r[i] > 0) {
                h = Math.max(h, (r[i] + degree[i] - 1) / degree[i]);
            }
        }
        return h;
    }

    static boolean isZero(int[] r) {
        for (int v : r) if (v != 0) return false;
        return true;
    }

    static final class StateKey {
        final int[] v;
        final int hash;

        StateKey(int[] v) {
            this.v = v;
            this.hash = Arrays.hashCode(v);
        }

        @Override public boolean equals(Object o) {
            return o instanceof StateKey s && Arrays.equals(v, s.v);
        }

        @Override public int hashCode() {
            return hash;
        }
    }
    */
    /*
    // ======= Accurate solver (memory-bounded IDA*) =======
    long minPressesAccurate(LightDiagram d) {
        int n = d.joltageRequirements().size();
        int m = d.buttonWiringList().size();

        int[] rem = d.joltageRequirements().stream().mapToInt(Integer::intValue).toArray();

        int[][] wiring = d.buttonWiringList().stream()
                .map(l -> l.stream().mapToInt(Integer::intValue).toArray())
                .toArray(int[][]::new);

        // degree[i] = number of buttons that can decrement light i
        int[] degree = new int[n];
        int maxWiringSize = 1;
        for (int[] w : wiring) {
            maxWiringSize = Math.max(maxWiringSize, w.length);
            for (int i : w) degree[i]++;
        }

        // If any light can never be affected, but needs >0, impossible.
        for (int i = 0; i < n; i++) {
            if (degree[i] == 0 && rem[i] != 0) return Long.MAX_VALUE;
        }

        // Static button order helps a lot: try "big impact" buttons first.
        Integer[] order = new Integer[m];
        for (int j = 0; j < m; j++) order[j] = j;
        var finalWiring = wiring;
        Arrays.sort(order, (a, b) -> {
            int da = finalWiring[a].length, db = finalWiring[b].length;
            if (db != da) return Integer.compare(db, da);
            // tiebreak: prefer buttons that touch larger remaining requirements initially
            long sa = 0, sb = 0;
            for (int i : finalWiring[a]) sa += rem[i];
            for (int i : finalWiring[b]) sb += rem[i];
            return Long.compare(sb, sa);
        });
        int[][] w2 = new int[m][];
        for (int k = 0; k < m; k++) w2[k] = wiring[order[k]];
        wiring = w2;

        int threshold = heuristic(rem, degree, maxWiringSize);
        while (true) {
            int next = idaDfs(rem, 0, threshold, wiring, degree, maxWiringSize);
            if (next == FOUND) return threshold;          // found optimal at this threshold
            if (next == Integer.MAX_VALUE) return Long.MAX_VALUE; // no solution
            threshold = next;                              // raise to smallest overrun f
        }
    }

    private static final int FOUND = -1;

    private int idaDfs(
            int[] rem,
            int g,
            int threshold,
            int[][] wiring,
            int[] degree,
            int maxWiringSize
    ) {
        int h = heuristic(rem, degree, maxWiringSize);
        int f = g + h;
        if (f > threshold) return f;
        if (h == 0) return FOUND;

        int minOver = Integer.MAX_VALUE;

        // Expand moves: press one button (subtract 1 from its wired lights).
        for (int[] w : wiring) {
            // quick applicability check: all affected lights must be >0
            boolean ok = true;
            for (int i : w) {
                if (rem[i] == 0) { ok = false; break; }
            }
            if (!ok) continue;

            // apply
            for (int i : w) rem[i]--;

            int t = idaDfs(rem, g + 1, threshold, wiring, degree, maxWiringSize);
            if (t == FOUND) return FOUND;
            if (t < minOver) minOver = t;

            // undo
            for (int i : w) rem[i]++;
        }

        return minOver;
    }

    private int heuristic(int[] rem, int[] degree, int maxWiringSize) {
        int h1 = 0;
        int sum = 0;

        for (int i = 0; i < rem.length; i++) {
            int r = rem[i];
            if (r > 0) {
                sum += r;
                int deg = degree[i];
                // admissible: a press can reduce light i by at most deg(i) per step across all buttons
                h1 = Math.max(h1, (r + deg - 1) / deg);
            }
        }

        // admissible: each press reduces total remaining sum by at most maxWiringSize
        int h2 = (sum + maxWiringSize - 1) / maxWiringSize;

        return Math.max(h1, h2);
    }
    */

    // my code
    long minPressesAccurate(AccurateMachine machine, int depth, Map<Integer, Long> cache) {
        long result;
        if (machine.states().equals(machine.lightDiagram.joltageRequirements))
            result = depth;
        else if (machine.tooFar())
            result = Long.MAX_VALUE;
        else {
            var absMin = Long.MAX_VALUE;
            for (int i = 0; i < machine.lightDiagram.buttonWiringList().size(); i++) {
                long min;

                int key = Objects.hash(machine.states, i);
                Long hashResult;
                if ((hashResult = cache.get(key)) != null)
                    min = hashResult;
                else {

                    machine.addInPlace(i);
                    try {
                        min = minPressesAccurate(machine, depth + 1, cache);
                        cache.put(key, min);
                    } catch (OutOfMemoryError e) {
                        System.out.println();
                        throw new RuntimeException(e);
                    }
                    machine.subtractInPlace(i);
                }

                if (min < absMin)
                    absMin = min;
            }
            result = absMin;
        }

        return result;
    }

    record LightDiagram(List<Boolean> lightDiagram,
                        List<List<Integer>> buttonWiringList,
                        List<Integer> joltageRequirements) {
        static LightDiagram parse(String input) {
            var parts = Arrays.asList(input.split(" "));

            var lightDiagram = Arrays.stream(
                            parts.getFirst().substring(1, parts.getFirst().length() - 1).split(""))
                    .map("#"::equals)
                    .toList();

            var buttonWiringList = parts.subList(1, parts.size() - 1).stream()
                    .map(s -> Arrays.stream(s.substring(1, s.length() - 1).split(",")).map(Integer::parseInt).toList())
                    .toList();

            var joltageRequirements = Arrays.stream(parts.getLast().substring(1, parts.getLast().length() - 1).split(","))
                    .map(Integer::parseInt).toList();

            return new LightDiagram(lightDiagram, buttonWiringList, joltageRequirements);
        }

        Machine machine() {
            return new Machine(new ArrayList<>(Collections.nCopies(lightDiagram.size(), false)), this);
        }

        AccurateMachine accurateMachine() {
            return new AccurateMachine(new ArrayList<>(Collections.nCopies(lightDiagram.size(), 0)), this);
        }
    }

    record Machine(List<Boolean> states, LightDiagram lightDiagram) {
        Machine flip(int wiring) {
            var newLightDiagram = new ArrayList<>(states);
            for (Integer button : lightDiagram.buttonWiringList.get(wiring)) {
                newLightDiagram.set(button, !newLightDiagram.get(button));
            }

            return new Machine(newLightDiagram, lightDiagram);
        }
    }

    record AccurateMachine(List<Integer> states, LightDiagram lightDiagram) {
        AccurateMachine add(int wiring) {
            var newLightDiagram = new ArrayList<>(states);
            for (Integer button : lightDiagram.buttonWiringList.get(wiring)) {
                newLightDiagram.set(button, newLightDiagram.get(button) + 1);
            }

            return new AccurateMachine(newLightDiagram, lightDiagram);
        }

        void addInPlace(int wiring) {
            for (Integer button : lightDiagram.buttonWiringList.get(wiring)) {
                states.set(button, states.get(button) + 1);
            }
        }

        void subtractInPlace(int wiring) {
            for (Integer button : lightDiagram.buttonWiringList.get(wiring)) {
                states.set(button, states.get(button) - 1);
            }
        }

        boolean tooFar() {
            for (int i = 0; i < states.size(); i++) {
                Integer value = states.get(i);
                Integer requirement = lightDiagram.joltageRequirements.get(i);
                if (value > requirement)
                    return true;
            }
            return false;
        }
    }

    // wtf!!!
    // actually interesting to see programming approach for linear programming
    // remember doing this with pencil and paper.
    // definitely want to review to learn how to do ILP in code
    static class AiCode {
        // =======================
        // Accurate solver (exact, fast, memory-safe)
        // Minimizes sum(x) s.t. A x = b, x >= 0 integer
        // =======================

        // ---------- helpers ----------
        private static long gcd(long a, long b) {
            a = Math.abs(a);
            b = Math.abs(b);
            while (b != 0) {
                long t = a % b;
                a = b;
                b = t;
            }
            return a;
        }

        private static long lcmSafe(long a, long b) {
            if (a == 0 || b == 0) return 0;
            long g = gcd(a, b);
            // a/g * b (inputs are small here; this stays well within long for these puzzles)
            return (a / g) * b;
        }

        private static long mod(long x, long m) {
            long r = x % m;
            return r < 0 ? r + m : r;
        }

        long minPressesAccurate(LightDiagram d) {
            int n = d.joltageRequirements().size();
            int m = d.buttonWiringList().size();

            // Build A (n x m) as integers (0/1)
            int[][] A = new int[n][m];
            for (int j = 0; j < m; j++) {
                for (int i : d.buttonWiringList().get(j)) {
                    A[i][j] = 1;
                }
            }

            int[] b = d.joltageRequirements().stream().mapToInt(Integer::intValue).toArray();

            // Per-button upper bound: cannot exceed the smallest requirement among lights it increments
            int[] maxPress = new int[m];
            Arrays.fill(maxPress, Integer.MAX_VALUE);
            for (int j = 0; j < m; j++) {
                boolean touches = false;
                for (int i = 0; i < n; i++) {
                    if (A[i][j] != 0) {
                        touches = true;
                        maxPress[j] = Math.min(maxPress[j], b[i]);
                    }
                }
                if (!touches) maxPress[j] = 0; // button does nothing, only sensible count is 0
            }

            // Solve using exact RREF over rationals, then enumerate free vars with pruning
            return solveMinL1NonnegInteger(A, b, maxPress);
        }

        private long solveMinL1NonnegInteger(int[][] Aint, int[] bInt, int[] maxPress) {
            int n = Aint.length;
            int m = Aint[0].length;

            // Build augmented matrix [A|b] in fractions
            BigFrac[][] M = new BigFrac[n][m + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) M[i][j] = BigFrac.of(Aint[i][j]);
                M[i][m] = BigFrac.of(bInt[i]);
            }

            // RREF
            int[] pivotColForRow = new int[n];
            Arrays.fill(pivotColForRow, -1);
            boolean[] isPivotCol = new boolean[m];

            int row = 0;
            for (int col = 0; col < m && row < n; col++) {
                int piv = -1;
                for (int r = row; r < n; r++) {
                    if (!M[r][col].isZero()) {
                        piv = r;
                        break;
                    }
                }
                if (piv == -1) continue;

                // swap
                if (piv != row) {
                    BigFrac[] tmp = M[piv];
                    M[piv] = M[row];
                    M[row] = tmp;
                }

                // normalize pivot row
                BigFrac pv = M[row][col];
                for (int j = col; j <= m; j++) M[row][j] = M[row][j].div(pv);

                // eliminate other rows
                for (int r = 0; r < n; r++) {
                    if (r == row) continue;
                    if (M[r][col].isZero()) continue;
                    BigFrac factor = M[r][col];
                    for (int j = col; j <= m; j++) {
                        M[r][j] = M[r][j].sub(factor.mul(M[row][j]));
                    }
                }

                pivotColForRow[row] = col;
                isPivotCol[col] = true;
                row++;
            }

            int rank = row;

            // Check consistency: 0 = nonzero
            for (int r = rank; r < n; r++) {
                boolean allZero = true;
                for (int c = 0; c < m; c++) {
                    if (!M[r][c].isZero()) {
                        allZero = false;
                        break;
                    }
                }
                if (allZero && !M[r][m].isZero()) return Long.MAX_VALUE; // no solution
            }

            // Identify free columns
            int[] freeCols = new int[m - rank];
            int f = 0;
            for (int c = 0; c < m; c++) if (!isPivotCol[c]) freeCols[f++] = c;
            f = freeCols.length;

            // Build affine expressions x_j = const[j] + sum_k coeff[j][k] * t_k, t_k = x_freeCols[k]
            BigFrac[] cst = new BigFrac[m];
            BigFrac[][] coeff = new BigFrac[m][f];
            for (int j = 0; j < m; j++) {
                cst[j] = BigFrac.ZERO;
                for (int k = 0; k < f; k++) coeff[j][k] = BigFrac.ZERO;
            }

            // Free vars: x_free = t
            for (int k = 0; k < f; k++) {
                int col = freeCols[k];
                coeff[col][k] = BigFrac.ONE;
            }

            // Pivot vars from RREF rows: x_pivot = b_row - sum free(M[row][free]*t)
            for (int r = 0; r < rank; r++) {
                int pc = pivotColForRow[r];
                cst[pc] = M[r][m];
                for (int k = 0; k < f; k++) {
                    int fc = freeCols[k];
                    // subtract because moving to RHS
                    coeff[pc][k] = M[r][fc].neg();
                }
            }

            // Compute a common denominator L to do fast integer arithmetic in the search
            long L = 1;
            for (int j = 0; j < m; j++) {
                L = lcmSafe(L, cst[j].denAsLong());
                for (int k = 0; k < f; k++) {
                    L = lcmSafe(L, coeff[j][k].denAsLong());
                }
            }

            // Convert to scaled integer forms: X_j = L*x_j = C_j + sum A_jk * t_k
            long[] C = new long[m];
            long[][] A = new long[m][f];
            for (int j = 0; j < m; j++) {
                C[j] = cst[j].mulInt(L).asLongExact();
                for (int k = 0; k < f; k++) {
                    A[j][k] = coeff[j][k].mulInt(L).asLongExact();
                }
            }

            // Bounds on free vars (they are actual press counts)
            int[] ub = new int[f];
            for (int k = 0; k < f; k++) ub[k] = maxPress[freeCols[k]];

            // Prepare pivot list for fast checks
            int[] pivots = Arrays.stream(pivotColForRow).filter(x -> x >= 0).toArray();

            // Precompute:
            // remGcd[pIndex][idx] = gcd(L, |A[p][idx]|, |A[p][idx+1]|, ...)
            // minAdd/maxAdd for pivot bounds pruning given remaining vars
            int pCount = pivots.length;
            long[][] remGcd = new long[pCount][f + 1];
            long[][] minAdd = new long[pCount][f + 1];
            long[][] maxAdd = new long[pCount][f + 1];

            for (int pi = 0; pi < pCount; pi++) {
                int pcol = pivots[pi];

                remGcd[pi][f] = L;
                minAdd[pi][f] = 0;
                maxAdd[pi][f] = 0;

                for (int idx = f - 1; idx >= 0; idx--) {
                    remGcd[pi][idx] = gcd(remGcd[pi][idx + 1], Math.abs(A[pcol][idx]));

                    long coeffK = A[pcol][idx];
                    long lo = 0, hi = 0;
                    if (coeffK >= 0) {
                        lo = 0;
                        hi = coeffK * (long) ub[idx];
                    } else {
                        lo = coeffK * (long) ub[idx];
                        hi = 0;
                    }
                    minAdd[pi][idx] = minAdd[pi][idx + 1] + lo;
                    maxAdd[pi][idx] = maxAdd[pi][idx + 1] + hi;
                }
            }

            // Search order for free vars: smaller ub first tends to reduce branching
            Integer[] order = new Integer[f];
            for (int i = 0; i < f; i++) order[i] = i;
            int[] finalUb = ub;
            Arrays.sort(order, Comparator.comparingInt(i -> finalUb[i]));

            int[] freeOrder = Arrays.stream(order).mapToInt(Integer::intValue).toArray();

            // Reorder ub and A columns accordingly
            int[] ub2 = new int[f];
            for (int i = 0; i < f; i++) ub2[i] = ub[freeOrder[i]];
            ub = ub2;

            long[][] A2 = new long[m][f];
            for (int j = 0; j < m; j++) {
                for (int i = 0; i < f; i++) A2[j][i] = A[j][freeOrder[i]];
            }
            A = A2;

            // Recompute remGcd/minAdd/maxAdd with reordered free vars
            for (int pi = 0; pi < pCount; pi++) {
                int pcol = pivots[pi];

                remGcd[pi][f] = L;
                minAdd[pi][f] = 0;
                maxAdd[pi][f] = 0;

                for (int idx = f - 1; idx >= 0; idx--) {
                    remGcd[pi][idx] = gcd(remGcd[pi][idx + 1], Math.abs(A[pcol][idx]));

                    long coeffK = A[pcol][idx];
                    long lo = 0, hi = 0;
                    if (coeffK >= 0) {
                        lo = 0;
                        hi = coeffK * (long) ub[idx];
                    } else {
                        lo = coeffK * (long) ub[idx];
                        hi = 0;
                    }
                    minAdd[pi][idx] = minAdd[pi][idx + 1] + lo;
                    maxAdd[pi][idx] = maxAdd[pi][idx + 1] + hi;
                }
            }

            // Current scaled pivot values X_p
            long[] curXp = new long[pCount];
            for (int pi = 0; pi < pCount; pi++) curXp[pi] = C[pivots[pi]];

            // Search free vars
            long[] best = new long[]{Long.MAX_VALUE};
            dfsFree(0, ub, C, A, pivots, maxPress, L, curXp, 0L, best, remGcd, minAdd, maxAdd);

            return best[0];
        }

        private void dfsFree(
                int idx,
                int[] ub,
                long[] C,
                long[][] A,
                int[] pivots,
                int[] maxPress,
                long L,
                long[] curXp,
                long sumFree,
                long[] best,
                long[][] remGcd,
                long[][] minAdd,
                long[][] maxAdd
        ) {
            int f = ub.length;
            int pCount = pivots.length;

            // Prune on best possible (even if pivots were 0, sum can't go below sumFree)
            if (sumFree >= best[0]) return;

            // Modular + range feasibility prune for pivots with remaining vars
            for (int pi = 0; pi < pCount; pi++) {
                int pcol = pivots[pi];
                long lo = curXp[pi] + minAdd[pi][idx];
                long hi = curXp[pi] + maxAdd[pi][idx];

                long maxScaled = L * (long) maxPress[pcol];
                if (hi < 0 || lo > maxScaled) return;

                long g = remGcd[pi][idx];
                // Need final X divisible by L; solvable with remaining vars iff currentX ≡ 0 (mod g)
                if (mod(curXp[pi], g) != 0) return;
            }

            if (idx == f) {
                // Evaluate pivots exactly
                long sum = sumFree;

                for (int pi = 0; pi < pCount; pi++) {
                    int pcol = pivots[pi];
                    long X = curXp[pi];

                    if (X < 0) return;
                    long maxScaled = L * (long) maxPress[pcol];
                    if (X > maxScaled) return;
                    if (mod(X, L) != 0) return;

                    long xp = X / L;
                    sum += xp;
                    if (sum >= best[0]) return;
                }

                // Also ensure any non-pivot, non-free columns (shouldn’t exist) are integral and within bounds
                // In our construction, every col is pivot or free.
                best[0] = Math.min(best[0], sum);
                return;
            }

            // Choose t in [0..ub[idx]]; iterate low->high since objective is sumFree + pivots
            // This tends to find good best early.
            for (int t = 0; t <= ub[idx]; t++) {
                long sumFree2 = sumFree + t;
                if (sumFree2 >= best[0]) break;

                // apply contribution to pivot scaled values
                for (int pi = 0; pi < pCount; pi++) {
                    int pcol = pivots[pi];
                    curXp[pi] += A[pcol][idx] * (long) t;
                }

                dfsFree(idx + 1, ub, C, A, pivots, maxPress, L, curXp, sumFree2, best, remGcd, minAdd, maxAdd);

                // undo
                for (int pi = 0; pi < pCount; pi++) {
                    int pcol = pivots[pi];
                    curXp[pi] -= A[pcol][idx] * (long) t;
                }
            }
        }

        // ---------- exact rational arithmetic (BigInteger, reduced) ----------
        static final class BigFrac {
            static final BigFrac ZERO = new BigFrac(BigInteger.ZERO, BigInteger.ONE);
            static final BigFrac ONE = new BigFrac(BigInteger.ONE, BigInteger.ONE);

            final BigInteger n; // numerator
            final BigInteger d; // denominator > 0

            private BigFrac(BigInteger n, BigInteger d) {
                if (d.signum() == 0) throw new ArithmeticException("zero denominator");
                if (d.signum() < 0) {
                    n = n.negate();
                    d = d.negate();
                }
                BigInteger g = n.gcd(d);
                this.n = n.divide(g);
                this.d = d.divide(g);
            }

            static BigFrac of(long v) {
                return new BigFrac(BigInteger.valueOf(v), BigInteger.ONE);
            }

            boolean isZero() {
                return n.signum() == 0;
            }

            BigFrac neg() {
                return new BigFrac(n.negate(), d);
            }

            BigFrac add(BigFrac o) {
                return new BigFrac(n.multiply(o.d).add(o.n.multiply(d)), d.multiply(o.d));
            }

            BigFrac sub(BigFrac o) {
                return new BigFrac(n.multiply(o.d).subtract(o.n.multiply(d)), d.multiply(o.d));
            }

            BigFrac mul(BigFrac o) {
                return new BigFrac(n.multiply(o.n), d.multiply(o.d));
            }

            BigFrac div(BigFrac o) {
                return new BigFrac(n.multiply(o.d), d.multiply(o.n));
            }

            // Multiply by integer L (exact): (n/d)*L = n*(L)/d
            BigFrac mulInt(long L) {
                BigInteger biL = BigInteger.valueOf(L);
                return new BigFrac(n.multiply(biL), d);
            }

            long denAsLong() {
                return d.longValueExact();
            }

            long asLongExact() {
                return n.longValueExact();
            }
        }
    }
}
