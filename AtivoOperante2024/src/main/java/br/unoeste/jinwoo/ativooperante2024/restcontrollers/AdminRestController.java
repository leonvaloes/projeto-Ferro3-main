package br.unoeste.jinwoo.ativooperante2024.restcontrollers;

import br.unoeste.jinwoo.ativooperante2024.db.entities.*;
import br.unoeste.jinwoo.ativooperante2024.dto.DenunciaComImagemDTO;
import br.unoeste.jinwoo.ativooperante2024.dto.DenunciaRespostaDTO;
import br.unoeste.jinwoo.ativooperante2024.services.*;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value="apis/adm/")
public class AdminRestController {

    @Autowired
    OrgaoService orgaoService;
    @Autowired
    DenunciaService denunciaService;
    @Autowired
    TipoService tipoService;
    @Autowired
    UsuarioService usuarioService;
    @GetMapping(value = "teste-conexao")
    public String testeConexao()
    {
        return "conectado";
    }

    @DeleteMapping("/delete-orgao")
    public ResponseEntity<Object> excluirOrgao(@RequestParam(value="id") Long id)
    {
        if(orgaoService.delete(id))
            return new ResponseEntity<>("",HttpStatus.OK);
        else
            return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-orgao")
    public ResponseEntity<Object> salvarOrgao (@RequestBody Orgao orgao)
    {
        Orgao novo;
        novo=orgaoService.save(orgao);
        return new ResponseEntity<>(novo, HttpStatus.OK);
    }

    @PutMapping("/update-orgao/{id}")
    public ResponseEntity<Object> alterarOrgao(@PathVariable Long id, @RequestBody Orgao orgao) {
        Orgao orgaoOptional = orgaoService.getById(id);

        if (orgaoOptional == null) {
            return ResponseEntity.notFound().build();
        }

        orgaoOptional.setNome(orgao.getNome());

        orgaoService.save(orgaoOptional);

        return ResponseEntity.ok(orgaoOptional);
    }

    @GetMapping("/get-orgao")
    public ResponseEntity<Object> buscarUmOrgao(@RequestParam(value="id") Long id)
    {
        Orgao orgao=orgaoService.getById(id);
        if(orgao==null)
            orgao=new Orgao();
        return new ResponseEntity<>(orgao,HttpStatus.OK);
    }

    @GetMapping("/get-all-orgaos")
    public ResponseEntity<Object> buscarTodosOrgaos()
    {
        return new ResponseEntity<>(orgaoService.getAll(),HttpStatus.OK);
    }


    @DeleteMapping("/delete-denuncia")
    public ResponseEntity<Object> excluirDenuncia(@RequestParam(value="id") Long id) {
        if (denunciaService.delete(id))
            return new ResponseEntity<>("", HttpStatus.OK);
        else
            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/savefile", method = RequestMethod.POST)
    public ModelAndView upload(@ModelAttribute DenunciaComImagemDTO denunciaRequestDTO, HttpSession session) {
        String path = session.getServletContext().getRealPath("/") + "images/";
        MultipartFile file = denunciaRequestDTO.getImagem();
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        try {
            // Verifica se o diretório existe e, se não, cria
            File directory = new File(path);
            if (!directory.exists()) {
                FileUtils.forceMkdir(directory);
            }

            // Cria o arquivo e escreve os bytes nele
            byte[] barr = file.getBytes();
            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(path + "/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();
            Denuncia denuncia = new Denuncia();
            denuncia.setTitulo(denunciaRequestDTO.getTitulo());
            denuncia.setTexto(denunciaRequestDTO.getTexto());
            denuncia.setUrgencia(denunciaRequestDTO.getUrgencia());
            denuncia.setData(denunciaRequestDTO.getData());
            if(usuarioService.findByEmail(denunciaRequestDTO.getUsuarioNome())!=null)
                denuncia.setUsuario(usuarioService.findByEmail(denunciaRequestDTO.getUsuarioNome()));
            if(orgaoService.getByNome(denunciaRequestDTO.getOrgaoNome())!=null)
                denuncia.setOrgao(orgaoService.getByNome(denunciaRequestDTO.getOrgaoNome()));
            if(tipoService.getByNome(denunciaRequestDTO.getTipoNome())!=null)
                denuncia.setTipo(tipoService.getByNome(denunciaRequestDTO.getTipoNome()));
            denuncia.setCaminhoImagem("images/" + filename);
            denunciaService.save(denuncia);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("upload-success", "filename", "images/" + filename);
    }

    @GetMapping("/get-denuncia")
    public ResponseEntity<Object> buscarUmaDenuncia(@RequestParam(value="id") Long id) throws IOException {
        DenunciaRespostaDTO denuncia = denunciaService.getById(id);
        if (denuncia == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(denuncia, HttpStatus.OK);
    }

    @GetMapping("/get-all-denuncias")
    public ResponseEntity<Object> buscarTodasDenuncias() {
        return new ResponseEntity<>(denunciaService.getAll(), HttpStatus.OK);
    }

    @PostMapping("/add-feedback")
    public ResponseEntity<Object> salvarFeedback(@RequestBody FeedBack feedBack) {
        FeedBack novo = denunciaService.save(feedBack);
        return new ResponseEntity<>(novo, HttpStatus.OK);
    }

    @GetMapping("/get-feedback")
    public ResponseEntity<Object> buscarFeedback(@RequestParam(value = "id") Long id) {
        FeedBack feedB = denunciaService.getByIdFeedback(id);
        if (feedB != null)
            return new ResponseEntity<>(feedB, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/get-denuncia-with-feedback")
    public ResponseEntity<Object> buscarDenunciaComFeedback(@RequestParam(value = "id") Long id) {
        Denuncia denuncia = denunciaService.getDenunciaWithFeedback(id);
        if (denuncia != null)
            return new ResponseEntity<>(denuncia, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get-all-denuncias-usuario")
    public ResponseEntity<Object>Buscarporusuario(@RequestParam(value="id") Long id)
    {
        return new ResponseEntity<>(denunciaService.findAllByUsuario(id),HttpStatus.OK);
    }

    @DeleteMapping("/delete-Tipo")
    public ResponseEntity<Object> excluirTipo(@RequestParam(value="id") Long id)
    {
        if(tipoService.delete(id))
            return new ResponseEntity<>("",HttpStatus.OK);
        else
            return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-Tipo")
    public ResponseEntity<Object> salvarTipo (@RequestBody Tipo tipo)
    {
        Tipo novo=tipoService.save(tipo);
        return new ResponseEntity<>(novo, HttpStatus.OK);
    }

    @PutMapping("/update-tipo/{id}")
    public ResponseEntity<Object> alterarTipo (@PathVariable Long id, @RequestBody Tipo tipo) {
        Tipo tipoOptional = tipoService.getById(id);

        if (tipoOptional == null) {
            return ResponseEntity.notFound().build();
        }

        tipoOptional.setNome(tipo.getNome());

        tipoService.save(tipoOptional);

        return ResponseEntity.ok(tipoOptional);
    }

    @GetMapping("/get-Tipo")
    public ResponseEntity<Object> buscarUmTipo(@RequestParam(value="id") Long id)
    {
       Tipo tipo=tipoService.getById(id);
        if(tipo==null)
            tipo=new Tipo();
        return new ResponseEntity<>(tipo,HttpStatus.OK);
    }

    @GetMapping("/get-all-tipos")
    public ResponseEntity<Object> buscarTodosTipos()
    {
        return new ResponseEntity<>(tipoService.findAll(),HttpStatus.OK);
    }


    @GetMapping("/delete-Usuario")
    public ResponseEntity<Object> excluirUsuario(@RequestParam(value="id") Long id)
    {
        if(usuarioService.delete(id))
            return new ResponseEntity<>("",HttpStatus.OK);
        else
            return new ResponseEntity<>("",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/add-Usuario")
    public ResponseEntity<Object> salvarUsuario (@RequestBody Usuario usuario)
    {
        Usuario novo=usuarioService.save(usuario);
        return new ResponseEntity<>(novo, HttpStatus.OK);
    }

    @GetMapping("/get-Usuario")
    public ResponseEntity<Object> buscarUmUsuario(@RequestParam(value="id") Long id)
    {
        Usuario usuario=usuarioService.getById(id);
        if(usuario==null)
            usuario=new Usuario();
        return new ResponseEntity<>(usuario,HttpStatus.OK);
    }

    @GetMapping("/get-all-Usuarios")
    public ResponseEntity<Object> buscarTodosUsuarios()
    {
        return new ResponseEntity<>(usuarioService.findAll(),HttpStatus.OK);
    }

}
