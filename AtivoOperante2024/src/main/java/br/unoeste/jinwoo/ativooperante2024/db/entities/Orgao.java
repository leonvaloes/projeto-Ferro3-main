package br.unoeste.jinwoo.ativooperante2024.db.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="orgaos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orgao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="org_id")
    private Long id;
    @Column(name="org_nome")
    private String nome;
}
