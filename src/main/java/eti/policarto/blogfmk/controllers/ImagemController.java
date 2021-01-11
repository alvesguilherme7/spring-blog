package eti.policarto.blogfmk.controllers;

import eti.policarto.blogfmk.controllers.dto.ErroDto;
import eti.policarto.blogfmk.models.Imagem;
import eti.policarto.blogfmk.models.Post;
import eti.policarto.blogfmk.repository.ImagemRepository;
import eti.policarto.blogfmk.repository.PostRepository;
import eti.policarto.blogfmk.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ImagemController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImagemRepository imagemRepository;

    private final StorageService storageService;

    @Autowired
    public ImagemController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping(value = "/post/{id}/imagem")
    public ResponseEntity<?> upload(@PathVariable Long id, @RequestParam(name = "imagem") @Valid MultipartFile file){

        Optional<Post> postOpt = postRepository.findById(id);
        if(postOpt.isPresent()){

            Post post = postOpt.get();

            storageService.store(file);

            Imagem imagem = imagemRepository.save(new Imagem(file.getOriginalFilename()));

            postRepository.addImagem(post.getId(), imagem.getId());

            return ResponseEntity.ok(imagem);

        }else{
            return ResponseEntity.badRequest().body(new ErroDto("Post não existe."));
        }
    }

    @GetMapping(value = "/post/{id}/imagem", name = "Listar Imagens do Post")
    @ResponseBody
    public ResponseEntity<?> listar(@PathVariable Long id) {

        Optional<Post> postOpt = postRepository.findById(id);
        if(postOpt.isPresent()){
            List<Imagem> allImagens = postRepository.findAllImagens(id);
            return ResponseEntity.ok().body(allImagens);
        }else{
            return ResponseEntity.badRequest().body(new ErroDto("Post não existe."));
        }

    }

    @GetMapping("/post/{id}/imagem/{id_img}")
    @ResponseBody
    public ResponseEntity<?> serveFile(@PathVariable Long id, @PathVariable Long id_img) {
        Optional<Imagem> imgOpt = imagemRepository.findById(id_img);
        if(imgOpt.isPresent()){
            Imagem imagem = imgOpt.get();
            Resource file = storageService.loadAsResource(imagem.getFilename());
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(file);
        }else{
            return ResponseEntity.badRequest().body(new ErroDto("Imagem não existe."));
        }

    }

}
