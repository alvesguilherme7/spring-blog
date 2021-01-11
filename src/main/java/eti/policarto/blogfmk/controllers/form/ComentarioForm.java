package eti.policarto.blogfmk.controllers.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import eti.policarto.blogfmk.models.Comentario;
import eti.policarto.blogfmk.models.Usuario;
import eti.policarto.blogfmk.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
public class ComentarioForm {

    private String texto;
    @JsonProperty("id_usuario")
    private Long usuario;

    public Comentario converter(UsuarioRepository usuarioRepository) throws Exception {
        Optional<Usuario> optUsuario = usuarioRepository.findById(this.usuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new Exception("Usuário não encontrado."));
        Comentario comentario = new Comentario();
        comentario.setTexto(this.texto);
        comentario.setUsuario(usuario);
        return comentario;
    }

    public Comentario atualizar(Long id, UsuarioRepository usuarioRepository) throws Exception {
        Comentario comentario = converter(usuarioRepository);
        comentario.setId(id);
        return comentario;
    }
}
