package com.loja_virtual.api.carrinho.respository;

import com.loja_virtual.api.carrinho.model.Carrinho;
import com.loja_virtual.api.produto.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CarrinhoRepository {
    private final Carrinho carrinho = new Carrinho();

    public void inserir(Produto produto) {
        carrinho.adicionarProduto(produto);
    }

    public Carrinho listarTodos(){ return carrinho;}

    public void remover(Produto produto) {
        carrinho.removeProduto(produto);
    }

    public Boolean existeProduto(UUID idProduto) {
        return carrinho.getProdutos().stream()
                .anyMatch(produto -> produto.getId().equals(idProduto));
    }
}
