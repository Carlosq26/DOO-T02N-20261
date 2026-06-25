package com.clima;

import java.io.InputStream;
import java.util.Properties;

public class ConfiguracaoApp {

    private static final String CHAVE_API = "api.key";

    public static String lerApiKey() throws ClimaException {
        Properties props = new Properties();

        try (InputStream input = ConfiguracaoApp.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new ClimaException(ClimaException.Tipo.CONFIG_NAO_ENCONTRADA);
            }

            props.load(input);

        } catch (ClimaException e) {
            throw e;
        } catch (Exception e) {
            throw new ClimaException(ClimaException.Tipo.CONFIG_NAO_ENCONTRADA);
        }

        String chave = props.getProperty(CHAVE_API, "").trim();

        if (chave.isEmpty() || chave.equals("SUA_CHAVE_AQUI")) {
            throw new ClimaException(
                ClimaException.Tipo.CHAVE_INVALIDA,
                "Edite src/main/resources/config.properties e insira sua chave."
            );
        }

        return chave;
    }
}   