package eti.policarto.blogfmk.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter @EqualsAndHashCode
@Entity
public class Post {

	@Id @GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String texto;

	@OneToMany(cascade = CascadeType.REFRESH)
	private List<Imagem> imagens;

	@OneToMany(cascade = CascadeType.REFRESH)
	private List<Link> links;

	@ManyToOne(cascade = CascadeType.REMOVE)
	private Usuario usuario;

	@OneToMany(cascade = CascadeType.REFRESH)
	private List<Comentario> comentarios;

	public Post() {
	}

	public Post(String titulo, String texto) {
		this.titulo = titulo;
		this.texto = texto;
	}
}
