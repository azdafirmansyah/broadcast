-- MariaDB dump 10.17  Distrib 10.4.6-MariaDB, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: db_broadcasts
-- ------------------------------------------------------
-- Server version	10.4.6-MariaDB-1:10.4.6+maria~bionic

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `external_info`
--

DROP TABLE IF EXISTS `external_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `external_info` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `data` text DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` bigint(20) DEFAULT NULL,
  `external_id` bigint(20) DEFAULT NULL,
  `key_data` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `externals_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqoj6dyjicmhe6le4qbmnxmvy8` (`externals_id`),
  KEY `FKa9mcgqib51s48svjos2ys4fke` (`external_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `external_info`
--

LOCK TABLES `external_info` WRITE;
/*!40000 ALTER TABLE `external_info` DISABLE KEYS */;
INSERT INTO `external_info` VALUES (114,'2019-11-05 00:32:40',107,'{\"L\":\"Low\",\"M\":\"Middle\",\"H\":\"High\"}',NULL,NULL,108,'farmer','farmer',NULL,NULL,'jumlah petani',NULL),(113,'2019-11-05 00:30:23',106,'{\"seed\":\"tersedia\",\"water\":\"tersedia\",\"land\":\"tersedia\"}',NULL,NULL,108,'season','season',NULL,NULL,'musim tanam',NULL),(112,'2019-11-05 00:28:27',106,'{\"water\":\"kadar air 50\",\"land\":\"luas tanah 1 hektar\"}',NULL,NULL,108,'pupuk','pupuk',NULL,NULL,'pupuk kopi',NULL);
/*!40000 ALTER TABLE `external_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `externals`
--

DROP TABLE IF EXISTS `externals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `externals` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` bigint(20) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `externals`
--

LOCK TABLES `externals` WRITE;
/*!40000 ALTER TABLE `externals` DISABLE KEYS */;
INSERT INTO `externals` VALUES (109,'Sorong','2019-11-05 00:21:14',85,NULL,NULL,'Distribusi Hasil Panen Kopi','Mitra Murah Distribusi Panen Kopi',57,'033 87687',NULL,NULL),(108,'Madura','2019-11-05 00:19:20',85,NULL,NULL,'Pupuk Pertumbuhan Bunga Anggrek','Vendor Pupuk Mutiara',57,'099 787792',NULL,NULL);
/*!40000 ALTER TABLE `externals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (122);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `internal_info`
--

DROP TABLE IF EXISTS `internal_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `internal_info` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `data` text DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` bigint(20) DEFAULT NULL,
  `internal_id` bigint(20) DEFAULT NULL,
  `key_data` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `internals_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK4tuohbf0wttv2giu3tb26hwfe` (`internals_id`),
  KEY `FKnjny8xnvngm6svxfcqi0xek` (`internal_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `internal_info`
--

LOCK TABLES `internal_info` WRITE;
/*!40000 ALTER TABLE `internal_info` DISABLE KEYS */;
INSERT INTO `internal_info` VALUES (104,'2019-11-04 23:54:00',81,'{\"friday\":\"Milk\",\"wednesday\":\"Tea\"}',NULL,NULL,92,'drink','drink',NULL,NULL,'Minuman Pagi Karyawan',NULL),(103,'2019-11-04 23:52:43',81,'{\"size\":\"16\",\"weight\":\"41\"}',NULL,NULL,91,'whiteboard','whiteboard',NULL,NULL,'whiteboard karyawan',NULL),(102,'2019-11-04 23:51:22',81,'{\"memory\":\"6\",\"core\":\"4\"}',NULL,NULL,91,'laptop','laptop',NULL,NULL,'laptop karyawan',NULL),(105,'2019-11-04 23:56:09',82,'{\"friday\":\"Nasi Goreng\",\"wednesday\":\"Bubur Ayam\"}',NULL,NULL,92,'eat','eat',NULL,NULL,'Makanan Pagi Karyawan',NULL);
/*!40000 ALTER TABLE `internal_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `internals`
--

DROP TABLE IF EXISTS `internals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `internals` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` bigint(20) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `organizations_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKq8d7gevswy2cuuqms3vqty5fm` (`organizations_id`),
  KEY `FKckev0cv6ropp5uv0lmuwtk6fi` (`organization_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `internals`
--

LOCK TABLES `internals` WRITE;
/*!40000 ALTER TABLE `internals` DISABLE KEYS */;
INSERT INTO `internals` VALUES (92,'Samarinda','2019-11-04 23:10:22',37,NULL,NULL,'Kantor Bank di daerah kalimantan','Bank Cabang Kalimantan',56,'043 34324',NULL,NULL,NULL),(91,'Jambi','2019-11-04 23:07:47',37,NULL,NULL,'Kantor Bank di daerah sumatera','Bank Cabang Sumatera',56,'0741 434234',NULL,NULL,NULL),(93,'Toraja','2019-11-04 23:12:43',37,NULL,NULL,'Kantor Bank di daerah sulawesi','Bank Cabang Sulawesi',56,'087 34343',NULL,NULL,NULL),(94,'Solo','2019-11-04 23:15:28',38,NULL,NULL,'Kantor Tani Di Jawa','Tani Cabang Jawa',57,'022 4324234',NULL,NULL,NULL),(95,'Ende','2019-11-04 23:15:53',38,NULL,NULL,'Kantor Tani Di NTT','Tani Cabang NTT',57,'077 867678',NULL,NULL,NULL);
/*!40000 ALTER TABLE `internals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notifications`
--

DROP TABLE IF EXISTS `notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `notifications` (
  `id` bigint(20) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` bigint(20) DEFAULT NULL,
  `external_id` bigint(20) DEFAULT NULL,
  `finished_at` datetime DEFAULT NULL,
  `internal_id` bigint(20) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `organization_id` bigint(20) DEFAULT NULL,
  `published_at` datetime DEFAULT NULL,
  `started_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `user_id` text DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notifications`
--

LOCK TABLES `notifications` WRITE;
/*!40000 ALTER TABLE `notifications` DISABLE KEYS */;
INSERT INTO `notifications` VALUES (117,'2019-11-05 00:51:23',36,NULL,NULL,NULL,'2019-12-26 12:50:12',NULL,'Christmas Messsage',56,'2019-02-23 12:50:12','2019-03-26 12:50:12',NULL,NULL,NULL),(118,'2019-11-05 00:53:53',36,NULL,NULL,NULL,NULL,91,'Independent Day Messsage',56,'2019-07-21 12:50:12','2019-08-21 12:50:12',NULL,NULL,NULL),(116,'2019-11-05 00:50:04',36,NULL,NULL,NULL,'2019-05-26 12:50:12',NULL,'New Year Messsage',56,'2019-02-23 12:50:12','2019-03-26 12:50:12',NULL,NULL,NULL),(115,'2019-11-05 00:47:54',36,NULL,NULL,NULL,'2019-11-26 12:50:12',NULL,'Lebaran Messsage',NULL,'2019-02-23 12:50:12','2019-03-26 12:50:12',NULL,NULL,NULL),(119,'2019-11-05 00:57:30',36,NULL,NULL,108,'2019-10-25 12:50:12',91,'Fun Day Messsage',56,'2019-07-21 12:50:12','2019-08-21 12:50:12',NULL,NULL,NULL),(120,'2019-11-05 01:01:18',36,NULL,NULL,NULL,NULL,93,'Internal Sulawesi Messsage',NULL,'2019-08-21 12:50:12','2019-08-29 12:50:12',NULL,NULL,NULL),(121,'2019-11-05 01:03:24',36,NULL,NULL,NULL,NULL,NULL,'Private Day Messsage',NULL,'2019-07-21 12:50:12','2019-08-21 12:50:12',NULL,NULL,'|82|106|107|');
/*!40000 ALTER TABLE `notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_info`
--

DROP TABLE IF EXISTS `organization_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_info` (
  `id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `data` text DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` int(11) DEFAULT NULL,
  `key_data` varchar(255) DEFAULT NULL,
  `label` varchar(255) DEFAULT NULL,
  `organization_id` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `organizations_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfpdkg0dakrr2s0xwproyum544` (`organizations_id`),
  KEY `FKb4uce1hgmyglbepi1ykra5uyb` (`organization_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_info`
--

LOCK TABLES `organization_info` WRITE;
/*!40000 ALTER TABLE `organization_info` DISABLE KEYS */;
INSERT INTO `organization_info` VALUES (87,'2019-11-04 22:28:37',36,'{\"sunday\":\"jas\",\"friday\":\"batik\"}',NULL,NULL,'dress_code','Dress Code',56,NULL,NULL,'Baju Besar',NULL),(88,'2019-11-04 22:36:41',37,'{\"kue\":\"Banyak\",\"drink\":\"Jus\"}',NULL,NULL,'anniversary','anniversary',56,NULL,NULL,'Ulang Tahun Kantor',NULL),(89,'2019-11-04 22:38:26',38,'{\"L\":\"Laki-Laki\",\"P\":\"Perempuan\"}',NULL,NULL,'gender','gender',57,NULL,NULL,'Jenis Kelamin',NULL),(90,'2019-11-04 22:39:29',38,'{\"S\":\"Sumatera\",\"K\":\"kalimantan\"}',NULL,NULL,'edit region','region',57,'2019-11-04 22:44:29',38,'Wilayah Sebaran',NULL);
/*!40000 ALTER TABLE `organization_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_type`
--

DROP TABLE IF EXISTS `organization_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_type` (
  `id` bigint(20) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_type`
--

LOCK TABLES `organization_type` WRITE;
/*!40000 ALTER TABLE `organization_type` DISABLE KEYS */;
INSERT INTO `organization_type` VALUES (1,'HQ'),(2,'INTERNAL'),(3,'EXTERNAL');
/*!40000 ALTER TABLE `organization_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organizations`
--

DROP TABLE IF EXISTS `organizations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organizations` (
  `id` int(11) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` int(11) DEFAULT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organizations`
--

LOCK TABLES `organizations` WRITE;
/*!40000 ALTER TABLE `organizations` DISABLE KEYS */;
INSERT INTO `organizations` VALUES (56,'Jakarta','2019-11-04 03:56:23',36,NULL,0,'Kantor Pusat Bank','Bank HQ','021 333222',NULL,0),(57,'Bandung','2019-11-04 03:56:32',36,NULL,0,'Anak Perusahaan Bank 1','Tani Maju Jaya','021 88772',NULL,0);
/*!40000 ALTER TABLE `organizations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL,
  `description` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'super_admin','broadcast system super admin'),(2,'admin','system owners admin'),(3,'user','system users');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_privilages`
--

DROP TABLE IF EXISTS `user_privilages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_privilages` (
  `id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` int(11) DEFAULT NULL,
  `external_id` int(11) DEFAULT NULL,
  `internal_id` int(11) DEFAULT NULL,
  `organization_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `users_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb2j6cmd230o20guv3tdb75lmb` (`users_id`),
  KEY `FKe8oelslr18vuxh5cy210nlh2f` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_privilages`
--

LOCK TABLES `user_privilages` WRITE;
/*!40000 ALTER TABLE `user_privilages` DISABLE KEYS */;
INSERT INTO `user_privilages` VALUES (78,'2019-11-04 21:05:47',36,NULL,NULL,NULL,NULL,56,2,NULL,NULL,37,NULL),(79,'2019-11-04 21:09:35',36,NULL,NULL,NULL,NULL,57,2,NULL,NULL,38,NULL),(86,'2019-11-04 21:55:11',36,NULL,NULL,NULL,NULL,57,2,NULL,NULL,85,NULL),(96,'2019-11-04 23:35:36',37,NULL,NULL,NULL,91,NULL,2,NULL,NULL,81,NULL),(97,'2019-11-04 23:38:50',37,NULL,NULL,NULL,92,NULL,2,NULL,NULL,81,NULL),(98,'2019-11-04 23:39:55',37,NULL,NULL,NULL,92,NULL,2,NULL,NULL,82,NULL),(110,'2019-11-05 00:23:34',85,NULL,NULL,108,NULL,NULL,2,NULL,NULL,106,NULL),(111,'2019-11-05 00:25:52',85,NULL,NULL,108,NULL,NULL,2,NULL,NULL,107,NULL);
/*!40000 ALTER TABLE `user_privilages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `data` text DEFAULT NULL,
  `deleted_at` datetime DEFAULT NULL,
  `deleted_by` bigint(20) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `fullname` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `token` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `updated_by` bigint(20) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (36,'Jakarta','2019-11-03 07:20:09',0,'{\"instagram\":\"@su1\",\"facebook\":\"@su1\",\"twitter\":\"@su1\"}',NULL,0,'su@broadcast.com','Superadmin','540c34bbf833d2a33fabc87823ba122e033f1081a849c4ef21dbfd34b6d85ac2','0812 1123 1122','XIduHlvQDLSxk3cxc8EmpI6gmArUCqbbRlIqhR6DYg6wMVZQLeX5ZlpLixxaSMbn4DrMw2X18aA8sf0VBwoiPV17c0OBkLbuTW4x',NULL,0,'superadmin'),(37,'Surabaya','2019-11-03 07:21:18',36,'{\"instagram\":\"aa\",\"facebook\":\"bb\",\"twitter\":\"cc\"}',NULL,0,'admin1@broadcast.com','admin1','e2f13fb5bc47424d6b27b3ac1c605d33f1f598c1db269b044c3f59338d1e583f','0813 3344 5566','G0Jw5venkejkDRxPFTj8S70tlFOvy0EaiiJL3X4J6y7I6p0kvn5P9BO1oXtFuX98NlzgCUsCk5nv5mO5ufRfV0rNWhAIxN2TZolD','2019-11-03 07:48:43',36,'admin1'),(38,'Jakarta','2019-11-03 07:21:57',36,'{\"instagram\":\"@admin2\",\"facebook\":\"@admin2\",\"twitter\":\"@admin2\"}',NULL,0,'admin2@broadcast.com','admin2','af3d131396a3c479f9d31c2b9ef5ff9b4c4d1f222087eb24049311402c856702','021 123456','he4MiRlIqmOBhLznrOdlv3wlcAta3tb3C8grXeBi9H1Sae1DjGZk6rA3waoFuuz32xqKG8sYjNWv5PEavOsR1g1K4oUc9p5mr6CE',NULL,0,'admin2'),(81,'Jakarta','2019-11-04 21:25:13',37,'{\"instagram\":\"@useradm1\",\"facebook\":\"@useradm1\",\"twitter\":\"@useradm1\"}',NULL,NULL,'useradm1@broadcast.com','useradm1','c4f6a1acd723c18be7ac760552c009043fef6a85c453ccb42b5425740783d6ec','021 123456','x8w01uJHQ8fwTNAt0Ag175Bc5nJy9h54WpIHWFrNiOGg1LalrYiMwmFIhdU6iPuwxaqnYFRajilbTu057HXHpwwvvdqcCyiLPPDD',NULL,NULL,'useradm1'),(82,'Bali','2019-11-04 21:27:22',37,'{\"instagram\":\"@useradm1b\",\"facebook\":\"@useradm1b\",\"twitter\":\"@useradm1b\"}',NULL,NULL,'useradm1b@broadcast.com','useradm1b','fb5cc73445db93055a80093a0bc5c7da2b10e9b973d5790c0afaf00163dee2cd','021 123456','2ENwNteBZlxLJruslxgSwHMzipr3do7G1ZItqa8owogrV4Kit37biXSz4DnlcjEkCQbhsvB11EXgJ6iZ2eu1gSOoeNiu6PrvjSM8',NULL,NULL,'useradm1b'),(85,'NTB','2019-11-04 21:52:06',36,'{\"instagram\":\"@admin3\",\"facebook\":\"@admin3b\",\"twitter\":\"@admin3\"}',NULL,NULL,'admin3@broadcast.com','admin3','72c535b1171f05c58533f9a031ff6445ed4ae3460063c06816eca3040655b6af','021 123456','qfPoSWosr3JQsMtPU7ZMYqBzBxg5646QEvMmfjccMnYuRA8XDFk8PpntfWV3zjjsKfX8PhGgt0Hu3IfjfafQbEFlaGc27fQIUJzi',NULL,NULL,'admin3'),(106,'Derawan','2019-11-05 00:12:41',85,'{\"instagram\":\"@user1adm3\",\"facebook\":\"@user1adm3\",\"twitter\":\"@user1adm3\"}',NULL,NULL,'user1adm3@broadcast.com','user1adm3','925b347d3c151113b7dc1a9dcfd499efe2106c04f7b4c0240761447119611b67','021 123456','1IiIU3Nv8LLymCyeykVZahXABZDkVW2tJmFAtEI88TVXjTbmRjgKN4YlcXHUfXOMTCEugJdWo33NPxKjQNaa5V3YN1YvETHQbCKA',NULL,NULL,'user1adm3'),(107,'Halmahera','2019-11-05 00:13:40',85,'{\"instagram\":\"@user2adm3\",\"facebook\":\"@user2adm3\",\"twitter\":\"@user2adm3\"}',NULL,NULL,'user2adm3@broadcast.com','user2adm3','d35d2bd7aeedc5e37c7031e7401ed1620443f3eec08f7bb38c6099d1e72ec2d8','021 123456','Ia6Jfo8KSpI5rudbMYQm4tSpfSlyzN88p3NgZTYBY82zhNgBzhqO4tf0nHLoSKDG7YXtr0XctSBs14MCjDVvxsdiYCh2pcPKy0RX',NULL,NULL,'user2adm3');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-11-05  7:12:30
