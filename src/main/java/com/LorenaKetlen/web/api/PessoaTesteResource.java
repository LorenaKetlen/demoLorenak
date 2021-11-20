package com.LorenaKetlen.demoLK.web.api;

import com.LorenaKetlen.demoLK.domain.Pessoa;
import com.LorenaKetlen.demoLK.service.PessoaService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Api(tags = "Recursos de Teste para pessoa")
public class PessoaTesteResource {
    private final Logger log = LoggerFactory.getLogger(PessoaTesteResource.class);

    private final PessoaService pessoaService;

    public PessoaTesteResource(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping(path = "/pessoas/criar/{name}")
    public String helloApp(@PathVariable String name) {
        Pessoa p = new Pessoa();
        p.setNome(name);
        pessoaService.save(p);
        return "criou";
    }

    @GetMapping(path = "/pessoas/listar/{id}")
    public Pessoa helloApp(@PathVariable Long id) {
        return pessoaService.findOne(id).get();
    }

}
