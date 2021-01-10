package eti.policarto.blogfmk.controllers.dto;

import lombok.Getter;

@Getter
public class ErroDto {

    private final String status = "erro";
    private final String mensagem;

    public ErroDto(String mensagem) {
        this.mensagem = mensagem;
    }
}
