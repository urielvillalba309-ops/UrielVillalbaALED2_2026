/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;


public class Libro {
    
    private int id;
    private String titulo;
    private String autor;
    private String categoria;
    private String editorial;
    private int stock;
    
    public Libro(int id,String titulo,String autor,String categoria,String editorial,int stock){
        this.id=id;
        this.titulo=titulo;
        this.autor=autor;
        this.categoria=categoria;
        this.editorial=editorial;
        this.stock=stock;
    }
    
    public Libro(String titulo,String autor,String categoria,String editorial,int stock){
        this(0,titulo,autor,categoria,editorial,stock);
    }
    
    public int getId() {return id;}
    public String getTitulo(){return titulo;}
    public String getAutor(){return autor;}
    public String getCategoria(){return categoria;}
    public String getEditorial(){return editorial;}
    public int getStock(){return stock;}

    public void setId(int id) {
        this.id = id;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
    
      
}
