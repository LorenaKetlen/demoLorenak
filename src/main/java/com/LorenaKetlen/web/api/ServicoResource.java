package com.LorenaKetlen.demoLK.web.api;

import com.LorenaKetlen.demoLK.domain.Servico;
import com.LorenaKetlen.demoLK.service.ServicoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/servicos")
public class ServicoResource {
    private final Logger log = LoggerFactory.getLogger(ServicoResource.class);

    private final ServicoService servicoService;

    public ServicoResource(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servico> getServico(@PathVariable Long id) {
        log.debug("REST request to get Servico : {}", id);
        Optional<Servico> servico = servicoService.findOne(id);
        if (servico.isPresent()) {
            return ResponseEntity.ok().body(servico.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Servico>> getServicos() {
        List<Servico> lista = servicoService.findAllList();
        if (lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Servico> updateServico(@RequestBody Servico servico) throws URISyntaxException {
        log.debug("REST request to update Servico : {}", servico);
        if (servico.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Servico id null");
        }
        Servico result = servicoService.save(servico);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/")
    public ResponseEntity<Servico> createServico(@RequestBody Servico servico) throws URISyntaxException {
        log.debug("REST request to save Servico : {}", servico);
        if (servico.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um novo servico não pode terum ID");
        }
        Servico result = servicoService.save(servico);
        return ResponseEntity.created(new URI("/api/servicos/" + result.getId())).body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Servico> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Servico> savedNotes = new ArrayList<>();
        List<Servico> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                        .map(Servico::parseNote).collect(Collectors.toList());
        servicoService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServico(@PathVariable Long id) {
        log.debug("REST request to delete Servico : {}", id);

        servicoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
