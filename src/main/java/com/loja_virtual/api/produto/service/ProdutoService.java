package com.loja_virtual.api.produto.service;

import com.loja_virtual.api.produto.ProdutoException;
import com.loja_virtual.api.produto.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final List<Produto> produtos = new ArrayList<>();

    public Produto criarProduto(Produto produto) {
        Produto novoProduto = new Produto( produto.getId() ,produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQuantidade(), produto.getCategoria());
        produtos.add(novoProduto);
        return novoProduto;
    }

    public List<Produto> listarTodosProdutos() {
        return produtos;
    }

    public Produto buscarProduto(UUID id) {
        Produto produto = produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProdutoException("Produto não encontrado"));
        return produto;
    }

    public void removerProduto(UUID id) {
        Produto produto = produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProdutoException("Produto não encontrado"));

        produtos.remove(produto);
    }

    public Produto atualizarProduto(UUID id, Produto produtoAtualizado) {
        Produto produto = produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ProdutoException("Produto não encontrado"));

        produto.setNome(produtoAtualizado.getNome());
        produto.setDescricao(produtoAtualizado.getDescricao());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setQuantidade(produtoAtualizado.getQuantidade());
        produto.setCategoria(produtoAtualizado.getCategoria());

        return produto;
    }




}
