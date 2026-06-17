import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        //ATV1, abaixo o código.
        List<Integer> numeros = List.of(1, 2, 3, 4, 5, 6, 7, 8);

        List<Integer> stream = numeros.stream()
            .filter(numero -> numero %2 == 0)
            .toList();
        System.out.println("Números pares: " + stream);

        //ATV2, abaixo o código.
        List<String> nomes = List.of("roberto", "josé", "caio", "vinicius");

        List<String> nomesMaiusculo = nomes.stream()
            .map(nome -> nome.toUpperCase())
            .toList();
        System.out.println("Nomes em maiúsculo: " + nomesMaiusculo);

        //ATV3, abaixo o código.
        List<String> palavras = List.of("se", "talvez", "hoje" ,"sábado", "se", "quarta", "sábado");

        long palavrasUnicas = palavras.stream()
            .filter(palavra -> Collections.frequency(palavras, palavra) == 1)
            .count();
        System.out.println("Número de palavras únicas: " + palavrasUnicas);
        
        //ATV4, abaixo o código.
        
        final List<Produto> produtos = List.of(
            new Produto("Mouse Gamer", 85.00),
            new Produto("Teclado Mecânico", 150.00),
            new Produto("Fone de Ouvido", 50.00),
            new Produto("Monitor 24'", 890.00)
        );
        List<String> produtosCaros = produtos.stream()
            .filter(produto -> produto.getPreco() > 100.00)
            .map(Produto::getNome)
            .toList();
        System.out.println("Produtos caros: " + produtosCaros);

        //ATV5, abaixo o código.
        double precoTotal = produtos.stream()
            .mapToDouble(produto -> produto.getPreco())
            .sum();
        System.out.println("Preço total dos produtos: " + precoTotal);

        //ATV6, abaixo o código.
        List<String> linguagems = List.of("Java", "Python", "C", "JavaScript", "Ruby");
        List<String> LinguagensOrdem = linguagems.stream()
            .sorted(Comparator
                .comparingInt(String::length)
                .thenComparing(Comparator.naturalOrder())
            )
            .toList();
        System.out.println("Linguagens em ordem: " + LinguagensOrdem);
    }
    public static class Produto {
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
    }
}