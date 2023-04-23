package com.rest.frontend;

import Objects.API;
import Objects.Pedidocliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

@Route(value = "clientes",layout=MainView.class)
public class PedidosclienteView extends VerticalLayout {
    public PedidosclienteView() throws Exception {

        API api = new API();
        String listajson = null;
        try {
            listajson = api.getallpedidoscliente();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        Type PedidoclienteListType = new TypeToken<List<Pedidocliente>>() {
        }.getType();
        List<Pedidocliente> lista = gson.fromJson(listajson, PedidoclienteListType);
        //List<Pedidocliente> lista = gson.fromJson(listajson,new TypeToken<List<Pedidocliente>>(){}.getType());

        Grid<Pedidocliente> tabla = new Grid<>();
        Grid.Column<Pedidocliente> idcol = tabla.addColumn(Pedidocliente::getId).setHeader("ID:");
        Grid.Column<Pedidocliente> namecol = tabla.addColumn(Pedidocliente::getUsername).setHeader("Usuario:");
        Grid.Column<Pedidocliente> dircol = tabla.addColumn(Pedidocliente::getDireccion).setHeader("Dirección:");
        Grid.Column<Pedidocliente> estadocol = tabla.addColumn(Pedidocliente::getEstado).setHeader("Estado del pedido:");

        tabla.setItems(lista);
        add(tabla);
    }
}
