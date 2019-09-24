package MainProgram;

import java.awt.Color;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import javax.swing.JFrame;
import java.sql.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import Database.Koneksi;
import com.sun.glass.events.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.util.Collections.list;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

public class Menu extends javax.swing.JFrame {

    static boolean maximized = true;
    int xMouse;
    int yMouse;
    public Connection con;
    public ResultSet rs;
    public Statement st;
    public PreparedStatement pst;
    Object set;
    private Object listbaju;
    private DefaultTableModel model;
    
    // tanggal & waktu
    Calendar kal = new GregorianCalendar();
    int tahun = kal.get(Calendar.YEAR);
    int bulan = kal.get(Calendar.MONTH)+1;
    int hari = kal.get(Calendar.DAY_OF_MONTH);
    int jam = kal.get(Calendar.HOUR_OF_DAY);
    int menit = kal.get(Calendar.MINUTE);
    //int detik = kal.get(Calendar.SECOND);
    String tanggal = hari + " / " + bulan + " / " + tahun;
    String waktu = jam + ":" + menit;
    
    public Menu() {
        initComponents();
        Koneksi classKoneksi = new Koneksi();
        con = classKoneksi.getKoneksi();
        //daftarbaju();
        this.setLocationRelativeTo(null);
            // code waktu & tanggal
            new Thread(){
            public void run(){
                while(true){
                    while(true) {
                        lbl_tgl.setText(tanggal);
                        lbl_jam.setText(waktu);
                    }
                }
            }
        }.start(); 
    }
    
    private void daftarbaju(){
        DefaultTableModel table = new DefaultTableModel();

        String [] header = {"No" ,"Kode Baju", "Nama Baju", "Kategori", "Size Baju", "Harga", "stock", "Tanggal Dibuat", "Waktu Dibuat"};
        model = new DefaultTableModel(header, 0);
        tablebaju.setModel(model);
        
        try {
            String sql = "SELECT * FROM `tb_baju`";
            st = con.createStatement();
            rs = st.executeQuery(sql);
            
            while(rs.next()) {
                String[] row = {rs.getString(1),
                                rs.getString(2), 
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9)
                };
                model.addRow(row);   
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }      
    }
    
    private void spdata(){
        lbl_home.setBackground(new Color(255,255,255));
        lbl_list.setBackground(new Color(255,255,255));
        lbl_pay.setBackground(new Color(255,255,255));
        Pnl_Home.removeAll();
        Pnl_Home.repaint();
        Pnl_Home.revalidate();
        Pnl_Home.add(SP_data);
        Pnl_Home.repaint();
        Pnl_Home.revalidate();
    }
    
    private void splist(){
        lbl_list.setBackground(new Color(153,153,255));
        lbl_home.setBackground(new Color(255,255,255));
        lbl_pay.setBackground(new Color(255,255,255));
        Pnl_Home.removeAll();
        Pnl_Home.repaint();
        Pnl_Home.revalidate();
        Pnl_Home.add(SP_List);
        Pnl_Home.repaint();
        Pnl_Home.revalidate();
        daftarbaju();
    }
    
    private void spPay(){
        lbl_pay.setBackground(new Color(153,153,255));
        lbl_list.setBackground(new Color(255,255,255));
        lbl_home.setBackground(new Color(255,255,255));
        Pnl_Home.removeAll();
        Pnl_Home.repaint();
        Pnl_Home.revalidate();
        Pnl_Home.add(SP_Pay);
        Pnl_Home.repaint();
        Pnl_Home.revalidate();
    }
    
    private void spHome(){
        lbl_home.setBackground(new Color(153,153,255));
        lbl_list.setBackground(new Color(255,255,255));
        lbl_pay.setBackground(new Color(255,255,255));
        Pnl_Home.removeAll();
        Pnl_Home.repaint();
        Pnl_Home.revalidate();
        Pnl_Home.add(SP_Home);
        Pnl_Home.repaint();
        Pnl_Home.revalidate();
    }
    
    private void reset(){
        Txt_kode_baju.setText("");
        Txt_nama_baju.setText("");
        CB_kategori.setSelectedIndex(0);
        Txt_harga_baju.setText("");
        Txt_stock.setText("");    
    }
    
    private void reset2(){
        txt_kodeBayar.setText("");
        txt_namabaju.setText("");
        txt_hargaBayar.setText("");
        txt_qyt.setText("");
        txt_subtotal.setText("");
        txt_bayar.setText("");
        txt_kembalian.setText("");
        DefaultTableModel model = (DefaultTableModel)tabel_bayar.getModel();
        model.setRowCount(0);
    }
    
