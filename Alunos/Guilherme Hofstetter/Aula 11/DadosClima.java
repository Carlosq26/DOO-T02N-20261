package com.clima;

public class DadosClima {

    private final String enderecoResolvido;
    private final double tempAtual;
    private final double tempMax;
    private final double tempMin;
    private final double umidade;
    private final String condicao;
    private final double precipitacao;
    private final double velocidadeVento;
    private final double direcaoVento;

    public DadosClima(
            String enderecoResolvido,
            double tempAtual,
            double tempMax,
            double tempMin,
            double umidade,
            String condicao,
            double precipitacao,
            double velocidadeVento,
            double direcaoVento) {

        this.enderecoResolvido = enderecoResolvido;
        this.tempAtual         = tempAtual;
        this.tempMax           = tempMax;
        this.tempMin           = tempMin;
        this.umidade           = umidade;
        this.condicao          = condicao;
        this.precipitacao      = precipitacao;
        this.velocidadeVento   = velocidadeVento;
        this.direcaoVento      = direcaoVento;
    }

    public String getEnderecoResolvido() { return enderecoResolvido; }
    public double getTempAtual()         { return tempAtual; }
    public double getTempMax()           { return tempMax; }
    public double getTempMin()           { return tempMin; }
    public double getUmidade()           { return umidade; }
    public String getCondicao()          { return condicao; }
    public double getPrecipitacao()      { return precipitacao; }
    public double getVelocidadeVento()   { return velocidadeVento; }
    public double getDirecaoVento()      { return direcaoVento; }

    public String getDirecaoVentoTexto() {
        double d = direcaoVento < 0 ? direcaoVento + 360 : direcaoVento;
        if (d < 22.5)  return "N";
        if (d < 67.5)  return "NE";
        if (d < 112.5) return "L";
        if (d < 157.5) return "SE";
        if (d < 202.5) return "S";
        if (d < 247.5) return "SO";
        if (d < 292.5) return "O";
        if (d < 337.5) return "NO";
        return "N";
    }
}
