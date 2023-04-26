package Objects;

public class Usuario {

    String username;
    String pwd;
    String rol;

    int id;

    public Usuario(String username, String pwd, String rol,int id) {
        this.username = username;
        this.pwd = pwd;
        this.rol = rol;
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", rol='" + rol + '\'' +
                '}';
    }
}
