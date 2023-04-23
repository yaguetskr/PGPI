package com.rest.frontend;

import Objects.API;
import Objects.Usuario;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;

import java.io.IOException;
import java.net.URISyntaxException;

@Route(value = "admin",layout=MainView.class)
public class AdminView extends VerticalLayout {


    public AdminView() throws Exception {
        API api=new API();
        HorizontalLayout columnLayout1=new HorizontalLayout();
        HorizontalLayout columnLayout2=new HorizontalLayout();
        HorizontalLayout columnLayout3=new HorizontalLayout();
        add(columnLayout1,columnLayout2,columnLayout3);


        Button inventariobt = new Button("Inventario");
        inventariobt.addClickListener(e -> {
            UI.getCurrent().navigate("inventario");
        });

        RouterLink enlace = new RouterLink("Inventario", InventarioView.class);




        Button proveedoresbt = new Button("Pedidos a proveedores");
        proveedoresbt.addClickListener(e -> {
            UI.getCurrent().navigate("proveedores");
        });

        RouterLink enlace2 = new RouterLink("Pedidos a proveedores", PedidosprovView.class);


        Button clientesbt = new Button("Pedidos de clientes");
        clientesbt.addClickListener(e -> {
            UI.getCurrent().navigate("clientes");
        });

        RouterLink enlace3 = new RouterLink("Pedidos de clientes", PedidosclienteView.class);

        columnLayout1.add(enlace,enlace2,enlace3);


        Button usuariosbt = new Button("Gestionar usuarios");
        usuariosbt.addClickListener(e -> {
            UI.getCurrent().navigate("usuarios");
        });

        RouterLink enlace4 = new RouterLink("Gestionar usuarios", UsuariosView.class);




        Button pickinglistbt = new Button("Listas de picking");
        pickinglistbt.addClickListener(e -> {
            UI.getCurrent().navigate("pickinglist");
        });

        RouterLink enlace5 = new RouterLink("Listas de picking", PedidosclienteView.class);



        Button analysisbt = new Button("Analytics");
        analysisbt.addClickListener(e -> {
            UI.getCurrent().navigate("analytics");
        });

        RouterLink enlace6 = new RouterLink("Analytics", AnalyticsView.class);




        columnLayout2.add(enlace4,enlace5,enlace6);


    }

}