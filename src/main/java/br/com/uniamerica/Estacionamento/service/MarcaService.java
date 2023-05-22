package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Marca;
import br.com.uniamerica.Estacionamento.repository.MarcaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;
    public Optional<Marca> procurarMarca(Long id) {
        if (!marcaRepository.idExistente(id)) {
            throw new RuntimeException("Esse ID nao esta no banco de dados, verifique e tente novamente");
        } else {
            Optional<Marca> marca = this.marcaRepository.findById(id);
            return marca;
        }
    }
    public List<Marca> listaMarca(){
        List<Marca> marca = marcaRepository.findAll();
        return marca;
    }

    public List<Marca> ativosMarca(){
        List<Marca> marca = marcaRepository.findByAtivoTrue();
        return marca;
    }
    @Transactional
    public void cadastraMarca(Marca marca){
        if(marca.getNomeMarca()==null || marca.getNomeMarca().isEmpty()){
            throw new RuntimeException("Insira uma marca)");
        }
        this.marcaRepository.save(marca);
    }



    @Transactional(rollbackOn = Exception.class)
    public void editarMarca(@RequestParam("id")  Long id, @RequestBody Marca marca) {
        final Marca marcabanco = this.marcaRepository.findById(id).orElse(null);
        if (marcabanco == null || !marca.getId().equals(marcabanco.getId())) {
            throw new RuntimeException("Não foi possivel identificar o registro informado");}
        if (!marca.getNomeMarca().matches("[a-zA-Z]{2,50}")){
            throw new RuntimeException("Nome inválido");}
        if (marcaRepository.MarcaRepetida(marca.getNomeMarca())) {
            throw new RuntimeException("Nome Repetido");}
        marcaRepository.save(marca);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deletarMarca( @RequestParam("id") final Long id) {
        Marca marca = this.marcaRepository.findById(id).orElse(null);
        if(marcaRepository.marcaExistente(marca.getId())){
            marca.setAtivo(false);
            marcaRepository.save(marca);
        }else {
            marcaRepository.delete(marca);
        }
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