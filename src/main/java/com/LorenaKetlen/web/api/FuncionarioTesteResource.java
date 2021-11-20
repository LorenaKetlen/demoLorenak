package com.LorenaKetlen.demoLK.web.api;

import com.LorenaKetlen.demoLK.domain.Funcionario;
import com.LorenaKetlen.demoLK.service.FuncionarioService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@Api(tags = "Recursos de Teste para funcionario")
public class FuncionarioTesteResource {
    private final Logger log = LoggerFactory.getLogger(FuncionarioTesteResource.class);

    private final FuncionarioService funcionarioService;

    public FuncionarioTesteResource(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping(path = "/funcionarios/criar/{name}")
    public String helloApp(@PathVariable String name) {
        Funcionario p = new Funcionario();
        p.setNome(name);
        funcionarioService.save(p);
        return "criou";
    }

    @GetMapping(path = "/funcionarios/listar/{id}")
    public Funcionario helloApp(@PathVariable Long id) {
        return funcionarioService.findOne(id).get();
    }

}
