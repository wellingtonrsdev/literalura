package com.alura.literalura.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroResponseDTO(
        int count,
        String next,
        String previous,
        List<LivroDTO> results
) {
}
