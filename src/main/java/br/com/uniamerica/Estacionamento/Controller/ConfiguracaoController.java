package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import br.com.uniamerica.Estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.Estacionamento.service.ConfiguracaoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    @PostMapping
    public ResponseEntity<?> cadastrarConfig(@RequestBody final Configuracao configuracao){
        try{
            this.configuracaoService.cadastraConfiguracao(configuracao);
            return ResponseEntity.ok("Configuracao cadastrada");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Configuracao configuracao) {
        Optional<Configuracao> configuracaoCriado = configuracaoRepository.findById(id);

        if (configuracaoCriado.isPresent()){
            Configuracao configuracaoAtualizado = configuracaoCriado.get();

            configuracaoService.atualizarConfiguracao(configuracaoAtualizado.getId(), configuracao);

            return  ResponseEntity.ok().body("Registro de cadastro atulizado com sucesso");
        }
        else{
            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }

}
