package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;
    public Optional<Movimentacao> procurarMovimentacao(Long id){
        if (!movimentacaoRepository.idExistente(id) ){
            throw new RuntimeException("Esse ID nao esta no banco de dados, verifique e tente novamente");
        }else {
            Optional<Movimentacao> movimentacao = this.movimentacaoRepository.findById(id);
            return movimentacao;
        }
    }
    public List<Movimentacao> listaMovimentacao(){

        List<Movimentacao> movimentacao = movimentacaoRepository.findAll();
        return movimentacao;
    }
    public List<Movimentacao> ativosMovimentacao(){
        List<Movimentacao> movimentacao = movimentacaoRepository.findByAtivoTrue();
        return movimentacao;
    }
    @Transactional
    public void cadastraMovimentacao(Movimentacao movimentacao){
        if(movimentacao.getVeiculo().getPlaca()==null || movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("Verifique a placa e tente novamente");
        }
        if("".equals(movimentacao.getVeiculo().getAno())){
            throw new RuntimeException("Verifique o ano do seu veiculo e tente novamente");
        }
        if(movimentacao.getVeiculo().getMarca().getNomeMarca()==null || movimentacao.getVeiculo().getMarca().getNomeMarca().isEmpty()){
            throw new RuntimeException("Verifique o nome da marca do seu veiculo e tente novamente");
        }
        if("".equals(movimentacao.getCondutor().getNomeCondutor())){
            throw new RuntimeException("Verifique o nome do Condutor e tente novamente");
        }
        if("".equals(movimentacao.getCondutor().getCpf())){
            throw new RuntimeException("Vefique o CPF do condutor e tente novamente");
        }
        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional
    public void attMovimentacao(final Long id, Movimentacao movimentacao){
        final Movimentacao movimentacaoBanco = this.movimentacaoRepository.findById(id).orElse(null);
        if(movimentacaoBanco==null || !movimentacaoBanco.getId().equals(movimentacao.getId())){
            throw new RuntimeException("Registro nao encontrado, verifique e tente novamente");
        }
        if(movimentacao.getVeiculo().getPlaca()==null || movimentacao.getVeiculo().getPlaca().isEmpty()){
            throw new RuntimeException("Verifique a placa e tente novamente");
        }
        if("".equals(movimentacao.getVeiculo().getAno())){
            throw new RuntimeException("Verifique o ano do seu veiculo e tente novamente");
        }
        if(movimentacao.getVeiculo().getMarca().getNomeMarca()==null || movimentacao.getVeiculo().getMarca().getNomeMarca().isEmpty()){
            throw new RuntimeException("Verifique o nome da marca do seu veiculo e tente novamente");
        }
        if("".equals(movimentacao.getCondutor().getNomeCondutor())){
            throw new RuntimeException("Verifique o nome do Condutor e tente novamente");
        }
        if("".equals(movimentacao.getCondutor().getCpf())){
            throw new RuntimeException("Vefique o CPF do condutor e tente novamente");
        }
        this.movimentacaoRepository.save(movimentacao);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteMovimentacao( @RequestParam("id") final Long id) {
        Movimentacao movimentacao = this.movimentacaoRepository.findById(id).orElse(null);
        if(movimentacaoRepository.idExistente(movimentacao.getId())){
            movimentacao.setAtivo(false);
            movimentacaoRepository.save(movimentacao);
        }else {
            movimentacaoRepository.delete(movimentacao);
        }
    }
}