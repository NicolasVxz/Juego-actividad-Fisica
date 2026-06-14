/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;

/**
 *
 * @author NICOLAS
 */
public class Estudiante {
    
    private String nombre;
    private int puntaje;
    private Nivel nivel;
    private java.util.ArrayList<Insignia> insignias;
    
    
    public void agregarInsignia(Insignia i){
        insignias.add(i);
    }
    public java.util.ArrayList<Insignia> getInsignias(){
        return insignias;
    }
    public Estudiante (String nombre){
        this.nombre=nombre;
        this.puntaje=0;
        this.nivel = new Nivel();
        this.insignias = new java.util.ArrayList<>();
    }
    public void sumarPuntos (int puntos){
        puntaje += puntos;
        nivel.actualizarNivel(puntaje);
        for(int i = 2; i <= nivel.getNivel() && i <= 50; i++){
            boolean yaExiste = false;
            for(Insignia ins : insignias){
                if(ins.getNombre().equals("Nivel " + i)){
                    yaExiste = true;
                    break;
                }
            }
            if(!yaExiste){
                Insignia nueva = new Insignia("Nivel " + i,"Alcanzaste el nivel " + i);
                nueva.desbloquear();
                insignias.add(nueva);
            }
        }
            if(puntaje >= 20){
                 boolean yaExiste = false;
                 for(Insignia ins : insignias){
                     if(ins.getNombre().equals("Primer logro")){
                        yaExiste = true; 
                        break;
                     }
                 }
                 if(!yaExiste){
                     Insignia i = new Insignia("Primer logro","Completaste tu primera misión");
                     i.desbloquear();
                     insignias.add(i);
                 }
            } 
            if(puntaje >= 100){
                boolean yaExiste = false;
                for(Insignia ins : insignias){
                    if(ins.getNombre().equals("100 puntos")){
                        yaExiste = true;
                        break;
                    }
                }
                if(!yaExiste){
                    Insignia i = new Insignia("100 puntos","Llegaste a 100 puntos");
                    i.desbloquear();
                    insignias.add(i);
                }
            }
        
    }
    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getNivel() {
        return nivel.getNivel();
    }
    
public void setPuntaje(int puntaje){
    this.puntaje = puntaje;
}

}
