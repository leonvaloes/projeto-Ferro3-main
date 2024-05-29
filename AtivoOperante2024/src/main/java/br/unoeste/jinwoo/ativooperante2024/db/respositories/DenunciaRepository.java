package br.unoeste.jinwoo.ativooperante2024.db.respositories;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Denuncia;
import br.unoeste.jinwoo.ativooperante2024.db.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DenunciaRepository extends JpaRepository<Denuncia,Long>  {
    List<Denuncia> findAllByUsuarioId(Long id);

}
