package eti.policarto.blogfmk.repository;

import eti.policarto.blogfmk.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

}
