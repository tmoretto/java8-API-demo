# java8-API-demo

Projeto demonstração de uma **`API REST`** **`Java 8`** com **`Spring Boot`**.
A API expõe um endpoint de **`Produtos`**, onde é possível `incluir`, `alterar`, `excluir` e `consultar` (todos ou por código de barras) produtos. 
A API disponibiliza também um endpoint de **`Cotação`** de produtos. Fora do escopo dessa demonstração, foi construída uma API externa para a consulta de preços de produtos por fornecedor baseado na quantidade mínima solicitada (um mesmo fornecedor pode ter mais de um preço para um mesmo produto, com quantidades mínimas diferentes).
Baseado no produto e a quantidade solicitada essa API irá consumir o serviço externo e retornar o melhor preço de cada fornecedor.

## Documentação
### `Endpoint de Produto`
#### Requests:

- `GET /api/produtos` 
  - **Retorno**: Lista com todos os produtos cadastrados.
- `GET /api/produtos/{codigoBarras}`
  - **Parametro**: Código de barras do produto a ser `buscado`
  - **Retorno**: Produto correspondente ao código de barras.
- `POST /api/produtos/`
  - **Parametro**: JSON representando o produto a ser `incluído`. Exemplo:
  ```javascript
  {
	"codigoBarras" : "7896019621755",
	"nome" : "CHOCOLATE AO LEITE LACTA"
  }
  ```
  - **Retorno**: Produto incluído.
- `PUT /api/produtos/`
  - **Parametro**: JSON representando o produto a ser `atualizado`. Exemplo:
  ```javascript
  {
	"codigoBarras" : "7896019621755",
	"nome" : "NOME ALTERADO"
  }
  ```
  - **Retorno**: Produto atualizado.
- `DELETE api/produtos/{codigoBarras}`
  - **Parametro**: Código de barras do produto a ser `excluído`


### `Endpoint de Cotação`

#### Requests:

- `POST /api/cotacao`
  - **Parametro**: Lista JSON com o código de barras do produto e quantidade solicitada. Exemplo:
  ```javascript
  [
	  {
		  "codigoBarras" : "0000078906938",
		  "quantidade" : 5
	  },
	  {
		  "codigoBarras" : "7894900019841",
		  "quantidade" : 100
	  }	
	
  ]
  ```
  - **Retorno**: Representação da cotação com o menor preço encontrado de cada fornecedor.
  
#### Informações importantes a respeito do Serviço Externo de consulta de preços:
- Caso o projeto esteja sendo executado dentro de uma rede corporativa (com proxy ou bloqueios) é possível que o acesso ao serviço externo não seja permitido.
- É possível verificar todos os produtos e quantidades disponíveis para cotação com um `GET` em https://demo-prices.herokuapp.com/api/precos
