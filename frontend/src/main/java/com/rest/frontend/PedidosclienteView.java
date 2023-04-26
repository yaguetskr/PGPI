package com.rest.frontend;

import Objects.API;
import Objects.Pedidocliente;
import Objects.Pickinglist;
import Objects.Product;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

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
        Grid.Column<Pedidocliente> empresacol = tabla.addColumn(Pedidocliente::getEmpresa).setHeader("Empresa de reparto:");

        tabla.setItems(lista);
        add(tabla);

        tabla.addSelectionListener(selection -> {
            Optional<Pedidocliente> optional = selection.getFirstSelectedItem();
            if (optional.isPresent()) {

                Dialog dialog = new Dialog();
                add(dialog);
                dialog.open();

                dialog.add(new H3("Editar the Usuario "+(optional.get().getId() ) +":"));

                Button Aceptar = new Button("Aceptar");
                Aceptar.addClickListener(clickEvent -> {


                    try {
                        generarPDF(optional.get(), "juan.pdf");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    dialog.close();

                });

                Button Cancelar = new Button("Cancelar");
                Cancelar.addClickListener(clickEvent -> {
                    dialog.close();

                });

                HorizontalLayout botones = new HorizontalLayout(Aceptar,Cancelar);

                dialog.add(botones);



            }
        });


    }
    public void generarPDF(Pedidocliente pedido, String nombreArchivo) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(nombreArchivo));
        document.open();
        document.add(new Paragraph("Etiqueta de envio del pedido ID: " + pedido.getId()));
        document.add(new Paragraph("Username: " + pedido.getUsername()));
        document.add(new Paragraph("Direccion: " + pedido.getDireccion()));
        document.add(new Paragraph("Empresa de reparto: " + pedido.getEmpresa()));
        document.close();
    }
}
