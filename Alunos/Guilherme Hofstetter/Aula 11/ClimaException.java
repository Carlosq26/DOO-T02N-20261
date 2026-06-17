package com.clima;

public class ClimaException extends Exception {

    public enum Tipo {
        CIDADE_NAO_ENCONTRADA,
        CHAVE_INVALIDA,
        LIMITE_EXCEDIDO,
        SEM_CONEXAO,
        RESPOSTA_INVALIDA,
        CONFIG_NAO_ENCONTRADA,
        ERRO_INESPERADO
    }

    private final Tipo tipo;

    public ClimaException(Tipo tipo) {
        super(mensagem(tipo));
        this.tipo = tipo;
    }

    public ClimaException(Tipo tipo, String detalhe) {
        super(mensagem(tipo) + "\n" + detalhe);
        this.tipo = tipo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    private static String mensagem(Tipo tipo) {
        switch (tipo) {
            case CIDADE_NAO_ENCONTRADA:  return "Cidade nao encontrada. Verifique o nome e tente novamente.";
            case CHAVE_INVALIDA:         return "Chave de API invalida. Verifique o arquivo config.properties.";
            case LIMITE_EXCEDIDO:        return "Limite de requisicoes excedido. Tente mais tarde.";
            case SEM_CONEXAO:            return "Sem conexao com a internet.";
            case RESPOSTA_INVALIDA:      return "Resposta inesperada da API.";
            case CONFIG_NAO_ENCONTRADA:  return "Arquivo config.properties nao encontrado.\nCrie o arquivo na raiz do projeto com: api.key=SUA_CHAVE";
            case ERRO_INESPERADO:        return "Ocorreu um erro inesperado.";
            default:                     return "Erro desconhecido.";
        }
    }
}
