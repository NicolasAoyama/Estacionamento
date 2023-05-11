package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    List<Veiculo> findByAtivoTrue();
}
