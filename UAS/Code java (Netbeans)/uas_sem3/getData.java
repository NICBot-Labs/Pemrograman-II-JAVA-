/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas_sem3;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author x260
 */
public class getData {

    koneksi konek = new koneksi();

    public LocalTime getJamMulaiByMataKuliah(String mataKuliah) {
        LocalTime jamMulai = null;
        try {
            konek.koneksi();
            String sql = "SELECT jam_mulai FROM jadwal_kuliah WHERE mata_kuliah = ?";
            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setString(1, mataKuliah);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                jamMulai = rs.getTime("jam_mulai").toLocalTime();
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return jamMulai;
    }

    public LocalTime getJamAkhirByMataKuliah(String mataKuliah) {
        LocalTime jamAkhir = null;
        try {
            konek.koneksi();
            String sql = "SELECT jam_selesai FROM jadwal_kuliah WHERE mata_kuliah = ?";
            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setString(1, mataKuliah);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                jamAkhir = rs.getTime("jam_selesai").toLocalTime();
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return jamAkhir;
    }

    public String getStatusAktif(int idAbsensi) {
        String status = null;
        try {
            konek.koneksi();
            String sql = "SELECT status FROM absensi WHERE id_absensi = ?";
            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setInt(1, idAbsensi);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                status = rs.getString("status");
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return status;
    }

    public Integer getIdAbsenAktif(long nim, String mataKuliah) {
        Integer idAbsensi = null;

        try {
            konek.koneksi();

            String sql = "SELECT id_absensi FROM absensi "
                    + "WHERE nim = ? AND mata_kuliah = ? "
                    + "AND tanggal = CURRENT_DATE "
                    + "AND jam_keluar IS NULL";

            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setLong(1, nim);
            pst.setString(2, mataKuliah);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                idAbsensi = rs.getInt("id_absensi");
            }

            rs.close();
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return idAbsensi; // null = belum absen masuk
    }

    public boolean sudahAbsenHariIni(long nim, String mataKuliah) {
        boolean sudah = false;
        try {
            konek.koneksi();
            String sql = "SELECT id_absensi FROM absensi "
                    + "WHERE nim = ? AND mata_kuliah = ? "
                    + "AND tanggal = CURRENT_DATE";

            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setLong(1, nim);
            pst.setString(2, mataKuliah);

            ResultSet rs = pst.executeQuery();
            sudah = rs.next();
            rs.close();
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return sudah;
    }

    public void tampilStatusByNim(long nim, JLabel lblNama, JLabel lblStatus,JLabel lblJamMasuk,JLabel lblJamKeluar){
        try {
            konek.koneksi();
            String sql = "SELECT m.nama, a.status, a.jam_masuk, a.jam_keluar "
                    + "FROM absensi a "
                    + "JOIN daftar_mahasiswa m ON a.nim = m.nim "
                    + "WHERE a.nim = ? AND a.tanggal = CURRENT_DATE "
                    + "ORDER BY a.jam_masuk DESC LIMIT 1";
            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setLong(1, nim);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                lblNama.setText(rs.getString("nama"));
                lblStatus.setText(rs.getString("status"));
                lblJamMasuk.setText(
                        rs.getTime("jam_masuk") != null
                        ? rs.getTime("jam_masuk").toString()
                        : "-"
                );
                lblJamKeluar.setText(
                        rs.getTime("jam_keluar") != null
                        ? rs.getTime("jam_keluar").toString()
                        : "-"
                );
            } else {
                lblNama.setText("-");
                lblStatus.setText("-");
                lblJamMasuk.setText("-");
                lblJamKeluar.setText("-");
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
