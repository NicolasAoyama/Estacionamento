package br.com.uniamerica.Estacionamento.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "Veiculos", schema="public")
public class Veiculo extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "placa",unique = true,length = 8)
    private String placa;
    @Getter
    @Setter
    @Column(name = "ano",length = 4)
    private int ano;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="Veiculo_marca",nullable = false)
    private Marca marca;
    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name="Veiculo_modelo",nullable = false)
    private Modelo modelo;
}

