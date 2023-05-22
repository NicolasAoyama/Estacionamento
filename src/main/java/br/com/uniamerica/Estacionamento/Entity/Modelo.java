package br.com.uniamerica.Estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "modelos", schema="public")
@Audited
@AuditTable(value = "modelo_audit",schema = "audit")
public class Modelo extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "nome_modelo")
    private String nomeModelo;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "modelo_marca",nullable = false)
    private Marca marca;


}