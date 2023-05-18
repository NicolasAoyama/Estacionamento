package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
    @Query(value = "select exists (select * from configuracoes where id = :id)", nativeQuery = true)
    boolean ProcuraConfiguracaoId(@Param("id") final Long id);
}
