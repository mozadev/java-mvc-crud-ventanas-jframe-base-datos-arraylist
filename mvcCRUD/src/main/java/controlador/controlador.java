package controlador;

import modelo.*;
import vista.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.stream.events.ProcessingInstruction;
import javax.xml.validation.SchemaFactoryLoader;

public class controlador implements ActionListener, KeyListener {

    int fila;
    Vista vistaCrud = new Vista();
    EmpleadoDAO modeloCrud = new EmpleadoDAO();

    public controlador(Vista vistaCRUD, EmpleadoDAO modeloCRU) {
        this.modeloCrud = modeloCRU;
        this.vistaCrud = vistaCRUD;
        this.vistaCrud.btnRegistrar.addActionListener(this);

        this.vistaCrud.btnListar.addActionListener(this);
        this.vistaCrud.btnModificar.addActionListener(this);
        this.vistaCrud.btnModificarOK.addActionListener(this);
        this.vistaCrud.btnEliminarMenorSueldo.addActionListener(this);
        this.vistaCrud.btnBuscar.addActionListener(this);
        this.vistaCrud.txtBUSCAR.addActionListener(this);

        this.vistaCrud.txtApellido.addKeyListener(this);
        this.vistaCrud.txtNombre.addKeyListener(this);
        this.vistaCrud.txtSueldo.addKeyListener(this);

    }

    public void InicializarCRUD() {
    }

    public void llenarTabla(JTable tablaD) {
        DefaultTableModel modeloT = new DefaultTableModel();

        tablaD.setModel(modeloT);
        modeloT.addColumn("APELLIDO");
        modeloT.addColumn("NOMBRE");
        modeloT.addColumn("SUELDO");
        Object[] columna = new Object[3];
        int numRegistros = modeloCrud.listEmpleados().size();
        for (int i = 0; i < numRegistros; i++) {
            columna[0] = modeloCrud.listEmpleados().get(i).getApellido();
            columna[1] = modeloCrud.listEmpleados().get(i).getNombre();
            columna[2] = modeloCrud.listEmpleados().get(i).getSueldo();
            modeloT.addRow(columna);

        }
    }

    public void llenarBusqueda(JTable tablaD) {
        String apellidos = vistaCrud.txtBUSCAR.getText();
        DefaultTableModel modeloA = new DefaultTableModel();

        vistaCrud.tabla.setModel(modeloA);
        modeloA.addColumn("APELLIDO");
        modeloA.addColumn("NOMBRE");
        modeloA.addColumn("SUELDO");
        Object[] columna = new Object[3];
        int numRegistros = modeloCrud.listEmpleados().size();
        for (int i = 0; i < 1; i++) {
            columna[0] = modeloCrud.BuscarEmpleadoPorApellido(apellidos).get(i).getApellido();
            columna[1] = modeloCrud.BuscarEmpleadoPorApellido(apellidos).get(i).getNombre();
            columna[2] = modeloCrud.BuscarEmpleadoPorApellido(apellidos).get(i).getSueldo();
            modeloA.addRow(columna);

        }
    }

    public void limpiarElementos() {
        vistaCrud.txtApellido.setText("");
        vistaCrud.txtApellido.setEditable(true);
        vistaCrud.txtNombre.setText("");
        vistaCrud.txtSueldo.setText("");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaCrud.btnRegistrar) {
            String apellido = vistaCrud.txtApellido.getText();
            String nombre = vistaCrud.txtNombre.getText();
            float sueldo = Float.parseFloat(vistaCrud.txtSueldo.getText());

            String rptaRegistro = modeloCrud.insertarEmpleado(apellido, nombre, sueldo);
            if (rptaRegistro != null) {
                JOptionPane.showMessageDialog(null, rptaRegistro);
            } else {
                JOptionPane.showMessageDialog(null, "registro erroneo");
            }
            limpiarElementos();
            llenarTabla(vistaCrud.tabla);

        }

        if (e.getSource() == vistaCrud.btnListar) {
            llenarTabla(vistaCrud.tabla);
        }

