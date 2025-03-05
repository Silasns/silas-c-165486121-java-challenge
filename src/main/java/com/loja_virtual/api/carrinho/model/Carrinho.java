package com.loja_virtual.api.carrinho.model;

import com.loja_virtual.api.produto.model.Produto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Carrinho {
    private UUID id;
    private List<Produto> produtos = new ArrayList<>();

    public void adicionarProduto(Produto produto){
        this.produtos.add(produto);
    }

    public void removeProduto(Produto produto){
        this.produtos.remove(produto);
    }
}
