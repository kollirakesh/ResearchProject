public class DGHTreePrinter {

    public static void printTree(DGHNode root) {
        printTree(root, "", true);
    }

    private static void printTree(DGHNode node, String prefix, boolean isLast) {
        if (node == null) return;

        System.out.println(prefix + (isLast ? "└── " : "├── ") + node.value);

        for (int i = 0; i < node.children.size(); i++) {
            boolean last = (i == node.children.size() - 1);
            printTree(
                    node.children.get(i),
                    prefix + (isLast ? "    " : "│   "),
                    last
            );
        }
    }
}
