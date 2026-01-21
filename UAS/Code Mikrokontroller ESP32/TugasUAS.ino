#include <WiFi.h>
#include <HTTPClient.h>
#include <SPI.h>
#include <MFRC522.h>

/* ================= WIFI ================= */
const char* ssid     = "NICbot Official";
const char* password = "selamatDatang";

/* ================= API ================= */
const char* API_MAIN_URL = "http://10.217.36.45/PojectUAS/simpan_data.php";
const char* API_MAIN_KEY = "RFID_ESP32_LOCAL";

const char* API_LOG_URL  = "http://10.217.36.45/PojectUAS/log.php";
const char* API_LOG_KEY  = "LOG_RFID_ESP32_LOCAL";

/* ================= RFID ================= */
#define SS_PIN   5
#define RST_PIN  21
MFRC522 rfid(SS_PIN, RST_PIN);

/* ================= GLOBAL ================= */
String uid = "";
unsigned long lastScan = 0;
const unsigned long debounceTime = 1500; // ms

/* ================= SETUP ================= */
void setup() {
  Serial.begin(115200);
  connectWiFi();
  SPI.begin();
  rfid.PCD_Init();
  Serial.println("ESP32 RFID siap...");
}

/* ================= LOOP ================= */
void loop() {

  if (millis() - lastScan < debounceTime) return;

  if (!rfid.PICC_IsNewCardPresent()) return;
  if (!rfid.PICC_ReadCardSerial()) return;

  uid = "";
  for (byte i = 0; i < rfid.uid.size; i++) {
    if (rfid.uid.uidByte[i] < 0x10) uid += "0";
    uid += String(rfid.uid.uidByte[i], HEX);
  }
  uid.toUpperCase();
  Serial.println("UID: " + uid);
  postData(API_MAIN_URL, API_MAIN_KEY, uid);
  postData(API_LOG_URL,  API_LOG_KEY,  uid);

  rfid.PICC_HaltA();
  rfid.PCD_StopCrypto1();

  lastScan = millis();
}

/* ================= WIFI ================= */
void connectWiFi() {
  WiFi.begin(ssid, password);
  Serial.print("Menghubungkan WiFi");

  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }

  Serial.println("\nWiFi terhubung");
  Serial.println(WiFi.localIP());
}

/* ================= HTTP POST ================= */
void postData(const char* url, const char* key, String uid) {

  if (WiFi.status() != WL_CONNECTED) {
    Serial.println("WiFi terputus, reconnect...");
    connectWiFi();
    return;
  }

  HTTPClient http;
  http.begin(url);
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");

  String payload = "counter=" + uid + "&api_key=" + key;
  int code = http.POST(payload);

  Serial.print("POST -> ");
  Serial.print(url);
  Serial.print(" | Code: ");
  Serial.println(code);

  http.end();
}
