package eti.policarto.blogfmk.controllers.dto;

import eti.policarto.blogfmk.models.Usuario;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UsuarioDto {

    private final Long id;
    private final String nome;
    private final String email;

    public UsuarioDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
    }

    public static List<UsuarioDto> converter(List<Usuario> asListUsuario) {
        return asListUsuario.stream().map(UsuarioDto::converter).collect(Collectors.toList());
    }

    public static UsuarioDto converter(Usuario usuario) {
        return new UsuarioDto(usuario);
    }


}
