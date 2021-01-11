package eti.policarto.blogfmk.controllers;

import eti.policarto.blogfmk.controllers.dto.ErroDto;
import eti.policarto.blogfmk.controllers.dto.UsuarioDto;
import eti.policarto.blogfmk.controllers.form.UsuarioForm;
import eti.policarto.blogfmk.models.Usuario;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping(name = "Listar Usuários")
    public ResponseEntity<List<UsuarioDto>> listar(){
        List<Usuario> usuarios = usuarioRepository.findAll();
        return ResponseEntity.ok(UsuarioDto.converter(usuarios));
    }

    @PostMapping(name = "Criar Usuário")
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid UsuarioForm usuarioForm, UriComponentsBuilder uriBuilder){
        Usuario usuario = usuarioForm.converter();
        boolean emailJaExiste = usuarioRepository.findByEmail(usuario.getEmail()).isPresent();
        if(!emailJaExiste){
            usuario = usuarioRepository.saveAndFlush(usuario);
            URI uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.getId()).toUri();
            return ResponseEntity.created(uri).body(new UsuarioDto(usuario));
        }else{
            return ResponseEntity.badRequest().body(new ErroDto("Email já existe."));
        }
    }

    @GetMapping(path = "/{id}", name = "Buscar Usuário")
    public ResponseEntity<UsuarioDto> buscar(@PathVariable Long id){
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario
            .map(value -> ResponseEntity.ok(new UsuarioDto(value)) )
            .orElseGet( () -> ResponseEntity.notFound().build() );
    }

    @PutMapping(path = "/{id}", name = "Atualizar Usuário")
    @Transactional
    public ResponseEntity<UsuarioDto> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioForm usuarioForm){
        boolean usuExiste = usuarioRepository.findById(id).isPresent();
        if(usuExiste){
            Usuario usuarioAlterado = usuarioForm.atualizar(id);
            Usuario usuario = usuarioRepository.saveAndFlush(usuarioAlterado);
            return ResponseEntity.ok(new UsuarioDto(usuario));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping(path = "/{id}", name = "Excluir Usuário")
    @Transactional
    public ResponseEntity<UsuarioDto> excluir(@PathVariable Long id){
        boolean usuExiste = usuarioRepository.findById(id).isPresent();
        if(usuExiste){
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
