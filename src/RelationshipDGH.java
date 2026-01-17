public class RelationshipDGH extends CategoricalDGH {

    public RelationshipDGH() {
        buildHierarchy();
    }

    @Override
    protected void buildHierarchy() {

        root = new DGHNode("Relationship");

        DGHNode spouse = new DGHNode("Spouse");
        DGHNode child = new DGHNode("Child");
        DGHNode otherFamily = new DGHNode("Other Family");
        DGHNode nonFamily = new DGHNode("Non-Family");

        root.addChild(spouse);
        root.addChild(child);
        root.addChild(otherFamily);
        root.addChild(nonFamily);

        DGHNode husband = new DGHNode("Husband");
        DGHNode wife = new DGHNode("Wife");
        spouse.addChild(husband);
        spouse.addChild(wife);

        DGHNode ownChild = new DGHNode("Own-child");
        child.addChild(ownChild);

        DGHNode otherRelative = new DGHNode("Other-relative");
        otherFamily.addChild(otherRelative);

        DGHNode notInFamily = new DGHNode("Not-in-family");
        DGHNode unmarried = new DGHNode("Unmarried");
        nonFamily.addChild(notInFamily);
        nonFamily.addChild(unmarried);

        // parent map (leaf -> immediate parent)
        registerParent(spouse, husband);
        registerParent(spouse, wife);
        registerParent(child, ownChild);
        registerParent(otherFamily, otherRelative);
        registerParent(nonFamily, notInFamily);
        registerParent(nonFamily, unmarried);
    }
}
