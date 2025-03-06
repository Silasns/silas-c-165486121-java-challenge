package com.loja_virtual.api.produto.controller;

import com.loja_virtual.api.produto.dto.ProdutoDTO;
import com.loja_virtual.api.produto.exception.ProdutoException;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.service.ProdutoProxy;
import com.loja_virtual.api.produto.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/produtos", produces = {"application/json"})
@Tag(name = "produtos", description = "Operações a cerca dos produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ProdutoProxy produtoProxy;

    @Operation(summary = "Realiza cadastro de novos produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description="Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao realizar cadastro de produto")
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Produto> cadastrarProduto(@Valid @RequestBody ProdutoDTO produto) {
        Produto novoProduto = produtoService.criarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @Operation(summary = "Busca todos os produtos cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao realizar busca de produto")
    })
    @GetMapping
    public ResponseEntity<List<Produto>> listarTodosProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarTodosProdutos());
    }

    @Operation(summary = "Busca um produto cadastrado pelo Id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao realizar busca de produto")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> listarProdutoPorId(
            @Parameter(description = "ID do produto a ser buscado. Deve ser um UUID válido.", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        try {
            Produto produto = produtoService.buscarProduto(id);
            return ResponseEntity.status(HttpStatus.OK).body(produto);
        } catch (ProdutoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Deleta um produto pelo id",
                description = "Produtos inseridos no carrinho de compras não podem ser removidos",
                method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Produto removido com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao deletar um produto")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeProduto(
            @Parameter(description = "ID do produto a ser deletado.", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID id) {
        try {
            produtoProxy.removerProduto(id);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso.");
        } catch (ProdutoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Realiza a associação de um produto com uma categoria", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Categoria associada com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao associar um produto a uma categoria")
    })
    @PostMapping("/{idProduto}/categorias/{nomeCategoria}")
    public ResponseEntity<Object> associaCategoria(
            @Parameter(description = "ID do produto a ser associado.", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID idProduto,
            @Parameter(description = "Nome da categoria a ser associada.", required = true, example = "vestuario")
            @PathVariable String nomeCategoria) {
        try {
            produtoService.associarProdutoCategoria(idProduto, nomeCategoria);
            return ResponseEntity.status(HttpStatus.OK).body("Categoria associada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Realiza a desassociação de um produto com uma categoria", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Categoria desassociada com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro desassociar um produto a uma categoria")
    })
    @DeleteMapping("/{idProduto}/categorias/{nomeCategoria}")
    public ResponseEntity<Object> desassociaCategoria(
            @Parameter(description = "ID do produto a ser desassociado.", required = true, example = "550e8400-e29b-41d4-a716-446655440000")
            @PathVariable UUID idProduto,
            @Parameter(description = "Nome da categoria a ser desassociada.", required = true, example = "vestuario")
            @PathVariable String nomeCategoria) {
        try{
            produtoService.desassociarProdutoCategoria(idProduto, nomeCategoria);
            return ResponseEntity.status(HttpStatus.OK).body("Categoria desassociada com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
