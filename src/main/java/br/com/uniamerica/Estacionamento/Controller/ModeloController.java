package br.com.uniamerica.Estacionamento.Controller;

import br.com.uniamerica.Estacionamento.Entity.Modelo;
import br.com.uniamerica.Estacionamento.Entity.Veiculo;
import br.com.uniamerica.Estacionamento.repository.ModeloRepository;
import br.com.uniamerica.Estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.Estacionamento.service.ModeloService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping (value = "/api/modelo")
public class ModeloController {
    @Autowired
    private ModeloService modeloService;
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public ResponseEntity<?> idModelo(@RequestParam("id") final Long id){
        try{
            return ResponseEntity.ok(modeloService.procurarModelo(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listamodelo(){
        return ResponseEntity.ok(modeloService.listaModelo());
    }
    @GetMapping({"/ativos"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(modeloService.ativosModelo());
    }
    @PostMapping
    public ResponseEntity<?> cadastrarModelo(@RequestBody final Modelo modelo){
        try{
            this.modeloService.cadastraModelo(modelo);
            return ResponseEntity.ok("Modelo cadastrado");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Modelo modelo) {
        Optional<Modelo> modeloExistente = modeloRepository.findById(id);

        if (modeloExistente.isPresent()) {

            Modelo modeloAtualizado = modeloExistente.get();


            modeloService.atualizarModelo(modeloAtualizado.getId(), modelo);

            return ResponseEntity.ok().body("Registro atualizado com sucesso");
        } else {

            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        Optional<Modelo> optionalModelo = modeloRepository.findById(id);
        Optional<Veiculo> optionalVeiculo = veiculoRepository.findById(id);

        if (optionalModelo.isPresent()) {
            Modelo modelo = optionalModelo.get();

            if (modelo.isAtivo()) {
                modeloRepository.delete(modelo);
                return ResponseEntity.ok("O registro do modelo foi deletado com sucesso");
            } else {
                modelo.setAtivo(false);
                modeloRepository.delete(modelo);
                return ResponseEntity.ok("O modelo estava vinculado a uma ou mais movimentações e foi desativado com sucesso");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
