package br.unoeste.jinwoo.ativooperante2024.db.respositories;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Orgao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgaoRepository extends JpaRepository<Orgao,Long> {
    Orgao findByNome(String nome);
}
