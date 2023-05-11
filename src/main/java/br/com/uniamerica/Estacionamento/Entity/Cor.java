package br.com.uniamerica.Estacionamento.Entity;

public enum Cor {
    AZUL("Azul"),
    PRETO("Pretp"),
    BRANCO("Branco"),
    VERMELHO("Vermelho");

    private String cores;
    Cor(String cores){this.cores = cores;}
    public String getCores(){return cores;}
}