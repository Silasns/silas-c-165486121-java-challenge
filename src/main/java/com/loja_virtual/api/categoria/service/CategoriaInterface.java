package com.loja_virtual.api.categoria.service;

public interface CategoriaInterface {

    default String removerCategoria(String nome){;
        return "";
    }

}
