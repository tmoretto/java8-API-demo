package br.com.itau.demo.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itau.demo.bo.CotacaoPorFornecedorBO;
import br.com.itau.demo.model.Produto;
import br.com.itau.demo.service.CotacaoService;

@RestController
@RequestMapping("/api/cotacao")
public class CotacaoController {

	@Autowired
	private CotacaoService service;

	@PostMapping
	public  ResponseEntity<List<CotacaoPorFornecedorBO>> cotar(@RequestBody Produto produto, HttpServletResponse response) {
		List<CotacaoPorFornecedorBO> cotacao = service.buscarCotacaoDeFornecedoresParaProduto(produto);
		
		if (cotacao.isEmpty()) {
			return new ResponseEntity<List<CotacaoPorFornecedorBO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<CotacaoPorFornecedorBO>>(cotacao, HttpStatus.OK);
	}

}
