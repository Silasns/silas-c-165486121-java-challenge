package com.loja_virtual.api.carrinho.service;

import com.loja_virtual.api.carrinho.exception.CarrinhoException;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoProxy implements CarrinhoInterface {
    private final CarrinhoService carrinhoService;

    public CarrinhoProxy(CarrinhoService carrinhoService) {
        this.carrinhoService = carrinhoService;
    }

    @Autowired
    private ProdutoService produtoService;

    @Override
    public void adicionarProdutoCarrinho(Produto produto) {
        Produto produtoEncontrado = produtoService.buscarProduto(produto.getId());
        if (produtoEncontrado.getCategoria().size() > 0) {
            carrinhoService.adicionarProdutoCarrinho(produtoEncontrado);
        } else {
            throw new CarrinhoException("Somente produtos associados à categoria podem ser adicionados ao Carrinho");
        }
    }
}
