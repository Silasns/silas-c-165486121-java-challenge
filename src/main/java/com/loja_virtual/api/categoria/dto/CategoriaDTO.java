package com.loja_virtual.api.categoria.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    @NotBlank(message = "A categoria não pode estar vazia")
    private String nome;
}
