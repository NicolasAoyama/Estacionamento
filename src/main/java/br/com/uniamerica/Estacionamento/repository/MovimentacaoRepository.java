package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Configuracao;
import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByAtivoTrue();
    @Query(value = "select exists (select * from movimentacoes where id = :id)", nativeQuery = true)
    boolean idExistente(@Param("id") final Long id);
    @Query(value = "SELECT c FROM Configuracao c")
    Configuracao obterConfiguracao();

}
