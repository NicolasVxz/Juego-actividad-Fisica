/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;
import java.util.List;

/**
 *
 * @author NICOLAS
 */
public class ValidacionLogin {
    
public static Usuario validar(String user, String pass){
        List<Usuario> lista = ArchivoUsuario.leerUsuarios();

        for(Usuario u : lista){
            if(u.getUsuario().equals(user) && u.getPassword().equals(pass)){
                return u;
            }
        }
        return null;
    }

}
