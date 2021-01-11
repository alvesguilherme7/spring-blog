package eti.policarto.blogfmk.controllers.form;

import eti.policarto.blogfmk.models.Link;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class LinkForm {
    private String descricao;
    private String valor;

    public LinkForm() {
    }

    public LinkForm(Link link) {
        this.descricao = link.getDescricao();
        this.valor = link.getValor();
    }

    public Link converter(){
        Link link = new Link();
        link.setDescricao(this.descricao);
        link.setValor(this.valor);
        return link;
    }

    public static List<Link> converter(List<LinkForm> linksForm) {
        return linksForm.stream()
            .map(LinkForm::converter)
            .collect(Collectors.toList());
    }
}
