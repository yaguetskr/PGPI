package Objects;

public class Product {

    String id;
    int stock;
    float price;
    String name;

    String proveedor;

    String ubicacion;

    String umbral;

    public Product(String id, int stock, float price, String name, String proveedor, String ubicacion, String umbral) {
        this.id = id;
        this.stock = stock;
        this.price = price;
        this.name = name;
        this.proveedor = proveedor;
        this.ubicacion = ubicacion;
        this.umbral = umbral;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUmbral() {
        return umbral;
    }

    public void setUmbral(String umbral) {
        this.umbral = umbral;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

