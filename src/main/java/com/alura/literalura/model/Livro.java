package com.alura.literalura.model;

import com.alura.literalura.dtos.AutorDTO;
import com.alura.literalura.dtos.LivroDTO;
import com.alura.literalura.dtos.LivroResponseDTO;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "tb_livro")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "tb_livro_autor")
    private List<Autor> autores;

    @ManyToMany(cascade = CascadeType.ALL, fetch =  FetchType.EAGER)
    @JoinTable(name = "tb_livro_idioma")
    private List<Idioma> idiomas;

    private Double numeroDownloads;

    public Livro() {
    }

    public Livro(Long id, String titulo, Double numeroDownloads) {
        this.id = id;
        this.titulo = titulo;
        this.numeroDownloads = numeroDownloads;
    }
    public Livro(LivroResponseDTO response) {
        LivroDTO dto = response.results().get(0); // pega o primeiro livro da lista

        this.titulo = dto.titulo();
        this.numeroDownloads = (double) dto.numeroDownloads();

        // Inicializa listas
        this.autores = dto.autores().stream()
                .map(autorDTO -> new Autor(autorDTO.nome(), autorDTO.anoNascimento(), autorDTO.anoFalecimento()))
                .collect(Collectors.toList());

        this.idiomas = dto.idiomas().stream()
                .map(Idioma::new) // cria novo Idioma com código como "pt", "en", etc.
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Double numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", autores=" + autores +
                ", idiomas=" + idiomas +
                ", numeroDownloads=" + numeroDownloads +
                '}';
    }
}
