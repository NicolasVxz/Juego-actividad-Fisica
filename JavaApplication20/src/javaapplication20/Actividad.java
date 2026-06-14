/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;

/**
 *
 * @author NICOLAS
 */
public class Actividad {
      
private String nombre;
    private String descripcion;
    private int puntaje;
    private String video;
public Actividad(String nombre, String descripcion, int puntaje, String video) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.puntaje = puntaje;
    this.video = video;
}
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public int getPuntaje() {
        return puntaje;
    }
    public String getVideo(){
        return video;
    }
@Override
    public String toString() {
        return nombre + " - " + descripcion + " (" + puntaje + " pts)";
    }
}
