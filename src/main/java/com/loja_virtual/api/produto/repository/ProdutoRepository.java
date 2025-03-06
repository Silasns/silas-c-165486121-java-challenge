package com.loja_virtual.api.produto.repository;

import com.loja_virtual.api.produto.model.Produto;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProdutoRepository {
    private final Map<UUID,Produto> produtos = new HashMap();

    public void salvarProduto(Produto produto) {
        this.produtos.put(produto.getId(), produto);
    }

    public void removerProduto(UUID id) {
        this.produtos.remove(id);
    }

    public List<Produto> listarTodos() {
        return new ArrayList(produtos.values());
    }

    public Optional<Produto> buscarProduto(UUID id) {
        return Optional.ofNullable(produtos.get(id));
    }

    public Boolean existeProduto(UUID id) {
        return produtos.containsKey(id);
    }
}
