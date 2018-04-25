package br.com.itau.demo.bo;

import java.math.BigDecimal;

public class PrecoDisponibilidadeProdutoBO implements Comparable<PrecoDisponibilidadeProdutoBO> {

	private Integer quantidade_minima;
	private BigDecimal preco;

	public Integer getQuantidade_minima() {
		return quantidade_minima;
	}

	public void setQuantidade_minima(Integer quantidade_minima) {
		this.quantidade_minima = quantidade_minima;
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
