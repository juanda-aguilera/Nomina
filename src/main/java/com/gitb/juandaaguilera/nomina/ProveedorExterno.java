package com.gitb.juandaaguilera.nomina;

import java.util.ArrayList;

// No extiende Empleado: es composicion, no herencia
public class ProveedorExterno {

    private String nombreEmpresa;
    private String nit;
    private Empleado empleadoResponsable;
    private ArrayList<Factura> facturas;

    public ProveedorExterno(String nombreEmpresa, String nit, Empleado empleadoResponsable) {
        this.nombreEmpresa = nombreEmpresa;
        this.nit = nit;
        this.empleadoResponsable = empleadoResponsable;
        this.facturas = new ArrayList<>();
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public String getNit() {
        return nit;
    }

    public Empleado getEmpleadoResponsable() {
        return empleadoResponsable;
    }

    public void agregarFactura(Factura factura) {
        facturas.add(factura);
    }

    public ArrayList<Factura> getFacturas() {
        return facturas;
    }

    public double calcularTotalFacturado() {
        double total = 0;
        for (Factura f : facturas) {
            total += f.getValor();
        }
        return total;
    }

    public void mostrarInformacion() {
        System.out.println("Proveedor: " + nombreEmpresa + " (NIT " + nit + ")"
                + " | Responsable interno: " + empleadoResponsable.getNombre());
        if (facturas.isEmpty()) {
            System.out.println("  (sin facturas registradas)");
        } else {
            for (Factura f : facturas) {
                f.mostrarInformacion();
            }
        }
        System.out.println("  Total facturado: $" + calcularTotalFacturado());
    }
}
