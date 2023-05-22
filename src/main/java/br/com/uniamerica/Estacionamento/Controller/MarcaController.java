package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Marca;
import br.com.uniamerica.Estacionamento.repository.MarcaRepository;
import br.com.uniamerica.Estacionamento.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<?> idMarca(@RequestParam("id") final Long id) {
        try {
            return ResponseEntity.ok(marcaService.procurarMarca(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
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
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editarMarca(
            @RequestParam("id") final Long id,
            @RequestBody final  Marca marca
    ) {
        try{
            this.marcaService.editarMarca(id,marca);
            return ResponseEntity.ok("Registro Atualizado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("ERROR " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            this.marcaService.deletarMarca(id);
            return ResponseEntity.ok("Registro Desativado");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }
}

