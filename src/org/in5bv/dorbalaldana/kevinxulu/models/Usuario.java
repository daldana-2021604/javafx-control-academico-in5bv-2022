
package org.in5bv.dorbalaldana.kevinxulu.models;

/**
 *
 * @author USUARIO
 * @date 19/07/2022
 * @time 14:30:42
 * Carné 2021604
 * Código técnico: IN5BV
 * Grupo: 1
 */
public class Usuario {
    private String user;
    private String pass;
    private String nombre;
    private int rolId;

    
    //Constructores
    public Usuario() {
    }

    public Usuario(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public Usuario(String user, String pass, String nombre, int rolId) {
        this.user = user;
        this.pass = pass;
        this.nombre = nombre;
        this.rolId = rolId;
    }
    
    
    
    
    // Getters and setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRol_id() {
        return rolId;
    }

    public void setRol_id(int rolId) {
        this.rolId = rolId;
    }
    
    // To string
    @Override
    public String toString() {
        return "Usuario{" + "user=" + user + ", pass=" + pass + ", nombre=" + nombre + ", rolId=" + rolId + '}';
    }
    
    
}
