/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;

/**
 *
 * @author NICOLAS
 */
public class Mision {
    private Actividad actividad;
    private boolean completada;
    public Mision(Actividad actividad) {
        this.actividad = actividad;
        this.completada = false;
    }
    public void completar() {
        completada = true;
    }
    public Actividad getActividad() {
        return actividad;
    }
    public boolean isCompletada() {
        return completada;
    }
    
}
