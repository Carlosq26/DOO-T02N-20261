import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // =========================
        // ATV1
        // Recebe uma lista de números inteiros e retorna apenas os pares
        // =========================
        List<Integer> numeros = Arrays.asList(10, 15, 22, 37, 48, 53, 64, 79);

        List<Integer> numerosPares = numeros.stream()
                .filter(n -> n % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("ATV1 - Números pares:");
        System.out.println(numerosPares);

        // =========================
        // ATV2
        // Converter nomes para letras maiúsculas
        // =========================
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("\nATV2 - Nomes em maiúsculo:");
        System.out.println(nomesMaiusculos);

        // =========================
        // ATV3
        // Contar quantas vezes cada palavra aparece
        // =========================
        List<String> palavras = Arrays.asList(
                "se", "talvez", "hoje", "sábado",
                "se", "quarta", "sábado"
        );

        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(
                        palavra -> palavra,
                        Collectors.counting()
                ));

        System.out.println("\nATV3 - Contagem de palavras:");
        contagemPalavras.forEach((palavra, quantidade) ->
                System.out.println(palavra + " = " + quantidade));

        // =========================
        // ATV4
        // Filtrar produtos com preço maior que R$100,00
        // =========================
        List<Produto> produtos = Arrays.asList(
                new Produto("Mouse Gamer", 89.90),
                new Produto("Teclado Mecânico", 249.90),
                new Produto("Monitor", 899.90),
                new Produto("Headset", 149.90)
        );

        List<Produto> produtosFiltrados = produtos.stream()
                .filter(produto -> produto.getPreco() > 100)
                .collect(Collectors.toList());

        System.out.println("\nATV4 - Produtos com preço maior que R$100,00:");
        produtosFiltrados.forEach(System.out::println);

        // =========================
        // ATV5
        // Soma total dos preços dos produtos
        // =========================
        double somaTotal = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("\nATV5 - Soma total dos produtos:");
        System.out.printf("R$ %.2f%n", somaTotal);

        // =========================
        // ATV6
        // Ordenar palavras pelo tamanho
        // =========================
        List<String> linguagens = Arrays.asList(
                "Java", "Python", "C", "JavaScript", "Ruby"
        );

        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("\nATV6 - Linguagens ordenadas por tamanho:");
        System.out.println(linguagensOrdenadas);
    }
}

class Produto {
    private String nome;
    private double preco;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    @Override
    public String toString() {
        return nome + " - R$ " + preco;
    }
}