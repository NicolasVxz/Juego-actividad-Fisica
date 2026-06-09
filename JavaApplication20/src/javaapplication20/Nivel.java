/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;

/**
 *
 * @author NICOLAS
 */
public class Nivel {
    private int nivel;
    public Nivel (){
        this.nivel = 1;
    }
    public void actualizarNivel(int puntaje) {
        nivel = (puntaje / 50) + 1;
        if(nivel > 50){
            nivel = 50;
        }
    }
    public int getNivel() {
        return nivel;
    }
    
    }  

