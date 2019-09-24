/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database;

import java.sql.*;

/**
 *
 * @author Fajar Adi Setyawan
 */
public class MainKoneksi {
    public static void main(String[] args) {
        Koneksi con = new Koneksi();
        Connection koneksi = con.getKoneksi();
    }
}
