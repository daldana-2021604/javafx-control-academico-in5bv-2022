
package org.in5bv.dorbalaldana.kevinxulu.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bv.dorbalaldana.kevinxulu.system.Principal;

/**
 * 
 *
 * @author Dorbal Emilio Aldana Ramos 2021604 y Kevin Josue Xulu Solis 2021348
 * @date 5/04/2022
 * @time 16:38:10
 * Código técnico: IN5BV
 * Grupo: 1 (Jueves)
 */
public class MenuPrincipalController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    void clicAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAlumnos();
    }

    @FXML
    void clicCarrerasTecnicas(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }
    
    @FXML
    void clicSalones(ActionEvent event){
        escenarioPrincipal.mostrarEscenaSalones();
    }
    
    @FXML
    void clicCursos(ActionEvent event){
        escenarioPrincipal.mostrarEscenaCursos();
    }
    
    @FXML
    void clicInstructores(ActionEvent event){
        escenarioPrincipal.mostrarEscenaInstructores();
    }
    
    @FXML
    void clicAsignaciones(ActionEvent event){
        escenarioPrincipal.mostrarEscenaAsigacion();
    }
    
    @FXML
    void clicHorarios(ActionEvent event){
        escenarioPrincipal.mostrarEscenaHorarios();
    }
    
    
}
