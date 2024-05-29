package br.unoeste.jinwoo.ativooperante2024.db.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tipo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="tip_id")
    private Long id;
    @Column(name="tip_nome")
    private String nome;
}
