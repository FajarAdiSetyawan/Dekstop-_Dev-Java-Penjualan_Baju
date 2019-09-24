
package Database;

import java.sql.*;

public class Koneksi {
    private Connection koneksi;
    public Connection getKoneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
                
        }catch (ClassNotFoundException ex) {
            System.out.println("Koneksi Error !");
        }
            
        try {
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost/penjualan_baju", "root", "");
            if(koneksi != null) {
                System.out.println("Berhasil Koneksi");
            }
        }catch (SQLException ex) {
            System.out.println("Koneksi Gagal");
        }
        return koneksi;
    }
}