        if (e.getSource() == vistaCrud.btnModificar) {

            int filaEditar = vistaCrud.tabla.getSelectedRow();
            int numfilasSele = vistaCrud.tabla.getSelectedRowCount();
            if (filaEditar >= 0 && numfilasSele == 1) {
                vistaCrud.txtApellido.setText(String.valueOf(vistaCrud.tabla.getValueAt(filaEditar, 0)));
                vistaCrud.txtApellido.setEditable(false);
                vistaCrud.btnRegistrar.setEnabled(false);
                vistaCrud.btnModificar.setEnabled(false);
                vistaCrud.btnEliminarMenorSueldo.setEnabled(false);
                vistaCrud.btnBuscar.setEnabled(false);

            } else {
                JOptionPane.showMessageDialog(null, "debe selecionar una fila o al menos una");

            }

        }
        if (e.getSource() == vistaCrud.btnModificarOK) {
            int filaEditar = vistaCrud.tabla.getSelectedRow();
            String apellido = vistaCrud.txtApellido.getText();
            String nombre = vistaCrud.txtNombre.getText();
            float sueldo = Float.parseFloat(vistaCrud.txtSueldo.getText());
            int rptaEdit = modeloCrud.editarEmpleado(apellido, nombre, sueldo, filaEditar);

            if (rptaEdit > 0) {
                JOptionPane.showMessageDialog(null, "edicion exitosa");
            } else {
                JOptionPane.showMessageDialog(null, "no se pudo realizar la edicion     ");
            }
            limpiarElementos();
            vistaCrud.btnRegistrar.setEnabled(true);
            vistaCrud.btnListar.setEnabled(true);
            vistaCrud.btnModificar.setEnabled(true);
            vistaCrud.btnModificarOK.setEnabled(false);
            vistaCrud.btnBuscar.setEnabled(true);

            llenarTabla(vistaCrud.tabla);

        }
        int Posiciomenor = 0;
        if (e.getSource() == vistaCrud.btnEliminarMenorSueldo) {
            int filaInicio = vistaCrud.tabla.getSelectedRow();

            int numfilas = vistaCrud.tabla.getSelectedRowCount();
            ArrayList<String> listaApellido = new ArrayList();
            String apellido = "";
            if (filaInicio >= 0) {
                for (int i = 0; i < numfilas; i++) {
                    apellido = String.valueOf(vistaCrud.tabla.getValueAt(i + filaInicio, 0));

                    listaApellido.add(apellido);

                }

                float menor = 9999999;
                for (int i = 0; i < modeloCrud.listEmpleados().size(); i++) {
                    if (modeloCrud.listEmpleados().get(i).getSueldo() < menor) {
                        menor = modeloCrud.listEmpleados().get(i).getSueldo();
                        Posiciomenor = i;
                    }

                }

                for (int i = 0; i < numfilas; i++) {
                    int rptaUsuario = JOptionPane.showConfirmDialog(null, "quiere eliminar registro con" + apellido + "?");
                    if (rptaUsuario == 0) {
                        modeloCrud.eliminarEmpleado(Posiciomenor);
                    }
                }
                llenarTabla(vistaCrud.tabla);

            } else {
                JOptionPane.showMessageDialog(null, "seleccione al menos una fila a eliminar");
            }

        }
        if (e.getSource() == vistaCrud.btnBuscar) {
            String apellidos = vistaCrud.txtBUSCAR.getText();

            System.out.println("hola");

            DefaultTableModel modeloT = new DefaultTableModel();

            vistaCrud.tabla.setModel(modeloT);
            modeloT.addColumn("APELLIDO");
            modeloT.addColumn("NOMBRE");
            modeloT.addColumn("SUELDO");
            Object[] columna = new Object[3];

            int numRegistros = modeloCrud.BuscarEmpleadoPorApellido(apellidos).size();
            for (int i = 0; i < numRegistros; i++) {
                columna[0] = modeloCrud.BuscarEmpleadoPorApellido(apellidos).get(i).getApellido();
                columna[1] = modeloCrud.BuscarEmpleadoPorApellido(apellidos).get(i).getNombre();
                columna[2] = modeloCrud.BuscarEmpleadoPorApellido(apellidos).get(i).getSueldo();
                modeloT.addRow(columna);
            }
            llenarBusqueda(vistaCrud.tabla);

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() == vistaCrud.txtSueldo) {
            char c = e.getKeyChar();
            if (c < '0' || c > '9') {
                e.consume();
            }

        }

        if (e.getSource() == vistaCrud.txtApellido || e.getSource() == vistaCrud.txtNombre) {
            char c = e.getKeyChar();
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z') && (c != (char) KeyEvent.VK_SPACE)) {
                e.consume();
            }

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
