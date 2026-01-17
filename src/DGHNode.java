import java.util.ArrayList;
import java.util.List;

public class DGHNode {
    public String value;
    public DGHNode parent;
    public List<DGHNode> children;

    public DGHNode(String value) {
        this.value = value;
        this.children = new ArrayList<>();
    }

    public void addChild(DGHNode child) {
        child.parent = this;
        this.children.add(child);
    }

    @Override
    public String toString() {
        return value;
    }
}
