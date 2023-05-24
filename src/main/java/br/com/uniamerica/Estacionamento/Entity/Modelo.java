package br.com.uniamerica.Estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modelos", schema="public")
public class Modelo extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "nome")
    private String nomeModelo;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "marca",nullable = false)
    private Marca marca;


}