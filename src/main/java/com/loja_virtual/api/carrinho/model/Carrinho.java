package com.loja_virtual.api.carrinho.model;

import com.loja_virtual.api.produto.model.Produto;
import lombok.*;

import java.util.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Carrinho {
    private UUID id = UUID.randomUUID();
    private List<Produto> produtos = new ArrayList<>();

    public void adicionarProduto(Produto produto){
        this.produtos.add(produto);
    }

    public void removeProduto(Produto produto){
        this.produtos.stream()
            .filter(p -> p.getId().equals(produto.getId()))
            .findFirst()
            .ifPresent(
                    p -> produtos.remove(p)
            );
    }
}
