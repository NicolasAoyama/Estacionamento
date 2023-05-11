package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import br.com.uniamerica.Estacionamento.Entity.Marca;
import br.com.uniamerica.Estacionamento.repository.ConfiguracaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoService {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    @Transactional
    public void cadastraConfiguracao(Configuracao configuracao){
        if("".equals(configuracao.getVagasCarro()) || "".equals(configuracao.getVagasMoto())){
            throw new RuntimeException("Seu estacionamento nao pode conter 0 vagas, verifique e tente novamente");
        }
        if("".equals(configuracao.getValorHora())){
            throw new RuntimeException("O valor por hora do seu estacionamento nao pode ser 0, verifique e tente novamente");
        }
        this.configuracaoRepository.save(configuracao);
    }
    @Transactional
    public void attConfiguracao(final Long id, Configuracao configuracao){
        final Configuracao configuracaoBanco = this.configuracaoRepository.findById(id).orElse(null);
        if(configuracaoBanco==null || !configuracaoBanco.getId().equals(configuracao.getId())){
            throw new RuntimeException("Registro nao encontrado, verifique e tente novamente");
        }
        if("".equals(configuracao.getVagasCarro()) || "".equals(configuracao.getVagasMoto())){
            throw new RuntimeException("Seu estacionamento nao pode conter 0 vagas, verifique e tente novamente");
        }
        if("".equals(configuracao.getValorHora())){
            throw new RuntimeException("O valor por hora do seu estacionamento nao pode ser 0, verifique e tente novamente");
        }
        this.configuracaoRepository.save(configuracao);
    }
}