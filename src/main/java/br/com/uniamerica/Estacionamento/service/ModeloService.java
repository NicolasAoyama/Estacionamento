package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Modelo;
import br.com.uniamerica.Estacionamento.repository.ModeloRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

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





    /*@Transactional
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
    }*/
/*@Transactional(rollbackOn = Exception.class)
    public void delete( @RequestParam("id") final Long id) {
        Modelo modelo = this.modeloRepository.findById(id).orElse(null);
        if(modeloRepository.modeloExistente(modelo.getId())){
            modelo.setAtivo(false);
            modeloRepository.save(modelo);
        }else {
            modeloRepository.delete(modelo);
        }
    }*/