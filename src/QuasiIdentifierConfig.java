import java.util.*;

public class QuasiIdentifierConfig {

    public static Set<String> getQuasiIdentifiers() {
        return new HashSet<>(Arrays.asList(
                "age",
                "workclass",
                "education",
                "marital-status",
                "occupation",
                "relationship",
                "race",
                "sex",
                "hours-per-week",
                "native-country"
        ));
    }
}
