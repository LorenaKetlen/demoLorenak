package com.LorenaKetlen.demoLK.web.api;

import com.LorenaKetlen.demoLK.domain.Servico;
import com.LorenaKetlen.demoLK.service.ServicoService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Api(tags = "Recursos de Teste para servico")
public class ServicoTesteResource {
    private final Logger log = LoggerFactory.getLogger(ServicoTesteResource.class);

    private final ServicoService servicoService;

    public ServicoTesteResource(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @GetMapping(path = "/servicos/criar/{name}")
    public String helloApp(@PathVariable String name) {
        Servico p = new Servico();
        p.setNome(name);
        servicoService.save(p);
        return "criou";
    }

    @GetMapping(path = "/servicos/listar/{id}")
    public Servico helloApp(@PathVariable Long id) {
        return servicoService.findOne(id).get();
    }

}
