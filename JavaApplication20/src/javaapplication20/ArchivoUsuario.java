/*

 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author NICOLAS
 */
public class ArchivoUsuario {
    
    

public static List<Usuario> leerUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(
               new FileReader("usuarios.txt")
            );
            String linea;
            while((linea = br.readLine()) != null){
                String[] datos = linea.split(",");
           
                if(datos.length >= 3){
                    lista.add(new Usuario(datos[0], datos[1], datos[2]));
                }
            }
            br.close();
        } catch (Exception e) {
            System.out.println("Error al leer archivo usuarios.txt");
        }
        return lista;
    }   
}
