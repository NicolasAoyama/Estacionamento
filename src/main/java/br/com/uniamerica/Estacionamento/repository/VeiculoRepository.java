package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByAtivoTrue();
    @Query(value = "select exists (select * from veiculos where id = :id)", nativeQuery = true)
    boolean idExistente(@Param("id") final Long id);
    @Query(value = "select exists (select * from movimentacoes where veiculo = :id)", nativeQuery = true)
    boolean veiculoExistente(@Param("id") final Long id);
    @Query(value = "select veiculo.id from Veiculo veiculo where veiculo.placa = :placa")
    Long placaExistente(@Param("placa") String placa);

    @Query(value = "select exists (select * from veiculos where placa = :placa)", nativeQuery = true)
    boolean ProcuraPlaca(@Param("placa") final String placa);


}
