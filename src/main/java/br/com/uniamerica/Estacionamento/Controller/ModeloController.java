package br.com.uniamerica.Estacionamento.Controller;

import br.com.uniamerica.Estacionamento.Entity.Modelo;
import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.ModeloRepository;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.Estacionamento.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping (value = "/api/modelo")
public class ModeloController {
    @Autowired
    private ModeloRepository modeloRepository;
    @Autowired
    private VeiculoRepository veiculoRepository;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @GetMapping
    public ResponseEntity<?> findByIdRequest(@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);

        return modelo == null
                ? ResponseEntity.badRequest().body("nenhum valor encontrado.")
                : ResponseEntity.ok(modelo);
    }
    @GetMapping ({"/all"})
    public ResponseEntity<?> Listacompleta(){
        return ResponseEntity.ok(this.modeloRepository.findAll());
    }
    @GetMapping({"/ativo"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(this.modeloRepository.findByAtivoTrue());
    }
    @PostMapping

    public ResponseEntity<?> cadastrarModelo(@RequestBody final Modelo modelo){
        try{
            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("Modelo cadastrado");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> alterar(
            @RequestParam("id") final Long id,
            @RequestBody final  Modelo modelo
    ) {
        try{
            final Modelo modelobanco = this.modeloRepository.findById(id).orElse(null);

            if (modelobanco == null || !modelo.getId().equals(modelobanco.getId())){
                throw new RuntimeException("Registro nao encontrado, verifique");
            }
            this.modeloRepository.save(modelo);
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
    public ResponseEntity<?> delete(@RequestParam("id") final Long id) {
        try {
            Modelo modelo = this.modeloRepository.findById(id).orElse(null);
            final List<Movimentacao> movimentacao = this.movimentacaoRepository.findAll();
            boolean found = false;

            for (Movimentacao m : movimentacao) {
                if (id == m.getCondutor().getId()) {
                    found = true;
                    break;
                }
            }
            if (found) {
                modelo.setAtivo(false);
                modeloRepository.save(modelo);
                return ResponseEntity.ok("Apagado");
            } else if (modelo != null) {
                modeloRepository.delete(modelo);
                return ResponseEntity.ok("Apagado");
            } else {
                return ResponseEntity.badRequest().body("Registro nao encontrado, verifique");
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }
}