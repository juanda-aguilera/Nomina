package com.gitb.juandaaguilera.nomina;

public class Factura {

    private String numero;
    private String descripcion;
    private double valor;

    public Factura(String numero, String descripcion, double valor) {
        this.numero = numero;
        this.descripcion = descripcion;
        this.valor = (valor > 0) ? valor : 0;
    }

    public String getNumero() {
        return numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getValor() {
        return valor;
    }

    public void mostrarInformacion() {
        System.out.println("  Factura " + numero + ": " + descripcion + " - $" + valor);
    }
}
