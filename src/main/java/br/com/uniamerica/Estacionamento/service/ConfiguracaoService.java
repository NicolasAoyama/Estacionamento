package br.com.uniamerica.Estacionamento.service;

import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import br.com.uniamerica.Estacionamento.repository.ConfiguracaoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfiguracaoService {
    @Autowired
    private ConfiguracaoRepository configuracaoRepository;
    public Optional<Configuracao> procurarConfig(Long id){
        if (!configuracaoRepository.idExistente(id) ){
            throw new RuntimeException("ID nao encontrado, verifique e tente novamente");
        }else {
            Optional<Configuracao> configuracao = this.configuracaoRepository.findById(id);
            return configuracao;
        }
    }
    @Transactional
    public void cadastraConfiguracao(Configuracao configuracao){
        if("".equals(configuracao.getVagasCarro()) || "".equals(configuracao.getVagasMoto()) || "".equals(configuracao.getVagasVan())){
            throw new RuntimeException("Seu estacionamento nao pode conter 0 vagas, verifique e tente novamente");
        }
        if("".equals(configuracao.getValorHora())){
            throw new RuntimeException("O valor por hora do seu estacionamento nao pode ser 0, verifique e tente novamente");
        }
        this.configuracaoRepository.save(configuracao);
    }
    public Configuracao atualizarConfiguracao(Long id, Configuracao configuracaoAtualizada) {
        Configuracao configuracaoExistente = configuracaoRepository.findById(1L).orElse(null);
        if (configuracaoExistente == null) {
            return null;
        } else {
            configuracaoExistente.setValorHora(configuracaoAtualizada.getValorHora());
            configuracaoExistente.setValorMinutoMulta(configuracaoAtualizada.getValorMinutoMulta());
            configuracaoExistente.setInicioExpediente(configuracaoAtualizada.getInicioExpediente());
            configuracaoExistente.setFimExpediente(configuracaoAtualizada.getFimExpediente());
            configuracaoExistente.setTempoParaDesconto(configuracaoAtualizada.getTempoParaDesconto());
            configuracaoExistente.setTempoDeDesconto(configuracaoAtualizada.getTempoDeDesconto());
            configuracaoExistente.setGerarDesconto(configuracaoAtualizada.isGerarDesconto());
            configuracaoExistente.setVagasMoto(configuracaoAtualizada.getVagasMoto());
            configuracaoExistente.setVagasCarro(configuracaoAtualizada.getVagasCarro());
            configuracaoExistente.setVagasVan(configuracaoAtualizada.getVagasVan());
            return configuracaoRepository.save(configuracaoExistente);
        }
    }

}