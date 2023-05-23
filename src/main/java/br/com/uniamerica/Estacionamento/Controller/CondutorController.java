package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.Estacionamento.service.CondutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/*Controller: O controller fornece os metodos para o meu programa, ele é como se fosse a chamada das funcoes do meu
programa, recebendo tambem os resultados. Nesses controllers que eu vou fazer aqui estaram metodos get,put e tal
*/
@RestController
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
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    /*O primeiro getmapping é diferente na questao do @RequestParam pois eu preciso de um parametro
     que no caso é o id, como nos proximos eu nao preciso, posso colocar dessa maneira*/
    @GetMapping ({"/lista"})
    public ResponseEntity<?> ListaCondutor(){
        return ResponseEntity.ok(condutorService.listaCondutor());
    }
    @GetMapping({"/ativos"})
    public ResponseEntity<?> getAtivos(){
        return ResponseEntity.ok(condutorService.ativosCondutor());
    }
    /*Todos os metodos tem a mesma logica, o metodo POST serve para eu cadastrar dados, aqui estou cadastrando
    na minha classe condutor
    A diferenca aqui é que eu faco um requestbody para indicar que um objeto do tipo Condutor vai ser obtido no
    body da requisicao (no body postman)
    De resto é o mesmo do GET, faco um try catch para chamar a funcao cadastrarCondutor com os parametros da classe
    condutor, e retorno uma mensagem de sucesso, e caso de errado eu pego a mensagem e retorno para o usuario*/
    @PostMapping
    public ResponseEntity<?> cadastrarCondutor(@RequestBody final Condutor condutor){
        try{
            this.condutorService.cadastrarCondutor(condutor);
            return ResponseEntity.ok("Condutor cadastrado");
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Erro: " + e.getMessage());
        }
    }
    /*Mesma coisa dos metodos anteriores, metodo PUT para editar condutores, dessa vez eu chamo o @RequestParam para
    identificar o id que devera ser atualizado, e chamo o @RequestBody para indicar que um objeto do tipo condutor
    vai ser obtido no body da requisicao, como foi explicado antes.*/
    @PutMapping
    public ResponseEntity<?> editarCondutor(@RequestParam("id") final Long id, @RequestBody final  Condutor condutor){
        try{
            this.condutorService.editarCondutor(id,condutor);
            return ResponseEntity.ok("Condutor atualizado");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Erro:  TA DANDO ERRADO AQ O" + e.getMessage());
        }
    }
    @DeleteMapping
    public ResponseEntity<?> delete( @RequestParam("id") final Long id){
        try {
            this.condutorService.delete(id);
            return ResponseEntity.ok("Condutor Desativado");
        } catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Erro: " + e.getCause().getCause().getMessage());
        }
    }
}