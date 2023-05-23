package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import br.com.uniamerica.Estacionamento.Entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
    List<Marca> findByAtivoTrue();
    @Query(value = "select exists (select * from marcas where id = :id)", nativeQuery = true)
    boolean idExistente(@Param("id") final Long id);
    @Query(value = "select exists (select * from marcas where nome = :nome)", nativeQuery = true)
    boolean MarcaRepetida(@Param("nome") final String nome);
    @Query(value = "select exists (select * from marcas where condutor = :id)", nativeQuery = true)
    boolean marcaExistente(@Param("id") final Long id);
}
