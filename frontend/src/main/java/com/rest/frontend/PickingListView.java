package com.rest.frontend;


import Objects.API;
import Objects.Pedidocliente;
import Objects.Pickinglist;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;

@Route(value = "pickinglist",layout=MainView.class)
public class PickingListView extends VerticalLayout {

    public PickingListView() throws Exception {

        API api = new API();
        String listajson = null;
        try {
            listajson = api.getalllistaspicking();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        Type PickingListType = new TypeToken<List<Pickinglist>>() {
        }.getType();
        List<Pickinglist> lista = gson.fromJson(listajson, PickingListType);

        Grid<Pickinglist> tabla = new Grid<>();
        Grid.Column<Pickinglist> idcol = tabla.addColumn(Pickinglist::getId).setHeader("ID:");
        Grid.Column<Pickinglist> lencol = tabla.addColumn(Pickinglist::getSize).setHeader("Numero de productos:");
        Grid.Column<Pickinglist> estadocol = tabla.addColumn(Pickinglist::getEstado).setHeader("Estado del pedido:");

        add(tabla);
        tabla.setItems(lista);
    }



}
