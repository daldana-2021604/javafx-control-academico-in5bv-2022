
package org.in5bv.dorbalaldana.kevinxulu.models;

/**
 * 
 *
 * @author Dorbal Emilio Aldana Ramos 2021604 y Kevin Josue Xulu Solis 2021348
 * @date 5/04/2022
 * @time 16:38:10
 * Código técnico: IN5BV
 * Grupo: 1 (Jueves)
 */
public class Salones {

    private String codigoSalon;
    private String descripcion;
    private int capacidadMaxima;
    private String edificio;
    private int nivel;
    private int cantidadDatos;

    public Salones() {
    }

    public Salones(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public Salones(String codigoSalon, int capacidadMaxima) {
        this.codigoSalon = codigoSalon;
        this.capacidadMaxima = capacidadMaxima;
    }

    public Salones(String codigoSalon, String descripcion, int capacidadMaxima, String edificio, int nivel) {
        this.codigoSalon = codigoSalon;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }

    public String getCodigoSalon() {
        return codigoSalon;
    }

    public void setCodigoSalon(String codigoSalon) {
        this.codigoSalon = codigoSalon;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getEdificio() {
        return edificio;
    }

    public void setEdificio(String edificio) {
        this.edificio = edificio;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getCantidadDatos() {
        return cantidadDatos;
    }

    public void setCantidadDatos(int cantidadDatos) {
        this.cantidadDatos = cantidadDatos;
    }

    @Override
    public String toString() {
        return codigoSalon + " | " + descripcion ;
    }
    
    
}
