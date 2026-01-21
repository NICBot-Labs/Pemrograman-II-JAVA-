<?php
require_once __DIR__ . "/koneksi.php";
date_default_timezone_set('Asia/Jakarta');

$API_KEY_SERVER = "RFID_ESP32_LOCAL";

/* ================= VALIDASI ================= */
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    exit("METHOD NOT ALLOWED");
}

$nomor_kartu = trim($_POST['counter'] ?? '');
$apiKey      = $_POST['api_key'] ?? '';

if ($apiKey !== $API_KEY_SERVER) {
    http_response_code(401);
    exit("INVALID API KEY");
}

if ($nomor_kartu === '') {
    http_response_code(400);
    exit("DATA KOSONG");
}

/* ================= CEK RFID ================= */
$q = $conn->prepare("SELECT nim FROM rfidterdaftar WHERE nomor_kartu=?");
$q->bind_param("s", $nomor_kartu);
$q->execute();
$r = $q->get_result();

if ($r->num_rows === 0) {
    http_response_code(403);
    exit("RFID TIDAK TERDAFTAR");
}

$nim = $r->fetch_assoc()['nim'];

/* ================= HARI & JAM ================= */
$hariMap = [
    'Monday' => 'Senin',
    'Tuesday' => 'Selasa',
    'Wednesday' => 'Rabu',
    'Thursday' => 'Kamis',
    'Friday' => 'Jumat',
    'Saturday' => 'Sabtu',
    'Sunday' => 'Minggu'
];

$hari        = $hariMap[date('l')];
$jamSekarang = date('H:i:s');

/* ================= CEK ABSEN AKTIF ================= */
$aktif = $conn->prepare("
    SELECT a.id_absensi, a.mata_kuliah, j.jam_selesai
    FROM absensi a
    JOIN jadwal_kuliah j 
      ON a.mata_kuliah = j.mata_kuliah
     AND j.hari = ?
    WHERE a.nim = ?
      AND a.tanggal = CURDATE()
      AND a.jam_keluar IS NULL
    LIMIT 1
");
$aktif->bind_param("ss", $hari, $nim);
$aktif->execute();
$resAktif = $aktif->get_result();

/* ================= ABSEN PULANG ================= */
if ($resAktif->num_rows > 0) {
    $row = $resAktif->fetch_assoc();

    if ($jamSekarang >= $row['jam_selesai']) {
        $upd = $conn->prepare("
            UPDATE absensi
            SET jam_keluar = CURTIME(),
                status = CONCAT(status,'/pulang')
            WHERE id_absensi = ?
        ");
        $upd->bind_param("i", $row['id_absensi']);
        $upd->execute();
    }
}

/* ================= CARI JADWAL AKTIF SAAT INI ================= */
$j = $conn->prepare("
    SELECT mata_kuliah, jam_mulai, jam_selesai
    FROM jadwal_kuliah
    WHERE hari = ?
      AND ? BETWEEN jam_mulai AND jam_selesai
    ORDER BY jam_mulai ASC
    LIMIT 1
");
$j->bind_param("ss", $hari, $jamSekarang);
$j->execute();
$jr = $j->get_result();

if ($jr->num_rows === 0) {
    exit("TIDAK ADA JADWAL AKTIF");
}

$jadwal      = $jr->fetch_assoc();
$mataKuliah  = $jadwal['mata_kuliah'];

/* ================= CEK SUDAH ABSEN ================= */
$cek = $conn->prepare("
    SELECT id_absensi 
    FROM absensi
    WHERE nim = ?
      AND mata_kuliah = ?
      AND tanggal = CURDATE()
");
$cek->bind_param("ss", $nim, $mataKuliah);
$cek->execute();

if ($cek->get_result()->num_rows > 0) {
    exit("SUDAH ABSEN");
}

/* ================= ABSEN MASUK ================= */
$ins = $conn->prepare("
    INSERT INTO absensi
    (nim, mata_kuliah, tanggal, jam_masuk, status)
    VALUES (?, ?, CURDATE(), CURTIME(), 'hadir')
");
$ins->bind_param("ss", $nim, $mataKuliah);
$ins->execute();

echo "ABSEN DIPROSES (PULANG + MASUK OTOMATIS)";
