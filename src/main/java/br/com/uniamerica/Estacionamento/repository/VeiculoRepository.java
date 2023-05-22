package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByAtivoTrue();
    @Query(value = "select exists (select * from veiculos where id = :id)", nativeQuery = true)
    boolean idExistente(@Param("id") final Long id);
    @Query(value = "select exists (select * from movimentacoes where veiculo = :id)", nativeQuery = true)
    boolean veiculoExistente(@Param("id") final Long id);
}
