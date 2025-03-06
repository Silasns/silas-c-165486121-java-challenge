package com.loja_virtual.api.produto.service;

import java.util.UUID;

public interface ProdutoInterface {

    default String removerProduto(UUID id) {
        return "";
    }

}