    private void simpandata(){
        String kode = Txt_kode_baju.getText();
        String nama = Txt_nama_baju.getText();
        String harga = Txt_harga_baju.getText();
        String kategori = CB_kategori.getSelectedItem().toString();
        String size ="";
        
        if("".equals(Txt_kode_baju.getText()) || "".equals(Txt_nama_baju.getText()) || "".equals(Txt_harga_baju.getText()) || "".equals(CB_kategori.getSelectedItem())){
            JOptionPane.showMessageDialog(this, "Data Tidak Di Masukkan", "ERROR", JOptionPane.WARNING_MESSAGE);
        
        }else {
            int status = 0;
            if (status == 0){
                String simpandata = "INSERT INTO `tb_baju` (`kode_baju`, `nama_baju`, `harga_baju`, `kategori`, `size_baju`, `stock`, tanggal, waktu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                try{
                    PreparedStatement pst = con.prepareStatement(simpandata);
                    pst.setString(1, kode);
                    pst.setString(2, nama);
                    pst.setString(3, harga);
                    pst.setString(4, kategori);
                    
                    // Pilih size
                    if(Cek_S.isSelected()){
                        size += Cek_S.getText()+" ";
                    }
                    if (Cek_M.isSelected()){
                        size += Cek_M.getText()+" ";
                    }
                    if (Cek_L.isSelected()){
                        size += Cek_L.getText()+" ";
                    }
                    if (Cek_XL.isSelected()){
                        size += Cek_XL.getText()+" ";
                    }
                    pst.setString(5, size);
                    
                    pst.setString(6, Txt_stock.getText());
                    pst.setString(7, tanggal);
                    pst.setString(8, waktu);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, "SUCESS");                    
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "GAGAL", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
                splist();
            }
        }
    }
  
    private void updatedata(){
        String kode = Txt_kode_baju.getText();
        String nama = Txt_nama_baju.getText();
        String harga = Txt_harga_baju.getText();
        String kategori = CB_kategori.getSelectedItem().toString();
        String size ="";
        
        if("".equals(Txt_kode_baju.getText()) || "".equals(Txt_nama_baju.getText()) || "".equals(Txt_harga_baju.getText()) || "".equals(CB_kategori.getSelectedItem())){
            JOptionPane.showMessageDialog(this, "Data Tidak Di Masukkan", "ERROR", JOptionPane.WARNING_MESSAGE);
        
        }else {
            int status = 0;
            try{
                int row = tablebaju.getSelectedRow();
                String nilai = (tablebaju.getModel().getValueAt(row, 0).toString());
                String update = "UPDATE `tb_baju` SET kode_baju=?, nama_baju=?, harga_baju=?, kategori=?, size_baju=? , stock=? WHERE no="+nilai;
                PreparedStatement pst = con.prepareStatement(update);
                pst.setString(1, Txt_kode_baju.getText());
                pst.setString(2, Txt_nama_baju.getText());
                pst.setString(3, Txt_harga_baju.getText());
                pst.setString(4, kategori);
                
                //size
                if(Cek_S.isSelected()){
                    size += Cek_S.getText()+" ";
                }
                if (Cek_M.isSelected()){
                    size += Cek_M.getText()+" ";
                }
                if (Cek_L.isSelected()){
                    size += Cek_L.getText()+" ";
                }
                if (Cek_XL.isSelected()){
                    size += Cek_XL.getText()+" ";
                }
                pst.setString(5, size);
                pst.setString(6, Txt_stock.getText());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "SUCESS");                    
            }catch (SQLException ex){
                JOptionPane.showMessageDialog(null, "GAGAL", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        splist();
        }
    }
    
    private void caridata(){
        DefaultTableModel table = new DefaultTableModel();

        String [] header = {"Kode Baju", "Nama Baju", "Kategori", "Size Baju", "Harga", "stock"};
        model = new DefaultTableModel(header, 0);
        tablebaju.setModel(model);
        
        try {
            String kode = Txt_search.getText();
            String nama = Txt_search.getText();

            String cariSQL = "SELECT * FROM tb_baju WHERE kode_baju='"+ kode + "' OR nama_baju='" + nama +"'";
            st = con.createStatement();
            rs = st.executeQuery(cariSQL);
            if(rs.next() == false){
                JOptionPane.showMessageDialog(this, "Kode Baju Tidak Ditemukan");
                daftarbaju();
                Txt_search.setText("");
            }else{
                while(rs.next()) {
                    String[] row = {rs.getString(2), 
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7)
                                };
                    model.addRow(row);   
                }
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }      
    }
    
    private void hapusData(){    
        int status = 0;
        int opt = JOptionPane.showConfirmDialog(null, "Hapus Data ?", "Hapus Data", JOptionPane.YES_NO_OPTION);
        if(opt==0){
            int row = tablebaju.getSelectedRow();
            if(row!=-1){
                String NOMER = tablebaju.getValueAt(row, 0).toString();
                String sql = "DELETE FROM tb_baju WHERE no='"+NOMER+"'";

                String resetno = "ALTER TABLE tb_baju DROP no";
                String noawal="ALTER TABLE tb_baju ADD no INT NOT NULL AUTO_INCREMENT PRIMARY KEY FIRST";

                try {
                    con.createStatement().execute(sql);
                    con.createStatement().execute(resetno);
                    con.createStatement().execute(noawal);

                    JOptionPane.showMessageDialog(this, "SUCCES");
                    
                    splist();
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "GAGAL", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
                }
            }
        }   
    }
    
    private void editdata(){
        int i = tablebaju.getSelectedRow();
        TableModel model = tablebaju.getModel();
        Txt_kode_baju.setText(model.getValueAt(i, 1).toString());
        Txt_nama_baju.setText(model.getValueAt(i, 2).toString());
        CB_kategori.setSelectedItem(model.getValueAt(i, 3).toString());
        
        String size = model.getValueAt(i,4).toString();
            switch(size){
                case "S ":
                    Cek_S.setSelected(true);
                    Cek_M.setSelected(false);
                    Cek_L.setSelected(false);
                    Cek_XL.setSelected(false);
                    break;
                case "M ":
                    Cek_M.setSelected(true);
                    Cek_S.setSelected(false);
                    Cek_L.setSelected(false);
                    Cek_XL.setSelected(false);
                    break;
                case "L ":
                    Cek_L.setSelected(true);
                    Cek_M.setSelected(false);
                    Cek_S.setSelected(false);
                    Cek_XL.setSelected(false);
                    break;
                case "XL ":
                    Cek_XL.setSelected(true);
                    Cek_M.setSelected(false);
                    Cek_L.setSelected(false);
                    Cek_S.setSelected(false);
                    break;
                default:
                    Cek_XL.setSelected(true);
                    Cek_M.setSelected(true);
                    Cek_L.setSelected(true);
                    Cek_S.setSelected(true);
                    break;
            }
        Txt_harga_baju.setText(model.getValueAt(i, 5).toString());
        Txt_stock.setText(model.getValueAt(i, 6).toString());
            
        spdata();
    }
    
    private void troli(){
        int i = tablebaju.getSelectedRow();
        TableModel model = tablebaju.getModel();
        txt_kodeBayar.setText(model.getValueAt(i, 1).toString());
        txt_namabaju.setText(model.getValueAt(i, 2).toString());
        txt_hargaBayar.setText(model.getValueAt(i, 5).toString());
        
        spPay();
    }
    
    private void kodeBaju_bayar(){
        String kode = txt_kodeBayar.getText();

        try {
            String cariSQL = "SELECT * FROM tb_baju WHERE kode_baju=?";
            PreparedStatement pst  = con.prepareStatement(cariSQL);
            pst.setString(1, kode);
            rs = pst.executeQuery();
                
            if(rs.next() == false){
                JOptionPane.showMessageDialog(this, "Data Tidak DiTemukan !", "WARNING", JOptionPane.WARNING_MESSAGE);
            }else{
                String nama = rs.getString("nama_baju");
                String harga = rs.getString("harga_baju");
                    
                txt_namabaju.setText(nama.trim());
                txt_hargaBayar.setText(harga.trim());
            }        
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    private void tambahbelanja(){
        String kode = txt_kodeBayar.getText();

        try {
            String cariSQL = "SELECT * FROM tb_baju WHERE kode_baju=?";
            PreparedStatement pst  = con.prepareStatement(cariSQL);
            pst.setString(1, kode);
            rs = pst.executeQuery();
                
            while(rs.next()){
                int stock; 
                stock = rs.getInt("stock");
                
                int harga = Integer.parseInt(txt_hargaBayar.getText());
                int qyt = Integer.parseInt(txt_qyt.getText());
                int total = harga * qyt;
                
                // stock yang tersedia
                if(qyt > stock){
                    JOptionPane.showMessageDialog(this, "Stock yang tersedia = " + stock);
                    JOptionPane.showMessageDialog(this, "Qyt Melebihi Stock", "ERROR", JOptionPane.WARNING_MESSAGE);
                }else{
                    // buat tabel belanja
                    DefaultTableModel model = (DefaultTableModel)tabel_bayar.getModel();
                    
                    model.addRow(new Object[]{
                        txt_kodeBayar.getText(),
                        txt_namabaju.getText(),
                        txt_hargaBayar.getText(),
                        txt_qyt.getText(),
                        total,
                    });
                }
                subtotal();
                
                txt_kodeBayar.setText("");
                txt_namabaju.setText("");
                txt_hargaBayar.setText("");
                txt_qyt.setText("");
            }    
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    private void subtotal(){
        int no = 0;
        for(int i = 0; i<tabel_bayar.getRowCount(); i++){
            no = no + Integer.parseInt(tabel_bayar.getValueAt(i, 4).toString());
        }
        txt_subtotal.setText(Integer.toString(no));
    }
    
    private void hapusbelanja(){
        DefaultTableModel model = (DefaultTableModel)tabel_bayar.getModel();
        model.removeRow(tabel_bayar.getSelectedRow());
        subtotal();
    }
    
    private void bayar(){
        int bayar = Integer.parseInt(txt_bayar.getText());
        int subtotal = Integer.parseInt(txt_subtotal.getText());
        int kembalian = bayar - subtotal;
        
        txt_kembalian.setText(String.valueOf(kembalian));
        
    }
    
    private void pembayaran(){
        String subtotal = txt_subtotal.getText();
        String bayar = txt_bayar.getText();
        String kembalian = txt_kembalian.getText();
        
        int idstruk = 0;
        // memasukkan data ke database tb_struk
        String strukSQL = "INSERT INTO `tb_struk` (`tanggal`, `waktu`, `subtotal`, `bayar`, `kembalian`) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement pst  = con.prepareStatement(strukSQL, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, tanggal);
            pst.setString(2, waktu);
            pst.setString(3, subtotal);
            pst.setString(4, bayar);
            pst.setString(5, kembalian);
            pst.executeUpdate();
            ResultSet generateKeyresult = pst.getGeneratedKeys();
            
            if(generateKeyresult.next()){
                idstruk = generateKeyresult.getInt(1);
            }
            
            // memasukkan data ke database tb_belanja
            int row = tabel_bayar.getRowCount();
            String kode = "";
            String nama = "";
            String harga = "";
            int qyt = 0;
            String qytS = Integer.toString(qyt);
            int total = 0;
            String daftarSQL = "INSERT INTO `tb_belanja` (`id_belanja`, `kode_baju`, `nama_baju`, `harga`, `qyt`, `total`) VALUES (?, ?, ?, ?, ?, ?)";

            pst = con.prepareStatement(daftarSQL);
            
            for(int i = 0; i < tabel_bayar.getRowCount(); i++){
                kode = (String)tabel_bayar.getValueAt(i, 0);
                nama = (String)tabel_bayar.getValueAt(i, 1);
                harga = (String)tabel_bayar.getValueAt(i, 2);
                qytS = (String)tabel_bayar.getValueAt(i, 3).toString();
                total = (int)tabel_bayar.getValueAt(i, 4);
                
                pst.setInt(1, idstruk);
                pst.setString(2, kode);
                pst.setString(3, nama);
                pst.setString(4, harga);
                pst.setString(5, qytS);
                pst.setInt(6, total);
                pst.executeUpdate();
            }
            pst.addBatch();
            JOptionPane.showMessageDialog(this, "Succes");
        }catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "GAGAL", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void stock(){
        int row = tabel_bayar.getRowCount();
        String kode = "";
        int qyt = 0;
        String qytS = Integer.toString(qyt);
  
        String stockSQL = "UPDATE `tb_baju` SET stock= stock-? WHERE kode_baju=?";
        try {
            pst = con.prepareStatement(stockSQL);
            for(int i = 0; i < tabel_bayar.getRowCount(); i++){
                kode = (String)tabel_bayar.getValueAt(i, 0);
                qytS = (String)tabel_bayar.getValueAt(i, 3).toString();
                
                pst.setString(1, qytS);
                pst.setString(2, kode);
               
                pst.executeUpdate();
            }
            daftarbaju();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "GAGAL", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void print(){
        String subtotal = txt_subtotal.getText();
        String bayar = txt_bayar.getText();
        String kembalian = txt_kembalian.getText();
 
        new Print(subtotal, bayar, kembalian, tabel_bayar.getModel()).setVisible(true);        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        klikkanan = new javax.swing.JPopupMenu();
        buatbaru = new javax.swing.JMenuItem();
        Edit = new javax.swing.JMenuItem();
        Salin = new javax.swing.JMenuItem();
        hapus = new javax.swing.JMenuItem();
        Tambahkan = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        Btn_logout = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Btn_Home = new javax.swing.JButton();
        Btn_List = new javax.swing.JButton();
        Btn_Pay = new javax.swing.JButton();
        lbl_home = new javax.swing.JLabel();
        lbl_list = new javax.swing.JLabel();
        lbl_pay = new javax.swing.JLabel();
        Txt_search = new MainProgram.CustomTextSearch();
        jLabel2 = new javax.swing.JLabel();
        Btn_search = new javax.swing.JButton();
        lbl_jam = new javax.swing.JLabel();
        lbl_tgl = new javax.swing.JLabel();
        Pnl_Header = new javax.swing.JPanel();
        Btn_Maximize = new javax.swing.JButton();
        Btn_Exit = new javax.swing.JButton();
        Btn_Minimize = new javax.swing.JButton();
        Pnl_Home = new javax.swing.JPanel();
        SP_Home = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        Btn_add = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Btn_kontak = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        Btn_help = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        Btn_help1 = new javax.swing.JButton();
        SP_List = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablebaju = new javax.swing.JTable();
        SP_Pay = new javax.swing.JScrollPane();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_bayar = new javax.swing.JTable();
        txt_kodeBayar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txt_namabaju = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txt_hargaBayar = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        txt_qyt = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        txt_subtotal = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        txt_bayar = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txt_kembalian = new javax.swing.JTextField();
        btn_tambahbelanja = new javax.swing.JButton();
        btn_hapusbelanja = new javax.swing.JButton();
        btn_bayar = new javax.swing.JButton();
        btn_hapusbelanja1 = new javax.swing.JButton();
        SP_data = new javax.swing.JScrollPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        Btn_save = new javax.swing.JButton();
        Btn_reset = new javax.swing.JButton();
        Btn_update = new javax.swing.JButton();
        Btn_delete = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Txt_kode_baju = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        Txt_harga_baju = new javax.swing.JTextField();
        Txt_nama_baju = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Cek_S = new javax.swing.JCheckBox();
        Cek_M = new javax.swing.JCheckBox();
        Cek_XL = new javax.swing.JCheckBox();
        Cek_L = new javax.swing.JCheckBox();
        jLabel33 = new javax.swing.JLabel();
        Txt_stock = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        CB_kategori = new javax.swing.JComboBox<>();

        buatbaru.setFont(new java.awt.Font("Corbel", 1, 14)); // NOI18N
        buatbaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_add_15px_1.png"))); // NOI18N
        buatbaru.setText("Buat Baru");
        buatbaru.setContentAreaFilled(false);
        buatbaru.setIconTextGap(10);
        buatbaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buatbaruActionPerformed(evt);
            }
        });
        klikkanan.add(buatbaru);

        Edit.setFont(new java.awt.Font("Corbel", 1, 14)); // NOI18N
        Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_edit_15px.png"))); // NOI18N
        Edit.setText("Edit");
        Edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Edit.setIconTextGap(10);
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });
        klikkanan.add(Edit);

        Salin.setFont(new java.awt.Font("Corbel", 1, 14)); // NOI18N
        Salin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_copy_15px.png"))); // NOI18N
        Salin.setText("Salin");
        Salin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Salin.setIconTextGap(10);
        klikkanan.add(Salin);

        hapus.setFont(new java.awt.Font("Corbel", 1, 14)); // NOI18N
        hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_delete_15px_2.png"))); // NOI18N
        hapus.setText("Hapus");
        hapus.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        hapus.setIconTextGap(10);
        hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hapusActionPerformed(evt);
            }
        });
        klikkanan.add(hapus);

        Tambahkan.setFont(new java.awt.Font("Corbel", 1, 14)); // NOI18N
        Tambahkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_shopping_cart_filled_15px.png"))); // NOI18N
        Tambahkan.setText("Tambahkan");
        Tambahkan.setContentAreaFilled(false);
        Tambahkan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Tambahkan.setIconTextGap(10);
        Tambahkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahkanActionPerformed(evt);
            }
        });
        klikkanan.add(Tambahkan);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu");
        setMinimumSize(new java.awt.Dimension(1100, 700));
        setUndecorated(true);
        setSize(new java.awt.Dimension(1100, 700));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1100, 150));

        namauser.setFont(new java.awt.Font("Tahoma", 0, 18));
        namauser.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        namauser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/User35px.png"))); // NOI18N
        namauser.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        Btn_logout.setBackground(new java.awt.Color(255, 255, 255));
        Btn_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/LogoutS.png"))); // NOI18N
        Btn_logout.setBorder(null);
        Btn_logout.setContentAreaFilled(false);
        Btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_logout.setFocusable(false);
        Btn_logout.setOpaque(true);
        Btn_logout.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Logout.png"))); // NOI18N
        Btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_logoutActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_shop_100px.png"))); // NOI18N

        Btn_Home.setBackground(new java.awt.Color(255, 255, 255));
        Btn_Home.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 15)); // NOI18N
        Btn_Home.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_home_25px.png"))); // NOI18N
        Btn_Home.setText("Home");
        Btn_Home.setBorder(null);
        Btn_Home.setContentAreaFilled(false);
        Btn_Home.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_Home.setFocusable(false);
        Btn_Home.setMaximumSize(new java.awt.Dimension(131, 25));
        Btn_Home.setMinimumSize(new java.awt.Dimension(131, 25));
        Btn_Home.setOpaque(true);
        Btn_Home.setPreferredSize(new java.awt.Dimension(131, 25));
        Btn_Home.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_HomeActionPerformed(evt);
            }
        });

        Btn_List.setBackground(new java.awt.Color(255, 255, 255));
        Btn_List.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 15)); // NOI18N
        Btn_List.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_list_25px.png"))); // NOI18N
        Btn_List.setText("Daftar Baju");
        Btn_List.setBorder(null);
        Btn_List.setContentAreaFilled(false);
        Btn_List.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_List.setFocusable(false);
        Btn_List.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_ListActionPerformed(evt);
            }
        });

        Btn_Pay.setBackground(new java.awt.Color(255, 255, 255));
        Btn_Pay.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 15)); // NOI18N
        Btn_Pay.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_payment_history_25px.png"))); // NOI18N
        Btn_Pay.setText("Pembayaran");
        Btn_Pay.setBorder(null);
        Btn_Pay.setContentAreaFilled(false);
        Btn_Pay.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_Pay.setFocusable(false);
        Btn_Pay.setMaximumSize(new java.awt.Dimension(131, 25));
        Btn_Pay.setMinimumSize(new java.awt.Dimension(131, 25));
        Btn_Pay.setOpaque(true);
        Btn_Pay.setPreferredSize(new java.awt.Dimension(131, 25));
        Btn_Pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_PayActionPerformed(evt);
            }
        });

        lbl_home.setBackground(new java.awt.Color(153, 153, 255));
        lbl_home.setMinimumSize(new java.awt.Dimension(131, 25));
        lbl_home.setOpaque(true);

        lbl_list.setBackground(new java.awt.Color(255, 255, 255));
        lbl_list.setMinimumSize(new java.awt.Dimension(131, 25));
        lbl_list.setOpaque(true);

        lbl_pay.setBackground(new java.awt.Color(255, 255, 255));
        lbl_pay.setMinimumSize(new java.awt.Dimension(131, 25));
        lbl_pay.setOpaque(true);

        Txt_search.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Txt_search.setBorder(null);
        Txt_search.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        Txt_search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                Txt_searchKeyReleased(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setOpaque(true);

        Btn_search.setBackground(new java.awt.Color(255, 255, 255));
        Btn_search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Search1.png"))); // NOI18N
        Btn_search.setBorder(null);
        Btn_search.setContentAreaFilled(false);
        Btn_search.setOpaque(true);
        Btn_search.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Search2.png"))); // NOI18N
        Btn_search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_searchActionPerformed(evt);
            }
        });

        lbl_jam.setFont(new java.awt.Font("Arial Unicode MS", 1, 18)); // NOI18N
        lbl_jam.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        lbl_tgl.setFont(new java.awt.Font("Arial Unicode MS", 1, 18)); // NOI18N
        lbl_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        Pnl_Header.setBackground(new java.awt.Color(33, 33, 33));
        Pnl_Header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                Pnl_HeaderMouseDragged(evt);
            }
        });
        Pnl_Header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                Pnl_HeaderMousePressed(evt);
            }
        });

        Btn_Maximize.setBackground(new java.awt.Color(33, 33, 33));
        Btn_Maximize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/max1.png"))); // NOI18N
        Btn_Maximize.setBorder(null);
        Btn_Maximize.setContentAreaFilled(false);
        Btn_Maximize.setOpaque(true);
        Btn_Maximize.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/max2.png"))); // NOI18N
        Btn_Maximize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_MaximizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_MaximizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_MaximizeMouseExited(evt);
            }
        });

        Btn_Exit.setBackground(new java.awt.Color(33, 33, 33));
        Btn_Exit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Close25px.png"))); // NOI18N
        Btn_Exit.setBorder(null);
        Btn_Exit.setContentAreaFilled(false);
        Btn_Exit.setOpaque(true);
        Btn_Exit.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Close25px.png"))); // NOI18N
        Btn_Exit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_ExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_ExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_ExitMouseExited(evt);
            }
        });

        Btn_Minimize.setBackground(new java.awt.Color(33, 33, 33));
        Btn_Minimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Minimize25px.png"))); // NOI18N
        Btn_Minimize.setBorder(null);
        Btn_Minimize.setContentAreaFilled(false);
        Btn_Minimize.setOpaque(true);
        Btn_Minimize.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/Minimize25pxabu.png"))); // NOI18N
        Btn_Minimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_MinimizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_MinimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_MinimizeMouseExited(evt);
            }
        });

        javax.swing.GroupLayout Pnl_HeaderLayout = new javax.swing.GroupLayout(Pnl_Header);
        Pnl_Header.setLayout(Pnl_HeaderLayout);
        Pnl_HeaderLayout.setHorizontalGroup(
            Pnl_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, Pnl_HeaderLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Btn_Minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_Maximize, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(Btn_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        Pnl_HeaderLayout.setVerticalGroup(
            Pnl_HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Btn_Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Btn_Maximize, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Btn_Minimize, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(59, 59, 59)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(Txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 155, Short.MAX_VALUE)
                        .addComponent(namauser, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(53, 53, 53)
                        .addComponent(Btn_logout))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(Btn_Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_home, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(lbl_list, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                                            .addComponent(Btn_List, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(18, 18, 18)
                                        .addComponent(Btn_Pay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(149, 149, 149)
                                        .addComponent(lbl_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lbl_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lbl_jam, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(Btn_search, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap())))
            .addComponent(Pnl_Header, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Pnl_Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(41, 41, 41))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(Txt_search, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Btn_search))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Btn_Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Btn_List)
                            .addComponent(Btn_Pay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_home, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_list, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl_pay, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(20, 20, 20))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Btn_logout)
                            .addComponent(namauser))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lbl_jam, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_tgl, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        Pnl_Home.setLayout(new java.awt.CardLayout());

        SP_Home.setBorder(null);
        SP_Home.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_Home.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));

        jLabel4.setFont(new java.awt.Font("Gill Sans MT", 1, 36)); // NOI18N
        jLabel4.setText("HOME");

        jLabel3.setBackground(new java.awt.Color(153, 153, 153));
        jLabel3.setOpaque(true);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setMaximumSize(new java.awt.Dimension(200, 200));
        jPanel6.setMinimumSize(new java.awt.Dimension(200, 200));
        jPanel6.setPreferredSize(new java.awt.Dimension(225, 250));

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_add_tag_127px.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Perpetua", 0, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Menambah Data Baju \nSesuai Keinginan");

        Btn_add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_tambah.png"))); // NOI18N
        Btn_add.setBorder(null);
        Btn_add.setContentAreaFilled(false);
        Btn_add.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_add.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_tambah2.png"))); // NOI18N
        Btn_add.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_addMouseClicked(evt);
            }
        });
        Btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_addActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Btn_add)
                .addGap(49, 49, 49))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_add)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.getAccessibleContext().setAccessibleName("Menambah Data Baju Sesuai Keinginan");

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setMaximumSize(new java.awt.Dimension(200, 200));
        jPanel7.setMinimumSize(new java.awt.Dimension(200, 200));
        jPanel7.setPreferredSize(new java.awt.Dimension(225, 250));

        jLabel12.setFont(new java.awt.Font("Perpetua", 0, 14)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Kontak Kami Jika Anda Mengalami Kendala");
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel12.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel12.setMaximumSize(new java.awt.Dimension(200, 30));
        jLabel12.setMinimumSize(new java.awt.Dimension(200, 30));
        jLabel12.setPreferredSize(new java.awt.Dimension(200, 30));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_contacts_127px.png"))); // NOI18N

        Btn_kontak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_kontak.png"))); // NOI18N
        Btn_kontak.setBorder(null);
        Btn_kontak.setContentAreaFilled(false);
        Btn_kontak.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_kontak.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_kontak2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(Btn_kontak)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_kontak)
                .addGap(7, 7, 7))
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setMaximumSize(new java.awt.Dimension(200, 200));
        jPanel8.setMinimumSize(new java.awt.Dimension(200, 200));
        jPanel8.setPreferredSize(new java.awt.Dimension(225, 250));

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_help_127px.png"))); // NOI18N

        jLabel18.setFont(new java.awt.Font("Perpetua", 0, 13)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Jika Transaksi Gagal Kita Dapat Membantu");

        Btn_help.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_help.png"))); // NOI18N
        Btn_help.setBorder(null);
        Btn_help.setContentAreaFilled(false);
        Btn_help.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_help.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_help2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(Btn_help)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_help)
                .addContainerGap(6, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setMaximumSize(new java.awt.Dimension(200, 200));
        jPanel11.setMinimumSize(new java.awt.Dimension(200, 200));
        jPanel11.setPreferredSize(new java.awt.Dimension(225, 250));

        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/icons8_about_127px.png"))); // NOI18N

        jLabel40.setFont(new java.awt.Font("Perpetua", 0, 18)); // NOI18N
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("Tentang Aplikasi");

        Btn_help1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_tentang.png"))); // NOI18N
        Btn_help1.setBorder(null);
        Btn_help1.setContentAreaFilled(false);
        Btn_help1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_help1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_tentang2.png"))); // NOI18N

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel40, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(Btn_help1)
                .addContainerGap(31, Short.MAX_VALUE))
            .addComponent(jLabel39, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_help1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1030, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel4)
                .addGap(3, 3, 3)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(70, 70, 70)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(2839, Short.MAX_VALUE))
        );

        SP_Home.setViewportView(jPanel3);

        Pnl_Home.add(SP_Home, "card2");

        SP_List.setBackground(new java.awt.Color(245, 245, 245));
        SP_List.setBorder(null);

        jPanel2.setBackground(new java.awt.Color(245, 245, 245));

        jLabel5.setFont(new java.awt.Font("Gill Sans MT", 1, 30)); // NOI18N
        jLabel5.setText("Daftar Baju");

        jLabel6.setBackground(new java.awt.Color(153, 153, 153));
        jLabel6.setOpaque(true);

        tablebaju.setFont(new java.awt.Font("Arima Madurai", 0, 14)); // NOI18N
        tablebaju.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No", "Kode Baju", "Nama Baju", "Kategori", "Size Baju", "Harga", "Tanggal Dibuat ", "Waktu Dibuat"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablebaju.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tablebaju.setOpaque(false);
        tablebaju.setRowHeight(25);
        tablebaju.setShowHorizontalLines(false);
        tablebaju.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablebajuMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tablebajuMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tablebaju);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2))
                .addContainerGap(1075, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addGap(3, 3, 3)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2725, Short.MAX_VALUE))
        );

        SP_List.setViewportView(jPanel2);

        Pnl_Home.add(SP_List, "card3");

        SP_Pay.setBackground(new java.awt.Color(245, 245, 245));
        SP_Pay.setBorder(null);
        SP_Pay.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_Pay.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel10.setBackground(new java.awt.Color(204, 204, 204));

        tabel_bayar.setFont(new java.awt.Font("Leelawadee", 0, 20)); // NOI18N
        tabel_bayar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Baju", "Nama Baju", "Harga", "Qty", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabel_bayar.setRowHeight(25);
        tabel_bayar.setShowVerticalLines(false);
        jScrollPane1.setViewportView(tabel_bayar);

        txt_kodeBayar.setBackground(new java.awt.Color(204, 204, 204));
        txt_kodeBayar.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_kodeBayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kodeBayar.setBorder(null);
        txt_kodeBayar.setNextFocusableComponent(txt_qyt);
        txt_kodeBayar.setPreferredSize(new java.awt.Dimension(150, 25));
        txt_kodeBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_kodeBayarKeyPressed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Kode Baju");

        jLabel10.setBackground(new java.awt.Color(0, 0, 0));
        jLabel10.setOpaque(true);
        jLabel10.setPreferredSize(new java.awt.Dimension(150, 1));

        jLabel14.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Nama Baju");

        txt_namabaju.setEditable(false);
        txt_namabaju.setBackground(new java.awt.Color(204, 204, 204));
        txt_namabaju.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_namabaju.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_namabaju.setBorder(null);
        txt_namabaju.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel16.setBackground(new java.awt.Color(0, 0, 0));
        jLabel16.setOpaque(true);
        jLabel16.setPreferredSize(new java.awt.Dimension(200, 1));

        jLabel23.setBackground(new java.awt.Color(0, 0, 0));
        jLabel23.setOpaque(true);
        jLabel23.setPreferredSize(new java.awt.Dimension(150, 10));

        jLabel26.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Harga Baju");

        txt_hargaBayar.setEditable(false);
        txt_hargaBayar.setBackground(new java.awt.Color(204, 204, 204));
        txt_hargaBayar.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_hargaBayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_hargaBayar.setBorder(null);
        txt_hargaBayar.setPreferredSize(new java.awt.Dimension(150, 25));

        jLabel27.setBackground(new java.awt.Color(0, 0, 0));
        jLabel27.setOpaque(true);
        jLabel27.setPreferredSize(new java.awt.Dimension(150, 10));

        jLabel28.setFont(new java.awt.Font("Corbel", 1, 18)); // NOI18N
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Qyt");

        txt_qyt.setBackground(new java.awt.Color(204, 204, 204));
        txt_qyt.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        txt_qyt.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_qyt.setBorder(null);
        txt_qyt.setPreferredSize(new java.awt.Dimension(150, 25));
        txt_qyt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_qytKeyPressed(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Corbel", 1, 20)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Sub Total");

        txt_subtotal.setEditable(false);
        txt_subtotal.setBackground(new java.awt.Color(204, 204, 204));
        txt_subtotal.setFont(new java.awt.Font("Digital-7", 0, 36)); // NOI18N
        txt_subtotal.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_subtotal.setBorder(null);
        txt_subtotal.setPreferredSize(new java.awt.Dimension(150, 40));

        jLabel30.setBackground(new java.awt.Color(0, 0, 0));
        jLabel30.setOpaque(true);
        jLabel30.setPreferredSize(new java.awt.Dimension(200, 1));

        jLabel31.setBackground(new java.awt.Color(0, 0, 0));
        jLabel31.setOpaque(true);
        jLabel31.setPreferredSize(new java.awt.Dimension(200, 1));

        txt_bayar.setBackground(new java.awt.Color(204, 204, 204));
        txt_bayar.setFont(new java.awt.Font("Digital-7", 1, 36)); // NOI18N
        txt_bayar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_bayar.setBorder(null);
        txt_bayar.setPreferredSize(new java.awt.Dimension(200, 40));
        txt_bayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_bayarKeyPressed(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Corbel", 1, 20)); // NOI18N
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Bayar");

        jLabel35.setBackground(new java.awt.Color(0, 0, 0));
        jLabel35.setOpaque(true);
        jLabel35.setPreferredSize(new java.awt.Dimension(200, 1));

        jLabel36.setFont(new java.awt.Font("Corbel", 1, 20)); // NOI18N
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("Kembalian");

        txt_kembalian.setEditable(false);
        txt_kembalian.setBackground(new java.awt.Color(204, 204, 204));
        txt_kembalian.setFont(new java.awt.Font("Digital-7", 1, 36)); // NOI18N
        txt_kembalian.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_kembalian.setBorder(null);
        txt_kembalian.setPreferredSize(new java.awt.Dimension(200, 40));

        btn_tambahbelanja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_add.png"))); // NOI18N
        btn_tambahbelanja.setBorder(null);
        btn_tambahbelanja.setContentAreaFilled(false);
        btn_tambahbelanja.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_add2.png"))); // NOI18N
        btn_tambahbelanja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahbelanjaActionPerformed(evt);
            }
        });

        btn_hapusbelanja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_del1.png"))); // NOI18N
        btn_hapusbelanja.setBorder(null);
        btn_hapusbelanja.setContentAreaFilled(false);
        btn_hapusbelanja.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_del2.png"))); // NOI18N
        btn_hapusbelanja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusbelanjaActionPerformed(evt);
            }
        });

        btn_bayar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_bayar.png"))); // NOI18N
        btn_bayar.setBorder(null);
        btn_bayar.setContentAreaFilled(false);
        btn_bayar.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_bayar2.png"))); // NOI18N
        btn_bayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bayarActionPerformed(evt);
            }
        });

        btn_hapusbelanja1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_reset.png"))); // NOI18N
        btn_hapusbelanja1.setBorder(null);
        btn_hapusbelanja1.setContentAreaFilled(false);
        btn_hapusbelanja1.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_reset2.png"))); // NOI18N
        btn_hapusbelanja1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusbelanja1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 804, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_kodeBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(46, 46, 46)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_namabaju, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_hargaBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(34, 34, 34)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_qyt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(81, 81, 81)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_subtotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(100, 100, 100)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_bayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(100, 100, 100)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_kembalian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(61, 61, 61)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_bayar)
                    .addComponent(btn_tambahbelanja)
                    .addComponent(btn_hapusbelanja)
                    .addComponent(btn_hapusbelanja1))
                .addContainerGap(1030, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_kodeBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel26)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_hargaBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel28)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_qyt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel10Layout.createSequentialGroup()
                            .addComponent(jLabel14)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_namabaju, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, 0)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_tambahbelanja))
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(btn_hapusbelanja)
                        .addGap(64, 64, 64)
                        .addComponent(btn_hapusbelanja1)))
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_subtotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_bayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_kembalian, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_bayar))
                        .addGap(0, 0, 0)
                        .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(2750, Short.MAX_VALUE))
        );

        SP_Pay.setViewportView(jPanel10);

        Pnl_Home.add(SP_Pay, "card4");

        SP_data.setBorder(null);
        SP_data.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        SP_data.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        jPanel5.setBackground(new java.awt.Color(204, 204, 204));

        jLabel13.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel13.setText("Kode Baju :");

        Btn_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_save1.png"))); // NOI18N
        Btn_save.setBorder(null);
        Btn_save.setContentAreaFilled(false);
        Btn_save.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_save.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_save2.png"))); // NOI18N
        Btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_saveActionPerformed(evt);
            }
        });

        Btn_reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_reset.png"))); // NOI18N
        Btn_reset.setBorder(null);
        Btn_reset.setContentAreaFilled(false);
        Btn_reset.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_reset.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_reset2.png"))); // NOI18N
        Btn_reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_resetActionPerformed(evt);
            }
        });

        Btn_update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_up.png"))); // NOI18N
        Btn_update.setBorder(null);
        Btn_update.setContentAreaFilled(false);
        Btn_update.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_update.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_up2.png"))); // NOI18N
        Btn_update.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_updateMouseClicked(evt);
            }
        });

        Btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_del1.png"))); // NOI18N
        Btn_delete.setBorder(null);
        Btn_delete.setContentAreaFilled(false);
        Btn_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Btn_delete.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/MainProgram/Image/btn_del2.png"))); // NOI18N
        Btn_delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Btn_deleteMouseClicked(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel19.setText("Harga Baju :");

        jLabel20.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel20.setText("Nama Baju :");

        jLabel21.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel21.setText("Size Baju :");

        jLabel22.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel22.setText("Kategori Baju :");

        Txt_kode_baju.setBackground(new java.awt.Color(204, 204, 204));
        Txt_kode_baju.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        Txt_kode_baju.setBorder(null);

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setOpaque(true);

        Txt_harga_baju.setBackground(new java.awt.Color(204, 204, 204));
        Txt_harga_baju.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        Txt_harga_baju.setBorder(null);

        Txt_nama_baju.setBackground(new java.awt.Color(204, 204, 204));
        Txt_nama_baju.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        Txt_nama_baju.setBorder(null);

        jLabel24.setBackground(new java.awt.Color(0, 0, 0));
        jLabel24.setOpaque(true);

        jLabel25.setBackground(new java.awt.Color(0, 0, 0));
        jLabel25.setOpaque(true);

        Cek_S.setBackground(new java.awt.Color(204, 204, 204));
        Cek_S.setFont(new java.awt.Font("Eras Medium ITC", 1, 18)); // NOI18N
        Cek_S.setText("S");

        Cek_M.setBackground(new java.awt.Color(204, 204, 204));
        Cek_M.setFont(new java.awt.Font("Eras Medium ITC", 1, 18)); // NOI18N
        Cek_M.setText("M");

        Cek_XL.setBackground(new java.awt.Color(204, 204, 204));
        Cek_XL.setFont(new java.awt.Font("Eras Medium ITC", 1, 18)); // NOI18N
        Cek_XL.setText("XL");

        Cek_L.setBackground(new java.awt.Color(204, 204, 204));
        Cek_L.setFont(new java.awt.Font("Eras Medium ITC", 1, 18)); // NOI18N
        Cek_L.setText("L");

        jLabel33.setFont(new java.awt.Font("Berlin Sans FB", 0, 24)); // NOI18N
        jLabel33.setText("Stock Baju :");

        Txt_stock.setBackground(new java.awt.Color(204, 204, 204));
        Txt_stock.setFont(new java.awt.Font("Cambria Math", 1, 14)); // NOI18N
        Txt_stock.setBorder(null);

        jLabel34.setBackground(new java.awt.Color(0, 0, 0));
        jLabel34.setOpaque(true);
        jLabel34.setPreferredSize(new java.awt.Dimension(375, 1));

        CB_kategori.setFont(new java.awt.Font("Gadugi", 0, 14)); // NOI18N
        CB_kategori.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "== Pilih ==", "Laki - Laki", "Perempuan", " " }));
        CB_kategori.setBorder(null);
        CB_kategori.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(Btn_save)
                        .addGap(153, 153, 153)
                        .addComponent(Btn_update)
                        .addGap(156, 156, 156)
                        .addComponent(Btn_reset)
                        .addGap(165, 165, 165)
                        .addComponent(Btn_delete))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(Txt_kode_baju)
                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Txt_nama_baju)
                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Txt_harga_baju, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                                .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Txt_stock)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(CB_kategori, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                            .addGap(10, 10, 10)
                                            .addComponent(Cek_S)
                                            .addGap(74, 74, 74)
                                            .addComponent(Cek_M)))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(Cek_L)
                                    .addGap(58, 58, 58)
                                    .addComponent(Cek_XL))))))
                .addContainerGap(995, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(Txt_kode_baju, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(Txt_nama_baju, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(Txt_harga_baju, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(CB_kategori, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(Cek_S)
                    .addComponent(Cek_L)
                    .addComponent(Cek_M)
                    .addComponent(Cek_XL))
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(Txt_stock, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(Btn_delete)
                    .addComponent(Btn_reset)
                    .addComponent(Btn_save)
                    .addComponent(Btn_update))
                .addContainerGap(2765, Short.MAX_VALUE))
        );

        SP_data.setViewportView(jPanel5);

        Pnl_Home.add(SP_data, "card5");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Pnl_Home, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(Pnl_Home, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        setSize(new java.awt.Dimension(1100, 700));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_ExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_ExitMouseClicked
        int opt = JOptionPane.showConfirmDialog(null, "KELUAR ?", "KELUAR", JOptionPane.YES_NO_OPTION);
        if(opt==0){
            System.exit(0);
        }
        
    }//GEN-LAST:event_Btn_ExitMouseClicked

    private void Btn_ExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_ExitMouseEntered
        Btn_Exit.setBackground(new Color(232, 17, 35));
    }//GEN-LAST:event_Btn_ExitMouseEntered

    private void Btn_ExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_ExitMouseExited
        Btn_Exit.setBackground(new Color(33, 33, 33));
    }//GEN-LAST:event_Btn_ExitMouseExited

    private void Btn_MaximizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_MaximizeMouseEntered
        Btn_Maximize.setBackground(new Color(75, 75, 75));
    }//GEN-LAST:event_Btn_MaximizeMouseEntered

    private void Btn_MaximizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_MaximizeMouseExited
        Btn_Maximize.setBackground(new Color(33, 33, 33));
    }//GEN-LAST:event_Btn_MaximizeMouseExited

    private void Btn_MinimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_MinimizeMouseEntered
        Btn_Minimize.setBackground(new Color(75, 75, 75));
    }//GEN-LAST:event_Btn_MinimizeMouseEntered

    private void Btn_MinimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_MinimizeMouseExited
        Btn_Minimize.setBackground(new Color(33, 33, 33));
    }//GEN-LAST:event_Btn_MinimizeMouseExited

    private void Btn_MinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_MinimizeMouseClicked
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_Btn_MinimizeMouseClicked

    private void Btn_MaximizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_MaximizeMouseClicked
        if (maximized) {
           Menu.this.setExtendedState(JFrame.MAXIMIZED_BOTH);
           GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
           Menu.this.setMaximizedBounds(env.getMaximumWindowBounds());
           maximized = false;
        } else {
           setExtendedState(JFrame.NORMAL);
           maximized = true;
        }
    }//GEN-LAST:event_Btn_MaximizeMouseClicked

    private void Pnl_HeaderMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Pnl_HeaderMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_Pnl_HeaderMousePressed

    private void Pnl_HeaderMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Pnl_HeaderMouseDragged
        if (maximized) {
            int x = evt.getXOnScreen();
            int y = evt.getYOnScreen();
            this.setLocation(x - xMouse, y - yMouse);
        }
    }//GEN-LAST:event_Pnl_HeaderMouseDragged

    private void Btn_HomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_HomeActionPerformed
        spHome();
    }//GEN-LAST:event_Btn_HomeActionPerformed

    private void Btn_ListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ListActionPerformed
        splist();
    }//GEN-LAST:event_Btn_ListActionPerformed

    private void Btn_PayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_PayActionPerformed
        spPay();
    }//GEN-LAST:event_Btn_PayActionPerformed

    private void Btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_logoutActionPerformed
        int opt = JOptionPane.showConfirmDialog(null, "KELUAR ?", "KELUAR", JOptionPane.YES_NO_OPTION);
        if(opt==0){
            System.exit(0);
        }
    }//GEN-LAST:event_Btn_logoutActionPerformed

    private void Btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_addActionPerformed
        spdata();
    }//GEN-LAST:event_Btn_addActionPerformed

    private void Btn_addMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_addMouseClicked
        
    }//GEN-LAST:event_Btn_addMouseClicked

    private void Btn_resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_resetActionPerformed
        reset();
    }//GEN-LAST:event_Btn_resetActionPerformed

    private void Btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_saveActionPerformed
        simpandata();
    }//GEN-LAST:event_Btn_saveActionPerformed

    private void Btn_deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_deleteMouseClicked
         if("".equals(Txt_kode_baju.getText()) || "".equals(Txt_nama_baju.getText()) || "".equals(Txt_harga_baju.getText()) || "".equals(CB_kategori.getSelectedItem())){
            JOptionPane.showMessageDialog(this, "Data Tidak Di Masukkan", "ERROR", JOptionPane.WARNING_MESSAGE);
        
        }else {
            hapusData();
            reset();
        }
        
        
    }//GEN-LAST:event_Btn_deleteMouseClicked

    private void tablebajuMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablebajuMouseReleased
        if(evt.getButton() == MouseEvent.BUTTON3);{
            if(evt.isPopupTrigger() && tablebaju.getSelectedRowCount() != 0){
                klikkanan.show(evt.getComponent(), evt.getX(), evt.getY());
            }       
        }
    }//GEN-LAST:event_tablebajuMouseReleased

    private void Btn_updateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_updateMouseClicked
        updatedata();
    }//GEN-LAST:event_Btn_updateMouseClicked

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        editdata();  
    }//GEN-LAST:event_EditActionPerformed

    private void hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hapusActionPerformed
        hapusData();       
    }//GEN-LAST:event_hapusActionPerformed

    private void tablebajuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablebajuMouseClicked
        
    }//GEN-LAST:event_tablebajuMouseClicked

    private void Btn_searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_searchActionPerformed
        if("".equals(Txt_search.getText())){
            JOptionPane.showMessageDialog(this, "Data Tidak Di Masukkan", "ERROR", JOptionPane.WARNING_MESSAGE);
            daftarbaju();
        }else {
            int status = 0;
            if (status == 0){
                splist();
                caridata();
            }               
        }
    }//GEN-LAST:event_Btn_searchActionPerformed

    private void buatbaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buatbaruActionPerformed
        spdata();
    }//GEN-LAST:event_buatbaruActionPerformed

    private void Txt_searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Txt_searchKeyReleased
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if("".equals(Txt_search.getText())){
                daftarbaju();
            }else {
                int status = 0;
                if (status == 0){
                    splist();
                    caridata();
                }       
            }         
        }
    }//GEN-LAST:event_Txt_searchKeyReleased

    private void txt_kodeBayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_kodeBayarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            kodeBaju_bayar();
        }         
    }//GEN-LAST:event_txt_kodeBayarKeyPressed

    private void txt_qytKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_qytKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tambahbelanja();
        }
    }//GEN-LAST:event_txt_qytKeyPressed

    private void btn_tambahbelanjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahbelanjaActionPerformed
        if("".equals(txt_kodeBayar.getText()) || "".equals(txt_qyt.getText())){
            JOptionPane.showMessageDialog(this, "Data Tidak Di Masukkan", "ERROR", JOptionPane.WARNING_MESSAGE);
        }else{
            tambahbelanja();
            subtotal();
        }
    }//GEN-LAST:event_btn_tambahbelanjaActionPerformed

    private void btn_hapusbelanjaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusbelanjaActionPerformed
        hapusbelanja();
    }//GEN-LAST:event_btn_hapusbelanjaActionPerformed

    private void btn_bayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bayarActionPerformed
        if("".equals(txt_bayar.getText())){
            JOptionPane.showMessageDialog(this, "Data Tidak Di Masukkan", "ERROR", JOptionPane.WARNING_MESSAGE);
        }else{
            tambahbelanja();
            subtotal();
            bayar();
            pembayaran();
            stock();
            print();
        }
        
    }//GEN-LAST:event_btn_bayarActionPerformed

    private void txt_bayarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_bayarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            tambahbelanja();
            subtotal();
            bayar();
            pembayaran();
            stock();
            print();
        }
    }//GEN-LAST:event_txt_bayarKeyPressed

    private void TambahkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahkanActionPerformed
        troli();
    }//GEN-LAST:event_TambahkanActionPerformed

    private void btn_hapusbelanja1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusbelanja1ActionPerformed
        reset2();
    }//GEN-LAST:event_btn_hapusbelanja1ActionPerformed

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu().setVisible(true);
            }
        });
    }
    
    
        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Btn_Exit;
    private javax.swing.JButton Btn_Home;
    private javax.swing.JButton Btn_List;
    private javax.swing.JButton Btn_Maximize;
    private javax.swing.JButton Btn_Minimize;
    private javax.swing.JButton Btn_Pay;
    private javax.swing.JButton Btn_add;
    private javax.swing.JButton Btn_delete;
    private javax.swing.JButton Btn_help;
    private javax.swing.JButton Btn_help1;
    private javax.swing.JButton Btn_kontak;
    private javax.swing.JButton Btn_logout;
    private javax.swing.JButton Btn_reset;
    private javax.swing.JButton Btn_save;
    private javax.swing.JButton Btn_search;
    private javax.swing.JButton Btn_update;
    private javax.swing.JComboBox<String> CB_kategori;
    private javax.swing.JCheckBox Cek_L;
    private javax.swing.JCheckBox Cek_M;
    private javax.swing.JCheckBox Cek_S;
    private javax.swing.JCheckBox Cek_XL;
    private javax.swing.JMenuItem Edit;
    private javax.swing.JPanel Pnl_Header;
    private javax.swing.JPanel Pnl_Home;
    private javax.swing.JScrollPane SP_Home;
    private javax.swing.JScrollPane SP_List;
    private javax.swing.JScrollPane SP_Pay;
    private javax.swing.JScrollPane SP_data;
    private javax.swing.JMenuItem Salin;
    private javax.swing.JMenuItem Tambahkan;
    private javax.swing.JTextField Txt_harga_baju;
    private javax.swing.JTextField Txt_kode_baju;
    private javax.swing.JTextField Txt_nama_baju;
    private javax.swing.JTextField Txt_search;
    private javax.swing.JTextField Txt_stock;
    private javax.swing.JButton btn_bayar;
    private javax.swing.JButton btn_hapusbelanja;
    private javax.swing.JButton btn_hapusbelanja1;
    private javax.swing.JButton btn_tambahbelanja;
    private javax.swing.JMenuItem buatbaru;
    private javax.swing.JMenuItem hapus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPopupMenu klikkanan;
    private javax.swing.JLabel lbl_home;
    private javax.swing.JLabel lbl_jam;
    private javax.swing.JLabel lbl_list;
    private javax.swing.JLabel lbl_pay;
    private javax.swing.JLabel lbl_tgl;
    public static final javax.swing.JLabel namauser = new javax.swing.JLabel();
    private javax.swing.JTable tabel_bayar;
    private javax.swing.JTable tablebaju;
    private javax.swing.JTextField txt_bayar;
    private javax.swing.JTextField txt_hargaBayar;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_kodeBayar;
    private javax.swing.JTextField txt_namabaju;
    private javax.swing.JTextField txt_qyt;
    private javax.swing.JTextField txt_subtotal;
    // End of variables declaration//GEN-END:variables

    private void initComonents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
