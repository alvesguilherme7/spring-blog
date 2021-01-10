package eti.policarto.blogfmk.repository;

import eti.policarto.blogfmk.models.Imagem;
import eti.policarto.blogfmk.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByTitulo(String titulo);

    @Modifying
    @Query(value = "insert into post_imagens (post_id,imagens_id) VALUES (:post_id,:imagem_id)", nativeQuery = true)
    @Transactional
    void addImagem(@Param("post_id") Long post_id, @Param("imagem_id") Long imagem_id);

    @Query(value = " select p.imagens from Post p where p.id = :post_id ")
    List<Imagem> findAllImagens(@Param("post_id") Long post_id);
}
