package modelo;

import java.util.ArrayList;
import javax.swing.plaf.metal.MetalBorders;

public class EmpleadoDAO {

    ArrayList<Empleados> datos = new ArrayList<Empleados>();

    public EmpleadoDAO() {
    }

    public String insertarEmpleado(String apellido, String nombre, float sueldo) {
        String rptaRegistro = null;
        Empleados e = new Empleados(apellido, nombre, sueldo);
        datos.add(e);
        rptaRegistro = "exito";
        return rptaRegistro;

    }

    public int editarEmpleado(String apellido, String nombre, float sueldo, int filaeditar) {

        int numfila = 0;
        Empleados e = new Empleados(apellido, nombre, sueldo);

        datos.set(filaeditar, e);
        return 1;

    }

    public int eliminarEmpleado(int fila) {

        int numfila = 0;
        datos.remove(fila);

        return numfila;

    }
    int indice;

    public ArrayList<Empleados> BuscarEmpleadoPorApellido(String apellido) {

        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i).getApellido().equals(apellido)) {
                indice = i;
            }

        }
        ArrayList<Empleados> datos1 = new ArrayList<Empleados>();
        datos1.add(datos.get(indice));
        return datos1;
    }

    public ArrayList<Empleados> listEmpleados() {
        return datos;
    }
}
