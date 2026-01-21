<?php
require_once "koneksi.php";

$rfid = $_POST['nomor_kartu'] ?? '';

if ($rfid == '') {
    exit("RFID kosong");
}

$q = $conn->prepare("SELECT nim, namaPemilik FROM rfidterdaftar WHERE noKtm=?");
$q->bind_param("s", $rfid);
$q->execute();
$r = $q->get_result();

if ($r->num_rows == 0) {
    exit("RFID tidak terdaftar");
}

$mhs = $r->fetch_assoc();
$hari = date('l');
$jam  = date('H:i:s');

$j = $conn->prepare("
    SELECT mata_kuliah 
    FROM jadwal 
    WHERE hari=? AND ? BETWEEN jam_mulai AND jam_selesai
    LIMIT 1
");
$j->bind_param("ss", $hari, $jam);
$j->execute();
$jr = $j->get_result();

if ($jr->num_rows == 0) {
    exit("Tidak ada jadwal aktif");
}

$mk = $jr->fetch_assoc()['mata_kuliah'];

$ins = $conn->prepare("
    INSERT INTO absensi (nim, mata_kuliah, tanggal, jam_masuk, status)
    VALUES (?, ?, CURDATE(), CURTIME(), 'MASUK')
");
$ins->bind_param("is", $mhs['nim'], $mk);
$ins->execute();

echo "ABSEN BERHASIL";
