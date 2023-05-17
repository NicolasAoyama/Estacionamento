package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.Estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
/*Controller: O controller fornece os metodos para o meu programa, ele é como se fosse a chamada das funcoes do meu
programa, recebendo tambem os resultados. Nesses controllers que eu vou fazer aqui estaram metodos get,put e tal
*/
@Controller
@RequestMapping (value = "/api/condutor")
public class CondutorController {
    //autowired chama variaveis, funcoes de outras classes para a minha
    @Autowired
    private CondutorService condutorService;
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    /*Metodo GET pra pegar informacoes do nosso banco de dados - nesse caso eu to definindo o caminho para "id"
    o try catch executa o codigo e interrompe caso ocorra algum erro, caso tudo de certo eu chamo a funcao
    procurarCondutor do meu condutorService enviando o id como parametro*/
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
    @PutMapping
    public ResponseEntity<?> editarCondutor(
            @RequestParam("id") final Long id,
            @RequestBody final  Condutor condutor
    ) {
        try{
            this.condutorService.editarCondutor(id,condutor);
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
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            this.condutorService.delete(id);
            return ResponseEntity.ok("Registro Desativado");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error" + e.getCause().getCause().getMessage());
        }

    }










   /*
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