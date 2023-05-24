package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Modelo;
import br.com.uniamerica.Estacionamento.repository.ModeloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeloService {
    @Autowired
    private ModeloRepository modeloRepository;
    public Optional<Modelo> procurarModelo(Long id){
        if (!modeloRepository.ProcuraId(id) ){
            throw new RuntimeException("Esse ID nao esta no banco de dados, verifique e tente novamente");
        }else {
            Optional<Modelo> modelo = this.modeloRepository.findById(id);
            return modelo;
        }
    }
    public List<Modelo> listaModelo(){

        List<Modelo> modelo = modeloRepository.findAll();
        return modelo;
    }
    public List<Modelo> ativosModelo(){
        List<Modelo> modelo = modeloRepository.findByAtivoTrue();
        return modelo;
    }
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
    public Modelo atualizarModelo(Long id, Modelo modeloAtualizado) {
        Modelo modeloExistente = modeloRepository.findById(id).orElse(null);
        if (modeloExistente == null) {
            return null;
        }else{

            modeloExistente.setNomeModelo(modeloAtualizado.getNomeModelo());
            return modeloRepository.save(modeloExistente);
        }
    }
    public boolean excluirModelo(Long id) {
        Modelo modeloExistente = modeloRepository.findById(id).orElse(null);
        if (modeloExistente == null) {
            return false;
        } else {
            modeloRepository.delete(modeloExistente);
            return true;
        }
    }
}
