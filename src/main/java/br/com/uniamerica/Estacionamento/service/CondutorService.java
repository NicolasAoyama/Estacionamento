package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.repository.CondutorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CondutorService {
    @Autowired
    private CondutorRepository condutorRepository;
    @Transactional
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
    }

}