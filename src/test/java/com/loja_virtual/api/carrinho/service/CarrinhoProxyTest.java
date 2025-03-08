package com.loja_virtual.api.carrinho.service;

import com.loja_virtual.api.carrinho.exception.CarrinhoException;
import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CarrinhoProxyTest {

    @Mock
    private CarrinhoService carrinhoService;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private CarrinhoProxy carrinhoProxy;

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
    void naoDeveAdicionarProdutoSemCategoriaAoCarrinho() {
        BDDMockito.given(produtoService.buscarProduto(produto.getId())).willReturn(produto);

        CarrinhoException exception = assertThrows(CarrinhoException.class, () -> {
            carrinhoProxy.adicionarProdutoCarrinho(produto);
        });

        assertEquals("Somente produtos associados à categoria podem ser adicionados ao Carrinho", exception.getMessage());
    }

    @Test
    void deveAdicionarProdutoAoCarrinho() {
        produto.adicionaCategoria(categoria);
        BDDMockito.given(produtoService.buscarProduto(produto.getId())).willReturn(produto);

        carrinhoProxy.adicionarProdutoCarrinho(produto);

        verify(carrinhoService, times(1)).adicionarProdutoCarrinho(produto);
    }

}