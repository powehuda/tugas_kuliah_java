/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modul;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author nurhuda ganteng
 */
public class Modul_order {
    /// master
    private String kode_invoice;
    private String no_meja;
    private float tagihan;
    private float pembayaran;
    private float kembalian;

    public float getTagihan() {
        return tagihan;
    }

    public void setTagihan(float tagihan) {
        this.tagihan = tagihan;
    }

    public float getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(float pembayaran) {
        this.pembayaran = pembayaran;
    }

    public float getKembalian() {
        return kembalian;
    }

    public void setKembalian(float kembalian) {
        this.kembalian = kembalian;
    }
    
    //detail
    private int id_order;
    private String id_menu;
    private int qty;
    private float harga;
    private float subtotal;

    public int getId_order() {
        return id_order;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getHarga() {
        return harga;
    }

    public void setHarga(float harga) {
        this.harga = harga;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public String getKode_invoice() {
        return kode_invoice;
    }

    public void setKode_invoice(String kode_invoice) {
        this.kode_invoice = kode_invoice;
    }

    public String getNo_meja() {
        return no_meja;
    }

    public void setNo_meja(String no_meja) {
        this.no_meja = no_meja;
    }

   

    public String getId_menu() {
        return id_menu;
    }

    public void setId_menu(String id_menu) {
        this.id_menu = id_menu;
    }
    
    public void simpan_order_master(){
        
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        try{
            konsi = db.getKoneksi();
            sql ="insert into orders(kode_order,no_meja,tagihan,pembayaran,kembalian)values(?,?,?,?,?)";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setString(1, this.kode_invoice);
            pInsert.setString(2, this.no_meja);
            pInsert.setFloat(3, this.tagihan);
            pInsert.setFloat(4, this.pembayaran);
            pInsert.setFloat(5, this.kembalian);
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                System.out.println("proses Input data order Berhasil");
//                JOptionPane.showMessageDialog(null, "proses Input data Admin Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses input Data order gagal "+e.getMessage());
//            JOptionPane.showMessageDialog(null, "proses input Data Admin gagal "+e.getMessage());
        }
        
    }
    
    public void update_order_master(){
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        try{
            konsi = db.getKoneksi();
//            sql ="insert into orders(kode_order,no_meja,tagihan,pembayaran,kembalian)values(?,?,?,?,?)";
            sql ="update orders set no_meja=?, tagihan=?, pembayaran =?, kembalian=? where kode_order = ?";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setString(1, this.no_meja);
            pInsert.setFloat(2, this.tagihan);
            pInsert.setFloat(3, this.pembayaran);
            pInsert.setFloat(4, this.kembalian);
            pInsert.setString(5, this.kode_invoice);
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                System.out.println("proses Update data Orders Berhasil");
//                JOptionPane.showMessageDialog(null, "proses Update data Admins Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses Update Data Orders gagal "+e.getMessage());
//            JOptionPane.showMessageDialog(null, "proses Update Data Admins gagal "+e.getMessage());
        }
    }
    
    
    public void simpan_order_detail(){
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        try{
            konsi = db.getKoneksi();
            sql ="insert into order_details(id_order,id_menu,qty,harga,sub_total)values(?,?,?,?,?)";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setInt(1, this.id_order);
            pInsert.setString(2, this.id_menu);
            pInsert.setInt(3, this.qty);
            pInsert.setFloat(4, this.harga);
            pInsert.setFloat(5, this.subtotal);
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                System.out.println("proses Input data order detail Berhasil");
//                JOptionPane.showMessageDialog(null, "proses Input data Admin Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses input Data order detail gagal "+e.getMessage());
//            JOptionPane.showMessageDialog(null, "proses input Data Admin gagal "+e.getMessage());
        }
    }
    
    public void hapus_menu_order(String id_order_detail){
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        try{
            konsi = db.getKoneksi();
            sql ="delete from order_details where id_order_detail =?";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setInt(1, Integer.parseInt(id_order_detail));
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                System.out.println("proses Hapus data order detail Berhasil");
//                JOptionPane.showMessageDialog(null, "proses Input data Admin Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses hapus Data order detail gagal "+e.getMessage()+" "+id_order_detail);
//            JOptionPane.showMessageDialog(null, "proses input Data Admin gagal "+e.getMessage());
        }
    }
    
    public void hapus_order(){
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        try{
            konsi = db.getKoneksi();
            sql ="delete from orders where kode_order =?";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setInt(1, Integer.parseInt(this.kode_invoice));
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                hapus_order_detail();
                System.out.println("proses Hapus data order detail Berhasil");
//                JOptionPane.showMessageDialog(null, "proses Input data Admin Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses hapus Data order detail gagal "+e.getMessage());
//            JOptionPane.showMessageDialog(null, "proses input Data Admin gagal "+e.getMessage());
        }
    }
    
    public void hapus_order_detail(){
         Connection konsi = null;
        koneksi db = new koneksi();
        String sql="";
        try{
            konsi = db.getKoneksi();
            sql ="delete from order_details where id_order =?";
            PreparedStatement pInsert = konsi.prepareStatement(sql);
            pInsert.setInt(1, Integer.parseInt(this.kode_invoice));
            int prosesInput = pInsert.executeUpdate();
            
            if(prosesInput > 0){
                
                System.out.println("proses Hapus data order detail Berhasil");
//                JOptionPane.showMessageDialog(null, "proses Input data Admin Berhasil");
            }
        }catch(Exception e){
            System.out.println("proses hapus Data order detail gagal "+e.getMessage());
//            JOptionPane.showMessageDialog(null, "proses input Data Admin gagal "+e.getMessage());
        }
    }
    
}
