package com.rest.frontend;


import Objects.API;
import Objects.Pickinglist;
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
import java.util.Optional;

@Route(value = "pickingspendientes",layout=MainView.class)
public class PickingspendientesView extends VerticalLayout {

    public PickingspendientesView() throws Exception {

        API api = new API();
        String listajson = null;
        try {
            listajson = api.getalllistaspickingpendientes();
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



        tabla.addSelectionListener(selection -> {
            Optional<Pickinglist> optional = selection.getFirstSelectedItem();
            if (optional.isPresent()) {

                Dialog dialog = new Dialog();
                dialog.setWidth("300%");
                add(dialog);
                dialog.open();


                Grid<Product> tablaprods=new Grid<>();
                Grid.Column<Product> namecol =tablaprods.addColumn(Product::getName).setHeader("Product name:");
                Grid.Column<Product> preciocol =tablaprods.addColumn(Product::getPrice).setHeader("Precio:");
                Grid.Column<Product> stockcol =tablaprods.addColumn(Product::getStock).setHeader("Stock:");
                Grid.Column<Product> proveedorcol =tablaprods.addColumn(Product::getProveedor).setHeader("Proveedor:");
                Grid.Column<Product> ubicacioncol =tablaprods.addColumn(Product::getUbicacion).setHeader("Ubicación:");
                Grid.Column<Product> umbralcol =tablaprods.addColumn(Product::getUmbral).setHeader("Umbral:");

                tablaprods.setItems(optional.get().getLista());

                Button Completado = new Button("Pedido escaneado");
                Completado.addClickListener(clickEvent -> {
                    for (Product prod : optional.get().getLista()){

                        try {

                            api.editproduct(String.valueOf(prod.getId()),String.valueOf(prod.getStock()-1),String.valueOf(prod.getPrice()),String.valueOf(prod.getName()),prod.getProveedor(),prod.getUbicacion(),prod.getUmbral());
                            api.pedidolisto(optional.get().getId());
                        } catch (URISyntaxException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }
                    dialog.close();

                });

                Button Cancelar = new Button("Cancelar");
                Cancelar.addClickListener(clickEvent -> {
                    dialog.close();

                });

                HorizontalLayout botones = new HorizontalLayout(Completado,Cancelar);

                dialog.add(tablaprods,botones);



            }
        });
    }


}
