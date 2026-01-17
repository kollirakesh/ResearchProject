public class Main {

    public static void main(String[] args) {

        try {
            DataSet ds = DataLoader.loadAdultData("data/adult.csv");

            System.out.println("Total Records: " + ds.rows.size());
            System.out.println("Total Attributes: " + ds.columns.size());
            System.out.println();

            // Print header
            for (ColumnMeta col : ds.columns) {
                System.out.print(col.name + "\t");
            }
            System.out.println();

            // Print first 20 records
            int limit = Math.min(20, ds.rows.size());

            for (int i = 0; i < limit; i++) {
                for (Object value : ds.rows.get(i)) {
                    System.out.print(value + "\t");
                }
                System.out.println();
            }

            System.out.println("\nQuasi-Identifier Attributes:");
            for (ColumnMeta col : ds.columns) {
                if (col.isQuasiIdentifier) {
                    System.out.println("- " + col.name);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
