package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import br.com.uniamerica.Estacionamento.repository.MovimentacaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovimentacaoService {
    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

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
}