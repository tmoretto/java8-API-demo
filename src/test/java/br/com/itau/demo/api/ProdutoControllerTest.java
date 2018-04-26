package br.com.itau.demo.api;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.itau.demo.model.Produto;
import br.com.itau.demo.service.ProdutoService;

@RunWith(SpringRunner.class)
@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {

	private static final String API_PRODUTOS = "/api/produtos";

	@Autowired
	MockMvc mvc;

	@Autowired
	ObjectMapper objectMapper;
	
	@MockBean
	ProdutoService service;
	
	private List<Produto> produtos;

	@Before
	public void setUp() {
		produtos = Arrays.asList(
				new Produto("7891025699880", "REQUEIJAO CREMOSO DANONE"),
				new Produto("7891515980252", "IOGURTE BATAVO"),
				new Produto("7893000492981", "LASANHA SADIA QUATRO QUEIJOS")
			);

		when(service.obterTodos()).thenReturn(produtos);
		when(service.obterPorCodigoDeBarras(Mockito.any(String.class))).thenReturn(new Produto("7891025699880", "REQUEIJAO CREMOSO DANONE"));
		when(service.salvar(Mockito.any(Produto.class))).thenReturn(new Produto("7891515980252", "IOGURTE BATAVO"));
		when(service.atualizar(Mockito.any(String.class), Mockito.any(Produto.class))).thenReturn(new Produto("7891515980252", "NOMEALTERADO"));
		
	}
	
	@Test
	public void deveRetornarTodosProdutosTest() throws Exception {
		this.mvc.perform(get(API_PRODUTOS))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(produtos.size())))
				.andExpect(jsonPath("$.[*].codigoBarras", hasItems("7891025699880", "7891515980252", "7893000492981")));
	}
	
	@Test
	public void deveRetornarProdutoPorCodigoBarrasTest() throws Exception {
		this.mvc.perform(get(API_PRODUTOS.concat("/{codigoBarras}"), "7891025699880"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.codigoBarras", is("7891025699880")));
	}
	
	@Test
	public void deveRetornarNenhumProdutoTest() throws Exception {
		when(service.obterTodos()).thenReturn(new ArrayList<>());
		this.mvc.perform(get(API_PRODUTOS))
				.andDo(print())
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void deveIncluirProdutoTest() throws Exception {
		final String dummyProdutoJson = objectMapper.writeValueAsString(new Produto("7891515980252", "IOGURTE BATAVO"));
		
		this.mvc.perform(post(API_PRODUTOS).contentType(MediaType.APPLICATION_JSON).content(dummyProdutoJson))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(jsonPath("$.codigoBarras", is("7891515980252")));
	}
	
	@Test
	public void deveAtualizarProdutoTest() throws Exception {
		final String dummyProdutoJson = objectMapper.writeValueAsString(new Produto("7891515980252", "NOMEALTERADO"));
		
		this.mvc.perform(put(API_PRODUTOS.concat("/{codigoBarras}"), "7891515980252").contentType(MediaType.APPLICATION_JSON).content(dummyProdutoJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.codigoBarras", is("7891515980252")))
				.andExpect(jsonPath("$.nome", is("NOMEALTERADO")));
	}
	
	@Test
	public void deveExcluirProdutoTest() throws Exception {
		this.mvc.perform(delete(API_PRODUTOS.concat("/{codigoBarras}"), "7891515980252").contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isNoContent());
	}
}
