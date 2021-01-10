package eti.policarto.blogfmk.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @EqualsAndHashCode
@Entity
public class Link {

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private String valor;

	public Link() {
	}

	public Link(Long id, String descricao, String valor) {
		this.descricao = descricao;
		this.valor = valor;
	}
}
