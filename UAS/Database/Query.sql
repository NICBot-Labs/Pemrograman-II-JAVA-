
CREATE DATABASE absensi_mahasiswa;

USE absensi_mahasiswa;

CREATE TABLE daftar_mahasiswa (
    nim INT PRIMARY KEY,
    nama VARCHAR(100) NOT NULL
);

CREATE TABLE jadwal_kuliah (
    kode_jadwal  VARCHAR PRIMARY KEY,
    mata_kuliah VARCHAR(100),
    hari VARCHAR(20),
    jam_mulai TIME,
    jam_selesai TIME
);

CREATE TABLE absensi (
    id_absensi INT AUTO_INCREMENT PRIMARY KEY,
    nim BIGINT NOT NULL,
    mata_kuliah VARCHAR(100) NOT NULL,
    tanggal DATE NOT NULL,
    jam_masuk TIME NOT NULL,
    jam_keluar TIME,
    STATUS VARCHAR(20)
);

