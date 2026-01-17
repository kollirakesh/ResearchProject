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

        for (int i = 0; i < qiIndexes.size(); i++) {
            ColumnMeta col = ds.columns.get(qiIndexes.get(i));
            res.qiColumnNames.add(col.name);
            res.minArray[i] = col.type == AttributeType.NUMERICAL
                    ? Integer.MAX_VALUE : -1;
        }

        for (List<Object> row : ds.rows) {
            List<Object> qiRow = new ArrayList<>();

            for (int i = 0; i < qiIndexes.size(); i++) {
                int idx = qiIndexes.get(i);
                Object val = row.get(idx);
                qiRow.add(val);

                if (res.minArray[i] != -1) {
                    res.minArray[i] = Math.min(res.minArray[i], (Integer) val);
                }
            }
            res.qiTable.add(qiRow);
        }
        return res;
    }
}
