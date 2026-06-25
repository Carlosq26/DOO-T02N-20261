package api.tempo;

import java.io.InputStream;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BuscadorApiTempo {
    
    private final String API_KEY;
    private static final String ENDPOINT = "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private final HttpClient clienteHttp;
    private final ObjectMapper extratorJson;

    public BuscadorApiTempo() {
        this.API_KEY = carregarChaveDoArquivo();
        this.clienteHttp = HttpClient.newHttpClient();
        this.extratorJson = new ObjectMapper();
    }

    private String carregarChaveDoArquivo() {
        Properties propriedades = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalStateException("Arquivo config.properties não encontrado na pasta resources.");
            }
            propriedades.load(input);
            return propriedades.getProperty("api.key.weather");
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao carregar a chave da API: " + ex.getMessage());
        }
    }

    public RegistroClima consultarTempo(String nomeCidade) throws Exception {
        
        String parametroCidade = URLEncoder.encode(nomeCidade, StandardCharsets.UTF_8);
        String urlCompleta = String.format("%s%s?unitGroup=metric&lang=pt&include=current,days&key=%s&contentType=json", 
                                           ENDPOINT, parametroCidade, API_KEY);

        HttpRequest requisicao = HttpRequest.newBuilder()
                .uri(URI.create(urlCompleta))
                .GET()
                .build();

        
        HttpResponse<String> resposta = clienteHttp.send(requisicao, HttpResponse.BodyHandlers.ofString());

        if (resposta.statusCode() != 200) {
            throw new RuntimeException("Falha na API. Status HTTP: " + resposta.statusCode());
        }

        return extrairDadosManualmente(nomeCidade, resposta.body());
    }

    private RegistroClima extrairDadosManualmente(String localBuscado, String json) throws Exception {
        JsonNode raiz = extratorJson.readTree(json);
        
        JsonNode atual = raiz.path("currentConditions");
        JsonNode previsaoHoje = raiz.path("days").path(0);

        return new RegistroClima(
            raiz.path("resolvedAddress").asText(localBuscado),
            atual.path("temp").asDouble(),
            previsaoHoje.path("tempmax").asDouble(),
            previsaoHoje.path("tempmin").asDouble(),
            atual.path("humidity").asDouble(),
            atual.path("conditions").asText("Indefinido"),
            atual.path("precip").asDouble(),
            atual.path("windspeed").asDouble(),
            atual.path("winddir").asDouble()
        );
    }
}