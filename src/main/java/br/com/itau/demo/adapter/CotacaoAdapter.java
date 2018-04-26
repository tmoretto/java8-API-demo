package br.com.itau.demo.adapter;

import java.util.List;

import br.com.itau.demo.bo.CotacaoPorFornecedorBO;

public interface CotacaoAdapter {

	List<CotacaoPorFornecedorBO> buscarCotacaoDeFornecedoresParaProduto(String codigoBarras);

}
