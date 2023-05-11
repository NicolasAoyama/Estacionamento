package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Condutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CondutorRepository extends JpaRepository<Condutor, Long> {
    List<Condutor> findByAtivoTrue();
}
