public class OccupationDGH extends CategoricalDGH {

    public OccupationDGH() {
        buildHierarchy();
    }

    @Override
    protected void buildHierarchy() {

        // Root
        root = new DGHNode("Occupation");
        registerParent(root, root);
        // -------- Level 1 --------
        DGHNode professional = new DGHNode("Professional");
        DGHNode technical = new DGHNode("Technical/Skilled");
        DGHNode service = new DGHNode("Service");
        DGHNode manual = new DGHNode("Manual");
        DGHNode defense = new DGHNode("Defense/Safety");

        root.addChild(professional);
        root.addChild(technical);
        root.addChild(service);
        root.addChild(manual);
        root.addChild(defense);

        // -------- Professional --------
        DGHNode exec = new DGHNode("Exec-managerial");
        DGHNode profSpec = new DGHNode("Prof-specialty");
        DGHNode adm = new DGHNode("Adm-clerical");

        professional.addChild(exec);
        professional.addChild(profSpec);
        professional.addChild(adm);

        registerParent(professional, exec);
        registerParent(professional, profSpec);
        registerParent(professional, adm);

        // -------- Technical / Skilled --------
        DGHNode techSupport = new DGHNode("Tech-support");
        DGHNode craft = new DGHNode("Craft-repair");
        DGHNode machine = new DGHNode("Machine-op-inspct");

        technical.addChild(techSupport);
        technical.addChild(craft);
        technical.addChild(machine);

        registerParent(technical, techSupport);
        registerParent(technical, craft);
        registerParent(technical, machine);

        // -------- Service --------
        DGHNode sales = new DGHNode("Sales");
        DGHNode otherService = new DGHNode("Other-service");
        DGHNode privHouse = new DGHNode("Priv-house-serv");

        service.addChild(sales);
        service.addChild(otherService);
        service.addChild(privHouse);

        registerParent(service, sales);
        registerParent(service, otherService);
        registerParent(service, privHouse);

        // -------- Manual --------
        DGHNode farming = new DGHNode("Farming-fishing");
        DGHNode transport = new DGHNode("Transport-moving");
        DGHNode handlers = new DGHNode("Handlers-cleaners");

        manual.addChild(farming);
        manual.addChild(transport);
        manual.addChild(handlers);

        registerParent(manual, farming);
        registerParent(manual, transport);
        registerParent(manual, handlers);

        // -------- Defense / Safety --------
        DGHNode protective = new DGHNode("Protective-serv");
        DGHNode armed = new DGHNode("Armed-Forces");

        defense.addChild(protective);
        defense.addChild(armed);

        registerParent(defense, protective);
        registerParent(defense, armed);
    }
}
