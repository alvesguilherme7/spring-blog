package eti.policarto.blogfmk.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @EqualsAndHashCode
@Entity
public class Imagem {

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	private String filename;

	public Imagem() {
	}

	public Imagem(String filename) {
		this.filename = filename;
	}
}
