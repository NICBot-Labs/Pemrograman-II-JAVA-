/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas_sem3;

/**
 *
 * @author x260
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class koneksi {

    Connection con = null;

    public void koneksi() {
        try {
            String connectionURL = "jdbc:mysql://localhost/absensi_mahasiswa";
            String username = "root";
            String password = "";
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(connectionURL, username, password);
//            JOptionPane.showMessageDialog(null, "Sukses Koneksi");
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e);
            //  System.exit(0);
        }
    }

    Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
