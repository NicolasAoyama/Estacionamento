package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.repository.CondutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Optional;
/*As services seriam minhas funcoes desenvolvidas, importante entender a estrutura do programa, essas funcoes sao chamadas
no meu controller, e retornam valores para o controller, essas funcoes chamam outras funcoes de verificacao dos repositorys*/
@Service
public class CondutorService {

    @Autowired
    private CondutorRepository condutorRepository;
    /*Aqui eu coloco um optional para informar que eu posso receber um objeto do tipo condutor ou nao passando o parametro id
    entao eu faco um if chamando o condutor repository que verifica se o id ja existe no meu bando de dados.
    caso nao exista, o usuario é avisado, caso exista eu atribuo meu condutor com as informacoes do id pedido e retorno ao usuario*/

    public Optional<Condutor> procurarCondutor(Long id){
        if (!condutorRepository.idExistente(id) ){
            throw new RuntimeException("Esse ID nao esta no banco de dados, verifique e tente novamente");
        }else {
            Optional<Condutor> condutor = this.condutorRepository.findById(id);
            return condutor;
        }
    }
    /*Assim como explicado no controller, todas as funcoes aqui vao ter a logica bem parecida, so os detalhes vao
    ser mudados. Nesse caso aqui eu crio uma lista de condutores e igualo ela a todos os condutores que eu tiver,
    chamando um findall no condutorRepository*/
    public List<Condutor> listaCondutor(){

        List<Condutor> condutor = condutorRepository.findAll();
        return condutor;
    }
    public List<Condutor> ativosCondutor(){
        List<Condutor> condutor = condutorRepository.findByAtivoTrue();
        return condutor;
    }
    //pesquisar as anotations para colocar na entity
    @Transactional(rollbackOn = Exception.class)
    public void cadastrarCondutor(final Condutor condutor){
        if(condutor.getNomeCondutor() == null){
            throw new RuntimeException("Insira um nome e tente novamente");
        } else if (!condutor.getNomeCondutor().matches("^[a-zA-Z]{2}[a-zA-Z\s]{0,48}$")) {
            throw new RuntimeException("Insira um nome real e tente novamente");
        } else if(condutor.getCpf() == null){
            throw new RuntimeException("CPF inválido, verifique e tente novamente");
        } else if (!condutor.getCpf().matches("[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}")) {
            throw new RuntimeException("CPF inválido, verifique e tente novamente");
        } else if (condutor.getTelefone() == null) {
            throw new RuntimeException("Telefone inválido, ferifique e tente novamente");
        } else if(condutor.getCadastro() == null){
            throw new RuntimeException("Data invalida, verifique e tente novamente");
        } else if (condutorRepository.nomeExistente(condutor.getNomeCondutor())) {
            throw new RuntimeException("Esse nome ja esta cadastrado");
        } else if (condutorRepository.idExistente(condutor.getId())) {
            throw new RuntimeException("ID Repetido");
        } else if (condutorRepository.telefoneExistente(condutor.getTelefone())) {
            throw new RuntimeException("Telefone ja cadastrado. Verifique e tente novamente.");
        } else if (condutorRepository.cpfExistente(condutor.getCpf())) {
            throw new RuntimeException("CPF ja cadastrado. Verifique e tente novamente");
        } else{
            condutorRepository.save(condutor);
        }
    }
    @Transactional(rollbackOn = Exception.class)
    public void editarCondutor(@RequestParam("id") final Long id, @RequestBody final  Condutor condutor) {
        final Condutor condutorbanco  = this.condutorRepository.findById(id).orElse(null);
        if (condutorbanco == null || !condutor.getId().equals(condutorbanco.getId())) {
            throw new RuntimeException("ID nao encontrado, verifique e tente novamente");
        } else if (!condutor.getNomeCondutor().matches("^[a-zA-Z]{2}[a-zA-Z\s]{0,48}$")){
            throw new RuntimeException("Insira um nome real e tente novamente");
        } else if (!condutor.getCpf().matches("[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}")) {
            throw new RuntimeException("CPF inválido, verifique e tente novamente");
        }/*else if (condutorRepository.nomeExistente(condutor.getNomeCondutor())) {
            throw new RuntimeException(" Nome ");
        } */else if(condutor.getCpf() == null){
            throw new RuntimeException(" CPF inválido");
        } else if (!condutor.getTelefone().matches("^[0-9\s]{2}[0-9]{4,5}[-][0-9]{4}$")) {
            throw new RuntimeException(" Telefone com Número Faltando");
        } else{
            condutorRepository.save(condutor);
        }
    }
    @Transactional(rollbackOn = Exception.class)
    public void delete( @RequestParam("id") final Long id) {
        Condutor condutor = this.condutorRepository.findById(id).orElse(null);
        if(condutorRepository.condutorExistente(condutor.getId())){
            condutor.setAtivo(false);
            condutorRepository.save(condutor);
        }else {
            condutorRepository.delete(condutor);
        }
    }
}













   /* @Transactional
    public void cadastraCondutor(Condutor condutor) {
        if (condutor.getNomeCondutor() == null || condutor.getNomeCondutor().isEmpty()) {
            throw new RuntimeException("Insira um nome para o condutor");
        }
        if (condutor.getCpf() == null || condutor.getCpf().isEmpty()) {
            throw new RuntimeException("Insira um CPF para o condutor");
        }
        if (condutor.getTelefone() == null || condutor.getTelefone().isEmpty()) {
            throw new RuntimeException("Insira um telefone para o condutor");
        }
        if (condutor.getCpf().length() > 15) {
            throw new RuntimeException("O CPF do condutor excedeu o limite de 15 caracteres, confirme se o CPF esta correto");
        }
        if (condutor.getTelefone().length() > 17) {
            throw new RuntimeException("O numero de telefone do condutor excedeu o limite de caracteres, confirme se o seu numero esta correto");
        }
        if (condutor.getNomeCondutor().length() > 100) {
            throw new RuntimeException("O nome do condutor excedeu o limite de caracteres, por favor altere seu nome");
        }
        this.condutorRepository.save(condutor);
    }
    @Transactional
    public void attCondutor(final Long id, Condutor condutor) {
        final Condutor condutorBanco = this.condutorRepository.findById(id).orElse(null);
        if (condutorBanco == null || !condutorBanco.getId().equals(condutor.getId())) {
            throw new RuntimeException("Registro nao encontrado, verifique e tente novamente");
        }
        if (condutor.getNomeCondutor() == null || condutor.getNomeCondutor().isEmpty()) {
            throw new RuntimeException("Insira um nome para o condutor");
        }
        if (condutor.getCpf() == null || condutor.getCpf().isEmpty()) {
            throw new RuntimeException("Insira um CPF para o condutor");
        }
        if (condutor.getTelefone() == null || condutor.getTelefone().isEmpty()) {
            throw new RuntimeException("Insira um telefone para o condutor");
        }
        this.condutorRepository.save(condutor);
    }*/
