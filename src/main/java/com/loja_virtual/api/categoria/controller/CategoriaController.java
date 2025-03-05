package com.loja_virtual.api.categoria.controller;

import com.loja_virtual.api.categoria.Exception.CategoriaException;
import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.categoria.service.CategoriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/categorias")
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public ResponseEntity<Object> cadastrarCategoria(@RequestBody Categoria categoria) {
        Categoria novaCategoria = categoriaService.criarCategoria(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }

    @GetMapping
    public ResponseEntity<List<Categoria>> listarCategorias() {
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

            return ResponseEntity.status(HttpStatus.OK).body(categoriaService.removerCategoria(nome));
        } catch (CategoriaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
