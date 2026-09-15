package com.gitb.juandaaguilera.nomina;

// Clase abstracta base de todos los empleados
public abstract class Empleado {

    protected String nombre;
    protected int documento;
    protected double salarioBase;
    protected double horasExtra;

    // Todos los empleados registran su horario
    protected String horaIngreso;
    protected String horaSalida;
    protected String horaSalidaDescanso;
    protected String horaEntradaDescanso;

    public Empleado(String nombre, int documento, double salarioBase) {
        this.nombre = nombre;
        this.documento = documento;
        this.salarioBase = (salarioBase >= 0) ? salarioBase : 0;
        this.horasExtra = 0;
        this.horaIngreso = null;
        this.horaSalida = null;
        this.horaSalidaDescanso = null;
        this.horaEntradaDescanso = null;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDocumento() {
        return documento;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public double getHorasExtra() {
        return horasExtra;
    }

    public String getHoraIngreso() {
        return horaIngreso;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public String getHoraSalidaDescanso() {
        return horaSalidaDescanso;
    }

    public String getHoraEntradaDescanso() {
        return horaEntradaDescanso;
    }

    public void registrarHorasExtra(double horas) {
        if (horas > 0) {
            this.horasExtra += horas;
        }
    }

    // Valor hora extra: (salario base / 240) * 1.25, sobre el salario base
    public double calcularHorasExtras() {
        double valorHoraExtra = (salarioBase / 240.0) * 1.25;
        return horasExtra * valorHoraExtra;
    }

    public double calcularPrimaYBonificaciones() {
        return salarioBase / 12.0;
    }

    // Tipos: ENTRADA, SALIDA, SALIDA_DESCANSO, ENTRADA_DESCANSO
    public void registrarMarcacion(String tipoMarcacion) {
        switch (tipoMarcacion.toUpperCase()) {
            case "ENTRADA" -> horaIngreso = obtenerHoraActual();
            case "SALIDA" -> horaSalida = obtenerHoraActual();
            case "SALIDA_DESCANSO" -> horaSalidaDescanso = obtenerHoraActual();
            case "ENTRADA_DESCANSO" -> horaEntradaDescanso = obtenerHoraActual();
            default -> System.out.println("Tipo de marcacion no reconocido.");
        }
    }

    private String obtenerHoraActual() {
        return java.time.LocalTime.now().withNano(0).toString();
    }

    public abstract double pagoMensual();

    public void mostrarInformacion() {
        System.out.println("Nombre: " + nombre
                + " | Documento: " + documento
                + " | Salario base: " + salarioBase
                + " | Horas extra: " + horasExtra + " (+" + calcularHorasExtras() + ")"
                + " | Pago mensual: " + pagoMensual());
    }
}
