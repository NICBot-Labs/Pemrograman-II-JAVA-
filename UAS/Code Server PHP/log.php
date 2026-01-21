<?php
// TAMPILKAN ERROR (WAJIB UNTUK DEBUG)
ini_set('display_errors', 1);
error_reporting(E_ALL);

require_once __DIR__ . "/koneksi.php";

/* ===== API KEY ===== */
$API_KEY_SERVER = "LOG_RFID_ESP32_LOCAL";

/* ===== CEK METHOD ===== */
if ($_SERVER['REQUEST_METHOD'] !== 'POST') {
    http_response_code(405);
    exit("METHOD NOT ALLOWED");
}

/* ===== AMBIL DATA ===== */
$nomor_kartu = trim($_POST['counter'] ?? '');
$apiKey      = $_POST['api_key'] ?? '';

/* ===== VALIDASI API KEY ===== */
if ($apiKey !== $API_KEY_SERVER) {
    http_response_code(401);
    exit("INVALID API KEY");
}

/* ===== VALIDASI DATA ===== */
if ($nomor_kartu === '') {
    http_response_code(400);
    exit("DATA KOSONG");
}

$stmt = $conn->prepare(
    "INSERT INTO log_RFID_Scaan (datarfid) VALUES (?)"

);


if (!$stmt) {
    exit("PREPARE ERROR: " . $conn->error);
}

$stmt->bind_param("s", $nomor_kartu);

if ($stmt->execute()) {
    echo "OK";
} else {
    exit("EXECUTE ERROR: " . $stmt->error);
}

$stmt->close();
$conn->close();
