package com.kyz205.nottakip.service;

import com.kyz205.nottakip.model.Ders;
import com.kyz205.nottakip.model.Ogrenci;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvDisaAktariciTest {

    @TempDir
    Path geciciKlasor;

    private NotDefteri defter;
    private final CsvDisaAktarici csv = new CsvDisaAktarici();

    @BeforeEach
    void hazirla() {
        defter = new NotDefteri();
        defter.dersEkle(new Ders("KYZ205", "Nesne Tabanlı Programlama", 3));
        defter.ogrenciEkle(new Ogrenci("1001", "Ali", "Demir"));
        defter.ogrenciEkle(new Ogrenci("1002", "Zeynep", "Çelik"));
        defter.notAta("1001", "KYZ205", 70, 80); // 76,00 -> CB
        defter.notAta("1002", "KYZ205", 90, 95); // 93,00 -> AA
    }

    private List<String> satirlariOku(Path dosya) throws IOException {
        return Files.readAllLines(dosya, StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("Genel sıralama başlık ve sıralı satırlarla yazılır")
    void siralamaAktar() throws IOException {
        Path dosya = geciciKlasor.resolve("siralama.csv");
        csv.siralamaAktar(defter, dosya);

        List<String> satirlar = satirlariOku(dosya);
        assertEquals(3, satirlar.size());
        assertEquals("﻿Sıra;Öğrenci No;Ad;Soyad;Ders Sayısı;Ortalama;GANO", satirlar.get(0));
        assertEquals("1;1002;Zeynep;Çelik;1;93,00;4,00", satirlar.get(1));
        assertEquals("2;1001;Ali;Demir;1;76,00;2,50", satirlar.get(2));
    }

    @Test
    @DisplayName("Ders notları harf ve geçme durumuyla yazılır")
    void dersNotlariAktar() throws IOException {
        defter.ogrenciEkle(new Ogrenci("1003", "Can", "Aydın"));
        defter.notAta("1003", "KYZ205", 40, 40);

        Path dosya = geciciKlasor.resolve("kyz205.csv");
        csv.dersNotlariAktar(defter, "kyz205", dosya);

        List<String> satirlar = satirlariOku(dosya);
        assertEquals(4, satirlar.size());
        assertEquals("﻿Sıra;Öğrenci No;Ad Soyad;Vize;Final;Ortalama;Harf;Durum", satirlar.get(0));
        assertEquals("1;1002;Zeynep Çelik;90,00;95,00;93,00;AA;Geçti", satirlar.get(1));
        assertEquals("3;1003;Can Aydın;40,00;40,00;40,00;FF;Kaldı", satirlar.get(3));
    }

    @Test
    @DisplayName("Ayırıcı veya çift tırnak içeren alanlar tırnak içine alınır")
    void ozelKarakterlerKacislanir() throws IOException {
        defter.ogrenciEkle(new Ogrenci("1004", "Ali \"Reis\"", "Demir;Kaya"));

        Path dosya = geciciKlasor.resolve("kacis.csv");
        csv.siralamaAktar(defter, dosya);

        List<String> satirlar = satirlariOku(dosya);
        assertEquals("3;1004;\"Ali \"\"Reis\"\"\";\"Demir;Kaya\";0;0,00;0,00", satirlar.get(3));
        assertEquals("düz metin", CsvDisaAktarici.kacis("düz metin"));
    }

    @Test
    @DisplayName("Boş defter için yalnızca başlık satırı yazılır")
    void bosDefter() throws IOException {
        Path dosya = geciciKlasor.resolve("bos.csv");
        csv.siralamaAktar(new NotDefteri(), dosya);

        List<String> satirlar = satirlariOku(dosya);
        assertEquals(1, satirlar.size());
        assertTrue(satirlar.get(0).endsWith("GANO"));
    }

    @Test
    @DisplayName("Kayıtlı olmayan ders için dışa aktarma hata verir")
    void olmayanDers() {
        Path dosya = geciciKlasor.resolve("yok.csv");
        assertThrows(KayitBulunamadiException.class,
                () -> csv.dersNotlariAktar(defter, "YOK101", dosya));
    }
}
