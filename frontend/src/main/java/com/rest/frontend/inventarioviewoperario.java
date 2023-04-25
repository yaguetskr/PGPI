package com.rest.frontend;

import Objects.API;
import Objects.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@Route(value = "inventariolite",layout=MainView.class)
public class inventarioviewoperario extends VerticalLayout {

    public inventarioviewoperario() throws Exception {

        API api = new API();
        String listajson = null;
        try {
            listajson = api.getallproducts();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        Type productListType = new TypeToken<List<Product>>() {
        }.getType();
        List<Product> lista = gson.fromJson(listajson, productListType);
        //List<Product> lista = gson.fromJson(listajson,new TypeToken<List<Product>>(){}.getType());

        Grid<Product> tabla = new Grid<>();
        Grid.Column<Product> idcol = tabla.addColumn(Product::getId).setHeader("Referencia:");
        Grid.Column<Product> namecol = tabla.addColumn(Product::getName).setHeader("Nombre comercial:");
        Grid.Column<Product> preciocol = tabla.addColumn(Product::getPrice).setHeader("Precio:");
        Grid.Column<Product> stockcol = tabla.addColumn(Product::getStock).setHeader("Stock:");
        Grid.Column<Product> proveedorcol = tabla.addColumn(Product::getProveedor).setHeader("Proveedor:");
        Grid.Column<Product> ubicacioncol = tabla.addColumn(Product::getUbicacion).setHeader("Ubicación:");
        Grid.Column<Product> umbralcol = tabla.addColumn(Product::getUmbral).setHeader("Umbral:");

        tabla.setItems(lista);
        add(tabla);






    }
}
