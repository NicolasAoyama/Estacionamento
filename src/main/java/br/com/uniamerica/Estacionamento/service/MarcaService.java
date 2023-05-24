package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Marca;
import br.com.uniamerica.Estacionamento.Entity.Modelo;
import br.com.uniamerica.Estacionamento.repository.MarcaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Marca atualizarMarca(Long id, Marca marcaAtualizada) {
        Marca marcaExistente = marcaRepository.findById(id).orElse(null);
        if (marcaExistente == null) {
            return null;
        } else {
            marcaExistente.setNomeMarca(marcaAtualizada.getNomeMarca());
            return marcaRepository.save(marcaExistente);
        }
    }
    @Transactional(rollbackOn = Exception.class)
    public void deletar(final Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(marca.getId()).orElse(null);

        List<Modelo> modeloLista = this.marcaRepository.findModeloByMarca(marcaBanco);

        if (modeloLista.isEmpty()){
            this.marcaRepository.delete(marcaBanco);
        }else{
            this.marcaRepository.save(marca);
        }
        if (marcaBanco != null){
            marcaBanco.setAtivo(false);
        }
    }
}
