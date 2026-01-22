import java.util.*;

public class QIProcessor {

    public static class QITableResult {
        public List<List<Object>> qiTable;
        public List<String> qiColumnNames;
        public int[] minArray;   // global min for numerical QIs
        public int[] maxArray;   // global max for numerical QIs
    }

    public static QITableResult buildQITable(DataSet ds) {

        List<Integer> qiIndexes = new ArrayList<>();
        for (int i = 0; i < ds.columns.size(); i++) {
            if (ds.columns.get(i).isQuasiIdentifier) {
                qiIndexes.add(i);
            }
        }

        QITableResult res = new QITableResult();
        res.qiTable = new ArrayList<>();
        res.qiColumnNames = new ArrayList<>();
        res.minArray = new int[qiIndexes.size()];
        res.maxArray = new int[qiIndexes.size()];

        // Initialize column names, minArray and maxArray
        for (int i = 0; i < qiIndexes.size(); i++) {
            ColumnMeta col = ds.columns.get(qiIndexes.get(i));
            res.qiColumnNames.add(col.name);

            if (col.type == AttributeType.NUMERICAL) {
                res.minArray[i] = Integer.MAX_VALUE;
                res.maxArray[i] = Integer.MIN_VALUE;
            } else {
                res.minArray[i] = -1;   // marker for categorical
                res.maxArray[i] = -1;
            }
        }

        // Process rows
        for (List<Object> row : ds.rows) {

            List<Object> qiRow = new ArrayList<>();
            boolean hasMissing = false;

            for (int i = 0; i < qiIndexes.size(); i++) {
                int idx = qiIndexes.get(i);
                Object val = row.get(idx);

                // Skip tuple if any categorical QI is missing
                if (val instanceof String && ((String) val).equals("?")) {
                    hasMissing = true;
                    break;
                }

                qiRow.add(val);
            }

            if (hasMissing) {
                continue;
            }

            // Update global min and max for numerical attributes
            for (int i = 0; i < qiRow.size(); i++) {
                if (res.minArray[i] != -1) { // numerical attribute
                    int value = (Integer) qiRow.get(i);
                    res.minArray[i] = Math.min(res.minArray[i], value);
                    res.maxArray[i] = Math.max(res.maxArray[i], value);
                }
            }

            res.qiTable.add(qiRow);
        }

        return res;
    }

    public static List<Integer> computeDistanceColumn(QITableResult res) {

        List<Integer> distanceColumn = new ArrayList<>();

        for (List<Object> row : res.qiTable) {

            int distance = 0;

            for (int i = 0; i < row.size(); i++) {
                if (res.minArray[i] != -1) { // numerical QI
                    int value = (Integer) row.get(i);
                    distance += (value - res.minArray[i]);
                }
            }

            distanceColumn.add(distance);
        }

        return distanceColumn;
    }

    public static void appendDistanceColumn(
            QITableResult res,
            List<Integer> distanceColumn) {

        res.qiColumnNames.add("distance");

        for (int i = 0; i < res.qiTable.size(); i++) {
            res.qiTable.get(i).add(distanceColumn.get(i));
        }
    }
}
