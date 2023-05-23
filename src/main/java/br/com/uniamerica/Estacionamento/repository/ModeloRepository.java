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

    @Query(value = "select exists (select * from modelos where id = :id)", nativeQuery = true)
    boolean idExistente(@Param("id") final Long id);

    @Query(value = "select exists (select * from modelos where condutor = :id)", nativeQuery = true)
    boolean modeloExistente(@Param("id") final Long id);
}
