package br.com.itau.demo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.itau.demo.bo.CotacaoBO;
import br.com.itau.demo.bo.CotacaoPorFornecedorBO;
import br.com.itau.demo.bo.ProdutoCotadoBO;
import br.com.itau.demo.bo.RetornoCotacaoFornecedorBO;
import br.com.itau.demo.model.Fornecedor;
import br.com.itau.demo.model.Produto;
import br.com.itau.demo.service.CotacaoService;

@Service
public class CotacaoServiceImpl implements CotacaoService {

	//private final String cotacaoUrl = "https://demo-prices.herokuapp.com/api";
	private final String cotacaoUrl = "https://egf1amcv33.execute-api.us-east-1.amazonaws.com/dev/produto/";	
	private RestTemplate restTemplate = new RestTemplate();

	public CotacaoBO realizarCotacaoDeProdutos(List<Produto> produtosSelecionados) {
		CotacaoBO cotacao = new CotacaoBO();
		
		produtosSelecionados.stream()
			.filter(produto -> produto.getQuantidade() > 0)
			.distinct()
			.forEach(produto -> {			
				cotacao.adicionarProdutoCotado(cotarProduto(produto));
			});
		
		return cotacao;
	}

	private ProdutoCotadoBO cotarProduto(Produto produto) {
		ProdutoCotadoBO produtoCotado = new ProdutoCotadoBO(produto, produto.getQuantidade());
		
		List<CotacaoPorFornecedorBO> cotacaoDeFornecedoresParaProduto = buscarCotacaoDeFornecedoresParaProduto(produto);
		cotacaoDeFornecedoresParaProduto.forEach(cotacao -> {
			buscarMenorPreco(produtoCotado, cotacao);
		});
		
		return produtoCotado;

	}

	public List<CotacaoPorFornecedorBO> buscarCotacaoDeFornecedoresParaProduto(Produto produto) {

		ResponseEntity<List<CotacaoPorFornecedorBO>> cotacaoResponse = restTemplate.exchange(cotacaoUrl.concat(produto.getCodigoBarras()), HttpMethod.GET,
				null, new ParameterizedTypeReference<List<CotacaoPorFornecedorBO>>() {
			});
		return cotacaoResponse.getBody();

	}
	
	private void buscarMenorPreco(ProdutoCotadoBO produtoCotado, CotacaoPorFornecedorBO cotacaoFornecedor) {
		BigDecimal menorPrecoPorQuantidadeSolicitada = cotacaoFornecedor.getMenorPrecoPorQuantidadeSolicitada(produtoCotado.getQuantidadeSolicitada());
		if (menorPrecoPorQuantidadeSolicitada != null) {
			RetornoCotacaoFornecedorBO retornoCotacao = new RetornoCotacaoFornecedorBO(new Fornecedor(cotacaoFornecedor.getCnpj(), cotacaoFornecedor.getNome()), menorPrecoPorQuantidadeSolicitada);
			produtoCotado.adicionarRetornoDeCotacao(retornoCotacao);
		}
	}

}
