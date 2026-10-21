/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.LibroDAO;
import java.sql.SQLException;
import java.util.List;
import modelo.Libro;
import vista.VentanaLibros;

public class LibroController {

    private final VentanaLibros vista;
    private final LibroDAO dao;

    public LibroController(VentanaLibros vista, LibroDAO dao) {
        this.vista = vista;
        this.dao = dao;
    }

   
    private interface AccionBD {
        void ejecutar() throws SQLException;
    }

    

    public void iniciar() {
        ejecutarSeguro(() -> vista.mostrarLibros(dao.listar()));
    }

    public void agregar() {
        Libro libro = leerLibro(0);
        if (libro == null) return;
        ejecutarSeguro(() -> {
            dao.agregar(libro);
            refrescar();
        });
    }

    public void modificar() {
        int id = idSeleccionado();
        if (id == -1) return;
        Libro libro = leerLibro(id);
        if (libro == null) return;
        ejecutarSeguro(() -> {
            dao.modificar(libro);
            refrescar();
        });
    }

    public void eliminar() {
        int id = idSeleccionado();
        if (id == -1) return;
        if (!vista.confirmar("Seguro que desea eliminar este libro?")) return;
        ejecutarSeguro(() -> {
            dao.eliminar(id);
            refrescar();
        });
    }

    public void buscar() {
        String texto = vista.getBusqueda();
        ejecutarSeguro(() -> {
            List<Libro> libros = texto.isEmpty() ? dao.listar() : dao.buscar(texto);
            vista.mostrarLibros(libros);
            if (libros.isEmpty()) {
                vista.mostrarMensaje("No se encontraron libros");
            }
        });
    }

    
    
    private Libro leerLibro(int id) {
        String titulo = vista.getTitulo();
        String autor = vista.getAutor();
        String categoria = vista.getCategoria();
        String editorial = vista.getEditorial();
        String stockTexto = vista.getStock();

        if (titulo.isEmpty() || autor.isEmpty() || categoria.isEmpty()
                || editorial.isEmpty() || stockTexto.isEmpty()) {
            vista.mostrarMensaje("Complete todos los campos");
            return null;
        }

        int stock;
        try {
            stock = Integer.parseInt(stockTexto);
        } catch (NumberFormatException e) {
            vista.mostrarMensaje("El stock debe ser un numero entero");
            return null;
        }

        if (stock < 0) {
            vista.mostrarMensaje("El stock no puede ser negativo");
            return null;
        }

        return new Libro(id, titulo, autor, categoria, editorial, stock);
    }

    
    private int idSeleccionado() {
        int id = vista.getIdSeleccionado();
        if (id == -1) {
            vista.mostrarMensaje("Seleccione un libro de la tabla");
        }
        return id;
    }

    
    private void refrescar() throws SQLException {
        vista.mostrarLibros(dao.listar());
        vista.limpiarFormulario();
    }

    
    private void ejecutarSeguro(AccionBD accion) {
        try {
            accion.ejecutar();
        } catch (SQLException e) {
            vista.mostrarMensaje("Error en la base de datos: " + e.getMessage());
        }
    }
}
