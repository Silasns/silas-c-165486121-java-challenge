package com.loja_virtual.api.categoria.service;

import com.loja_virtual.api.categoria.exception.CategoriaException;
import com.loja_virtual.api.categoria.repository.CategoriaRepository;
import com.loja_virtual.api.produto.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CategoriaProxy implements CategoriaInterface {

    private final CategoriaService categoriaService;

    public CategoriaProxy(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoService produtoService;

    @Override
    public String removerCategoria(String nome){
        categoriaRepository.buscarCategoriaPorNome(nome)
                .orElseThrow(() -> new CategoriaException("Categoria não Encontrada"));

        produtoService.listarTodosProdutos().stream()
                .filter(produto -> produto.getCategoria().stream()
                        .anyMatch(produtoCategoria -> produtoCategoria.getNome().equalsIgnoreCase(nome)))
                .findFirst().
                ifPresentOrElse(
                        produto -> { throw new CategoriaException("Categoria associada a um produto não pode ser excluida"); },
                        () -> {categoriaRepository.removerCategoria(nome);}
                        );

        return "Categoria removida com sucesso.";
    }

}
