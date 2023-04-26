package com.rest.backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pedidoclientes")
public class PedidoclienteController {


    private List<Pedidocliente> Pedidoclientes=new ArrayList<Pedidocliente>();

    @GetMapping("/getall")
    public List<Pedidocliente> getall(){
        this.loadjson();
        return Pedidoclientes;
    }

    @GetMapping("/get")
    public Pedidocliente get(@RequestParam(value = "id",defaultValue = "-1")String id){
        this.loadjson();

        for (Pedidocliente prod : Pedidoclientes){
            if(prod.getId()==(id)){
                return prod;
            }

        }

        return null;

    }



    @GetMapping("/delete")
    public void delete(@RequestParam(value = "id",defaultValue = "-1")String id) throws FileNotFoundException {

        this.loadjson();
        for (int i = 0; i < Pedidoclientes.size(); i++) {
            if (Pedidoclientes.get(i).getId().equals(id)) {
                Pedidoclientes.remove(i);
                this.savejson();
            }
        }
    }
    @GetMapping("/pedidolisto")
    public void pedidolisto(@RequestParam(value = "id",defaultValue = "-1")String id) throws FileNotFoundException {

        this.loadjson();
        for (int i = 0; i < Pedidoclientes.size(); i++) {
            if (Pedidoclientes.get(i).getId().equals(id)) {
                Pedidocliente temp=Pedidoclientes.get(i);
                temp.setEstado("preparado");
                Pedidoclientes.set(i,temp);

                this.savejson();
            }
        }
    }

    @GetMapping("/createpedido")
    public void create(@RequestParam String id, @RequestParam String username, @RequestParam String direccion, @RequestParam String empresa, @RequestParam String estado) throws FileNotFoundException {

        Pedidocliente pedido = new Pedidocliente(id,username,direccion,empresa,estado);

        System.out.println(pedido.getUsername());
        this.loadjson();

        System.out.println(Pedidoclientes.size());
        Pedidoclientes.add(pedido);
        System.out.println(Pedidoclientes.size());
        this.savejson();

    }

    @GetMapping("/getPediosUser")
    public List<Pedidocliente> getUserPedidos(@RequestParam String username){

        List<Pedidocliente> pedidosUser = new ArrayList<Pedidocliente>();
        this.loadjson();

        for (Pedidocliente prod : Pedidoclientes){
            if(prod.getUsername().equals(username)){
                pedidosUser.add(prod);
            }
        }
        return pedidosUser;

    }

    @GetMapping("/loadjson")
    public void loadjson(){
        Gson gson= new Gson();
        Reader reader=null;

        try {
            reader = Files.newBufferedReader(Paths.get("./src/main/resources/pedidosclientes.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.Pedidoclientes=gson.fromJson(reader,new TypeToken<List<Pedidocliente>>(){}.getType());


    }


    public void savejson() throws FileNotFoundException {
        Gson gson=new Gson();
        PrintWriter writer= new PrintWriter("./src/main/resources/pedidosclientes.json");
        writer.print(gson.toJson(this.Pedidoclientes));
        writer.close();

    }

}
