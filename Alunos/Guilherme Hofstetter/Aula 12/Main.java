import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // ATV1
        List<Integer> numeros = Arrays.asList(10, 15, 22, 33, 40, 57, 68, 71);

        List<Integer> numerosPares = numeros.stream()
                .filter(numero -> numero % 2 == 0)
                .collect(Collectors.toList());

        System.out.println("ATV1 - Números pares:");
        System.out.println(numerosPares);


        // ATV2
        List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculos = nomes.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("\nATV2 - Nomes em maiúsculo:");
        System.out.println(nomesMaiusculos);


        // ATV3
        List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");

        Map<String, Long> contagemPalavras = palavras.stream()
                .collect(Collectors.groupingBy(palavra -> palavra, Collectors.counting()));

        System.out.println("\nATV3 - Contagem de palavras:");
        System.out.println(contagemPalavras);


        // ATV4
        List<Produto> produtos = Arrays.asList(
                new Produto("Teclado", 80.00),
                new Produto("Mouse", 45.00),
                new Produto("Monitor", 750.00),
                new Produto("Headset", 150.00)
        );

        List<Produto> produtosAcimaDeCem = produtos.stream()
                .filter(produto -> produto.getPreco() > 100.00)
                .collect(Collectors.toList());

        System.out.println("\nATV4 - Produtos acima de R$ 100,00:");
        produtosAcimaDeCem.forEach(System.out::println);


        // ATV5
        double somaTotalProdutos = produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();

        System.out.println("\nATV5 - Soma total dos produtos:");
        System.out.println("R$ " + somaTotalProdutos);


        // ATV6
        List<String> linguagens = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");

        List<String> linguagensOrdenadas = linguagens.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());

        System.out.println("\nATV6 - Linguagens ordenadas pelo tamanho:");
        System.out.println(linguagensOrdenadas);
    }

    static class Produto {
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
}