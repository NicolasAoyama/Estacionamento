package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Movimentacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MovimentacaoRepository extends JpaRepository<Movimentacao, Long> {
    List<Movimentacao> findByAtivoTrue();
}
