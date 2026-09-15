package com.gitb.juandaaguilera.nomina;

public class Practicante extends Empleado {

    private double valorHora;
    private double horasTrabajadas;

    // El salario base no se paga como tal (se paga por horas), pero se
    // pide igual porque es la base para calcular sus horas extra.
    public Practicante(String nombre, int documento, double salarioBase, double valorHora) {
        super(nombre, documento, salarioBase);
        this.valorHora = (valorHora >= 0) ? valorHora : 0;
        this.horasTrabajadas = 0;
    }

    public double getValorHora() {
        return valorHora;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void registrarHorasTrabajadas(double horas) {
        if (horas > 0) {
            this.horasTrabajadas += horas;
        }
    }

    @Override
    public double pagoMensual() {
        return (horasTrabajadas * valorHora) + calcularHorasExtras();
    }

    @Override
    public void mostrarInformacion() {
        super.mostrarInformacion();
        System.out.println("  Horas trabajadas: " + horasTrabajadas + " | Valor hora: " + valorHora);
    }
}
