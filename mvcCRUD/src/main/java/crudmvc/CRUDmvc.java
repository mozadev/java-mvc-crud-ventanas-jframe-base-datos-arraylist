package crudmvc;

import modelo.*;
import vista.*;
import controlador.*;

public class CRUDmvc {

    public static void main(String[] args) {
        Vista vistac = new Vista();
        EmpleadoDAO modeloC = new EmpleadoDAO();
        controlador contolaC = new controlador(vistac, modeloC);
        vistac.setVisible(true);
        vistac.setLocationRelativeTo(null);
    }
}
