package br.com.itau.demo.adapter.impl;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.itau.demo.adapter.CotacaoAdapter;
import br.com.itau.demo.bo.CotacaoPorFornecedorBO;

@Service
public class CotacaoHeroku implements CotacaoAdapter {

	private final String cotacaoUrl = "https://demo-prices.herokuapp.com/api/precos/";
	private RestTemplate restTemplate = new RestTemplate();

	public List<CotacaoPorFornecedorBO> buscarCotacaoDeFornecedoresParaProduto(String codigoBarras) {

		ResponseEntity<List<CotacaoPorFornecedorBO>> cotacaoResponse = restTemplate.exchange(cotacaoUrl.concat(codigoBarras), HttpMethod.GET,
				null, new ParameterizedTypeReference<List<CotacaoPorFornecedorBO>>() {
			});
		return cotacaoResponse.getBody();

	}

}
