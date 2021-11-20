package com.LorenaKetlen.demoLK.service;

import com.LorenaKetlen.demoLK.domain.Servico;
import com.LorenaKetlen.demoLK.repository.ServicoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServicoService {

    private final Logger log = LoggerFactory.getLogger(ServicoService.class);

    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public List<Servico> findAllList() {
        log.debug("Request to get All Servico");
        return servicoRepository.findAll();
    }

    public Optional<Servico> findOne(Long id) {
        log.debug("Request to get Servico : {}", id);
        return servicoRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Servico : {}", id);
        servicoRepository.deleteById(id);
    }

    public Servico save(Servico servico) {
        log.debug("Request to save Servico : {}", Servico);
        Servico = ServicoRepository.save(Servico);
        return Servico;
    }

    public List<Servico> saveAll(List<Servico> servicos) {
        log.debug("Request to save Servico : {}", servicos);
        servicos = servicoRepository.saveAll(servicos);
        return servicos;
    }
