package com.loja_virtual.api.carrinho.controller;

import com.loja_virtual.api.carrinho.model.Carrinho;
import com.loja_virtual.api.carrinho.service.CarrinhoProxy;
import com.loja_virtual.api.carrinho.service.CarrinhoService;
import com.loja_virtual.api.produto.model.Produto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/carrinho", produces = {"application/json"})
@Tag(name = "carrinho", description = "Carrinho de compras da loja")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;
    @Autowired
    private CarrinhoProxy carrinhoProxy;

    @Operation(summary = "Adiciona um produto ao carrinho",
                description = "Somente produtos associados à categoria podem ser adicionados ao Carrinho de compras",
                method = "POST"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description="Produto adicionado ao carrinho com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao adicionar produto ao carrinho")
    })
    @PostMapping
    public ResponseEntity<Object> AdicionarProdutoCarrinho(@RequestBody Produto produto) {
        try {
            carrinhoProxy.adicionarProdutoCarrinho(produto);
        return ResponseEntity.status(HttpStatus.OK).body("Produto adicionado ao carrinho");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Busca todos os carrinhos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description="Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao realizar busca do carrinho")
    })
    @GetMapping
    public ResponseEntity<Carrinho> ListarProdutosCarrinho() {
        return ResponseEntity.status(HttpStatus.OK).body(carrinhoService.listarCarrinho());
    }

    @Operation(summary = "Remove um produto do carrinho", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description="Produto removido do carrinho com sucesso"),
            @ApiResponse(responseCode = "422", description="Dados da requisição inválido"),
            @ApiResponse(responseCode = "400", description="Parametros inválidos"),
            @ApiResponse(responseCode = "500", description="Erro ao remover um produto do carrinho")
    })
    @DeleteMapping
    public ResponseEntity<Object> DeletarProdutoCarrinho(@RequestBody Produto produto) {
        try {
            carrinhoService.removerProdutoCarrinho(produto);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido do carrinho");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
