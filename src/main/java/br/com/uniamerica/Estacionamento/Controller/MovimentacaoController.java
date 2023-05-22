package br.com.uniamerica.Estacionamento.Controller;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.Estacionamento.service.MovimentacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
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
            return ResponseEntity.ok("Condutor cadastrado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editarMovimentacao(
            @RequestParam("id") final Long id,
            @RequestBody final Movimentacao movimentacao
    ) {
        try {
            this.movimentacaoService.attMovimentacao(id, movimentacao);
            return ResponseEntity.ok("Registro Atualizado com sucesso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") final Long id) {
        try {
            this.movimentacaoService.deleteMovimentacao(id);
            return ResponseEntity.ok("Registro Desativado");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }
}