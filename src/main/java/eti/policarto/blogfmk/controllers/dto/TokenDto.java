package eti.policarto.blogfmk.controllers.dto;

import lombok.Getter;

@Getter
public class TokenDto {

    private final String token;
    private final String tipo;

    public TokenDto(String token, String tipo) {
        this.token = token;
        this.tipo = tipo;
    }
}