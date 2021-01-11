package eti.policarto.blogfmk.configurations.security;

import eti.policarto.blogfmk.models.Usuario;
import eti.policarto.blogfmk.repository.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class TokenFilter extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final UsuarioRepository usuarioRepository;

    public TokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
    	this.tokenService = tokenService;
    	this.usuarioRepository = usuarioRepository;
    }

    @Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws ServletException, IOException {

		String token = extrairToken(req);

		if(tokenService.ehValido(token))
			autenticarUsuario(token);
		
		chain.doFilter(req, resp);
	}

	private void autenticarUsuario(String token) {
		Long idUsuarioLogado = tokenService.getIdUsuarioLogado(token);
		Optional<Usuario> usuOpt = usuarioRepository.findById(idUsuarioLogado);
		if(usuOpt.isPresent()){
			Usuario usuario = usuOpt.get();
			UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(
				usuario.getEmail(),
				usuario.getSenha(),
				usuario.getAuthorities()
			);
			SecurityContextHolder.getContext().setAuthentication(userAuth);
		}
	}

	private String extrairToken(HttpServletRequest req) {
		String authorization = req.getHeader("Authorization");
		if(authorization == null || authorization.isEmpty() || !authorization.startsWith("Bearer ") )
			return null;
		return authorization.replace("Bearer ", "");
	}
}
