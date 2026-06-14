/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;

/**
 *
 * @author NICOLAS
 */
public class Docente {
    private String nombre;
    
    public Docente (String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
    public void supervisarEstudiantes(){
        System.out.println("Supervisando estudiantes...");
    }
    public void administrarMisiones(){
        System.out.println("Administrando misiones...");
    }
    public void generarReportes(){
        System.out.println("Generando reportes...");
    }
}
