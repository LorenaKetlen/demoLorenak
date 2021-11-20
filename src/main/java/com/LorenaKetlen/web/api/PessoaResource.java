package com.LorenaKetlen.demoLK.web.api;

import com.LorenaKetlen.demoLK.domain.Pessoa;
import com.LorenaKetlen.demoLK.service.PessoaService;
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
@RequestMapping("/pessoas")
public class PessoaResource {
    private final Logger log = LoggerFactory.getLogger(PessoaResource.class);

    private final PessoaService pessoaService;

    public PessoaResource(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    /**
     * {@code GET  /pessoas/:id} : get the "id" pessoa.
     *
     * @param id o id do pessoa que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o
     *         pessoa, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        log.debug("REST request to get Pessoa : {}", id);
        Optional<Pessoa> pessoa = pessoaService.findOne(id);
        if (pessoa.isPresent()) {
            return ResponseEntity.ok().body(pessoa.get());
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/")
    public ResponseEntity<List<Pessoa>> getPessoas() {
        List<Pessoa> lista = pessoaService.findAllList();
        if (lista.size() > 0) {
            return ResponseEntity.ok().body(lista);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * {@code PUT  /pessoas} : Atualiza um pessoa existenteUpdate.
     *
     * @param pessoa o pessoa a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o
     *         pessoa atualizado, ou com status {@code 400 (Bad Request)} se o
     *         pessoa não é válido, ou com status
     *         {@code 500 (Internal Server Error)} se o pessoa não pode ser
     *         atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Pessoa> updatePessoa(@RequestBody Pessoa pessoa) throws URISyntaxException {
        log.debug("REST request to update Pessoa : {}", pessoa);
        if (pessoa.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Pessoa id null");
        }
        Pessoa result = pessoaService.save(pessoa);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code POST  /} : Create a new pessoa.
     *
     * @param pessoa the pessoa to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
     *         body the new pessoa, or with status {@code 400 (Bad Request)} if the
     *         pessoa has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) throws URISyntaxException {
        log.debug("REST request to save Pessoa : {}", pessoa);
        if (pessoa.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Um novo pessoa não pode terum ID");
        }
        Pessoa result = pessoaService.save(pessoa);
        return ResponseEntity.created(new URI("/api/pessoas/" + result.getId())).body(result);
    }

    @PostMapping(value = "/csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<Pessoa> upload(@RequestPart("data") MultipartFile csv) throws IOException {
        List<Pessoa> savedNotes = new ArrayList<>();
        List<Pessoa> notes = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(csv).getInputStream(), StandardCharsets.UTF_8)).lines()
                        .map(Pessoa::parseNote).collect(Collectors.toList());
        pessoaService.saveAll(notes).forEach(savedNotes::add);
        return savedNotes;
    }

    /**
     * {@code DELETE  /:id} : delete pelo "id" pessoa.
     *
     * @param id o id do pessoas que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        log.debug("REST request to delete Pessoa : {}", id);

        pessoaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
