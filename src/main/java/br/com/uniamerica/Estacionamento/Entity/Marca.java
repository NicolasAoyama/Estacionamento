package br.com.uniamerica.Estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "marcas", schema="public")
public class Marca extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "nomeMarca",nullable = false,unique = true)
    private String nomeMarca;
}
