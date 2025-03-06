package com.loja_virtual.api.carrinho.service;

import com.loja_virtual.api.produto.model.Produto;

public interface CarrinhoInterface {
    default void adicionarProdutoCarrinho(Produto produto) {

    }
}
