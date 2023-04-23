package com.rest.backend;

public class Pedidoprov {
    String id;
    String idproducto;
    String proveedor;
    int cantidad;

    public Pedidoprov(String id, String idproducto, String proveedor, int cantidad) {
        this.id = id;
        this.idproducto = idproducto;
        this.proveedor = proveedor;
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
