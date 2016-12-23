/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package order;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import modul.koneksi;
import order.list_menu;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modul.Modul_order;
/**
 *
 * @author nurhuda ganteng
 */
public class order extends javax.swing.JInternalFrame {

    /**
     * Creates new form order
     */
    public order() {
        initComponents();
        auto_item.setVisible(false);
        harga.setVisible(false);
        id_order.setVisible(false);
        tagihan.setVisible(false);
        kembalian.setVisible(false);
        /// no invoice
        int x = acak.nextInt(100) + 5500;
        String output_no_order = Integer.toString(x);
        no_order.setText(output_no_order);
        id_order.setText(output_no_order);
        
        // tampad order
        tampad.addColumn("Kode Menu");
        tampad.addColumn("Qty");
        tampad.addColumn("Harga");
        tampad.addColumn("Sub Total");
        tampad.addColumn("token");
        data_order.setModel(tampad);
    }
    
    private DefaultTableModel tampad = new DefaultTableModel();
    float harga_menu =0;
    float qty_menu = 0;
    float tagihan_order = 0;
    float pembayaran_order = 0;
    float kembalian_order = 0;
    
    String token_order;
    
    list_menu frm_menu = new list_menu();
    Random acak = new Random();
    Modul_order m_order = new Modul_order();
    
