import java.util.HashMap;
import java.util.Map;

public abstract class CategoricalDGH {

    protected DGHNode root;

    // leaf -> immediate parent
    protected Map<String, String> parentMap = new HashMap<>();

    protected abstract void buildHierarchy();

    public DGHNode getRoot() {
        return root;
    }

    public Map<String, String> getParentMap() {
        return parentMap;
    }

    protected void registerParent(DGHNode parent, DGHNode child) {
        parentMap.put(child.value, parent.value);
    }

 // -------- HEIGHT LOGIC --------
public int getHeight() {
    return height(root);
}

protected int height(DGHNode node) {
    if (node == null) {
        return 0;
    }

    // Leaf node
    if (node.children.isEmpty()) {
        return 1;
    }

    int maxChildHeight = 0;
    for (DGHNode child : node.children) {
        maxChildHeight = Math.max(maxChildHeight, height(child));
    }

    return 1 + maxChildHeight;
}


}
