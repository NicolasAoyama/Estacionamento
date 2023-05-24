package br.com.uniamerica.Estacionamento.Controller;
import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.repository.CondutorRepository;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import br.com.uniamerica.Estacionamento.service.CondutorService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    CondutorRepository condutorRepository;
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
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable final @NotNull Long id, @RequestBody final Condutor condutor) {
        Optional<Condutor> condutorExistente = condutorRepository.findById(id);

        if (condutorExistente.isPresent()) { //not null

            //atribui o valor presente dentro do Optional chamado condutorExistente para a variável condutorAtualizado.
            Condutor condutorAtualizado = condutorExistente.get();

            //chama atualizarCondutor passando o ID do condutor atualizado e o objeto condutor que vai ser usado pra atualizar os dados
            condutorService.atualizarCondutor(condutorAtualizado.getId(), condutor);

            return ResponseEntity.ok().body("Registro atualizado com sucesso");
        } else {

            return ResponseEntity.badRequest().body("ID não encontrado");
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable final Long id) {
        Optional<Condutor> optionalCondutor = condutorRepository.findById(id);

        if (optionalCondutor.isPresent()) {
            Condutor condutor = optionalCondutor.get();

            if (condutor.isAtivo()) {
                condutorRepository.delete(condutor);
                return ResponseEntity.ok().body("O registro do condutor foi deletado com sucesso");
            } else {
                condutor.setAtivo(false);
                condutorRepository.save(condutor);
                return ResponseEntity.ok().body("O condutor estava vinculado a uma ou mais movimentações e foi desativado com sucesso");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}