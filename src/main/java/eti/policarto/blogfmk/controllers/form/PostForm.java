package eti.policarto.blogfmk.controllers.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import eti.policarto.blogfmk.models.Imagem;
import eti.policarto.blogfmk.models.Link;
import eti.policarto.blogfmk.models.Post;
import eti.policarto.blogfmk.models.Usuario;
import eti.policarto.blogfmk.repository.UsuarioRepository;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter @Setter
public class PostForm {

    @NotNull @NotEmpty
    private String titulo;
    @NotNull @NotEmpty
    private String texto;
    @NotNull @JsonProperty("id_usuario")
    private Long usuario;
    @JsonProperty("links")
    private List<LinkForm> linkForm;

    public PostForm() {
    }

    public PostForm(Post post) {
        this.titulo = post.getTitulo();
        this.texto = post.getTexto();
        this.usuario = post.getUsuario().getId();
    }

    public Post converter(UsuarioRepository usuarioRepository, List<Link> links) throws Exception {
        Optional<Usuario> optUsuario = usuarioRepository.findById(this.usuario);
        Usuario usuario = optUsuario.orElseThrow(() -> new Exception("Usuário não encontrado."));
        Post post = new Post(this.titulo, this.texto);
        post.setUsuario(usuario);
        post.setLinks(links);
        return post;
    }

    public Post atualizar(Long id) {
        Post post = new Post();
        post.setId(id);
        post.setTitulo(this.titulo);
        post.setTexto(this.texto);
        return post;
    }

}
