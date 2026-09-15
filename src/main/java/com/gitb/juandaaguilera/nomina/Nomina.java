package com.gitb.juandaaguilera.nomina;

import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Nomina {

    private static ArrayList<Empleado> nomina = new ArrayList<>();
    private static ArrayList<ProveedorExterno> proveedores = new ArrayList<>();

    public static void main(String[] args) {

        cargarDatosDemo();

        int opcion = 0;

        do {
            String menu = """
                ========================================
                       SISTEMA DE NOMINA
                ========================================

                1. Registrar domiciliario
                2. Registrar practicante
                3. Registrar gerente
                4. Registrar proveedor externo
                5. Registrar marcacion de horario (entrada/salida/descanso)
                6. Mostrar toda la nomina
                7. Calcular el total de la nomina del mes
                8. Mostrar proveedores externos y sus facturas
                9. Buscar empleado por documento (ver su pago del mes)
                10. Salir

                Seleccione una opcion:
                          """;

            opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opcion) {
                case 1 -> registrarDomiciliario();
                case 2 -> registrarPracticante();
                case 3 -> registrarGerente();
                case 4 -> registrarProveedorExterno();
                case 5 -> registrarMarcacion();
                case 6 -> mostrarNomina();
                case 7 -> calcularTotalNomina();
                case 8 -> mostrarProveedores();
                case 9 -> buscarEmpleadoPorDocumento();
                case 10 -> System.out.println("Fin del programa.");
                default -> System.out.println("Opcion invalida.");
            }

        } while (opcion != 10);
    }

    public static void cargarDatosDemo() {
        nomina.add(new Domiciliario("Kevin Diaz", 1004, 1_750_905, 3_500));
        nomina.add(new Practicante("Camila Ruiz", 1005, 1_750_905, 6_500));
        nomina.add(new Gerente("Jorge Mendez", 1006, 6_000_000, 6_000_000));

        Empleado responsable = nomina.get(2);
        ProveedorExterno proveedor = new ProveedorExterno("Suministros ABC S.A.S.", "900123456-1", responsable);
        proveedor.agregarFactura(new Factura("F-001", "Papeleria de oficina", 250_000));
        proveedor.agregarFactura(new Factura("F-002", "Mantenimiento de equipos", 480_000));
        proveedores.add(proveedor);

        System.out.println("Datos de ejemplo cargados: 3 empleados y 1 proveedor externo.");
    }

    public static void registrarDomiciliario() {
        String nombre = JOptionPane.showInputDialog("Nombre: ");
        int documento = Integer.parseInt(JOptionPane.showInputDialog("Documento: "));
        double salario = Double.parseDouble(JOptionPane.showInputDialog("Salario base: "));
        double pagoPorEntrega = Double.parseDouble(JOptionPane.showInputDialog("Pago por entrega: "));
        nomina.add(new Domiciliario(nombre, documento, salario, pagoPorEntrega));
        System.out.println("Domiciliario registrado.");
    }

    public static void registrarPracticante() {
        String nombre = JOptionPane.showInputDialog("Nombre: ");
        int documento = Integer.parseInt(JOptionPane.showInputDialog("Documento: "));
        double salario = Double.parseDouble(JOptionPane.showInputDialog("Salario base: "));
        double valorHora = Double.parseDouble(JOptionPane.showInputDialog("Valor por hora: "));
        nomina.add(new Practicante(nombre, documento, salario, valorHora));
        System.out.println("Practicante registrado (pago por horas trabajadas).");
    }

    public static void registrarGerente() {
        String nombre = JOptionPane.showInputDialog("Nombre: ");
        int documento = Integer.parseInt(JOptionPane.showInputDialog("Documento: "));
        double salario = Double.parseDouble(JOptionPane.showInputDialog("Salario base: "));
        double bonificacion = Double.parseDouble(JOptionPane.showInputDialog("Bonificacion anual: "));
        nomina.add(new Gerente(nombre, documento, salario, bonificacion));
        System.out.println("Gerente registrado.");
    }

    public static void registrarProveedorExterno() {
        if (nomina.isEmpty()) {
            System.out.println("Debe existir al menos un empleado para asignarlo como responsable.");
            return;
        }
        String nombreEmpresa = JOptionPane.showInputDialog("Nombre de la empresa proveedora: ");
        String nit = JOptionPane.showInputDialog("NIT: ");

        int indice = elegirDeLista("EMPLEADOS (elija el responsable interno)", descripcionesEmpleados());
        if (indice < 0 || indice >= nomina.size()) {
            System.out.println("Opcion invalida.");
            return;
        }
        Empleado responsable = nomina.get(indice);
        proveedores.add(new ProveedorExterno(nombreEmpresa, nit, responsable));
        System.out.println("Proveedor externo registrado (no pertenece a la nomina, no extiende Empleado).");
    }

    // Pide el documento, ubica al empleado y abre el submenu de marcacion
    public static void registrarMarcacion() {
        String textoDocumento = JOptionPane.showInputDialog("Ingrese el numero de documento del empleado: ");
        if (textoDocumento == null) {
            return;
        }
        int documento;
        try {
            documento = Integer.parseInt(textoDocumento.trim());
        } catch (NumberFormatException ex) {
            System.out.println("Documento invalido.");
            return;
        }

        Empleado empleado = buscarPorDocumento(documento);
        if (empleado == null) {
            System.out.println("No existe ningun empleado con el documento " + documento + ".");
            return;
        }

        String submenu = """
                ========================================
                 MARCACION DE HORARIO - %s
                ========================================

                1. Hora de ingreso
                2. Hora de salida
                3. Salida a descanso
                4. Entrada de descanso
                5. Registrar horas extra

                Seleccione una opcion:
                          """.formatted(empleado.getNombre());

        int opcion;
        try {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(submenu));
        } catch (NumberFormatException ex) {
            System.out.println("Opcion invalida.");
            return;
        }

        if (opcion == 5) {
            double horas = Double.parseDouble(JOptionPane.showInputDialog("Cantidad de horas extra: "));
            empleado.registrarHorasExtra(horas);
            System.out.println("Horas extra registradas para " + empleado.getNombre() + ".");
            return;
        }

        String tipo = switch (opcion) {
            case 1 -> "ENTRADA";
            case 2 -> "SALIDA";
            case 3 -> "SALIDA_DESCANSO";
            case 4 -> "ENTRADA_DESCANSO";
            default -> null;
        };

        if (tipo == null) {
            System.out.println("Opcion invalida.");
            return;
        }

        empleado.registrarMarcacion(tipo);
        System.out.println("Marcacion procesada para " + empleado.getNombre() + ".");
    }

    public static void mostrarNomina() {
        System.out.println(" ===== NOMINA COMPLETA ===== ");
        for (Empleado e : nomina) {
            e.mostrarInformacion();
            System.out.println("  ------------------------------");
        }
    }

    public static void calcularTotalNomina() {
        double total = 0;
        for (Empleado e : nomina) {
            total += e.pagoMensual();
        }
        System.out.println("Total de la nomina del mes: $" + total);
    }

    public static void mostrarProveedores() {
        System.out.println(" ===== PROVEEDORES EXTERNOS ===== ");
        if (proveedores.isEmpty()) {
            System.out.println("No hay proveedores registrados.");
            return;
        }
        for (ProveedorExterno p : proveedores) {
            p.mostrarInformacion();
            System.out.println("  ------------------------------");
        }
    }

    // Busca por documento y muestra el pago del mes
    public static void buscarEmpleadoPorDocumento() {
        String textoDocumento = JOptionPane.showInputDialog("Ingrese el numero de documento del empleado: ");
        if (textoDocumento == null) {
            return;
        }
        int documento;
        try {
            documento = Integer.parseInt(textoDocumento.trim());
        } catch (NumberFormatException ex) {
            System.out.println("Documento invalido.");
            return;
        }

        Empleado empleado = buscarPorDocumento(documento);
        if (empleado == null) {
            System.out.println("No existe ningun empleado con el documento " + documento + ".");
            return;
        }

        System.out.println(" ===== PAGO DEL MES ===== ");
        empleado.mostrarInformacion();
        System.out.println("  Pago del mes de " + empleado.getNombre() + ": $" + empleado.pagoMensual());
    }

    private static Empleado buscarPorDocumento(int documento) {
        for (Empleado e : nomina) {
            if (e.getDocumento() == documento) {
                return e;
            }
        }
        return null;
    }

    private static ArrayList<String> descripcionesEmpleados() {
        ArrayList<String> lista = new ArrayList<>();
        for (Empleado e : nomina) {
            lista.add(e.getNombre() + " (" + e.getClass().getSimpleName() + ")");
        }
        return lista;
    }

    private static int elegirDeLista(String titulo, ArrayList<String> opciones) {
        StringBuilder menu = new StringBuilder();
        menu.append(titulo).append("\n\n");
        for (int i = 0; i < opciones.size(); i++) {
            menu.append((i + 1)).append(". ").append(opciones.get(i)).append("\n");
        }
        menu.append("\nDigite el numero de la opcion: ");
        int seleccion = Integer.parseInt(JOptionPane.showInputDialog(menu.toString()));
        return seleccion - 1;
    }
}
