package com.loja_virtual.api.produto.service;

import com.loja_virtual.api.carrinho.respository.CarrinhoRepository;
import com.loja_virtual.api.produto.exception.ProdutoException;
import com.loja_virtual.api.produto.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProdutoProxy implements ProdutoInterface {

    private final ProdutoService produtoService;

    public ProdutoProxy(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Override
    public String removerProduto(UUID id){
        produtoRepository.buscarProduto(id).ifPresentOrElse(
                produto -> {
                    Boolean produtoInserido = carrinhoRepository.existeProduto(id);

                    if (produtoInserido){
                        throw new ProdutoException("O produto não pode ser removido, pois esta inserido no carrinho");
                    } else {
                        produtoService.removerProduto(id);
                    }
                },
                () -> { throw new ProdutoException("Produto não encontrado"); }
                );
        return "Produto removido com sucesso.";
    }


}
