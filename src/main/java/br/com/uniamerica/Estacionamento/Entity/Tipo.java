package br.com.uniamerica.Estacionamento.Entity;
public enum Tipo {
    CARRO("Carro"),
    MOTO("Moto"),
    VAN("Van");

    private String Tipos;

    Tipo(String tipos){
        this.Tipos = tipos;
    }
    public String getTipos(){
        return Tipos;
    }
}
