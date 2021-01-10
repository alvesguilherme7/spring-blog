package eti.policarto.blogfmk.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import eti.policarto.blogfmk.models.*;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
public class PostDto {

    private final Long id;
    private final String titulo;
    private final String texto;
    private final List<Link> links;
    @JsonProperty("comentarios")
    private final List<ComentarioDto> comentariosDto;
    @JsonProperty("usuario")
    private UsuarioDto usuarioDto;
    private List<Imagem> imagens;

    public PostDto(Post post) {
        this.id = post.getId();
        this.titulo = post.getTitulo();
        this.texto = post.getTexto();
        this.imagens = post.getImagens();
        this.links = post.getLinks();
        this.comentariosDto = ComentarioDto.converter(post.getComentarios());

        Optional<Usuario> usuario = Optional.ofNullable(post.getUsuario());
        if(usuario.isPresent())
            this.usuarioDto = new UsuarioDto(post.getUsuario());

    }

    public static List<PostDto> converter(List<Post> asListPost) {
        return asListPost.stream().map(PostDto::converter).collect(Collectors.toList());
    }

    public static PostDto converter(Post post) {
        return new PostDto(post);
    }

}
