<?php
header("Content-Type: application/json");
require_once __DIR__ . "/koneksi.php";

$sql = "
    SELECT datarfid
    FROM log_RFID_Scaan
    ORDER BY ID DESC
    LIMIT 1
";

$result = $conn->query($sql);

if ($result && $result->num_rows > 0) {
    echo json_encode($result->fetch_assoc());
} else {
    echo json_encode([
        "datarfid" => ""
    ]);
}

$conn->close();
