package com.LorenaKetlen.demoLK.service;

import com.LorenaKetlen.demoLK.domain.Pessoa;
import com.LorenaKetlen.demoLK.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    private final Logger log = LoggerFactory.getLogger(PessoaService.class);

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    public List<Pessoa> findAllList() {
        log.debug("Request to get All Pessoa");
        return pessoaRepository.findAll();
    }

    public Optional<Pessoa> findOne(Long id) {
        log.debug("Request to get Pessoa : {}", id);
        return pessoaRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Pessoa : {}", id);
        pessoaRepository.deleteById(id);
    }

    public Pessoa save(Pessoa pessoa) {
        log.debug("Request to save Pessoa : {}", pessoa);
        pessoa = pessoaRepository.save(pessoa);
        return pessoa;
    }

    public List<Pessoa> saveAll(List<Pessoa> pessoas) {
        log.debug("Request to save Pessoa : {}", pessoas);
        pessoas = pessoaRepository.saveAll(pessoas);
        return pessoas;
    }
