package br.com.uniamerica.Estacionamento.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "veiculos", schema="public")
public class Veiculo extends AbstractEntity{
    @Getter @Setter
    @Column(name = "placa",unique = true,length = 8)
    private String placa;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name="modelo",nullable = false)
    private Modelo modelo;
    @Getter @Setter
    @Column(name = "cor",length = 10,nullable = false)
    @Enumerated(EnumType.STRING)
    private Cor cor;
    @Getter @Setter
    @Column(name = "tipo",length = 10,nullable = false)
    @Enumerated(EnumType.STRING)
    private Tipo tipo;
    @Getter @Setter
    @Column(name = "ano",length = 4)
    private int ano;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name="marca",nullable = false)
    private Marca marca;


}

