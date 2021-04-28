package modelo;

public class Empleados {

    String apellido;
    String nombre;
    float sueldo;

    public Empleados() {

    }

    public Empleados(String apellido, String nombre, float sueldo) {
        this.apellido = apellido;
        this.nombre = nombre;
        this.sueldo = sueldo;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getSueldo() {
        return sueldo;
    }

    public void setSueldo(float sueldo) {
        this.sueldo = sueldo;
    }

}
