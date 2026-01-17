import java.io.*;
import java.util.*;

public class DataLoader {

    public static DataSet loadAdultData(String filePath) throws Exception {

        DataSet dataSet = new DataSet();
        Set<String> qiSet = QuasiIdentifierConfig.getQuasiIdentifiers();

        String[] names = {
                "age", "workclass", "fnlwgt", "education", "education-num",
                "marital-status", "occupation", "relationship", "race", "sex",
                "capital-gain", "capital-loss", "hours-per-week",
                "native-country", "income"
        };

        AttributeType[] types = {
                AttributeType.NUMERICAL,
                AttributeType.CATEGORICAL,
                AttributeType.NUMERICAL,
                AttributeType.CATEGORICAL,
                AttributeType.NUMERICAL,
                AttributeType.CATEGORICAL,
                AttributeType.CATEGORICAL,
                AttributeType.CATEGORICAL,
                AttributeType.CATEGORICAL,
                AttributeType.CATEGORICAL,
                AttributeType.NUMERICAL,
                AttributeType.NUMERICAL,
                AttributeType.NUMERICAL,
                AttributeType.CATEGORICAL,
                AttributeType.CATEGORICAL
        };

        // Build schema
        for (int i = 0; i < names.length; i++) {
            dataSet.columns.add(
                    new ColumnMeta(
                            names[i],
                            types[i],
                            qiSet.contains(names[i])
                    )
            );
        }

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            String[] tokens = line.split(",");
            List<Object> row = new ArrayList<>();

            for (int i = 0; i < tokens.length; i++) {
                String value = tokens[i].trim();

                if (types[i] == AttributeType.NUMERICAL) {
                    row.add(Integer.parseInt(value));
                } else {
                    row.add(value);
                }
            }
            dataSet.rows.add(row);
        }
        br.close();
        return dataSet;
    }
}
