package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import br.com.uniamerica.Estacionamento.Entity.Marca;
import br.com.uniamerica.Estacionamento.repository.ConfiguracaoRepository;
import br.com.uniamerica.Estacionamento.repository.MarcaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional
    public void cadastraMarca(Marca marca){
        if(marca.getNomeMarca()==null || marca.getNomeMarca().isEmpty()){
            throw new RuntimeException("Insira uma marca)");
        }
        this.marcaRepository.save(marca);
    }
    @Transactional
    public void attMarca(final Long id, Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        if(marcaBanco==null || !marcaBanco.getId().equals(marca.getId())){
            throw new RuntimeException("Registro nao encontrado, verifique e tente novamente");
        }
        if(marca.getNomeMarca()==null || marca.getNomeMarca().isEmpty()){
            throw new RuntimeException("Insira uma marca");
        }
        this.marcaRepository.save(marca);
    }
}