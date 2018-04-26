package br.com.itau.demo.bo;

import java.math.BigDecimal;

public class PrecoDisponibilidadeProdutoBO implements Comparable<PrecoDisponibilidadeProdutoBO> {

	private Integer quantidadeMinima;
	private BigDecimal preco;

	public Integer getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	@Override
	public int compareTo(PrecoDisponibilidadeProdutoBO o) {
		return this.preco.compareTo(o.preco);
	}

}
