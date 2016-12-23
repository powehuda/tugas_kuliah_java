/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;
import java.sql.*;
/**
 *
 * @author nurhuda ganteng
 */
public class Modul_menu {
    private String kode;
    private String nama;
    private String harga;
    private String status;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
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
            sql ="insert into menus(kode,nama,harga,status)values(?,?,?,?)";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setString(1, this.kode);
            pInsert.setString(2, this.nama);
            pInsert.setString(3, this.harga);
            pInsert.setString(4, this.status);
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                System.out.println("proses Input data Menu Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses input Data Menu gagal "+e.getMessage());
        }
    }
    
    public void update(){
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        try{
            konsi = db.getKoneksi();
            sql ="update admin_suteki set nama=?, harga=?, status =? where kode = ?";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setString(1, this.nama);
            pInsert.setString(2, this.harga);
            pInsert.setString(3, this.status);
            pInsert.setString(4, this.kode);
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                System.out.println("proses Update data Menu Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses Update Data Menu gagal "+e.getMessage());
        }
    }
    
    
    
}
