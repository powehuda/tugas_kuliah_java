/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO - PC
 */
public class koneksi {
    
    private Properties myKonfig;
    private String hKonfig;
    
    private String driver;
    private String lokasi;
    private String host;
    private String database;
    private String user;
    private String pass;
    private Connection konek;
    
    public Connection getKoneksi(){
        // ("jdbc:derby://localhost:1527/testDb","username",
//         "password");
        driver = "com.mysql.jdbc.Driver";
        lokasi = "";
        host = "jdbc:mysql://localhost:3306";
        database ="restaurant_7";
        user ="root";
        pass ="";
        try{
            Class.forName(driver);
        }catch(ClassNotFoundException es){
            System.out.println("driver mysql belum dipasang "+es.getMessage());
        }
        try{
            if(pass.equals("")){
                konek =DriverManager.getConnection(lokasi+host+"/"+database,user,"");
            }else{
                konek =DriverManager.getConnection(lokasi+host+"/"+database,user,pass);
            }
            
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ada yang gagal "+e.getMessage());
        }
        
        return konek;
    }
}
