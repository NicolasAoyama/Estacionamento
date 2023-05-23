package br.com.uniamerica.Estacionamento.Controller;

import br.com.uniamerica.Estacionamento.Entity.Modelo;
import br.com.uniamerica.Estacionamento.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    @PutMapping
    public ResponseEntity<?> editarModelo(
            @RequestParam("id") final Long id,
            @RequestBody final  Modelo modelo
    ) {
        try{
            this.modeloService.attModelo(id,modelo);
            return ResponseEntity.ok("Modelo atualizado");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            this.modeloService.delete(id);
            return ResponseEntity.ok("Modelo desativado");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro: " + e.getCause().getCause().getMessage());
        }
    }
}