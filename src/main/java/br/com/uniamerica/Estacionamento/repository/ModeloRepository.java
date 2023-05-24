package br.com.uniamerica.Estacionamento.repository;
import br.com.uniamerica.Estacionamento.Entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findByAtivoTrue();

    @Query(value = "select exists (select * from modelos where nome = :nome)", nativeQuery = true)
    boolean existente(@Param("nome")  String nome);
    @Query(value = "select exists (select * from modelos where id = :id)", nativeQuery = true)
    boolean ProcuraId(@Param("id")  Long id);
    @Query(value = "select exist (select * from modelos where marca = :id", nativeQuery = true)
    boolean marcaExistente(@Param("id") Long id );
    /*@Query(value = "select exists (select * from veiculos where modelo = :id)", nativeQuery = true)
    boolean modeloExistente(@Param("id") final Long id);*/
    @Query(value = "select count(*)>0 from Veiculo veiculo where veiculo.modelo.id = :id")
    boolean modeloExistente(@Param("id") Long id);
}
