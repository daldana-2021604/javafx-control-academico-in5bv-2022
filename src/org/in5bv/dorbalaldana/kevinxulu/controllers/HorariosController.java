package org.in5bv.dorbalaldana.kevinxulu.controllers;

import com.jfoenix.controls.JFXTimePicker;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.fxml.Initializable;

import java.sql.Time;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.in5bv.dorbalaldana.kevinxulu.system.Principal;

import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.in5bv.dorbalaldana.db.Conexion;

import org.in5bv.dorbalaldana.kevinxulu.models.Horarios;
import org.in5bv.dorbalaldana.kevinxulu.reports.GenerarReporte;

/**
 *
 * @author Kevin Josue Xulú Solis
 * @date 11/06/2022
 * @time 17:17:40 Carne: 2021348 Código tecnico: IN5BV Grupo: 1
 */
public class HorariosController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR;
    }
    private final String PAQUETE_IMAGES = "org/in5bv/dorbalaldana/kevinxulu/resources/images/";

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;
    private ObservableList<Horarios> listaHorarios;

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
    private CheckBox chbxLunes;

    @FXML
    private CheckBox chbxMartes;

    @FXML
    private CheckBox chbxMiercoles;

    @FXML
    private CheckBox chbxJueves;

    @FXML
    private CheckBox chbxViernes;

    @FXML
    private CheckBox chbxSabado;

    @FXML
    private CheckBox chbxDomingo;

    @FXML
    private JFXTimePicker tmpHoraEntrada;

    @FXML
    private JFXTimePicker tmpHoraFinal;

    @FXML
    private TextField txtId;

    @FXML
    private TableView tblHorario;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colHorarioEntrada;

    @FXML
    private TableColumn colHorarioSalida;

    @FXML
    private TableColumn colLunes;

    @FXML
    private TableColumn colMartes;

    @FXML
    private TableColumn colMiercoles;

    @FXML
    private TableColumn colJueves;

    @FXML
    private TableColumn colViernes;

    @FXML
    private ImageView imgRegresar;

    @FXML
    private TextField txtCantidadDatos;

    private int contador = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public ObservableList getHorarios() {
        ArrayList<Horarios> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //stmt = Conexion.getInstance().getConexion().createStatement();
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_horarios_read()");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Horarios horario = new Horarios();
                horario.setId(rs.getInt(1));
                horario.setHorarioInicio(rs.getTime(2).toLocalTime());
                horario.setHorarioFinal(rs.getTime(3).toLocalTime());
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));
                System.out.println(horario.toString());

                lista.add(horario);

                for (int i = 0; i <= lista.size(); i++) {
                    contador = 0 + i;
                }

            }

            listaHorarios = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Horarios");
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
        return listaHorarios;

    }

    public void cargarDatos() {
        Horarios horario = new Horarios();
        tblHorario.setItems(getHorarios());
        colId.setCellValueFactory(new PropertyValueFactory<Horarios, String>("id"));
        colHorarioEntrada.setCellValueFactory(new PropertyValueFactory<Horarios, String>("horarioInicio"));
        colHorarioSalida.setCellValueFactory(new PropertyValueFactory<Horarios, String>("horarioFinal"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horarios, String>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horarios, String>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horarios, String>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horarios, String>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horarios, String>("viernes"));
        horario.setCantidadDatos(contador);
        txtCantidadDatos.setText(Integer.toString(horario.getCantidadDatos()));
    }

    @FXML

    private boolean existeElementoSeleccionado() {
        return (tblHorario.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado() == true) {
            txtId.setText(String.valueOf(((Horarios) tblHorario.getSelectionModel().getSelectedItem()).getId()));
            tmpHoraEntrada.setValue(((Horarios) tblHorario.getSelectionModel().getSelectedItem()).getHorarioInicio());
            tmpHoraFinal.setValue(((Horarios) tblHorario.getSelectionModel().getSelectedItem()).getHorarioFinal());
            chbxLunes.setText(Boolean.toString(((Horarios) tblHorario.getSelectionModel().getSelectedItem()).isLunes()));
            chbxMartes.setText(Boolean.toString(((Horarios) tblHorario.getSelectionModel().getSelectedItem()).isMartes()));
            chbxMiercoles.setText(Boolean.toString(((Horarios) tblHorario.getSelectionModel().getSelectedItem()).isMiercoles()));
            chbxJueves.setText(Boolean.toString(((Horarios) tblHorario.getSelectionModel().getSelectedItem()).isJueves()));
            chbxViernes.setText(Boolean.toString(((Horarios) tblHorario.getSelectionModel().getSelectedItem()).isViernes()));

        }

    }

    private boolean eliminarHorario() {
        if (existeElementoSeleccionado()) {
            Horarios horario = (Horarios) tblHorario.getSelectionModel().getSelectedItem();
            System.out.println(horario);
            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_horarios_delete(?)");
                pstmt.setInt(1, horario.getId());
                System.out.println(pstmt.toString());
                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguente Registro: " + horario.toString());
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

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);

        txtId.setDisable(true);
        tmpHoraEntrada.setDisable(true);
        tmpHoraFinal.setDisable(true);
        chbxLunes.setSelected(true);
        chbxMartes.setSelected(true);
        chbxMiercoles.setSelected(true);
        chbxJueves.setSelected(true);
        chbxViernes.setSelected(true);

    }

    private void habilitarCampos() {
        txtId.setEditable(true);

        txtId.setDisable(false);
        tmpHoraEntrada.setDisable(false);
        tmpHoraFinal.setDisable(false);
        chbxLunes.setSelected(false);
        chbxMartes.setSelected(true);
        chbxMiercoles.setSelected(false);
        chbxJueves.setSelected(false);
        chbxViernes.setSelected(false);

    }

    private void limpiarCampos() {
        txtId.clear();
        tmpHoraEntrada.getEditor().clear();
        tmpHoraFinal.getEditor().clear();
        chbxLunes.setSelected(false);
        chbxMartes.setSelected(false);
        chbxMiercoles.setSelected(false);
        chbxJueves.setSelected(false);
        chbxViernes.setSelected(false);

    }

    private boolean agregarHorario() {
        Horarios horario = new Horarios();
        horario.setHorarioInicio(tmpHoraEntrada.getValue());
        horario.setHorarioFinal(tmpHoraFinal.getValue());
        horario.setLunes(chbxLunes.isSelected());
        horario.setMartes(chbxMartes.isSelected());
        horario.setMiercoles(chbxMiercoles.isSelected());
        horario.setJueves(chbxJueves.isSelected());
        horario.setViernes(chbxViernes.isSelected());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_create(?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setTime(1, Time.valueOf(horario.getHorarioInicio()));
            pstmt.setTime(2, Time.valueOf(horario.getHorarioFinal()));
            pstmt.setBoolean(3, horario.isLunes());
            pstmt.setBoolean(4, horario.isMartes());
            pstmt.setBoolean(5, horario.isMiercoles());
            pstmt.setBoolean(6, horario.isJueves());
            pstmt.setBoolean(7, horario.isViernes());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaHorarios.add(horario);

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + horario.toString());
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

    private boolean actualizarHorario() {
        Horarios horario = new Horarios();
        horario.setId(Integer.parseInt(txtId.getText()));
        horario.setHorarioInicio(tmpHoraEntrada.getValue());
        horario.setHorarioFinal(tmpHoraFinal.getValue());
        horario.setLunes(chbxLunes.isSelected());
        horario.setMartes(chbxMartes.isSelected());
        horario.setMiercoles(chbxMiercoles.isSelected());
        horario.setJueves(chbxJueves.isSelected());
        horario.setViernes(chbxViernes.isSelected());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_update(?, ?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setInt(1, horario.getId());
            pstmt.setTime(2, Time.valueOf(horario.getHorarioInicio()));
            pstmt.setTime(3, Time.valueOf(horario.getHorarioFinal()));
            pstmt.setBoolean(4, horario.isLunes());
            pstmt.setBoolean(5, horario.isMartes());
            pstmt.setBoolean(6, horario.isMiercoles());
            pstmt.setBoolean(7, horario.isJueves());
            pstmt.setBoolean(8, horario.isViernes());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaHorarios.add(horario);

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro: " + horario.toString());
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

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                limpiarCampos();
                habilitarCampos();
                tblHorario.setDisable(true);

                // Clave primaria para autoincrementables
                txtId.setEditable(false);
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
                if (agregarHorario()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    Alert information = new Alert(Alert.AlertType.INFORMATION);
                    information.setTitle("CONTROL ACADÉMICO");
                    information.setHeaderText(null);
                    information.setContentText("Registro Insertado Exitosamente");
                    Stage stageInformation = (Stage) information.getDialogPane().getScene().getWindow();
                    stageInformation.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                    information.show();

                    btnNuevo.setText("Nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGES + "boton-agregar.png/"));

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png/"));

                    btnEliminar.setVisible(true);
                    btnEliminar.setDisable(false);

                    btnReporte.setVisible(true);
                    btnReporte.setDisable(false);

                    tblHorario.setDisable(false);

                    operacion = Operacion.NINGUNO;
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
                habilitarCampos();
                limpiarCampos();

                tblHorario.setDisable(false);
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image(PAQUETE_IMAGES + "boton-agregar.png/"));

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png/"));

                btnEliminar.setVisible(true);
                btnEliminar.setDisable(false);

                btnReporte.setVisible(true);
                btnReporte.setDisable(false);

                deshabilitarCampos();

                operacion = Operacion.NINGUNO;
                break;

            case ACTUALIZAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarHorario()) {
                        limpiarCampos();
                        deshabilitarCampos();
                        cargarDatos();

                        Alert information = new Alert(Alert.AlertType.INFORMATION);
                        information.setTitle("CONTROL ACADÉMICO");
                        information.setHeaderText(null);
                        information.setContentText("Registro Actualizado Exitosamente");
                        Stage stageInformation = (Stage) information.getDialogPane().getScene().getWindow();
                        stageInformation.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                        information.show();

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
            case ACTUALIZAR:
                btnNuevo.setDisable(false);
                btnNuevo.setVisible(true);

                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png/"));

                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png/"));

                btnReporte.setDisable(false);
                btnReporte.setVisible(true);

                limpiarCampos();
                deshabilitarCampos();

                tblHorario.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;

                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Esta seguro que desea eliminar el registro seleccionado?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarHorario()) {
                            listaHorarios.remove(tblHorario.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();
                            Alert information = new Alert(Alert.AlertType.INFORMATION);
                            information.setTitle("CONTROL ACADÉMICO");
                            information.setHeaderText(null);
                            information.setContentText("Registro Eliminado ¡Exitosamente!");
                            Stage stageInformation = (Stage) information.getDialogPane().getScene().getWindow();
                            stageInformation.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                            information.show();

                        }
                    } else {
                        alert.close();
                        tblHorario.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }

                    if (eliminarHorario()) {
                        cargarDatos();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Académico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
                }

                break;

        }
    }

    @FXML
    private void clicReporte() {
        // Inicio de reporte para aplicacion no pro
        /*Alert alerta = new Alert(Alert.AlertType.INFORMATION);
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
        // Version pro
        Map<String, Object> parametros = new HashMap();
        parametros.put("LOGO_HORARIOS", PAQUETE_IMAGES + "tiempo.png");
        GenerarReporte.getInstance().mostrarReporte("Horarios.jasper", parametros, "Reporte Horarios");

    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

}
