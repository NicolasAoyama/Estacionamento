package br.com.uniamerica.Estacionamento.repository;

import br.com.uniamerica.Estacionamento.Entity.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
