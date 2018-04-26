package br.com.itau.demo.service;

import java.util.List;

import br.com.itau.demo.model.Produto;

public interface ProdutoService {

	List<Produto> obterTodos();

	Produto salvar(Produto novoProduto);

	Produto atualizar(String codigoBarras, Produto produto);
	
	Produto obterPorCodigoDeBarras(String codigoBarras);

	void excluirPorCodigoDeBarras(String codigoBarras);

}
