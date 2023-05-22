package br.com.uniamerica.Estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "marcas", schema="public")
@Audited
@AuditTable(value = "marca_audit", schema = "audit")
public class Marca extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "nome_marca",nullable = false,unique = true)
    private String nomeMarca;
}
