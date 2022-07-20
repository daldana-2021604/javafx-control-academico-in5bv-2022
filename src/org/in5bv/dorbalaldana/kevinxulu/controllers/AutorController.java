
package org.in5bv.dorbalaldana.kevinxulu.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bv.dorbalaldana.kevinxulu.system.Principal;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class AutorController implements Initializable {
    private Principal escenarioPrincipal;

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    void clicRegresar(){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
}
