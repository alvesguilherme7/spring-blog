package eti.policarto.blogfmk.controllers;

import eti.policarto.blogfmk.commons.Utils;
import eti.policarto.blogfmk.configurations.security.AutenticacaoService;
import eti.policarto.blogfmk.controllers.dto.ErroDto;
import eti.policarto.blogfmk.controllers.dto.PostDto;
import eti.policarto.blogfmk.controllers.form.LinkForm;
import eti.policarto.blogfmk.controllers.form.PostForm;
import eti.policarto.blogfmk.models.Link;
import eti.policarto.blogfmk.models.Post;
import eti.policarto.blogfmk.models.Usuario;
import eti.policarto.blogfmk.repository.LinkRepository;
import eti.policarto.blogfmk.repository.PostRepository;
import eti.policarto.blogfmk.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LinkRepository linkRepository;

    @GetMapping(name = "Listar Pots")
    public ResponseEntity<List<PostDto>> listar(){
        List<Post> posts = postRepository.findAll();
        return ResponseEntity.ok(PostDto.converter(posts));
    }

    @PostMapping(name = "Criar Post")
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid PostForm postForm, UriComponentsBuilder uriBuilder){
        try{

            List<Link> links = LinkForm.converter(postForm.getLinkForm());
            links.forEach( link -> link = linkRepository.saveAndFlush(link));

            Post post = postRepository.saveAndFlush(postForm.converter(usuarioRepository, links));
            URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(post.getId()).toUri();

            return ResponseEntity.created(uri).body(new PostDto(post));
        }catch (Exception e){
            return ResponseEntity.badRequest().body((new ErroDto(e.getMessage())));
        }

    }

    @GetMapping(path = "/{id}", name = "Buscar Post")
    public ResponseEntity<PostDto> buscar(@PathVariable Long id){
        Optional<Post> post = postRepository.findById(id);
        return post
            .map(value -> ResponseEntity.ok(new PostDto(value)) )
            .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PutMapping(path = "/{id}", name = "Atualizar Post")
    @Transactional
    public ResponseEntity<PostDto> atualizar(@PathVariable Long id, @RequestBody @Valid PostForm postForm){
        boolean usuExiste = postRepository.findById(id).isPresent();
        if(usuExiste){
            Post post = postRepository.saveAndFlush(postForm.atualizar(id));
            return ResponseEntity.ok(new PostDto(post));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}", name = "Excluir Usuário")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id){
        Optional<Post> postOpt = postRepository.findById(id);
        if(postOpt.isPresent()){
            Post post = postOpt.get();
            Usuario usuarioLogado = (Usuario) autenticacaoService.loadUserByUsername(Utils.getUsuarioRequisicao());
            if(usuarioLogado.equals(post.getUsuario())){
                postRepository.deleteById(id);
                return ResponseEntity.ok().build();
            }else{
                return ResponseEntity.badRequest().body(new ErroDto("Não é permitido excluir posts de outros usuários."));
            }

        }
        return ResponseEntity.notFound().build();
    }

}
