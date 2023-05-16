package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.CondutorRepository;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.Estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping (value = "/api/condutor")
public class CondutorController {
    @Autowired
    private CondutorService condutorService;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    @GetMapping
    public ResponseEntity<?> idCondutor(@RequestParam("id") final Long id){
        try{
            return ResponseEntity.ok(condutorService.procurarCondutor(id));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO " + e.getMessage());
        }
    }
    @GetMapping ({"/lista"})
    public ResponseEntity<?> Listacondutor(){
        return ResponseEntity.ok(condutorService.listaCondutor());
    }
    @GetMapping({"/ativos"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(condutorService.ativosCondutor());
    }
    @PostMapping
    public ResponseEntity<?> cadastrarCondutor(@RequestBody final Condutor condutor){
        try{
            this.condutorService.cadastrarCondutor(condutor);
            return ResponseEntity.ok("Condutor cadastrado");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("ERRO" + e.getMessage());
        }
    }









   /* @GetMapping ({"/all"})
    public ResponseEntity<?> listacompleta(){
        return ResponseEntity.ok(this.condutorRepository.findAll());
    }

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Condutor condutor = this.condutorRepository.findById(id).orElse(null);
        return condutor == null
                ? ResponseEntity.badRequest().body("Valor nao encontrado")
                : ResponseEntity.ok(condutor);
    }
    @GetMapping({"/ativo"})
    public ResponseEntity<?> ativos(){
        return ResponseEntity.ok(this.condutorRepository.findByAtivoTrue());
    }

    @PutMapping
    public ResponseEntity<?> alterar(
            @RequestParam("id") final Long id,
            @RequestBody final  Condutor condutor
    ) {
        try{
            final Condutor condutorbanco = this.condutorRepository.findById(id).orElse(null);

            if (condutorbanco == null || !condutor.getId().equals(condutorbanco.getId())){
                throw new RuntimeException("Registro nao encontrado, verifique");
            }
            this.condutorRepository.save(condutor);
            return ResponseEntity.ok("aleluia funcionou");
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
            Condutor condutor = this.condutorRepository.findById(id).orElse(null);
            final List<Movimentacao> movimentacao = this.movimentacaoRepository.findAll();
            boolean found = false;

            for (Movimentacao m : movimentacao) {
                if (id == m.getCondutor().getId()) {
                    found = true;
                    break;
                }
            }
            if (found) {
                condutor.setAtivo(false);
                condutorRepository.save(condutor);
                return ResponseEntity.ok("Apagado");
            } else if (condutor != null) {
                condutorRepository.delete(condutor);
                return ResponseEntity.ok("Apagado");
            } else {
                return ResponseEntity.badRequest().body("Registro nao encontrado, verifique");
            }
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }
    }*/

}