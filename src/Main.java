import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {

    // ✅ QI column indices (must match qi.qiTable order)
    static final int AGE_COL = 0;
    static final int HOURS_COL = 8;

    public static void main(String[] args) {

        try {
            DataSet ds = DataLoader.loadAdultData("data/adult.csv");
            System.out.println("Original dataset size: " + ds.rows.size());

            // ---------------- QI TABLE ----------------
            QIProcessor.QITableResult qi = QIProcessor.buildQITable(ds);

            List<Integer> distanceCol = QIProcessor.computeDistanceColumn(qi);
            QIProcessor.appendDistanceColumn(qi, distanceCol);

            // ---------------- UNIQUE CATEGORICAL ----------------
            Map<String, Set<String>> uniqueCategorical =
                    CategoricalUniqueExtractor.extractUniqueCategoricalValues(
                            qi,
                            CategoricalQIMeta.CATEGORICAL_QI_COLUMNS
                    );

            CategoricalUniqueExtractor.printUniqueCategoricalValues(uniqueCategorical);

                    System.out.println("\nMin Array:");
                    System.out.println(Arrays.toString(qi.minArray));
                    System.out.println("Max Array:");
                    System.out.println(Arrays.toString(qi.maxArray));
            // ---------------- DGH SETUP ----------------
            int maxlevel = 0;
            List<Map<String, String>> parentsMapList = new ArrayList<>();
            List<DGHNode> rootsList = new ArrayList<>();

            CategoricalDGH workclassDGH = new WorkclassDGH();
            parentsMapList.add(workclassDGH.getParentMap());
            rootsList.add(workclassDGH.getRoot());
            maxlevel = Math.max(maxlevel, workclassDGH.getHeight());

            CategoricalDGH educationDGH = new EducationDGH();
            parentsMapList.add(educationDGH.getParentMap());
            rootsList.add(educationDGH.getRoot());
            maxlevel = Math.max(maxlevel, educationDGH.getHeight());

            CategoricalDGH maritalDGH = new MaritalStatusDGH();
            parentsMapList.add(maritalDGH.getParentMap());
            rootsList.add(maritalDGH.getRoot());
            maxlevel = Math.max(maxlevel, maritalDGH.getHeight());

            CategoricalDGH occupationDGH = new OccupationDGH();
            parentsMapList.add(occupationDGH.getParentMap());
            rootsList.add(occupationDGH.getRoot());
            maxlevel = Math.max(maxlevel, occupationDGH.getHeight());

            CategoricalDGH relationshipDGH = new RelationshipDGH();
            parentsMapList.add(relationshipDGH.getParentMap());
            rootsList.add(relationshipDGH.getRoot());
            maxlevel = Math.max(maxlevel, relationshipDGH.getHeight());

            CategoricalDGH raceDGH = new RaceDGH();
            parentsMapList.add(raceDGH.getParentMap());
            rootsList.add(raceDGH.getRoot());
            maxlevel = Math.max(maxlevel, raceDGH.getHeight());

            CategoricalDGH sexDGH = new SexDGH();
            parentsMapList.add(sexDGH.getParentMap());
            rootsList.add(sexDGH.getRoot());
            maxlevel = Math.max(maxlevel, sexDGH.getHeight());

            CategoricalDGH countryDGH = new NativeCountryDGH();
            parentsMapList.add(countryDGH.getParentMap());
            rootsList.add(countryDGH.getRoot());
            maxlevel = Math.max(maxlevel, countryDGH.getHeight());
            
            System.out.println("Max DGH level: " + maxlevel);
            // ---------------- BEFORE FILE ----------------
            Path before = Paths.get("C:/Users/user/Desktop/ResearchProject/data/before.txt");
            Files.write(
                    before,
                    qi.qiTable.stream()
                            .map(r -> r.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).get())
                            .toList()
            );

            // ---------------- CLUSTERING ----------------
            int k = 20;
            Clusters.generateClusters(
                    0, 0,
                    parentsMapList,
                    qi.qiTable.size(),
                    0,
                    maxlevel,
                    qi.qiTable,
                    rootsList,
                    k
            );

            // ---------------- NUMERICAL GENERALIZATION ----------------
            Clusters.generalizeNumericalAttributes(qi.qiTable, k);

            // ---------------- INFORMATION LOSS ----------------
            double numLoss = Clusters.numericalInformationLoss(
                    qi.qiTable, k,
                    qi.minArray[AGE_COL], qi.maxArray[AGE_COL],
                    qi.minArray[HOURS_COL], qi.maxArray[HOURS_COL]
            );

            double catLoss = Clusters.categoricalInformationLoss(qi.qiTable, rootsList);
            double totalLoss = Clusters.totalInformationLoss(numLoss, catLoss);

            System.out.println("\nNumerical Loss  = " + numLoss);
            System.out.println("Categorical Loss = " + catLoss);
            System.out.println("Total Loss = " + totalLoss);

            // ---------------- CLEANUP ----------------
            Clusters.removeDistanceColumn(qi.qiTable);

            // ---------------- AFTER FILE ----------------
            Path after = Paths.get("C:/Users/user/Desktop/ResearchProject/data/after.txt");
            Files.write(
                    after,
                    qi.qiTable.stream()
                            .map(r -> r.stream().map(String::valueOf).reduce((a, b) -> a + "," + b).get())
                            .toList()
            );

            // Print QI column names
for (String colName : qi.qiColumnNames) {
    System.out.print(colName + "\t");
}
System.out.println();

// Print first 20 QI rows
int limit = Math.min(20, qi.qiTable.size());

for (int i = 0; i < limit; i++) {
    for (Object val : qi.qiTable.get(i)) {
        System.out.print(val + "\t");
    }
    System.out.println();
}
System.out.println("raw dataset size: " + ds.rows.size());
System.out.println("\nProcessed QI table size: " + qi.qiTable.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
