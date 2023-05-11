package br.com.uniamerica.Estacionamento.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalTime;

@Entity
@Table(name = "condutores", schema="public")
public class Condutor extends AbstractEntity{
    @Getter @Setter
    @Column(name = "nome",nullable = false,length = 100)
    private String nomeCondutor;
    @Getter @Setter
    @Column(name = "cpf",nullable = false,unique = true)
    private String cpf;
    @Getter @Setter
    @Column(name = "telefone",nullable = false,length = 17)
    private String telefone;
    @Getter @Setter
    @Column(name = "tempo_pago")
    private LocalTime tempoPago;
    @Getter @Setter
    @Column(name = "tempo_desconto")
    private LocalTime tempoDesconto;

   }
