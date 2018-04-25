package br.com.itau.demo.api;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.itau.demo.dto.ProdutoDTO;
import br.com.itau.demo.model.Produto;
import br.com.itau.demo.service.ProdutoService;
import utils.ObjectMapperUtils;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	@Autowired
	private ProdutoService service;

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> obterTodos() {
		List<ProdutoDTO> produtosDTO = ObjectMapperUtils.mapAll(service.obterTodos(), ProdutoDTO.class);
		if (produtosDTO.isEmpty()) {
			return new ResponseEntity<List<ProdutoDTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<ProdutoDTO>>(produtosDTO, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ProdutoDTO> incluir(@RequestBody Produto novoProduto, HttpServletResponse response) {
		Produto produto = service.incluir(novoProduto);
		
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigoBarras}")
				.buildAndExpand(produto.getCodigoBarras()).toUri();
		response.setHeader("Location", uri.toASCIIString());
		
		return ResponseEntity.created(uri).body(ObjectMapperUtils.map(produto, ProdutoDTO.class));
	}

}
