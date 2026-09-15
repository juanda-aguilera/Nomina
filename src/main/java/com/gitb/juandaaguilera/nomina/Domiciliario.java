package com.gitb.juandaaguilera.nomina;

public class Domiciliario extends Empleado {

    private int entregasRealizadas;
    private double pagoPorEntrega;

    public Domiciliario(String nombre, int documento, double salarioBase, double pagoPorEntrega) {
        super(nombre, documento, salarioBase);
        this.entregasRealizadas = 0;
        this.pagoPorEntrega = (pagoPorEntrega > 0) ? pagoPorEntrega : 0;
    }

    public int getEntregasRealizadas() {
        return entregasRealizadas;
    }

    public void registrarEntrega() {
        this.entregasRealizadas++;
    }

    public double getPagoPorEntrega() {
        return pagoPorEntrega;
    }

    @Override
    public double pagoMensual() {
        return salarioBase + (entregasRealizadas * pagoPorEntrega) + calcularHorasExtras();
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("  Entregas realizadas: " + entregasRealizadas + " | Pago por entrega: " + pagoPorEntrega);
    }
}
