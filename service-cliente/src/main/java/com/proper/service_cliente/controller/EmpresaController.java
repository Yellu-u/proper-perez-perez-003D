package com.proper.service_cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proper.service_cliente.model.Empresa;
import com.proper.service_cliente.repository.EmpresaRepository;



@RestController
@RequestMapping("/api/v1/clientes/empresas")
public class EmpresaController 
{
    @Autowired
    private EmpresaRepository repository;

    @GetMapping
    public List<Empresa> listar()
    {
        return repository.findAll();
    }

    @PostMapping
    public Empresa crear(@RequestBody Empresa empresa)
    {
        return repository.save(empresa);
    }
}
