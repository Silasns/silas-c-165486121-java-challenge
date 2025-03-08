package com.loja_virtual.api.categoria.service;

import com.loja_virtual.api.categoria.dto.CategoriaDTO;
import com.loja_virtual.api.categoria.exception.CategoriaException;
import com.loja_virtual.api.categoria.model.Categoria;
import com.loja_virtual.api.categoria.repository.CategoriaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServiceTest {

    @InjectMocks
    private CategoriaService categoriaService;

    @Mock
    private CategoriaRepository categoriaRepository;

    private CategoriaDTO categoriaDTO;

    private Categoria categoria;
    private Categoria categoria2;

    @Captor
    private ArgumentCaptor<Categoria> categoriaCaptor;

    @Captor
    private ArgumentCaptor<String> categoriaStringCaptor;

    @BeforeEach
    void setUp() {
        categoriaDTO = new CategoriaDTO("Eletrônicos");
        categoria = new Categoria("Eletrônicos");
        categoria2 = new Categoria("Livros");
    }

    @Test
    void deveSalvarCategoria() {
        categoriaService.criarCategoria(categoriaDTO);

        then(categoriaRepository).should().salvarCategoria(categoriaCaptor.capture());
        Categoria categoriaSalva = categoriaCaptor.getValue();
        assertEquals(categoriaDTO.getNome(), categoriaSalva.getNome());
    }

    @Test
    void deveBuscarCategoriaPorNome() {
        BDDMockito.given(categoriaRepository.buscarCategoriaPorNome(categoriaDTO.getNome())).willReturn(Optional.of(categoria));

        Categoria resultado = categoriaService.buscarCategoriaPorNome(categoriaDTO.getNome());

        assertNotNull(resultado);
        assertEquals(categoriaDTO.getNome(), resultado.getNome());
        then(categoriaRepository).should().buscarCategoriaPorNome(categoriaStringCaptor.capture());
        verify(categoriaRepository).buscarCategoriaPorNome(categoriaStringCaptor.capture());
        assertEquals(categoriaDTO.getNome(), categoriaStringCaptor.getValue());
    }

    @Test
    void deveListarTodasAsCategorias() {
        Set<Categoria> categoriasMock = new HashSet<>();
        categoriasMock.add(categoria);
        categoriasMock.add(categoria2);

        when(categoriaRepository.listarTodos()).thenReturn(categoriasMock);

        Set<Categoria> resultado = categoriaService.listaCategorias();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Eletrônicos")));
        assertTrue(resultado.stream().anyMatch(c -> c.getNome().equals("Livros")));

        verify(categoriaRepository, times(1)).listarTodos();
    }

    @Test
    void deveRemoverCategoriaComSucesso() {
        when(categoriaRepository.buscarCategoriaPorNome(categoria.getNome())).thenReturn(Optional.of(categoria));
        String resultado = categoriaService.removerCategoria(categoria.getNome());

        assertEquals("Categoria removida com sucesso.", resultado);

        verify(categoriaRepository, times(1)).buscarCategoriaPorNome(categoria.getNome());
        verify(categoriaRepository, times(1)).removerCategoria(categoria.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoCategoriaNaoEncontrada() {
        when(categoriaRepository.buscarCategoriaPorNome(categoria.getNome())).thenReturn(Optional.empty());

        CategoriaException exception = assertThrows(CategoriaException.class, () -> categoriaService.removerCategoria(categoria.getNome()));

        assertEquals("Categoria não Encontrada", exception.getMessage());

        verify(categoriaRepository, times(1)).buscarCategoriaPorNome(categoria.getNome());
        verify(categoriaRepository, never()).removerCategoria(anyString());
    }

}