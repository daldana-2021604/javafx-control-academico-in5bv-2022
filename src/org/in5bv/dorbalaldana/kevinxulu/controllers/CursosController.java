
package org.in5bv.dorbalaldana.kevinxulu.controllers;

import com.mysql.cj.jdbc.result.ResultSetFactory;
import com.mysql.cj.result.ValueFactory;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.scene.control.cell.PropertyValueFactory;


import org.in5bv.dorbalaldana.db.Conexion;
import org.in5bv.dorbalaldana.kevinxulu.models.Cursos;
import org.in5bv.dorbalaldana.kevinxulu.models.CarrerasTecnicas;
import org.in5bv.dorbalaldana.kevinxulu.models.Horarios;
import org.in5bv.dorbalaldana.kevinxulu.models.Instructores;
import org.in5bv.dorbalaldana.kevinxulu.models.Salones;
import org.in5bv.dorbalaldana.kevinxulu.system.Principal;


/**
 * 
 * 
 * @author Dorbal Emilio Aldana Ramos 2021604
 * @date 2/06/2022
 * @time 16:38:10 Código técnico: IN5BV Grupo: 1 (Jueves)
 */
public class CursosController implements Initializable {

    private enum Operacion {
        NINGUNO, GUARDAR, ACTUALIZAR;
    }
    
    private final String PAQUETE_IMAGES = "org/in5bv/dorbalaldana/kevinxulu/resources/images/";

    private Operacion operacion = Operacion.NINGUNO;
    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<Instructores> listaObservableInstructores;
    private ObservableList<CarrerasTecnicas> listaObservableCarrerasTecnicas;
    private ObservableList<Salones> listaObservableSalones;
    private ObservableList<Horarios> listaObservableHorarios;
    
    private Principal escenarioPrincipal;
    
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
    private TextField txtNombreCurso;
    @FXML
    private Spinner<Integer> spnCiclo;
    private SpinnerValueFactory<Integer> valueFactoryCiclo;
    @FXML
    private Spinner<Integer> spnMaximo;
    private SpinnerValueFactory<Integer> valueFactoyMaximo;
    @FXML
    private Spinner<Integer> spnMinimo;
    private SpinnerValueFactory<Integer> valueFactoyMinimo;
    @FXML
    private ComboBox<CarrerasTecnicas> cmbCarreraTecnica;
    @FXML
    private ComboBox<Horarios> cmbHorario;
    @FXML
    private ComboBox<Instructores> cmbInstructor;
    @FXML
    private ComboBox<Salones> cmbSalon;
    @FXML
    private TableView<Cursos> tblCursos;
    @FXML
    private TableColumn<Cursos, Integer> colId;
    @FXML
    private TableColumn<Cursos, String> colNombreCurso;
    @FXML
    private TableColumn<Cursos, Integer> colCiclo;
    @FXML
    private TableColumn<Cursos, Integer> colMaximo;
    @FXML
    private TableColumn<Cursos, Integer> colMinimo;
    @FXML
    private TableColumn<Cursos, String> colCodigoTecnico;
    @FXML
    private TableColumn<Cursos, Integer> colHorario;
    @FXML
    private TableColumn<Cursos, Integer> colInstructor;
    @FXML
    private TableColumn<Cursos, String> colSalon;
    @FXML
    private ImageView imgRegresar;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        valueFactoryCiclo = new SpinnerValueFactory.IntegerSpinnerValueFactory(2020, 2050, 2022);
        spnCiclo.setValueFactory(valueFactoryCiclo);
        
        valueFactoyMaximo = new SpinnerValueFactory.IntegerSpinnerValueFactory(11, 50, 20);
        spnMaximo.setValueFactory(valueFactoyMaximo);
        
