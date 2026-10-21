/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Libro;

public class LibroDAO {
    
    private static final String SELECT_LIBROS = 
            "SELECT id,titulo,autor,categoria,editorial,stock FROM libros";
    
    public List<Libro> listar() throws SQLException{
        return consultar(SELECT_LIBROS + " ORDER BY titulo");
    }
    
    public List<Libro> buscar(String texto) throws SQLException{
        String patron = "%" + texto + "%";
        return consultar(SELECT_LIBROS + " WHERE titulo LIKE ? OR autor LIKE ? ORDER BY titulo",
               patron, patron);
    }
    
    public void agregar(Libro libro) throws SQLException{
        ejecutar("INSERT INTO libros (titulo, autor, categoria, editorial, stock ) VALUES (?,?,?,?,?)",
        libro.getTitulo(),libro.getAutor(),libro.getCategoria(),libro.getEditorial(),libro.getStock());
    }
    
    public void modificar(Libro libro) throws SQLException{
        ejecutar("UPDATE libros SET titulo = ?, autor = ?,categoria = ?, editorial = ?,stock = ? WHERE id = ?",
                libro.getTitulo(),libro.getAutor(),libro.getCategoria(),libro.getEditorial(),libro.getStock(),libro.getId());
    }
    
    public void eliminar(int id) throws SQLException{
        ejecutar("DELETE FROM libros WHERE id = ?",id);
    }
    
    //METODOS PRIVADOS
    private List<Libro> consultar(String sql, Object... parametros) throws SQLException{
        List<Libro> libros = new ArrayList<>();
        try (Connection con = Conexion.conectar();
        PreparedStatement pst = con.prepareStatement(sql)){
            asignarParametros(pst, parametros);
            try (ResultSet rs = pst.executeQuery()){
                while(rs.next()){
                    libros.add(armarLibro(rs));
                }
            }
        }
        return libros;
    }
    
    private void ejecutar(String sql, Object... parametros) throws SQLException{
        try (Connection con = Conexion.conectar();
        PreparedStatement pst = con.prepareStatement(sql)){
            asignarParametros(pst, parametros);
            pst.executeUpdate();
        }
    }
    
    private void asignarParametros(PreparedStatement pst, Object... parametros) throws SQLException{
        for (int i = 0; i < parametros.length; i++){
            pst.setObject(i + 1, parametros[i]);
        }
    }
    
    private Libro armarLibro(ResultSet rs) throws SQLException{
        return new Libro(
          rs.getInt("id"),
          rs.getString("titulo"),
          rs.getString("autor"),
          rs.getString("categoria"),
          rs.getString("editorial"),
          rs.getInt("stock")
        );
    }
}
