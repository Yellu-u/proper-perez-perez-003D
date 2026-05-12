package com.proper.service_productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proper.service_productos.model.Linea;
import com.proper.service_productos.repository.LineaRepository;

@RestController
@RequestMapping("/productos/linea")
public class LineaController 
{
        @Autowired
        private LineaRepository repository;

        @GetMapping
        public List<Linea> listar()
        {
                return repository.findAll();
        }

        @PostMapping
        public Linea crear(@RequestBody Linea linea)
        {
                return repository.save(linea);
        }
}