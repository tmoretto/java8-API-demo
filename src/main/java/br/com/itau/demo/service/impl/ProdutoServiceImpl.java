package br.com.itau.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.itau.demo.model.Produto;
import br.com.itau.demo.repository.ProdutoRepository;
import br.com.itau.demo.service.ProdutoService;

public class ProdutoServiceImpl implements ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	public List<Produto> obterTodos() {
        return repository.findAll();
    }

}
