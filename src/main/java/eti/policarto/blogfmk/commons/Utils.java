package eti.policarto.blogfmk.commons;

import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {

    public static String getUsuarioRequisicao(){
        return String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }
}
