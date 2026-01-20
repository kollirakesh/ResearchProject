public class MaritalStatusDGH extends CategoricalDGH {

    public MaritalStatusDGH() {
        buildHierarchy();
    }

    @Override
    protected void buildHierarchy() {

        // Root
        root = new DGHNode("Marital-Status");
        
        // -------- Level 1 --------
        DGHNode neverMarried = new DGHNode("Never Married");
        DGHNode currentlyMarried = new DGHNode("Currently Married");
        DGHNode previouslyMarried = new DGHNode("Previously Married");

        root.addChild(neverMarried);
        root.addChild(currentlyMarried);
        root.addChild(previouslyMarried);

          registerParent(root, neverMarried);
            registerParent(root, currentlyMarried);
              registerParent(root, previouslyMarried);


        // -------- Never Married --------
        DGHNode never = new DGHNode("Never-married");
        neverMarried.addChild(never);

        registerParent(neverMarried, never);

        // -------- Currently Married --------
        DGHNode marriedCiv = new DGHNode("Married-civ-spouse");
        DGHNode marriedAF = new DGHNode("Married-AF-spouse");
        DGHNode marriedAbsent = new DGHNode("Married-spouse-absent");

        currentlyMarried.addChild(marriedCiv);
        currentlyMarried.addChild(marriedAF);
        currentlyMarried.addChild(marriedAbsent);

        registerParent(currentlyMarried, marriedCiv);
        registerParent(currentlyMarried, marriedAF);
        registerParent(currentlyMarried, marriedAbsent);

        // -------- Previously Married --------
        DGHNode divorced = new DGHNode("Divorced");
        DGHNode separated = new DGHNode("Separated");
        DGHNode widowed = new DGHNode("Widowed");

        previouslyMarried.addChild(divorced);
        previouslyMarried.addChild(separated);
        previouslyMarried.addChild(widowed);

        registerParent(previouslyMarried, divorced);
        registerParent(previouslyMarried, separated);
        registerParent(previouslyMarried, widowed);
    }
}
