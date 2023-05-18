package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.Entity.Veiculo;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.Estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping (value = "/api/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);
        return veiculo == null
                ? ResponseEntity.badRequest().body("Valor nao encontrado")
                : ResponseEntity.ok(veiculo);
    }
    @GetMapping ({"/all"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(this.veiculoRepository.findAll());
    }
    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(this.veiculoRepository.findByAtivoTrue());
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Veiculo veiculo){
        try{
            this.veiculoRepository.save(veiculo);
            return ResponseEntity.ok("Registro Cadastrado com sucesso");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editar(
            @RequestParam("id") final Long id,
            @RequestBody final  Veiculo veiculo
    ) {
        try{
            final Veiculo veiculobanco = this.veiculoRepository.findById(id).orElse(null);
            if (veiculobanco == null || !veiculobanco.getId().equals(veiculobanco.getId())){
                throw new RuntimeException("Não foi possivel identificar o registro informado");
            }
            this.veiculoRepository.save(veiculo);
            return ResponseEntity.ok("Registro Atualizado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR" + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") final Long id) {
        try {
            Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);
            final List<Movimentacao> movimentacao = this.movimentacaoRepository.findAll();
            boolean found = false;

            for (Movimentacao m : movimentacao) {
                if (id == m.getCondutor().getId()) {
                    found = true;
                    break;
                }
            }
            if (found) {
                veiculo.setAtivo(false);
                veiculoRepository.save(veiculo);
                return ResponseEntity.ok("Apagado");
            } else if (veiculo != null) {
                veiculoRepository.delete(veiculo);
                return ResponseEntity.ok("Apagado");
            } else {
                return ResponseEntity.badRequest().body("Registro nao encontrado, verifique");
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }
}