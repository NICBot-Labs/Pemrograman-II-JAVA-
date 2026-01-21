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
import java.sql.Statement;
import javax.swing.JOptionPane;

public class delate {

    koneksi konek = new koneksi();

    public void delete(long nim) {

        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();

            String sql = "delete from datamahasiswa where nim = '" + nim + "'";
            statement.executeUpdate(sql);
            statement.close();

            JOptionPane.showMessageDialog(null, "Berhasil Dihapus");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public static void main(String[] args) {
        delate hapus = new delate();
        hapus.delete(2455201014L);
    } 
}
