package br.com.itau.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itau.demo.model.Produto;
import br.com.itau.demo.repository.ProdutoRepository;
import br.com.itau.demo.service.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public List<Produto> obterTodos() {
        return repository.findAll();
    }

	public Produto incluir(Produto novoProduto) {
		return repository.save(novoProduto);
	}

	public Produto obterPorCodigoDeBarras(String codigoBarras) {
		return repository.findOneProdutoByCodigoBarras(codigoBarras);
	}

	public void excluirPorCodigoDeBarras(String codigoBarras) {
		repository.deleteById(codigoBarras);
	}

}
