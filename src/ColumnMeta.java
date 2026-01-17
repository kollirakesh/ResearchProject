public class ColumnMeta {
    public String name;
    public AttributeType type;
    public boolean isQuasiIdentifier;

    public ColumnMeta(String name, AttributeType type, boolean isQI) {
        this.name = name;
        this.type = type;
        this.isQuasiIdentifier = isQI;
    }
}
