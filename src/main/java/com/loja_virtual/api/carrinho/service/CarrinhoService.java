package com.loja_virtual.api.carrinho.service;

import com.loja_virtual.api.carrinho.Exception.CarrinhoException;
import com.loja_virtual.api.carrinho.model.Carrinho;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarrinhoService {

    @Autowired
    private ProdutoService produtoService;

    private final Carrinho carrinho = new Carrinho();

    public void adicionarProdutoCarrinho(UUID idProduto) {
        Produto produto = produtoService.buscarProduto(idProduto);
        carrinho.adicionarProduto(produto);
    }

    public Carrinho listarCarrinho() {
        return carrinho;
    }

    public void removerProdutoCarrinho(UUID idProduto) {
        Produto produto = produtoService.buscarProduto(idProduto);
        carrinho.getProdutos().stream()
                .filter(p -> p.getId().equals(produto.getId()))
                .findFirst()
                .orElseThrow(() -> new CarrinhoException("Produto não esta inserido no carrinho"));
        carrinho.removeProduto(produto);
    }
}
