package br.com.itau.demo.bo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CotacaoPorFornecedorBO {

	private String cnpj;
	private String nome;
	private List<PrecoDisponibilidadeProdutoBO> precos = new ArrayList<>();

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<PrecoDisponibilidadeProdutoBO> getPrecos() {
		return precos;
	}

	public void setPrecoDisponibilidadeProdutos(List<PrecoDisponibilidadeProdutoBO> precos) {
		this.precos = precos;
	}

	public BigDecimal getMenorPrecoPorQuantidadeSolicitada(Integer quantidadeSolicitada) {
		return this.precos.stream()
				.filter(precoQtde -> quantidadeSolicitada >= precoQtde.getQuantidade_minima())			
				.sorted()
				.findFirst()
				.map(preco -> preco.getPreco())
				.orElse(null);
	}

}
