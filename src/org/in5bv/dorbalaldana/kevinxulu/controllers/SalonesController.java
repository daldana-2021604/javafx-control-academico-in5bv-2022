package org.in5bv.dorbalaldana.kevinxulu.controllers;

import javafx.fxml.Initializable;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.in5bv.dorbalaldana.db.Conexion;
import org.in5bv.dorbalaldana.kevinxulu.models.Alumnos;
import org.in5bv.dorbalaldana.kevinxulu.models.CarrerasTecnicas;
import org.in5bv.dorbalaldana.kevinxulu.models.Salones;
import org.in5bv.dorbalaldana.kevinxulu.reports.GenerarReporte;
import org.in5bv.dorbalaldana.kevinxulu.system.Principal;

/**
 *
 *
 * @author Dorbal Emilio Aldana Ramos 2021604 y Kevin Josue Xulu Solis 2021348
 * @date 5/04/2022
 * @time 16:38:10 Código técnico: IN5BV Grupo: 1 (Jueves)
 */
public class SalonesController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR;
    }
    private final String PAQUETE_IMAGES = "org/in5bv/dorbalaldana/kevinxulu/resources/images/";

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    private ObservableList<Salones> listaSalones;

    @FXML
    private TextField txtCod;

    @FXML
    private TextField txtDesc;

    @FXML
    private TextField txtEdificio;

    @FXML
    private TextField txtCapM;

    @FXML
    private TextField txtNivel;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblSalones;

    @FXML
    private TableColumn colCod;

    @FXML
    private TableColumn colDesc;

    @FXML
    private TableColumn colCapM;

    @FXML
    private TableColumn colEdificio;

    @FXML
    private TableColumn colNivel;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgRegresar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private TextField txtCantidadDatos;
    
    private int contador = 0;

     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //getSalones();
        cargarDatos();
    }

    public ObservableList getSalones() {
        ArrayList<Salones> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //stmt = Conexion.getInstance().getConexion().createStatement();
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_salones_read()");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));
                System.out.println(salon.toString());

                lista.add(salon);
                
                for (int i = 0; i <= lista.size(); i++){
                    contador = 0 + i;
                }

            }

            listaSalones = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de carreras tecnicas");
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
        return listaSalones;

    }

    public void cargarDatos() {
        Salones salon = new Salones();
        tblSalones.setItems(getSalones());
        colCod.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDesc.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapM.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
        salon.setCantidadDatos(contador);
        txtCantidadDatos.setText(Integer.toString(salon.getCantidadDatos()));
    }

    private boolean existeElementoSeleccionad() {
        return (tblSalones.getSelectionModel().getSelectedItem() != null);
    }

    public void seleccionarElemento() {
        if (existeElementoSeleccionad()) {
            txtCod.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            txtDesc.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
            txtCapM.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima()));
            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            txtNivel.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel()));
        }
    }

    private boolean eliminarSalon() {
        if (existeElementoSeleccionad()) {

            Salones salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();
            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_delete(?)}");
                pstmt.setString(1, salon.getCodigoSalon());

                System.out.println(pstmt);

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + salon.toString());
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
        txtCod.setEditable(false);
        txtDesc.setEditable(false);
        txtEdificio.setEditable(false);
        txtCapM.setEditable(false);
        txtNivel.setEditable(false);

        txtCod.setDisable(true);
        txtDesc.setDisable(true);
        txtEdificio.setDisable(true);
        txtCapM.setDisable(true);
        txtNivel.setDisable(true);
    }

    private void habilitarCampos() {
        txtCod.setEditable(true);
        txtDesc.setEditable(true);
        txtEdificio.setEditable(true);
        txtCapM.setEditable(true);
        txtNivel.setEditable(true);

        txtCod.setDisable(false);
        txtDesc.setDisable(false);
        txtEdificio.setDisable(false);
        txtCapM.setDisable(false);
        txtNivel.setDisable(false);

    }

    private void limpiarCampos() {
        txtCod.clear();
        txtDesc.clear();
        txtEdificio.clear();
        txtCapM.clear();
        txtNivel.clear();

    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();

                tblSalones.setDisable(true);

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
                if (agregarSalon()) {
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

                    tblSalones.setDisable(false);

                    operacion = Operacion.NINGUNO;

                }
                break;

        }

    }

    private boolean agregarSalon() {
        Salones salon = new Salones();
        salon.setCodigoSalon(txtCod.getText());
        salon.setDescripcion(txtDesc.getText());
        salon.setCapacidadMaxima(Integer.parseInt(txtCapM.getText()));
        salon.setEdificio(txtEdificio.getText());
        salon.setNivel(Integer.parseInt(txtCapM.getText()));

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_create(?, ?, ?, ?, ?)}");
            pstmt.setString(1, salon.getCodigoSalon());
            pstmt.setString(2, salon.getDescripcion());
            pstmt.setInt(3, salon.getCapacidadMaxima());
            pstmt.setString(4, salon.getEdificio());
            pstmt.setInt(5, salon.getNivel());

            System.out.println(pstmt.toString());
            pstmt.execute();
            listaSalones.add(salon);

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + salon.toString());
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
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionad()) {
                    habilitarCampos();

                    txtCod.setEditable(false);
                    txtCod.setVisible(true);

                    btnNuevo.setDisable(true);
                    btnNuevo.setVisible(false);

                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "hola.png/"));

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "cancelar.png/"));

                    btnReporte.setDisable(true);
                    btnReporte.setVisible(false);

                    operacion = Operacion.ACTUALIZAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, selecciona un registro");
                    alert.show();
                }

                break;

            case GUARDAR:

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

                tblSalones.setDisable(false);

                operacion = Operacion.NINGUNO;
                break;

            case ACTUALIZAR:
                if (actualizarSalon()) {
                    limpiarCampos();
                    deshabilitarCampos();
                    cargarDatos();

                    btnNuevo.setDisable(false);
                    btnNuevo.setVisible(true);

                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGES + "editar.png/"));

                    btnEliminar.setText("Eliminar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGES + "eliminar.png/"));

                    btnReporte.setDisable(false);
                    btnReporte.setVisible(true);

                    operacion = Operacion.NINGUNO;

                }
                break;

        }
    }

    private boolean actualizarSalon() {

        if (existeElementoSeleccionad()) {
            Salones salon = new Salones();
            salon.setCodigoSalon(txtCod.getText());
            salon.setDescripcion(txtDesc.getText());
            salon.setCapacidadMaxima(Integer.parseInt(txtCapM.getText()));
            salon.setEdificio(txtEdificio.getText());
            salon.setNivel(Integer.parseInt(txtNivel.getText()));

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_salones_update(?, ?, ?, ?, ?)}");
                pstmt.setString(1, salon.getCodigoSalon());
                pstmt.setString(2, salon.getDescripcion());
                pstmt.setInt(3, salon.getCapacidadMaxima());
                pstmt.setString(4, salon.getEdificio());
                pstmt.setInt(5, salon.getNivel());

                System.out.println(pstmt.toString());

                pstmt.execute();
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar actualizar el siguiente registro: " + salon.toString());
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

                tblSalones.getSelectionModel().clearSelection();

                operacion = Operacion.NINGUNO;

                break;
            case NINGUNO:
                if (existeElementoSeleccionad()) {

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("¿Esta seguro que desea eliminar el registro seleccionado?");
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.get().equals(ButtonType.OK)) {
                        if (eliminarSalon()) {
                            listaSalones.remove(tblSalones.getSelectionModel().getFocusedIndex());
                            limpiarCampos();
                            cargarDatos();
                        }
                    } else {
                        alert.close();
                        tblSalones.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }

                    if (eliminarSalon()) {
                        cargarDatos();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro");
                    alert.show();
                }

                break;

        }
    }

    @FXML
    private void clicReporte() {
        // Finaliza la version no pro
        /*
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("AVISO!");
        alerta.setHeaderText("");
        alerta.setContentText("Esta función solo esta disponible en la versión PRO");
        alerta.show();
         */
        // Inicia la version pro
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_SALONES", PAQUETE_IMAGES + "aula.png");
        GenerarReporte.getInstance().mostrarReporte("Salones.jasper", parametros, "Reporte de Salones");

    }

    @FXML
    void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

}
