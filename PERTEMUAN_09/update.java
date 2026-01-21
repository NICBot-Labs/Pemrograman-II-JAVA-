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

public class update {

    koneksi konek = new koneksi();

//, String nama, String alamat, String jenis
    public void update(long nim, String nama, String alamat, String jenis) {

        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();
            String sql_alamat = "update datamahasiswa set alamat='" + alamat + "'where nim='" + nim + "'";
            String sql_nama = "update datamahasiswa set nama='" + nama + "'where nim='" + nim + "'";
            String sql_jenis = "update datamahasiswa set gender='" + jenis + "'where nim='" + nim + "'";

//String sql_nim = "update identitas set nim ='" + nim + "' where nim = '" + nim + "'";
            //    String sql = "update identitas set nim ='" + nim + "','" + nama + "','" + alamat + "','" + jk + "')";
            statement.executeUpdate(sql_nama);
            statement.executeUpdate(sql_alamat);
            statement.executeUpdate(sql_jenis);
            statement.close();

            JOptionPane.showMessageDialog(null, "Berhasil Diubah");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public static void main(String[] args) {
       update update = new  update ();
       update.update(2455201014L, "NIKO", "Garum", "L");
    }
}
