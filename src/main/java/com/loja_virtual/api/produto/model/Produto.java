package com.loja_virtual.api.produto.model;

import com.loja_virtual.api.categoria.model.Categoria;
import lombok.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Produto {
    private UUID id;
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private int quantidade;
    private Set<Categoria> categoria = new HashSet<>();

    public void adicionaCategoria(Categoria categoria) {
        this.categoria.add(categoria);
    }

    public void removerCategoria(Categoria categoria) {
        this.categoria.remove(categoria);
    }
}
