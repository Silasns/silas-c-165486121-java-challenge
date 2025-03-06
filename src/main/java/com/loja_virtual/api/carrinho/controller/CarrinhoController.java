package com.loja_virtual.api.carrinho.controller;

import com.loja_virtual.api.carrinho.model.Carrinho;
import com.loja_virtual.api.carrinho.service.CarrinhoService;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping
    public ResponseEntity<Object> AdicionarProdutoCarrinho(@RequestBody Produto produto) {
        try {
            carrinhoService.adicionarProdutoCarrinho(produto);
        return ResponseEntity.status(HttpStatus.OK).body("Produto adicionado ao carrinho");
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<Carrinho> ListarProdutosCarrinho() {
        return ResponseEntity.status(HttpStatus.OK).body(carrinhoService.listarCarrinho());
    }

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
