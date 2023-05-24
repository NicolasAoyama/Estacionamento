package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Marca;
import br.com.uniamerica.Estacionamento.repository.MarcaRepository;
import br.com.uniamerica.Estacionamento.service.MarcaService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping (value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<?> idMarca(@RequestParam("id") final Long id) {
        try {
            return ResponseEntity.ok(marcaService.procurarMarca(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listamarca(){
        return ResponseEntity.ok(marcaService.listaMarca());
    }
    @GetMapping({"/ativos"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(marcaService.ativosMarca());
    }
    @PostMapping
    public ResponseEntity<?> cadastrarMarca(@RequestBody final Marca marca){
        try{
            this.marcaService.cadastraMarca(marca);
            return ResponseEntity.ok("Marca cadastrada");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Marca marca) {
        Optional<Marca> marcaExiste = marcaRepository.findById(id);

        if (marcaExiste.isPresent()) {

            Marca marcaAtualizado = marcaExiste.get();


            marcaService.atualizarMarca(marcaAtualizado.getId(), marca);

            return ResponseEntity.ok().body("Registro atualizado com sucesso");
        } else {

            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deletar (@RequestParam("id") final Long id){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);

        try{
            this.marcaService.deletar(marcaBanco);
            return ResponseEntity.ok("Registro deletado");
        }catch (RuntimeException erro){
            return ResponseEntity.internalServerError().body("Erro"+erro.getMessage());
        }
    }


}

   /*@PutMapping
    public ResponseEntity<?> editarMarca(
            @RequestParam("id") final Long id,
            @RequestBody final  Marca marca
    ) {
        try{
            this.marcaService.editarMarca(id,marca);
            return ResponseEntity.ok("Marca atualizada");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro: " + e.getMessage());
        }
    }*/

    /*@DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            this.marcaService.deletarMarca(id);
            return ResponseEntity.ok("Marca Desativada");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro: " + e.getCause().getCause().getMessage());
        }
    }*/