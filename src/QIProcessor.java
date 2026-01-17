import java.util.*;

public class QIProcessor {

    public static class QITableResult {
        public List<List<Object>> qiTable;
        public List<String> qiColumnNames;
        public int[] minArray;
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

        // Initialize column names and minArray
        for (int i = 0; i < qiIndexes.size(); i++) {
            ColumnMeta col = ds.columns.get(qiIndexes.get(i));
            res.qiColumnNames.add(col.name);
            res.minArray[i] =
                    col.type == AttributeType.NUMERICAL ? Integer.MAX_VALUE : -1;
        }

        // Process rows
        for (List<Object> row : ds.rows) {

            List<Object> qiRow = new ArrayList<>();
            boolean hasMissing = false;

            for (int i = 0; i < qiIndexes.size(); i++) {
                int idx = qiIndexes.get(i);
                Object val = row.get(idx);

                // ❌ Missing categorical value
                if (val instanceof String && ((String) val).equals("?")) {
                    hasMissing = true;
                    break;
                }

                qiRow.add(val);
            }

            // Skip tuple if any QI value is missing
            if (hasMissing) {
                continue;
            }

            // Update minArray ONLY for valid rows
            for (int i = 0; i < qiRow.size(); i++) {
                if (res.minArray[i] != -1) {
                    int value = (Integer) qiRow.get(i);
                    res.minArray[i] = Math.min(res.minArray[i], value);
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

            // Only numerical QIs have valid minArray value
            if (res.minArray[i] != -1) {
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

    // Add column name
    res.qiColumnNames.add("distance");

    // Append distance to each row
    for (int i = 0; i < res.qiTable.size(); i++) {
        res.qiTable.get(i).add(distanceColumn.get(i));
    }
}


}
