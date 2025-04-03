package com.hospetagem.hotel.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.hospetagem.hotel.model.Cliente;
import com.hospetagem.hotel.service.ClienteService;

@RestController
@RequestMapping("/api")
class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @PostMapping(value = "/createCliente", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cliente createNewCliente(@RequestBody Cliente cliente) {
        return clienteService.createCliente(cliente);
    }

    @PutMapping(value = "updateCliente", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cliente updateCliente(@RequestBody Cliente cliente) {
        return clienteService.updateCliente(cliente);
    }

    @DeleteMapping(value = "/deleteCliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente deleteCliente(@PathVariable Long id) {
        return clienteService.deleteCliente(id);
    }

    @GetMapping(value = "/consultarCliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Cliente consultaClientePorId(@PathVariable Long id) {
        return clienteService.getClienteById(id);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Cliente login(@RequestBody Cliente cliente) {
        return clienteService.login(cliente.getEmail(), cliente.getSenha());
    }
}