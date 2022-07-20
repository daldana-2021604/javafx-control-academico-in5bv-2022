package org.in5bv.dorbalaldana.kevinxulu.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import org.in5bv.dorbalaldana.kevinxulu.reports.GenerarReporte;
import org.in5bv.dorbalaldana.kevinxulu.system.Principal;
import org.in5bv.dorbalaldana.kevinxulu.models.AsignacionesAlumnos;
import javafx.scene.control.TextInputDialog;
import org.in5bv.dorbalaldana.db.Conexion;
import org.in5bv.dorbalaldana.kevinxulu.models.Cursos;
import org.in5bv.dorbalaldana.kevinxulu.controllers.LoginController;

/**
 *
 *
 * @author Dorbal Emilio Aldana Ramos 2021604 y Kevin Josue Xulu Solis 2021348
 * @date 5/04/2022
 * @time 16:38:10 Código técnico: IN5BV Grupo: 1 (Jueves)
 */
public class MenuPrincipalController implements Initializable {

    private final String PAQUETE_IMAGES = "org/in5bv/dorbalaldana/kevinxulu/resources/images/";

    private Principal escenarioPrincipal;
    @FXML
    private MenuItem btnAlumnos;
    @FXML
    private MenuItem btnInstructores;
    @FXML
    private MenuItem btnSalones;
    @FXML
    private MenuItem btnCarreras;
    @FXML
    private MenuItem btnHorarios;

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
    void clicSalones(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaSalones();
    }

    @FXML
    void clicCursos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaCursos();
    }

    @FXML
    void clicInstructores(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaInstructores();
    }

    @FXML
    void clicAsignaciones(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAsigacion();
    }

    @FXML
    void clicHorarios(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaHorarios();
    }

    @FXML
    void clicReporteAlumnos(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_ALUMNOS", PAQUETE_IMAGES + "estudiante-con-gorro-de-graduacion.png");
        GenerarReporte.getInstance().mostrarReporte("Alumnos.jasper", parametros, "Reporte de alumnos");
    }

    @FXML
    void clicReporteAsignacionesAlumnos(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_ASIGNACIONES", PAQUETE_IMAGES + "delegar.png");
        GenerarReporte.getInstance().mostrarReporte("AsignacionesAlumnos.jasper", parametros, "Reporte de Asignaciones alumnos");
    }

    @FXML
    void clicReporteCarrerasTecnicas(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_CARRERAS", PAQUETE_IMAGES + "mecanico.png");
        GenerarReporte.getInstance().mostrarReporte("CarrerasTecnicas.jasper", parametros, "Reporte de Carreras Tecnicas");
    }

    @FXML
    void clicReporteHorarios(ActionEvent event) {
        Map<String, Object> parametros = new HashMap();
        parametros.put("LOGO_HORARIOS", PAQUETE_IMAGES + "tiempo.png");
        GenerarReporte.getInstance().mostrarReporte("Horarios.jasper", parametros, "Reporte Horarios");
    }

    @FXML
    void clicReporteInstructores(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_INSTRUCTORES", PAQUETE_IMAGES + "Instructores.png");
        GenerarReporte.getInstance().mostrarReporte("Instructores.jasper", parametros, "Reporte de Instructores");
    }

    @FXML
    void clicReporteSalones(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_SALONES", PAQUETE_IMAGES + "aula.png");
        GenerarReporte.getInstance().mostrarReporte("Salones.jasper", parametros, "Reporte de Salones");
    }

    @FXML
    void clicReporteCursos(ActionEvent event) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("LOGO_CURSOS", PAQUETE_IMAGES + "Cursos.png");
        GenerarReporte.getInstance().mostrarReporte("Cursos.jasper", parametros, "Reporte de cursos");
    }

    @FXML
    void clicAcercaDe(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAutor();
    }

    public static void cerrarVentana(ActionEvent e) {
        Platform.exit();
    }

    @FXML
    void clicCerrar(ActionEvent event) {
        cerrarVentana(event);
    }
    
    @FXML
    void clicCerrarSesion(ActionEvent event){
        escenarioPrincipal.mostrarLogin();
    }

    @FXML
    private void clicCursosId(ActionEvent event) {
        Cursos curso = new Cursos();

        TextInputDialog dialog = new TextInputDialog("Tran");

        dialog.setTitle("Confirmacion de id");
        dialog.setHeaderText("Ingrese el id");
        dialog.setContentText("Id:");

        Optional<String> result = dialog.showAndWait();

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Control Accademico");
        alerta.setHeaderText(null);

        if (buscarCurso(Integer.parseInt(result.get())) != null) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("LOGO_ASIGNACIONES", PAQUETE_IMAGES + "Cursos.png");
            parametros.put("idCurso", Integer.parseInt(result.get()));
            GenerarReporte.getInstance().mostrarReporte("CursosPorId.jasper", parametros, "Reporte de cursos por id");
        } else {

            alerta.setContentText("El id ingresado no existe");
            alerta.show();
        }
    }

    @FXML
    private void clicAsignacionesAlumnosId(ActionEvent event) {

        AsignacionesAlumnos asignacionesAlumnos = new AsignacionesAlumnos();

        TextInputDialog dialog = new TextInputDialog("Tran");

        dialog.setTitle("Confirmacion de id");
        dialog.setHeaderText("Ingrese el id");
        dialog.setContentText("Id:");

        Optional<String> result = dialog.showAndWait();

        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Control Accademico");
        alerta.setHeaderText(null);

        if (buscarAsignacionesAlumnos(Integer.parseInt(result.get())) != null) {
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("LOGO_ASIGNACIONES", PAQUETE_IMAGES + "delegar.png");
            parametros.put("idAsignacion", Integer.parseInt(result.get()));
            GenerarReporte.getInstance().mostrarReporte("AsignacionesAlumnosPorId.jasper", parametros, "Reporte de Asignaciones Alumnos por id");
        } else {

            alerta.setContentText("El id ingresado no existe");
            alerta.show();
        }
    }

    //Asignaciones
    private ObservableList<AsignacionesAlumnos> listaObservableAsignaciones;

    private AsignacionesAlumnos buscarAsignacionesAlumnos(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AsignacionesAlumnos alumno = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_asignaciones_alumnos_read_by_id(?)}");

            pstmt.setInt(1, id);
            System.out.println(pstmt.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                alumno = new AsignacionesAlumnos(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getTimestamp(4).toLocalDateTime()
                );

                System.out.println(alumno.toString());
            }

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar buscar al Alumno con el id: " + id);
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

        return alumno;
    }

    //Cusos
    private ObservableList<Cursos> listaObservableCursos;

    private Cursos buscarCurso(int id) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        Cursos curso = null;

        try {
            pstmt = Conexion.getInstance().getConexion()
                    .prepareCall("{CALL sp_cursos_read_by_id(?)}");
            pstmt.setInt(1, id);

            System.out.println(pstmt.toString());

            rs = pstmt.executeQuery();

            while (rs.next()) {
                curso = new Cursos();
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

        return curso;
    }
    
    
    
    public void deshabilitarMenus(){
        btnAlumnos.setDisable(true);
        btnCarreras.setDisable(true);
        btnHorarios.setDisable(true);
        btnInstructores.setDisable(true);
        btnSalones.setDisable(true);
        
        btnAlumnos.setVisible(false);
        btnCarreras.setVisible(false);
        btnHorarios.setVisible(false);
        btnInstructores.setVisible(false);
        btnSalones.setVisible(false);
    }
    
    public void tipoUsuario(){
        LoginController login = null;
        if(login.usuarioTipo() == 2){
            deshabilitarMenus();
        }
        
    }
    
    
}
