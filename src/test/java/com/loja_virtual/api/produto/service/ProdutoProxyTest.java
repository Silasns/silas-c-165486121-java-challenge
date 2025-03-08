package com.loja_virtual.api.produto.service;

import com.loja_virtual.api.carrinho.respository.CarrinhoRepository;
import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.produto.exception.ProdutoException;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ProdutoProxyTest {

    @Mock
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CarrinhoRepository carrinhoRepository;

    @InjectMocks
    private ProdutoProxy produtoProxy;

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
    void deveRetornarExecptionSeProdutoNaoExiste() {
        BDDMockito.given(produtoRepository.buscarProduto(produto.getId())).willReturn(Optional.empty());

        ProdutoException exception = assertThrows(ProdutoException.class, () -> {
            produtoProxy.removerProduto(produto.getId());
        });

        assertEquals("Produto não encontrado", exception.getMessage());
    }

    @Test
    void deveRetornarExecptionSeProdutoEstiverInseridoNoCarrinho() {
        BDDMockito.given(produtoRepository.buscarProduto(produto.getId())).willReturn(Optional.of(produto));
        BDDMockito.given(carrinhoRepository.existeProduto(produto.getId())).willReturn(true);

        ProdutoException exception = assertThrows(ProdutoException.class, () -> {
            produtoProxy.removerProduto(produto.getId());
        });

        assertEquals("O produto não pode ser removido, pois esta inserido no carrinho", exception.getMessage());
    }

    @Test
    void deveRemoverProduto() {
        BDDMockito.given(produtoRepository.buscarProduto(produto.getId())).willReturn(Optional.of(produto));
        BDDMockito.given(carrinhoRepository.existeProduto(produto.getId())).willReturn(false);

        String resultado = produtoProxy.removerProduto(produto.getId());

        assertEquals("Produto removido com sucesso.", resultado);
    }
}