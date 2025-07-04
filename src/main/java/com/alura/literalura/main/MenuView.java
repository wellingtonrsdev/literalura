package com.alura.literalura.main;

import com.alura.literalura.dtos.LivroResponseDTO;
import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Idioma;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repositories.AutorRepository;
import com.alura.literalura.repositories.LivroRepository;
import com.alura.literalura.services.ConvertData;
import com.alura.literalura.services.GutendexClient;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class MenuView {

    private final Scanner scanner = new Scanner(System.in);
    private final GutendexClient client = new GutendexClient();
    private final ConvertData converter = new ConvertData();
    private final String baseUrl = "https://gutendex.com/books?search=";
    private final LivroRepository repository;
    private final AutorRepository autorRepository;

    public MenuView(LivroRepository repository, AutorRepository autorRepository) {
        this.repository = repository;
        this.autorRepository = autorRepository;
    }

    public void showMenu() {
        int opcao;
        do {
            System.out.println("--------------------------------------------------------------------------\n");
            System.out.println("""
                    Escolha o número de sua opção:
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma

                    0 - Sair
                    """);
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> searchBookByTitle();
                case 2 -> listAllBooks();
                case 3 -> listAllAuthors();
                case 4 -> listLivingAuthors();
                case 5 -> listBooksByLanguage();
            }
        } while (opcao != 0);
    }

    private void searchBookByTitle() {
        System.out.println("Digite o nome do livro: ");
        String nome = scanner.nextLine();

        try {
            String tituloCodificado = URLEncoder.encode(nome, StandardCharsets.UTF_8).replace("+", "%20");
            String url = baseUrl + tituloCodificado;

            String json = client.getDataGutendex(url);
            LivroResponseDTO response = converter.getData(json, LivroResponseDTO.class);

            Livro livro = new Livro(response);
            printBookDetails(livro);
            repository.save(livro);

        } catch (Exception e) {
            System.out.println("Erro ao buscar livro. Verifique sua conexão ou tente outro título.");
        }
    }

    private void listAllBooks() {
        List<Livro> livros = repository.findAll();
        livros.forEach(this::printBookDetails);
    }

    private void listAllAuthors() {
        List<Autor> autores = autorRepository.findAll();
        for (Autor autor : autores) {
            System.out.println("--------------------------------- AUTORES ----------------------------------");
            System.out.println("Autor: " + autor.getNome());
            System.out.println("Ano de Nascimento: " + autor.getAnoNascimento());
            System.out.println("Ano de Falecimento: " + autor.getAnoFalecimento());
            for (Livro livro : autor.getLivros()) {
                System.out.println("Livros: " + livro.getTitulo());
            }
            System.out.println();
        }
    }

    private void listLivingAuthors() {
        System.out.println("Insira o ano que deseja pesquisar: ");
        Integer year = scanner.nextInt();
        scanner.nextLine();
        List<Autor> autorAlive = autorRepository.findAuthorsAliveInYearWithBooks(year);
        for (Autor autor : autorAlive) {
            System.out.println("Autor: " + autor.getNome());
            System.out.println("Ano de Nascimento: " + autor.getAnoNascimento());
            System.out.println("Ano de Falecimento: " + autor.getAnoFalecimento());
            for (Livro livro : autor.getLivros()) {
                System.out.println("Livros: " + livro.getTitulo());
            }
            System.out.println("--------------------------------------------------------------------------");
        }
    }

    private void listBooksByLanguage() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                es - Espanhol
                en - Inglês
                fr - Francês
                pt - Português
                """);

        String idioma = scanner.nextLine().trim().toLowerCase();
        List<Livro> livrosPorIdioma = repository.findByIdioma(idioma);

        if (livrosPorIdioma.isEmpty()) {
            System.out.println("❌ Nenhum livro encontrado para o idioma informado.");
        } else {
            System.out.println("📚 Livros no idioma '" + idioma + "':");
            for (Livro livro : livrosPorIdioma) {
                System.out.println("- " + livro.getTitulo());
            }
        }
    }

    private void printBookDetails(Livro livro) {
        System.out.println("--------------------------------- LIVRO ----------------------------------");
        System.out.println("Título: " + livro.getTitulo());
        System.out.println("Autores: " + livro.getAutores().stream().map(Autor::getNome).toList());
        System.out.println("Idiomas: " + livro.getIdiomas().stream().map(Idioma::getNome).toList());
        System.out.println("Número de downloads: " + livro.getNumeroDownloads());
        System.out.println("--------------------------------------------------------------------------");
    }
}
