/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas_sem3;

import java.sql.PreparedStatement;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class UpdateData {

    koneksi konek = new koneksi();

    public void updateMahasiswa(long nimLama, long nimBaru, String namaBaru) {
        try {
            konek.koneksi();
            String sql = "UPDATE daftar_mahasiswa " + "SET nim = ?, nama = ? " + "WHERE nim = ?";
            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setLong(1, nimBaru);     
            pst.setString(2, namaBaru);  
            pst.setLong(3, nimLama);     
            int hasil = pst.executeUpdate();
            if (hasil > 0) {
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate");
            } else {
                JOptionPane.showMessageDialog(null, "NIM lama tidak ditemukan");
            }
            pst.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void updateJadwal(String kodeJadwal,String mataKuliah,String hari,String waktuMulai,String waktuAkhir){
        try {
            konek.koneksi();
            String sql = "UPDATE jadwal_kuliah "
                    + "SET mata_kuliah = ?, hari = ?, jam_mulai = ?, jam_selesai = ? "
                    + "WHERE kode_jadwal = ?";
            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setString(1, mataKuliah);
            pst.setString(2, hari);
            pst.setString(3, waktuMulai);
            pst.setString(4, waktuAkhir);
            pst.setString(5, kodeJadwal); 
            int hasil = pst.executeUpdate();
            if (hasil > 0) {
                JOptionPane.showMessageDialog(null, "Jadwal berhasil diupdate");
            } else {
                JOptionPane.showMessageDialog(null, "Kode jadwal tidak ditemukan");
            }
            pst.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void updateAbsenKeluar(int idAbsensi) {
        getData getdata = new getData();
        try {
            konek.koneksi();
            String statusMasuk = getdata.getStatusAktif(idAbsensi);
            if (statusMasuk == null) {
                JOptionPane.showMessageDialog(null, "Status absensi tidak ditemukan");
                return;
            }
            String statusAkhir = statusMasuk + "/Pulang";
            String sql = "UPDATE absensi "
                    + "SET jam_keluar = CURRENT_TIME, status = ? "
                    + "WHERE id_absensi = ?";
            PreparedStatement pst = konek.con.prepareStatement(sql);
            pst.setString(1, statusAkhir);
            pst.setInt(2, idAbsensi);
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(null, "Absen keluar berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

}
