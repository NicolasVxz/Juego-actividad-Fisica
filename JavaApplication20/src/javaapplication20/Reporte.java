/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;

/**
 *
 * @author NICOLAS
 */
public class Reporte {
    public String generarReporte(Estudiante estudiante){
        String reporte = "===== REPORTE DEL ESTUDIANTE =====\n\n";
        reporte += "Nombre: " + estudiante.getNombre() + "\n";
        reporte += "Nivel: " + estudiante.getNivel() + "\n";
        reporte += "Puntaje: " + estudiante.getPuntaje() + "\n\n";
        reporte += "INSIGNIAS:\n";
        for(int i = 2; i <= estudiante.getNivel() && i <= 50; i++){
            reporte += "- Nivel " + i + " (Alcanzaste el nivel " + i + ")\n";
        }
        return reporte;
    }
}
