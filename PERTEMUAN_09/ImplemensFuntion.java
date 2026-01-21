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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class ImplemensFuntion implements MahasiswaService {

    koneksi konek = new koneksi();

    public void insert(long nim, String nama, String alamat, String jk) {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();
            //   String sql = "insertData into identitas (nim, nama, alamat, jeniskelamin)" + "values('" + nim + "','" + nama + "','" + alamat + "','" + jk + "')";
            String sql = "insert into datamahasiswa values('" + nim + "','" + nama + "','" + alamat + "','" + jk + "')";
            statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "DATA Dengan NIM Tersebut, Sudah Ada Sebelumnya !!");
            System.out.println("=== SQL ERROR ===");
            System.out.println("Error Code : " + ex.getErrorCode());
            System.out.println("SQL State : " + ex.getSQLState());
            System.out.println("Message   : " + ex.getMessage());
        }
    }

    public ArrayList<Object[]> getAll() {
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();

            String sql = "SELECT * FROM datamahasiswa";
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                Object[] data = new Object[4];
                data[0] = rs.getLong("nim");
                data[1] = rs.getString("nama");
                data[2] = rs.getString("alamat");
                data[3] = rs.getString("Gender");

                list.add(data);
            }

            rs.close();
            statement.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return list;
    }

    public void update(long nim, String nama, String alamat, String jenis) {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();

            String sql = "UPDATE datamahasiswa "
                    + "SET nama='" + nama + "', alamat='" + alamat + "', gender='" + jenis + "' "
                    + "WHERE nim='" + nim + "'";

            int row = statement.executeUpdate(sql);
            statement.close();

            if (row > 0) {
                JOptionPane.showMessageDialog(null, "Berhasil Diubah");
            } else {
                JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void delete(long nim) {

        try {
            int pilihan = JOptionPane.showConfirmDialog(
                    null,
                    "Apakah Anda benar-benar ingin menghapus data ini?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION
            );

            if (pilihan == JOptionPane.YES_OPTION) {
                // PROSES HAPUS
                konek.koneksi();
                Statement statement = konek.con.createStatement();

                String sql = "DELETE FROM datamahasiswa WHERE nim = '" + nim + "'";
                int row = statement.executeUpdate(sql);

                statement.close();

                if (row > 0) {
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
                } else {
                    JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Penghapusan dibatalkan");
                return;
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Data tidak ditemukan");
        }
    }

}
