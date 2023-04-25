package Objects;

public class Productotemp {
    private String provider;
    private String reference;
    private String minOrderQty;

    public Productotemp(String provider, String reference, String minOrderQty) {
        this.provider = provider;
        this.reference = reference;
        this.minOrderQty = minOrderQty;
    }

    public String getProvider() {
        return provider;
    }

    public String getReference() {
        return reference;
    }

    public String getMinOrderQty() {
        return minOrderQty;
    }
}
