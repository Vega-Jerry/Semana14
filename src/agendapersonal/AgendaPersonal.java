/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package agendapersonal;

/**
 *
 * @author Jerry Vega
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AgendaPersonal extends JFrame {

    private JTable tablaEventos;
    private DefaultTableModel modeloTabla;
    private JSpinner spinnerFecha;
    private JSpinner spinnerHora;
    private JTextField campoDescripcion;

    public AgendaPersonal() {
        setTitle("Agenda Personal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de entrada de datos
        JPanel panelEntrada = new JPanel();
        panelEntrada.setLayout(new GridLayout(3, 2));

        panelEntrada.add(new JLabel("Fecha:"));
        spinnerFecha = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorFecha = new JSpinner.DateEditor(spinnerFecha, "dd/MM/yyyy");
        spinnerFecha.setEditor(editorFecha);
        panelEntrada.add(spinnerFecha);

        panelEntrada.add(new JLabel("Hora:"));
        spinnerHora = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorHora = new JSpinner.DateEditor(spinnerHora, "HH:mm");
        spinnerHora.setEditor(editorHora);
        panelEntrada.add(spinnerHora);

        panelEntrada.add(new JLabel("Descripción:"));
        campoDescripcion = new JTextField();
        panelEntrada.add(campoDescripcion);

        add(panelEntrada, BorderLayout.NORTH);

        // Panel de lista de eventos
        JPanel panelLista = new JPanel();
        panelLista.setLayout(new BorderLayout());

        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Hora");
        modeloTabla.addColumn("Descripción");

        tablaEventos = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaEventos);
        panelLista.add(scrollPane, BorderLayout.CENTER);

        add(panelLista, BorderLayout.CENTER);

        // Panel de acciones
        JPanel panelAcciones = new JPanel();
        panelAcciones.setLayout(new FlowLayout());

        JButton botonAgregar = new JButton("Agregar");
        botonAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarEvento();
            }
        });
        panelAcciones.add(botonAgregar);

        JButton botonEliminar = new JButton("Eliminar seleccionado");
        botonEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarEvento();
            }
        });
        panelAcciones.add(botonEliminar);

        JButton botonSalir = new JButton("Salir");
        botonSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelAcciones.add(botonSalir);

        add(panelAcciones, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private void agregarEvento() {
        String descripcion = campoDescripcion.getText().trim();
        if (descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La descripción no puede estar vacía", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

        Date fecha = (Date) spinnerFecha.getValue();
        Date hora = (Date) spinnerHora.getValue();

        String fechaFormateada = formatoFecha.format(fecha);
        String horaFormateada = formatoHora.format(hora);

        modeloTabla.addRow(new Object[]{fechaFormateada, horaFormateada, descripcion});

        campoDescripcion.setText("");
        campoDescripcion.requestFocus();
    }

    private void eliminarEvento() {
        int filaSeleccionada = tablaEventos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un evento primero", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this, "¿Eliminar el evento seleccionado?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (respuesta == JOptionPane.YES_OPTION) {
            modeloTabla.removeRow(filaSeleccionada);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                AgendaPersonal agenda = new AgendaPersonal();
                agenda.setVisible(true);
            }
        });
    }
}