/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas_sem3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JTextField;

/**
 *
 * @author x260
 */
public class getLogRFID {
    private String[] data;
    
    public String getLog(JTextField lblNomorKartu) {
        try {
            URL url = new URL("http://localhost/PojectUAS/getlogRFID.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            String response = reader.readLine();
            reader.close();
            conn.disconnect();

            // Contoh response: {"datarfid":"TES12"}
            response = response.replace("{", "")
                    .replace("}", "")
                    .replace("\"", "");

            data = response.split(":");

            if (data.length == 2) {
                lblNomorKartu.setText(data[1]);
            }

        } catch (Exception e) {
            System.out.println("Gagal ambil log RFID: " + e.getMessage());
        }
       return data[1];
    }
}
