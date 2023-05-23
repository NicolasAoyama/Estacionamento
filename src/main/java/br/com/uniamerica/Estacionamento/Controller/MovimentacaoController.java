package br.com.uniamerica.Estacionamento.Controller;

import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;


    @GetMapping
    public ResponseEntity<?> idCondutor(@RequestParam("id") final Long id) {
        try {
            return ResponseEntity.ok(movimentacaoService.procurarMovimentacao(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @GetMapping({"/lista"})
    public ResponseEntity<?> Listamovimentacao() {
        return ResponseEntity.ok(movimentacaoService.listaMovimentacao());
    }

    @GetMapping({"/ativos"})
    public ResponseEntity<?> getAtivos() {
        return ResponseEntity.ok(movimentacaoService.ativosMovimentacao());
    }

    @PostMapping
    public ResponseEntity<?> cadastrarMovimentacao(@RequestBody final Movimentacao movimentacao) {
        try {
            this.movimentacaoService.cadastraMovimentacao(movimentacao);
            return ResponseEntity.ok("Movimentacao cadastrada");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editarMovimentacao(
            @RequestParam("id") final Long id,
            @RequestBody final Movimentacao movimentacao
    ) {
        try {
            this.movimentacaoService.attMovimentacao(id, movimentacao);
            return ResponseEntity.ok("Movimentacao atualizada com sucesso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") final Long id) {
        try {
            this.movimentacaoService.deleteMovimentacao(id);
            return ResponseEntity.ok("Movimentacao desativada");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Erro: " + e.getCause().getCause().getMessage());
        }
    }/*
    @PutMapping(value = "/finalizar")
    public ResponseEntity<?> finalizar(@RequestParam("id")final Long id,@RequestBody final  Movimentacao movimentacao){
        try{
            return ResponseEntity.ok(movimentacaoService.finalizarMovimentacao(id,movimentacao));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }*/
}