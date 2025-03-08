package com.loja_virtual.api.categoria.service;

import com.loja_virtual.api.categoria.exception.CategoriaException;
import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.categoria.repository.CategoriaRepository;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.repository.ProdutoRepository;
import com.loja_virtual.api.produto.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoriaProxyTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private CategoriaProxy categoriaProxy;

    @Mock
    private CategoriaService categoriaService;

    private Categoria categoria;
    private Categoria categoria2;
    private Produto produto;

    @BeforeEach
    void setUp() {
        categoria = new Categoria("Eletrônicos");
        categoria2 = new Categoria("Livros");
        produto = Produto.builder()
                .id(UUID.fromString("2d364bcc-fbe7-4cf2-9ebe-34d7dd39374d"))
                .nome("Produto")
                .descricao("Descrição")
                .preco(new BigDecimal(100.0))
                .quantidadeDisponivel(10)
                .build();
    }

    @Test
    void deveRetornarExceptionAoTentarRemoverCategoriaNaoExistente() {
        BDDMockito.given(categoriaRepository.buscarCategoriaPorNome(categoria.getNome())).willReturn(Optional.empty());

        CategoriaException exception = assertThrows(CategoriaException.class, () -> {
            categoriaProxy.removerCategoria(categoria.getNome());
        });

        assertEquals("Categoria não Encontrada", exception.getMessage());
    }

    @Test
    void deveRetornarExceptionAoTentarRemoverCategoriaAssociadaAoProduto() {
        produto.adicionaCategoria(categoria);

        BDDMockito.given(produtoService.listarTodosProdutos()).willReturn(List.of(produto));

        BDDMockito.given(categoriaRepository.buscarCategoriaPorNome(categoria.getNome())).willReturn(Optional.of(categoria));

        CategoriaException exception = assertThrows(CategoriaException.class, () -> {
            categoriaProxy.removerCategoria(categoria.getNome());
        });

        assertEquals("Categoria associada a um produto não pode ser excluida", exception.getMessage());
    }

    @Test
    void deveRemoverCategoria(){
        produto.adicionaCategoria(categoria);
        BDDMockito.given(produtoService.listarTodosProdutos()).willReturn(List.of(produto));
        BDDMockito.given(categoriaRepository.buscarCategoriaPorNome(categoria2.getNome())).willReturn(Optional.of(categoria2));

        String resultado = categoriaProxy.removerCategoria(categoria2.getNome());

        assertEquals("Categoria removida com sucesso.", resultado);
    }


}