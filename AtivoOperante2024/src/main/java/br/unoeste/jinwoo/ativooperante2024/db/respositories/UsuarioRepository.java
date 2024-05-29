package br.unoeste.jinwoo.ativooperante2024.db.respositories;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    Usuario findByEmail(String email);
}
