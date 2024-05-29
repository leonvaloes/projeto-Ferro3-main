package br.unoeste.jinwoo.ativooperante2024.services;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Usuario;
import br.unoeste.jinwoo.ativooperante2024.db.respositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository repo;
    public Usuario save(Usuario usuario)
    {
        return  repo.save(usuario);
    }
    public Usuario getById(Long id)
    {
        return repo.findById(id).get();
    }
    public List<Usuario> findAll()
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
    public Usuario findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public boolean authenticate(String email, int senha) {
        Usuario usuarioOpt = repo.findByEmail(email);
        return usuarioOpt!=null && usuarioOpt.getSenha() == senha;
    }
}
