package com.rest.frontend;

import Objects.API;
import Objects.Pedidoprov;
import Objects.Product;
import Objects.Usuario;
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

@Route(value = "proveedores",layout=MainView.class)
public class PedidosprovView extends VerticalLayout {

    public PedidosprovView() throws Exception {

        API api = new API();
        String listajson = null;
        try {
            listajson = api.getallpedidosprov();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        Type PedidoprovListType = new TypeToken<List<Pedidoprov>>() {
        }.getType();
        List<Pedidoprov> lista = gson.fromJson(listajson, PedidoprovListType);
        //List<Pedidoprov> lista = gson.fromJson(listajson,new TypeToken<List<Pedidoprov>>(){}.getType());

        Grid<Pedidoprov> tabla = new Grid<>();
        Grid.Column<Pedidoprov> idcol = tabla.addColumn(Pedidoprov::getId).setHeader("ID:");
        Grid.Column<Pedidoprov> namecol = tabla.addColumn(Pedidoprov::getIdproducto).setHeader("Producto:");
        Grid.Column<Pedidoprov> cantcol = tabla.addColumn(Pedidoprov::getCantidad).setHeader("Cantidad:");
        Grid.Column<Pedidoprov> proveedorcol = tabla.addColumn(Pedidoprov::getProveedor).setHeader("Proveedor:");

        tabla.setItems(lista);
        add(tabla);



        Button nuevopedido = new Button("Nuevo pedido a proveedores");
        add(nuevopedido);
        nuevopedido.addClickListener(clickEvent -> {

            String listajsontemp= null;
            try {
                listajsontemp = api.getallproducts();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }



            Type productListType = new TypeToken<List<Product>>(){}.getType();
            List<Product> listaprods = gson.fromJson(listajsontemp, productListType);

            Dialog dialog = new Dialog();
            add(dialog);
            dialog.open();
            VerticalLayout layoutdialog = new VerticalLayout();
            layoutdialog.add(new H3("Realizar nuevo pedido a proveedores:"));

            Select<Product> select = new Select<>();
            select.setLabel("Producto:");
            select.setItemLabelGenerator(Product::getName);
            select.setItems(listaprods);
            layoutdialog.add(select);


            layoutdialog.add("Cantidad:");
            TextField canttf= new TextField();
            layoutdialog.add(canttf);


            Button confirm = new Button("Confirmar pedido");

            confirm.addClickListener(clickEvent2 -> {
            String provider=null;
                try {

                    api.createpedidoprov(select.getValue().getId(),select.getValue().getProveedor(),canttf.getValue() );
                    Notification.show("Pedido realizado");
                    String temp = api.getallpedidosprov();
                    List<Pedidoprov> templist = gson.fromJson(temp,new TypeToken<List<Pedidoprov>>(){}.getType());
                    tabla.setItems(templist);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            });

            Button Cancel = new Button("Cancel");

            Cancel.addClickListener(clickEvent2 -> {
                dialog.close();
            });



            HorizontalLayout botones = new HorizontalLayout(confirm, Cancel);

            dialog.add(layoutdialog,botones);


        });
    }
    
}
