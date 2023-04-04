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
@RequestMapping("/users")
public class UsuarioController {
    private List<Usuario> users=new ArrayList<Usuario>();

    @GetMapping("/get")
    public Usuario get(@RequestParam(value = "username",defaultValue = "-1")String nombre){
        this.loadjson();



        for (Usuario usr : users){

            if(usr.getUsername().equals(nombre)){

                return usr;
            }

        }

        return null;

    }

    @GetMapping("/create")
    public void create(@RequestParam String username,@RequestParam String pwd) throws FileNotFoundException {
        this.loadjson();
        int tempid=0;

        users.add(new Usuario(username,pwd,"ADMIN"));

        this.savejson();


    }


    @GetMapping("/loadjson")
    public void loadjson(){
        Gson gson= new Gson();
        Reader reader=null;

        try {
            reader = Files.newBufferedReader(Paths.get("./src/main/resources/usuarios.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        this.users=gson.fromJson(reader,new TypeToken<List<Usuario>>(){}.getType());



    }


    public void savejson() throws FileNotFoundException {
        Gson gson=new Gson();
        PrintWriter writer= new PrintWriter("./src/main/resources/usuarios.json");
        writer.print(gson.toJson(this.users));
        writer.close();

    }
}
