import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        try {
            DataSet ds = DataLoader.loadAdultData("data/adult.csv");

            // System.out.println("Total Records: " + ds.rows.size());
            // System.out.println("Total Attributes: " + ds.columns.size());
            // System.out.println();

            // // Print header
            // for (ColumnMeta col : ds.columns) {
            //     System.out.print(col.name + "\t");
            // }
            // System.out.println();

            // // Print first 20 records
            // int limit = Math.min(20, ds.rows.size());

            // for (int i = 0; i < limit; i++) {
            //     for (Object value : ds.rows.get(i)) {
            //         System.out.print(value + "\t");
            //     }
            //     System.out.println();
            // }

            // System.out.println("\nQuasi-Identifier Attributes:");
            // for (ColumnMeta col : ds.columns) {
            //     if (col.isQuasiIdentifier) {
            //         System.out.println("- " + col.name);
            //     }
            // }

                   QIProcessor.QITableResult qi =
                    QIProcessor.buildQITable(ds);

            // Compute distance
            List<Integer> distanceCol =
                    QIProcessor.computeDistanceColumn(qi);

            // Append distance column
            QIProcessor.appendDistanceColumn(qi, distanceCol);

            // Print first 20 rows (QI + distance)
            for (String name : qi.qiColumnNames) {
                System.out.print(name + "\t");
            }
            System.out.println();

            for (int i = 0; i < 20; i++) {
                for (Object val : qi.qiTable.get(i)) {
                    System.out.print(val + "\t");
                }
                System.out.println();
            }

            // --------- CATEGORICAL UNIQUE VALUES ---------

Map<String, Set<String>> uniqueCategorical =
        CategoricalUniqueExtractor.extractUniqueCategoricalValues(
                qi,
                CategoricalQIMeta.CATEGORICAL_QI_COLUMNS
        );

CategoricalUniqueExtractor.printUniqueCategoricalValues(uniqueCategorical);






            System.out.println("\nMin Array:");
            System.out.println(Arrays.toString(qi.minArray));

            //Initialize maxlevel
            int maxlevel = 0;

            //List of parentMaps of every categorical attribute
            List<Map<String, String>> parentsMapList = new ArrayList<>();

            //List of roots of every DGH'S
            List<DGHNode> rootsList = new ArrayList<>();
            
             // 1. Workclass
            CategoricalDGH workclassDGH = new WorkclassDGH();
            System.out.println("\n=== Workclass DGH ===");
            DGHTreePrinter.printTree(workclassDGH.getRoot());
            // for(Map.Entry<String,String> hEntry: workclassDGH.getParentMap().entrySet()){
            //     System.out.println(hEntry.getKey()+" "+hEntry.getValue());
            // }
            System.out.println(workclassDGH.getHeight());
            maxlevel = Math.max(maxlevel, workclassDGH.getHeight());
            parentsMapList.add(workclassDGH.getParentMap());
            rootsList.add(workclassDGH.getRoot());

            // 2. Education
            CategoricalDGH educationDGH = new EducationDGH();
            System.out.println("\n=== Education DGH ===");
            DGHTreePrinter.printTree(educationDGH.getRoot());
            System.out.println(educationDGH.getHeight()); 
            maxlevel = Math.max(maxlevel, educationDGH.getHeight());
            parentsMapList.add(educationDGH.getParentMap());
            rootsList.add(educationDGH.getRoot());

            // 3. Marital-Status
            CategoricalDGH maritalStatusDGH = new MaritalStatusDGH();
            System.out.println("\n=== Marital-Status DGH ===");
            DGHTreePrinter.printTree(maritalStatusDGH.getRoot());
            System.out.println(maritalStatusDGH.getHeight());
            maxlevel = Math.max(maxlevel, maritalStatusDGH.getHeight());
            parentsMapList.add(maritalStatusDGH.getParentMap());
            rootsList.add(maritalStatusDGH.getRoot());

            // 4. Occupation
            CategoricalDGH occupationDGH = new OccupationDGH();
            System.out.println("\n=== Occupation DGH ===");
            DGHTreePrinter.printTree(occupationDGH.getRoot());
            System.out.println(occupationDGH.getHeight());
            maxlevel = Math.max(maxlevel, occupationDGH.getHeight());
            parentsMapList.add(occupationDGH.getParentMap());
            rootsList.add(occupationDGH.getRoot());

            // 5. Relationship
            CategoricalDGH relationshipDGH = new RelationshipDGH();
            System.out.println("\n=== Relationship DGH ===");
            DGHTreePrinter.printTree(relationshipDGH.getRoot());
            System.out.println(relationshipDGH.getHeight());
            maxlevel = Math.max(maxlevel, relationshipDGH.getHeight());
            parentsMapList.add(relationshipDGH.getParentMap());
            rootsList.add(relationshipDGH.getRoot());

            // 6. Native-Country
            CategoricalDGH nativeCountryDGH = new NativeCountryDGH();
            System.out.println("\n=== Native-Country DGH ===");
            DGHTreePrinter.printTree(nativeCountryDGH.getRoot());
            System.out.println(nativeCountryDGH.getHeight());
            maxlevel = Math.max(maxlevel, nativeCountryDGH.getHeight());
            parentsMapList.add(nativeCountryDGH.getParentMap());
            rootsList.add(nativeCountryDGH.getRoot());

            // 7. Race
            CategoricalDGH raceDGH = new RaceDGH();
            System.out.println("\n=== Race DGH ===");
            DGHTreePrinter.printTree(raceDGH.getRoot());
            System.out.println(raceDGH.getHeight());
            maxlevel = Math.max(maxlevel, raceDGH.getHeight());
            parentsMapList.add(raceDGH.getParentMap());
            rootsList.add(raceDGH.getRoot());

            // 8. Sex
            CategoricalDGH sexDGH = new SexDGH();
            System.out.println("\n=== Sex DGH ===");
            DGHTreePrinter.printTree(sexDGH.getRoot());
            System.out.println(sexDGH.getHeight());
            maxlevel = Math.max(maxlevel, sexDGH.getHeight());
            parentsMapList.add(sexDGH.getParentMap());
            rootsList.add(sexDGH.getRoot());

            System.out.println("Max level: " + maxlevel);
            
            int n = qi.qiTable.size();
            // Clusters.generateClusters(0, 0, parentsMapList, n, 0, maxlevel, qi.qiTable, rootsList, 5);
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
