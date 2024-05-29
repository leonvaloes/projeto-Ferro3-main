package br.unoeste.jinwoo.ativooperante2024.services;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Orgao;
import br.unoeste.jinwoo.ativooperante2024.db.respositories.OrgaoRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgaoService {
    @Autowired
    private OrgaoRepository repo;
     public Orgao save(Orgao orgao)
    {
      return repo.save(orgao);
    }
    public Orgao getById(Long Id)
    {
        return repo.findById(Id).get();
    }
    public Orgao getByNome(String nome){ return repo.findByNome(nome);}
    public List<Orgao> getAll()
    {
        return repo.findAll();
    }
    public boolean delete(Long id)
    {
     try
     {
         repo.deleteById(id);
     }
     catch (Exception e){
          return false;
     }
        return true;
    }
}
