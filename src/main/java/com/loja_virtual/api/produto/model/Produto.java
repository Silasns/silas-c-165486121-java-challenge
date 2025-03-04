package com.loja_virtual.api.produto.model;

import com.loja_virtual.api.categoria.model.Categoria;

import java.math.BigDecimal;
import java.util.UUID;


public record Produto (
        UUID id,
        String nome,
        String descricao,
        BigDecimal preco,
        int quantidade,
        Categoria categoria
    ){}
