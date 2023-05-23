package br.com.uniamerica.Estacionamento.Entity;
import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@MappedSuperclass

public abstract class AbstractEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;
    @Getter @Setter
    @Column(name = "dtCadastro", nullable = false)
    private LocalDateTime cadastro;
    @Getter @Setter
    @Column(name = "dtAtualizacao")
    private LocalDateTime atualizacao = LocalDateTime.now();;
    @Getter @Setter
    @Column(name = "ativo", nullable = false)
    boolean ativo;
    @PrePersist
    private void prePersist(){
        this.cadastro = LocalDateTime.now();
        this.ativo = true;
    }
    @PreUpdate
    private void preUpdate(){this.atualizacao = LocalDateTime.now();}
}