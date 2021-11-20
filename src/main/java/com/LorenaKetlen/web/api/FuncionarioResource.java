package com.LorenaKetlen.demoLK.web.api;

import com.LorenaKetlen.demoLK.domain.Funcionario;
import com.LorenaKetlen.demoLK.service.FuncionarioService;
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
@RequestMapping("/funcionarios")
public class FuncionarioResource {
    private final Logger log = LoggerFactory.getLogger(FuncionarioResource.class);

    private final FuncionarioService funcionarioService;

    public FuncionarioResource(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Funcionario> getFuncionario(@PathVariable Long id) {
        log.debug("REST request to get Funcionario : {}", id);
        Optional<Funcionario> funcionario = funcionarioService.findOne(id);
        if (funcionario.isPresent()) {
            return ResponseEntity.ok().body(funcionario.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Funcionario>> getFuncionarios() {
        List<Funcionario> lista = funcionarioService.findAllList();
        if (lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/")
    public ResponseEntity<Funcionario> updateFuncionario(@RequestBody Funcionario funcionario)
            throws URISyntaxException {
        log.debug("REST request to update Funcionario : {}", funcionario);
        if (funcionario.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Funcionario id null");
        }
        Funcionario result = funcionarioService.save(funcionario);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/")
    public ResponseEntity<Funcionario> createFuncionario(@RequestBody Funcionario funcionario)
            throws URISyntaxException {
        log.debug("REST request to save Funcionario : {}", funcionario);
        if (funcionario.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um novo funcionario não pode terum ID");
        }
        Funcionario result = funcionarioService.save(funcionario);
        return ResponseEntity.created(new URI("/api/funcionarios/" + result.getId())).body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Funcionario> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Funcionario> savedNotes = new ArrayList<>();
        List<Funcionario> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                        .map(Funcionario::parseNote).collect(Collectors.toList());
        funcionarioService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuncionario(@PathVariable Long id) {
        log.debug("REST request to delete Funcionario : {}", id);

        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
