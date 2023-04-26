package com.rest.frontend;

import Objects.Usuario;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

import java.awt.Label;

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

@Route
@PWA(name = "My Application", shortName = "My Application")
public class MainView extends AppLayout {



    private final RouterLink tablogin;

    private final RouterLink tabadmin;
    private  ListItem itemadmin;

    private final RouterLink tabjefe;
    private  ListItem itemjefe;

    private final RouterLink taboperario;
    private  ListItem itemoperario;
    private H1 title=new H1("Administración");

    public MainView() {


        Button logoutButton = new Button("Cerrar sesión", event -> {
            // Eliminar la sesión del usuario
            VaadinSession.getCurrent().getSession().invalidate();
            // Redirigir a la vista de inicio de sesión
            UI.getCurrent().navigate(LoginView.class);
            visibilizar();
        });


        this.tablogin =new RouterLink("Login",LoginView.class);
        this.tabadmin =new RouterLink("Admin",AdminView.class);
        this.tabjefe =new RouterLink("Jefe de obra",JefeView.class);
        this.taboperario =new RouterLink("Operario",OperarioView.class);
        itemadmin=new ListItem(tabadmin);
        itemjefe=new ListItem(tabjefe);
        itemoperario=new ListItem(taboperario);

        visibilizar();

        final UnorderedList list = new UnorderedList(new ListItem(tablogin),itemadmin,itemjefe,itemoperario);
        final Nav navigation = new Nav(list);
        addToDrawer(navigation);
        addToDrawer(logoutButton);
        setPrimarySection(Section.DRAWER);
        setDrawerOpened(false);

        final Header header = new Header(new DrawerToggle(), title);
        addToNavbar(header);








    }


    private RouterLink[] getRouterLinks() {
        return new RouterLink[] {tablogin, tabadmin};
    }

    
    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void visibilizar() {

        if( ((Usuario) VaadinSession.getCurrent().getAttribute("user"))==null) {
            itemadmin.setVisible(false);


        }else if( ((Usuario) VaadinSession.getCurrent().getAttribute("user")).getRol().equals("Admin")){
            itemadmin.setVisible(true);

        }else{
            itemadmin.setVisible(false);

        }



        if( ((Usuario) VaadinSession.getCurrent().getAttribute("user"))==null) {
            itemjefe.setVisible(false);


        }else if( ((Usuario) VaadinSession.getCurrent().getAttribute("user")).getRol().equals("Jefe de obra")){
            itemjefe.setVisible(true);

        }else{
            itemjefe.setVisible(false);

        }

        if( ((Usuario) VaadinSession.getCurrent().getAttribute("user"))==null) {
            itemoperario.setVisible(false);


        }else if( ((Usuario) VaadinSession.getCurrent().getAttribute("user")).getRol().equals("Operario")){
            itemoperario.setVisible(true);

        }else{
            itemoperario.setVisible(false);

        }



    }

}
