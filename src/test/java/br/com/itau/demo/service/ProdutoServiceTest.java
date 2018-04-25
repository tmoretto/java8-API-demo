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
	@Sql({"classpath:insert_produtos.sql"})
	public void deveRetornarTodosProdutosTest() {
		List<Produto> todosProdutos = service.obterTodos();
		assertThat(todosProdutos).isNotNull();
		assertThat(todosProdutos.isEmpty()).isEqualTo(Boolean.FALSE);
		assertThat(todosProdutos.size()).isEqualTo(2);
	}

}
