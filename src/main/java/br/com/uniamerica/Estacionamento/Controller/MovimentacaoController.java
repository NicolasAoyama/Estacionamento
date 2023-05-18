package br.com.uniamerica.Estacionamento.Controller;

import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);
        return movimentacao == null
                ? ResponseEntity.badRequest().body("Valor nao encontrado")
                : ResponseEntity.ok(movimentacao);
    }
    @GetMapping ({"/all"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(this.movimentacaoRepository.findAll());
    }
    @GetMapping({"/ativo"})
    public ResponseEntity<?> Ativos(){
        return ResponseEntity.ok(this.movimentacaoRepository.findByAtivoTrue());
    }
    @PostMapping
    public ResponseEntity<?> cadastrarMov(@RequestBody final Movimentacao movimentacao){
        try{
            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("Movimentacao cadastrada");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> alterar(
            @RequestParam("id") final Long id,
            @RequestBody final  Movimentacao movimentacao
    ) {
        try{
            final Movimentacao movimentacaobanco = this.movimentacaoRepository.findById(id).orElse(null);

            if (movimentacaobanco == null || !movimentacao.getId().equals(movimentacaobanco.getId())){
                throw new RuntimeException("Registro nao encontrado, verifique");
            }
            this.movimentacaoRepository.save(movimentacao);
            return ResponseEntity.ok("Atualizado");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);

            if(movimentacao != null && movimentacao.isAtivo() == true){
                movimentacao.setAtivo(false);
                movimentacaoRepository.save(movimentacao);
                return ResponseEntity.ok("Apagado");
            }
            else if (movimentacao != null && movimentacao.isAtivo() == false){
                return ResponseEntity.badRequest().body("Movimentaçao ja apagada");
            }
            else {
                return ResponseEntity.badRequest().body("Registro nao encontrado, verifique");
            }
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }
}