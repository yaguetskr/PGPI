package com.rest.backend;

import java.util.ArrayList;

public class Pickinglist {
    String id;
    ArrayList<Product> lista;

    String estado;

    public Pickinglist(String id, ArrayList<Product> lista) {
        this.id = id;
        this.lista = lista;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Product> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Product> lista) {
        this.lista = lista;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    public int getSize(){
        return this.lista.size();
    }
}