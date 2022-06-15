

package org.in5bv.dorbalaldana.kevinxulu.controllers;


import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;
import com.jfoenix.controls.JFXDatePicker;
import java.sql.Date;
import java.time.LocalDate;
import javafx.event.Event;
import javafx.scene.control.ButtonType;
import org.in5bv.dorbalaldana.db.Conexion;
import org.in5bv.dorbalaldana.kevinxulu.models.Instructores;
import org.in5bv.dorbalaldana.kevinxulu.system.Principal;


/**
 *
 * @author Dorbal Emilio Aldana Ramos 2021604
 * @date 7/06/2022
 * @time 09:19:10
 * Carné 2021604
 * Código técnico: IN5BV
 * Grupo: 1
 */
public class InstructoresController implements Initializable {

    @FXML
    private Button btnNuevo;
    @FXML
    private ImageView imgNuevo;
    @FXML
    private Button btnModificar;
    @FXML
    private ImageView imgModificar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnReporte;
    @FXML
    private ImageView imgReporte;
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtNombre1;
    @FXML
    private TextField txtNombre2;
    @FXML
    private TextField txtNombre3;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtApellido2;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private JFXDatePicker txtfecha;
    @FXML
    private ImageView imgRegresar;
    @FXML
    private TableView<Instructores> tblInstructores;
    @FXML
    private TableColumn<Instructores, Integer> colId;
    @FXML
    private TableColumn<Instructores, String> colNombre1;
    @FXML
    private TableColumn<Instructores, String> colNombre2;
    @FXML
    private TableColumn<Instructores, String> colNombre3;
    @FXML
    private TableColumn<Instructores, String> colApellido1;
    @FXML
    private TableColumn<Instructores, String> colApellido2;
    @FXML
    private TableColumn<Instructores, String> colDireccion;
    @FXML
    private TableColumn<Instructores, String> colEmail;
    @FXML
    private TableColumn<Instructores, String> colTelefono;
    @FXML
    private TableColumn<Instructores, LocalDate> colFecha;
    
    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR;
    }

    private final String PAQUETE_IMAGES = "org/in5bv/dorbalaldana/kevinxulu/resources/images/";

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private ObservableList<Instructores> listaInstructores;

    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
     private ObservableList getInstructores() {
        ArrayList<Instructores> arrayListInstructores = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {

                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaNacimiento(rs.getDate(10).toLocalDate());

                System.out.println(instructor);

                arrayListInstructores.add(instructor);
            }

            listaInstructores = FXCollections.observableArrayList(arrayListInstructores);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Alumnos");
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

        return listaInstructores;
    }
    
    public void cargarDatos(){
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
    }
    
    private boolean existeElementoSeleccionado() {
        return ((tblInstructores.getSelectionModel().getSelectedItem()) != null);
    }
    
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            
            txtId.setText(String.valueOf(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getId()));
            txtNombre1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());
            txtDireccion.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());
            txtEmail.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getEmail());
            txtTelefono.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());
            txtfecha.setValue(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getFechaNacimiento());
        }

    }
    
    private void habilitarCampos() {
        txtId.setEditable(true);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);

        txtId.setDisable(false);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
        txtDireccion.setDisable(false);
        txtEmail.setDisable(false);
        txtTelefono.setDisable(false);
        txtfecha.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);
        
        txtId.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
        txtDireccion.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefono.setDisable(true);
        txtfecha.setDisable(true);

    }

    private void limpiarCampos() {
        /*txtCarne.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");*/
        txtId.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
        txtDireccion.clear();
        txtEmail.clear();
        txtTelefono.clear();
        txtfecha.setValue(null);
    }
    
    private boolean camposObligatoriosVacios() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Control Accademico");
        alerta.setHeaderText(null);
        if (txtNombre1.getText().isBlank()) {
            alerta.setContentText("El campo de primer nombre debe estar lleno");
            alerta.show();
            return true;
        }

        if (txtApellido1.getText().isBlank()) {
            alerta.setContentText("El campo de primer apellido debe estar lleno.");
            alerta.show();
            return true;
        }
        if (txtEmail.getText().isBlank()) {
            alerta.setContentText("El campo email debe estar lleno");
            alerta.show();
            return true;
        }
        if (txtTelefono.getText().isBlank()) {
            alerta.setContentText("El campo telefono debe estar lleno");
            alerta.show();
            return true;
        }
        if (txtfecha.getValue() == null) {
            alerta.setContentText("El campo fecha debe estar lleno");
            alerta.show();
            return true;
        }
        
        return false;
    }
    
    private boolean agregarInstructor() {
        Instructores instructor = new Instructores();
        //instructor.setId(Integer.parseInt(txtId.getText()));
        instructor.setNombre1(txtNombre1.getText());
        instructor.setNombre2(txtNombre2.getText());
        instructor.setNombre3(txtNombre3.getText());
        instructor.setApellido1(txtApellido1.getText());
        instructor.setApellido2(txtApellido2.getText());
        instructor.setDireccion(txtDireccion.getText());
        instructor.setEmail(txtEmail.getText());
        instructor.setTelefono(txtTelefono.getText());
        instructor.setFechaNacimiento(txtfecha.getValue());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_create(?,?,?,?,?,?,?,?,?)}");
            
            pstmt.setString(1, instructor.getNombre1());
            pstmt.setString(2, instructor.getNombre2());
            pstmt.setString(3, instructor.getNombre3());
            pstmt.setString(4, instructor.getApellido1());
            pstmt.setString(5, instructor.getApellido2());
            pstmt.setString(6,instructor.getDireccion());
            pstmt.setString(7, instructor.getEmail());
            pstmt.setString(8, instructor.getTelefono());
            pstmt.setDate(9, Date.valueOf(instructor.getFechaNacimiento()));

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaInstructores.add(instructor);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + instructor.toString());
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
        return false;
    }
    
    private boolean actualizarInstructores() {

        if (existeElementoSeleccionado()) {
            Instructores instructores = new Instructores();

            instructores.setId(Integer.parseInt(txtId.getText()));
            instructores.setNombre1(txtNombre1.getText());
            instructores.setNombre2(txtNombre2.getText());
            instructores.setNombre3(txtNombre3.getText());
            instructores.setApellido1(txtApellido1.getText());
            instructores.setApellido2(txtApellido2.getText());
            instructores.setDireccion(txtDireccion.getText());
            instructores.setEmail(txtEmail.getText());
            instructores.setTelefono(txtTelefono.getText());
            instructores.setFechaNacimiento(txtfecha.getValue());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_update(?,?,?,?,?,?,?,?,?,?)}");
                pstmt.setInt(1,instructores.getId());
                pstmt.setString(2, instructores.getNombre1());
                pstmt.setString(3, instructores.getNombre2());
                pstmt.setString(4, instructores.getNombre3());
                pstmt.setString(5, instructores.getApellido1());
                pstmt.setString(6, instructores.getApellido2());
                pstmt.setString(7,instructores.getDireccion());
                pstmt.setString(8, instructores.getEmail());
                pstmt.setString(9, instructores.getTelefono());
                pstmt.setDate(10, Date.valueOf(instructores.getFechaNacimiento()));
                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro:" + instructores.toString());
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
    
    public boolean eliminarInstructor() {
        if (existeElementoSeleccionado()) {
            Instructores instructor = (Instructores) tblInstructores.getSelectionModel().getSelectedItem();
            System.out.println(instructor);

            PreparedStatement pstmt = null;

            try {

                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_instructores_delete(?)}");
                System.out.println(pstmt.toString());
                
                pstmt.setInt(1, instructor.getId());
                System.out.println(pstmt);
                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + instructor.toString());
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

     @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                tblInstructores.setDisable(true);
                txtId.setDisable(true);

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
                    if (agregarInstructor()) {
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

                        tblInstructores.setDisable(false);

                        operacion = Operacion.NINGUNO;
                    }
                }
                
                break;

        }
    }

    @FXML
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);
                    txtId.setEditable(false);
                    txtId.setDisable(true);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "hola.png/"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png/"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
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

                tblInstructores.setDisable(false);
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
                    if (actualizarInstructores()) {
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
    private void cliclEliminar() {
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
                        if (eliminarInstructor()) {

                            listaInstructores.remove(tblInstructores.getSelectionModel().getFocusedIndex());
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
                        tblInstructores.getSelectionModel().clearSelection();
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
                
                tblInstructores.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
        }
    }

    @FXML
    private void clicReporte() {
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
        alerta.show();
    }

    
    @FXML
    void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    

}
