
package org.in5bv.dorbalaldana.kevinxulu.controllers;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bv.dorbalaldana.kevinxulu.system.Principal;
import org.in5bv.dorbalaldana.db.Conexion;
import org.in5bv.dorbalaldana.kevinxulu.models.CarrerasTecnicas;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import org.in5bv.dorbalaldana.db.Conexion;
import org.in5bv.dorbalaldana.kevinxulu.models.Alumnos;
import org.in5bv.dorbalaldana.kevinxulu.reports.GenerarReporte;

/**
 * 
 *
 * @author Dorbal Emilio Aldana Ramos 2021604
 * @date 5/04/2022
 * @time 16:38:10
 * Código técnico: IN5BV
 * Grupo: 1 (Jueves)
 */
public class CarrerasTecnicasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    private enum Operacion{
        NINGUNO, GUARDAR, ACTUALIZAR;
    }
    
    private final String PAQUETE_IMAGES = "org/in5bv/dorbalaldana/kevinxulu/resources/images/";
    
    private Operacion operacion = Operacion.NINGUNO;
    
    private Principal escenarioPrincipal;
    
    private ObservableList<CarrerasTecnicas> listaCarrerasTecnicas;
    
    
    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TextField txtCodigoTecnico;

    @FXML
    private TextField txtCarrera;

    @FXML
    private TextField txtGrado;

    @FXML
    private TextField txtSeccion;

    @FXML
    private TextField txtJornada;

    
    @FXML
    private TableView tblCarrerasTecnicas;

    @FXML
    private TableColumn  colCodigo;

    @FXML
    private TableColumn colCarrera;

    @FXML
    private TableColumn colGrado;

    @FXML
    private TableColumn colSeccion;
    
    @FXML
    private TableColumn colJornada;
    
    @FXML
    private ImageView imgNuevo;
    
    @FXML
    private ImageView imgModificar;
    
    @FXML
    private ImageView imgEliminar;
    
    @FXML
    private ImageView imgReporte;
    
    @FXML
    private TextField txtCantidadDatos;
    
    private int contador = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    public ObservableList getCarrerasTecnicas(){
        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            //stmt = Conexion.getInstance().getConexion().createStatement();
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_read()");
            rs = pstmt.executeQuery();
            while(rs.next()){
                CarrerasTecnicas carreras = new CarrerasTecnicas();
                carreras.setCodigoTecnico(rs.getString(1));
                carreras.setCarrera(rs.getString(2));
                carreras.setGrado(rs.getString(3));
                carreras.setSeccion((rs.getString(4)));
                carreras.setJornada(rs.getString(5));
                System.out.println(carreras.toString());
                
               lista.add(carreras);
               
                for (int i = 0;i <= lista.size(); i++) {
                    contador = 0+i;
                }
                
            }
            
            listaCarrerasTecnicas = FXCollections.observableArrayList(lista);
            
        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de carreras tecnicas");
            System.err.println("Message: " + e.getMessage());
            System.err.println("Error code: " + e.getErrorCode());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            
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
        return listaCarrerasTecnicas;
    
    }
    
    public void cargarDatos(){
        CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCodigo.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        //colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String> ("jornada"));
        carreraTecnica.setCantidadDatos(contador);
        txtCantidadDatos.setText(Integer.toString(carreraTecnica.getCantidadDatos()));
    }
    
    private boolean existeElementoSeleccionado(){
        return ((tblCarrerasTecnicas.getSelectionModel().getSelectedItem())!= null);
    }
    
    @FXML
    public void seleccionarElemento(){
        if(existeElementoSeleccionado()){
            txtCodigoTecnico.setText(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtCarrera.setText(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera());
            txtGrado.setText(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());
            txtSeccion.setText(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion());
            txtJornada.setText(((CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());
        }

    }
    
   

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
    
    private void habilitarCampos(){
        txtCodigoTecnico.setEditable(true);
        txtCarrera.setEditable(true);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtJornada.setEditable(true);
        
        txtCodigoTecnico.setDisable(false);
        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);
        

    }
    
    private void deshabilitarCampos(){
        txtCodigoTecnico.setEditable(false);
        txtCarrera.setEditable(false);
        txtGrado.setEditable(false);
        txtSeccion.setEditable(false);
        txtJornada.setEditable(false);
        
        txtCodigoTecnico.setDisable(true);
        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtSeccion.setDisable(true);
        txtJornada.setDisable(true);
    }
    
    private void limpiarCampos(){
        /*txtCarne.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");*/
        
        txtCodigoTecnico.clear();
        txtCarrera.clear();
        txtGrado.clear();
        txtSeccion.clear();
        txtJornada.clear();

    }
    
    
    private boolean camposObligatoriosVacios() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Control Accademico");
        alerta.setHeaderText(null);
        if (txtCodigoTecnico.getText().isBlank()) {
            alerta.setContentText("El campo de Código Técnico debe estar lleno");
            alerta.show();
            return true;
        }

        if (txtCarrera.getText().isBlank()) {
            alerta.setContentText("El campo de Carrera debe estar lleno.");
            alerta.show();
            return true;
        }
        if (txtGrado.getText().isBlank()) {
            alerta.setContentText("El campo de Grado debe estar lleno.");
            alerta.show();
            return true;
        }
        if (txtSeccion.getText().isBlank()) {
            alerta.setContentText("El campo de Sección debe estar lleno.");
            alerta.show();
            return true;
        }
        if (txtJornada.getText().isBlank()) {
            alerta.setContentText("El Jornada debe estar lleno");
            alerta.show();
            return true;
        }
        return false;
    }
    
    public boolean eliminarCarrerasTecnicas(){
        if(existeElementoSeleccionado()){
            CarrerasTecnicas carrerasTecnicas = (CarrerasTecnicas)tblCarrerasTecnicas.getSelectionModel().getSelectedItem();
            System.out.println(carrerasTecnicas);
            
            PreparedStatement pstmt = null;
            
            try {
                
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_delete(?)");
                pstmt.setString(1,carrerasTecnicas.getCodigoTecnico());
                System.out.println(pstmt);
                pstmt.execute();
                return true;
                
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + carrerasTecnicas.toString());
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                try {
                    if(pstmt != null){
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    
    private boolean actualizarCarrerasTecnicas(){
        if(existeElementoSeleccionado()){
            CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
        
            carreraTecnica.setCodigoTecnico(txtCodigoTecnico.getText());
            carreraTecnica.setCarrera(txtCarrera.getText());
            carreraTecnica.setGrado(txtGrado.getText());
            carreraTecnica.setSeccion(txtSeccion.getText());
            carreraTecnica.setJornada(txtJornada.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_carreras_tecnicas_update(?,?,?,?,?)}");
                pstmt.setString(1,carreraTecnica.getCodigoTecnico());
                pstmt.setString(2, carreraTecnica.getCodigoTecnico());
                pstmt.setString(3, carreraTecnica.getGrado());
                pstmt.setString(4, carreraTecnica.getSeccion());
                pstmt.setString(5, carreraTecnica.getJornada());
                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro:" + carreraTecnica.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        
        return false;
    }
    
    private boolean agregarCarreraTecnica(){
        CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
        carreraTecnica.setCodigoTecnico(txtCodigoTecnico.getText());
        carreraTecnica.setCarrera(txtCarrera.getText());
        carreraTecnica.setGrado(txtGrado.getText());
        carreraTecnica.setSeccion(txtSeccion.getText());
        carreraTecnica.setJornada(txtJornada.getText());
        
        PreparedStatement pstmt = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_create(?,?,?,?,?)");
            
            pstmt.setString(1, carreraTecnica.getCodigoTecnico());
            pstmt.setString(2, carreraTecnica.getCarrera());
            pstmt.setString(3, carreraTecnica.getGrado());
            pstmt.setString(4, carreraTecnica.getSeccion());
            pstmt.setString(5, carreraTecnica.getJornada());
            
            System.out.println(pstmt.toString());
            
            pstmt.execute();
            
            listaCarrerasTecnicas.add(carreraTecnica);
            
            return true;
            
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + carreraTecnica.toString());
            e.printStackTrace();
            
        } catch(Exception e){
            e.printStackTrace();
            
        } finally {
            try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            
        }
        return false;
        
    }
    
    @FXML
    private void clicNuevo(){
        switch(operacion){
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                tblCarrerasTecnicas.setDisable(true);
                
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "hola.png/"));
                
                btnModificar.setText("Cancelar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png/"));
                
                //Desabilitar
                btnEliminar.setDisable(true);
                //Ocultar
                btnEliminar.setVisible(false);
                
                //Desabilitar
                btnReporte.setDisable(true);
                //Ocultar
                btnReporte.setVisible(false);
                
                operacion = Operacion.GUARDAR;
                
                
                break;
            case GUARDAR:
                
                if(camposObligatoriosVacios() == false){
                    if(agregarCarreraTecnica()){
                        tblCarrerasTecnicas.setDisable(false);
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarDatos();

                        btnNuevo.setText("Nuevo");
                        imgNuevo.setImage(new Image(PAQUETE_IMAGES + "boton-agregar.png/"));

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png/"));

                        btnEliminar.setVisible(true);
                        btnEliminar.setDisable(false);

                        btnReporte.setVisible(true);
                        btnReporte.setDisable(false);

                        operacion = Operacion.NINGUNO;
                    }                    
                }
                
                

                break;
                
                
        }
        
    }
    
    @FXML
    private void clicModificar(){
        switch (operacion) {
            case NINGUNO:
                if(existeElementoSeleccionado()){
                    habilitarCampos();
                
                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "hola.png/"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png/"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, selecciona un registro");
                    alert.show();
                }
                
                break;
            
            case GUARDAR: // cancelar inserción
                tblCarrerasTecnicas.setDisable(false);
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "boton-agregar.png/"));
                
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png/"));
                
                btnEliminar.setVisible(true);
                btnEliminar.setDisable(false);
                
                btnReporte.setVisible(true);
                btnReporte.setDisable(false);
                
                limpiarCampos();
                deshabilitarCampos();
                
                operacion = Operacion.NINGUNO;
                break;
            case ACTUALIZAR:
                if(camposObligatoriosVacios() == false){
                    if(actualizarCarrerasTecnicas()){
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarDatos();
                        btnNuevo.setVisible(true);
                        btnNuevo.setDisable(false);

                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png/"));

                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png/"));

                        btnReporte.setVisible(true);
                        btnReporte.setDisable(false);

                        operacion = Operacion.NINGUNO;
                    }
                }
                break;
            
        }
    }
    
    @FXML
    private void clicEliminar(){
        switch (operacion) {
            case NINGUNO:// Eliminar un registro
                if (existeElementoSeleccionado()) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Esta seguro que quiere eliminar el registro selecionado?");
                    
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                    
                    Optional<ButtonType> result = alert.showAndWait();
                    
                    if(result.get().equals(ButtonType.OK)){
                        if (eliminarCarrerasTecnicas()) {

                            listaCarrerasTecnicas.remove(tblCarrerasTecnicas.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();
                            Alert alertInformation = new Alert(Alert.AlertType.INFORMATION);
                            Stage stageInformation = (Stage) alertInformation.getDialogPane().getScene().getWindow();
                            stageInformation.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                            alertInformation.setTitle("Control Académico Kinal");
                            alertInformation.setHeaderText(null);
                            alertInformation.setContentText("Se elimino el registro exitosamente");
                            alertInformation.show();
                            
                            
                        }
                    }else {
                        alert.close();
                        limpiarCampos();
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();
                    }
                    

                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                }
                break;
                
            case ACTUALIZAR: // cancelar modificación
                btnNuevo.setVisible(true);
                btnNuevo.setDisable(false);
                
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png/"));
                
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png/"));
                
                btnReporte.setVisible(true);
                btnReporte.setDisable(false);
                
                limpiarCampos();
                deshabilitarCampos();
                tblCarrerasTecnicas.getSelectionModel().clearSelection();
                
                operacion = Operacion.NINGUNO;
                break;
        }
    }
    
    
    @FXML
    private void clicReporte(){
        // Inicio de la version no pro de la aplicacion:
        /*
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        // cambiar titulo del mensaje:
        alerta.setTitle("AVISO!");
        // cambiar hear:
        // alerta.setHeaderText("Control Académico kinal");
        // eliminar hear:
        // alerta.setHeaderText(null);
        // O
        alerta.setHeaderText("");
        // Contenido del mensaje:
        alerta.setContentText("Esta función solo esta disponible en la versión PRO");
        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
        alerta.show();*/
        
        // Inicio de la vesion pro de la aplicacion
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_CARRERAS", PAQUETE_IMAGES + "mecanico.png");
        GenerarReporte.getInstance().mostrarReporte("CarrerasTecnicas.jasper", parametros, "Reporte de Carreras Tecnicas");
    }
    
    @FXML
    void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    
}
