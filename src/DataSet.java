import java.util.*;

public class DataSet {
    public List<ColumnMeta> columns;
    public List<List<Object>> rows;

    public DataSet() {
        columns = new ArrayList<>();
        rows = new ArrayList<>();
    }
}
