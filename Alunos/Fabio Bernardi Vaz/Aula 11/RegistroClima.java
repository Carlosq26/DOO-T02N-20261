package api.tempo;

public class RegistroClima {
    private String localidade;
    private double temperaturaMomento;
    private double temperaturaMax;
    private double temperaturaMin;
    private double percentualUmidade;
    private String descricaoTempo;
    private double volumeChuva;
    private double velocidadeVento;
    private double grausVento;

    public RegistroClima(String localidade, double temperaturaMomento, double temperaturaMax, double temperaturaMin,
                         double percentualUmidade, String descricaoTempo, double volumeChuva,
                         double velocidadeVento, double grausVento) {
        this.localidade = localidade;
        this.temperaturaMomento = temperaturaMomento;
        this.temperaturaMax = temperaturaMax;
        this.temperaturaMin = temperaturaMin;
        this.percentualUmidade = percentualUmidade;
        this.descricaoTempo = descricaoTempo;
        this.volumeChuva = volumeChuva;
        this.velocidadeVento = velocidadeVento;
        this.grausVento = grausVento;
    }

    public String getLocalidade() { 
        return localidade; 
    }

    public double getTemperaturaMomento() { 
        return temperaturaMomento; 
    }
    
    public double getTemperaturaMax() { 
        return temperaturaMax; 
    }
    
    public double getTemperaturaMin() { 
        return temperaturaMin; 
    }
    
    public double getPercentualUmidade() { 
        return percentualUmidade; 
    }
    
    public String getDescricaoTempo() { 
        return descricaoTempo; 
    }
    
    public double getVolumeChuva() { 
        return volumeChuva; 
    }
    
    public double getVelocidadeVento() { 
        return velocidadeVento; 
    }
    
    public double getGrausVento() { 
        return grausVento; 
    }

    public String obterPontoCardealVento() {
        if (grausVento >= 337.5 || grausVento < 22.5) return "N";
        if (grausVento >= 22.5 && grausVento < 67.5) return "NE";
        if (grausVento >= 67.5 && grausVento < 112.5) return "L";
        if (grausVento >= 112.5 && grausVento < 157.5) return "SE";
        if (grausVento >= 157.5 && grausVento < 202.5) return "S";
        if (grausVento >= 202.5 && grausVento < 247.5) return "SO";
        if (grausVento >= 247.5 && grausVento < 292.5) return "O";
        return "NO";
    }
}