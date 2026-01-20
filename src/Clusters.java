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
import java.util.StringTokenizer;

public class Clusters {
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

    private static List<HashSet<String>> getListOfUniqueValuesfromIEveryDomain(int i, List<List<Object>> list){
        List<HashSet<String>> ls = new ArrayList<>();

        for(int p=0; p < list.get(i).size(); p++){
            HashSet<String> hSet = new HashSet<>();
            for(int q=i; q < list.size(); q++){
                if(p != 0 && p != 8 && p != 10){
                    String s = (String) (list.get(q).get(p));
                    hSet.add(s);
                }
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
    
     private static String individualLca(HashSet<String> values, DGHNode respectiveRootNode) {

        // At least one value is guaranteed
        Iterator<String> iterator = values.iterator();

        // Get first node and its ancestors
        DGHNode firstNode = findNode(respectiveRootNode, iterator.next());
        Set<DGHNode> commonAncestors = getAncestors(firstNode);

        // Intersect ancestors for remaining values
        while (iterator.hasNext()) {
            DGHNode currentNode = findNode(respectiveRootNode, iterator.next());
            Set<DGHNode> currentAncestors = getAncestors(currentNode);
            commonAncestors.retainAll(currentAncestors);
        }

        // Deepest common ancestor = LCA
        DGHNode lcaNode = getDeepestNode(commonAncestors);

        return lcaNode.value;
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
        for(int p=i; p < list.size(); p++){
            int cols = 0;
            for(int q = 0; q < list.get(p).size(); q++){
                if(q != 0 && q != 8 && q != 10){
                    list.get(p).set(q, lcaList.get(cols));
                    cols++;
                }
            }
        }
    }

    private static void generalise(int i,List<List<Object>> list, List<Map<String, String>> parentsMapList){
        
        for(int tuple = i; tuple < list.size(); tuple++){
            int cols = 0;
            for(int rcell = 0; rcell < list.get(tuple).size(); rcell++){
                if(rcell !=0 && rcell != 8 && rcell != 10){
                    String key = (String)list.get(tuple).get(rcell);
                    list.get(tuple).set(rcell, parentsMapList.get(cols).get(key));
                    cols++;
                }
            }
        }
    }

    private static HashMap<String, PriorityQueue<ArrayList<Integer>>> constructHashMap(int i, List<List<Object>> list){
        HashMap<String, PriorityQueue<ArrayList<Integer>>> map = new HashMap<>();
        for(int p=i; p < list.size(); p++){
            StringBuilder str = new StringBuilder();
            ArrayList<Integer> al = new ArrayList<>();
            for(int q = 0; q < list.get(p).size(); q++){
                if(q != 0 && q != 8 && q != 10){
                    String s = (String)(list.get(p).get(q));
                    str.append(s);
                    str.append(" ");
                }else{
                    al.add((int)(list.get(p).get(q)));
                }
            }
                String key = str.toString();
                if(map.containsKey(key)){
                    map.get(key).add(new ArrayList<>(al));
                }else{
                    PriorityQueue<ArrayList<Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(x -> x.get(x.size() - 1)));
                    pq.add(new ArrayList<>(al));
                    map.put(key, pq);
                }
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

    private static void arrangeOriginalList(int i, int j, HashMap<String, PriorityQueue<ArrayList<Integer>>> hMap, List<List<Object>> list, int k){
       Map<String, PriorityQueue<ArrayList<Integer>>> sortedMap =
        hMap.entrySet()
            .stream()
            .sorted(Comparator.comparingInt(e -> e.getValue().size()))
            .collect(
                LinkedHashMap::new,
                (m, e) -> m.put(e.getKey(), e.getValue()),
                LinkedHashMap::putAll
            );
            int temp1 = i;
            int temp2 = j;
            for(HashMap.Entry<String, PriorityQueue<ArrayList<Integer>>> hEntry: sortedMap.entrySet()){
                String key = hEntry.getKey();
                PriorityQueue<ArrayList<Integer>> pq = hEntry.getValue();
                int pqsize = pq.size();
                while(pqsize >= k){
                    StringTokenizer str = new StringTokenizer(key);
                    ArrayList<Integer> al = pq.poll();
                    for(int p=0; p < list.get(temp1).size(); p++){
                        if(p == 0){
                            list.get(temp1).set(p, al.get(0));
                        }else if(p == 8){
                            list.get(temp1).set(p, al.get(1));
                        }else if(p == 10){
                            list.get(temp1).set(p, al.get(2));
                        }else{
                            list.get(temp1).set(p, str.nextToken());
                        }
                    }
                    temp1++;
                    pqsize--;
                }
                if(pqsize > 0){
                    while(pqsize > 0){
                    StringTokenizer str = new StringTokenizer(key);
                    ArrayList<Integer> al = pq.poll();
                    for(int p=0; p < list.get(temp2).size(); p++){
                        if(p == 0){
                            list.get(temp2).set(p, al.get(0));
                        }else if(p == 8){
                            list.get(temp2).set(p, al.get(1));
                        }else if(p == 10){
                            list.get(temp2).set(p, al.get(2));
                        }else{
                            list.get(temp2).set(p, str.nextToken());
                        }
                    }
                    temp2++;
                    pqsize--;
                    }
                }
            }
    }

}
