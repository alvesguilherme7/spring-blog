package eti.policarto.blogfmk.controllers;

import eti.policarto.blogfmk.commons.Utils;
import eti.policarto.blogfmk.configurations.security.AutenticacaoService;
import eti.policarto.blogfmk.controllers.dto.ComentarioDto;
import eti.policarto.blogfmk.controllers.dto.ErroDto;
import eti.policarto.blogfmk.controllers.dto.PostDto;
import eti.policarto.blogfmk.controllers.form.ComentarioForm;
import eti.policarto.blogfmk.models.Comentario;
import eti.policarto.blogfmk.models.Post;
import eti.policarto.blogfmk.models.Usuario;
import eti.policarto.blogfmk.repository.ComentarioRepository;
import eti.policarto.blogfmk.repository.PostRepository;
import eti.policarto.blogfmk.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post/{idPost}/comentario")
public class ComentarioController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(name = "Listar Comentários")
    public ResponseEntity<List<ComentarioDto>> listar(){
        List<Comentario> comentarios = comentarioRepository.findAll();
        return ResponseEntity.ok(ComentarioDto.converter(comentarios));
    }

    @PostMapping(name = "Criar Comentário")
    @Transactional
    public ResponseEntity<?> criar(@PathVariable Long idPost, @RequestBody @Valid ComentarioForm comentarioForm, UriComponentsBuilder uriBuilder){
        try{

            Optional<Post> postOpt = postRepository.findById(idPost);

            if(postOpt.isPresent()){
                Post post = postOpt.get();

                Comentario comentario = comentarioRepository.saveAndFlush(comentarioForm.converter(usuarioRepository));
                post.getComentarios().add(comentario);

                postRepository.saveAndFlush(post);

                URI uri = uriBuilder
                        .path("/post/{idPost}}")
                        .buildAndExpand(idPost)
                        .toUri();

                return ResponseEntity.created(uri).body(new PostDto(post));

            }else{
                return ResponseEntity.badRequest().body((new ErroDto("Post não encontrado.")));
            }


        }catch (Exception e){
            return ResponseEntity.badRequest().body((new ErroDto(e.getMessage())));
        }

    }

    @GetMapping(path = "/{id}", name = "Buscar Comentário")
    public ResponseEntity<ComentarioDto> buscar(@PathVariable Long id){
        Optional<Comentario> comentario = comentarioRepository.findById(id);
        return comentario
            .map(value -> ResponseEntity.ok(new ComentarioDto(value)) )
            .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PutMapping(path = "/{id}", name = "Atualizar Comentário")
    @Transactional
    public ResponseEntity<ComentarioDto> atualizar(@PathVariable Long id, @RequestBody @Valid ComentarioForm ComentarioForm) throws Exception {
        boolean usuExiste = comentarioRepository.findById(id).isPresent();
        if(usuExiste){
            Comentario Comentario = comentarioRepository.saveAndFlush(ComentarioForm.atualizar(id, usuarioRepository));
            return ResponseEntity.ok(new ComentarioDto(Comentario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}", name = "Excluir Comentário")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id){
        Optional<Comentario> comentarioOpt = comentarioRepository.findById(id);
        if(comentarioOpt.isPresent()){
            Comentario comentario = comentarioOpt.get();
            Usuario usuarioLogado = (Usuario) autenticacaoService.loadUserByUsername(Utils.getUsuarioRequisicao());
            if(usuarioLogado.equals(comentario.getUsuario())){
                comentarioRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.badRequest().body(new ErroDto("Não é permitido excluir comentários de outros usuários."));
            }
        }
        return ResponseEntity.notFound().build();
    }

}
