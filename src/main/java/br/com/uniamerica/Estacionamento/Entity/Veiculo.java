package br.com.uniamerica.Estacionamento.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "veiculos", schema="public")
public class Veiculo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "placa",unique = true,length = 8)
    private String placa;
    @Getter @Setter
    @Column(name = "cor",length = 10,nullable = false)
    @Enumerated(EnumType.STRING)
    private Cor cor;
    @Getter @Setter
    @Column(name = "ano",length = 4)
    private int ano;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name="veiculo_modelo",nullable = false)
    private Modelo modelo;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name="veiculo_marca",nullable = false)
    private Marca marca;
    @Getter @Setter
    @Column(name = "tipo",length = 10,nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;

}

