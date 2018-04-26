package br.com.itau.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.itau.demo.config.ConfigurationTest;
import br.com.itau.demo.model.Produto;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = ConfigurationTest.class)
public class ProdutoServiceTest {

	@Autowired
	private ProdutoService service;
	
	@Test
	@Sql({ "classpath:insert_produtos.sql" })
	@Sql(scripts = "classpath:limpa_dados.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void deveRetornarTodosProdutosTest() {
		List<Produto> todosProdutos = service.obterTodos();
		assertThat(todosProdutos).isNotNull();
		assertThat(todosProdutos.isEmpty()).isEqualTo(Boolean.FALSE);
		assertThat(todosProdutos.size()).isEqualTo(2);
	}

	@Test
	@Sql(scripts = "classpath:limpa_dados.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void deveIncluirUmNovoProdutoTest() {
		Produto novoProduto = service.salvar(new Produto("7891048050262", "GELATINA DR.OETKER MORANGO"));
		assertThat(novoProduto).isNotNull();

		List<Produto> todosProdutos = service.obterTodos();
		assertThat(todosProdutos).isNotNull();
		assertThat(todosProdutos.isEmpty()).isEqualTo(Boolean.FALSE);
		assertThat(todosProdutos.size()).isEqualTo(1);
	}

	@Test
	@Sql({ "classpath:insert_produtos.sql" })
	@Sql(scripts = "classpath:limpa_dados.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void deveRetornarProdutoPorCodigoDeBarrasTest() {
		String codigoBarras = "0000078906938";
		Produto produto = service.obterPorCodigoDeBarras(codigoBarras);
		
		assertThat(produto).isNotNull();
		assertThat(produto.getCodigoBarras()).isEqualTo(codigoBarras);
	}
	
	@Test
	@Sql({ "classpath:insert_produtos.sql" })
	@Sql(scripts = "classpath:limpa_dados.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	public void deveExcluirProdutoPorCodigoDeBarrasTest() {
		String codigoBarras = "0000078906938";
		service.excluirPorCodigoDeBarras(codigoBarras);
		
		List<Produto> todosProdutos = service.obterTodos();
		assertThat(todosProdutos).isNotNull();
		assertThat(todosProdutos.isEmpty()).isEqualTo(Boolean.FALSE);
		assertThat(todosProdutos.size()).isEqualTo(1);;
	}
}
