package com.loja_virtual.api.categoria.service;

import com.loja_virtual.api.categoria.exception.CategoriaException;
import com.loja_virtual.api.categoria.repository.CategoriaRepository;
import com.loja_virtual.api.categoria.model.Categoria;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria criarCategoria(String categoria) {
        Categoria novaCategoria = new Categoria(categoria);
        categoriaRepository.salvarCategoria(novaCategoria);
        return novaCategoria;
    }

    public Set<Categoria> listaCategorias() { return categoriaRepository.listarTodos(); }

    public Categoria buscarCategoriaPorNome(String nome) {
        Categoria categoria = categoriaRepository.buscarCategoriaPorNome(nome)
                .orElseThrow(() -> new CategoriaException("Categoria não Encontrada"));
        return categoria;
    }

    public String removerCategoria(String nome) {
        categoriaRepository.buscarCategoriaPorNome(nome)
                .orElseThrow(() -> new CategoriaException("Categoria não Encontrada"));
        categoriaRepository.removerCategoria(nome);
        return "Categoria removida com sucesso.";
    }

}
