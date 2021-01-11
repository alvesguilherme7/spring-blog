package eti.policarto.blogfmk.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter @EqualsAndHashCode
@Entity
public class Comentario {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String texto;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Usuario usuario;
}
