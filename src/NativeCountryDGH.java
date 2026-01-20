public class NativeCountryDGH extends CategoricalDGH {

    public NativeCountryDGH() {
        buildHierarchy();
    }

    @Override
    protected void buildHierarchy() {

        root = new DGHNode("Native-Country");
      
        // -------- Level 1 : Regions --------
        DGHNode northAmerica = new DGHNode("North America");
        DGHNode latinAmerica = new DGHNode("Latin America");
        DGHNode europe = new DGHNode("Europe");
        DGHNode asiaPacific = new DGHNode("Asia-Pacific");
        DGHNode otherRegions = new DGHNode("Other Regions");

        root.addChild(northAmerica);
        root.addChild(latinAmerica);
        root.addChild(europe);
        root.addChild(asiaPacific);
        root.addChild(otherRegions);

        registerParent(root, northAmerica);
        registerParent(root, latinAmerica);
        registerParent(root, europe);
        registerParent(root, asiaPacific);
        registerParent(root, otherRegions);
        // -------- North America --------
        DGHNode usa = new DGHNode("United-States");
        DGHNode canada = new DGHNode("Canada");
      
        northAmerica.addChild(usa);
        northAmerica.addChild(canada);
       

        registerParent(northAmerica, usa);
        registerParent(northAmerica, canada);
        
        // -------- Latin America --------
        String[] latinCountries = {
                "Mexico", "Puerto-Rico", "Cuba", "Jamaica", "Haiti",
                "Trinadad&Tobago", "Honduras", "Guatemala", "Nicaragua",
                "El-Salvador", "Peru", "Ecuador", "Columbia",
                "Dominican-Republic"
        };

        for (String c : latinCountries) {
            DGHNode node = new DGHNode(c);
            latinAmerica.addChild(node);
            registerParent(latinAmerica, node);
        }

        // -------- Europe --------
        String[] europeanCountries = {
                "England", "Germany", "Ireland", "France", "Italy",
                "Scotland", "Greece", "Poland", "Portugal",
                "Hungary", "Yugoslavia", "Holand-Netherlands"
        };

        for (String c : europeanCountries) {
            DGHNode node = new DGHNode(c);
            europe.addChild(node);
            registerParent(europe, node);
        }

        // -------- Asia-Pacific --------
        String[] asiaPacificCountries = {
                "India", "China", "Japan", "Taiwan",
                "Philippines", "Hong", "Vietnam",
                "Thailand", "Laos", "Cambodia", "Iran"
        };

        for (String c : asiaPacificCountries) {
            DGHNode node = new DGHNode(c);
            asiaPacific.addChild(node);
            registerParent(asiaPacific, node);
        }

        // -------- Other Regions --------
        String[] others = {
                "South", "Outlying-US(Guam-USVI-etc)"
        };

        for (String c : others) {
            DGHNode node = new DGHNode(c);
            otherRegions.addChild(node);
            registerParent(otherRegions, node);
        }
    }
}
