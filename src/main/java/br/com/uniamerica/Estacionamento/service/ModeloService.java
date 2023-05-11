package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Modelo;
import br.com.uniamerica.Estacionamento.repository.ModeloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;
    @Transactional
    public void cadastraModelo(Modelo modelo){
        if("".equals(modelo.getMarca().getNomeMarca())){
            throw new RuntimeException("A marca de modelo não possui nome (deve conter!)");
        }
        if("".equals(modelo.getNomeModelo())){
            throw new RuntimeException("O modelo não possui um nome (deve conter!)");
        }
        this.modeloRepository.save(modelo);
    }
    @Transactional
    public void attModelo(final Long id, Modelo modelo){
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        if(modeloBanco==null || !modeloBanco.getId().equals(modelo.getId())){
            throw new RuntimeException("Não foi possivel encontrar o registro informado");
        }
        if("".equals(modelo.getMarca().getNomeMarca())){
            throw new RuntimeException("A marca de modelo não possui nome (deve conter!)");
        }
        if("".equals(modelo.getNomeModelo())){
            throw new RuntimeException("O modelo não possui um nome (deve conter!)");
        }
        if(modelo.getNomeModelo().length()>50){
            throw new RuntimeException("Nome de modelo excedeu o limite (50 caracteres!)");
        }
        this.modeloRepository.save(modelo);
    }
}
