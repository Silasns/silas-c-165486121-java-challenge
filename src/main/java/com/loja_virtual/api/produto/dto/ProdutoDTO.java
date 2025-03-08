package com.loja_virtual.api.produto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
    @NotBlank(message = "O nome não pode estar vazio")
    private String nome;

    @NotBlank(message = "A descrição não pode estar vazia")
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo")
    private BigDecimal preco;

    @NotNull(message = "A quantidadde não pode ser nula")
    private int quantidadeDisponivel;
}

