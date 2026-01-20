public class SexDGH extends CategoricalDGH {

    public SexDGH() {
        buildHierarchy();
    }

    @Override
    protected void buildHierarchy() {

        root = new DGHNode("Sex");
        // registerParent(root, root);
        DGHNode male = new DGHNode("Male");
        DGHNode female = new DGHNode("Female");

        root.addChild(male);
        root.addChild(female);

        registerParent(root, male);
        registerParent(root, female);
    }
}
