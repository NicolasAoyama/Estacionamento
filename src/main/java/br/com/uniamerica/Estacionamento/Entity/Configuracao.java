package br.com.uniamerica.Estacionamento.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalTime;


@Entity
@Table(name = "configuracoes", schema="public")
public class Configuracao extends AbstractEntity{
    @Getter
    @Setter
    @Column(name = "valorhora",length = 2, nullable = false)
    private BigDecimal valorHora;
    @Getter @Setter
    @Column(name = "valorminutomulta",length = 2, nullable = false)
    private BigDecimal valorMinutoMulta;
    @Getter @Setter
    @Column(name = "inicioexpediente", nullable = false)
    private LocalTime inicioExpediente;
    @Getter @Setter
    @Column(name = "fimexpediente", nullable = false)
    private LocalTime fimExpediente;
    @Getter @Setter
    @Column(name = "tempoparadesconto",length = 2, nullable = false)
    private LocalTime tempoParaDesconto;
    @Getter @Setter
    @Column(name = "tempodedesconto",length = 2, nullable = false)
    private LocalTime tempoDeDesconto;
    @Getter @Setter
    @Column(name = "gerardesconto", nullable = false)
    private boolean gerarDesconto;
    @Getter @Setter
    @Column(name = "vagasmoto", nullable = false)
    private int vagasMoto;
    @Getter @Setter
    @Column(name = "vagascarro", nullable = false)
    private int vagasCarro;
    @Getter@Setter
    @Column(name = "vagasvan", nullable = false)
    private int vagasVan;
}
