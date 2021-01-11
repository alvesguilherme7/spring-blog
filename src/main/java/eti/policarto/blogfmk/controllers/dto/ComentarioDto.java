package eti.policarto.blogfmk.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import eti.policarto.blogfmk.models.Comentario;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ComentarioDto {

    private final Long id;
    private final String texto;
    @JsonProperty("usuario")
    private final UsuarioDto usuarioDto;

    public ComentarioDto(Comentario comentario) {
        this.id = comentario.getId();
        this.texto = comentario.getTexto();
        this.usuarioDto = new UsuarioDto(comentario.getUsuario());
    }

    public static List<ComentarioDto> converter(List<Comentario> comentarios) {
        return comentarios
            .stream()
            .map(ComentarioDto::new)
            .collect(Collectors.toList());
    }

}
