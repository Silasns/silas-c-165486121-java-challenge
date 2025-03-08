package com.loja_virtual.api.produto.service;

import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.categoria.service.CategoriaService;
import com.loja_virtual.api.produto.dto.ProdutoDTO;
import com.loja_virtual.api.produto.exception.ProdutoException;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaService categoriaService;

    @Captor
    private ArgumentCaptor<Produto> produtoCaptor;

    @Captor
    private ArgumentCaptor<List<Produto>> produtoListCaptor;

    @Captor
    private ArgumentCaptor<UUID> produtoIdCaptor;

    private ProdutoDTO produtoDTO;
    private Produto produto;
    private Produto produto2;
    private UUID produtoId;
    private Categoria categoria;

    @BeforeEach
    void setUp() {
        categoria = new Categoria("Eletrônicos");
        produtoId = UUID.fromString("4d33bcc-fbe7-4cf5-9ebe-34d7dd39374d");
        produtoDTO = new ProdutoDTO("Produto", "Descrição", new BigDecimal(100.0), 10);
        produto = Produto.builder()
                .id(produtoId)
                .nome(produtoDTO.getNome())
                .descricao(produtoDTO.getDescricao())
                .preco(produtoDTO.getPreco())
                .quantidadeDisponivel(produtoDTO.getQuantidadeDisponivel())
                .build();

        produto2 = Produto.builder()
                .id(UUID.fromString("2d364bcc-fbe7-4cf2-9ebe-34d7dd39374d"))
                .nome("Outro Produto")
                .descricao("Outra descrição")
                .preco(new BigDecimal(200.0))
                .quantidadeDisponivel(5)
                .build();
    }

    @Test
    void deveCriarProduto() {
        produtoService.criarProduto(produtoDTO);

        then(produtoRepository).should().salvarProduto(produtoCaptor.capture());
        Produto produtoCriado = produtoCaptor.getValue();

        assertNotNull(produtoCriado);
        assertEquals(produtoDTO.getNome(), produtoCriado.getNome());
        assertEquals(produtoDTO.getDescricao(), produtoCriado.getDescricao());
        assertEquals(produtoDTO.getPreco(), produtoCriado.getPreco());
        assertEquals(produtoDTO.getQuantidadeDisponivel(), produtoCriado.getQuantidadeDisponivel());
    }

    @Test
    void deveListarTodosOsProdutos() {
        List<Produto> produtos = List.of(produto, produto2);
        given(produtoRepository.listarTodos()).willReturn(produtos);
        when(produtoRepository.listarTodos()).thenReturn(produtos);

        List<Produto> resultado = produtoService.listarTodosProdutos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.contains(produto));
        assertTrue(resultado.contains(produto2));

        verify(produtoRepository, times(1)).listarTodos();
    }

    @Test
    void deveBuscarProdutoPorIdComSucesso() {
        given(produtoRepository.buscarProduto(produtoId)).willReturn(Optional.of(produto));

        Produto resultado = produtoService.buscarProduto(produtoId);

        assertNotNull(resultado);
        assertEquals(produtoId, resultado.getId());

        then(produtoRepository).should().buscarProduto(produtoIdCaptor.capture());
        assertEquals(produtoId, produtoIdCaptor.getValue());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoEncontrado() {
        given(produtoRepository.buscarProduto(produtoId)).willReturn(Optional.empty());

        ProdutoException exception = assertThrows(ProdutoException.class, () -> produtoService.buscarProduto(produtoId));

        assertEquals("Produto não encontrado", exception.getMessage());

        then(produtoRepository).should().buscarProduto(produtoIdCaptor.capture());
        assertEquals(produtoId, produtoIdCaptor.getValue());
    }

    @Test
    void deveRemoverProdutoComSucesso() {
        given(produtoRepository.existeProduto(produtoId)).willReturn(true);

        produtoService.removerProduto(produtoId);

        then(produtoRepository).should().removerProduto(produtoIdCaptor.capture());
        assertEquals(produtoId, produtoIdCaptor.getValue());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExisteAoRemover() {
        given(produtoRepository.existeProduto(produtoId)).willReturn(false);

        ProdutoException exception = assertThrows(ProdutoException.class, () -> produtoService.removerProduto(produtoId));

        assertEquals("Produto não encontrado", exception.getMessage());

        then(produtoRepository).should().existeProduto(produtoIdCaptor.capture());
        assertEquals(produtoId, produtoIdCaptor.getValue());
    }

    @Test
    void deveAssociarProdutoACategoria() {
        given(produtoRepository.buscarProduto(produtoId)).willReturn(Optional.of(produto));
        given(categoriaService.buscarCategoriaPorNome(categoria.getNome())).willReturn(categoria);

        produtoService.associarProdutoCategoria(produtoId, categoria.getNome());

        assertTrue(produto.getCategoria().contains(categoria));

        then(produtoRepository).should().buscarProduto(produtoIdCaptor.capture());
        assertEquals(produtoId, produtoIdCaptor.getValue());

        then(categoriaService).should().buscarCategoriaPorNome(categoria.getNome());
    }

    @Test
    void deveDesassociarProdutoDeCategoria() {
        given(produtoRepository.buscarProduto(produtoId)).willReturn(Optional.of(produto));
        given(categoriaService.buscarCategoriaPorNome(categoria.getNome())).willReturn(categoria);

        produto.adicionaCategoria(categoria);
        produtoService.desassociarProdutoCategoria(produtoId, categoria.getNome());

        assertFalse(produto.getCategoria().contains(categoria));

        then(produtoRepository).should().buscarProduto(produtoIdCaptor.capture());
        assertEquals(produtoId, produtoIdCaptor.getValue());

        then(categoriaService).should().buscarCategoriaPorNome(categoria.getNome());
    }

    @Test
    void deveLancarExcecaoAoDesassociarCategoriaNaoAssociada() {
        given(produtoRepository.buscarProduto(produtoId)).willReturn(Optional.of(produto));
        given(categoriaService.buscarCategoriaPorNome(categoria.getNome())).willReturn(categoria);

        ProdutoException exception = assertThrows(ProdutoException.class, () -> produtoService.desassociarProdutoCategoria(produtoId, categoria.getNome()));

        assertEquals("Categoria não esta associada a este produto", exception.getMessage());

        then(produtoRepository).should().buscarProduto(produtoIdCaptor.capture());
        assertEquals(produtoId, produtoIdCaptor.getValue());

        then(categoriaService).should().buscarCategoriaPorNome(categoria.getNome());
    }
}