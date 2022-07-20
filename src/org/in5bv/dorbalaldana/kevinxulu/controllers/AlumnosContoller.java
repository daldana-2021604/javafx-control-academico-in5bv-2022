package org.in5bv.dorbalaldana.kevinxulu.controllers;

/**
 *
 *
 * @author Dorbal Emilio Aldana Ramos 2021604 y Kevin Josue Xulu Solis 2021348
 * @date 5/04/2022
 * @time 16:38:10 Código técnico: IN5BV Grupo: 1 (Jueves)
 */
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
import org.in5bv.dorbalaldana.kevinxulu.models.Alumnos;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import org.in5bv.dorbalaldana.db.Conexion;
import org.in5bv.dorbalaldana.kevinxulu.reports.GenerarReporte;

public class AlumnosContoller implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR;
    }

    private final String PAQUETE_IMAGES = "org/in5bv/dorbalaldana/kevinxulu/resources/images/";

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private ObservableList<Alumnos> listaAlumnos;

    @FXML
    private TextField txtCarne;

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
    private TextField txtCantidadDatos;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblAlumnos;

    @FXML
    private TableColumn colCarne;

    @FXML
    private TableColumn colNombre1;

    @FXML
    private TableColumn colNombre2;

    @FXML
    private TableColumn colNombre3;

    @FXML
    private TableColumn colApellido1;

    @FXML
    private TableColumn colApellido2;

    @FXML
    private ImageView imgRegresar;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    private int contador = 0;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // getAlumnos();
        cargarDatos();
    }

    public ObservableList getAlumnos() {
        ArrayList<Alumnos> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //stmt = Conexion.getInstance().getConexion().createStatement();
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_read()}");
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));
                System.out.println(alumno.toString());

                lista.add(alumno);

                for (int i = 0; i <= lista.size(); i++) {
                    contador = 0 + i;
                }

            }

            listaAlumnos = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Alumnos");
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
        return listaAlumnos;
    }

    public void cargarDatos() {
        Alumnos alumnos = new Alumnos();
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));
        alumnos.setCantidadDatos(contador);
        txtCantidadDatos.setText(Integer.toString(alumnos.getCantidadDatos()));
    }

    private boolean existeElementoSeleccionado() {
        return ((tblAlumnos.getSelectionModel().getSelectedItem()) != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtCarne.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());
            txtNombre1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        }

    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

    private void habilitarCampos() {
        txtCarne.setEditable(true);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);

        txtCarne.setDisable(false);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtCarne.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);

    }

    private void limpiarCampos() {
        /*txtCarne.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");*/
        txtCarne.clear();
        txtNombre1.clear();
        txtNombre2.clear();
        txtNombre3.clear();
        txtApellido1.clear();
        txtApellido2.clear();
    }

    private boolean camposObligatoriosVacios() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Control Accademico");
        alerta.setHeaderText(null);
        if (txtCarne.getText().isBlank()) {
            alerta.setContentText("El campo de carne debe estar lleno");
            alerta.show();
            return true;
        }

        if (txtNombre1.getText().isBlank()) {
            alerta.setContentText("El campo de nombre1 debe estar lleno.");
            alerta.show();
            return true;
        }
        if (txtApellido1.getText().isBlank()) {
            alerta.setContentText("El campo apellido1 debe estar lleno");
            alerta.show();
            return true;
        }

        return false;
    }

    public boolean eliminarAlumno() {
        if (existeElementoSeleccionado()) {
            Alumnos alumno = (Alumnos) tblAlumnos.getSelectionModel().getSelectedItem();
            System.out.println(alumno);

            PreparedStatement pstmt = null;

            try {

                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_delete(?)}");
                System.out.println(pstmt.toString());

                pstmt.setString(1, alumno.getCarne());
                System.out.println(pstmt);
                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + alumno.toString());
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

    private boolean actualizarAlumno() {

        if (existeElementoSeleccionado()) {
            Alumnos alumno = new Alumnos();

            alumno.setCarne(txtCarne.getText());
            alumno.setNombre1(txtNombre1.getText());
            alumno.setNombre2(txtNombre2.getText());
            alumno.setNombre3(txtNombre3.getText());
            alumno.setApellido1(txtApellido1.getText());
            alumno.setApellido2(txtApellido2.getText());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_update(?, ?, ?, ?, ? ,?)}");
                pstmt.setString(1, alumno.getCarne());
                pstmt.setString(2, alumno.getNombre1());
                pstmt.setString(3, alumno.getNombre2());
                pstmt.setString(4, alumno.getNombre3());
                pstmt.setString(5, alumno.getApellido1());
                pstmt.setString(6, alumno.getApellido2());
                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;
            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro:" + alumno.toString());
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

    private boolean agregarAlumno() {
        Alumnos alumno = new Alumnos();
        alumno.setCarne(txtCarne.getText());
        alumno.setNombre1(txtNombre1.getText());
        alumno.setNombre2(txtNombre2.getText());
        alumno.setNombre3(txtNombre3.getText());
        alumno.setApellido1(txtApellido1.getText());
        alumno.setApellido2(txtApellido2.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_alumnos_create(?,?,?,?,?,?)}");

            pstmt.setString(1, alumno.getCarne());
            pstmt.setString(2, alumno.getNombre1());
            pstmt.setString(3, alumno.getNombre2());
            pstmt.setString(4, alumno.getNombre3());
            pstmt.setString(5, alumno.getApellido1());
            pstmt.setString(6, alumno.getApellido2());

            System.out.println(pstmt.toString());

            pstmt.execute();

            listaAlumnos.add(alumno);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + alumno.toString());
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
                habilitarCampos();
                limpiarCampos();
                tblAlumnos.setDisable(true);

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
                if (camposObligatoriosVacios() == false) {
                    if (agregarAlumno()) {
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

                        tblAlumnos.setDisable(false);

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
                    txtCarne.setEditable(false);
                    txtCarne.setDisable(true);

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

                tblAlumnos.setDisable(false);
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
                if (camposObligatoriosVacios() == false) {
                    if (actualizarAlumno()) {
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

                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarAlumno()) {

                            listaAlumnos.remove(tblAlumnos.getSelectionModel().getFocusedIndex());
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
                    } else {
                        alert.close();
                        limpiarCampos();
                        tblAlumnos.getSelectionModel().clearSelection();
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

                tblAlumnos.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;
                break;
        }
    }

    @FXML
    private void clicReporte() {
        // Generacion del reporte.
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_ALUMNOS", PAQUETE_IMAGES + "estudiante-con-gorro-de-graduacion.png");
        GenerarReporte.getInstance().mostrarReporte("Alumnos.jasper", parametros, "Reporte de alumnos");

    }

    @FXML
    void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

}
