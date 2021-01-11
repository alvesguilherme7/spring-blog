package eti.policarto.blogfmk.configurations.validation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErroFormDto {

    private final String nomeCampo;
    private final String mensagem;

    public ErroFormDto(String nomeCampo, String mensagem) {
        this.nomeCampo = nomeCampo;
        this.mensagem = mensagem;
    }
}
