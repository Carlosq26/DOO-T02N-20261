import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.Scanner;
import java.net.http.HttpResponse;

class Main {
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        try{
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("")).GET().build();
        }catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println();
        System.out.println("Digite o nome da cidade");
        String cidade = scan.nextLine();
    }
}