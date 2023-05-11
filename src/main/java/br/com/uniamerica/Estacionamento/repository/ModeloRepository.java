package br.com.uniamerica.Estacionamento.repository;
import br.com.uniamerica.Estacionamento.Entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findByAtivoTrue();
}
