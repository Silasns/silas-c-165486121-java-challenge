package com.loja_virtual.api.categoria.controller;

import com.loja_virtual.api.categoria.Exception.CategoriaException;
import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.categoria.service.CategoriaProxy;
import com.loja_virtual.api.categoria.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaProxy categoriaProxy;

    @PostMapping("/{categoria}")
    public ResponseEntity<Object> cadastrarCategoria(@PathVariable String categoria) {
        Categoria novaCategoria = categoriaService.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @GetMapping
    public ResponseEntity<Set<Categoria>> listarCategorias() {
        return ResponseEntity.status(HttpStatus.OK).body(categoriaService.listaCategorias());
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Object> listarCategoriasPorNome(@PathVariable String nome) {
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorNome(nome);
            return ResponseEntity.status(HttpStatus.OK).body(categoria);
        } catch (CategoriaException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Object> removerCategoria(@PathVariable String nome) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categoriaProxy.removerCategoria(nome));
        } catch (CategoriaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
