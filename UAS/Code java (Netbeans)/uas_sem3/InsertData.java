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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.swing.JOptionPane;

public class InsertData {

    koneksi konek = new koneksi();

    public void insertMahasiswa(long nim, String nama) {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();
            String sql = "insert into daftar_mahasiswa values('" + nim + "','" + nama + "')";
            statement.executeUpdate(sql);
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void insertJadwal(String codejdw, String mataKuliah, String hari, String waktuAwal, String waktuAkhir) {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();
            String sql = "insert into jadwal_kuliah values('" + codejdw + "','" + mataKuliah + "','" + hari + "','" + waktuAwal + "','" + waktuAkhir + "')";
            statement.executeUpdate(sql);
            statement.close();
            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void insertAbsensi(long nim, String mataKuliah, String status) {
        LocalDate tanggal = LocalDate.now();
        LocalTime jamMasuk = LocalTime.now();
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();

            String sql = "INSERT INTO absensi "
                    + "(nim, mata_kuliah, tanggal, jam_masuk, status) "
                    + "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setLong(1, nim);
            pst.setString(2, mataKuliah);
            pst.setDate(3, java.sql.Date.valueOf(tanggal));
            pst.setTime(4, java.sql.Time.valueOf(jamMasuk));
            pst.setString(5, status);

            pst.executeUpdate();
            pst.close();

            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "NIM Tidak terdaftar");
        }
    }

    public void insertDaftarkan(String noKtm, String namaPemilik, long nim) {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();

            String sql = "INSERT INTO rfidterdaftar "
                    + "(nomor_kartu, nama_pemilik, NIM) "
                    + "VALUES (?, ?, ?)";

            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setString(1, noKtm);
            pst.setString(2, namaPemilik);
            pst.setLong(3, nim);

            pst.executeUpdate();
            pst.close();

            JOptionPane.showMessageDialog(null, "Berhasil Disimpan");
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                JOptionPane.showMessageDialog(
                        null,
                        "ID Nomor KTM sudah terdaftar",
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }
}
