package br.unoeste.jinwoo.ativooperante2024.services;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Tipo;
import br.unoeste.jinwoo.ativooperante2024.db.respositories.TipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class TipoService {
    @Autowired
    private TipoRepository repo;

    public Tipo save(Tipo tipo)
    {
        return repo.save(tipo);
    }
    public Tipo getById(Long id)
    {
        return repo.findById(id).get();
    }
    public Tipo getByNome(String Nome){return repo.findByNome(Nome);}
    public List<Tipo> findAll()
    {
        return repo.findAll();
    }
    public boolean delete(Long id)
    {
        try
        {
            repo.deleteById(id);
        }
        catch (Exception e)
        {
          return false;
        }
        return true;
    }
}
