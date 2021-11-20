package com.LorenaKetlen.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_Servico")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(descricao = "descricao", length = 64)
    private String descricao;

    private String NumFuncionarios;
    private String duracao;
    private String preco;
    private Boolean isActive;

    public static Servico parseNote(String line) {
        String[] text = line.split(",");
        Servico note = new Servico();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
