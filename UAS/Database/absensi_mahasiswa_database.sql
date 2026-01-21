/*
SQLyog Ultimate v12.4.3 (64 bit)
MySQL - 10.4.27-MariaDB : Database - absensi_mahasiswa
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`absensi_mahasiswa` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `absensi_mahasiswa`;

/*Table structure for table `absensi` */

DROP TABLE IF EXISTS `absensi`;

CREATE TABLE `absensi` (
  `id_absensi` int(11) NOT NULL AUTO_INCREMENT,
  `nim` bigint(20) NOT NULL,
  `mata_kuliah` varchar(100) NOT NULL,
  `tanggal` date NOT NULL,
  `jam_masuk` time NOT NULL,
  `jam_keluar` time DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id_absensi`),
  KEY `Nim absensi ke nim mahasiswa` (`nim`),
  CONSTRAINT `Nim absensi ke nim mahasiswa` FOREIGN KEY (`nim`) REFERENCES `daftar_mahasiswa` (`nim`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `absensi` */

insert  into `absensi`(`id_absensi`,`nim`,`mata_kuliah`,`tanggal`,`jam_masuk`,`jam_keluar`,`status`) values 
(138,2455201014,'Statistika','2026-01-21','11:56:50','11:58:59','hadir/pulang'),
(139,2455201005,'Statistika','2026-01-21','11:57:59','11:58:47','hadir/pulang'),
(140,2455201001,'Statistika','2026-01-21','11:58:07','11:59:03','hadir/pulang');

/*Table structure for table `daftar_mahasiswa` */

DROP TABLE IF EXISTS `daftar_mahasiswa`;

CREATE TABLE `daftar_mahasiswa` (
  `nim` bigint(20) NOT NULL,
  `nama` varchar(100) NOT NULL,
  PRIMARY KEY (`nim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `daftar_mahasiswa` */

insert  into `daftar_mahasiswa`(`nim`,`nama`) values 
(2455201001,'Dandi'),
(2455201005,'Radit'),
(2455201014,'M.Niko Nur'),
(2455201018,'Sarat Rido');

/*Table structure for table `datarfid` */

DROP TABLE IF EXISTS `datarfid`;

CREATE TABLE `datarfid` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `nomor_kartu` varchar(100) DEFAULT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  PRIMARY KEY (`ID`),
  KEY `No kartu scaan to No Kartu terdaftar` (`nomor_kartu`),
  CONSTRAINT `No kartu scaan to No Kartu terdaftar` FOREIGN KEY (`nomor_kartu`) REFERENCES `rfidterdaftar` (`nomor_kartu`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1552 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `datarfid` */

/*Table structure for table `jadwal_kuliah` */

DROP TABLE IF EXISTS `jadwal_kuliah`;

CREATE TABLE `jadwal_kuliah` (
  `kode_jadwal` varchar(11) NOT NULL,
  `mata_kuliah` varchar(100) DEFAULT NULL,
  `hari` varchar(20) DEFAULT NULL,
  `jam_mulai` time DEFAULT NULL,
  `jam_selesai` time DEFAULT NULL,
  PRIMARY KEY (`kode_jadwal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `jadwal_kuliah` */

insert  into `jadwal_kuliah`(`kode_jadwal`,`mata_kuliah`,`hari`,`jam_mulai`,`jam_selesai`) values 
('PK3','Statistika','Rabu','10:00:00','11:50:00');

/*Table structure for table `log_rfid_scaan` */

DROP TABLE IF EXISTS `log_rfid_scaan`;

CREATE TABLE `log_rfid_scaan` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `datarfid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1119 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `log_rfid_scaan` */

insert  into `log_rfid_scaan`(`ID`,`datarfid`) values 
(977,'1A16FA3F'),
(978,'1A16FA3F'),
(979,'0586677D24E100'),
(980,'0586677D24E100'),
(981,'7AAEF23F'),
(982,'7AAEF23F'),
(983,'0586677D24E100'),
(984,'0586677D24E100'),
(985,'0586677D24E100'),
(986,'0586677D24E100'),
(987,'7AAEF23F'),
(988,'E912375A'),
(989,'7AAEF23F'),
(990,'0586677D24E100'),
(991,'0586677D24E100'),
(992,'1A16FA3F'),
(993,'1A16FA3F'),
(994,'E912375A'),
(995,'E912375A'),
(996,'7AAEF23F'),
(997,'0586677D24E100'),
(998,'0586677D24E100'),
(999,'0586677D24E100'),
(1000,'1A16FA3F'),
(1001,'0586677D24E100'),
(1002,'0586677D24E100'),
(1003,'0586677D24E100'),
(1004,'0586677D24E100'),
(1005,'0586677D24E100'),
(1006,'0586677D24E100'),
(1007,'0586677D24E100'),
(1008,'0586677D24E100'),
(1009,'1A16FA3F'),
(1010,'0586677D24E100'),
(1011,'0586677D24E100'),
(1012,'0586677D24E100'),
(1013,'0586677D24E100'),
(1014,'0586677D24E100'),
(1015,'0586677D24E100'),
(1016,'0586677D24E100'),
(1017,'1A16FA3F'),
(1018,'1A16FA3F'),
(1019,'0586677D24E100'),
(1020,'0586677D24E100'),
(1021,'0586677D24E100'),
(1022,'0586677D24E100'),
(1023,'0586677D24E100'),
(1024,'0586677D24E100'),
(1025,'0586677D24E100'),
(1026,'0586677D24E100'),
(1027,'0586677D24E100'),
(1028,'0586677D24E100'),
(1029,'0586677D24E100'),
(1030,'0586677D24E100'),
(1031,'0586677D24E100'),
(1032,'0586677D24E100'),
(1033,'0586677D24E100'),
(1034,'0586677D24E100'),
(1035,'0586677D24E100'),
(1036,'0586677D24E100'),
(1037,'0586677D24E100'),
(1038,'0586677D24E100'),
(1039,'0586677D24E100'),
(1040,'0586677D24E100'),
(1041,'0586677D24E100'),
(1042,'0586677D24E100'),
(1043,'0586677D24E100'),
(1044,'0586677D24E100'),
(1045,'0586677D24E100'),
(1046,'1890353546701C'),
(1047,'1890353546701C'),
(1048,'0586677D24E100'),
(1049,'1890353546701C'),
(1050,'188235E6DD2EFF'),
(1051,'188235E6DD2EFF'),
(1052,'188235E6DD2EFF'),
(1053,'188235E6DD2EFF'),
(1054,'188235E6DD2EFF'),
(1055,'188235E6DD2EFF'),
(1056,'0586677D24E100'),
(1057,'188235E6DD2EFF'),
(1058,'0586677D24E100'),
(1059,'188235E6DD2EFF'),
(1060,'188235E6DD2EFF'),
(1061,'0586677D24E100'),
(1062,'188235E6DD2EFF'),
(1063,'188235E6DD2EFF'),
(1064,'188235E6DD2EFF'),
(1065,'188235E6DD2EFF'),
(1066,'188235E6DD2EFF'),
(1067,'188235E6DD2EFF'),
(1068,'188235E6DD2EFF'),
(1069,'0586677D24E100'),
(1070,'0586677D24E100'),
(1071,'0586677D24E100'),
(1072,'0586677D24E100'),
(1073,'0586677D24E100'),
(1074,'0586677D24E100'),
(1075,'0586677D24E100'),
(1076,'188235E6DD2EFF'),
(1077,'0586677D24E100'),
(1078,'0586677D24E100'),
(1079,'0586677D24E100'),
(1080,'0588A551D6B200'),
(1081,'188235B16148DA'),
(1082,'0586677D24E100'),
(1083,'0586677D24E100'),
(1084,'0586677D24E100'),
(1085,'0586677D24E100'),
(1086,'0588A551D6B200'),
(1087,'0588A551D6B200'),
(1088,'188235A0B1E7BC'),
(1089,'188235A0B1E7BC'),
(1090,'188235A0B1E7BC'),
(1091,'188235A0B1E7BC'),
(1092,'05863B752FB200'),
(1093,'188235A0B1E7BC'),
(1094,'05863B752FB200'),
(1095,'05863B752FB200'),
(1096,'188235A0B1E7BC'),
(1097,'05863B752FB200'),
(1098,'05863B752FB200'),
(1099,'05863B752FB200'),
(1100,'05863B752FB200'),
(1101,'05863B752FB200'),
(1102,'05863B752FB200'),
(1103,'05863B752FB200'),
(1104,'188235A0B1E7BC'),
(1105,'188235A0B1E7BC'),
(1106,'188235A0B1E7BC'),
(1107,'188235A0B1E7BC'),
(1108,'188235A0B1E7BC'),
(1109,'188235A0B1E7BC'),
(1110,'188235A0B1E7BC'),
(1111,'188235A0B1E7BC'),
(1112,'0586677D24E100'),
(1113,'0586677D24E100'),
(1114,'0586677D24E100'),
(1115,'05863B752FB200'),
(1116,'0586677D24E100'),
(1117,'188235A0B1E7BC'),
(1118,'05863B752FB200');

/*Table structure for table `rfidterdaftar` */

DROP TABLE IF EXISTS `rfidterdaftar`;

CREATE TABLE `rfidterdaftar` (
  `id_rfid` int(11) NOT NULL AUTO_INCREMENT,
  `nomor_kartu` varchar(50) NOT NULL,
  `nama_pemilik` varchar(100) DEFAULT NULL,
  `NIM` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id_rfid`),
  UNIQUE KEY `nomor_kartu` (`nomor_kartu`),
  KEY `Nim daftar KTM ke Nim terdaftar` (`NIM`),
  CONSTRAINT `Nim daftar KTM ke Nim terdaftar` FOREIGN KEY (`NIM`) REFERENCES `daftar_mahasiswa` (`nim`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=122 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `rfidterdaftar` */

insert  into `rfidterdaftar`(`id_rfid`,`nomor_kartu`,`nama_pemilik`,`NIM`) values 
(118,'05863B752FB200','dandi',2455201001),
(120,'188235A0B1E7BC','M.Niko',2455201014),
(121,'0586677D24E100','radit',2455201005);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
