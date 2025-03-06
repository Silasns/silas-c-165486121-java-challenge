package com.loja_virtual.api.produto.service;

import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.categoria.service.CategoriaService;
import com.loja_virtual.api.produto.exception.ProdutoException;
import com.loja_virtual.api.produto.repository.ProdutoRepository;
import com.loja_virtual.api.produto.model.Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class ProdutoService {

    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto criarProduto(Produto produto) {
        Produto novoProduto = new Produto( randomUUID() ,produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQuantidadeDisponivel(), produto.getCategoria());
        produtoRepository.salvarProduto(novoProduto);
        return novoProduto;
    }

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.listarTodos();
    }

    public Produto buscarProduto(UUID id) {
        return produtoRepository.buscarProduto(id)
                .orElseThrow(() -> new ProdutoException("Produto não encontrado"));
    }

    public void removerProduto(UUID id) {
        Boolean existeProduto = produtoRepository.existeProduto(id);
        if (!existeProduto) {
            throw new ProdutoException("Produto não encontrado");
        } else {
            produtoRepository.removerProduto(id);
        }
    }

    public void associarProdutoCategoria(UUID idProduto, String nomeCategoria){
        Produto produto = buscarProduto(idProduto);
        Categoria categoria = categoriaService.buscarCategoriaPorNome(nomeCategoria);
        produto.adicionaCategoria(categoria);
    }

    public void desassociarProdutoCategoria(UUID idProduto, String nomeCategoria){
        Produto produto = buscarProduto(idProduto);
        Categoria categoria = categoriaService.buscarCategoriaPorNome(nomeCategoria);

        produto.getCategoria().stream()
                .filter(produtoCategoria -> produtoCategoria.getNome().equals(categoria.getNome()))
                .findFirst()
                        .orElseThrow(() -> new ProdutoException("Categoria não esta associada a este produto"));

        produto.removerCategoria(categoria);
    }

}
