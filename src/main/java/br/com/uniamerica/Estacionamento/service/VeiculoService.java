package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Veiculo;
import br.com.uniamerica.Estacionamento.repository.ModeloRepository;
import br.com.uniamerica.Estacionamento.repository.VeiculoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {
    @Autowired
    private VeiculoRepository veiculoRepository;

    public Optional<Veiculo> procurarVeiculo(Long id){
        if (!veiculoRepository.idExistente(id) ){
            throw new RuntimeException("Esse ID nao esta no banco de dados, verifique e tente novamente");
        }else {
            Optional<Veiculo> veiculo = this.veiculoRepository.findById(id);
            return veiculo;
        }
    }
    public List<Veiculo> listaVeiculo(){

        List<Veiculo> veiculo = veiculoRepository.findAll();
        return veiculo;
    }
    public List<Veiculo> ativosVeiculo(){
        List<Veiculo> veiculo = veiculoRepository.findByAtivoTrue();
        return veiculo;
    }




    @Transactional
    public void cadastraVeiculo(Veiculo veiculo){
        if("".equals(veiculo.getPlaca())){
            throw new RuntimeException("Verifique a placa do seu veiculo e tente novamente)");
        }
        if("".equals(veiculo.getModelo().getNomeModelo())){
            throw new RuntimeException("Verifique o  modelo do seu veiculo e tente novamente");
        }
        if("".equals(veiculo.getModelo().getMarca().getNomeMarca())){
            throw new RuntimeException("Verifique a marca do seu veiculo e tente novamente");
        }
        if("".equals(veiculo.getAno())){
            throw new RuntimeException("Verifique o ano do seu veiculo e tente novamente");
        }
        this.veiculoRepository.save(veiculo);
    }
    @Transactional
    public void atualizaVeiculo(final Long id, Veiculo veiculo){
        final Veiculo veiculoBanco = this.veiculoRepository.findById(id).orElse(null);
        if(veiculoBanco==null || !veiculoBanco.getId().equals(veiculo.getId())){
            throw new RuntimeException("Registro nao encontrado, verifique e tente novamente");
        }
        if("".equals(veiculo.getPlaca())){
            throw new RuntimeException("Verifique a placa do seu veiculo e tente novamente");
        }
        if("".equals(veiculo.getModelo().getNomeModelo())){
            throw new RuntimeException("Verifique o  modelo do seu veiculo e tente novamente");
        }
        if("".equals(veiculo.getModelo().getMarca().getNomeMarca())){
            throw new RuntimeException("Verifique a marca do seu veiculo e tente novamente");
        }
        if("".equals(veiculo.getAno())){
            throw new RuntimeException("Verifique o ano do seu veiculo e tente novamente");
        }
        this.veiculoRepository.save(veiculo);
    }
    @Transactional(rollbackOn = Exception.class)
    public void deleteVeiculo( @RequestParam("id") final Long id) {
        Veiculo veiculo = this.veiculoRepository.findById(id).orElse(null);
        if(veiculoRepository.veiculoExistente(veiculo.getId())){
            veiculo.setAtivo(false);
            veiculoRepository.save(veiculo);
        }else {
            veiculoRepository.delete(veiculo);
        }
    }
}
/*@Transactional
    public Veiculo cadastrarVeiculo(Veiculo veiculo){
        Assim tambem funcionaria
        Assert.hasText(veiculo.getPlaca(), "Insira uma placa para o veiculo");
        Assert.notNull(veiculo.getMarca(), "Insira uma cor para o veiculo");
        Assert.notNull(veiculo.getAno(), "Insira um ano para o veiculo");

        return this.veiculoRepository.save(veiculo);

    }*/