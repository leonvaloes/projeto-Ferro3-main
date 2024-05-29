package br.unoeste.jinwoo.ativooperante2024.db.respositories;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Denuncia;
import org.springframework.data.jpa.repository.JpaRepository;

import br.unoeste.jinwoo.ativooperante2024.db.entities.FeedBack;


public interface FeedbackRepository extends JpaRepository<FeedBack,Long>{
    FeedBack findByDenuncia(Denuncia denuncia);
}
