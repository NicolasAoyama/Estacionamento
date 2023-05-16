package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.repository.CondutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;

    public Optional<Condutor> procurarCondutor(Long id){
        if (!condutorRepository.idExistente(id) ){
            throw new RuntimeException("Esse ID nao esta no banco de dados, verifique e tente novamente");
        }else {
            Optional<Condutor> condutor = this.condutorRepository.findById(id);
            return condutor;
        }
    }
    public List<Condutor> listaCondutor(){

        List<Condutor> condutor = condutorRepository.findAll();
        return condutor;
    }
    public List<Condutor> ativosCondutor(){
        List<Condutor> condutor = condutorRepository.findByAtivoTrue();
        return condutor;
    }
    @Transactional(rollbackOn = Exception.class)
    public void cadastrarCondutor(final Condutor condutor){
        if(condutor.getNomeCondutor() == null){
            throw new RuntimeException("Insira um nome e tente novamente");
        } else if (!condutor.getNomeCondutor().matches("^[a-zA-Z]{2}[a-zA-Z\s]{0,48}$")) {
            throw new RuntimeException("Insira um nome real e tente novamente");
        } else if(condutor.getCpf() == null){
            throw new RuntimeException("CPF inválido, verifique e tente novamente");
        } else if (!condutor.getCpf().matches("[0-9]{3}[.][0-9]{3}[.][0-9]{3}[-][0-9]{2}")) {
            throw new RuntimeException(" CPF inválido, verifique e tente novamente");
        } else if (condutor.getTelefone() == null) {
            throw new RuntimeException("Telefone inválido, ferifique e tente novamente");
        } else if(condutor.getCadastro() == null){
            throw new RuntimeException("Data invalida, verifique e tente novamente");
        } else if (condutorRepository.nomeExistente(condutor.getNomeCondutor())) {
            throw new RuntimeException("Esse nome");
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

}