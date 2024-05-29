package br.unoeste.jinwoo.ativooperante2024.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DenunciaComImagemDTO {
    private Long id;
    private String titulo;
    private String texto;
    private int urgencia;
    private LocalDate data;
    private String caminhoImagem;
    private String orgaoNome;
    private String tipoNome;
    private String usuarioNome;
    private MultipartFile imagem;  // Alterado para MultipartFile
}
