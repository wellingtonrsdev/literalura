package com.alura.literalura.repositories;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {

    @Query("SELECT l FROM Livro l JOIN l.idiomas i WHERE LOWER(i.nome) = LOWER(:idioma)")
    List<Livro> findByIdioma(@Param("idioma") String idioma);

}
