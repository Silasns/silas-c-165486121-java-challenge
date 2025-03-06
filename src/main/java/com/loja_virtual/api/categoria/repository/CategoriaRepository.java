package com.loja_virtual.api.categoria.repository;

import com.loja_virtual.api.categoria.model.Categoria;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class CategoriaRepository {
    private final Set<Categoria> categorias = new HashSet<>();

    public void salvarCategoria(Categoria categoria) {
        categorias.add(categoria);
    }

    public void removerCategoria(String nome) {
        categorias.removeIf(categoria -> categoria.getNome().equalsIgnoreCase(nome));
    }

    public Set<Categoria> listarTodos(){
        return categorias;
    }

    public Optional<Categoria> buscarCategoriaPorNome(String nome) {
        return categorias.stream()
                .filter(categoria -> categoria.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public Boolean existeCategoria(String nome) {
        return categorias.stream()
                .anyMatch(categoria -> categoria.getNome().equalsIgnoreCase(nome));
    }


}
