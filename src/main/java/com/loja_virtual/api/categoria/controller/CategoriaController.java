package com.loja_virtual.api.categoria.controller;

import com.loja_virtual.api.categoria.exception.CategoriaException;
import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.categoria.service.CategoriaProxy;
import com.loja_virtual.api.categoria.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping(value="api/v1/categorias", produces = {"application/json"})
@Tag(name = "categoria", description = "Operações a cerca das categorias para produtos")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaProxy categoriaProxy;

    @Operation(summary = "Realiza cadastro de novas categorias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description="Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao realizar cadastro de categoria")
    })
    @PostMapping("/{categoria}")
    public ResponseEntity<Object> cadastrarCategoria(
            @Parameter(description = "Nome da categoria a ser criada.", required = true, example = "vestuario")
            @PathVariable String categoria) {
        Categoria novaCategoria = categoriaService.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @Operation(summary = "Busca todas as categorias cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao realizar busca de categorias")
    })
    @GetMapping
    public ResponseEntity<Set<Categoria>> listarCategorias() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listaCategorias());
    }

    @Operation(summary = "Busca a categoria por nome", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao realizar busca da categoria")
    })
    @GetMapping("/{nome}")
    public ResponseEntity<Object> listarCategoriasPorNome(
            @Parameter(description = "Nome da categoria a ser buscada.", required = true, example = "vestuario")
            @PathVariable String nome) {
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorNome(nome);
            return ResponseEntity.status(HttpStatus.OK).body(categoria);
        } catch (CategoriaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Deleta uma categoria pelo nome",
                description = "Categorias que estejam associadas a produtos e que esteja associada a um produto, que esteja no carrinho, não podem ser excluídas;",
                method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Categoria removida com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao deletar uma categoria")
    })
    @DeleteMapping("/{nome}")
    public ResponseEntity<Object> removerCategoria(
            @Parameter(description = "Nome da categoria a ser removida.", required = true, example = "vestuario")
            @PathVariable String nome) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoriaProxy.removerCategoria(nome));
        } catch (CategoriaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
