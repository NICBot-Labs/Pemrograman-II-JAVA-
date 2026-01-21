/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uas_sem3;

import java.awt.Color;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import javax.swing.JComboBox;
import javax.swing.JPasswordField;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author x260
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    InsertData insert_database;
    koneksi connect;
    delateData haspusData;
    UpdateData updateDat;
    getData jadwalMatkul;
    getLogRFID realtimedata;

    public GUI() {
        initComponents();
        setSize(1000, 610);
        setResizable(false);
        this.setLocationRelativeTo(this);

        connect = new koneksi();
        insert_database = new InsertData();
        haspusData = new delateData();
        updateDat = new UpdateData();
        jadwalMatkul = new getData();
        realtimedata = new getLogRFID();
        connect.koneksi();

        TableColumnModel columnModel = viewTabelJadwalU.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(40);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(120);
        columnModel.getColumn(3).setPreferredWidth(100);
        DefaultTableCellRenderer headerRenderer
                = (DefaultTableCellRenderer) viewTabelJadwalU.getTableHeader().getDefaultRenderer();

        headerRenderer.setHorizontalAlignment(JLabel.CENTER);

        Container.removeAll();
        Container.repaint();
        Container.revalidate();

        Container.add(containerJadwall);
        Container.repaint();
        Container.revalidate();
        tableJadwal();

        getNimNew.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();// PERINTAH UNTUK TEXT FIELD AGAR HURUF TIDAK MASUK
                }
            }
        });
        getNimDelate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();// PERINTAH UNTUK TEXT FIELD AGAR HURUF TIDAK MASUK
                }
            }
        });
        getNimNewedit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();// PERINTAH UNTUK TEXT FIELD AGAR HURUF TIDAK MASUK
                }
            }
        });
        getNimOldedit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();// PERINTAH UNTUK TEXT FIELD AGAR HURUF TIDAK MASUK
                }
            }
        });
        getJmAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();// PERINTAH UNTUK TEXT FIELD AGAR HURUF TIDAK MASUK
                }
            }
        });
        getJmAkhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();// PERINTAH UNTUK TEXT FIELD AGAR HURUF TIDAK MASUK
                }
            }
        });
        getMnAwal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();// PERINTAH UNTUK TEXT FIELD AGAR HURUF TIDAK MASUK
                }
            }
        });
        getMnAkhir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();// PERINTAH UNTUK TEXT FIELD AGAR HURUF TIDAK MASUK
                }
            }
        });

        new javax.swing.Timer(1000, e -> {
            realtimedata.getLog(getData);
        }).start();

        new javax.swing.Timer(1000, e -> {
            tampilRiwayatAbsensi();
        }).start();
    }

    private boolean accessAdmin() {
        JPasswordField pf = new JPasswordField();
        int ok = JOptionPane.showConfirmDialog(
                this,
                pf,
                "Masukkan password admin:",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.WARNING_MESSAGE
        );
        if (ok != JOptionPane.OK_OPTION) {
            return false;
        }
        String password = new String(pf.getPassword());
        if (!password.equals("123")) {
            JOptionPane.showMessageDialog(
                    this,
                    "Password salah!",
                    "Akses Ditolak",
                    JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

    private void tableMahasiswa() {
        try {
            String sql = "SELECT nim AS 'NIM', nama AS 'NAMA' FROM daftar_mahasiswa";
            PreparedStatement pst = connect.con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            viewTabelMahasiswa.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void tableJadwalSimple() {
        try {
            String sql = "SELECT "
                    + "kode_jadwal AS 'Kode Jadwal', "
                    + "mata_kuliah AS 'Mata Kuliah', "
                    + "hari AS 'Hari', "
                    + "jam_mulai AS 'Waktu Mulai', "
                    + "jam_selesai AS 'Waktu Akhir'"
                    + "FROM jadwal_kuliah";

            PreparedStatement pst = connect.con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            viewJadwalSimple.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private void tableJadwal() {
        try {
            String sql = "SELECT "
                    + "kode_jadwal AS 'Kode Jadwal', "
                    + "mata_kuliah AS 'Mata Kuliah', "
                    + "hari AS 'Hari', "
                    + "jam_mulai AS 'Waktu Mulai', "
                    + "jam_selesai AS 'Waktu Akhir'"
                    + "FROM jadwal_kuliah";

            PreparedStatement pst = connect.con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            viewTabelJadwalU.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void tampilRiwayatAbsensi() {
        try {
            String sql = "SELECT "
                    + "m.nama AS 'Nama', "
                    + "a.nim AS 'NIM', "
                    + "a.mata_kuliah AS 'Mata Kuliah', "
                    + "a.tanggal AS 'Tanggal', "
                    + "a.jam_masuk AS 'Jam Masuk', "
                    + "a.jam_keluar AS 'Jam Keluar', "
                    + "a.status AS 'Status' "
                    + "FROM absensi a "
                    + "JOIN daftar_mahasiswa m ON a.nim = m.nim "
                    + "ORDER BY a.tanggal DESC, a.jam_masuk DESC";

            PreparedStatement pst = connect.con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            tabelRiwayat.setModel(DbUtils.resultSetToTableModel(rs));

            rs.close();
            pst.close();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void tampildaftar() {
        try {
            String sql = "SELECT "
                    + "nomor_kartu AS 'NO KARTU KTM', "
                    + "nama_pemilik AS 'NAMA PEMILIK', "
                    + "NIM AS 'NIM' "
                    + "FROM rfidterdaftar";

            PreparedStatement pst = connect.con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            viewDaftarKTM.setModel(DbUtils.resultSetToTableModel(rs));

            rs.close();
            pst.close();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, e.getMessage());
        }

    }

    private void isiWaktuKeTextField(String waktuMulai, String waktuAkhir) {
        String[] mulai = waktuMulai.split(":");
        String[] akhir = waktuAkhir.split(":");
        getJmAwal.setText(mulai[0]);
        getMnAwal.setText(mulai[1]);
        getJmAkhir.setText(akhir[0]);
        getMnAkhir.setText(akhir[1]);
    }

    private void loadMataKuliah(JComboBox<String> combo) {
        try {
            connect.koneksi();
            combo.removeAllItems();
            combo.addItem("-- Pilih Mata Kuliah --");
            String sql = "SELECT mata_kuliah FROM jadwal_kuliah ORDER BY mata_kuliah";
            PreparedStatement pst = connect.con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                combo.addItem(rs.getString("mata_kuliah"));
            }
            rs.close();
            pst.close();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public boolean sudahAbsenMasuk(long nim, String mataKuliah) {
        boolean sudah = false;
        try {
            connect.koneksi();
            String sql = "SELECT id_absensi FROM absensi "
                    + "WHERE nim = ? AND mata_kuliah = ? "
                    + "AND tanggal = CURRENT_DATE "
                    + "AND jam_keluar IS NULL";
            PreparedStatement pst = connect.con.prepareStatement(sql);
            pst.setLong(1, nim);
            pst.setString(2, mataKuliah);
            ResultSet rs = pst.executeQuery();
            sudah = rs.next();
            rs.close();
            pst.close();
        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return sudah;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        ContainerUtama = new javax.swing.JPanel();
        Header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Navbar = new javax.swing.JPanel();
        jadwal = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Absensi = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rfid = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        daftarKTM = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        Container = new javax.swing.JPanel();
        containerJadwall = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        viewTabelJadwalU = new javax.swing.JTable();
        containerAbsen = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        viewNameInfo = new javax.swing.JLabel();
        viewStatusInfo = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        viewJMOutInfo = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        viewJMInInfo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        getNimAbsenMasuk = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        mataKuliahIn = new javax.swing.JComboBox<>();
        tbAbsenMasuk = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelRiwayat = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        getNimAbsenKeluar = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        mataKuliahOut = new javax.swing.JComboBox<>();
        tbAbsenOut = new javax.swing.JButton();
        delateRiwayat = new javax.swing.JToggleButton();
        containerData = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        getNimNew = new javax.swing.JTextField();
        getNameNew = new javax.swing.JTextField();
        insert = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        viewNameHaspusdata = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        getNimDelate = new javax.swing.JTextField();
        delate = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        getNimOldedit = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        getNimNewedit = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        getNameNewedit = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        getKdJdwNew = new javax.swing.JTextField();
        insertJadwal = new javax.swing.JButton();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        getMtkuliahNew = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        getHariNew = new javax.swing.JComboBox<>();
        jLabel72 = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        getMnAkhir = new javax.swing.JTextField();
        getJmAkhir = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        getJmAwal = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        getMnAwal = new javax.swing.JTextField();
        updateJadwal = new javax.swing.JButton();
        delateJadwal = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        viewJadwalSimple = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        viewTabelMahasiswa = new javax.swing.JTable();
        containerKTP = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        simpanNim = new javax.swing.JTextField();
        simpanRFIFD = new javax.swing.JTextField();
        simpanNama = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        viewDaftarKTM = new javax.swing.JTable();
        loaddata = new javax.swing.JButton();
        daftarkan = new javax.swing.JButton();
        delateDataKTP2 = new javax.swing.JButton();
        jLabel42 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        getData = new javax.swing.JTextField();

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        ContainerUtama.setBackground(new java.awt.Color(0, 0, 0));
        ContainerUtama.setPreferredSize(new java.awt.Dimension(1000, 600));

        Header.setBackground(new java.awt.Color(7, 117, 158));
        Header.setPreferredSize(new java.awt.Dimension(1000, 63));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/app.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(248, 247, 255));
        jLabel2.setText("ABSENSI MAHASISWA ILMU KOMPUTER");

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap(638, Short.MAX_VALUE))
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        Navbar.setBackground(new java.awt.Color(18, 29, 40));
        Navbar.setPreferredSize(new java.awt.Dimension(203, 531));

        jadwal.setBackground(new java.awt.Color(18, 29, 40));
        jadwal.setPreferredSize(new java.awt.Dimension(203, 39));
        jadwal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jadwalMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jadwalMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jadwalMouseExited(evt);
            }
        });

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/schedule.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(248, 247, 255));
        jLabel4.setText("Jadwal Kuliah");

        javax.swing.GroupLayout jadwalLayout = new javax.swing.GroupLayout(jadwal);
        jadwal.setLayout(jadwalLayout);
        jadwalLayout.setHorizontalGroup(
            jadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jadwalLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(65, Short.MAX_VALUE))
        );
        jadwalLayout.setVerticalGroup(
            jadwalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        Absensi.setBackground(new java.awt.Color(18, 29, 40));
        Absensi.setPreferredSize(new java.awt.Dimension(203, 39));
        Absensi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbsensiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                AbsensiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                AbsensiMouseExited(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/avatar.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(248, 247, 255));
        jLabel6.setText("Absensi Mahasiswa");

        javax.swing.GroupLayout AbsensiLayout = new javax.swing.GroupLayout(Absensi);
        Absensi.setLayout(AbsensiLayout);
        AbsensiLayout.setHorizontalGroup(
            AbsensiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AbsensiLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addContainerGap(35, Short.MAX_VALUE))
        );
        AbsensiLayout.setVerticalGroup(
            AbsensiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        rfid.setBackground(new java.awt.Color(18, 29, 40));
        rfid.setPreferredSize(new java.awt.Dimension(203, 39));
        rfid.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rfidMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rfidMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                rfidMouseExited(evt);
            }
        });

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/folder.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(248, 247, 255));
        jLabel8.setText("Data Mahasiswa");

        javax.swing.GroupLayout rfidLayout = new javax.swing.GroupLayout(rfid);
        rfid.setLayout(rfidLayout);
        rfidLayout.setHorizontalGroup(
            rfidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rfidLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel7)
                .addGap(18, 18, 18)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );
        rfidLayout.setVerticalGroup(
            rfidLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        daftarKTM.setBackground(new java.awt.Color(18, 29, 40));
        daftarKTM.setPreferredSize(new java.awt.Dimension(203, 39));
        daftarKTM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                daftarKTMMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                daftarKTMMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                daftarKTMMouseExited(evt);
            }
        });

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/credit-card.png"))); // NOI18N

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(248, 247, 255));
        jLabel19.setText("Daftarkan KTM");

        javax.swing.GroupLayout daftarKTMLayout = new javax.swing.GroupLayout(daftarKTM);
        daftarKTM.setLayout(daftarKTMLayout);
        daftarKTMLayout.setHorizontalGroup(
            daftarKTMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(daftarKTMLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addContainerGap(54, Short.MAX_VALUE))
        );
        daftarKTMLayout.setVerticalGroup(
            daftarKTMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
            .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout NavbarLayout = new javax.swing.GroupLayout(Navbar);
        Navbar.setLayout(NavbarLayout);
        NavbarLayout.setHorizontalGroup(
            NavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavbarLayout.createSequentialGroup()
                .addGroup(NavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(NavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jadwal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Absensi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(rfid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(daftarKTM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        NavbarLayout.setVerticalGroup(
            NavbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(NavbarLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jadwal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Absensi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(daftarKTM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(rfid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Container.setBackground(new java.awt.Color(248, 247, 255));
        Container.setPreferredSize(new java.awt.Dimension(100, 531));
        Container.setLayout(new java.awt.CardLayout());

        containerJadwall.setBackground(new java.awt.Color(248, 247, 255));
        containerJadwall.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("JADWAL  MATA KULAIH SEMESTER 03");
        containerJadwall.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, -1, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Prodi Ilmu Komputer");
        containerJadwall.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 40, -1, -1));

        viewTabelJadwalU.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No", "Matkul", "Hari", "JAM"
            }
        ));
        jScrollPane1.setViewportView(viewTabelJadwalU);

        containerJadwall.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 750, 280));

        Container.add(containerJadwall, "card2");

        containerAbsen.setBackground(new java.awt.Color(248, 247, 255));
        containerAbsen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel13.setText("ABSENSI MAHASISWA");
        containerAbsen.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, -1, -1));

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Prodi Ilmu Komputer");
        containerAbsen.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 40, -1, -1));

        jPanel1.setBackground(new java.awt.Color(248, 247, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(248, 247, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Status", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText(":");
        jLabel20.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 10, 20));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Satus");
        jLabel17.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 40, 20));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("NAMA");
        jLabel21.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 40, 20));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText(":");
        jLabel22.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 10, 20));

        viewNameInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        viewNameInfo.setText("-");
        viewNameInfo.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(viewNameInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 120, 20));

        viewStatusInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        viewStatusInfo.setText("-");
        viewStatusInfo.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(viewStatusInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 110, 20));

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel33.setText("In");
        jLabel33.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 50, 20));

        viewJMOutInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        viewJMOutInfo.setText("-");
        viewJMOutInfo.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(viewJMOutInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 110, 20));

        jLabel35.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel35.setText(":");
        jLabel35.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 10, 20));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setText("Out");
        jLabel36.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 50, 20));

        jLabel37.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel37.setText(":");
        jLabel37.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 10, 20));

        viewJMInInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        viewJMInInfo.setText("-");
        viewJMInInfo.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel2.add(viewJMInInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 60, 110, 20));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 0, 230, 120));

        jPanel3.setBackground(new java.awt.Color(248, 247, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Absen Masuk", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText(":");
        jLabel23.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel3.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 20, 20));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("Mata Kuliah");
        jLabel24.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel3.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, 20));

        getNimAbsenMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNimAbsenMasukActionPerformed(evt);
            }
        });
        jPanel3.add(getNimAbsenMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 150, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("NIM");
        jLabel25.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel3.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 70, 20));

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setText(":");
        jLabel26.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel3.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 20, 20));

        mataKuliahIn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Mata Kuliah --" }));
        jPanel3.add(mataKuliahIn, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 150, 20));

        tbAbsenMasuk.setText("ABSENSI MASUK");
        tbAbsenMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbAbsenMasukActionPerformed(evt);
            }
        });
        jPanel3.add(tbAbsenMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 230, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 120));

        tabelRiwayat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama", "NIM", "Mata Kuliah", "Tanggal", "Jam Masuk", "Jam Keluar", "Status"
            }
        ));
        tabelRiwayat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelRiwayatMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelRiwayat);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 750, 220));

        jPanel6.setBackground(new java.awt.Color(248, 247, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Absen Keluar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setText(":");
        jLabel27.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel6.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 50, 20, 20));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setText("Mata Kuliah");
        jLabel28.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel6.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, 20));

        getNimAbsenKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNimAbsenKeluarActionPerformed(evt);
            }
        });
        jPanel6.add(getNimAbsenKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 150, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("NIM");
        jLabel29.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel6.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 70, 20));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText(":");
        jLabel30.setPreferredSize(new java.awt.Dimension(19, 40));
        jPanel6.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 20, 20));

        mataKuliahOut.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-- Pilih Mata Kuliah --" }));
        jPanel6.add(mataKuliahOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 150, 20));

        tbAbsenOut.setText("ABSENSI KALUAR");
        tbAbsenOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tbAbsenOutActionPerformed(evt);
            }
        });
        jPanel6.add(tbAbsenOut, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 230, 30));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 0, 250, 120));

        delateRiwayat.setText("HAPUS SEMUA ABSENSI");
        delateRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delateRiwayatActionPerformed(evt);
            }
        });
        jPanel1.add(delateRiwayat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 360, 180, 30));

        containerAbsen.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 750, 430));

        Container.add(containerAbsen, "card3");

        containerData.setBackground(new java.awt.Color(248, 247, 255));
        containerData.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(18, 29, 40)));
        containerData.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("REGISTER DATA MAHASISWA");
        containerData.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Prodi Ilmu Komputer");
        containerData.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 40, -1, -1));

        jPanel8.setBackground(new java.awt.Color(248, 247, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tambah Data Mahasiswa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel43.setText("NIM");
        jPanel8.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 40, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel44.setText("NAMA");
        jPanel8.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 40, -1));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setText(":");
        jPanel8.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 10, -1));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setText(":");
        jPanel8.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 10, -1));

        getNimNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNimNewActionPerformed(evt);
            }
        });
        jPanel8.add(getNimNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 170, -1));

        getNameNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNameNewActionPerformed(evt);
            }
        });
        jPanel8.add(getNameNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 170, -1));

        insert.setText("TAMBAHKAN");
        insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertActionPerformed(evt);
            }
        });
        jPanel8.add(insert, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 220, -1));

        containerData.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 240, 130));

        jPanel9.setBackground(new java.awt.Color(248, 247, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hapus Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel48.setText("NAMA");
        jPanel9.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 40, -1));

        viewNameHaspusdata.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        viewNameHaspusdata.setText("-");
        jPanel9.add(viewNameHaspusdata, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 170, -1));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setText(":");
        jPanel9.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 10, -1));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setText(":");
        jPanel9.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 10, -1));

        getNimDelate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNimDelateActionPerformed(evt);
            }
        });
        jPanel9.add(getNimDelate, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 170, -1));

        delate.setText("HAPUS");
        delate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delateActionPerformed(evt);
            }
        });
        jPanel9.add(delate, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 220, -1));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel52.setText("NIM");
        jPanel9.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 40, -1));

        containerData.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 240, 130));

        jPanel10.setBackground(new java.awt.Color(248, 247, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hapus Data", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel57.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel57.setText(":");
        jPanel10.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 10, -1));

        getNimOldedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNimOldeditActionPerformed(evt);
            }
        });
        jPanel10.add(getNimOldedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 140, -1));

        update.setText("UPDATE DATA MAHASISWA");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel10.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 220, -1));

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel58.setText("NIM Lama");
        jPanel10.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 60, -1));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel59.setText("NIM Baru");
        jPanel10.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 60, -1));

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel60.setText(":");
        jPanel10.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 10, -1));

        getNimNewedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNimNeweditActionPerformed(evt);
            }
        });
        jPanel10.add(getNimNewedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 140, -1));

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel61.setText("NAMA Baru");
        jPanel10.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 70, -1));

        jLabel62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel62.setText(":");
        jPanel10.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, 10, -1));

        getNameNewedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getNameNeweditActionPerformed(evt);
            }
        });
        jPanel10.add(getNameNewedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 140, -1));

        containerData.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 240, 160));

        jPanel11.setBackground(new java.awt.Color(248, 247, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jadwal Mata Kuliah", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel66.setText(":");
        jPanel11.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 10, -1));

        getKdJdwNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getKdJdwNewActionPerformed(evt);
            }
        });
        jPanel11.add(getKdJdwNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 30, 160, -1));

        insertJadwal.setText("TAMBAH JADWAL MATA KULIAH");
        insertJadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertJadwalActionPerformed(evt);
            }
        });
        jPanel11.add(insertJadwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 90, 220, -1));

        jLabel67.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel67.setText("Kode Jadwal");
        jPanel11.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 80, -1));

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel68.setText("Mata Kuliah");
        jPanel11.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 80, -1));

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel69.setText(":");
        jPanel11.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 10, -1));

        getMtkuliahNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getMtkuliahNewActionPerformed(evt);
            }
        });
        jPanel11.add(getMtkuliahNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 160, -1));

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel70.setText("hari");
        jPanel11.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 70, -1));

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel71.setText(":");
        jPanel11.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, 10, -1));

        getHariNew.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Senin", "Selasa", "Rabu", "Kamis", "Jum'at" }));
        jPanel11.add(getHariNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, 160, -1));

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel72.setText("Waktu Mulai");
        jPanel11.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 70, -1));

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel73.setText("Waktu Akhir");
        jPanel11.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 60, 70, -1));

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel74.setText(":");
        jPanel11.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 20, -1));

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel75.setText(":");
        jPanel11.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 10, 20));

        getMnAkhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getMnAkhirActionPerformed(evt);
            }
        });
        jPanel11.add(getMnAkhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 60, 40, -1));

        getJmAkhir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getJmAkhirActionPerformed(evt);
            }
        });
        jPanel11.add(getJmAkhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 60, 40, -1));

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel76.setText(":");
        jPanel11.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 20, -1));

        getJmAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getJmAwalActionPerformed(evt);
            }
        });
        jPanel11.add(getJmAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, 40, -1));

        jLabel77.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel77.setText(":");
        jPanel11.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 30, 10, 20));

        getMnAwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getMnAwalActionPerformed(evt);
            }
        });
        jPanel11.add(getMnAwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 30, 40, -1));

        updateJadwal.setText("UPDATE JADWAL MATA KULIAH");
        updateJadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateJadwalActionPerformed(evt);
            }
        });
        jPanel11.add(updateJadwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 120, 220, -1));

        delateJadwal.setText("HAPUS BERDASARKAN KODE JADWAL");
        delateJadwal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delateJadwalActionPerformed(evt);
            }
        });
        jPanel11.add(delateJadwal, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 250, -1));

        containerData.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 60, 500, 160));

        viewJadwalSimple.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode", "Mata Kuliah", "Hari", "Waktu Mulai", "Waktu Akhir"
            }
        ));
        viewJadwalSimple.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewJadwalSimpleMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(viewJadwalSimple);

        containerData.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 500, 130));

        viewTabelMahasiswa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIM", "Mahasiswa"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        viewTabelMahasiswa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewTabelMahasiswaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(viewTabelMahasiswa);

        containerData.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 360, 500, 130));

        Container.add(containerData, "card4");

        containerKTP.setBackground(new java.awt.Color(248, 247, 255));
        containerKTP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel15.setText("DAFTARKAN KTM UNUK ABSEN YANG CEPAT");
        containerKTP.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 12, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Prodi Ilmu Komputer");
        containerKTP.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(11, 40, -1, -1));

        jPanel4.setBackground(new java.awt.Color(248, 247, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Daftar KTM Anda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setText("NIM yang didaftarkan");
        jPanel4.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, 30));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setText("Tempelkan ID NOMOR KARTU Anda disini");
        jPanel4.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 20));

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setText("Nama yang didaftarkan");
        jPanel4.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 30));

        simpanNim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanNimActionPerformed(evt);
            }
        });
        jPanel4.add(simpanNim, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 440, 30));

        simpanRFIFD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanRFIFDActionPerformed(evt);
            }
        });
        jPanel4.add(simpanRFIFD, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 40, 440, 30));

        simpanNama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpanNamaActionPerformed(evt);
            }
        });
        jPanel4.add(simpanNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 440, 30));

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel39.setText(":");
        jPanel4.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 40, 20, 20));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel40.setText(":");
        jPanel4.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 80, 20, 30));

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setText(":");
        jPanel4.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 120, 20, 30));

        viewDaftarKTM.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID NOMOR KARTU", "NAMA PEMILIK", "NIM PEMILIK"
            }
        ));
        viewDaftarKTM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewDaftarKTMMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(viewDaftarKTM);

        jPanel4.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 710, 140));

        loaddata.setText("LOAD DATA");
        loaddata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loaddataActionPerformed(evt);
            }
        });
        jPanel4.add(loaddata, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 320, 130, -1));

        daftarkan.setText("DAFTARKAN");
        daftarkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                daftarkanActionPerformed(evt);
            }
        });
        jPanel4.add(daftarkan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 130, -1));

        delateDataKTP2.setText("HAPUS DATA");
        delateDataKTP2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delateDataKTP2ActionPerformed(evt);
            }
        });
        jPanel4.add(delateDataKTP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 320, 130, -1));

        containerKTP.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 750, 370));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel42.setText("HASIL SCAAN ID NOMOR KARTU KTM ANDA SAAT INI  :");
        containerKTP.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jPanel5.setBackground(new java.awt.Color(18, 29, 40));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        getData.setBackground(new java.awt.Color(18, 29, 40));
        getData.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        getData.setForeground(new java.awt.Color(248, 247, 255));
        getData.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        getData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getDataActionPerformed(evt);
            }
        });
        jPanel5.add(getData, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 380, 50));

        containerKTP.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 50, 380, 50));

        Container.add(containerKTP, "card2");

        javax.swing.GroupLayout ContainerUtamaLayout = new javax.swing.GroupLayout(ContainerUtama);
        ContainerUtama.setLayout(ContainerUtamaLayout);
        ContainerUtamaLayout.setHorizontalGroup(
            ContainerUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ContainerUtamaLayout.createSequentialGroup()
                .addComponent(Navbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ContainerUtamaLayout.setVerticalGroup(
            ContainerUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContainerUtamaLayout.createSequentialGroup()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(ContainerUtamaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Navbar, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE)
                    .addComponent(Container, javax.swing.GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ContainerUtama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ContainerUtama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jadwalMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jadwalMouseEntered
        // TODO add your handling code here:
        jadwal.setBackground(new Color(30, 50, 70)); // warna hover
    }//GEN-LAST:event_jadwalMouseEntered

    private void jadwalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jadwalMouseExited
        // TODO add your handling code here:
        jadwal.setBackground(new Color(18, 29, 40)); // warna normal
    }//GEN-LAST:event_jadwalMouseExited

    private void AbsensiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbsensiMouseEntered
        // TODO add your handling code here:
        Absensi.setBackground(new Color(30, 50, 70)); // warna hover
    }//GEN-LAST:event_AbsensiMouseEntered

    private void AbsensiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbsensiMouseExited
        // TODO add your handling code here:
        Absensi.setBackground(new Color(18, 29, 40)); // warna normal
    }//GEN-LAST:event_AbsensiMouseExited

    private void rfidMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rfidMouseEntered
        // TODO add your handling code here:
        rfid.setBackground(new Color(30, 50, 70)); // warna hover
    }//GEN-LAST:event_rfidMouseEntered

    private void rfidMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rfidMouseExited
        // TODO add your handling code here:
        rfid.setBackground(new Color(18, 29, 40)); // warna normal
    }//GEN-LAST:event_rfidMouseExited

    private void jadwalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jadwalMouseClicked
        // TODO add your handling code here:
        tableJadwal();
        Container.removeAll();
        Container.repaint();
        Container.revalidate();

        Container.add(containerJadwall);
        Container.repaint();
        Container.revalidate();

    }//GEN-LAST:event_jadwalMouseClicked

    private void AbsensiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbsensiMouseClicked
        // TODO add your handling code here:
        loadMataKuliah(mataKuliahOut);
        loadMataKuliah(mataKuliahIn);
        tampilRiwayatAbsensi();

        Container.removeAll();
        Container.repaint();
        Container.revalidate();

        Container.add(containerAbsen);
        Container.repaint();
        Container.revalidate();
    }//GEN-LAST:event_AbsensiMouseClicked

    private void rfidMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rfidMouseClicked
        // TODO add your handling code here:
        boolean condition = accessAdmin();
        if (condition == true) {
            tableMahasiswa();
            tableJadwalSimple();

            Container.removeAll();
            Container.repaint();
            Container.revalidate();

            Container.add(containerData);
            Container.repaint();
            Container.revalidate();
        }
    }//GEN-LAST:event_rfidMouseClicked

    private void getNimAbsenMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNimAbsenMasukActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getNimAbsenMasukActionPerformed

    private void getNimAbsenKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNimAbsenKeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getNimAbsenKeluarActionPerformed

    private void getNimNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNimNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getNimNewActionPerformed

    private void getNameNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNameNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getNameNewActionPerformed

    private void getNimOldeditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNimOldeditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getNimOldeditActionPerformed

    private void getNimNeweditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNimNeweditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getNimNeweditActionPerformed

    private void getNameNeweditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNameNeweditActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getNameNeweditActionPerformed

    private void getKdJdwNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getKdJdwNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getKdJdwNewActionPerformed

    private void getMtkuliahNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getMtkuliahNewActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getMtkuliahNewActionPerformed

    private void getMnAkhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getMnAkhirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getMnAkhirActionPerformed

    private void insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertActionPerformed

        long nim = Long.parseLong(getNimNew.getText().trim());
        String nama = getNameNew.getText().trim();
        if (getNimNew.getText().isEmpty() || nama.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "NIM dan Nama tidak boleh kosong!");
            return;
        } else {
            System.out.println("NIM  : " + nim);
            System.out.println("Nama : " + nama);

            insert_database.insertMahasiswa(nim, nama);
            tableMahasiswa();
        }

    }//GEN-LAST:event_insertActionPerformed

    private void getNimDelateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getNimDelateActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_getNimDelateActionPerformed

    private void delateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delateActionPerformed
        // TODO add your handling code here:
        long nim = Long.parseLong(getNimDelate.getText().trim());

        if (getNimDelate.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "NIM tidak boleh kosong!");
            return;
        }
        haspusData.delete(nim);
        tableMahasiswa();

    }//GEN-LAST:event_delateActionPerformed

    private void viewTabelMahasiswaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewTabelMahasiswaMouseClicked
        // TODO add your handling code here:
        int row = viewTabelMahasiswa.getSelectedRow();
        if (row == -1) {
            return;
        }
        String nim = viewTabelMahasiswa.getValueAt(row, 0).toString();
        String nama = viewTabelMahasiswa.getValueAt(row, 1).toString();

        getNimDelate.setText(nim);
        viewNameHaspusdata.setText(nama);

        getNimOldedit.setText(nim);
        getNimNewedit.setText(nim);
        getNameNewedit.setText(nama);

    }//GEN-LAST:event_viewTabelMahasiswaMouseClicked

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // TODO add your handling code here:
        long nimOld = Long.parseLong(getNimOldedit.getText().trim());
        long nimNew = Long.parseLong(getNimNewedit.getText().trim());
        String nama = getNameNewedit.getText();

        if (getNimOldedit.getText().isEmpty() || nama.isEmpty() || getNimNewedit.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "NIM dan Nama tidak boleh kosong!");
            return;
        } else {

            updateDat.updateMahasiswa(nimOld, nimNew, nama);
            tableMahasiswa();
        }
    }//GEN-LAST:event_updateActionPerformed

    private void insertJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertJadwalActionPerformed
        // TODO add your handling code here:
        String kodeJadwal = getKdJdwNew.getText().trim();
        String mataKuliah = getMtkuliahNew.getText().trim();
        String hari = getHariNew.getSelectedItem().toString();

        String jamMulaiStr = getJmAwal.getText().trim();
        String menitMulaiStr = getMnAwal.getText().trim();
        String jamAkhirStr = getJmAkhir.getText().trim();
        String menitAkhirStr = getMnAkhir.getText().trim();

        if (kodeJadwal.isEmpty()
                || mataKuliah.isEmpty()
                || hari.isEmpty()
                || jamMulaiStr.isEmpty()
                || menitMulaiStr.isEmpty()
                || jamAkhirStr.isEmpty()
                || menitAkhirStr.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Semua data jadwal wajib di isi...!!",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int jamMulai, menitMulai, jamAkhir, menitAkhir;
        try {
            jamMulai = Integer.parseInt(jamMulaiStr);
            menitMulai = Integer.parseInt(menitMulaiStr);
            jamAkhir = Integer.parseInt(jamAkhirStr);
            menitAkhir = Integer.parseInt(menitAkhirStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Jam dan menit harus berupa angka!",
                    "Input Salah",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (jamMulai < 0 || jamMulai > 23
                || jamAkhir < 0 || jamAkhir > 23
                || menitMulai < 0 || menitMulai > 59
                || menitAkhir < 0 || menitAkhir > 59) {

            JOptionPane.showMessageDialog(this,
                    "Format waktu tidak valid (HH:MM)",
                    "Validasi Waktu",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (jamMulai > jamAkhir
                || (jamMulai == jamAkhir && menitMulai >= menitAkhir)) {

            JOptionPane.showMessageDialog(this,
                    "Waktu mulai harus lebih kecil dari waktu akhir",
                    "Validasi Waktu",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String waktuMulai = String.format("%02d:%02d", jamMulai, menitMulai);
        String waktuAkhir = String.format("%02d:%02d", jamAkhir, menitAkhir);
//        System.out.println("Kode Jadwal : " + kodeJadwal);
//        System.out.println("Mata Kuliah : " + mataKuliah);
//        System.out.println("Hari        : " + hari);
//        System.out.println("Waktu Mulai : " + waktuMulai);
//        System.out.println("Waktu Akhir : " + waktuAkhir);
        insert_database.insertJadwal(kodeJadwal, mataKuliah, hari, waktuMulai, waktuAkhir);
        tableJadwalSimple();


    }//GEN-LAST:event_insertJadwalActionPerformed

    private void getJmAkhirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getJmAkhirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getJmAkhirActionPerformed

    private void getJmAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getJmAwalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getJmAwalActionPerformed

    private void getMnAwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getMnAwalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getMnAwalActionPerformed

    private void viewJadwalSimpleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewJadwalSimpleMouseClicked
        // TODO add your handling code here:
        int row = viewJadwalSimple.getSelectedRow();
        if (row == -1) {
            return;
        }
        String kodeJadwal = viewJadwalSimple.getValueAt(row, 0).toString();
        String mataKuliah = viewJadwalSimple.getValueAt(row, 1).toString();
        String hari = viewJadwalSimple.getValueAt(row, 2).toString();
        String waktuMulai = viewJadwalSimple.getValueAt(row, 3).toString();
        String waktuAkhir = viewJadwalSimple.getValueAt(row, 4).toString();

        getKdJdwNew.setText(kodeJadwal);
        getMtkuliahNew.setText(mataKuliah);
        getHariNew.setSelectedItem(hari);

        isiWaktuKeTextField(waktuMulai, waktuAkhir);
    }//GEN-LAST:event_viewJadwalSimpleMouseClicked

    private void updateJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateJadwalActionPerformed

        String kodeJadwal = getKdJdwNew.getText().trim();
        String mataKuliah = getMtkuliahNew.getText().trim();
        String hari = getHariNew.getSelectedItem().toString();

        String jamMulaiStr = getJmAwal.getText().trim();
        String menitMulaiStr = getMnAwal.getText().trim();
        String jamAkhirStr = getJmAkhir.getText().trim();
        String menitAkhirStr = getMnAkhir.getText().trim();

        if (kodeJadwal.isEmpty()
                || mataKuliah.isEmpty()
                || hari.isEmpty()
                || jamMulaiStr.isEmpty()
                || menitMulaiStr.isEmpty()
                || jamAkhirStr.isEmpty()
                || menitAkhirStr.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Semua data jadwal wajib di isi...!!",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int jamMulai, menitMulai, jamAkhir, menitAkhir;
        try {
            jamMulai = Integer.parseInt(jamMulaiStr);
            menitMulai = Integer.parseInt(menitMulaiStr);
            jamAkhir = Integer.parseInt(jamAkhirStr);
            menitAkhir = Integer.parseInt(menitAkhirStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Jam dan menit harus berupa angka!",
                    "Input Salah",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (jamMulai < 0 || jamMulai > 23
                || jamAkhir < 0 || jamAkhir > 23
                || menitMulai < 0 || menitMulai > 59
                || menitAkhir < 0 || menitAkhir > 59) {

            JOptionPane.showMessageDialog(this,
                    "Format waktu tidak valid (HH:MM)",
                    "Validasi Waktu",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (jamMulai > jamAkhir
                || (jamMulai == jamAkhir && menitMulai >= menitAkhir)) {

            JOptionPane.showMessageDialog(this,
                    "Waktu mulai harus lebih kecil dari waktu akhir",
                    "Validasi Waktu",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        String waktuMulai = String.format("%02d:%02d", jamMulai, menitMulai);
        String waktuAkhir = String.format("%02d:%02d", jamAkhir, menitAkhir);
        System.out.println("Kode Jadwal : " + kodeJadwal);
        System.out.println("Mata Kuliah : " + mataKuliah);
        System.out.println("Hari        : " + hari);
        System.out.println("Waktu Mulai : " + waktuMulai);
        System.out.println("Waktu Akhir : " + waktuAkhir);

        updateDat.updateJadwal(kodeJadwal, mataKuliah, hari, waktuMulai, waktuAkhir);
        tableJadwalSimple();
    }//GEN-LAST:event_updateJadwalActionPerformed

    private void delateJadwalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delateJadwalActionPerformed
        // TODO add your handling code here:
        String kode = getKdJdwNew.getText().trim();

        if (kode.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Kode Jadwal tidak boleh kosong!");
            return;
        }
        haspusData.deleteJadwal(kode);
        tableJadwalSimple();
    }//GEN-LAST:event_delateJadwalActionPerformed

    private void tbAbsenMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbAbsenMasukActionPerformed
        if (getNimAbsenMasuk.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM belum diisi!");
            return;
        }
        if (mataKuliahIn.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Pilih mata kuliah terlebih dahulu!");
            return;
        }
        long nim;
        try {
            nim = Long.parseLong(getNimAbsenMasuk.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "NIM harus berupa angka!");
            return;
        }

        String mataKuliah = mataKuliahIn.getSelectedItem().toString();
        if (jadwalMatkul.sudahAbsenHariIni(nim, mataKuliah)) {
            JOptionPane.showMessageDialog(this,
                    "Anda sudah melakukan absensi hari ini untuk mata kuliah ini!");
            return;
        }
        LocalTime jamMulai = jadwalMatkul.getJamMulaiByMataKuliah(mataKuliah);
        LocalTime jamAkhir = jadwalMatkul.getJamAkhirByMataKuliah(mataKuliah);

        if (jamMulai == null || jamAkhir == null) {
            JOptionPane.showMessageDialog(this, "Jadwal tidak ditemukan!");
            return;
        }

        LocalTime jamSekarang = LocalTime.now();
        if (jamSekarang.isBefore(jamMulai) || jamSekarang.isAfter(jamAkhir)) {
            JOptionPane.showMessageDialog(this,
                    "Absen hanya dapat dilakukan pada jam kuliah!\n"
                    + "Jam Kuliah: " + jamMulai + " - " + jamAkhir);
            return;
        }

        String status = "Hadir";
        insert_database.insertAbsensi(nim, mataKuliah, status);
        tampilRiwayatAbsensi();
        jadwalMatkul.tampilStatusByNim(
                nim,
                viewNameInfo,
                viewStatusInfo,
                viewJMInInfo,
                viewJMOutInfo
        );
    }//GEN-LAST:event_tbAbsenMasukActionPerformed

    private void tbAbsenOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tbAbsenOutActionPerformed
        // TODO add your handling code here:
        if (getNimAbsenKeluar.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM belum diisi!");
            return;
        }
        if (mataKuliahOut.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Pilih mata kuliah terlebih dahulu!");
            return;
        }
        long nim;
        try {
            nim = Long.parseLong(getNimAbsenKeluar.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "NIM harus berupa angka!");
            return;
        }
        LocalTime jamSekarang = LocalTime.now();
        String mataKuliah = mataKuliahOut.getSelectedItem().toString();
        Integer idAktif = jadwalMatkul.getIdAbsenAktif(nim, mataKuliah);

        LocalTime jamAkhir = jadwalMatkul.getJamAkhirByMataKuliah(mataKuliah);
        System.out.println("Status Absen : " + idAktif);

        if (jamSekarang.isBefore(jamAkhir)) {
            JOptionPane.showMessageDialog(this,
                    "Tinggu Waktu Pulang : " + jamAkhir);
            return;
        }
        if (idAktif == null) {
            JOptionPane.showMessageDialog(this,
                    "Belum melakukan absen masuk atau sudah absen keluar");
            return;
        }
        updateDat.updateAbsenKeluar(idAktif);
        tampilRiwayatAbsensi();
        jadwalMatkul.tampilStatusByNim(nim, viewNameInfo, viewStatusInfo, viewJMInInfo, viewJMOutInfo);
    }//GEN-LAST:event_tbAbsenOutActionPerformed

    private void daftarKTMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_daftarKTMMouseClicked
        // TODO add your handling code here:
        boolean condition = accessAdmin();
        if (condition == true) {
            Container.removeAll();
            Container.repaint();
            Container.revalidate();

            Container.add(containerKTP);
            Container.repaint();
            Container.revalidate();
            tampildaftar();
        }
    }//GEN-LAST:event_daftarKTMMouseClicked

    private void daftarKTMMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_daftarKTMMouseEntered
        // TODO add your handling code here:
        daftarKTM.setBackground(new Color(30, 50, 70)); // warna hover
    }//GEN-LAST:event_daftarKTMMouseEntered

    private void daftarKTMMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_daftarKTMMouseExited
        // TODO add your handling code here:
        daftarKTM.setBackground(new Color(18, 29, 40)); // warna normal
    }//GEN-LAST:event_daftarKTMMouseExited

    private void simpanNimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanNimActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simpanNimActionPerformed

    private void simpanRFIFDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanRFIFDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simpanRFIFDActionPerformed

    private void simpanNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpanNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_simpanNamaActionPerformed

    private void getDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getDataActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_getDataActionPerformed

    private void daftarkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_daftarkanActionPerformed
        // TODO add your handling code here:
        if (simpanRFIFD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID NOMOR KTM belum diisi!");
            return;
        }

        if (simpanNama.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "NAMA belum diisi!");
            return;
        }

        long nim;
        try {
            nim = Long.parseLong(simpanNim.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "NIM belum diisi!");
            return;
        }
        String simpanRfid = simpanRFIFD.getText().trim();
        String simpanaUser = simpanNama.getText().trim();

        insert_database.insertDaftarkan(simpanRfid, simpanaUser, nim);
        tampildaftar();
    }//GEN-LAST:event_daftarkanActionPerformed

    private void delateDataKTP2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delateDataKTP2ActionPerformed
        // TODO add your handling code here:
        if (simpanRFIFD.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID NOMOR KTM belum diisi!");
            return;
        }
        String simpanRfid = simpanRFIFD.getText().trim();
        haspusData.deleteKartu(simpanRfid);
        tampildaftar();
    }//GEN-LAST:event_delateDataKTP2ActionPerformed

    private void loaddataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loaddataActionPerformed
        // TODO add your handling code here:
        realtimedata.getLog(simpanRFIFD);
    }//GEN-LAST:event_loaddataActionPerformed

    private void viewDaftarKTMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewDaftarKTMMouseClicked
        // TODO add your handling code here:
        int row = viewDaftarKTM.getSelectedRow();
        if (row == -1) {
            return;
        }

        // ===== AMBIL DATA DARI TABLE =====
        String kartu = viewDaftarKTM.getValueAt(row, 0).toString();
        String nama = viewDaftarKTM.getValueAt(row, 1).toString();
        String nim = viewDaftarKTM.getValueAt(row, 2).toString();

        simpanNama.setText(nama);
        simpanRFIFD.setText(kartu);
        simpanNim.setText(nim);
    }//GEN-LAST:event_viewDaftarKTMMouseClicked

    private void tabelRiwayatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelRiwayatMouseClicked
        // TODO add your handling code here:
        int row = tabelRiwayat.getSelectedRow();
        if (row == -1) {
            return;
        }
        String nama = tabelRiwayat.getValueAt(row, 0).toString();
        String nim = tabelRiwayat.getValueAt(row, 1).toString();
        String mataKuliah = tabelRiwayat.getValueAt(row, 2).toString();
        String jamMasuk = tabelRiwayat.getValueAt(row, 4).toString();
        String jamKeluar = tabelRiwayat.getValueAt(row, 5).toString();
        String status = tabelRiwayat.getValueAt(row, 6).toString();

        getNimAbsenMasuk.setText(nim);
        mataKuliahIn.setSelectedItem(mataKuliah);
        getNimAbsenKeluar.setText(nim);
        mataKuliahOut.setSelectedItem(mataKuliah);

        viewNameInfo.setText(nama);
        viewStatusInfo.setText(status);
        viewJMInInfo.setText(
                jamMasuk == null || jamMasuk.isEmpty() ? "-" : jamMasuk
        );
        viewJMOutInfo.setText(
                jamKeluar == null || jamKeluar.isEmpty() ? "-" : jamKeluar
        );
    }//GEN-LAST:event_tabelRiwayatMouseClicked

    private void delateRiwayatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delateRiwayatActionPerformed
        // TODO add your handling code here:
        boolean status = accessAdmin();
        if (status == true) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Yakin ingin menghapus SEMUA data?",
                    "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (confirm == JOptionPane.YES_OPTION) {
                haspusData.deleteRiwayatAbsen();
                tampilRiwayatAbsensi();
                JOptionPane.showMessageDialog(
                        this,
                        "Data berhasil dihapus!",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        }
    }//GEN-LAST:event_delateRiwayatActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows Classic".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Absensi;
    private javax.swing.JPanel Container;
    private javax.swing.JPanel ContainerUtama;
    private javax.swing.JPanel Header;
    private javax.swing.JPanel Navbar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel containerAbsen;
    private javax.swing.JPanel containerData;
    private javax.swing.JPanel containerJadwall;
    private javax.swing.JPanel containerKTP;
    private javax.swing.JPanel daftarKTM;
    private javax.swing.JButton daftarkan;
    private javax.swing.JButton delate;
    private javax.swing.JButton delateDataKTP2;
    private javax.swing.JButton delateJadwal;
    private javax.swing.JToggleButton delateRiwayat;
    private javax.swing.JTextField getData;
    private javax.swing.JComboBox<String> getHariNew;
    private javax.swing.JTextField getJmAkhir;
    private javax.swing.JTextField getJmAwal;
    private javax.swing.JTextField getKdJdwNew;
    private javax.swing.JTextField getMnAkhir;
    private javax.swing.JTextField getMnAwal;
    private javax.swing.JTextField getMtkuliahNew;
    private javax.swing.JTextField getNameNew;
    private javax.swing.JTextField getNameNewedit;
    private javax.swing.JTextField getNimAbsenKeluar;
    private javax.swing.JTextField getNimAbsenMasuk;
    private javax.swing.JTextField getNimDelate;
    private javax.swing.JTextField getNimNew;
    private javax.swing.JTextField getNimNewedit;
    private javax.swing.JTextField getNimOldedit;
    private javax.swing.JButton insert;
    private javax.swing.JButton insertJadwal;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JTable jTable2;
    private javax.swing.JPanel jadwal;
    private javax.swing.JButton loaddata;
    private javax.swing.JComboBox<String> mataKuliahIn;
    private javax.swing.JComboBox<String> mataKuliahOut;
    private javax.swing.JPanel rfid;
    private javax.swing.JTextField simpanNama;
    private javax.swing.JTextField simpanNim;
    private javax.swing.JTextField simpanRFIFD;
    private javax.swing.JTable tabelRiwayat;
    private javax.swing.JButton tbAbsenMasuk;
    private javax.swing.JButton tbAbsenOut;
    private javax.swing.JButton update;
    private javax.swing.JButton updateJadwal;
    private javax.swing.JTable viewDaftarKTM;
    private javax.swing.JLabel viewJMInInfo;
    private javax.swing.JLabel viewJMOutInfo;
    private javax.swing.JTable viewJadwalSimple;
    private javax.swing.JLabel viewNameHaspusdata;
    private javax.swing.JLabel viewNameInfo;
    private javax.swing.JLabel viewStatusInfo;
    private javax.swing.JTable viewTabelJadwalU;
    private javax.swing.JTable viewTabelMahasiswa;
    // End of variables declaration//GEN-END:variables
}
