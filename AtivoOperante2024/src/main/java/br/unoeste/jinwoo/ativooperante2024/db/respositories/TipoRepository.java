package br.unoeste.jinwoo.ativooperante2024.db.respositories;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoRepository extends JpaRepository<Tipo,Long> {
    Tipo findByNome(String nome);
}
