package br.com.itau.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.itau.demo.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, String> {

}
