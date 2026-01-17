import java.util.*;

public class CategoricalQIMeta {

    public static final Set<String> CATEGORICAL_QI_COLUMNS =
            new LinkedHashSet<>(Arrays.asList(
                    "workclass",
                    "education",
                    "marital-status",
                    "occupation",
                    "relationship",
                    "race",
                    "sex",
                    "native-country"
            ));
}
