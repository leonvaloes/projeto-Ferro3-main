package br.unoeste.jinwoo.ativooperante2024.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DenunciaRespostaDTO {
    private Long id;
    private String titulo;
    private String texto;
    private int urgencia;
    private LocalDate data;
    private String orgaoNome;
    private String tipoNome;
    private String usuarioNome;
    private String imagemBase64;
}
