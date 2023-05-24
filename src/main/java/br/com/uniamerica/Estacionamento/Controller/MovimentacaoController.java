package br.com.uniamerica.Estacionamento.Controller;

import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.Estacionamento.service.MovimentacaoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping (value = "/api/movimentacao")
public class MovimentacaoController {
    @Autowired
    private MovimentacaoService movimentacaoService;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;


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
    public ResponseEntity<?> cadastrar(@RequestBody final Movimentacao movimentacao) {
        try {
            this.movimentacaoService.cadastrar(movimentacao);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERRO AQUI TA DANDO ERRO" + e.getMessage());
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Movimentacao movimentacao) {
        Optional<Movimentacao> movimentacaoExiste = movimentacaoRepository.findById(id);

        if (movimentacaoExiste.isPresent()) {
            Movimentacao movimentacaoAtualizado = movimentacaoExiste.get();

            movimentacaoService.atualizarMovimentacao(movimentacaoAtualizado.getId(), movimentacao);

            return ResponseEntity.ok().body("Registro de Movimetnacao atulizado com sucesso");
        } else {
            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        Optional<Movimentacao> optionalMovimentacao = movimentacaoRepository.findById(id);

        if (optionalMovimentacao.isPresent()) {
            Movimentacao movimentacao = optionalMovimentacao.get();

            if (movimentacao.isAtivo()) {
                movimentacaoRepository.delete(movimentacao);
                return ResponseEntity.ok().body("O Registro de Movimentacao Foi Deletado com sucesso");
            } else {
                movimentacao.setAtivo(false);
                movimentacaoRepository.save(movimentacao);
                return ResponseEntity.ok().body("A movimentacao foi desativada com sucesso");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/finalizar")
    public ResponseEntity<?> finalizar(@RequestParam("id")final Long id,@RequestBody final  Movimentacao movimentacao){
        try{
            return ResponseEntity.ok(movimentacaoService.finalizarMovimentacao(id,movimentacao));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Error " + e.getMessage());
        }
    }




}
