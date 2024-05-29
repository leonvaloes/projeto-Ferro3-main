package br.unoeste.jinwoo.ativooperante2024.db.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="usu_id")
    private Long id;
    @Column(name="usu_cpf")
    private String cpf;
    @Column(name="usu_email")
    private String email;
    @Column(name="usu_senha")
    private int senha;
    @Column(name="usu_nivel")
    private int nivel;
}
