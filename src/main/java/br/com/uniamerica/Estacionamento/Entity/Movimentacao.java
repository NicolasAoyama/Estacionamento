package br.com.uniamerica.Estacionamento.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "movimentacoes", schema="public")
public class Movimentacao extends AbstractEntity{
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "movimentacao_veiculo", nullable = false)
    private Veiculo veiculo;
    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "Movimentacao_condutor", nullable = false)
    private Condutor condutor;
    @Getter @Setter
    @Column(name = "entrada", nullable = false)
    private LocalDateTime entrada;
    @Getter @Setter
    @Column(name = "saida", nullable = false)
    private LocalDateTime saida;
    @Getter @Setter
    @Column(name = "tempoTotalHora")
    private  Integer tempoTotalHora;
    @Getter @Setter
    @Column(name = "tempoTotalMinuto")
    private int tempoTotalMinuto;
    @Getter @Setter
    @Column(name = "tempoMultaMinuto")
    private int tempoMultaMinuto;
    @Getter @Setter
    @Column(name = "tempoMultaHora")
    private int tempoMultaHora;
    @Getter @Setter
    @Column(name = "tempoDesconto")
    private LocalTime tempoDesconto;
    @Getter @Setter
    @Column(name = "valor_desconto")
    private LocalTime valorDesconto;
    @Getter @Setter
    @Column(name = "valor_hora", nullable = false,unique = true)
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valor_multa", nullable = false,unique = true)
    private BigDecimal valorMulta;
    @Getter @Setter
    @Column(name = "valor_total")
    private BigDecimal valorTotal;
    @Getter @Setter
    @Column(name = "valor_hora_multa", nullable = false,unique = true)
    private BigDecimal valorHoraMulta;
}
