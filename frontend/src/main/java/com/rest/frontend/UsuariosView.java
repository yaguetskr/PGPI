package com.rest.frontend;


import Objects.API;
import Objects.Usuario;
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

@Route(value = "usuarios",layout=MainView.class)
public class UsuariosView extends VerticalLayout {
    public UsuariosView() throws Exception {

        API api=new API();
        String listajson= null;
        try {
            listajson = api.getallusers();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        Gson gson = new Gson();

        Type userListType = new TypeToken<List<Usuario>>(){}.getType();
        List<Usuario> lista = gson.fromJson(listajson, userListType);
        //List<Usuario> lista = gson.fromJson(listajson,new TypeToken<List<Usuario>>(){}.getType());

        Grid<Usuario> tabla=new Grid<>();

        Grid.Column<Usuario> idcol =tabla.addColumn(Usuario::getId).setHeader("ID:");
        Grid.Column<Usuario> namecol =tabla.addColumn(Usuario::getUsername).setHeader("Nombre de usuario:");
        Grid.Column<Usuario> rolcol =tabla.addColumn(Usuario::getRol).setHeader("Rol:");

        tabla.setItems(lista);
        add(tabla);



        tabla.addSelectionListener(selection -> {
            Optional<Usuario> optional = selection.getFirstSelectedItem();
            if (optional.isPresent()) {

                Dialog dialog = new Dialog();
                add(dialog);
                dialog.open();

                int i=lista.indexOf(optional.get());

                VerticalLayout layoutdialog= new VerticalLayout();
                layoutdialog.add(new H3("Editar the Usuario "+(optional.get().getId() ) +":"));

                layoutdialog.add("Name:");
                TextField nametf= new TextField();
                nametf.setValue(optional.get().getUsername());
                layoutdialog.add(nametf);

                layoutdialog.add("Rol:");
                Select<String> roldd = new Select<>();
                roldd.setItems("Cliente", "Operario", "Jefe de obra","Admin");
                roldd.setValue(optional.get().getRol());
                layoutdialog.add(roldd);






                Button Guardar = new Button("Guardar");
                Guardar.addClickListener(clickEvent -> {

                    String temp;
                    try {
                        //api.editUsuario(Integer.toString(optional.get().getId()),stocktf.getValue(),pricetf.getValue(),nametf.getValue());
                        temp = api.getallusers();
                        Notification.show("Edited succesfully");
                        List<Usuario> templist = gson.fromJson(temp,new TypeToken<List<Usuario>>(){}.getType());
                        tabla.setItems(templist);



                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                    dialog.close();

                });

                Button Cancelar = new Button("Cancelar");
                Cancelar.addClickListener(clickEvent -> {
                    dialog.close();

                });

                HorizontalLayout botones = new HorizontalLayout(Guardar, Cancelar);

                dialog.add(layoutdialog,botones);



            }
        });


        Button newUsuario = new Button("Create new Usuario");
        add(newUsuario);
        newUsuario.addClickListener(clickEvent -> {

            Dialog dialog = new Dialog();
            add(dialog);
            dialog.open();
            VerticalLayout layoutdialog = new VerticalLayout();
            layoutdialog.add(new H3("Create new Usuario:"));
            layoutdialog.add("Nombre de usuario:");
            TextField usernametf= new TextField();
            layoutdialog.add(usernametf);

            layoutdialog.add("Contraseña:");
            TextField pwdtf= new TextField();
            layoutdialog.add(pwdtf);

            layoutdialog.add("Rol:");
            Select<String> roldd = new Select<>();
            roldd.setItems("Cliente", "Operario", "Jefe de obra","Admin");
            layoutdialog.add(roldd);


            Button Guardar = new Button("Guardar");

            Guardar.addClickListener(clickEvent2 -> {

                try {
                    api.crearusuario(usernametf.getValue(),pwdtf.getValue(),roldd.getValue());
                    Notification.show("Usuario added succesfully");
                    String temp = api.getallusers();
                    List<Usuario> templist = gson.fromJson(temp,new TypeToken<List<Usuario>>(){}.getType());
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



            HorizontalLayout botones = new HorizontalLayout(Guardar, Cancel);

            dialog.add(layoutdialog,botones);


        });




        Button deleteUsuario = new Button("Delete Usuario");
        add(deleteUsuario);
        deleteUsuario.addClickListener(clickEvent -> {

            Dialog dialog = new Dialog();
            add(dialog);
            dialog.open();
            VerticalLayout layoutdialog = new VerticalLayout();
            layoutdialog.add(new H3("Delete Usuario:"));

            Select<Usuario> select = new Select<>();
            select.setLabel("Usuario ID");
            select.setItemLabelGenerator(Usuario::getUsername);
            select.setItems(lista);
            layoutdialog.add(select);




            Button Delete = new Button("Delete");

            Delete.addClickListener(clickEvent2 -> {

                Dialog confirm=new Dialog();
                add(confirm);
                confirm.open();
                VerticalLayout layoutconfirm=new VerticalLayout();
                layoutconfirm.add(new H3("Are you sure you want to delete Usuario "+select.getValue().getId()+" ("+select.getValue().getUsername()+")?"));


                Button yes=new Button("Yes");

                yes.addClickListener(yesevent -> {
                    try {
                        api.deleteuser(select.getValue().getId());
                        Notification.show("Usuario deleted succesfully");
                        String temp = api.getallusers();
                        List<Usuario> templist = gson.fromJson(temp,new TypeToken<List<Usuario>>(){}.getType());
                        tabla.setItems(templist);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });

                Button no=new Button("No");

                no.addClickListener(noevent -> {
                    confirm.close();
                });
                layoutconfirm.add(new HorizontalLayout(yes,no));


                confirm.add(layoutconfirm);

            });

            Button Cancel = new Button("Cancel");

            Cancel.addClickListener(clickEvent2 -> {
                dialog.close();
            });



            HorizontalLayout botones = new HorizontalLayout(Delete, Cancel);

            dialog.add(layoutdialog,botones);


        });


    }

}
