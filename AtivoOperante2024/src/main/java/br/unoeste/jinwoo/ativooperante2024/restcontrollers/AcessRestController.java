package br.unoeste.jinwoo.ativooperante2024.restcontrollers;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Usuario;
import br.unoeste.jinwoo.ativooperante2024.security.JWTTokenProvider;
import br.unoeste.jinwoo.ativooperante2024.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="apis/security")
public class AcessRestController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("logar")
    public ResponseEntity<Object> logar(@RequestBody Usuario usuario) {
        String token = "não autenticado";
        // Acesso ao banco de dados para verificar existencia do usuario e comparar a senha
        if (usuarioService.authenticate(usuario.getEmail(), usuario.getSenha())) {
            Usuario foundUser = usuarioService.findByEmail(usuario.getEmail());
            token = JWTTokenProvider.getToken(usuario.getEmail(), "" + foundUser.getNivel());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().body(token);
    }
}