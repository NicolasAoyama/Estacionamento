package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Veiculo;
import br.com.uniamerica.Estacionamento.service.MovimentacaoService;
import br.com.uniamerica.Estacionamento.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping (value = "/api/veiculo")
public class VeiculoController {
    @Autowired
    private VeiculoService veiculoService;
    @Autowired
    private MovimentacaoService movimentacaoService;

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
            this.veiculoService.cadastraVeiculo(veiculo);
            return ResponseEntity.ok("Veiculo cadastrado");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editarVeiculo(
            @RequestParam("id") final Long id,
            @RequestBody final  Veiculo veiculo
    ) {
        try{
            this.veiculoService.atualizaVeiculo(id,veiculo);
            return ResponseEntity.ok("Veiculo atualizado");
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
            this.veiculoService.deleteVeiculo(id);
            return ResponseEntity.ok("Veiculo desativado");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error " + e.getCause().getCause().getMessage());
        }
    }


}