package com.rest.backend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/listaspicking")
public class PickinglistController {


    private List<Pickinglist> Pickinglists=new ArrayList<Pickinglist>();

    @GetMapping("/getall")
    public List<Pickinglist> getall(){
        this.loadjson();
        return Pickinglists;
    }

    @GetMapping("/get")
    public Pickinglist get(@RequestParam(value = "id",defaultValue = "-1")String id){
        this.loadjson();

        for (Pickinglist prod : Pickinglists){
            if(prod.getId()==(id)){
                return prod;
            }

        }

        return null;

    }

    @GetMapping("/getpendientes")
    public List<Pickinglist> getpendientes(){
        this.loadjson();
        ArrayList<Pickinglist> objetosPendientes = new ArrayList<Pickinglist>();
        for (Pickinglist obj : Pickinglists) {
            if (obj.getEstado().equals("pendiente")) {
                objetosPendientes.add(obj);
            }
        }
        return objetosPendientes;
    }


    @GetMapping("/delete")
    public void delete(@RequestParam(value = "id",defaultValue = "-1")String id) throws FileNotFoundException {

        this.loadjson();
        for (int i = 0; i < Pickinglists.size(); i++) {
            if (Pickinglists.get(i).getId().equals(id)) {
                Pickinglists.remove(i);
                this.savejson();
            }
        }
    }

    @PostMapping("/createpedido")
    public void create(@RequestBody String pickinglist) throws FileNotFoundException {
        Gson gson = new Gson();
        Pickinglist pickinglistFinal = gson.fromJson(pickinglist, Pickinglist.class);
        this.loadjson();
        Pickinglists.add(pickinglistFinal);

        this.savejson();

    }

    @GetMapping("/loadjson")
    public void loadjson(){
        Gson gson= new Gson();
        Reader reader=null;

        try {
            reader = Files.newBufferedReader(Paths.get("./src/main/resources/listaspicking.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.Pickinglists=gson.fromJson(reader,new TypeToken<List<Pickinglist>>(){}.getType());


    }


    public void savejson() throws FileNotFoundException {
        Gson gson=new Gson();
        PrintWriter writer= new PrintWriter("./src/main/resources/listaspicking.json");
        writer.print(gson.toJson(this.Pickinglists));
        writer.close();

    }

}
