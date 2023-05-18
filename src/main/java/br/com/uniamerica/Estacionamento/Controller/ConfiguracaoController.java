package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import br.com.uniamerica.Estacionamento.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value = "/api/configuracao")
public class ConfiguracaoController {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Configuracao configuracao = this.configuracaoRepository.findById(id).orElse(null);
        return configuracao == null
                ? ResponseEntity.badRequest().body("Valor nao encontrado")
                : ResponseEntity.ok(configuracao);
    }
    @PostMapping
    public ResponseEntity<?> cadastrarConfig(@RequestBody final Configuracao configuracao){
        try{
            this.configuracaoRepository.save(configuracao);
            return ResponseEntity.ok("Configuracao cadastrada");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> alterar(
            @RequestParam("id") final Long id,
            @RequestBody final  Configuracao configuracao
    ) {
        try{
            final Configuracao configuracaobanco  = this.configuracaoRepository.findById(id).orElse(null);
            if (configuracaobanco == null || !configuracaobanco.getId().equals(configuracaobanco.getId())){
                throw new RuntimeException("Registro nao encontrado, verifique");
            }
            this.configuracaoRepository.save(configuracao);
            return ResponseEntity.ok("Atualizado");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }
    }
}
