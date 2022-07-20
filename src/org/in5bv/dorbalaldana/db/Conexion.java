
package org.in5bv.dorbalaldana.db;

/**
 *
 * @author Dorbal Emilio Aldana Ramos 2021604 y Kevin Josue Xulu Solis 2021348
 * @date 3/05/2022
 * @time 16:59:10
 * Carné 2021604
 * Código técnico: IN5BV
 * Grupo: 1
 */
import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;


public class Conexion {

    private final String IP_SERVER = "localhost";
    private final String PORT = "3306";
    private final String DB = "db_control_academico_in5bv";
    private final String USER = "kinal";
    private final String PASSWORD = "admin";
    private final String URL;
    private Connection conexion;
    
    private static Conexion instancia;
    
    public static Conexion getInstance(){
        if (instancia == null) {
            
            instancia = new Conexion();
        }
        return instancia;
    }
    
    private Conexion(){
        URL = "jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB + "?allowPublicKeyRetrieval=true&serverTimezone=UTC&useSSL=false";
        
        try {
            
            //Registrar el Driver jdbc
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("com.mysql.jodbc.Driver").newInstance();
            
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa");
            
            DatabaseMetaData dma = conexion.getMetaData();
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion());
            
        /*}catch(InstantiationException ex){
            System.err.println("No se puede crear una instancia del objeto");
            ex.printStackTrace();
        }
        catch(IllegalAccessException ex){
            System.err.println("No se tiene los permisos para acceder al paquete");*/
        }
        catch(ClassNotFoundException ex){
            System.err.println("No se encuentra ninguna definicion para la clase");
            ex.printStackTrace();
            
        }
        catch(CommunicationsException ex){
            System.err.println("ERROR: No se puede establecer la conexcion con el servidor de MySQL"
                    + "\nRecomendacion:"
                    + "\nVerifique que el nombre del Host, la IP_Server o el numero de puerto sea correcto"
                    + "\nVerifique que el servicio de MySQL este iniciado");
            ex.printStackTrace();
            
        } catch(SQLException ex){
            System.err.println("Se produjo un error de tipo SQLException:");
            System.err.println("Mensaje: " + ex.getMessage());
            System.err.println("Error code: " + ex.getErrorCode());
            System.err.println("SQLState: " + ex.getSQLState());
            ex.printStackTrace();
            
        }
        catch (Exception ex) {
            System.err.println("Sin conexion");
            ex.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    
    
    
    
}
