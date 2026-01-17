import java.util.*;

public class CategoricalUniqueExtractor {

    /**
     * Extract unique values for each categorical QI column
     */
    public static Map<String, Set<String>> extractUniqueCategoricalValues(
            QIProcessor.QITableResult qi,
            Set<String> categoricalColumns) {

        Map<String, Set<String>> result = new LinkedHashMap<>();

        // Initialize sets
        for (String colName : categoricalColumns) {
            result.put(colName, new HashSet<>());
        }

        // Traverse rows
        for (List<Object> row : qi.qiTable) {

            for (int colIndex = 0; colIndex < qi.qiColumnNames.size(); colIndex++) {

                String colName = qi.qiColumnNames.get(colIndex);

                if (categoricalColumns.contains(colName)) {
                    Object value = row.get(colIndex);
                    result.get(colName).add(value.toString());
                }
            }
        }

        return result;
    }

    /**
     * Pretty print unique categorical values
     */
    public static void printUniqueCategoricalValues(
            Map<String, Set<String>> uniqueMap) {

        System.out.println("\n===== UNIQUE CATEGORICAL VALUES =====");

        for (Map.Entry<String, Set<String>> entry : uniqueMap.entrySet()) {
            System.out.println("Attribute: " + entry.getKey());
            System.out.println("Unique Values (" + entry.getValue().size() + "):");
            System.out.println(entry.getValue());
            System.out.println("-----------------------------------");
        }
    }
}
