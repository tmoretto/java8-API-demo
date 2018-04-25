package br.com.itau.demo.bo;

import java.math.BigDecimal;

import br.com.itau.demo.model.Fornecedor;

public class RetornoCotacaoFornecedorBO implements Comparable<RetornoCotacaoFornecedorBO>{

	private Fornecedor fornecedor;
	private BigDecimal menorPreco;
	
	public RetornoCotacaoFornecedorBO(Fornecedor fornecedor, BigDecimal menorPreco) {
		super();
		this.fornecedor = fornecedor;
		this.menorPreco = menorPreco;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public BigDecimal getMenorPreco() {
		return menorPreco;
	}

	public void setMenorPreco(BigDecimal menorPreco) {
		this.menorPreco = menorPreco;
	}

	@Override
	public int compareTo(RetornoCotacaoFornecedorBO o) {
		return this.menorPreco.compareTo(o.getMenorPreco());
	}

}
