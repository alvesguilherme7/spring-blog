package eti.policarto.blogfmk.controllers.form;

import eti.policarto.blogfmk.models.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class UsuarioForm {

    @NotNull @NotEmpty
    private String nome;
    @NotNull @NotEmpty
    private String email;
    @NotNull @NotEmpty
    private String senha;

    public Usuario converter() {
        return new Usuario(this.nome, this.email, this.senha);
    }

    public Usuario atualizar(Long id) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome(this.nome);
        usuario.setEmail(this.email);
        usuario.setSenha(this.senha);
        return usuario;
    }
}
