package com.rest.frontend;

import Objects.API;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;


@Route(value = "jefe",layout=MainView.class)
public class JefeView extends VerticalLayout{

    public JefeView() throws Exception {
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



        Button pickingListBtn = new Button("Listas de picking");
        pickingListBtn.addClickListener(e -> UI.getCurrent().navigate("pickinglist"));

        Button pickingspendientesBtn = new Button("Pickings por preparar");
        pickingspendientesBtn.addClickListener(e -> UI.getCurrent().navigate("pickingspendientes"));

        Button analyticsBtn = new Button("Analytics");
        analyticsBtn.addClickListener(e -> UI.getCurrent().navigate("analytics"));

        topMenu.add(
                new RouterLink(inventarioBtn.getText(), InventarioView.class),
                new RouterLink(importarBtn.getText(), VistaCargarArchivo.class),
                new RouterLink(proveedoresBtn.getText(), PedidosprovView.class),
                new RouterLink(clientesBtn.getText(), PedidosclienteView.class),

                new RouterLink(pickingListBtn.getText(), PickingListView.class),
                new RouterLink(pickingspendientesBtn.getText(), PickingspendientesView.class),
                new RouterLink(analyticsBtn.getText(), AnalyticsView.class)
        );

        // Configuración de los botones
        configureButton(inventarioBtn);
        configureButton(importarBtn);
        configureButton(proveedoresBtn);
        configureButton(clientesBtn);

        configureButton(pickingListBtn);
        configureButton(analyticsBtn);
    }

    private void configureButton(Button button) {
        button.setWidthFull();
        button.getStyle().set("margin-right", "5px");
    }
}
