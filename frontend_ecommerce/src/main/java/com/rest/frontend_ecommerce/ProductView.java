package com.rest.frontend_ecommerce;

import Objects.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vaadin.flow.server.VaadinSession;

import javax.swing.*;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and
 * use @Route annotation to announce it in a URL as a Spring managed
 * bean.
 * Use the @PWA annotation make the application installable on phones,
 * tablets and some desktop browsers.
 * <p>
 * A new instance of this class is created for every new user and every
 * browser tab/window.
 */
@Route(value = "shop", layout=MainView.class)
public class ProductView extends VerticalLayout {
    Grid<Product> productGrid;
    static Usuario user = (Usuario) VaadinSession.getCurrent().getAttribute("user");
    static String username = user.getUsername();
    List<Product> lista;
    List<Product> listaP = new ArrayList<Product>();
    private String imageAddress = "https://m.media-amazon.com/images/S/aplus-media/sota/4f35871c-62f0-4ed4-8278-f521b0909723._SR285,285_.jpg";

    public Dialog mostrarCompra(){
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Compra de Productos");

        VerticalLayout dialogLayout = createDialogLayout(dialog, listaP);
        dialog.add(dialogLayout);

        return dialog;
    }

    public static void meterProducto(Pedidocliente pedidocliente) throws URISyntaxException, IOException, InterruptedException, IOException {
        API api = new API();
        api.addPedido(pedidocliente.getId(),pedidocliente.getUsername(),pedidocliente.getDireccion(),pedidocliente.getEmpresa(),pedidocliente.getEstado());
    }
    public static void meterPicking(Pickinglist listacliente) throws URISyntaxException, IOException, InterruptedException, IOException {
        API api = new API();
        api.addPicking(listacliente.getId(),listacliente.getLista(),listacliente.getEstado());
    }
    private static <PopupView> VerticalLayout createDialogLayout(Dialog dialog, List<Product> listaProd) {
        H2 headline = new H2("Terminar pedido");
        headline.getStyle().set("margin", "var(--lumo-space-m) 0 0 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        Dialog dialogListEmpty = new Dialog();
        dialogListEmpty.getElement().setAttribute("aria-label", "Employee list");
        H2 listEmptyMSG = new H2("Tu carrito está vacío");
        listEmptyMSG.getStyle().set("margin", "var(--lumo-space-m) 0")
                .set("font-size", "1.5em").set("font-weight", "bold");
        Button closeButtonEmpty = new Button("Close");
        closeButtonEmpty.addClickListener(e -> dialogListEmpty.close());
        dialogListEmpty.add(listEmptyMSG,closeButtonEmpty);

        HorizontalLayout nameInfo = new HorizontalLayout();
        TextField nombre = new TextField("Nombre");
        nombre.setRequired(true);
        nombre.setErrorMessage("Campo obligatorio");
        TextField apellido1 = new TextField("Primmer Apellido");
        apellido1.setRequired(true);
        apellido1.setErrorMessage("Campo obligatorio");
        TextField apellido2 = new TextField("Segundo Apellido");
        apellido2.setRequired(true);
        apellido2.setErrorMessage("Campo obligatorio");
        nameInfo.add(nombre,apellido1,apellido2);
        HorizontalLayout cardInfo = new HorizontalLayout();
        TextField cardF = new TextField("Número de Tarjeta");
        cardF.setMinLength(16);
        cardF.setMaxLength(16);
        cardF.setRequired(true);
        cardF.setErrorMessage("Campo obligatorio");
        TextField cardB = new TextField("CVV");
        cardB.setMinLength(3);
        cardB.setMaxLength(3);
        cardB.setRequired(true);
        cardB.setErrorMessage("Campo obligatorio");
        cardInfo.add(cardF, cardB);
        TextField direccion = new TextField("Dirección");
        direccion.setRequired(true);
        direccion.setErrorMessage("Campo obligatorio");
        HorizontalLayout dirInfo = new HorizontalLayout();
        TextField portal = new TextField("Portal");
        portal.setMinLength(1);
        portal.setMaxLength(3);
        portal.setRequired(true);
        portal.setErrorMessage("Campo obligatorio");
        TextField piso = new TextField("Piso");
        piso.setRequired(true);
        piso.setErrorMessage("Campo obligatorio");
        dirInfo.add(portal,piso);
        ComboBox<String> comboBox = new ComboBox<>("Servicio de Paquetería");
        comboBox.setAllowCustomValue(true);
        comboBox.setItems("FedEx", "Correos", "UPS");
        comboBox.setRequired(true);
        comboBox.setErrorMessage("Campo obligatorio");
        VerticalLayout fieldLayout = new VerticalLayout(nameInfo,cardInfo,direccion,dirInfo,comboBox);
        fieldLayout.setSpacing(false);
        fieldLayout.setPadding(false);
        fieldLayout.setAlignItems(FlexComponent.Alignment.STRETCH);

        Button cancelButton = new Button("Volver", e -> dialog.close());
        Button saveButton = new Button("Realizar Compra", e -> {
            if(listaProd.size()>0){
                /*byte[] array = new byte[3];
                new Random().nextBytes(array);
                String generatedString = new String(array, Charset.forName("US-ASCII"));*/
                try {
                    Pedidocliente pedido = new Pedidocliente(
                            String.valueOf(nombre.getValue().charAt(0)+apellido1.getValue().charAt(0)+apellido2.getValue().charAt(0)),//generatedString,
                            username,
                            direccion.getValue()+", "+portal.getValue()+", "+piso.getValue(),
                            comboBox.getValue(),
                            "pendiente");
                    System.out.println(pedido.getUsername());
                    meterProducto(pedido);
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    meterPicking(new Pickinglist(
                            String.valueOf(nombre.getValue().charAt(0)+apellido1.getValue().charAt(0)+apellido2.getValue().charAt(0)),//generatedString,
                            new ArrayList<Product>(listaProd),
                            "pendiente"));
                } catch (URISyntaxException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                dialog.close();
                UI.getCurrent().getPage().reload();
            }
            else{
                dialogListEmpty.open();
                dialog.close();
            }});

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttonLayout = new  HorizontalLayout(cancelButton,
                saveButton);
        buttonLayout
                .setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        VerticalLayout dialogLayout = new VerticalLayout(headline, fieldLayout,
                buttonLayout);
        dialogLayout.setPadding(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.getStyle().set("width", "600px").set("max-width", "100%");

        return dialogLayout;
    }
    public ProductView() {
        VerticalLayout mainVL = new VerticalLayout();
        API api=new API();
        String listajson= null;
        try {
            listajson = api.getallproducts();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        H1 titleWeb = new H1("Bienvenido, " + username);
        titleWeb.getStyle()
                .set("padding", "var(--lumo-space-m)");
        add(titleWeb);
        Gson gson = new Gson();

        Type productListType = new TypeToken<List<Product>>(){}.getType();
        lista = gson.fromJson(listajson, productListType);


        H2 title2=new H2(" Carrito");
        VerticalLayout layoutSide = new VerticalLayout();
        layoutSide.setWidth("40%");
        layoutSide.setPadding(false);
        layoutSide.getStyle().set("background-color", "#F5F5F5");
        layoutSide.getElement().getStyle().set("flex-grow", "1");

        productGrid = new Grid<>(Product.class, false);
        productGrid.addColumn(Product::getName).setHeader("Producto");
        productGrid.addColumn(Product::getPrice).setHeader("€");
        productGrid.setItems(listaP);

        Button comprar = new Button("Terminar Pedido");
        comprar.addClickListener(e -> mostrarCompra().open());
        layoutSide.setAlignItems(FlexComponent.Alignment.CENTER);

        Button mostrarPedidos = new Button("Mostrar Pedidos");
        mostrarPedidos.addClickListener(e -> mostrarPedidos().open());
        layoutSide.setAlignItems(FlexComponent.Alignment.CENTER);

        layoutSide.add(title2,productGrid,comprar,mostrarPedidos);

        VerticalLayout prodLayout = new VerticalLayout();
        prodLayout.setWidth("60%");

        for (Product product : lista) {
            prodLayout.add(createProductComponent(product.getName(),
                    product.getPrice()));
            prodLayout.add(new Hr());
        }

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.add(layoutSide, prodLayout);

        mainVL.add(mainLayout);
        add(mainVL);

    }
    public Dialog mostrarPedidos(){
        Dialog dialog = new Dialog();
        dialog.getElement().setAttribute("aria-label", "Estado de Pedidos");

        VerticalLayout dialogLayout = getPedidosLayout(dialog);
        dialog.add(dialogLayout);

        return dialog;
    }
    private VerticalLayout getPedidosLayout(Dialog dialog){
        API api=new API();
        List<Pedidocliente> listajsonPedidos;
        Gson gson = new Gson();
        try {
            String pedidosJSON = api.getPedidoUser(username);
            System.out.println(pedidosJSON);
            listajsonPedidos = gson.fromJson(pedidosJSON, new TypeToken<List<Pedidocliente>>(){}.getType());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        VerticalLayout pedidosLayout = new VerticalLayout();
        H2 title=new H2(" Pedidos");
        pedidosLayout.setWidth("100%");
        pedidosLayout.setPadding(false);
        pedidosLayout.getStyle().set("background-color", "#F5F5F5");
        pedidosLayout.getElement().getStyle().set("flex-grow", "1");

        Grid<Pedidocliente> pedidosGrid = new Grid();
        pedidosGrid.addColumn(Pedidocliente::getId).setHeader("Id");
        pedidosGrid.addColumn(Pedidocliente::getEstado).setHeader("Estado");
        pedidosGrid.setItems(listajsonPedidos);
        pedidosLayout.add(title,pedidosGrid);
        pedidosLayout.getStyle().set("width", "400px").set("max-width", "100%");
        return pedidosLayout;
    }

    private Component createProductComponent(String title, double price) {
        HorizontalLayout mainLayout = new HorizontalLayout();
        Image image = new Image(imageAddress, "");
        image.getStyle().set("border-radius","--lumo-border-radius-m");
        image.setWidth("30%");
        image.setHeight("40%");

        VerticalLayout itemLayout = new VerticalLayout();
        H3 titleComponent = new H3(title);
        Span priceComponent = new Span("€ " + price);
        itemLayout.add(titleComponent, priceComponent);
        Button buttonMas = new Button(new Icon(VaadinIcon.PLUS));
        buttonMas.addThemeVariants(ButtonVariant.LUMO_ICON);
        buttonMas.getElement().setAttribute("aria-label", "Add item");
        buttonMas.addClickListener(event -> {listaP.add(getElementFrom(title)); productGrid.setItems(listaP);});
        mainLayout.add(image, itemLayout, buttonMas);
        mainLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        return mainLayout;
    }

    private Product getElementFrom(String title) {
        for (Product product :lista) {
            if(product.getName() == title){
                return product;
            }
        }
        return lista.get(0);
    }



}
