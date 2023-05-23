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
    private int tempoDesconto;
    @Getter @Setter
    @Column(name = "valor_desconto")
    private BigDecimal valorDesconto;
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


    @Override
    public String toString(){

        return
                        ("############- Estacionamento Mercosul -######################\n") +
                        ("############- Obrigado por nos escolher! -###################\n") +
                        ("############-  RECIBO  -#####################################\n") +
                        ("Condutor: " + getCondutor().getNomeCondutor() + "############\n") +
                        ("Veiculo: " + "################################################\n" +
                        "PLACA: " + getVeiculo().getPlaca() +  "########################\n" +
                        "COR: "+ getVeiculo().getCor()  + "#############################\n" +
                        "TIPO: " + getVeiculo().getTipo() + "###########################\n" +
                        "ANO: " + getVeiculo().getAno() + "#############################\n" +
                        "MODELO: " + getVeiculo().getModelo().getNomeModelo() + "##########\n" +
                        "MARCA: " + getVeiculo().getModelo().getMarca().getNomeMarca()+"\n\n") +
                        ("Entrada: " + getEntrada() + "################################\n") +
                        ("Saida: " + getSaida() + "####################################\n") +
                        ("Tempo Total do Condutor: " + getCondutor().getTempoTotal() + "Horas" +"\n")+
                        ("Tempo de Desconto: " + getCondutor().getTempoDesconto() + "Horas" + "\n")+
                        ("Tempo Pago Acumulado: " + getCondutor().getTempoPago() + "Horas\n\n") +
                        ("############- CONTROLE DE TEMPO -###########################\n") +
                        ("Tempo Total Estacionado: " + getTempoTotalHora()+ " Horas\n" +
                        "Tempo Total Estacionado:" + getTempoTotalMinuto() + " Minutos\n") +
                        ("Tempo Excedido: " + getTempoMultaHora() + " Horas\n"+
                        "Tempo Excedido:"  + getTempoMultaMinuto() + " Minutos\n") +
                        ("Tempo De Desconto: " + getTempoDesconto() + " Horas\n\n") +
                        ("#########- PAGAMENTO -##########################################\n") +
                        ("Valor Por Hora: R$ " + getValorHora() + "\n") +
                        ("Multa por Minuto: R$ " + getValorHoraMulta() + "\n") +
                        ("Desconto: R$ " + "-" + getValorDesconto() + "\n") +
                        ("Valor a pagar de Multa: R$ " + getValorMulta() + "\n") +
                        ("Total sem Multa: R$ " + (getTempoTotalHora().intValue() - getTempoMultaHora() ) * getValorHora().intValue() + "\n") +
                        ("Valor Total: R$ " + getValorTotal()  + "\n");

    }}
