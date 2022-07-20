
package org.in5bv.dorbalaldana.kevinxulu.system;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bv.dorbalaldana.kevinxulu.controllers.AlumnosContoller;
import org.in5bv.dorbalaldana.kevinxulu.controllers.AsignacionesAlumnosController;
import org.in5bv.dorbalaldana.kevinxulu.controllers.AutorController;
import org.in5bv.dorbalaldana.kevinxulu.controllers.MenuPrincipalController;
import org.in5bv.dorbalaldana.kevinxulu.controllers.CarrerasTecnicasController;
import org.in5bv.dorbalaldana.kevinxulu.controllers.CursosController;
import org.in5bv.dorbalaldana.kevinxulu.controllers.HorariosController;
import org.in5bv.dorbalaldana.kevinxulu.controllers.InstructoresController;
import org.in5bv.dorbalaldana.kevinxulu.controllers.SalonesController;
import org.in5bv.dorbalaldana.kevinxulu.controllers.AutorController;
import org.in5bv.dorbalaldana.kevinxulu.controllers.LoginController;

/**
 * 
 *
 * @author Dorbal Emilio Aldana Ramos 2021604 y Kevin Josue Xulu Solis 2021348
 * @date 5/04/2022
 * @time 16:38:10
 * Código técnico: IN5BV
 * Grupo: 1 (Jueves)
 */
public class Principal extends Application{
    
    private Stage escenarioPrincipal;
    private final String PAQUETE_IMAGES = "org/in5bv/dorbalaldana/kevinxulu/resources/images/";
    private final String PAQUETE_VIEW = "../views/";
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.setTitle("Control Académico Kinal");
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGES + "Icono.png"));
        this.escenarioPrincipal.centerOnScreen();
        
        // cambiarEscena("MenuPrincipalView.fxml", 1200, 600);
        // cambiarEscena("AlumnosView.fxml", 1200, 600);
        
        mostrarLogin();
    }
    
    
    
    /*
    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException{
        
        Initializable resultado;
        //Cargador de archivo FXML
        FXMLLoader cargadorFXML = new FXMLLoader();
        //Cargador de fabirca para cargar el archivo FXML
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        
        // Ubicacíon dle archivo FXML que se pintará en el escenario
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VIEW + vistaFxml));
        
        //Asignacion de la logica del archivo
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VIEW + vistaFxml);
        
        // Creación del nodo raíz 
        AnchorPane root = cargadorFXML.load(archivo);
        
        //Creación de la escena
        Scene nuevaEscena = new Scene(root, ancho, alto);
        
        //cargar la escena en el escenario principal
        this.escenarioPrincipal.setScene(nuevaEscena);
        
        // optener el controlador del archivo FXML
        resultado = (Initializable)cargadorFXML.getController();
        
        // Devolver el controlador
        return resultado;
    }*/
    
    
    
    /*//Parent root = FXMLLoader.load(getClass().getResource("../views/MenuPrincipalView.fxml"));
        Parent root = FXMLLoader.load(getClass().getResource("../views/AlumnosView.fxml"));
        
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Control academico Kinal");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("org/in5bv/dorbalaldana/kevinxulu/resources/images/Icono.png"));
        
        
        
        // Agregar un icono a la barra de titulos y a la barra de tareas
        
        
        primaryStage.show();
        this.escenarioPrincipal.show();
        */
    
    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException{
        
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        
        Scene scene = new Scene((AnchorPane)cargadorFXML.load(), ancho, alto);
        
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable)cargadorFXML.getController();
    }
    
    public void mostrarEscenaAlumnos(){
        try {
            AlumnosContoller alumnosController = (AlumnosContoller) cambiarEscena("AlumnosView.fxml", 1200, 600);
            alumnosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un error al intentar mostrar la vista de alumnos");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaInstructores(){
        try {
            InstructoresController intructorController = (InstructoresController) cambiarEscena("InstructoresView.fxml", 1200, 600);
            intructorController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            
        }
        
    }
    
    public void mostrarEscenaPrincipal(){
        
      try{  
       MenuPrincipalController menuPrincipalController = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 1200, 600);
       menuPrincipalController.setEscenarioPrincipal(this);
      }catch (Exception ex){
          System.err.println("\nSe produjo un error al intentar mostrar la vista Menú principal");
          ex.printStackTrace();
      }
      
    }
    
    public void mostrarEscenaCursos(){
        try {
            CursosController cursoController = (CursosController) cambiarEscena("CursosView.fxml", 1200, 600);
            cursoController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.out.println("\nSe produjo un error al intentar mostrar la vista Cursos");
            e.printStackTrace();
        }
    }
    
    public void mostrarEscenaCarrerasTecnicas(){
        try {
            CarrerasTecnicasController menuCarrerasTecnicasController = (CarrerasTecnicasController)cambiarEscena("CarrerasTecnicasView.fxml",1200,600);
            menuCarrerasTecnicasController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.out.println("\nSe produjo un error al intentar mostrar la vista carreras tecnicas");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaSalones(){
        try {
            SalonesController menuSalones = (SalonesController)cambiarEscena("SalonesFXML.fxml", 1200, 600);
            menuSalones.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe produjo un erro al intentar mostrar la vista de menu principal");
        }
    }
    
    public void mostrarEscenaAsigacion() {
        try {
            AsignacionesAlumnosController asignacionController = (AsignacionesAlumnosController) cambiarEscena("AsignacionesAlumnosView.fxml", 1200, 600);
            asignacionController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe ha un producido un error al mostrar la vista de Asignaciones");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaHorarios() {
        try {
            HorariosController horariosController = (HorariosController) cambiarEscena("HorariosView.fxml", 1200, 600);
            horariosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe ha un producido un error al mostrar la vista de Horarios");
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaAutor(){
        try {
            AutorController autorController = (AutorController) cambiarEscena("AutorView.fxml",430,470);
            autorController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            System.err.println("\nSe ha un producido un error al mostrar la vista de Autor");
            ex.printStackTrace();
        }
    }
    
    public void mostrarLogin(){
        try {
            LoginController loginController = (LoginController) cambiarEscena("LoginView.fxml", 566,370);
            loginController.setEscenarioPrincipal(this);
        } catch (Exception e) {
            System.err.println("\nSe ha producido un error al mostrar la vista de Login");
            e.printStackTrace();
        }
    }
    
}
