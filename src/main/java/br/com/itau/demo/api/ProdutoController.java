package br.com.itau.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itau.demo.model.Produto;
import br.com.itau.demo.service.ProdutoService;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@GetMapping
	public ResponseEntity<List<Produto>> obterTodos() {
		List<Produto> produtos = service.obterTodos();
		if (produtos.isEmpty()) {
			return new ResponseEntity<List<Produto>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Produto>>(produtos, HttpStatus.OK);
	}
	
}
