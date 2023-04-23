package Objects;

public class Pedidocliente {

    String id;
    String username;
    String direccion;
    String estado;

    public Pedidocliente(String id, String username, String direccion, String estado) {
        this.id = id;
        this.username = username;
        this.direccion = direccion;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
