package com.loja_virtual.api.categoria.service;

import com.loja_virtual.api.categoria.Exception.CategoriaException;
import com.loja_virtual.api.categoria.model.Categoria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.util.UUID.randomUUID;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final List<Categoria> categorias = new ArrayList<>();

    public Categoria criarCategoria(Categoria categoria) {
        Categoria novaCategoria = new Categoria( randomUUID(), categoria.nome());
        categorias.add(novaCategoria);
        return novaCategoria;
    }

    public List<Categoria> listaCategorias() { return categorias; }

    public Categoria listaCategoriaPorNome(String nome) {
        Categoria categoria = categorias.stream()
                .filter(c -> c.nome().equals(nome))
                .findFirst()
                .orElseThrow(() -> new CategoriaException("Categoria não Encontrada"));

        return categoria;
    }

    public String removerCategoria(String nome) {
        Categoria categoria = categorias.stream()
                .filter(c -> c.nome().equals(nome))
                .findFirst()
                .orElseThrow(() -> new CategoriaException("Categoria não Encontrada"));

        categorias.remove(categoria);
        return "Categoria removida com sucesso.";
    }

}
