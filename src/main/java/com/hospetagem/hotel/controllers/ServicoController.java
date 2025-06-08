package com.hospetagem.hotel.controllers;

import com.hospetagem.hotel.dto.ServicoDTO;
import com.hospetagem.hotel.model.Servico;
import com.hospetagem.hotel.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

   @Autowired
   private ServicoService servicoService;

   @PostMapping
   public ResponseEntity<Servico> criarServico(@RequestBody ServicoDTO servicoDTO) {
       Servico servico = servicoService.criarServico(servicoDTO);
       return ResponseEntity.ok(servico);
   }

   @GetMapping
   public ResponseEntity<List<Servico>> listarServicos() {
       List<Servico> servicos = servicoService.listarTodosServicos();
       return ResponseEntity.ok(servicos);
   }

   @GetMapping("/calcular-preco")
   public ResponseEntity<BigDecimal> calcularPrecoTotal(@RequestParam List<Long> servicosIds) {
       BigDecimal precoTotal = servicoService.calcularPrecoTotal(servicosIds);
       return ResponseEntity.ok(precoTotal);
   }
}