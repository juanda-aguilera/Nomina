package com.gitb.juandaaguilera.nomina;

public class Gerente extends Empleado {

    private double bonificacionAnual;

    public Gerente(String nombre, int documento, double salarioBase, double bonificacionAnual) {
        super(nombre, documento, salarioBase);
        this.bonificacionAnual = (bonificacionAnual >= 0) ? bonificacionAnual : 0;
    }

    public double getBonificacionAnual() {
        return bonificacionAnual;
    }

    @Override
    public double pagoMensual() {
        return salarioBase + (bonificacionAnual / 12.0) + calcularHorasExtras();
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("  Bonificacion anual: " + bonificacionAnual + " (mensualizada: " + (bonificacionAnual / 12.0) + ")");
    }
}
