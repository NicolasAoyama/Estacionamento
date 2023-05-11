package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
}
