package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Veiculo;
import br.com.uniamerica.Estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.Estacionamento.service.VeiculoService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping (value = "/api/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public ResponseEntity<?> idVeiculo(@RequestParam("id") final Long id){
        try{
            return ResponseEntity.ok(veiculoService.procurarVeiculo(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listaveiculo(){
        return ResponseEntity.ok(veiculoService.listaVeiculo());
    }

    @GetMapping({"/ativos"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(veiculoService.ativosVeiculo());
    }

    @PostMapping
    public ResponseEntity<?> cadastrarCondutor(@RequestBody final Veiculo veiculo){
        try{
            this.veiculoService.cadastrar(veiculo);
            return ResponseEntity.ok("Veiculo cadastrado");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Veiculo veiculo) {
        Optional<Veiculo> veiculoExiste = veiculoRepository.findById(id);

        if (veiculoExiste.isPresent()) {
            Veiculo veiculoAtualizado = veiculoExiste.get();

            veiculoService.atualizarVeiculo(veiculoAtualizado.getId(), veiculo);
            return ResponseEntity.ok().body("Registro atualizado com sucesso");
        } else {
            return ResponseEntity.badRequest().body("Id não foi encontrado ou não corresponde ao veículo informado");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        Optional<Veiculo> optionalVeiculo = veiculoRepository.findById(id);

        if (optionalVeiculo.isPresent()) {
            Veiculo veiculo = optionalVeiculo.get();

            if (veiculo .isAtivo()) {
                veiculoRepository.delete(veiculo);
                return ResponseEntity.ok("O registro do veículo foi deletado com sucesso");
            } else {
                veiculo.setAtivo(false);
                veiculoRepository.save(veiculo);
                return ResponseEntity.ok("O veículo estava vinculado a uma ou mais movimentações e foi desativado com sucesso");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}