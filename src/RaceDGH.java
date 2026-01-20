public class RaceDGH extends CategoricalDGH {

    public RaceDGH() {
        buildHierarchy();
    }

    @Override
    protected void buildHierarchy() {

        root = new DGHNode("Race");
       
        DGHNode white = new DGHNode("White");
        DGHNode black = new DGHNode("Black");
        DGHNode asian = new DGHNode("Asian-Pac-Islander");
        DGHNode amerIndian = new DGHNode("Amer-Indian-Eskimo");
        DGHNode other = new DGHNode("Other");

        root.addChild(white);
        root.addChild(black);
        root.addChild(asian);
        root.addChild(amerIndian);
        root.addChild(other);
        
         registerParent(root, white);
          registerParent(root, black);
           registerParent(root, asian);
           registerParent(root, amerIndian);
            registerParent(root, other);
        
    }
}
