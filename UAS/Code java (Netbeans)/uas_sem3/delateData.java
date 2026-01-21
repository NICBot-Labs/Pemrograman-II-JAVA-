/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas_sem3;

import java.sql.Statement;

/**
 *
 * @author x260
 */
public class delateData {

    koneksi konek = new koneksi();

    public void delete(long nim) {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();

            String sql = "delete from daftar_mahasiswa where nim = '" + nim + "'";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {

        }
    }

    public void deleteJadwal(String kode) {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();

            String sql = "delete from jadwal_kuliah where kode_jadwal = '" + kode + "'";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {

        }
    }

    public void deleteKartu(String ktm) {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();

            String sql = "delete from rfidterdaftar where nomor_kartu = '" + ktm + "'";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {

        }
    }
    
    public void deleteRiwayatAbsen() {
        try {
            konek.koneksi();
            Statement statement = konek.con.createStatement();

            String sql = "DELETE FROM absensi";
            statement.executeUpdate(sql);
            statement.close();
        } catch (Exception ex) {

        }
    }
}
