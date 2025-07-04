package com.alura.literalura.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LivroDTO(
        @JsonProperty("title") String titulo,
        @JsonProperty("authors") List<AutorDTO> autores,
        @JsonProperty("languages") List<String> idiomas,
        @JsonProperty("download_count") int numeroDownloads
) {
}
