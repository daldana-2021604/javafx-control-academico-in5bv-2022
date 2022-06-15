

package org.in5bv.dorbalaldana.kevinxulu.system;

import org.in5bv.dorbalaldana.kevinxulu.models.Alumnos;
/**
 *
 * @author USUARIO
 * @date 26/04/2022
 * @time 14:20:30
 * Carné 2021604
 * Código técnico: IN5BV
 * Grupo: 1
 */
public class TestAlumnos {
    public static void main(String[] args) {
        
        Alumnos alumno1 = new Alumnos();
        alumno1.setNombre1("Dorbal");
        alumno1.setNombre2("Emilio");
        
        System.out.println(alumno1.getNombre1());
        System.out.println("\n");
        
        Alumnos alumno2= new Alumnos("2021604", "Dorbal","Aldana");
        System.out.println(alumno2.getNombre1());
        
        System.out.println(alumno2);
    }
    
}
