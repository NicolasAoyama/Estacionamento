package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import br.com.uniamerica.Estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.Estacionamento.service.ConfiguracaoService;
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
    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping
    public ResponseEntity<?> idConfig(@RequestParam("id") final Long id) {
        try {
            return ResponseEntity.ok(configuracaoService.procurarConfig(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> cadastrarConfig(@RequestBody final Configuracao configuracao){
        try{
            this.configuracaoService.cadastraConfiguracao(configuracao);
            return ResponseEntity.ok("Configuracao cadastrada");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editarConfig(
            @RequestParam("id") final Long id,
            @RequestBody final  Configuracao configuracao
    ) {
        try{
            this.configuracaoService.attConfiguracao(id,configuracao);
            return ResponseEntity.ok("Registro Atualizado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }
    }
}
