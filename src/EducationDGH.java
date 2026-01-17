public class EducationDGH extends CategoricalDGH {

    public EducationDGH() {
        buildHierarchy();
    }

    @Override
    protected void buildHierarchy() {

        // Root
        root = new DGHNode("Education");

        // -------- Level 1 --------
        DGHNode school = new DGHNode("School Education");
        DGHNode undergrad = new DGHNode("Undergraduate/Diploma");
        DGHNode postgrad = new DGHNode("Postgraduate");

        root.addChild(school);
        root.addChild(undergrad);
        root.addChild(postgrad);

        // -------- School Education --------
        DGHNode primary = new DGHNode("Primary");
        DGHNode middle = new DGHNode("Middle");
        DGHNode high = new DGHNode("High");

        school.addChild(primary);
        school.addChild(middle);
        school.addChild(high);

        // -------- Primary --------
        DGHNode preschool = new DGHNode("Preschool");
        DGHNode firstToFourth = new DGHNode("1st-4th");

        primary.addChild(preschool);
        primary.addChild(firstToFourth);

        registerParent(primary, preschool);
        registerParent(primary, firstToFourth);

        // -------- Middle --------
        DGHNode fifthToSixth = new DGHNode("5th-6th");
        DGHNode seventhToEighth = new DGHNode("7th-8th");

        middle.addChild(fifthToSixth);
        middle.addChild(seventhToEighth);

        registerParent(middle, fifthToSixth);
        registerParent(middle, seventhToEighth);

        // -------- High --------
        DGHNode ninth = new DGHNode("9th");
        DGHNode tenth = new DGHNode("10th");
        DGHNode eleventh = new DGHNode("11th");
        DGHNode twelfth = new DGHNode("12th");
        DGHNode hsGrad = new DGHNode("HS-grad");

        high.addChild(ninth);
        high.addChild(tenth);
        high.addChild(eleventh);
        high.addChild(twelfth);
        high.addChild(hsGrad);

        registerParent(high, ninth);
        registerParent(high, tenth);
        registerParent(high, eleventh);
        registerParent(high, twelfth);
        registerParent(high, hsGrad);

        // -------- Undergraduate / Diploma --------
        DGHNode someCollege = new DGHNode("Some-college");
        DGHNode assocVoc = new DGHNode("Assoc-voc");
        DGHNode assocAcdm = new DGHNode("Assoc-acdm");

        undergrad.addChild(someCollege);
        undergrad.addChild(assocVoc);
        undergrad.addChild(assocAcdm);

        registerParent(undergrad, someCollege);
        registerParent(undergrad, assocVoc);
        registerParent(undergrad, assocAcdm);

        // -------- Postgraduate --------
        DGHNode masters = new DGHNode("Masters");
        DGHNode profSchool = new DGHNode("Prof-school");
        DGHNode doctorate = new DGHNode("Doctorate");

        postgrad.addChild(masters);
        postgrad.addChild(profSchool);
        postgrad.addChild(doctorate);

        registerParent(postgrad, masters);
        registerParent(postgrad, profSchool);
        registerParent(postgrad, doctorate);
    }
}
