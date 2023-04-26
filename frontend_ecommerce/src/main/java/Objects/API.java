package Objects;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class API {

    private static int backendPort=8080;
    private static String backendUrlPrefix="http://localhost:"+ backendPort+"%s";


    public Usuario buscarusuario(String nombre) throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/users/get?username="+nombre);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());

        if (response.body()==null || response.body().isEmpty()) {
            return null;
        }else{
            Gson gson=new Gson();
            Usuario usr = gson.fromJson(response.body(),new TypeToken<Usuario>(){}.getType());
            return usr;

        }

    }

    public void deleteuser(int id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/products/delete?id="+Integer.toString(id));
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void crearusuario(String nombre, String pwd,String rol) throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/users/create?username="+nombre+"&pwd="+pwd+"&rol="+rol);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void edituser(String id, String username, String pwd, String rol) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/users/edit?id="+(id)+"&username="+(username)+"&pwd="+(pwd)+"&rol="+rol);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public String getallusers() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/users/getall");
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public String getallproducts() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/products/getall");
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        
        return response.body();

    }

    public String getproduct(String id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/products/get?id="+id);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        
        return response.body();

    }

    public void editproduct(String id, String stock, String price, String name,String proveedor,String ubicacion, String umbral) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/products/edit?id="+(id)+"&stock="+(stock)+"&price="+(price)+"&name="+name+"&proveedor="+proveedor+"&ubicacion="+ubicacion+"&umbral="+umbral);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        

    }

    public void createproduct(String id,String stock, String price, String name,String proveedor,String ubicacion, String umbral) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/products/create?id="+id+"&stock="+stock+"&price="+price+"&name="+name+"&proveedor="+proveedor+"&ubicacion="+ubicacion+"&umbral="+umbral);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        

    }

    public void deleteproduct(String id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/products/delete?id="+id);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        

    }


    //
    public String getallpedidoscliente() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/pedidoclientes/getall");
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        
        return response.body();

    }

    public String getpedidoclientes(String id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/pedidoclientes/get?id="+id);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        
        return response.body();

    }



    public void deletepedidoclientes(String id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/pedidoclientes/delete?id="+id);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        

    }

    public String getalllistaspicking() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/listaspicking/getall");
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());

        return response.body();

    }

    public String getalllistaspickingpendientes() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/listaspicking/getpendientes");
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());

        return response.body();

    }

    public String getlistapicking(String id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/listaspicking/get?id="+id);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());

        return response.body();

    }



    public void deletepelistapickings(String id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/listaspicking/delete?id="+id);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public String getallpedidosprov() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/pedidosprov/getall");
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());

        return response.body();

    }

    public String getpedidoprov(String id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/pedidosprov/get?id="+id);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());

        return response.body();

    }

    public void createpedidoprov(String idproduct,String proveedor, String cantidad) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/pedidosprov/create?idproducto="+idproduct+"&proveedor="+proveedor+"&cantidad="+cantidad);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void deletepedidoprov(String id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/pedidosprov/delete?id="+id);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void addPedido(String id, String username, String direccion, String empresa, String estado) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/pedidoclientes/createpedido?id="+id+"&username="+username+"&direccion="+direccion+"&empresa="+empresa+"&estado="+estado);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        System.out.println(fullUrl);
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
    }


    public void addPicking(String id, ArrayList<Product> products, String estado) throws URISyntaxException, IOException, InterruptedException{
        Pickinglist pickinglist = new Pickinglist(id, products, estado);
        Gson gson = new Gson();
        String jsonRequestBody = gson.toJson(pickinglist);
        String fullUrl=String.format(backendUrlPrefix,"/listaspicking/createpedido");
        //fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
    }

    public String getPedidoUser(String nombre) throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/pedidoclientes/getPediosUser?username="+nombre);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        if (response.body()==null || response.body().isEmpty()) {
            return null;
        }else{
            return response.body();

        }

    }
}
