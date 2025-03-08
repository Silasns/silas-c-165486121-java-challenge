package com.loja_virtual.api;

import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.categoria.service.CategoriaService;
import com.loja_virtual.api.produto.dto.ProdutoDTO;
import com.loja_virtual.api.produto.exception.ProdutoException;
import com.loja_virtual.api.produto.model.Produto;
import com.loja_virtual.api.produto.repository.ProdutoRepository;
import com.loja_virtual.api.produto.service.ProdutoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class LojaVirtualApiApplicationTests {

}
