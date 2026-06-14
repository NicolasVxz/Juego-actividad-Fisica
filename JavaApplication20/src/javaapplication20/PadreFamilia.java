/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;


/**
 *
 * @author NICOLAS
 */
public class PadreFamilia {
    private String nombre;
    private Estudiante estudiante;
    
    public PadreFamilia(String nombre, Estudiante estudiante) {
        this.nombre = nombre;
        this.estudiante = estudiante;
    }
    public String getNombre() {
        return nombre;
    }
    public Estudiante getEstudiante(){
        return estudiante;
    }
    public void verProgreso(Estudiante estudiante) {
        System.out.println("Alumno: " + estudiante.getNombre());
        System.out.println("Nivel: " + estudiante.getNivel());
        System.out.println("Puntaje: " + estudiante.getPuntaje());
    }
    public void verActividades() {
        System.out.println("Mostrando actividades realizadas...");
    }
}
