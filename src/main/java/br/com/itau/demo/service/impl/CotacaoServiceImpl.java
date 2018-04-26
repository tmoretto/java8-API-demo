package br.com.itau.demo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itau.demo.adapter.CotacaoAdapter;
import br.com.itau.demo.bo.CotacaoBO;
import br.com.itau.demo.bo.CotacaoPorFornecedorBO;
import br.com.itau.demo.bo.ProdutoBO;
import br.com.itau.demo.bo.ProdutoCotadoBO;
import br.com.itau.demo.bo.RetornoCotacaoFornecedorBO;
import br.com.itau.demo.model.Fornecedor;
import br.com.itau.demo.model.Produto;
import br.com.itau.demo.service.CotacaoService;

@Service
public class CotacaoServiceImpl implements CotacaoService {

	@Autowired
	private CotacaoAdapter adapter;

	public CotacaoBO realizarCotacaoDeProdutos(List<Produto> produtosSelecionados) {
		CotacaoBO cotacao = new CotacaoBO();

		produtosSelecionados.stream().filter(produto -> produto.getQuantidade() > 0).distinct().forEach(produto -> {
			cotacao.adicionarProdutoCotado(cotarProduto(produto));
		});

		return cotacao;
	}

	private ProdutoCotadoBO cotarProduto(Produto produto) {
		ProdutoCotadoBO produtoCotado = new ProdutoCotadoBO(new ProdutoBO(produto.getCodigoBarras()),
				produto.getQuantidade());

		List<CotacaoPorFornecedorBO> cotacaoDeFornecedoresParaProduto = adapter.buscarCotacaoDeFornecedoresParaProduto(produto.getCodigoBarras());
		cotacaoDeFornecedoresParaProduto.forEach(cotacao -> {
			buscarMenorPreco(produtoCotado, cotacao);
		});

		return produtoCotado;

	}

	private void buscarMenorPreco(ProdutoCotadoBO produtoCotado, CotacaoPorFornecedorBO cotacaoFornecedor) {
		BigDecimal menorPrecoPorQuantidadeSolicitada = cotacaoFornecedor
				.getMenorPrecoPorQuantidadeSolicitada(produtoCotado.getQuantidadeSolicitada());
		if (menorPrecoPorQuantidadeSolicitada != null) {
			RetornoCotacaoFornecedorBO retornoCotacao = new RetornoCotacaoFornecedorBO(
					new Fornecedor(cotacaoFornecedor.getCnpj(), cotacaoFornecedor.getNome()),
					menorPrecoPorQuantidadeSolicitada);
			produtoCotado.adicionarRetornoDeCotacao(retornoCotacao);
		}
	}

}
