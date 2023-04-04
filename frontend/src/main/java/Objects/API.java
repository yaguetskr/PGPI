package Objects;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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

    public void crearusuario(String nombre, String pwd) throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/users/create?username="+nombre+"&pwd="+pwd);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public String getall() throws URISyntaxException, IOException, InterruptedException {
        String fullUrl=String.format(backendUrlPrefix,"/products/getall");
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public String get(int id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/products/get?id="+Integer.toString(id));
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();

    }

    public void edit(String id,String stock,String price, String name) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/products/edit?id="+(id)+"&stock="+(stock)+"&price="+(price)+"&name="+name);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void create(String stock,String price, String name) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/products/create?stock="+stock+"&price="+price+"&name="+name);
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }

    public void delete(int id) throws URISyntaxException, IOException, InterruptedException {

        String fullUrl=String.format(backendUrlPrefix,"/products/delete?id="+Integer.toString(id));
        fullUrl = fullUrl.replaceAll(" " ,"%20");
        HttpRequest request= HttpRequest.newBuilder().uri(new URI(fullUrl)).GET().build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request,HttpResponse.BodyHandlers.ofString());


    }


}
