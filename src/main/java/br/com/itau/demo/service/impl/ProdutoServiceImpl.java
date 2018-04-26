package br.com.itau.demo.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	public Produto salvar(Produto novoProduto) {
		return repository.save(novoProduto);
	}

	public Produto atualizar(String codigoBarras, Produto produto) {
		Produto produtoAtualizar = this.obterPorCodigoDeBarras(codigoBarras);
		if (produtoAtualizar == null) {
			throw new EmptyResultDataAccessException(1);
		}
		BeanUtils.copyProperties(produto, produtoAtualizar, "codigoBarras");
		return this.salvar(produtoAtualizar);
	}
	
	public Produto obterPorCodigoDeBarras(String codigoBarras) {
		return repository.findOneProdutoByCodigoBarras(codigoBarras);
	}

	public void excluirPorCodigoDeBarras(String codigoBarras) {
		repository.deleteById(codigoBarras);
	}

}
