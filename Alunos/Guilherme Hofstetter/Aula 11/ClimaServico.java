package com.clima;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ClimaServico {

    private static final String BASE_URL =
        "https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/";

    private final String apiKey;
    private final ObjectMapper mapper;

    public ClimaServico(String apiKey) {
        this.apiKey  = apiKey;
        this.mapper  = new ObjectMapper();
    }

    public DadosClima buscar(String cidade) throws ClimaException {
        String json = fazerRequisicao(cidade);
        return parsear(json);
    }

    private String fazerRequisicao(String cidade) throws ClimaException {
        try {
            String cidadeCodificada = URLEncoder.encode(cidade, StandardCharsets.UTF_8.name());
            String urlStr = BASE_URL + cidadeCodificada + "/today"
                + "?key="        + apiKey
                + "&unitGroup=metric"
                + "&lang=pt"
                + "&include=current,days"
                + "&elements=temp,tempmax,tempmin,humidity,conditions,precip,windspeed,winddir";

            HttpURLConnection conexao = (HttpURLConnection) new URL(urlStr).openConnection();
            conexao.setRequestMethod("GET");
            conexao.setConnectTimeout(8000);
            conexao.setReadTimeout(8000);

            int codigo = conexao.getResponseCode();

            switch (codigo) {
                case 200:  break;
                case 401:  throw new ClimaException(ClimaException.Tipo.CHAVE_INVALIDA);
                case 404:  throw new ClimaException(ClimaException.Tipo.CIDADE_NAO_ENCONTRADA);
                case 429:  throw new ClimaException(ClimaException.Tipo.LIMITE_EXCEDIDO);
                default:   throw new ClimaException(ClimaException.Tipo.ERRO_INESPERADO, "HTTP " + codigo);
            }

            BufferedReader leitor = new BufferedReader(
                new InputStreamReader(conexao.getInputStream(), StandardCharsets.UTF_8)
            );
            StringBuilder resposta = new StringBuilder();
            String linha;
            while ((linha = leitor.readLine()) != null) {
                resposta.append(linha);
            }
            leitor.close();
            return resposta.toString();

        } catch (ClimaException e) {
            throw e;
        } catch (java.net.UnknownHostException e) {
            throw new ClimaException(ClimaException.Tipo.SEM_CONEXAO);
        } catch (Exception e) {
            throw new ClimaException(ClimaException.Tipo.ERRO_INESPERADO, e.getMessage());
        }
    }

    private DadosClima parsear(String json) throws ClimaException {
        try {
            JsonNode raiz    = mapper.readTree(json);
            JsonNode current = raiz.get("currentConditions");
            JsonNode hoje    = raiz.get("days").get(0);

            return new DadosClima(
                raiz.get("resolvedAddress").asText(),
                current.get("temp").asDouble(),
                hoje.get("tempmax").asDouble(),
                hoje.get("tempmin").asDouble(),
                current.get("humidity").asDouble(),
                current.get("conditions").asText(),
                current.path("precip").asDouble(0.0),
                current.get("windspeed").asDouble(),
                current.get("winddir").asDouble()
            );

        } catch (Exception e) {
            throw new ClimaException(ClimaException.Tipo.RESPOSTA_INVALIDA, e.getMessage());
        }
    }
}