    public void auto_menu(String nama_menu){
         Connection konsi = null;
        koneksi db = new koneksi();
        String sql ="";
        auto_item.addItem("");
        try{
//            hapusTabel(tampad);
            konsi =db.getKoneksi();
            sql ="select kode, nama, harga, status from menus where nama like '%"+nama_menu+"%' and status ='aktif'";
            Statement stmt = konsi.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String kode = rs.getString(1);
                String nama = rs.getString(2);
                String harga = rs.getString(3);
                String status = rs.getString(4);
                String item_auto = kode+"-"+nama+"-"+harga;
                auto_item.addItem(item_auto);
                
//                String email = rs.getString(6);
                
                Object[] data ={kode,nama,harga,status};
                
//                tampad.addRow(data);
            }
            auto_item.setVisible(true);
        }catch(Exception e){
            System.out.println("proses penampilan data Menu gagal "+e.getMessage());
            auto_item.removeAllItems();
            auto_item.setVisible(false);
        }
    }
    
    public void tambah_order_master(){
        
        //// cek kode sudah pernah di input atau belum
        
        Connection konsi = null;
        koneksi db = new koneksi();
        String sql ="";
        int x_cek = 0;
        try{

            konsi =db.getKoneksi();
            sql ="select kode_order from orders where kode_order ='"+id_order.getText()+"'";
            Statement stmt = konsi.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                x_cek++;
                String kode_cek = rs.getString(1);
            }
            
            //// simpan ke master
            if(x_cek < 1){
                m_order.setKode_invoice(id_order.getText());
                m_order.setNo_meja(no_meja.getText());
                m_order.setTagihan(tagihan_order);
                m_order.setPembayaran(pembayaran_order);
                m_order.setKembalian(kembalian_order);
                m_order.simpan_order_master();
                
            }
        }catch(Exception e){
            System.out.println("proses penampilan data Menu gagal "+e.getMessage());
        }
    }
    
    public void tambah_order_detail(){
        m_order.setId_order(Integer.parseInt(id_order.getText()));
        m_order.setId_menu(menu.getText());
        m_order.setQty(Integer.parseInt(qty.getText()));
        m_order.setHarga(Float.parseFloat(harga.getText()));
        float sub_total = Integer.parseInt(qty.getText()) * Float.parseFloat(harga.getText());
        m_order.setSubtotal(sub_total);
        m_order.simpan_order_detail();
    }
    
    
    /// tampil order
    public void hapusTabel(DefaultTableModel contoh){
        int a = contoh.getRowCount();
        for(int i = 0; i < a; i++){
            contoh.removeRow(0);
        }
    }
     
    public void tampil_order(int id_order){
        tagihan_order = 0;
         Connection konsi = null;
        koneksi db = new koneksi();
        String sql ="";
        try{
            hapusTabel(tampad);
            konsi =db.getKoneksi();
            sql ="select id_menu,qty,harga,sub_total,id_order_detail from order_details where id_order='"+id_order+"'";
            Statement stmt = konsi.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String id_menu = rs.getString(1);
                String qty = rs.getString(2);
                String harga = rs.getString(3);
                String sub_total = rs.getString(4);
                String token = rs.getString(5);
                tagihan_order = tagihan_order + Float.parseFloat(sub_total);
//                String email = rs.getString(6);
                
                Object[] data ={id_menu,qty,harga,sub_total,token};
                
                tampad.addRow(data);
            }
            label_total_belanja.setText(Float.toString(tagihan_order));
        }catch(Exception e){
            System.out.println("proses penampilan data Order Detail gagal "+e.getMessage());
        }
    }
    
    public void kosongkan(){
        menu.setText("");
        qty.setText("");
    }
    public void pilihMenu(){
        int a = data_order.getSelectedRow();
       if(a == -1){
           JOptionPane.showMessageDialog(null, "Belum ada Menu yang anda Pilih");
       }
       
       String kodeMenu = (String)tampad.getValueAt(a, 4);
       token_order = kodeMenu;
//       edit_menu(kodeMenu);
      }
    public void hitung_uang(){
        pembayaran_order = Float.parseFloat(pembayaran.getText());
        kembalian_order = pembayaran_order - tagihan_order;
        label_pembayaran.setText(pembayaran.getText());
        label_kembalian.setText(Float.toString(kembalian_order));
    }
    
    public void reset(){
        kosongkan();
        label_pembayaran.setText("0000");
        label_kembalian.setText("0000");
        label_total_belanja.setText("");
        no_meja.setText("");
        pembayaran.setText("");
        hapusTabel(tampad);
        int x = acak.nextInt(100) + 5500;
        String output_no_order = Integer.toString(x);
        no_order.setText(output_no_order);
        id_order.setText(output_no_order);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        menu = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        no_order = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        qty = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        auto_item = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        data_order = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        no_meja = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        pembayaran = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        harga = new javax.swing.JTextField();
        id_order = new javax.swing.JTextField();
        tagihan = new javax.swing.JTextField();
        kembalian = new javax.swing.JTextField();
        label_pembayaran = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        label_total_belanja = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        label_kembalian = new javax.swing.JLabel();

        jLabel1.setText("Menu        :");

        menu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                menuKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                menuKeyTyped(evt);
            }
        });

        jLabel2.setText("No Order  :");

        no_order.setText("No Order Akan Terisi Secara Otomatis");

        jLabel3.setText("Qty           :");

        jButton1.setText("Tambah");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        auto_item.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                auto_itemItemStateChanged(evt);
            }
        });
        auto_item.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                auto_itemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(qty))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(no_order, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(auto_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton1))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(no_order))
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(auto_item, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(qty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1))
        );

        data_order.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(data_order);

        jButton2.setText("Selesai");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText("Hapus");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel4.setText("No Meja");

        jLabel5.setText("Pembayaran");

        pembayaran.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                pembayaranKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                pembayaranKeyTyped(evt);
            }
        });

        jButton3.setText("hapus Menu");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 766, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pembayaran))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(no_meja, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(no_meja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(pembayaran, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addContainerGap())
        );

        label_pembayaran.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        label_pembayaran.setText("00000");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setText("Kembalian    :");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setText("Total :");

        label_total_belanja.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        label_total_belanja.setText("00000");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setText("Pembayaran :");

        label_kembalian.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        label_kembalian.setText("00000");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel7)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel8)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(id_order, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tagihan, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(label_pembayaran, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label_total_belanja, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(label_kembalian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addComponent(jLabel8)
                                .addGap(18, 18, 18))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(label_total_belanja)
                                .addGap(9, 9, 9)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_pembayaran)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(label_kembalian))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(harga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(id_order, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tagihan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_menuKeyPressed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_menuKeyPressed

    private void auto_itemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_auto_itemActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_auto_itemActionPerformed

    private void auto_itemItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_auto_itemItemStateChanged
        // TODO add your handling code here:
        String target = auto_item.getSelectedItem().toString();
        if(target !=""){
            String[] pecah = target.split("-");
            String output_kode = pecah[0];
            String output_harga = pecah[2];
            auto_item.setVisible(false);
            menu.setText(output_kode);
            harga.setText(output_harga);
        }
        
    }//GEN-LAST:event_auto_itemItemStateChanged

    private void menuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_menuKeyTyped
        // TODO add your handling code here:
        auto_item.removeAllItems();
        auto_menu(menu.getText());
    }//GEN-LAST:event_menuKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tambah_order_master();
        tambah_order_detail();
        tampil_order(Integer.parseInt(id_order.getText()));
        hitung_uang();
        kosongkan();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void pembayaranKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pembayaranKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_pembayaranKeyTyped

    private void pembayaranKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pembayaranKeyReleased
        // TODO add your handling code here:
        hitung_uang();
    }//GEN-LAST:event_pembayaranKeyReleased

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        pilihMenu();
        m_order.hapus_menu_order(token_order);
        tampil_order(Integer.parseInt(id_order.getText()));
        hitung_uang();
        kosongkan();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
            m_order.setKode_invoice(id_order.getText());
            m_order.setNo_meja(no_meja.getText());
            m_order.setTagihan(tagihan_order);
            m_order.setPembayaran(pembayaran_order);
            m_order.setKembalian(kembalian_order);
            m_order.update_order_master();
            reset();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        m_order.setKode_invoice(id_order.getText());
        m_order.hapus_order();
        reset();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox auto_item;
    private javax.swing.JTable data_order;
    private javax.swing.JTextField harga;
    private javax.swing.JTextField id_order;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField kembalian;
    private javax.swing.JLabel label_kembalian;
    private javax.swing.JLabel label_pembayaran;
    private javax.swing.JLabel label_total_belanja;
    private javax.swing.JTextField menu;
    private javax.swing.JTextField no_meja;
    private javax.swing.JLabel no_order;
    private javax.swing.JTextField pembayaran;
    private javax.swing.JTextField qty;
    private javax.swing.JTextField tagihan;
    // End of variables declaration//GEN-END:variables
}
