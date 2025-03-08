package com.loja_virtual.api.carrinho.service;

import com.loja_virtual.api.carrinho.exception.CarrinhoException;
import com.loja_virtual.api.carrinho.model.Carrinho;
import com.loja_virtual.api.carrinho.respository.CarrinhoRepository;
import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.produto.dto.ProdutoDTO;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class CarrinhoServiceTest {
    @InjectMocks
    private CarrinhoService carrinhoService;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @Captor
    private ArgumentCaptor<Produto> produtoCaptor;
    private Produto produto;
    private UUID produtoId;
    private Categoria categoria;
    private ProdutoDTO produtoDTO;

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
    }

    @Test
    void deveAdicionarProdutoAoCarrinho() {
        produto.adicionaCategoria(categoria);
        given(produtoService.buscarProduto(produtoId)).willReturn(produto);

        carrinhoService.adicionarProdutoCarrinho(produto);

        then(carrinhoRepository).should().inserir(produtoCaptor.capture());
        Produto produtoAdicionado = produtoCaptor.getValue();


        assertNotNull(produtoAdicionado);
        assertEquals(produto.getId(), produtoAdicionado.getId());
        assertEquals(produto.getNome(), produtoAdicionado.getNome());
    }

    @Test
    void deveListarCarrinhoComSucesso() {
        produto.adicionaCategoria(categoria);
        Carrinho carrinhoMock = new Carrinho();
        carrinhoMock.adicionarProduto(produto);

        given(carrinhoRepository.listarTodos()).willReturn(carrinhoMock);

        Carrinho carrinhoRetornado = carrinhoService.listarCarrinho();

        assertNotNull(carrinhoRetornado);
        assertEquals(1, carrinhoRetornado.getProdutos().size());
        assertTrue(carrinhoRetornado.getProdutos().contains(produto));

        then(carrinhoRepository).should().listarTodos();
    }

    @Test
    void deveRemoverProdutoDoCarrinhoComSucesso() {
        given(carrinhoRepository.existeProduto(produtoId)).willReturn(true);

        carrinhoService.removerProdutoCarrinho(produto);

        then(carrinhoRepository).should().remover(produtoCaptor.capture());
        Produto produtoRemovido = produtoCaptor.getValue();

        assertNotNull(produtoRemovido);
        assertEquals(produto.getId(), produtoRemovido.getId());
    }

    @Test
    void deveLancarExcecaoAoRemoverProdutoNaoExistenteNoCarrinho() {
        given(carrinhoRepository.existeProduto(produtoId)).willReturn(false);

        CarrinhoException exception = assertThrows(CarrinhoException.class, () -> {
            carrinhoService.removerProdutoCarrinho(produto);
        });

        assertEquals("Produto não esta inserido no carrinho", exception.getMessage());
        then(carrinhoRepository).should().existeProduto(produtoId);
    }
}