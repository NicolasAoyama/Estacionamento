package br.com.uniamerica.Estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity
@Table(name = "marcas", schema="public")
public class Marca extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "nome",nullable = false,unique = true)
    private String nomeMarca;
}
