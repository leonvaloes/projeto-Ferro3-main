package br.unoeste.jinwoo.ativooperante2024.db.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "denuncia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "den_id")
    private Long id;

    @Column(name = "den_titulo", nullable = false)
    private String titulo;

    @Column(name = "den_texto", nullable = false)
    private String texto;

    @Column(name = "den_urgencia", nullable = false)
    private int urgencia;

    @Column(name = "den_data", nullable = false)
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "org_id", nullable = false)
    private Orgao orgao;

    @ManyToOne
    @JoinColumn(name = "tip_id", nullable = false)
    private Tipo tipo;

    @ManyToOne
    @JoinColumn(name = "usu_id", nullable = false)
    private Usuario usuario;

    @OneToOne(mappedBy = "denuncia", cascade = CascadeType.ALL)
    private FeedBack feedBack;

    @Column(name = "den_caminho_imagem")
    private String caminhoImagem;
}
