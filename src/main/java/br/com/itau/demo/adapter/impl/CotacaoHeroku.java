package br.com.itau.demo.adapter.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.itau.demo.adapter.CotacaoAdapter;
import br.com.itau.demo.bo.CotacaoBO;
import br.com.itau.demo.bo.CotacaoPorFornecedorBO;
import br.com.itau.demo.bo.ProdutoBO;
import br.com.itau.demo.bo.ProdutoCotadoBO;
import br.com.itau.demo.bo.RetornoCotacaoFornecedorBO;
import br.com.itau.demo.model.Fornecedor;
import br.com.itau.demo.model.Produto;

@Service
public class CotacaoHeroku implements CotacaoAdapter {

	//private final String cotacaoUrl = "https://demo-prices.herokuapp.com/api/";
	private final String cotacaoUrl = "https://demo-prices.herokuapp.com/api/precos/";
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
		ProdutoCotadoBO produtoCotado = new ProdutoCotadoBO(new ProdutoBO(produto.getCodigoBarras()), produto.getQuantidade());
		
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
