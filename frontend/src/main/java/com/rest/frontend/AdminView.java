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
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.io.IOException;
import java.net.URISyntaxException;


@Route(value = "admin",layout=MainView.class)
public class AdminView extends VerticalLayout {


    public AdminView() throws Exception {
        API api = new API();

        HorizontalLayout topMenu = new HorizontalLayout();
        topMenu.setPadding(true);
        topMenu.setWidthFull();
        add(topMenu);

        Button inventarioBtn = new Button("Inventario");
        inventarioBtn.addClickListener(e -> UI.getCurrent().navigate("inventario"));

        Button importarBtn = new Button("Importar desde archivo");
        importarBtn.addClickListener(e -> UI.getCurrent().navigate("importarxcl"));

        Button proveedoresBtn = new Button("Pedidos a proveedores");
        proveedoresBtn.addClickListener(e -> UI.getCurrent().navigate("proveedores"));

        Button clientesBtn = new Button("Pedidos de clientes");
        clientesBtn.addClickListener(e -> UI.getCurrent().navigate("clientes"));

        Button usuariosBtn = new Button("Gestionar usuarios");
        usuariosBtn.addClickListener(e -> UI.getCurrent().navigate("usuarios"));

        Button pickingListBtn = new Button("Listas de picking");
        pickingListBtn.addClickListener(e -> UI.getCurrent().navigate("pickinglist"));

        Button pickingspendientesBtn = new Button("Pickings por preparar");
        pickingspendientesBtn.addClickListener(e -> UI.getCurrent().navigate("pickingspendientes"));


        topMenu.add(
                new RouterLink(inventarioBtn.getText(), InventarioView.class),
                new RouterLink(importarBtn.getText(), VistaCargarArchivo.class),
                new RouterLink(proveedoresBtn.getText(), PedidosprovView.class),
                new RouterLink(clientesBtn.getText(), PedidosclienteView.class),
                new RouterLink(usuariosBtn.getText(), UsuariosView.class),
                new RouterLink(pickingListBtn.getText(), PickingListView.class),
                new RouterLink(pickingspendientesBtn.getText(), PickingspendientesView.class)

        );

        // Configuración de los botones
        configureButton(inventarioBtn);
        configureButton(importarBtn);
        configureButton(proveedoresBtn);
        configureButton(clientesBtn);
        configureButton(usuariosBtn);
        configureButton(pickingListBtn);

    }

    private void configureButton(Button button) {
        button.setWidthFull();
        button.getStyle().set("margin-right", "5px");
    }

}