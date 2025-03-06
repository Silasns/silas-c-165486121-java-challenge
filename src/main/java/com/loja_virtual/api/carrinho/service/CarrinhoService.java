package com.loja_virtual.api.carrinho.service;

import com.loja_virtual.api.carrinho.exception.CarrinhoException;
import com.loja_virtual.api.carrinho.model.Carrinho;
import com.loja_virtual.api.carrinho.respository.CarrinhoRepository;
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

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public void adicionarProdutoCarrinho(Produto produto) {
        carrinhoRepository.inserir(produto);
    }

    public Carrinho listarCarrinho() {
        return carrinhoRepository.listarTodos();
    }

    public void removerProdutoCarrinho(Produto produto) {
        Boolean existePruduto = carrinhoRepository.existeProduto(produto.getId());
        if (!existePruduto) {
            throw new CarrinhoException("Produto não esta inserido no carrinho");
        } else {
            carrinhoRepository.remover(produto);
        }
    }
}
