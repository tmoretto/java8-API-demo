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

import br.com.itau.demo.bo.CotacaoBO;
import br.com.itau.demo.model.Produto;
import br.com.itau.demo.service.CotacaoService;

@RestController
@RequestMapping("/api/cotacao")
public class CotacaoController {

	@Autowired
	private CotacaoService service;

	@PostMapping
	public  ResponseEntity<CotacaoBO> cotar(@RequestBody List<Produto> produtos, HttpServletResponse response) {
		CotacaoBO cotacao = service.realizarCotacaoDeProdutos(produtos);
		if (cotacao == null) {
			return new ResponseEntity<CotacaoBO>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<CotacaoBO>(cotacao, HttpStatus.OK);
	}

}
