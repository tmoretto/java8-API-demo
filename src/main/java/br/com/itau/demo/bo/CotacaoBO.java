package br.com.itau.demo.bo;

import java.util.ArrayList;
import java.util.List;

public class CotacaoBO {
	
	private List<ProdutoCotadoBO> produtosCotados = new ArrayList<>();

	public List<ProdutoCotadoBO> getProdutosCotados() {
		return produtosCotados;
	}

	public void adicionarProdutoCotado(ProdutoCotadoBO produtoCotado) {
		this.produtosCotados.add(produtoCotado);
	}
	
}
