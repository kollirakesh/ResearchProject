import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Clusters {

    static final int[] CAT_COLS = {1,2,3,4,5,6,7,9};
    static final Set<Integer> NUM_COLS = Set.of(0,8,10);


    // numeric columns in fixed positions
static final int AGE_COL = 0;
static final int HOURS_COL = 8;
static final int DIST_COL = 10;

// safe delimiter (never appears in Adult dataset)
static final String DELIM = "\u0001";

    public static void generateClusters(int i, int j, List<Map<String, String>> parentsMapList, int n, int level, int maxlevel, List<List<Object>> list, List<DGHNode> rootsList, int k){
        if( (n-i) <= k || level == maxlevel){
            if(i < n){
            List<HashSet<String>> hSets = getListOfUniqueValuesfromIEveryDomain(i, list);
            List<String> lcalist = lcaList(hSets, rootsList);
            generaliseWithLca(i, list, lcalist);
            return ;
            }
        }
        generalise(i, list, parentsMapList);
        HashMap<String, PriorityQueue<ArrayList<Integer>>> hMap = constructHashMap(i, list);
        int index = getJIndex(hMap, k);
        j = n - index;
        arrangeOriginalList(i, j, hMap, list, k);
        generateClusters(j, 0, parentsMapList, n, level + 1, maxlevel, list, rootsList, k);
    }

    private static List<HashSet<String>> getListOfUniqueValuesfromIEveryDomain(int i, List<List<Object>> list) {
    List<HashSet<String>> ls = new ArrayList<>();

    for (int c = 0; c < CAT_COLS.length; c++) {
    int col = CAT_COLS[c];
    HashSet<String> hSet = new HashSet<>();
    for (int q = i; q < list.size(); q++) {
        hSet.add((String) list.get(q).get(col));
    }
    ls.add(hSet);
}

    return ls;
}


    private static List<String> lcaList(List<HashSet<String>> hSets, List<DGHNode> rootsList){
        List<String> ls = new ArrayList<>();
        for(int i=0; i < hSets.size(); i++){
            ls.add(individualLca(hSets.get(i), rootsList.get(i)));
        }
        return ls;
    }
    
     private static String individualLca(HashSet<String> values, DGHNode root) {

    // fallback
    if (values == null || values.isEmpty()) {
        return root.value;
    }

    Iterator<String> it = values.iterator();

    DGHNode firstNode = findNode(root, it.next());
    if (firstNode == null) {
        return root.value;   // value not in DGH → generalize to root
    }

    Set<DGHNode> commonAncestors = getAncestors(firstNode);

    while (it.hasNext()) {
        DGHNode cur = findNode(root, it.next());

        if (cur == null) {
            return root.value; // missing node → safe generalization
        }

        commonAncestors.retainAll(getAncestors(cur));

        if (commonAncestors.isEmpty()) {
            return root.value; // no common ancestor → root
        }
    }

    DGHNode lca = getDeepestNode(commonAncestors);
    return (lca != null) ? lca.value : root.value;
}


    // DFS search
    private static DGHNode findNode(DGHNode root, String value) {
        if (root == null) return null;
        if (root.value.equals(value)) return root;

        for (DGHNode child : root.children) {
            DGHNode found = findNode(child, value);
            if (found != null) return found;
        }
        return null;
    }

    // Collect ancestors including self
    private static Set<DGHNode> getAncestors(DGHNode node) {
        Set<DGHNode> ancestors = new HashSet<>();
        while (node != null) {
            ancestors.add(node);
            node = node.parent;
        }
        return ancestors;
    }

    // Find deepest node using depth
    private static DGHNode getDeepestNode(Set<DGHNode> nodes) {
        DGHNode deepest = null;
        int maxDepth = -1;

        for (DGHNode node : nodes) {
            int depth = 0;
            DGHNode temp = node;
            while (temp.parent != null) {
                depth++;
                temp = temp.parent;
            }

            if (depth > maxDepth) {
                maxDepth = depth;
                deepest = node;
            }
        }
        return deepest;
    }

    private static void generaliseWithLca(int i, List<List<Object>> list, List<String> lcaList){
        for (int p = i; p < list.size(); p++) {
    for (int c = 0; c < CAT_COLS.length; c++) {
        list.get(p).set(CAT_COLS[c], lcaList.get(c));
    }
}

    }

    private static void generalise(
        int i,
        List<List<Object>> list,
        List<Map<String, String>> parentsMapList
) {
    for (int tuple = i; tuple < list.size(); tuple++) {
    for (int c = 0; c < CAT_COLS.length; c++) {
        int colIndex = CAT_COLS[c];
        String key = (String) list.get(tuple).get(colIndex);
        String parent = parentsMapList.get(c).get(key);

        if (parent != null) {
            list.get(tuple).set(colIndex, parent);
        }
    }
}

}


    private static HashMap<String, PriorityQueue<ArrayList<Integer>>>
constructHashMap(int i, List<List<Object>> list) {

    HashMap<String, PriorityQueue<ArrayList<Integer>>> map = new HashMap<>();

    for (int p = i; p < list.size(); p++) {

        StringBuilder key = new StringBuilder();
        ArrayList<Integer> nums = new ArrayList<>(3);

        for (int c : CAT_COLS) {
            key.append((String) list.get(p).get(c)).append(DELIM);
        }

        nums.add((int) list.get(p).get(AGE_COL));
        nums.add((int) list.get(p).get(HOURS_COL));
        nums.add((int) list.get(p).get(DIST_COL));

        map.computeIfAbsent(
                key.toString(),
                k -> new PriorityQueue<>(Comparator.comparingInt(a -> a.get(2)))
        ).add(nums);
    }
    return map;
}

    private static int getJIndex(HashMap<String, PriorityQueue<ArrayList<Integer>>> hMap, int k){
        int sum = 0;
        for(HashMap.Entry<String, PriorityQueue<ArrayList<Integer>>> hEntry: hMap.entrySet()){
            sum += hEntry.getValue().size() % k;
        }
        return sum;
    }

   private static void arrangeOriginalList(
        int i,
        int j,
        HashMap<String, PriorityQueue<ArrayList<Integer>>> hMap,
        List<List<Object>> list,
        int k
) {

    Map<String, PriorityQueue<ArrayList<Integer>>> sortedMap =
            hMap.entrySet()
                    .stream()
                    .sorted(Comparator.comparingInt(e -> e.getValue().size()))
                    .collect(
                            LinkedHashMap::new,
                            (m, e) -> m.put(e.getKey(), e.getValue()),
                            LinkedHashMap::putAll
                    );

    int writeNormal = i;
    int writeOverflow = j;

    for (Map.Entry<String, PriorityQueue<ArrayList<Integer>>> entry : sortedMap.entrySet()) {

        String[] catValues = entry.getKey().split(DELIM, -1);
        PriorityQueue<ArrayList<Integer>> pq = entry.getValue();

        int size = pq.size();
        int rem = size % k;
        int normalCount = size - rem;

        /* ---- normal k-blocks ---- */
        while (normalCount-- > 0) {
            writeRow(list.get(writeNormal++), catValues, pq.poll());
        }

        /* ---- overflow rows ---- */
        while (!pq.isEmpty()) {
            writeRow(list.get(writeOverflow++), catValues, pq.poll());
        }
    }
}

private static void writeRow(
        List<Object> row,
        String[] catValues,
        ArrayList<Integer> nums
) {
    row.set(AGE_COL, nums.get(0));
    row.set(HOURS_COL, nums.get(1));
    row.set(DIST_COL, nums.get(2));

    for (int c = 0; c < CAT_COLS.length; c++) {
        row.set(CAT_COLS[c], catValues[c]);
    }
}

public static void generalizeNumericalAttributes(
        List<List<Object>> table,
        int k
) {
    for (int i = 0; i < table.size(); i += k) {

        int end = Math.min(i + k, table.size());

        int minAge = Integer.MAX_VALUE, maxAge = Integer.MIN_VALUE;
        int minHours = Integer.MAX_VALUE, maxHours = Integer.MIN_VALUE;

        for (int r = i; r < end; r++) {
            int age = (int) table.get(r).get(AGE_COL);
            int hrs = (int) table.get(r).get(HOURS_COL);

            minAge = Math.min(minAge, age);
            maxAge = Math.max(maxAge, age);
            minHours = Math.min(minHours, hrs);
            maxHours = Math.max(maxHours, hrs);
        }

        String ageRange = minAge + "-" + maxAge;
        String hourRange = minHours + "-" + maxHours;

        for (int r = i; r < end; r++) {
            table.get(r).set(AGE_COL, ageRange);
            table.get(r).set(HOURS_COL, hourRange);
        }
    }
}

public static void removeDistanceColumn(List<List<Object>> table) {
    for (List<Object> row : table) {
        row.remove(DIST_COL);
    }
}

public static double numericalInformationLoss(
        List<List<Object>> table,
        int k,
        int globalMinAge,
        int globalMaxAge,
        int globalMinHours,
        int globalMaxHours
) {
    double loss = 0.0;

    for (int i = 0; i < table.size(); i += k) {
        int end = Math.min(i + k, table.size());

        String[] ageRange = table.get(i).get(AGE_COL).toString().split("-");
        String[] hourRange = table.get(i).get(HOURS_COL).toString().split("-");

        int minAge = Integer.parseInt(ageRange[0]);
        int maxAge = Integer.parseInt(ageRange[1]);
        int minHr = Integer.parseInt(hourRange[0]);
        int maxHr = Integer.parseInt(hourRange[1]);

        double ageLoss =
                (double)(maxAge - minAge) / (globalMaxAge - globalMinAge);
        double hourLoss =
                (double)(maxHr - minHr) / (globalMaxHours - globalMinHours);

        loss += (ageLoss + hourLoss) * (end - i);
    }

    return loss / table.size();
}


public static double categoricalInformationLoss(
        List<List<Object>> table,
        List<DGHNode> rootsList
) {
    double loss = 0.0;

    for (List<Object> row : table) {
        double rowLoss = 0.0;

        for (int c = 0; c < CAT_COLS.length; c++) {
            String val = row.get(CAT_COLS[c]).toString();
            DGHNode root = rootsList.get(c);

            DGHNode node = findNode(root, val);

            int leafCountNode = countLeaves(node);
            int leafCountRoot = countLeaves(root);

            rowLoss += (double) leafCountNode / leafCountRoot;
        }
        loss += rowLoss / CAT_COLS.length;
    }

    return loss / table.size();
}

public static double totalInformationLoss(
        double numericalLoss,
        double categoricalLoss
) {
    return (numericalLoss + categoricalLoss) / 2.0;
}
private static int countLeaves(DGHNode node) {
    if (node == null) return 0;
    if (node.children.isEmpty()) return 1;

    int count = 0;
    for (DGHNode child : node.children) {
        count += countLeaves(child);
    }
    return count;

}
}