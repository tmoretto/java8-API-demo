package br.com.itau.demo.service;

import java.util.List;

import br.com.itau.demo.bo.CotacaoBO;
import br.com.itau.demo.model.Produto;

public interface CotacaoService {

	public CotacaoBO realizarCotacaoDeProdutos(List<Produto> produtosSelecionados);
	
}
