public class WorkclassDGH extends CategoricalDGH {

    public WorkclassDGH() {
        buildHierarchy();
    }

    @Override
    protected void buildHierarchy() {

        // Root
        root = new DGHNode("Workclass");

        // -------- Level 1 --------
        DGHNode government = new DGHNode("Government");
        DGHNode selfEmployed = new DGHNode("Self-Employed");
        DGHNode privateOther = new DGHNode("Private/Other");

        root.addChild(government);
        root.addChild(selfEmployed);
        root.addChild(privateOther);

        // -------- Government --------
        DGHNode federal = new DGHNode("Federal-gov");
        DGHNode state = new DGHNode("State-gov");
        DGHNode local = new DGHNode("Local-gov");

        government.addChild(federal);
        government.addChild(state);
        government.addChild(local);

        registerParent(government, federal);
        registerParent(government, state);
        registerParent(government, local);

        // -------- Self-Employed --------
        DGHNode selfEmpInc = new DGHNode("Self-emp-inc");
        DGHNode selfEmpNotInc = new DGHNode("Self-emp-not-inc");

        selfEmployed.addChild(selfEmpInc);
        selfEmployed.addChild(selfEmpNotInc);

        registerParent(selfEmployed, selfEmpInc);
        registerParent(selfEmployed, selfEmpNotInc);

        // -------- Private / Other --------
        DGHNode privateNode = new DGHNode("Private");
        DGHNode withoutPay = new DGHNode("Without-pay");

        privateOther.addChild(privateNode);
        privateOther.addChild(withoutPay);

        registerParent(privateOther, privateNode);
        registerParent(privateOther, withoutPay);
    }
}