        valueFactoyMinimo = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5);
        spnMinimo.setValueFactory(valueFactoyMinimo);
        
        cargarDatos();
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    private void habilitarCampos() {
        txtId.setEditable(true);
        txtNombreCurso.setEditable(true);
        spnCiclo.setEditable(true);
        spnMaximo.setEditable(true);
        spnMinimo.setEditable(true);


        txtId.setDisable(false);
        txtNombreCurso.setDisable(false);
        spnCiclo.setDisable(false);
        spnMaximo.setDisable(false);
        spnMinimo.setDisable(false);
        cmbCarreraTecnica.setDisable(false);
        cmbHorario.setDisable(false);
        cmbInstructor.setDisable(false);
        cmbSalon.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombreCurso.setEditable(false);
        spnCiclo.setEditable(false);
        spnMaximo.setEditable(false);
        spnMinimo.setEditable(false);
        cmbCarreraTecnica.setEditable(false);
        cmbHorario.setEditable(false);
        cmbInstructor.setEditable(false);
        cmbSalon.setEditable(false);

        txtId.setDisable(true);
        txtNombreCurso.setDisable(true);
        spnCiclo.setDisable(true);
        spnMaximo.setDisable(true);
        spnMinimo.setDisable(true);
        cmbCarreraTecnica.setDisable(true);
        cmbHorario.setDisable(true);
        cmbInstructor.setDisable(true);
        cmbSalon.setDisable(true);

    }

    private void limpiarCampos() {
        /*txtId .setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");*/
        txtId.clear();
        txtNombreCurso.clear();
        spnCiclo.getValueFactory().setValue(0);
        spnMaximo.getValueFactory().setValue(0);
        spnMinimo.getValueFactory().setValue(0);
        cmbCarreraTecnica.valueProperty().set(null);
        cmbHorario.valueProperty().set(null);
        cmbInstructor.valueProperty().set(null);
        cmbSalon.valueProperty().set(null);
    }
    
    // read -> listar los registros
    private ObservableList getCursos() {
        ArrayList<Cursos> arrayListCursos = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read()}");

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt("id"));
                curso.setNombreCurso(rs.getString("nombre_curso"));
                curso.setCiclo(rs.getInt("ciclo"));
                curso.setCupoMaximo(rs.getInt("cupo_maximo"));
                curso.setCupoMinimo(rs.getInt("cupo_minimo"));
                curso.setCarreraTecnicaId(rs.getString("carrera_tecnica_id"));
                curso.setHorarioId(rs.getInt("horario_id"));
                curso.setIntructorId(rs.getInt("instructor_id"));
                curso.setSalonId(rs.getString("salon_id"));

                System.out.println(curso.toString());

                arrayListCursos.add(curso);
            }

            listaObservableCursos = FXCollections.observableArrayList(arrayListCursos);

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

        return listaObservableCursos;
    }
    
    public ObservableList getCarrerasTecnicas(){
        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            //stmt = Conexion.getInstance().getConexion().createStatement();
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_carreras_tecnicas_read");
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
                
            }
            
            listaObservableCarrerasTecnicas = FXCollections.observableArrayList(lista);
            
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
        return listaObservableCarrerasTecnicas;
    }
    
    public ObservableList getInstructores() {
        ArrayList<Instructores> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //stmt = Conexion.getInstance().getConexion().createStatement();
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
                System.out.println(instructor.toString());

                lista.add(instructor);

            }

            listaObservableInstructores = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Se produjo un error al intentar listar la tabla de Instructores");
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
        return listaObservableInstructores;
    }
    
    public ObservableList getSalones(){
        ArrayList<Salones> lista = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            //stmt = Conexion.getInstance().getConexion().createStatement();
            pstmt = Conexion.getInstance().getConexion().prepareCall("CALL sp_salones_read()");
            rs = pstmt.executeQuery();
            while(rs.next()){
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));
                System.out.println(salon.toString());
                
               lista.add(salon);
                
            }
            
            listaObservableSalones = FXCollections.observableArrayList(lista);
            
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
        return listaObservableSalones;
    }
    
    private ObservableList getHorarios() {
        ArrayList<Horarios> arrayListHorarios = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_horarios_read()}");
            System.out.println(pstmt.toString());
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
                arrayListHorarios.add(horario);
            }
            listaObservableHorarios = FXCollections.observableArrayList(arrayListHorarios);
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al consultar la tabla de Horarios");
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
        return listaObservableHorarios;
    }
    
    
    
    public void cargarDatos(){
        tblCursos.setItems(getCursos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
        colMaximo.setCellValueFactory(new PropertyValueFactory<>("cupoMaximo"));
        colMinimo.setCellValueFactory(new PropertyValueFactory<>("cupoMinimo"));
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<>("carreraTecnicaId"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horarioId"));
        colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colSalon.setCellValueFactory(new PropertyValueFactory<>("salonId"));
        
        cmbCarreraTecnica.setItems(getCarrerasTecnicas());
        cmbHorario.setItems(getHorarios());
        cmbInstructor.setItems(getInstructores());
        cmbSalon.setItems(getSalones());
    }
    
    private boolean agregarCursos() {
        
        Cursos curso = new Cursos();
        
        //curso.setId(Integer.parseInt(txtId.getText()));
        
        curso.setNombreCurso(txtNombreCurso.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnMaximo.getValue());
        curso.setCupoMinimo(spnMinimo.getValue());
        
        curso.setCarreraTecnicaId(((CarrerasTecnicas) cmbCarreraTecnica
                .getSelectionModel().getSelectedItem()).getCodigoTecnico());
        
        curso.setHorarioId(((Horarios) cmbHorario.getSelectionModel()
                .getSelectedItem()).getId());
        
        curso.setIntructorId(((Instructores) cmbInstructor.getSelectionModel()
                .getSelectedItem()).getId());
        
        curso.setSalonId(((Salones) cmbSalon.getSelectionModel()
                .getSelectedItem()).getCodigoSalon());
        

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_create(?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setString(1, curso.getNombreCurso());
            pstmt.setInt(2, curso.getCiclo());
            pstmt.setInt(3, curso.getCupoMaximo());
            pstmt.setInt(4, curso.getCupoMinimo());
            pstmt.setString(5, curso.getCarreraTecnicaId());
            pstmt.setInt(6, curso.getHorarioId());
            pstmt.setInt(7, curso.getInstructorId());
            pstmt.setString(8, curso.getSalonId());

            System.out.println(pstmt.toString());

            pstmt.execute();
            listaObservableCursos.add(curso);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar "
                    + "el siguiente registro: " + curso.toString());
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
    
    public boolean actualizarCursos(){
        
        Cursos curso = new Cursos();
        curso.setId(Integer.parseInt(txtId.getText()));
        
        curso.setNombreCurso(txtNombreCurso.getText());
        curso.setCiclo(spnCiclo.getValue());
        curso.setCupoMaximo(spnMaximo.getValue());
        curso.setCupoMinimo(spnMinimo.getValue());
        
        curso.setCarreraTecnicaId(((CarrerasTecnicas) cmbCarreraTecnica
                .getSelectionModel().getSelectedItem()).getCodigoTecnico());
        
        curso.setHorarioId(((Horarios) cmbHorario.getSelectionModel()
                .getSelectedItem()).getId());
        
        curso.setIntructorId(((Instructores) cmbInstructor.getSelectionModel()
                .getSelectedItem()).getId());
        
        curso.setSalonId(((Salones) cmbSalon.getSelectionModel()
                .getSelectedItem()).getCodigoSalon());
        

        PreparedStatement pstmt = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_update(?, ?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setInt(1, curso.getId());
            pstmt.setString(2, curso.getNombreCurso());
            pstmt.setInt(3, curso.getCiclo());
            pstmt.setInt(4, curso.getCupoMaximo());
            pstmt.setInt(5, curso.getCupoMinimo());
            pstmt.setString(6, curso.getCarreraTecnicaId());
            pstmt.setInt(7, curso.getHorarioId());
            pstmt.setInt(8, curso.getInstructorId());
            pstmt.setString(9, curso.getSalonId());

            System.out.println(pstmt.toString());

            pstmt.execute();
            listaObservableCursos.add(curso);
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar actualizar "
                    + "el siguiente registro: " + curso.toString());
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
    
    public boolean eliminarCursos() {
        if (existeElementoSeleccionado()) {
            Cursos curso = (Cursos) tblCursos.getSelectionModel().getSelectedItem();
            System.out.println(curso);

            PreparedStatement pstmt = null;

            try {

                pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_delete(?)}");
                System.out.println(pstmt.toString());
                
                pstmt.setInt(1, curso.getId());
                System.out.println(pstmt);
                pstmt.execute();
                
                listaObservableCursos.remove(tblCursos.getSelectionModel().getSelectedItem());
                return true;

            } catch (SQLException e) {
                System.err.println("\nSe produjo un error al intentar eliminar el siguiente registro: " + curso.toString());
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
    
    private boolean camposObligatoriosVacios() {
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Control Accademico");
        alerta.setHeaderText(null);
        if (txtNombreCurso.getText().isBlank()) {
            alerta.setContentText("El campo de nombre curso debe estar lleno");
            alerta.show();
            return true;
        }

        if (cmbCarreraTecnica.getValue() == null) {
            alerta.setContentText("El campo de carrera técnica debe estar lleno.");
            alerta.show();
            return true;
        }
        if (cmbHorario.getValue() == null) {
            alerta.setContentText("El campo horario debe estar lleno");
            alerta.show();
            return true;
        }
        if (cmbInstructor.getValue() == null) {
            alerta.setContentText("El campo intructor debe estar lleno");
            alerta.show();
            return true;
        }
        if (cmbSalon.getValue() == null) {
            alerta.setContentText("El campo salon debe estar lleno");
            alerta.show();
            return true;
        }
        
        return false;
    }
    
    public boolean existeElementoSeleccionado(){
        return (tblCursos.getSelectionModel().getSelectedItem() != null);
    }
    

    @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                limpiarCampos();
                tblCursos.setDisable(true);
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
                    if (agregarCursos()) {
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

                        tblCursos.setDisable(false);

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

                tblCursos.setDisable(false);
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
                    if (actualizarCursos()) {
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
                        if (eliminarCursos()) {

                            listaObservableCursos.remove(tblCursos.getSelectionModel().getFocusedIndex());
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
                        tblCursos.getSelectionModel().clearSelection();
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
                
                tblCursos.getSelectionModel().clearSelection();

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
    private void seleccionarElemento() {
        if(existeElementoSeleccionado()){
            txtId.setText(String.valueOf(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getId()));
            txtNombreCurso.setText(((Cursos)tblCursos.getSelectionModel().getSelectedItem()).getNombreCurso());
            cmbCarreraTecnica.getSelectionModel().select(buscarCarrerasTecnicas(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getCarreraTecnicaId()));
            cmbHorario.getSelectionModel().select( buscarHorarios(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getHorarioId()));
            cmbInstructor.getSelectionModel().select(buscarInstructor(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getInstructorId()));cmbSalon.getSelectionModel().select(buscarSalon(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getSalonId()));
        }
    }
    
    public Cursos buscarCurso(int id){
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = null;
        
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{CALL sp_cursos_read_by_id(?)}");
            pstmt.setInt(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            
            while(rs.next()){
                curso = new Cursos(rs.getInt(1), rs.getString(2),rs.getInt(3),rs.getInt(4), rs.getInt(5), rs.getString(6), rs.getInt(7),rs.getInt(8),rs.getString(9));
                
            }
            System.out.println(curso.toString());
        } catch (SQLException e) {
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
        return curso;
    }
    
     private CarrerasTecnicas buscarCarrerasTecnicas(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CarrerasTecnicas carrera = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_carreras_tecnicas_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                carrera = new CarrerasTecnicas(
                        rs.getString("codigo_tecnico"),
                        rs.getString("carrera"),
                        rs.getString("grado"),
                        rs.getString("seccion"),
                        rs.getString("jornada")
                );

                System.out.println(carrera.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Carrera");
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

        return carrera;
    }
     
    private Horarios buscarHorarios(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Horarios horario = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_horarios_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                horario = new Horarios(
                        rs.getInt("id"), 
                        rs.getTime("horario_inicio").toLocalTime(), 
                        rs.getTime("horario_final").toLocalTime(), 
                        rs.getBoolean("lunes"), 
                        rs.getBoolean("martes"), 
                        rs.getBoolean("miercoles"), 
                        rs.getBoolean("jueves"), 
                        rs.getBoolean("viernes")
                );

                System.out.println(horario.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Horarios");
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

        return horario;
    }
    
    
    private Instructores buscarInstructor(int id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Instructores instructor = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_instructores_read_by_id(?)}");

            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                instructor = new Instructores(
                        rs.getInt("id"), 
                        rs.getString("nombre1"), 
                        rs.getString("nombre2"), 
                        rs.getString("nombre3"), 
                        rs.getString("apellido1"), 
                        rs.getString("apellido2"), 
                        rs.getString("direccion"), 
                        rs.getString("email"), 
                        rs.getString("telefono"), 
                        rs.getDate("fecha_nacimiento").toLocalDate()
                );

                System.out.println(instructor.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Instructores");
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

        return instructor;
    }
    
    
    private Salones buscarSalon(String id) {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Salones salon = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_salones_read_by_id(?)}");

            pstmt.setString(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                
                salon = new Salones(
                        rs.getString("codigo_salon"), 
                        rs.getString("descripcion"), 
                        rs.getInt("capacidad_maxima"), 
                        rs.getString("edificio"), 
                        rs.getInt("nivel")
                );

                System.out.println(salon.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar listar la tabla de Salones");
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

        return salon;
    }    


    @FXML
    void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
}
