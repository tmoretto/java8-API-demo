package br.com.itau.demo.service;

import java.util.List;

import br.com.itau.demo.model.Produto;

public interface ProdutoService {

	List<Produto> obterTodos();

	Produto incluir(Produto novoProduto);

	Produto obterPorCodigoDeBarras(String codigoBarras);

}
