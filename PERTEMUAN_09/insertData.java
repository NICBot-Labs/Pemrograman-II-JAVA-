/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PERTEMUAN_09;

/**
 *
 * @author x260
 */
//import java.sql.PreparedStatement; 
import java.sql.Statement;
import javax.swing.JOptionPane;

public class insertData{
    koneksi konek = new koneksi();
    public void insert(long nim, String nama, String alamat, String jk) {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();
            //   String sql = "insertData into identitas (nim, nama, alamat, jeniskelamin)" + "values('" + nim + "','" + nama + "','" + alamat + "','" + jk + "')";
            String sql = "insert into datamahasiswa values('" + nim + "','" + nama + "','" + alamat + "','" + jk + "')";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
