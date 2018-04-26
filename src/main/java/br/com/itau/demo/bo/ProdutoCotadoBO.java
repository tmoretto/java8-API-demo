package br.com.itau.demo.bo;

import java.util.ArrayList;
import java.util.List;

public class ProdutoCotadoBO {

	private ProdutoBO produto;
	private Integer quantidadeSolicitada;
	private List<RetornoCotacaoFornecedorBO> retornoCotacao = new ArrayList<>();

	public ProdutoCotadoBO(ProdutoBO produto, Integer quantidadeSolicitada) {
		super();
		this.produto = produto;
		this.quantidadeSolicitada = quantidadeSolicitada;
	}

	public ProdutoBO getProduto() {
		return produto;
	}

	public void setProduto(ProdutoBO produto) {
		this.produto = produto;
	}

	public Integer getQuantidadeSolicitada() {
		return quantidadeSolicitada;
	}

	public void setQuantidadeSolicitada(Integer quantidadeSolicitada) {
		this.quantidadeSolicitada = quantidadeSolicitada;
	}

	public List<RetornoCotacaoFornecedorBO> getRetornoCotacao() {
		return retornoCotacao;
	}

	public void adicionarRetornoDeCotacao(RetornoCotacaoFornecedorBO retornoCotacao) {
		this.retornoCotacao.add(retornoCotacao);
	}

	public RetornoCotacaoFornecedorBO buscarMenorPrecoPorRetorno() {
		return this.retornoCotacao.stream()
				.filter(retorno -> retorno.getMenorPreco() != null)
				.distinct()
				.sorted()
				.findFirst()
				.orElse(null);
	}
	
}
