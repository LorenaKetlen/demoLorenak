package com.LorenaKetlen.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "table_Pessoa")
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 64)
    private String nome;

    private Instant dataNascimento;
    private String cpf;
    private String telefone;
    private String cep;
    private String rua;
    private String bairro;
    private String cidade;
    private String uf;
    private Boolean isActive;

    public static Pessoa parseNote(String line) {
        String[] text = line.split(",");
        Pessoa note = new Pessoa();
        note.setId(Long.parseLong(text[0]));
        note.setNome(text[1]);
        return note;
    }
