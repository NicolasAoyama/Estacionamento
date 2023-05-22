package br.com.uniamerica.Estacionamento.Controller;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Modelo;
import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.ModeloRepository;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.Estacionamento.repository.VeiculoRepository;
import br.com.uniamerica.Estacionamento.service.ModeloService;
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
    private ModeloService modeloService;

    @GetMapping
    public ResponseEntity<?> idModelo(@RequestParam("id") final Long id){
        try{
            return ResponseEntity.ok(modeloService.procurarModelo(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
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
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editarModelo(
            @RequestParam("id") final Long id,
            @RequestBody final  Modelo modelo
    ) {
        try{
            this.modeloService.attModelo(id,modelo);
            return ResponseEntity.ok("Registro Atualizado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR " + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            this.modeloService.delete(id);
            return ResponseEntity.ok("Registro Desativado");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }








}