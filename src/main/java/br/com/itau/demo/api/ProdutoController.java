package br.com.itau.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itau.demo.dto.ProdutoDTO;
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
	
}
