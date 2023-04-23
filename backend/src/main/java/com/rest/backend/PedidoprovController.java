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
@RequestMapping("/pedidosprov")
public class PedidoprovController {


    private List<Pedidoprov> Pedidoprovs=new ArrayList<Pedidoprov>();

    @GetMapping("/getall")
    public List<Pedidoprov> getall(){
        this.loadjson();
        return Pedidoprovs;
    }

    @GetMapping("/get")
    public Pedidoprov get(@RequestParam(value = "id",defaultValue = "-1")String id){
        this.loadjson();

        for (Pedidoprov prod : Pedidoprovs){
            if(prod.getId()==(id)){
                return prod;
            }

        }

        return null;

    }

    @GetMapping("/create")
    public void create(@RequestParam String idproducto,@RequestParam String proveedor,@RequestParam int cantidad) throws FileNotFoundException {
        this.loadjson();
        int tempid=0;

        Pedidoprovs.add(new Pedidoprov(String.valueOf(Pedidoprovs.size()),idproducto,proveedor,cantidad));

        this.savejson();


    }

    @GetMapping("/delete")
    public void delete(@RequestParam(value = "id",defaultValue = "-1")String id) throws FileNotFoundException {

        this.loadjson();
        for (int i = 0; i < Pedidoprovs.size(); i++) {
            if (Pedidoprovs.get(i).getId().equals(id)) {
                Pedidoprovs.remove(i);
                this.savejson();
            }
        }
    }

    @GetMapping("/loadjson")
    public void loadjson(){
        Gson gson= new Gson();
        Reader reader=null;

        try {
            reader = Files.newBufferedReader(Paths.get("./src/main/resources/pedidosprov.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.Pedidoprovs=gson.fromJson(reader,new TypeToken<List<Pedidoprov>>(){}.getType());


    }


    public void savejson() throws FileNotFoundException {
        Gson gson=new Gson();
        PrintWriter writer= new PrintWriter("./src/main/resources/pedidosprov.json");
        writer.print(gson.toJson(this.Pedidoprovs));
        writer.close();

    }
    
}
