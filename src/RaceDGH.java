public class RaceDGH extends CategoricalDGH {

    public RaceDGH() {
        buildHierarchy();
    }

    @Override
    protected void buildHierarchy() {

        root = new DGHNode("Race");
        registerParent(root, root);
        DGHNode white = new DGHNode("White");
        DGHNode black = new DGHNode("Black");
        DGHNode asian = new DGHNode("Asian");
        DGHNode other = new DGHNode("Other");

        root.addChild(white);
        root.addChild(black);
        root.addChild(asian);
        root.addChild(other);

        DGHNode asianPac = new DGHNode("Asian-Pac-Islander");
        asian.addChild(asianPac);

        DGHNode amerIndian = new DGHNode("Amer-Indian-Eskimo");
        other.addChild(amerIndian);

        registerParent(asian, asianPac);
        registerParent(other, amerIndian);
    }
}
