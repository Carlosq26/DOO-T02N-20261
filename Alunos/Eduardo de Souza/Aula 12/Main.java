package streamApiDOO;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.*;

public class Main {
	public static void main(String[]args) {
		executarAtv1();
		executarAtv2();
		executarAtv3();
		executarAtv4();
		executarAtv5();
		executarAtv6();
	}
	//Atv 1
	public static void executarAtv1() {
		List<Integer> numeros = Arrays.asList(3, 8, 15, 22, 7, 10, 14, 19, 26, 31);
		
		List<Integer> pares = numeros.stream()
				.filter(numero -> numero % 2 == 0)
				.collect(Collectors.toList());
		System.out.println("Atv 1 | Números Pares: " + pares);
	}
	//Atv2
	public static void executarAtv2() {
		List<String> nomes = Arrays.asList("roberto", "josé", "caio", "vinicius");
		
		List<String> nomesMaiscula = nomes.stream()
				.map(String::toUpperCase)
				.collect(Collectors.toList());
		System.out.println("Atv 2 | Nomes em Maisculo: " +nomesMaiscula);
	}
	//Atv 3
	public static void executarAtv3() {
		List<String> palavras = Arrays.asList("se", "talvez", "hoje", "sábado", "se", "quarta", "sábado");
		
		long qntsPalavras = palavras.stream()
				.distinct()
				.count();
		
		System.out.println("Atv 3 | Quantidade de Palavras: " +qntsPalavras);
	}
	//Atv 4
	public static class Produto{
		private String nome;
		private Double preco;
		public Produto(String nome, Double preco) {
			this.nome = nome;
			this.preco = preco;
		}
		public String getNome() {return nome;}
		public Double getPreco() {return preco;}
	
	    @Override
	    public String toString() {
	        return "\n Produto: " + getNome() + " | Preço: R$ " + getPreco();
	    }
	}
	public static void executarAtv4() {
		List<Produto> produtos = Arrays.asList(
		new Produto("Notebook", 1000.0),
		new Produto("Celular", 1000.0),
		new Produto("Maçã", 2.0),
		new Produto("Caneta", 1.0));
		
		List<Produto> produtosAcimaCem = produtos.stream()
				.filter(produto -> produto.getPreco() > 100.0)
				.collect(Collectors.toList());
		
		System.out.println("Atv 4 | Produtos acima de R$ 100: " +produtosAcimaCem);
	}
	//Atv 5
	public static void executarAtv5() {
		List<Produto> produtos = Arrays.asList(
		new Produto("Notebook", 1000.0),
		new Produto("Celular", 1000.0),
		new Produto("Maçã", 2.0),
		new Produto("Caneta", 1.0));
		
		double somaProdutos = produtos.stream()
				.map(Produto::getPreco)
				.mapToDouble(Double::doubleValue)
				.sum();
		
		System.out.printf("Atv 5 | A soma dos Produtos: R$ %.2f \n", somaProdutos);
				
	}
	//Atv 6
	public static void executarAtv6() {
		List<String> nomes = Arrays.asList("Java", "Python", "C", "JavaScript", "Ruby");
		
		List<String> nomesOrdenados = nomes.stream()
				.sorted(Comparator.comparing(String::length))
				.collect(Collectors.toList());
		System.out.println("Atv 6 | Lista ordenada: "+ nomesOrdenados);
		
	}
}
