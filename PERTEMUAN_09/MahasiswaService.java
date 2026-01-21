/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PERTEMUAN_09;

import java.util.ArrayList;

/**
 *
 * @author x260
 */
public interface MahasiswaService {
    public void insert(long  nim, String nama, String alamat, String jk);
    public ArrayList<Object[]> getAll();
    void update(long nim, String nama, String alamat, String jenis);
    void delete(long nim);
}
