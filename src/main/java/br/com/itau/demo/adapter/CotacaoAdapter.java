package br.com.itau.demo.adapter;

import java.util.List;

import br.com.itau.demo.bo.CotacaoBO;
import br.com.itau.demo.model.Produto;

public interface CotacaoAdapter {

	CotacaoBO realizaCotacao(List<Produto> produtos);
	
}
