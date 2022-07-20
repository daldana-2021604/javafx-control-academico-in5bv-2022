package org.in5bv.dorbalaldana.kevinxulu.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bv.dorbalaldana.db.Conexion;
import org.in5bv.dorbalaldana.kevinxulu.models.Usuario;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.in5bv.dorbalaldana.kevinxulu.system.Principal;
import org.in5bv.dorbalaldana.kevinxulu.controllers.MenuPrincipalController;

/**
 * FXML Controller class
 *
 * @author USUARIO
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtContraseña;

    private Principal escenarioPrincipal;

    private final String PAQUETE_IMAGES = "org/in5bv/dorbalaldana/kevinxulu/resources/images/";


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
    private void btnIngresar(ActionEvent event) {
        if (existeUsuario()) {
            if (usuarioTipo() == 1) {
                escenarioPrincipal.mostrarEscenaPrincipal();
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Información");
                alerta.setHeaderText(null);
                alerta.setContentText("Bienvenido administrador");
                Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
                stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                alerta.show();
            }
            if (usuarioTipo() == 2) {
                MenuPrincipalController menu = new MenuPrincipalController();
                escenarioPrincipal.mostrarEscenaPrincipal();
                Alert alerta = new Alert(Alert.AlertType.INFORMATION);
                alerta.setTitle("Información");
                alerta.setHeaderText(null);
                alerta.setContentText("Bienvenido");
                Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
                stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                alerta.show();
            }

        }

    }
    
    public int usuarioTipo(){
        if (buscarUsuario(txtUsuario.getText(), txtContraseña.getText()).getRol_id() == 1){
            return 1;
        }
        if (buscarUsuario(txtUsuario.getText(), txtContraseña.getText()).getRol_id() == 2) {
            return 2;
        }
        return 0;
    }

    public boolean existeUsuario() {
        if (camposLlenos() == true) {
            if (buscarUsuario(txtUsuario.getText(), txtContraseña.getText()) != null) {
                return true;
            }
            Alert alerta = new Alert(Alert.AlertType.ERROR);
            alerta.setTitle("Control Accademico");
            alerta.setHeaderText(null);
            alerta.setContentText("Sus datos son incorrectos");
            alerta.show();
        }
        return false;
    }

    public boolean camposLlenos() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Control Accademico");
        alerta.setHeaderText(null);
        if (txtUsuario.getText().isBlank()) {
            alerta.setContentText("El campo de usuario debe estar lleno");
            alerta.show();
            return false;
        }
        if (txtContraseña.getText().isBlank()) {
            alerta.setContentText("El campo de contraseña debe estar lleno");
            alerta.show();
            return false;
        }
        return true;
    }
    

    private Usuario buscarUsuario(String user, String password) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Usuario usuario = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_usuario_read(?,?)}");
            pstmt.setString(1, user);
            pstmt.setString(2, password);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                usuario = new Usuario();
                usuario.setUser(rs.getString("user"));
                usuario.setPass(rs.getString("pass"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setRol_id(rs.getInt("rol_id"));

                System.out.println(usuario.toString());
            }
            

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Cursos");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return usuario;
    }

    
}
