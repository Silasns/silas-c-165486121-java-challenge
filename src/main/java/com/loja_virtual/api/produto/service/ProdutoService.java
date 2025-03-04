package com.loja_virtual.api.produto.service;

import com.loja_virtual.api.produto.Exception.ProdutoException;
import com.loja_virtual.api.produto.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final List<Produto> produtos = new ArrayList<>();

    public Produto criarProduto(Produto produto) {
        Produto novoProduto = new Produto( randomUUID() ,produto.nome(), produto.descricao(), produto.preco(), produto.quantidade(), produto.categoria());
        produtos.add(novoProduto);
        return novoProduto;
    }

    public List<Produto> listarTodosProdutos() {
        return produtos;
    }

    public Produto buscarProduto(UUID id) {
        Produto produto = produtos.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProdutoException("Produto não encontrado"));
        return produto;
    }

    public void removerProduto(UUID id) {
        Produto produto = produtos.stream()
                .filter(p -> p.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProdutoException("Produto não encontrado"));

        produtos.remove(produto);
    }

}
