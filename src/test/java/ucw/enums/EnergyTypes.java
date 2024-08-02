package ucw.enums;

public enum EnergyTypes {
    DIESEL("Diesel"),
    ESSENCE("Essence"),
    ELECTRIC("Electric"),
    GPL("GPL"),
    GNV("GNV"),
    HYBRID("Hybrid"),
    HYBRIDPLUGIN("Hybrid plug in");

    private String type;
    EnergyTypes(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
