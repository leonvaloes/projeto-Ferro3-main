package br.unoeste.jinwoo.ativooperante2024.services;

import br.unoeste.jinwoo.ativooperante2024.db.entities.Denuncia;
import br.unoeste.jinwoo.ativooperante2024.db.entities.FeedBack;
import br.unoeste.jinwoo.ativooperante2024.db.respositories.DenunciaRepository;
import br.unoeste.jinwoo.ativooperante2024.db.respositories.FeedbackRepository;

import br.unoeste.jinwoo.ativooperante2024.dto.DenunciaRespostaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Service
public class DenunciaService {
    @Autowired
    private DenunciaRepository repo;
    @Autowired
    private FeedbackRepository frepo;

    public Denuncia save(Denuncia denuncia) {
        return repo.save(denuncia);
    }

    public DenunciaRespostaDTO getById(Long id) throws IOException {
        Optional<Denuncia> optionalDenuncia = repo.findById(id);
        if (optionalDenuncia.isPresent()) {
            Denuncia denuncia = optionalDenuncia.get();
            DenunciaRespostaDTO responseDTO = new DenunciaRespostaDTO();
            responseDTO.setId(denuncia.getId());
            responseDTO.setTitulo(denuncia.getTitulo());
            responseDTO.setTexto(denuncia.getTexto());
            responseDTO.setUrgencia(denuncia.getUrgencia());
            responseDTO.setData(denuncia.getData());
            responseDTO.setOrgaoNome(denuncia.getOrgao().getNome());
            responseDTO.setTipoNome(denuncia.getTipo().getNome());
            responseDTO.setUsuarioNome(denuncia.getUsuario().getEmail());

            // Converter imagem para Base64
            if (denuncia.getCaminhoImagem() != null) {
                File imageFile = new File(denuncia.getCaminhoImagem());
                if (imageFile.exists()) {
                    byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
                    String imageBase64 = Base64Utils.encodeToString(imageBytes);
                    responseDTO.setImagemBase64(imageBase64);
                }
            }

            return responseDTO;
        } else {
            throw new RuntimeException("Denúncia não encontrada");
        }
    }

    public List<Denuncia> getAll() {
        return repo.findAll();
    }

    public List<Denuncia> findAllByUsuario(Long id) {
        return repo.findAllByUsuarioId(id);
    }

    public Boolean delete(Long id) {
        try {
            repo.deleteById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public FeedBack getByIdFeedback(Long id) {
        return frepo.findById(id).orElse(null);
    }

    public FeedBack save(FeedBack feedBack) {
        return frepo.save(feedBack);
    }

    // Método para buscar denúncia com feedback
    public Denuncia getDenunciaWithFeedback(Long id) {
        Denuncia denuncia = repo.findById(id).orElse(null);
        if (denuncia != null) {
            FeedBack feedback = frepo.findByDenuncia(denuncia);
            denuncia.setFeedBack(feedback);
        }
        return denuncia;
    }
}
