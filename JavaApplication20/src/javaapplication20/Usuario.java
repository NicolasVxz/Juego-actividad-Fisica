/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication20;

/**
 *
 * @author NICOLAS
 */
public class Usuario {
    private String usuario;
    private String password;
    private String rol;
    
    public Usuario (String usuario, String password, String rol){
        this.usuario = usuario;
        this.password = password;
        this.rol=rol;
    }
    public String getUsuario (){
        return usuario;
    }
    public String getPassword (){
        return password;
    }
    
public String getRol() {
        return rol;
    }
}
