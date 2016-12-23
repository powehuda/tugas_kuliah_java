/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
/**
 *
 * @author Yosep
 */
public class Modul_admin {
    
    private String username;
    private String password;
    private String nama;
    private String no_telp;
    private String status;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public void simpan(){
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        try{
            konsi = db.getKoneksi();
            sql ="insert into admins(username,password,nama,no_telp,status)values(?,?,?,?,?)";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setString(1, this.username);
            pInsert.setString(2, this.password);
            pInsert.setString(3, this.nama);
            pInsert.setString(4, this.no_telp);
            pInsert.setString(5, this.status);
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                System.out.println("proses Input data Admin Berhasil");
//                JOptionPane.showMessageDialog(null, "proses Input data Admin Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses input Data Admin gagal "+e.getMessage());
//            JOptionPane.showMessageDialog(null, "proses input Data Admin gagal "+e.getMessage());
        }
    }
    
    public void update(){
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        try{
            konsi = db.getKoneksi();
            
            sql ="update admins set nama=?, no_telp=?, status =?, password=? where username = ?";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setString(1, this.nama);
            pInsert.setString(2, this.no_telp);
            pInsert.setString(3, this.status);
            pInsert.setString(4, this.password);
            pInsert.setString(5, this.username);
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                System.out.println("proses Update data Admins Berhasil");
//                JOptionPane.showMessageDialog(null, "proses Update data Admins Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses Update Data Admins gagal "+e.getMessage());
//            JOptionPane.showMessageDialog(null, "proses Update Data Admins gagal "+e.getMessage());
        }
    }
    
    public int login(){
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        int status_login = 0;
        try{
            konsi = db.getKoneksi();
            sql ="select * from admins where `username` = '"+this.username+"' and `password` ='"+this.password+"' and `status` ='aktif'";
            Statement stmt = konsi.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                
                status_login = 1;
            }
        }catch(Exception e){
            System.out.println("proses Login Admins gagal "+e.getMessage()+sql);
//            JOptionPane.showMessageDialog(null, "proses Update Data Admins gagal "+e.getMessage());
            status_login = 0;
        }
        
        return status_login;
    }
   
}
