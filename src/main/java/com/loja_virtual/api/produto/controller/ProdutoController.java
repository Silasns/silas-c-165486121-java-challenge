package com.loja_virtual.api.produto.controller;

import com.loja_virtual.api.produto.Exception.ProdutoException;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity<Produto> cadastrarProduto(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.criarProduto(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoProduto);
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarTodosProdutos() {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.listarTodosProdutos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> listarProdutoPorId(@PathVariable UUID id) {
        try {
            Produto produto = produtoService.buscarProduto(id);
            return ResponseEntity.status(HttpStatus.OK).body(produto);
        } catch (ProdutoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeProduto(@PathVariable UUID id) {
        try {
            produtoService.removerProduto(id);
            return ResponseEntity.status(HttpStatus.OK).body("Produto removido com sucesso.");
        } catch (ProdutoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
