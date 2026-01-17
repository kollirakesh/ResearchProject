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
}
